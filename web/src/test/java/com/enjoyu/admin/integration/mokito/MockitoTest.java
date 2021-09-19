package com.enjoyu.admin.integration.mokito;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.LinkedList;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MockitoTest {

    @Test
    @SuppressWarnings("unchecked")
    public void m() {
        List<String> mockedList = mock(List.class);
        when(mockedList.get(0)).thenReturn("first");

        System.out.println(mockedList.get(0));
        System.out.println(mockedList.get(999));
        mockedList.clear();

        verify(mockedList, atLeastOnce()).get(0);
        verify(mockedList).clear();
    }

    @Test
    public void s() {
        List<String> list = new LinkedList<>();
        List<String> spy = Mockito.spy(list);
        //由于 spy 是局部 mock，spy.get(0)会报错，因为会调用真实对象的get(0)
        //Mockito.when(spy.get(0)).thenReturn(3);
        //这种情况可以使用doReturn-when
        Mockito.doReturn(999).when(spy).get(999);
        when(spy.size()).thenReturn(100);
        spy.add("1");
        spy.add("2");
        verify(spy).add("1");
        Assert.assertEquals(100, spy.size());
        Assert.assertEquals("1", spy.get(0));
        Assert.assertEquals("2", spy.get(1));
        Assert.assertEquals("999", spy.get(999));
    }

    @Test
    public void testAnn() {
        when(ito.getData()).thenReturn("asdf");
        System.out.println(tmo.getIto().getData());
        System.out.println(tmo.hello());
    }

    @Mock
    InjectTestObject ito;
    @InjectMocks
    TestMockObject tmo;


}
