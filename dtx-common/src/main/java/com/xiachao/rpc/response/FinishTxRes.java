package com.xiachao.rpc.response;

import com.xiachao.rpc.request.BaseReqRes;
import lombok.Data;

/**
 * 开启事务
 * @author xiachao
 * @version [V1.0, 2019-07-09]
 */
@Data
public class FinishTxRes extends BaseReqRes {


    private static final long serialVersionUID = 5802547111037839654L;
    private String globalTxId;

    private Integer txState;
}
