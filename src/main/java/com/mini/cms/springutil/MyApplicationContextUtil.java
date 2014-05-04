package com.mini.cms.springutil;

import org.apache.ibatis.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class MyApplicationContextUtil implements ApplicationContextAware {
	
	private static ApplicationContext applicationContext;//声明一个静态变量保存

     	@Override
     	public void setApplicationContext(ApplicationContext context) throws BeansException {
     		MyApplicationContextUtil.applicationContext = context;

     		LogFactory.useLog4JLogging();
     	}

	public static ApplicationContext getContext() {
		return applicationContext;
	}
	
}
