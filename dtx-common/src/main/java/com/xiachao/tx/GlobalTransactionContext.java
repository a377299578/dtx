package com.xiachao.tx;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * @author xiachao
 * @version [V1.0, 2019-07-05]
 */
public class GlobalTransactionContext {
    /**
     * 单例
     */
    private volatile static GlobalTransactionContext instance;

    private volatile static ConcurrentHashMap<String, String> starterMapToTxIdMap;

    private volatile static ConcurrentHashMap<String, DtxTransaction> dtxTransactionMap;

    /**
     * 全局id 对应的链接
     */
    private volatile static ConcurrentHashMap<String, ConnectionProxy> connectionProxyMap;

    private ScheduledExecutorService executorService;

    private GlobalTransactionContext() {
        executorService = Executors.newSingleThreadScheduledExecutor();
        dtxTransactionMap = new ConcurrentHashMap<>();
        connectionProxyMap = new ConcurrentHashMap<>();
        starterMapToTxIdMap = new ConcurrentHashMap<>();
    }

    public static GlobalTransactionContext getInstance() {
        if (instance == null) {
            synchronized (GlobalTransactionContext.class) {
                if (instance == null) {
                    instance = new GlobalTransactionContext();
                }
            }
        }
        return instance;
    }

    /**
     * 添加事务
     *
     * @param starter
     * @param localTxId
     * @param dtxTransaction
     */
    public void addDtxTransaction(String starter, String localTxId, DtxTransaction dtxTransaction) {
        starterMapToTxIdMap.putIfAbsent(dtxTransaction.getGlobalTxId(), generateId(starter, localTxId));
        dtxTransactionMap.put(generateId(starter, localTxId), dtxTransaction);
    }

    public Boolean containDtxTransaction(String starter, String localTxId) {
        return dtxTransactionMap.contains(generateId(starter, localTxId));
    }

    /**
     * 添加事务
     *
     * @param starter
     * @param localTxId
     */
    public DtxTransaction getDtxTransaction(String starter, String localTxId) {
        return dtxTransactionMap.getOrDefault(generateId(starter, localTxId), null);
    }


    public void addConnectionProxy(String globalTxId, ConnectionProxy sqlConnectionProxy) {
        connectionProxyMap.putIfAbsent(globalTxId, sqlConnectionProxy);
    }

    public ConnectionProxy getConnectionProxy(String globalTxId) {
        return connectionProxyMap.get(globalTxId);
    }

    public String generateId(String participant, String localTxId) {
        return participant + "_" + localTxId;
    }

    public DtxTransaction getDtxTransaction(String globalTxId) {
        return dtxTransactionMap.getOrDefault(starterMapToTxIdMap.get(globalTxId), null);
    }
}
