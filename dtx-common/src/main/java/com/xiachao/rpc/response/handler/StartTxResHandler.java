package com.xiachao.rpc.response.handler;

import com.alibaba.fastjson.JSON;
import com.xiachao.rpc.ReqResContext;
import com.xiachao.rpc.request.StartTxReq;
import com.xiachao.rpc.request.handler.ReqResHandler;
import com.xiachao.rpc.response.StartTxRes;
import com.xiachao.tx.DtxTransaction;
import com.xiachao.tx.GlobalTransactionContext;
import com.xiachao.tx.ConnectionProxy;
import com.xiachao.tx.LocalTxContext;
import io.netty.channel.ChannelHandlerContext;

/**
 *
 * @author xiachao
 * @version [V1.0, 2019-07-10]
 */
public class StartTxResHandler implements ReqResHandler<StartTxRes> {

    @Override
    public void execute(ChannelHandlerContext ctx, StartTxRes reqRes) {
        System.out.println("StartTxResHandler"+ JSON.toJSONString(LocalTxContext.defaultOrNew().getDtxTransaction()));
        String globalTxId = reqRes.getGlobalTxId();
        DtxTransaction transactionInfo = reqRes.getTransactionInfo();
        //TODO
        StartTxReq startTxReq=reqRes.getRequest();
        GlobalTransactionContext.getInstance().addDtxTransaction(startTxReq.getStarter(),startTxReq.getLocalTxId(),transactionInfo);
        GlobalTransactionContext.getInstance().addConnectionProxy(globalTxId,new ConnectionProxy(null));
        ReqResContext.getInstance().singal(ctx.channel(), reqRes);
    }
}
