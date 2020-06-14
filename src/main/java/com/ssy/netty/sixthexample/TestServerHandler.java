package com.ssy.netty.sixthexample;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author: wangyingjie
 * @Date: 2020/6/14 8:04 下午
 * @Description:
 */
public class TestServerHandler extends SimpleChannelInboundHandler<MyDataInfo.MyMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MyDataInfo.MyMessage msg) throws Exception {
        MyDataInfo.MyMessage.DataType dataType = msg.getDataType();
        if(dataType == MyDataInfo.MyMessage.DataType.PersonType){
            System.out.println(msg.getPerson());
        }else if(dataType == MyDataInfo.MyMessage.DataType.DogType){
            System.out.println(msg.getDog());
        }else {
            System.out.println(msg.getCat());
        }
    }


}
