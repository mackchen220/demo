package org.jeecg.modules.commons.util;

import lombok.extern.log4j.Log4j2;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;


/**
 * 该类提供了生成随机数的相关功能。
 *
 * @version 1.0.0 [2013.03.19]
 */
@Log4j2
public final class RandomUtil {

    private static final String[] NUMS = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
    private static final String[] LOWER = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};
    private static final String[] UPPER = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};



    private static final String RANDOM_STR = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    private static final java.util.Random RANDOM = new java.util.Random();

    public static String getRandomStr() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 16; i++) {
            sb.append(RANDOM_STR.charAt(RANDOM.nextInt(RANDOM_STR.length())));
        }
        return sb.toString();
    }


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
                default:
            }
        }
        return stringBuilder.toString();
    }

    /**
     * 生成一组指定长度的，由数字及英文字母组成的随机字符串
     *
     * @param length 生成的随机字符串的长度
     * @param letterType 字母类型 0-大小写混合 1-大写 2-小写
     * @param isRepeat 允许重复字符
     * @return 生成的随机字符串 参数错误返回空字符串
     */
    public static String nextNumberLetter(int length, int letterType, boolean isRepeat) {
        StringBuilder stringBuilder = new StringBuilder();
        if (length <= 0) {
            return "";
        }
        List<String> arr = new LinkedList<>(Arrays.asList(NUMS));
        switch (letterType) {
            case 0:
                arr.addAll(Arrays.asList(UPPER));
                arr.addAll(Arrays.asList(LOWER));
                break;
            case 1:
                arr.addAll(Arrays.asList(UPPER));
                break;
            case 2:
                arr.addAll(Arrays.asList(LOWER));
                break;
            default:
        }

        for (int i = 0; i < length; i++) {
            int index = nextNumber(0, arr.size() - 1);
            if (!isRepeat) {
                if (arr.size() < length) {
                    return "";
                }
                if (stringBuilder.indexOf(arr.get(index)) > -1) {
                    i -= 1;
                    continue;
                }
            }
            stringBuilder.append(arr.get(index));

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
                default:
            }
        }
        return stringBuilder.toString();
    }

    public static void main(String[] args) {
        System.out.println(nextInviteCode(6,6));
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

    /**
     * 随机整数
     * @return
     */
    public static int nextInt() {
        return RANDOM.nextInt();
    }

}
