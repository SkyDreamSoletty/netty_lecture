package com.ssy.netty.firstexample;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class TestServer {

    public static void main(String[] args) throws Exception {
        //由bossGroup接受请求并不做处理的转发给workGroup
        EventLoopGroup boosGroup = new NioEventLoopGroup();
        EventLoopGroup workGroup = new NioEventLoopGroup();

        try{
            ServerBootstrap serverBootstrap = new ServerBootstrap();//一个允许很轻松的启动服务端的类，对启动相关类做了简单封装
            serverBootstrap.group(boosGroup,workGroup).channel(NioServerSocketChannel.class)
                    .childHandler(new TestServerInitializer());//自己编写的子处理器，对接收的请求做处理
            ChannelFuture channelFuture = serverBootstrap.bind(8899).sync();//绑定到指定端口
            channelFuture.channel().closeFuture().sync();//关闭监听
        }finally {
            //优雅关闭
            boosGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }


    }
}
