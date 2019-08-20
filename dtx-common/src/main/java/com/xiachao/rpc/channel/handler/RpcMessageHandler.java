package com.xiachao.rpc.channel.handler;

import com.xiachao.rpc.request.RpcMessage;
import com.xiachao.rpc.request.handler.HanlderMap;
import com.xiachao.rpc.request.handler.ReqResHandler;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Map;

/**
 * @author xiachao
 * @version [V1.0, 2019-07-05]
 */
@ChannelHandler.Sharable
public class RpcMessageHandler extends SimpleChannelInboundHandler<RpcMessage> {


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RpcMessage msg) {
        dispatch(ctx, msg);
    }

    private void dispatch(ChannelHandlerContext ctx, RpcMessage msg) {
        Object body = msg.getBody();
        HanlderMap.get().get(body.getClass()).execute(ctx, body);
    }
}
