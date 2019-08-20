package com.xiachao.tx;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author xiachao
 * @version [V1.0, 2019-07-10]
 */
@Data
public class DtxTransaction {
    /**
     * 事务id
     */
    private String globalTxId;
    /**
     * 事务参与者
     */
    private List<String> participants=new ArrayList<>();

    /**
     * 本地事务合集 key:participant; value:localTxId
     */
    private Map<String,String> localTxIds=new ConcurrentHashMap<>();

    /**
     * 本地事务超时时间
     */
    private Long timeOut;
}
