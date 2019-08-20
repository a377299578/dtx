package com.xiachao.rpc;

import com.alibaba.fastjson.JSON;
import com.xiachao.rpc.request.BaseReqRes;
import com.xiachao.utils.CommonUtil;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author xiachao
 * @version [V1.0, 2019-07-05]
 */
public class ReqResContext {
    /**
     * 单例
     */
    private volatile static ReqResContext instance;

    private static AtomicInteger key;

    private volatile static ConcurrentHashMap<String, ConcurrentHashMap<Integer, BaseReqRes>> reqResMap;

    private ScheduledExecutorService executorService;


    private ReqResContext() {
        key = new AtomicInteger(0);
        reqResMap = new ConcurrentHashMap<>();
        executorService = Executors.newSingleThreadScheduledExecutor();
    }

    public static ReqResContext getInstance() {
        if (instance == null) {
            synchronized (ReqResContext.class) {
                if (instance == null) {
                    instance = new ReqResContext();
                }
            }
        }
        return instance;
    }

    /**
     * @return
     */
    public Integer getKey() {
        return key.incrementAndGet();
    }

    public void cacheReqRes(Channel channel, BaseReqRes baseReqRes) {
        ConcurrentHashMap<Integer, BaseReqRes> channelReqRes = reqResMap.get(CommonUtil.getChannelId(channel));
        if (channelReqRes==null) {
            synchronized (reqResMap) {
                channelReqRes = new ConcurrentHashMap<>();
                channelReqRes.putIfAbsent(baseReqRes.getKey(), baseReqRes);
                createTask(channelReqRes);
                reqResMap.putIfAbsent(CommonUtil.getChannelId(channel), channelReqRes);
            }
        } else {
            channelReqRes.putIfAbsent(baseReqRes.getKey(), baseReqRes);
        }
    }

    private void createTask(ConcurrentHashMap<Integer, BaseReqRes> channelReqRes) {
        executorService.scheduleAtFixedRate(() -> cleanHasRes(channelReqRes), 10L, 30L, TimeUnit.SECONDS);
    }

    public void singal(Channel channel, BaseReqRes baseReqRes) {
        ConcurrentHashMap<Integer, BaseReqRes> channelReqRes = reqResMap.get(CommonUtil.getChannelId(channel));
        BaseReqRes reqRes = channelReqRes.get(baseReqRes.getRequestKey());
        if (reqRes == null) {
            return;
        }
        reqRes.setResponse(baseReqRes.getResponse());
        reqRes.signal();
    }

    public void cleanHasRes(ConcurrentHashMap<Integer, BaseReqRes> channelReqRes) {
        Iterator<Map.Entry<Integer, BaseReqRes>> iterator = channelReqRes.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Integer, BaseReqRes> next = iterator.next();
            if ((next.getValue().getResponse()) && (next.getValue().getIsUse())) {
                iterator.remove();
            }
        }
    }
}
