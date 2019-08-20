package com.xiachao.tx.spring;

import com.alibaba.fastjson.JSON;
import com.xiachao.tx.DtxTransaction;
import com.xiachao.tx.LocalTxContext;
import feign.Feign;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 *
 * @author xiachao
 * @version [V1.0, 2019-07-11]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@ConditionalOnClass(Feign.class)
@Component
@Order
public class FeignRequestHeaderAdd implements RequestInterceptor {
    @PostConstruct
    public void init1(){
        System.out.println("RequestHeaderAdd");
    }
    @Override
    public void apply(RequestTemplate requestTemplate) {

        requestTemplate.header("dtxTranscation", JSON.toJSONString(getDtxTranscation()));
    }

    private DtxTransaction getDtxTranscation() {
        return LocalTxContext.defaultOrNew().getDtxTransaction();
    }
}
