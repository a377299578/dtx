package com.xiachao.rpc.request;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author xiachao
 * @version [V1.0, 2019-07-05]
 */
@Data
@Accessors(chain = true)
public class PingReq extends BaseReqRes {
        private static final long serialVersionUID = 2929845544898577933L;
        private String ping;
}
