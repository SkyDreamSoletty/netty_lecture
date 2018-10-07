package com.csdn.netty.qq_18603599.handler.tcp;

import com.alibaba.fastjson.JSONObject;
import com.csdn.netty.qq_18603599.bean.DefaultFuture;
import com.csdn.netty.qq_18603599.bean.Response;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;


/**
 * & 读取服务端返回来的数据,并且转换成对应的response
 *
 * & 把该response和请求响应上下文绑定在一起
 */
public class TcpClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //判断服务端和客户端是在能够正常通信的情况下
        if(msg.toString().equals("ping")){
            ctx.channel().writeAndFlush("ping\r\n");
            return ;
        }
        System.out.println("客户端获取到服务端响应数据:"+msg.toString());

        String str = getJSONObject(msg.toString()).toString();
        //读取服务端的响应结果
        Response res = JSONObject.parseObject(str, Response.class);
        //存储响应结果
        DefaultFuture.recive(res);
    }

    private JSONObject getJSONObject(String str){
        JSONObject json = JSONObject.parseObject(str);
        json.remove("content");
        json.put("msg","保存用户信息成功");
        return json;
    }
}

