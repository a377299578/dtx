package com.xiachao.rpc.request.handler;

import com.alibaba.fastjson.JSON;
import com.xiachao.rpc.RpcMessageUtil;
import com.xiachao.rpc.request.StartTxReq;
import com.xiachao.rpc.response.StartTxRes;
import com.xiachao.tx.DtxTransaction;
import com.xiachao.tx.GlobalTransactionContext;
import com.xiachao.tx.LocalTxContext;
import com.xiachao.utils.CommonUtil;
import io.netty.channel.ChannelHandlerContext;

import java.util.UUID;

/**
 * @author xiachao
 * @version [V1.0, 2019-07-10]
 */
public class StartTxReqHandler implements ReqResHandler<StartTxReq> {

    @Override
    public void execute(ChannelHandlerContext ctx, StartTxReq startTxReq) {
        System.out.println("StartTxReqHandler"+ JSON.toJSONString(LocalTxContext.defaultOrNew().getDtxTransaction()));
        DtxTransaction dtxTransaction = GlobalTransactionContext.getInstance().getDtxTransaction(startTxReq.getStarter(), startTxReq.getLocalTxId());
        if (CommonUtil.isEmpty(dtxTransaction)) {
            dtxTransaction = new DtxTransaction();
            dtxTransaction.setGlobalTxId(UUID.randomUUID().toString());
            dtxTransaction.getParticipants().add(startTxReq.getStarter());
            dtxTransaction.getLocalTxIds().put(startTxReq.getStarter(), startTxReq.getLocalTxId());
        }
        StartTxRes startTxRes = new StartTxRes();
        startTxRes.setRequestKey(startTxReq.getKey());
        startTxRes.setGlobalTxId(dtxTransaction.getGlobalTxId());
        startTxRes.setTransactionInfo(dtxTransaction);
        startTxRes.setResponse(Boolean.TRUE);
        startTxRes.setRequest(startTxReq);
        RpcMessageUtil.sendAsync(ctx.channel(),startTxRes);
        GlobalTransactionContext.getInstance().addDtxTransaction(startTxReq.getStarter(), startTxReq.getLocalTxId(),dtxTransaction);
    }
}
