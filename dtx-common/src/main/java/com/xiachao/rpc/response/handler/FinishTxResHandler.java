package com.xiachao.rpc.response.handler;

import com.xiachao.rpc.request.handler.ReqResHandler;
import com.xiachao.rpc.response.FinishTxRes;
import com.xiachao.tx.LocalTxContext;
import io.netty.channel.ChannelHandlerContext;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 *
 * @author xiachao
 * @version [V1.0, 2019-07-11]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class FinishTxResHandler implements ReqResHandler<FinishTxRes> {
    @Override
    public void execute(ChannelHandlerContext ctx, FinishTxRes reqRes) {
        LocalTxContext.cur().getConnectionProxy().notify(reqRes.getTxState());
    }
}
