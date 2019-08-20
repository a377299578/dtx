package com.xiachao.tx.aspect;

import com.alibaba.fastjson.JSON;
import com.xiachao.rpc.ChannelManager;
import com.xiachao.rpc.RpcMessageUtil;
import com.xiachao.rpc.request.JoinTxReq;
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
public class SupportDtxAspect {
    @Autowired
    ClientConfig clientConfig;

    @Pointcut("@annotation(com.xiachao.tx.annotation.SupportDtx)")
    public void pointcut() {
    }

    @Around("pointcut()")
    public void before(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("SupportDtx:" + JSON.toJSONString(LocalTxContext.defaultOrNew().getDtxTransaction()));
        String localTxId = UUID.randomUUID().toString();
        Channel channel = ChannelManager.getInstance().randomSelect();
        LocalTxContext.defaultOrNew().setLocalTxId(localTxId).setChannel(channel);
        JoinTxReq joinTxReq = new JoinTxReq();
        joinTxReq.setLocalTxId(localTxId);
        joinTxReq.setJoiner(clientConfig.getAppName());
        joinTxReq.setGlobalTxId(LocalTxContext.defaultOrNew().getGlobalTxId());
        RpcMessageUtil.sendSync(channel, joinTxReq);
        Object proceed = joinPoint.proceed();
    }
}
