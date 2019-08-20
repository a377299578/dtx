package com.xiachao.rpc.request.handler;

import com.xiachao.rpc.RpcMessageUtil;
import com.xiachao.rpc.request.PingReq;
import com.xiachao.rpc.request.RpcMessage;
import com.xiachao.rpc.response.PingRes;
import io.netty.channel.ChannelHandlerContext;

/**
 *
 * @author xiachao
 * @version [V1.0, 2019-07-10]
 */
public class PingReqHandler implements ReqResHandler<PingReq> {

    @Override
    public void execute(ChannelHandlerContext ctx, PingReq pingReq) {
        PingRes pingRes = new PingRes();
        pingRes.setRequestKey(pingReq.getKey());
        pingRes.setRequest(pingReq);
        pingRes.setResponse(Boolean.TRUE);
        RpcMessageUtil.sendAsync(ctx.channel(),pingRes);
    }
}
