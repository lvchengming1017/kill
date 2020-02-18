package com.debug.kill.server.TomcatConfig;/*
 *FileName:  TomcatConfig
 * Author:   lvchengming
 * Description: TODO
 * Date  :   2020/2/17 11:21
 * Version: 1.0
 * */

import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;

@Configuration
public class TomcatConfig {

    @Bean
    public EmbeddedServletContainerFactory embeddedServletContainerFactory() {
        ConfigurableEmbeddedServletContainer factory = new TomcatEmbeddedServletContainerFactory();
        factory.setDocumentRoot(new File("E:\\mmm\\kill\\server\\src\\main\\webapp\\"));
        return (EmbeddedServletContainerFactory) factory;
    }
}