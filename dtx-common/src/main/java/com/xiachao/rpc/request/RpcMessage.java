package com.xiachao.rpc.request;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 *
 * @author xiachao
 * @version [V1.0, 2019-07-05]
 */
@Data
@Accessors(chain = true)
public class RpcMessage implements Serializable {

    private static final long serialVersionUID = 42840446647083899L;

    private Object body;

}
