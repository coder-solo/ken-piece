package com.ken.test;

import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;

public class NumberTest {

    // BigDecimal初始化参数最好是字符串类型，防止计算错误
    @Test
    public void testBigDecimal() {

        BigDecimal cost = new BigDecimal("999.9");
        BigDecimal costTotal = cost.multiply(new BigDecimal("3"));
        BigDecimal costExpect = new BigDecimal("2999.7");
        Assert.assertTrue(costTotal.equals(costExpect));

        BigDecimal costNum = new BigDecimal(999.9);
        BigDecimal costTotalNum = cost.multiply(new BigDecimal(3));
        BigDecimal costExpectNum = new BigDecimal(2999.7);
        Assert.assertFalse(costTotal.equals(costExpect));
    }
}
