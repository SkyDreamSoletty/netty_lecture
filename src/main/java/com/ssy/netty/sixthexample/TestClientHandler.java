package com.ssy.netty.sixthexample;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Random;

/**
 * @author: wangyingjie
 * @Date: 2020/6/14 8:09 下午
 * @Description:
 */
public class TestClientHandler extends SimpleChannelInboundHandler<MyDataInfo.Person> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MyDataInfo.Person msg) throws Exception {

    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        for(int i=0;i<10;i++){
            MyDataInfo.MyMessage myMessage = getMyMessage();
            ctx.writeAndFlush(myMessage);
        }
    }

    private MyDataInfo.MyMessage getMyMessage() {
        int randomInt = new Random().nextInt(3);
        MyDataInfo.MyMessage myMessage;
        if(0 == randomInt){
            MyDataInfo.Person person = MyDataInfo.Person.newBuilder().setName("张三").setAge(11).setAddress("testAddress").build();
            myMessage = MyDataInfo.MyMessage.newBuilder().setDataType(MyDataInfo.MyMessage.DataType.PersonType).setPerson(person).build();
        }else if(1 == randomInt){
            MyDataInfo.Dog dog = MyDataInfo.Dog.newBuilder().setName("李四").setAge(2).setAddress("testAddress").build();
            myMessage = MyDataInfo.MyMessage.newBuilder().setDataType(MyDataInfo.MyMessage.DataType.DogType).setDog(dog).build();
        }else {
            MyDataInfo.Cat cat = MyDataInfo.Cat.newBuilder().setName("王武").setAge(3).setColor("white").build();
            myMessage = MyDataInfo.MyMessage.newBuilder().setDataType(MyDataInfo.MyMessage.DataType.CatType).setCat(cat).build();
        }
        return myMessage;
    }

}
