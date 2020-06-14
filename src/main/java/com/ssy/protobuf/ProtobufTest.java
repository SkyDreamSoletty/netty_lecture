package com.ssy.protobuf;

import com.google.protobuf.InvalidProtocolBufferException;

/**
 * @author: wangyingjie
 * @Date: 2020/6/14 6:38 下午
 * @Description:
 */
public class ProtobufTest {

    public static void main(String[] args) throws InvalidProtocolBufferException {
        DataInfo.Student.Builder builder = DataInfo.Student.newBuilder();
        DataInfo.Student build = builder.setName("张三").setAge(11).setAddress("测试地址").build();
        byte[] student2ByteArray = build.toByteArray();
        DataInfo.Student student = DataInfo.Student.parseFrom(student2ByteArray);
        System.out.println(student.toString());

    }

}
