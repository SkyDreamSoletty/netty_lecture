package com.ssy.netty.secondexample;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.HttpObject;

import java.util.UUID;

public class MyServerHandler extends SimpleChannelInboundHandler<String> {

    /**
     * ChannelHandlerContext表示的是上下文 可以获得一些相关的信息，比如连接地址等
     * @param ctx
     * @param msg 真正的接受到的请求对象
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {

        System.out.println(msg.getClass());
        System.out.println(ctx.channel().remoteAddress()+", "+msg);
        ctx.channel().writeAndFlush("from server:"+ UUID.randomUUID());

    }


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush("来自于客户端的问候");//video_08 向服务端发出客户端的数据
    }

    /**
     * 出现异常后会调用此方法
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
