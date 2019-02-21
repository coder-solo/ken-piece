package com.ken.code;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class StreamTest {

    /**
     * Stream去重代码碎片
     */
    @Test
    public void testDistinct() {

        List<String> nameList = new ArrayList();
        nameList.add("Ken");
        nameList.add("Tom");
        nameList.add("Jerry");
        nameList.add("Tom");

        List<String> resultList = nameList.stream().distinct().collect(Collectors.toList());
        Assert.assertEquals(3, resultList.size());
    }
}
