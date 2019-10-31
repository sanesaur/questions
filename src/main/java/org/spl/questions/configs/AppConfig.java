/**
 * 
 */
package org.spl.questions.configs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

/**
 * @author sane
 *
 */
@Component
public class AppConfig
{
    public static final Logger logger = LoggerFactory.getLogger(AppConfig.class);
    
    private static final String MONGO_TEMPLATE = "mongoTemplate";
    
    private static final String MONGO_CONFIG_FILE = "mongo-config.xml";
    
    private static final String CUSTOM_CONFIG = "customConfiguration";
    
    private static final String CUSTOM_CONFIG_FILE = "custom-config.xml";
    
    private static final GenericXmlApplicationContext mongoConfigContext;
    
    private static final GenericXmlApplicationContext custConfigContext;
    
    private static MongoTemplate mongoTemplate;
    
    private CustomConfiguration customConfiguration;
    
    static
    {
        logger.info("Initializing application config");
        mongoConfigContext = new GenericXmlApplicationContext(MONGO_CONFIG_FILE); 
        custConfigContext = new GenericXmlApplicationContext(CUSTOM_CONFIG_FILE);
    }
    
    public MongoTemplate getMongoTemplate()
    {
        if(mongoConfigContext != null)
        {
            logger.info("Mongo Config Context is available");
            
            if(mongoTemplate == null)
            {
                mongoTemplate = (MongoTemplate) mongoConfigContext.getBean(MONGO_TEMPLATE);
            }
        }
        logger.info("Mongo Template : {}", mongoTemplate);
        
        return mongoTemplate;
    }
    
    public CustomConfiguration getCustomConfiguration()
    {
        if(custConfigContext != null)
        {
            logger.info("Custom Config Context is available");
            customConfiguration = (CustomConfiguration) custConfigContext.getBean(CUSTOM_CONFIG);
        }
        
        logger.info("Custom Configuration : {}",customConfiguration);
        
        return customConfiguration;
    }
}
