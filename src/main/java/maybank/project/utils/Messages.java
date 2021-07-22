package maybank.project.utils;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Component;


@Component
public class Messages {
	Logger logger = LoggerFactory.getLogger(Messages.class);

    @Autowired
    private MessageSource messageSource;

    private MessageSourceAccessor accessor;

    @PostConstruct
    private void init() {
    	logger.debug("LocaleContextHolder.getLocale() : " + LocaleContextHolder.getLocale());
        accessor = new MessageSourceAccessor(messageSource, LocaleContextHolder.getLocale());
    }

    public String get(String code) {
    	logger.debug("GET ... LocaleContextHolder.getLocale() : " + LocaleContextHolder.getLocale());
    	
        return accessor.getMessage(code, LocaleContextHolder.getLocale());
    }
    
    public String get(String code, String[] params) {
    	logger.debug("GET ... LocaleContextHolder.getLocale() : " + LocaleContextHolder.getLocale());
    	
        return accessor.getMessage(code, params, LocaleContextHolder.getLocale());
    }

}
