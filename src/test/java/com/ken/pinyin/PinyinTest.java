package com.ken.pinyin;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
import org.junit.Test;

public class PinyinTest {

    @Test
    public void test() {

        String name = "互联网六";
        char[] charArray = name.toCharArray();

        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        defaultFormat.setVCharType(HanyuPinyinVCharType.WITH_V);
        defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);

        StringBuilder pinyin = new StringBuilder();
        for (int i = 0; i < charArray.length; i++) {
            if (Character.toString(charArray[i]).matches("[\\u4E00-\\u9FA5]+")) {
                try {
                    pinyin.append(PinyinHelper.toHanyuPinyinStringArray(charArray[i], defaultFormat)[0]);
                } catch (BadHanyuPinyinOutputFormatCombination badHanyuPinyinOutputFormatCombination) {
                    badHanyuPinyinOutputFormatCombination.printStackTrace();
                }
            } else {
                pinyin.append(charArray[i]);
            }
        }
        System.out.println(name);
        System.out.println(pinyin.toString());
    }

    @Test
    public void transArea() {

        System.out.println(transToPinyin("北京市"));
        System.out.println(transToPinyin("上海市"));
        System.out.println(transToPinyin("六安市"));
        System.out.println(transToPinyin("江苏省"));
        System.out.println(transToPinyin("滨湖区"));
        System.out.println(transToPinyin("临沭县"));
    }

    private String transToPinyin(String name) {

        String tempName = name;
        if(name.endsWith("省") || name.endsWith("市") || name.endsWith("区") || name.endsWith("县")) {
            tempName = name.substring(0, name.length() - 1);
        }

        char[] nameCharArray = tempName.toCharArray();

        // 默认不带音标、v拼音翻译
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        defaultFormat.setVCharType(HanyuPinyinVCharType.WITH_V);

        StringBuilder pinyin = new StringBuilder();
        boolean isError = false;
        for (int i = 0; i < nameCharArray.length; i++) {
            // 判断是否中文，中文则翻译
            if (Character.toString(nameCharArray[i]).matches("[\\u4E00-\\u9FA5]+")) {
                try {
                    // 多音字默认使用第一个
                    pinyin.append(PinyinHelper.toHanyuPinyinStringArray(nameCharArray[i], defaultFormat)[0]);
                } catch (BadHanyuPinyinOutputFormatCombination badHanyuPinyinOutputFormatCombination) {
                    badHanyuPinyinOutputFormatCombination.printStackTrace();
                    isError = true;
                }
            } else {
                pinyin.append(nameCharArray[i]);
            }
        }

        String result = "";
        if(!isError && pinyin.length() > 0) {
            result = pinyin.toString();
            // 首字母大写
            result = result.substring(0,1).toUpperCase() + result.substring(1, result.length());
        }
        return result;
    }
}
