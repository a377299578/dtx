package com.xiachao.rpc.request.handler;

import com.xiachao.rpc.RpcMessageUtil;
import com.xiachao.rpc.request.JoinTxReq;
import com.xiachao.rpc.response.JoinTxRes;
import com.xiachao.tx.DtxTransaction;
import com.xiachao.tx.GlobalTransactionContext;
import io.netty.channel.ChannelHandlerContext;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 *
 * @author xiachao
 * @version [V1.0, 2019-07-15]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class JoinTxReqHandler implements ReqResHandler<JoinTxReq> {


    @Override
    public void execute(ChannelHandlerContext ctx, JoinTxReq joinTxReq) {
        DtxTransaction dtxTransaction = GlobalTransactionContext.getInstance().getDtxTransaction(joinTxReq.getGlobalTxId());
        dtxTransaction.getLocalTxIds().put(joinTxReq.getJoiner(), joinTxReq.getLocalTxId());
        dtxTransaction.getParticipants().add(joinTxReq.getJoiner());
        JoinTxRes joinTxRes = new JoinTxRes();
        joinTxRes.setRequestKey(joinTxReq.getKey());
        joinTxRes.setTransactionInfo(dtxTransaction);
        joinTxRes.setRequest(joinTxReq);
        joinTxRes.setResponse(Boolean.TRUE);
        RpcMessageUtil.sendAsync(ctx.channel(), joinTxRes);
    }
}
