package com.xiachao.utils;

import com.xiachao.rpc.coder.SerializerException;
import io.protostuff.LinkedBuffer;
import io.protostuff.ProtobufIOUtil;
import io.protostuff.Schema;
import io.protostuff.runtime.RuntimeSchema;
import org.objenesis.Objenesis;
import org.objenesis.ObjenesisStd;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author lorne 2017/11/11
 */
@SuppressWarnings("unchecked")
public class ProtostuffSerializer {

    private static ConcurrentHashMap<Class, Schema> map = new ConcurrentHashMap<>();

    private static final Objenesis OBJENESIS = new ObjenesisStd(true);


    private static <T> Schema<T> getSchema(Class<T> cls) {
        if (map.containsKey(cls)) {
            return map.get(cls);
        } else {
            map.putIfAbsent(cls, (Schema<T>) RuntimeSchema.getSchema(cls));
            return map.get(cls);
        }
    }


    public static byte[] serialize(Object obj) throws SerializerException {
        Class cls = obj.getClass();
        LinkedBuffer buffer = LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE);
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            Schema schema = getSchema(cls);
            ProtobufIOUtil.writeTo(outputStream, obj, schema, buffer);
            return outputStream.toByteArray();
        } catch (Exception e) {
            throw new SerializerException(e.getMessage(), e);
        } finally {
            buffer.clear();
        }
    }


    public static void serialize(Object obj, OutputStream outputStream) throws SerializerException {
        Class cls = obj.getClass();
        LinkedBuffer buffer = LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE);
        try {
            Schema schema = getSchema(cls);
            ProtobufIOUtil.writeTo(outputStream, obj, schema, buffer);
        } catch (Exception e) {
            throw new SerializerException(e.getMessage(), e);
        } finally {
            buffer.clear();
        }
    }


    public static <T> T deSerialize(byte[] param, Class<T> cls) throws SerializerException {
        T object;
        try (ByteArrayInputStream inputStream = new ByteArrayInputStream(param)) {
            object = OBJENESIS.newInstance(cls);
            Schema schema = getSchema(cls);
            ProtobufIOUtil.mergeFrom(inputStream, object, schema);
            return object;
        } catch (Exception e) {
            throw new SerializerException(e.getMessage(), e);
        }
    }

    public static <T> T deSerialize(InputStream inputStream, Class<T> cls) throws SerializerException {
        T object;
        try {
            object = OBJENESIS.newInstance(cls);
            Schema schema = getSchema(cls);
            ProtobufIOUtil.mergeFrom(inputStream, object, schema);
            return object;
        } catch (Exception e) {
            e.printStackTrace();
            throw new SerializerException(e.getMessage(), e);
        }
    }
}

