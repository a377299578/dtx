package com.xiachao.rpc.response.handler;

import com.alibaba.fastjson.JSON;
import com.xiachao.rpc.ReqResContext;
import com.xiachao.rpc.request.handler.ReqResHandler;
import com.xiachao.rpc.response.JoinTxRes;
import com.xiachao.tx.LocalTxContext;
import io.netty.channel.ChannelHandlerContext;

/**
 * @author xiachao
 * @version [V1.0, 2019-07-10]
 */
public class JoinTxResHandler implements ReqResHandler<JoinTxRes> {

    @Override
    public void execute(ChannelHandlerContext ctx, JoinTxRes reqRes) {
        System.out.println("JoinTxResHandler" + JSON.toJSONString(LocalTxContext.defaultOrNew().getDtxTransaction()));
        ReqResContext.getInstance().singal(ctx.channel(), reqRes);
    }
}
