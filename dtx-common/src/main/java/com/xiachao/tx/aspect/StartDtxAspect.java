package com.xiachao.tx.aspect;

import com.xiachao.rpc.ChannelManager;
import com.xiachao.rpc.RpcMessageUtil;
import com.xiachao.rpc.request.FinishTxReq;
import com.xiachao.rpc.request.StartTxReq;
import com.xiachao.tx.DtxTransaction;
import com.xiachao.tx.GlobalTransactionContext;
import com.xiachao.tx.LocalTxContext;
import com.xiachao.tx.client.ClientConfig;
import io.netty.channel.Channel;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * @author xiachao
 * @version [V1.0, 2019-07-10]
 */
@Component
@Aspect
public class StartDtxAspect {

    @Autowired
    ClientConfig clientConfig;

    @Pointcut("@annotation(com.xiachao.tx.annotation.StartDtx)")
    public void pointcut() {
    }

    @Around("pointcut()")
    public void before(ProceedingJoinPoint joinPoint) throws Throwable {
        String localTxId = UUID.randomUUID().toString();
        LocalTxContext.defaultOrNew().setLocalTxId(localTxId);
        Channel channel = ChannelManager.getInstance().randomSelect();
        LocalTxContext.defaultOrNew().setChannel(channel);
        StartTxReq startReq = new StartTxReq();
        startReq.setLocalTxId(localTxId);
        startReq.setStarter(clientConfig.getAppName());
        RpcMessageUtil.sendSync(channel, startReq);
        DtxTransaction dtxTransaction = GlobalTransactionContext.getInstance().getDtxTransaction(clientConfig.getAppName(), localTxId);
        LocalTxContext.defaultOrNew().setDtxTransaction(dtxTransaction).setGlobalTxId(dtxTransaction.getGlobalTxId());
        startReq.setIsUse(Boolean.TRUE);
        FinishTxReq finishTxReq = new FinishTxReq();
        Integer txState = 200;
        try {
            joinPoint.proceed();
        } catch (Throwable throwable) {
            txState = 0;
            throw throwable;
        } finally {
            finishTxReq.setGlobalTxId(LocalTxContext.defaultOrNew().getGlobalTxId());
            finishTxReq.setTxState(txState);
            RpcMessageUtil.sendAsync(channel, finishTxReq);
        }

    }
}
