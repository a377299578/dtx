package com.xiachao.rpc.request.handler;

import com.xiachao.rpc.RpcMessageUtil;
import com.xiachao.rpc.request.FinishTxReq;
import com.xiachao.rpc.response.FinishTxRes;
import io.netty.channel.ChannelHandlerContext;

/**
 * @author xiachao
 * @version [V1.0, 2019-07-11]
 */
public class FinishTxReqHandler implements ReqResHandler<FinishTxReq> {

    @Override
    public void execute(ChannelHandlerContext ctx, FinishTxReq req) {
        FinishTxRes finishTxRes = new FinishTxRes();
        finishTxRes.setRequest(req);
        finishTxRes.setRequestKey(req.getRequestKey());
        finishTxRes.setTxState(req.getTxState());
        finishTxRes.setGlobalTxId(req.getGlobalTxId());
        RpcMessageUtil.sendAsync(ctx.channel(), finishTxRes);
    }
}
