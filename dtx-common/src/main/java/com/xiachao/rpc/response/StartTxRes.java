package com.xiachao.rpc.response;

import com.xiachao.rpc.request.BaseReqRes;
import com.xiachao.rpc.request.StartTxReq;
import lombok.Data;

/**
 * 开启事务
 *
 * @author xiachao
 * @version [V1.0, 2019-07-09]
 */
@Data
public class StartTxRes extends BaseReqRes {


    private static final long serialVersionUID = 8111670387803480519L;
    private StartTxReq request;

    private String globalTxId;
}
