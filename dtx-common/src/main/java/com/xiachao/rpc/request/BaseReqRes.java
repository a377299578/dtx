package com.xiachao.rpc.request;

import com.xiachao.rpc.ReqResContext;
import com.xiachao.tx.DtxTransaction;
import lombok.Data;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author xiachao
 * @version [V1.0, 2019-07-05]
 */
@Data
public class BaseReqRes implements Serializable {

    private static final long serialVersionUID = -4968733563691735880L;

    @Override
    public String toString() {
        return "BaseReqRes{" +
                "key=" + key +
                ", channelId='" + channelId + '\'' +
                ", request='" + request + '\'' +
                ", response='" + response + '\'' +
                ", isUse=" + isUse +
                '}';
    }

    private Integer key;

    private Integer requestKey;

    private Lock lock;

    private Condition condition;

    private String channelId;

    private BaseReqRes request;

    private Boolean response;

    private Boolean isUse;

    private DtxTransaction transactionInfo;

    public BaseReqRes() {
        key = ReqResContext.getInstance().getKey();
        response = Boolean.FALSE;
        isUse = Boolean.FALSE;
        lock = new ReentrantLock();
        condition = lock.newCondition();
    }

    public void await() {
        await(1);
    }

    public void await(long timeOut) {
        if (!response) {
            lock.lock();
            try {
                condition.await(timeOut, TimeUnit.MILLISECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }

    public void signal() {
        lock.lock();
        condition.signal();
        lock.unlock();
    }
}
