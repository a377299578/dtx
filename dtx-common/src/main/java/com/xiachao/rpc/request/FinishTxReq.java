package com.xiachao.rpc.request;

import lombok.Data;

/**
 * 开启事务
 * @author xiachao
 * @version [V1.0, 2019-07-09]
 */
@Data
public class FinishTxReq extends BaseReqRes {

    private static final long serialVersionUID = 4084169976616683243L;
    private String globalTxId;

    private Integer txState;

    private String starter;

    private String localTxId;
}
