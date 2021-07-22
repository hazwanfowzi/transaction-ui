package maybank.project.service;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import maybank.project.utils.ExposedResourceBundleMessageSource;

@Service
public class MessageService {
	static Logger logger = LoggerFactory.getLogger(MessageService.class);
	
	@Cacheable(value="jsMessages",key="#locale")
	public String getMessages(Locale locale) {
		logger.debug("Generate messages.......");
		ExposedResourceBundleMessageSource msgSource= ExposedResourceBundleMessageSource.getInstance();
		
		String messages = msgSource.getKeys("messages/messages", locale);
		
		return messages;
		
	}
	
}
