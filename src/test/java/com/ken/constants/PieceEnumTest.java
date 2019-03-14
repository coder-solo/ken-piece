package com.ken.constants;

import org.junit.Assert;
import org.junit.Test;

public class PieceEnumTest {

	@Test
	public void testEnumContains() {
		boolean con1 = PieceEnum.PieceType.contains(1);
		Assert.assertTrue(con1);

		boolean con2 = PieceEnum.PieceType.contains(3);
		Assert.assertFalse(con2);

		boolean con3 = PieceEnum.PieceType.containsName("JAVA");
		Assert.assertTrue(con3);

		boolean con4 = PieceEnum.PieceType.containsName("java");
		Assert.assertFalse(con4);
	}
}
