package org.jeecg.modules.commons.util;

import lombok.extern.log4j.Log4j2;

import java.util.Random;


/**
 * 该类提供了生成随机数的相关功能。
 *
 * @version 1.0.0 [2013.03.19]
 */
@Log4j2
public final class RandomUtil {

    /**
     * 生成一组指定长度的，由数字及大写英文字母组成的随机字符串。
     *
     * @param length 生成的随机字符串的长度。
     * @return 生成的随机字符串。
     * @throws IllegalArgumentException 参数异常。
     */
    public static String nextNumberLetterToUpper(int length) {
        if (length < 0) {
            throw new IllegalArgumentException("length 参数异常");
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int type = nextNumber(0, 1);
            switch (type) {
                case 0:
                    stringBuilder.append(nextNumber(0, 9));
                    break;
                case 1:
                    stringBuilder.append((char) nextNumber(65, 90));
                    break;
            }
        }
        return stringBuilder.toString();
    }



    public static String nextInviteCode(int start, int end) {
        int length = nextNumber(start, end);
        StringBuilder stringBuilder = new StringBuilder();
        String s1 = "ABCDEFGHJKMNLUVWSYPQRST";
        String s2 = "23456789";
        char[] charArray1 = s1.toCharArray();
        char[] charArray2 = s2.toCharArray();
        Random random = new Random();
        for( int i = 0; i < length; i ++) {
            //字母数字几率给个一半
            int type = nextNumber(0, 1);
            log.info(type);
            switch (type) {
                case 0:
                    stringBuilder.append(charArray1[random.nextInt(charArray1.length)]);
                    break;
                case 1 :
                    stringBuilder.append(charArray2[random.nextInt(charArray2.length)]);
                    break;
            }
        }
        return stringBuilder.toString();
    }

    /**
     * 生成一个指定范围（包括该范围）内的随机数。
     *
     * @param start 随机数的起始范围。
     * @param end   随机数的结束范围。
     * @return 生成的随机数。
     * @throws IllegalArgumentException 参数异常。
     */
    public static int nextNumber(int start, int end) {
        if (end < start) {
            throw new IllegalArgumentException("end 参数异常");
        }
        Random random =  new Random();
        int i = random.nextInt(end - start + 1);
        return i + start;
    }

}
