package com.tet;

import org.junit.jupiter.api.Test;
import org.springframework.beans.DirectFieldAccessor;


public class DirectAccessTest {
    @Test
    public void t1() {
        Ace ace = new Ace();
        DirectFieldAccessor directFieldAccessor = new DirectFieldAccessor(ace);
        directFieldAccessor.setAutoGrowNestedPaths(true);
        directFieldAccessor.setPropertyValue("strArr[1]","123");
        directFieldAccessor.setPropertyValue("listStr[1]","abc");
        directFieldAccessor.setPropertyValue("listMap[0][xx]","123abc");
        System.out.println(ace.toString());
    }
}
