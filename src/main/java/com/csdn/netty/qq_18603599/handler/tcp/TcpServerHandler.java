package com.csdn.netty.qq_18603599.handler.tcp;

import com.alibaba.fastjson.JSONObject;
import com.csdn.netty.qq_18603599.bean.Response;
import com.csdn.netty.qq_18603599.bean.ServerRequest;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

import java.nio.charset.Charset;

/**
 * & 获取客户端的request
 *
 * & 解析完成成之后实例化对应的response
 *
 * & 写入reponse到客户端并加上\r\n
 */
public class TcpServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        if(msg instanceof ByteBuf){
            ByteBuf req = (ByteBuf)msg;
            String content = req.toString(Charset.defaultCharset());
            System.out.println("服务端开始读取客户端的请求数据:"+content);
            //获取客户端的请求信息
            ServerRequest request = JSONObject.parseObject(content,ServerRequest.class);
            JSONObject user = (JSONObject) request.getContent();
            user.put("success","ok");
            //写入解析请求之后结果对应的响应信息
            Response res = new Response();
            res.setId(request.getId());
            res.setContent(user);
            //先写入
            ctx.channel().write(JSONObject.toJSONString(res));
            //再一起刷新
            ctx.channel().writeAndFlush("\r\n");
            System.out.println("      ");
        }
    }


    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt)
            throws Exception {

        if(evt instanceof IdleStateEvent){
            IdleStateEvent event = (IdleStateEvent)evt;
            if(event.equals(IdleState.READER_IDLE)){
                System.out.println("读空闲====");
                ctx.close();
            }else if(event.equals(IdleState.WRITER_IDLE)){
                System.out.println("写空闲====");
            }else if(event.equals(IdleState.WRITER_IDLE)){
                System.out.println("读写空闲====");
                ctx.channel().writeAndFlush("ping\r\n");
            }

        }

        super.userEventTriggered(ctx, evt);
    }
}
