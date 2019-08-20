package com.xiachao.tx.spring;

import com.alibaba.fastjson.JSON;
import com.xiachao.tx.DtxTransaction;
import com.xiachao.tx.LocalTxContext;
import com.xiachao.tx.client.ClientConfig;
import com.xiachao.utils.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 *
 * @author xiachao
 * @version [V1.0, 2019-07-11]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Component
public class RequestHeaderGet implements WebMvcConfigurer, HandlerInterceptor {

    @Autowired
    ClientConfig clientConfig;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("RequestHeaderGet"+JSON.toJSONString(LocalTxContext.defaultOrNew().getDtxTransaction()));
        String dtxTranscationString = request.getHeader("dtxTranscation");
        if(CommonUtil.isStringEmpty(dtxTranscationString)){
            return true;
        }
        DtxTransaction dtxTransaction = JSON.parseObject(dtxTranscationString, DtxTransaction.class);
        LocalTxContext.defaultOrNew().setGlobalTxId(dtxTransaction.getGlobalTxId()).setDtxTransaction(dtxTransaction);
        return true;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(this);
    }
}
