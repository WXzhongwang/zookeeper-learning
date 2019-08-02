package com.zk.publishsubscribe;

import org.I0Itec.zkclient.exception.ZkMarshallingError;
import org.I0Itec.zkclient.serialize.ZkSerializer;

import java.nio.charset.Charset;

/**
 * @author dick <18668485565@163.com>
 * @version V1.0.0
 * @description 定义自己的系列化类
 * @date created on 2019/8/2
 */
public class MyZkSerializer implements ZkSerializer
{
    @Override
    public Object deserialize(byte[] bytes) throws ZkMarshallingError
    {
        return new String(bytes, Charset.forName("UTF-8"));
    }

    @Override
    public byte[] serialize(Object obj) throws ZkMarshallingError
    {
        return String.valueOf(obj).getBytes(Charset.forName("UTF-8"));
    }
}