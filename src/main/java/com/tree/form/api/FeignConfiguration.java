package com.yunsom.form.api;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients
@ComponentScan({"com.yunsom.form.api.phpapi"})
public class FeignConfiguration {

}