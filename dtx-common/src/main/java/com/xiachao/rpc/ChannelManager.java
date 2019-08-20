package com.xiachao.rpc;

import com.xiachao.rpc.request.PingReq;
import com.xiachao.utils.CommonUtil;
import io.netty.channel.Channel;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author xiachao
 * @version [V1.0, 2019-07-05]
 */
public class ChannelManager {
    /**
     * 单例
     */
    private volatile static ChannelManager instance;

    private volatile static ConcurrentHashMap<String, Channel> channelMap;

    private ScheduledExecutorService executorService;

    private ChannelManager() {
        executorService = Executors.newSingleThreadScheduledExecutor();
        channelMap = new ConcurrentHashMap<>();
    }

    public static ChannelManager getInstance() {
        if (instance == null) {
            synchronized (ChannelManager.class) {
                if (instance == null) {
                    instance = new ChannelManager();
                }
            }
        }
        return instance;
    }

    public Channel add(Channel channel) {
        executorService.scheduleAtFixedRate(() -> ping(channel), 10L, 100L, TimeUnit.SECONDS);
        return channelMap.putIfAbsent(CommonUtil.getChannelId(channel), channel);
    }

    public Channel getChannel(String key) {
        return channelMap.get(key);
    }

    public void removeChannel(String key) {
        Channel channel = getChannel(key);
        channel.close();
        channelMap.remove(channel);
    }

    public void ping(Channel channel) {
        PingReq pingReq = new PingReq().setPing("1");
        RpcMessageUtil.sendSync(channel, pingReq);
        pingReq.setIsUse(Boolean.TRUE);
    }

    public Channel randomSelect() {
        return channelMap.entrySet().iterator().next().getValue();
    }
}
