package com.xiachao.rpc.request.handler;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 *
 * @author xiachao
 * @version [V1.0, 2019-07-10]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class HanlderMap {

    public volatile static Map<Class, ReqResHandler> dispatchMap;


    public static void setDispatchMap(Map<Class, ReqResHandler> dispatchMap1){
        dispatchMap=dispatchMap1;
    }

    public static Map<Class, ReqResHandler> get(){
        return dispatchMap;
    }
}
