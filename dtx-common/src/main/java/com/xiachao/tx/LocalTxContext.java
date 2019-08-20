package com.xiachao.tx;

import com.xiachao.utils.CommonUtil;
import io.netty.channel.Channel;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author xiachao
 * @version [V1.0, 2019-07-10]
 */
@Data
@Accessors(chain = true)
public class LocalTxContext {
    private static ThreadLocal<LocalTxContext> localTxContextThreadLocal = new ThreadLocal<>();

    private String globalTxId;

    private String localTxId;

    private ConnectionProxy connectionProxy;

    private Channel channel;

    private volatile DtxTransaction dtxTransaction;

    public static LocalTxContext defaultOrNew() {
        LocalTxContext localTxContext = LocalTxContext.localTxContextThreadLocal.get();
        if (CommonUtil.isEmpty(localTxContext)) {
            localTxContext = new LocalTxContext();
            localTxContextThreadLocal.set(localTxContext);
        }
        return localTxContext;
    }

    /**
     * 获取当前线程中的本地事务上下文
     *
     * @return
     */
    public static LocalTxContext cur() {
        return LocalTxContext.localTxContextThreadLocal.get();
    }
}
