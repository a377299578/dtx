package com.xiachao.rpc.response;

import com.xiachao.rpc.request.BaseReqRes;
import com.xiachao.rpc.request.PingReq;
import lombok.Data;

/**
 * @author xiachao
 * @version [V1.0, 2019-07-05]
 */
@Data
public class PingRes extends BaseReqRes {

    private static final long serialVersionUID = -782359167538495443L;
    private PingReq request;

}
