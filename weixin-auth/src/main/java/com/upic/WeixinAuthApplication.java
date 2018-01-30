package com.upic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

import com.upic.weixin.filter.UserPrivilegeFilter;

@SpringBootApplication
public class WeixinAuthApplication {

	public static void main(String[] args) {
		SpringApplication.run(WeixinAuthApplication.class, args);
	}
	@Bean  
    public FilterRegistrationBean myFilter() {  
        FilterRegistrationBean myFilter = new FilterRegistrationBean();  
        myFilter.addUrlPatterns("/*");  
        myFilter.setFilter(new UserPrivilegeFilter());  
        return myFilter;  
    }   
}
