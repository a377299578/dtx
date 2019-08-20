package com.xiachao.utils;

import io.netty.channel.Channel;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 * 公共工具类
 *
 * @author xiachao
 */
public class CommonUtil {

    /**
     * 是否为空
     *
     * @param obj
     * @return
     */
    public static boolean isEmpty(Object obj) {
        if (obj == null) {
            return true;
        }
        if (obj instanceof String) {
            if (((String) obj).length() == 0) {
                return true;
            }
        } else if (obj instanceof Collection) {
            if (((Collection) obj).size() == 0) {
                return true;
            }
        } else if (obj instanceof Map) {
            if (((Map) obj).size() == 0) {
                return true;
            }
        } else if (obj instanceof List) {
            if (((List) obj).size() == 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * 是否为空
     *
     * @param obj
     * @return
     */
    public static boolean isNotEmpty(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj instanceof String) {
            if (((String) obj).length() > 0) {
                return true;
            }
        } else if (obj instanceof Collection) {
            if (((Collection) obj).size() > 0) {
                return true;
            }
        } else if (obj instanceof Map) {
            if (((Map) obj).size() > 0) {
                return true;
            }
        } else if (obj instanceof List) {
            if (((List) obj).size() > 0) {
                return true;
            }
        } else if (obj != null) {
            return true;
        }
        return false;
    }

    /**
     * 将对象转化为字符串
     *
     * @param obj
     * @return
     */
    public static String getObjToString(Object obj) {
        if (isEmpty(obj)) {
            return "";
        }
        return obj.toString();
    }

    /**
     * 将对象转化为int
     *
     * @param obj
     * @return
     */
    public static Integer getObjToInteger(Object obj) {
        if (isEmpty(obj)) {
            return 0;
        }
        return Integer.valueOf(getObjToString(obj));
    }

    /**
     * 转为BigDecimal
     *
     * @param str
     * @return
     */
    public static BigDecimal getStringToBigDecimal(String str) {
        if (str == null) {
            return new BigDecimal("0");
        }
        BigDecimal bd = new BigDecimal(str);
        return bd;
    }

    /**
     * 将对象转化为int
     *
     * @param obj
     * @param defval 默认值
     * @return
     */
    public static Integer getObjToInt(Object obj, int defval) {
        if (isEmpty(obj)) {
            return defval;
        }
        return Integer.valueOf(getObjToString(obj));
    }

    /**
     * 判断是否是字符串空
     *
     * @param str
     * @return
     */
    public static boolean isStringEmpty(String str) {
        return null == str || "".equals(str.trim());
    }

    /**
     * 比较两个集合的字符串是否完全相同
     *
     * @param list
     * @param list1
     * @return
     */
    public static boolean compareList(List<String> list, List<String> list1) {
        list.sort(Comparator.comparing(String::hashCode));
        list1.sort(Comparator.comparing(String::hashCode));
        return list.toString().equals(list1.toString());
    }


    public static String getChannelId(Channel channel){
        return channel.id().asShortText();
    }

}
