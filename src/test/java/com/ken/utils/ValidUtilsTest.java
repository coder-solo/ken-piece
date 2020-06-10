package com.ken.utils;

import org.junit.Assert;
import org.junit.Test;

public class ValidUtilsTest {

	@Test
	public void testValidIdCard() {

		Assert.assertTrue(ValidUtils.validIdCard("310101198701010019"));
		Assert.assertFalse(ValidUtils.validIdCard("31010119870101009"));
		Assert.assertFalse(ValidUtils.validIdCard("3101011987010100199"));
		Assert.assertFalse(ValidUtils.validIdCard("310101198701010018"));
		Assert.assertFalse(ValidUtils.validIdCard("310101198701010020"));
		Assert.assertFalse(ValidUtils.validIdCard("31010119870101002Y"));
	}

	@Test
	public void testValidCompanyIdCard() {

		Assert.assertTrue(ValidUtils.validCompanyIdCard("91320594566882209H"));
		Assert.assertTrue(ValidUtils.validCompanyIdCard("91320214693333296Q"));
		Assert.assertTrue(ValidUtils.validCompanyIdCard("91370781MA3D6DLE0N"));
		Assert.assertFalse(ValidUtils.validCompanyIdCard("913205945668822O9H"));
		Assert.assertFalse(ValidUtils.validCompanyIdCard("91370781MA3D6DLEON"));
	}
}
