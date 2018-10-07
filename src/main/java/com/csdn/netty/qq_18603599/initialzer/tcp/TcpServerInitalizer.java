package com.csdn.netty.qq_18603599.initialzer.tcp;

import com.csdn.netty.qq_18603599.handler.tcp.TcpServerHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

/**
 * 对应的初始化channel:这里和之前演示的基本功能不一样,因为客户端和服务端要一致保持联系,所以需要增加一个客户端和服务端之间的心跳检查机制handler,netty中已经为我们实现好了,可以直接拿过来使用
 */
public class TcpServerInitalizer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ch.pipeline().addLast(new DelimiterBasedFrameDecoder(Integer.MAX_VALUE, Delimiters.lineDelimiter()[0]));
        //添加客户端和服务端之间的心跳检查状态
        ch.pipeline().addLast(new IdleStateHandler(6, 2, 1, TimeUnit.SECONDS));
        ch.pipeline().addLast(new TcpServerHandler());
        ch.pipeline().addLast(new StringEncoder());
    }
}
