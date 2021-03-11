package com.tet;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.expression.BeanFactoryResolver;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.ParserContext;
import org.springframework.expression.common.TemplateParserContext;
import org.springframework.expression.spel.SpelEvaluationException;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SpELTest {

    ExpressionParser parser = new SpelExpressionParser();

    @Test
    public void literal() {
        Assertions.assertEquals(parser.parseExpression("123").getValue(), 123);
        Assertions.assertEquals(parser.parseExpression("true").getValue(), true);
        Assertions.assertEquals(parser.parseExpression("1L").getValue(), 1L);
        Assertions.assertEquals(parser.parseExpression("'hello'").getValue(), "hello");
    }

    @Test
    public void expression() {
        Assertions.assertEquals(parser.parseExpression("1 NE 2").getValue(), true);
        Assertions.assertEquals(parser.parseExpression("1 > 2").getValue(), false);
        Assertions.assertEquals(parser.parseExpression("1 between {21,12}").getValue(), false);
        Assertions.assertEquals(parser.parseExpression("1 + 4 * (3 / 2)").getValue(), 5);
        Assertions.assertEquals(parser.parseExpression("null ?: 12").getValue(), 12);
        Assertions.assertEquals(parser.parseExpression("12%10").getValue(), 2);
        Assertions.assertEquals(parser.parseExpression("2>1 and (!true || !false)").getValue(boolean.class), true);
        Assertions.assertEquals(parser.parseExpression("'123' matches '\\d{3}'").getValue(), true);
    }

    @Test
    public void object() {
        Assertions.assertEquals(parser.parseExpression("T(Integer).MAX_VALUE").getValue(int.class), Integer.MAX_VALUE);
        Assertions.assertEquals(parser.parseExpression("T(Integer).parseInt('321')").getValue(int.class), 321);
        Assertions.assertEquals(parser.parseExpression("new java.math.BigDecimal('12.333')").getValue(String.class), "12.333");
        Assertions.assertEquals(parser.parseExpression("new java.math.BigDecimal('12.333')").getValue(Integer.class), 12);
        Assertions.assertEquals(parser.parseExpression("'xyz' instanceof T(String)").getValue(boolean.class), true);
    }

    @Test
    public void variable() {
        StandardEvaluationContext context = new StandardEvaluationContext();
        LocalDateTime now = LocalDateTime.now();
        context.setVariable("now", now);
        Assertions.assertEquals(parser.parseExpression("#now").getValue(context, LocalDateTime.class), now);
        StandardEvaluationContext root = new StandardEvaluationContext();
        ContextRoot contextRoot = new ContextRoot("rootObj", "context value", new User("123", null));
        root.setRootObject(contextRoot);
        Assertions.assertSame(parser.parseExpression("#root").getValue(root), contextRoot);
        Assertions.assertSame(parser.parseExpression("#this").getValue(root), contextRoot);
        Assertions.assertEquals(parser.parseExpression("name").getValue(root), "rootObj");
        Assertions.assertNull(parser.parseExpression("user?.address?.location").getValue(root));
        Assertions.assertThrows(SpelEvaluationException.class, () -> parser.parseExpression("now").getValue(root));
        parser.parseExpression("value").setValue(root, "null");
        Assertions.assertEquals(parser.parseExpression("value").getValue(root), "null");
    }

    @Test
    public void function() throws NoSuchMethodException {
        StandardEvaluationContext context = new StandardEvaluationContext();
        Method subStr = ContextRoot.class.getDeclaredMethod("subStr", String.class, int.class, int.class);
        context.registerFunction("subStr", subStr);
        Assertions.assertEquals(parser.parseExpression("#subStr('0123456789',2,5)").getValue(context), "234");
    }

    @Test
    public void bean() {
        DefaultListableBeanFactory factory = new DefaultListableBeanFactory();
        ContextRoot contextRoot = new ContextRoot("rootObj", "context value", null);
        factory.registerSingleton("cr", contextRoot);
        StandardEvaluationContext context = new StandardEvaluationContext();
        context.setBeanResolver(new BeanFactoryResolver(factory));
        Assertions.assertSame(parser.parseExpression("@cr").getValue(context, ContextRoot.class), contextRoot);
    }

    @Test
    @SuppressWarnings({"unchecked"})
    public void list() {
        List<Integer> list = parser.parseExpression("{1,2,3}").getValue(List.class);
        Assertions.assertNotNull(list);
        Assertions.assertEquals(list.get(0), 1);
        Assertions.assertThrows(UnsupportedOperationException.class, () -> list.add(123));
        int[][] arr = parser.parseExpression("{{1,2,3},{4,5,6}}").getValue(int[][].class);
        Assertions.assertNotNull(arr);
        Assertions.assertNotNull(arr[0]);
        Assertions.assertEquals(arr[0][0], 1);
        StandardEvaluationContext context = new StandardEvaluationContext();
        List<String> strings = new ArrayList<>();
        strings.add("ss1");
        strings.add("ss2");
        context.setVariable("set", strings);
        Assertions.assertEquals(parser.parseExpression("#set[1]").getValue(context), "ss2");
        parser.parseExpression("#set[1]").setValue(context, "ns2");
        Assertions.assertEquals(parser.parseExpression("#set[1]").getValue(context), "ns2");
        System.out.println(parser.parseExpression("#set.![#this + '_copy']").getValue(context));
        Map<String, Integer> map = new HashMap<>();
        map.put("key", 111);
        map.put("key1", 88);
        context.setVariable("map", map);
        Assertions.assertEquals(parser.parseExpression("#map['key']").getValue(context), 111);
        parser.parseExpression("#map['key2']").setValue(context, 100);
        Assertions.assertEquals(parser.parseExpression("#map['key2']").getValue(context), 100);
        System.out.println(parser.parseExpression("#map.![key + value]").getValue(context));
        System.out.println(parser.parseExpression("#map.?[value > 100]").getValue(context));
        System.out.println(parser.parseExpression("#map.?[key != 'key']").getValue(context));
    }

    @Test
    public void template() {
        ParserContext parserContext = new TemplateParserContext();
        Expression expression = parser.parseExpression("#{#name} go to school", parserContext);
        StandardEvaluationContext evaluationContext = new StandardEvaluationContext();
        evaluationContext.setVariable("name", "ace");
        System.out.println(expression.getValue(evaluationContext));
    }


    static class ContextRoot {
        public ContextRoot(String name, String value, User user) {
            this.name = name;
            this.value = value;
            this.user = user;
        }

        String name;
        String value;
        User user;

        public static String subStr(String context, int start, int end) {
            return context.substring(start, end);
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public User getUser() {
            return user;
        }

        public void setUser(User user) {
            this.user = user;
        }
    }

    static class User {
        String id;
        Address address;

        public User(String id, Address address) {
            this.id = id;
            this.address = address;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public Address getAddress() {
            return address;
        }

        public void setAddress(Address address) {
            this.address = address;
        }
    }

    static class Address {
        public Address(String location) {
            this.location = location;
        }

        String location;

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }
    }
}
