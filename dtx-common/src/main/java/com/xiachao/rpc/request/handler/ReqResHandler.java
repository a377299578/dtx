package com.xiachao.rpc.request.handler;

import com.xiachao.rpc.request.BaseReqRes;
import io.netty.channel.ChannelHandlerContext;

/**
 * @author xiachao
 * @version [V1.0, 2019-07-10]
 */
public interface ReqResHandler<T> {
    /**
     * 执行
     * @param ctx
     * @param reqRes
     */
    void execute(ChannelHandlerContext ctx, T reqRes);
}
