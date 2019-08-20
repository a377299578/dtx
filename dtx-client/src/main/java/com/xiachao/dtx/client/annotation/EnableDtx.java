package com.xiachao.dtx.client.annotation;

import com.xiachao.dtx.client.RpcClient;
import com.xiachao.tx.aspect.DataSourceAspect;
import com.xiachao.tx.aspect.StartDtxAspect;
import com.xiachao.tx.aspect.SupportDtxAspect;
import com.xiachao.tx.client.ClientConfig;
import com.xiachao.tx.spring.FeignRequestHeaderAdd;
import com.xiachao.tx.spring.RequestHeaderGet;
import com.xiachao.tx.spring.RestTemplateHeaderAdd;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 *
 * @author xiachao
 * @version [V1.0, 2019-07-10]
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Import(value = {RpcClient.class, StartDtxAspect.class, ClientConfig.class, RequestHeaderGet.class, FeignRequestHeaderAdd.class, RestTemplateHeaderAdd.class, SupportDtxAspect.class, DataSourceAspect.class})
//@ComponentScan("com.xiachao")
public @interface EnableDtx {
    boolean enableTxc() default true;
}
