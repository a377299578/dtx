package com.xiachao.rpc.request;

import lombok.Data;

/**
 * 开启事务
 * @author xiachao
 * @version [V1.0, 2019-07-09]
 */
@Data
public class JoinTxReq extends BaseReqRes {

    private static final long serialVersionUID = 4851030311609548410L;
    private String joiner;

    private String localTxId;

    private String globalTxId;
}
