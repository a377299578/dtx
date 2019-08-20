package com.xiachao.rpc;

import com.xiachao.rpc.request.BaseReqRes;
import com.xiachao.rpc.request.RpcMessage;
import com.xiachao.utils.CommonUtil;
import io.netty.channel.Channel;

/**
 * @author xiachao
 * @version [V1.0, 2019-07-11]
 */
public class RpcMessageUtil {

    public static void sendAsync(Channel channel, BaseReqRes baseReqRes) {
        baseReqRes.setChannelId(CommonUtil.getChannelId(channel));
        channel.writeAndFlush(new RpcMessage().setBody(baseReqRes));
    }

    public static void sendSync(Channel channel, BaseReqRes baseReqRes) {
        ReqResContext.getInstance().cacheReqRes(channel, baseReqRes);
        baseReqRes.setChannelId(CommonUtil.getChannelId(channel));
        channel.writeAndFlush(new RpcMessage().setBody(baseReqRes));
        baseReqRes.await(10000);
    }
}
