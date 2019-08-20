package com.xiachao.utils;

import org.nustaq.serialization.FSTConfiguration;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 *
 * @author xiachao
 * @version [V1.0, 2019-07-16]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class FstUtil {

    private static ThreadLocal<FSTConfiguration> confs = new ThreadLocal() {
        @Override
        public FSTConfiguration initialValue() {
            return FSTConfiguration.createDefaultConfiguration();
        }
    };

    private static FSTConfiguration getFST() {
        return confs.get();
    }

    public static <T> byte[] serializer(T t) {
        return getFST().asByteArray(t);
    }


    public static <T> T deserializer(byte[] bytes, Class<T> c) {
        return (T) getFST().asObject(bytes);
    }

}
