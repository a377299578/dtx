package com.xiachao.rpc.response;

import com.xiachao.rpc.request.BaseReqRes;
import com.xiachao.rpc.request.JoinTxReq;
import lombok.Data;

/**
 * @author xiachao
 * @version [V1.0, 2019-07-15]
 */
@Data
public class JoinTxRes extends BaseReqRes {
    private static final long serialVersionUID = 2998219951521297598L;
    private JoinTxReq request;
}
