package com.csdn.那怪大尾巴狼咯;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;


public class NettyServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
//        增加任务处理
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(new MessageDecoder(), new MessageEncoder(), new NettyServerHandler());

    }
}
