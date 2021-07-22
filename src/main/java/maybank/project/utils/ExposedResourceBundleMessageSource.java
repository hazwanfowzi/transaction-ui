package maybank.project.utils;

import java.util.Locale;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ResourceBundleMessageSource;

public class ExposedResourceBundleMessageSource extends
ResourceBundleMessageSource {
	Logger logger = LoggerFactory.getLogger(ExposedResourceBundleMessageSource.class);
	private static ExposedResourceBundleMessageSource INSTANCE;
	
	public static ExposedResourceBundleMessageSource getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new ExposedResourceBundleMessageSource();
        }
         
        return INSTANCE;
    }

	 public String getKeys(String basename, Locale locale) {
		logger.debug("getKeys not cahche");
	        ResourceBundle bundle = getResourceBundle(basename, locale);
	        String messages = "";
	        for(Object key : bundle.keySet()){
	            
	        	messages += "\"" + (String)key + "\":\"" + bundle.getString((String)key).replace("\"", "'") + "\",";
	            
	        }
	        return messages;
	    }


}