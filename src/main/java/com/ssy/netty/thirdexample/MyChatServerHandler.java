package com.ssy.netty.thirdexample;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.util.UUID;

public class MyChatServerHandler extends SimpleChannelInboundHandler<String> {

    private static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);//netty自带的用来保存channel对象的类

    /**
     * ChannelHandlerContext表示的是上下文 可以获得一些相关的信息，比如连接地址等
     * @param ctx
     * @param msg 真正的接受到的请求对象
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        Channel channel = ctx.channel();

        channelGroup.forEach(ch ->{
                if(channel != ch){
                    ch.writeAndFlush(channel.remoteAddress()+"发送的消息："+msg+"\n");
                }else{
                    ch.writeAndFlush("【自己】 "+msg+"\n");
                }
            }
        );
        System.out.println(msg.getClass());
        System.out.println(ctx.channel().remoteAddress()+", "+msg);
        ctx.channel().writeAndFlush("from server:"+ UUID.randomUUID());
    }


    /**
     * 表示连接建立
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        channelGroup.writeAndFlush("【服务器】-"+channel.remoteAddress()+" 加入\n");//会遍历channelGroup中的每个channel并发送消息
        channelGroup.add(channel);
    }

    /**
     * 表示连接断开
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        channelGroup.writeAndFlush("【服务器】-"+channel.remoteAddress()+" 离开\n");//会遍历channelGroup中的每个channel并发送消息
        //netty会自动调用remove方法删除这个断开连接的channel
    }

    /**
     * 表示连接表示活动状态
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        channelGroup.writeAndFlush("【服务器】-"+channel.remoteAddress()+" 上线\n");//会遍历channelGroup中的每个channel并发送消息
    }

    /**
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        channelGroup.writeAndFlush("【服务器】-"+channel.remoteAddress()+" 下线\n");//会遍历channelGroup中的每个channel并发送消息
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
