package com.xiachao.tx.client;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 *
 * @author xiachao
 * @version [V1.0, 2019-07-10]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Data
@ConfigurationProperties("dtx.client")
@Configuration
public class ClientConfig {
    /**
     * 分布式事务的参与者
     */
    private String appName;
}
