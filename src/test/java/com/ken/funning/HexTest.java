package com.ken.funning;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * 本地测试文件
 */
public class HexTest {

	@Test
	public void testCustomChange() {

		int a = 1;
		int b = 3;
		int c = 4;
		int tc = (1 << a) + (1 << b) + (1 << c);
		String ct = Long.toBinaryString(tc);
		System.out.println(ct);

		long cn = Long.valueOf(ct, 2);
		System.out.println(cn);

		String t = Long.toBinaryString(cn);
		for (int i = 0, len = t.length(); i < t.length(); i++) {
			char cr = t.charAt(i);
			if (cr == '1') {
				System.out.println(len - i - 1);
			}
		}

	}

	@Test
	public void testConvAdaptChange() {

		List<Integer> paramList = new ArrayList<Integer>();
		paramList.add(1);
		paramList.add(2);
		paramList.add(3);
		Long tranTmp = convAdaptObject2Long(paramList);
		System.out.println(tranTmp);

		List<Integer> resultList = convAdaptObject2List(tranTmp);
		resultList.forEach(System.out::println);
	}

	// {1,3}转换为2+8=10
	private Long convAdaptObject2Long(List<Integer> adaptObjectTypeList) {

		if (adaptObjectTypeList == null || adaptObjectTypeList.size() < 1) {
			System.out.println("error");
			return 0L;
		}
		Long result = 0L;
		for (Integer item : adaptObjectTypeList) {
			result = (long) (result + Math.pow(2, item));
		}
		return result;
	}

	// 10转换为{1,3}
	private List<Integer> convAdaptObject2List(long adaptObject) {

		StringBuffer binary = new StringBuffer();
		while (adaptObject != 0) {
			long quotient = adaptObject / 2; // 商
			long remender = adaptObject % 2; // 余数
			binary.append(remender);
			adaptObject = quotient;
		}

		binary = binary.reverse(); // 逆序

		List<Integer> list = new ArrayList<>();
		for (int i = 0; i < binary.toString().length(); i++) {
			char c = binary.charAt(i);
			if (c == '1') {
				list.add(i + 1);
			}
		}
		return list;
	}
}
