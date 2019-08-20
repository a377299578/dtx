package com.xiachao.rpc.request;

import lombok.Data;

/**
 * 开启事务
 * @author xiachao
 * @version [V1.0, 2019-07-09]
 */
@Data
public class StartTxReq extends BaseReqRes {


    private static final long serialVersionUID = -1791437296007267303L;
    private String starter;

    private String localTxId;
}
