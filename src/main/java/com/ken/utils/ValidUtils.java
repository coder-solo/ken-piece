package com.ken.utils;

import org.apache.commons.lang3.StringUtils;

/**
 * 各种内容校验</br>
 * <p>
 * https://any86.github.io/any-rule/
 * </p>
 */
public class ValidUtils {

	//身份证前17位每位加权因子
	private static int[] power = {7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2};

	//身份证第18位校检码
	private static String[] refNumber = {"1", "0", "X", "9", "8", "7", "6", "5", "4", "3", "2"};

	// 18位身份证
	public static boolean validIdCard(String value) {
		return StringUtils.isNotBlank(value) && value.matches("^[1-9]\\d{5}(?:18|19|20)\\d{2}(?:0\\d|10|11|12)(?:0[1-9]|[1-2]\\d|30|31)\\d{3}[\\dXx]$")
				&& validIdCardLastNum(value);
	}

	// 统一信用代码
	public static boolean validCompanyIdCard(String value) {
		return StringUtils.isNotBlank(value) && value.matches("^[0-9A-HJ-NPQRTUWXY]{2}\\d{6}[0-9A-HJ-NPQRTUWXY]{10}$");
	}

	// 校验身份证最后一位
	private static boolean validIdCardLastNum(String idCard) {
		if (idCard.length() != 18) {
			return false;
		}

		String checkCode = calcCheckCode(idCard);
		String lastNum = idCard.substring(17, 18);
		if (!checkCode.equalsIgnoreCase(lastNum)) {
			return false;
		}
		return true;
	}

	// 计算第18位校验码
	private static String calcCheckCode(String idNo) {

		char[] tmp = idNo.toCharArray();
		int result = 0;
		for (int i = 0; i < power.length; i++) {
			result += power[i] * Integer.parseInt(tmp[i] + "");
		}
		return refNumber[(result % 11)];
	}
}
