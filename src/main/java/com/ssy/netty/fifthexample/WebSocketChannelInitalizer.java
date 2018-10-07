package com.ssy.netty.fifthexample;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

public class WebSocketChannelInitalizer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();

        pipeline.addLast(new HttpServerCodec());//http的编解码器
        pipeline.addLast(new ChunkedWriteHandler());//以块的方式来进行写操作的处理器
        pipeline.addLast(new HttpObjectAggregator(8192));//对http消息进行聚合的处理器 netty本身对消息是做分段处理的，将消息聚合为一个完整的请求/响应
        pipeline.addLast(new WebSocketServerProtocolHandler("/ws"));//可以处理关于webSocket的繁重的处理  /ws-协议名字

        pipeline.addLast(new TextWebSokcetFrameHandler());

    }

}
