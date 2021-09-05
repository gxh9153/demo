package com.gxh.demo.config;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * 用于配置不需要保护的资源路径
 */
@Data
@EnableConfigurationProperties(IgnoreUrlsConfig.class)
@ConfigurationProperties(prefix = "secure.ignored")
public class IgnoreUrlsConfig {

    private List<String> urls = new ArrayList<>();

}
