package com.xiachao.rpc.channel.handler;

import com.xiachao.rpc.ChannelManager;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author xiachao
 * @version [V1.0, 2019-07-05]
 */
public class ChannelRegisterHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channelRegistered");
        ChannelManager.getInstance().add(ctx.channel());
    }
}
