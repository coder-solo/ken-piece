package com.ken.utils;

import java.util.Random;

/**
 * 字符串共通类
 * 
 * @author ken
 */
public class CharacterUtils {

	/**
	 * 随机数字
	 * 
	 * @param length
	 * @return
	 */
	public static String randomNumber(int length) {
		char[] chs = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
		return random(length, chs);
	}

	/**
	 * 随机数字+字母
	 * 
	 * @param length
	 * @return
	 */
	public static String randomCharacter(int length) {
		char[] chs = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I',
				'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd',
				'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y',
				'z' };
		return random(length, chs);
	}

	/**
	 * 随机字母
	 * 
	 * @param length
	 * @return
	 */
	public static String randomLetters(int length) {
		char[] chs = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S',
				'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n',
				'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z' };
		return random(length, chs);
	}

	private static String random(int length, char[] chs) {
		int chsLength = chs.length;
		Random random = new Random();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < length; i++) {
			sb.append(chs[random.nextInt(chsLength)]);
		}
		return sb.toString();
	}
}
