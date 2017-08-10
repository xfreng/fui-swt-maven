package com.fui.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringMvcUtils {
	private static final Logger logger = LoggerFactory.getLogger(CommonConfiguration.class);

	private static ThreadLocal<ApplicationContext> currThreadLocal = new ThreadLocal<ApplicationContext>();

	public static <T> T get(Class<T> requiredType) {
		logger.info("spring mvc config init ...");
		String[] fileUrl = new String[] { "conf/applicationContext-dao.xml", "conf/applicationContext-bean.xml" };
		if (currThreadLocal.get() == null) {
			ApplicationContext applicationContext = new ClassPathXmlApplicationContext(fileUrl);
			currThreadLocal.set(applicationContext);
			logger.info("spring mvc config init finished ...");
			return applicationContext.getBean(requiredType);
		} else {
			return currThreadLocal.get().getBean(requiredType);
		}
	}
}
