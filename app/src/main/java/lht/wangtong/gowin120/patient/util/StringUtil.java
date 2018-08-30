package lht.wangtong.gowin120.patient.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * author: wqlin
 * time: 2016/4/11 17:10
 */
public class StringUtil {
    public static String null2Empty(String str) {
        if (str == null) {
            return "";
        }
        return str;
    }
    public static boolean isName(String name) {
        if (name == null) {
            return false;
        }
        //Pattern pattern = Pattern.compile("[\u4E00-\u9FBF]+");
        Pattern pattern = Pattern.compile("[\\u4e00-\\u9fa5a-z0-9A-Z]{1}");
        int len=name.length();
        for (int i = 0; i <len; i++) {
            if (!pattern.matcher(String.valueOf(name.charAt(i))).matches()){
                return false;
            }
        }
        return true;
    }
    /**

     * 判定输入汉字

     *

     * @param c

     * @return

     */

    public static boolean isChinese(char c) {

        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);

        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {

            return true;

        }

        return false;

    }



    /**

     * 检测String是否全是中文

     *

     * @param name

     * @return

     */

    public static boolean checkNameChese(String name) {

        boolean res = true;

        char[] cTemp = name.toCharArray();

        for (int i = 0; i < name.length(); i++) {

            if (!isChinese(cTemp[i])) {

                res = false;

                break;

            }

        }

        return res;

    }
    /**
     * 只能判断部分CJK字符（CJK统一汉字）
     */

    public static boolean isChineseByREG(String str) {
        if (str == null) {
            return false;
        }
        //Pattern pattern = Pattern.compile("[\u4E00-\u9FBF]+");
        Pattern pattern = Pattern.compile("[\\u4e00-\\u9fa5]{1}");
        int len=str.length();
        for (int i = 0; i <len; i++) {
            if (!pattern.matcher(String.valueOf(str.charAt(i))).matches()){
                return false;
            }
        }
        return true;
    }

    /*// 根据Unicode编码完美的判断中文汉字和符号
    private static boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION) {
            return true;
        }
        return false;
    }*/

    /**
     * 判断字符串是否只含有数字与(大小)字母
     * @param str
     * @return
     */
    public static boolean isLetterNum(String str) {
        String regex = "^[a-z0-9A-Z]+$";
        return str.matches(regex);
    }
    /**
     * 判断是否为浮点数或者整数
     * @param str
     * @return true Or false
     */
    public static boolean isNumeric(String str){
        Pattern pattern = Pattern.compile("^(-?\\d+)(\\.\\d+)?$");
        Matcher isNum = pattern.matcher(str);
        if( !isNum.matches() ){
            return false;
        }
        return true;
    }

    /**
     * 判断是否为正确的邮件格式
     * @param str
     * @return boolean
     */
    public static boolean isEmail(String str){
        if(isEmpty(str))
            return false;
        return str.matches("^[\\w-]+(\\.[\\w-]+)*@[\\w-]+(\\.[\\w-]+)+$");
    }

    /**
     * 判断字符串是否为合法手机号 11位 13 14 15 17 18开头
     * @param str
     * @return boolean
     */
    public static boolean isMobile(String str){
        if(isEmpty(str))
            return false;
        return str.matches("^(13|14|15|17|18)\\d{9}$");
    }

    /**
     * 判断是否为数字
     * @param str
     * @return
     */
    public static boolean isNumber(String str) {
        try{
            Integer.parseInt(str);
            return true;
        }catch(Exception ex){
            return false;
        }
    }

    /**
     * 判断是否为double
     * @param str
     * @return
     */
    public static boolean isDouble(String str) {
        try{
            Double.parseDouble(str);
            return true;
        }catch(Exception ex){
            return false;
        }
    }
    /**
     * 判断字符串是否为非空(包含null与"")
     * @param str
     * @return
     */
    public static boolean isNotEmpty(String str){
        if(str == null || "".equals(str))
            return false;
        return true;
    }

    /**
     * 判断字符串是否为非空(包含null与"","    ")
     * @param str
     * @return
     */
    public static boolean isNotEmptyIgnoreBlank(String str){
        if(str == null || "".equals(str) || "".equals(str.trim()))
            return false;
        return true;
    }

    /**
     * 判断字符串是否为空(包含null与"")
     * @param str
     * @return
     */
    public static boolean isEmpty(String str){
        if(str == null || "".equals(str.trim()))
            return true;
        return false;
    }

    /**
     * 判断字符串是否为空(包含null与"","    ")
     * @param str
     * @return
     */
    public static boolean isEmptyIgnoreBlank(String str){
        if(str == null || "".equals(str) || "".equals(str.trim()))
            return true;
        return false;
    }

    /**
     * 将1-9数字转为汉字
     * @param num
     * 1-9数字
     * @return
     */
    public static String num2HanZi(int num) {
        switch (num) {
            case 1:
                return "一";
            case 2:
                return "二";
            case 3:
                return "三";
            case 4:
                return "四";
            case 5:
                return "五";
            case 6:
                return "六";
            case 7:
                return "七";
            case 8:
                return "八";
            case 9:
                return "九";
            default:
                return "";
        }
    }

    /**
     * 将字符串的中间字符串替换成format字符串
     * 左闭右开
     * @param str
     * @param format
     * @param start
     * @param end
     * @return
     */
    public static String ellipsis(String str, String format, int start, int end) {

        return str.replace(str.substring(start,end),format);
    }
    /**
     * 密码格式校验
     *
     * @param password
     * @return
     */
    public static boolean checkPassword(String password) {
        if (password != null) {
            return password.matches("^[0-9a-z_A-Z]{6,20}$");
        }
        return false;
    }

}
