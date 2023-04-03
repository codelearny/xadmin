package com.enjoyu.admin.jdbc;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.insert.Insert;
import net.sf.jsqlparser.statement.select.*;
import org.junit.jupiter.api.Test;

/**
 * {@link net.sf.jsqlparser.util.TablesNamesFinder}
 */
public class SqlParserTest {
    @Test
    public void test() throws JSQLParserException {
        Statement statement = CCJSqlParserUtil.parse("select t.customer_type, c.company_id,t.development_channel\n" +
                "from customer.cc_party t,customer.cc_customer c\n" +
                "where\n" +
                "t.customer_code = c.customer_code\n" +
                "and\n" +
                "(c.customer_id = :customerId)");
        if (statement instanceof Insert) {
            Column column = ((Insert) statement).getColumns().get(0);
            System.out.println(column);
        } else if (statement instanceof Select) {
            SelectBody selectBody = ((Select) statement).getSelectBody();
            System.out.println(selectBody);
            selectBody.accept(new SelectVisitorAdapter() {
                @Override
                public void visit(PlainSelect plainSelect) {
                    System.out.println("=================");


                    FromItem fromItem = plainSelect.getFromItem();
                    fromItem.accept(new FromItemVisitorAdapter() {
                        @Override
                        public void visit(Table table) {
                            System.out.println("+++++++++");
                            System.out.println(table);
                            System.out.println(table.getDatabase());
                            System.out.println(table.getSchemaName());
                            System.out.println("+++++++++");
                        }
                    });
                    System.out.println(fromItem);
                    for (Join join : plainSelect.getJoins()) {

                    }
                    System.out.println();
                }
            });
        }


    }


}
