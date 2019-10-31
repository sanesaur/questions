/**
 * 
 */
package org.spl.questions.services;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.spl.questions.configs.AppConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoTemplate;

/**
 * @author sane
 *
 */
public abstract class AbstractMongoDbService
{
    private static final Logger logger = LoggerFactory.getLogger(AbstractMongoDbService.class);
    
    public static final String MONGO_DB = "Mongo";
        
    private static Pattern pattern = Pattern.compile("[a-zA-Z0-9_]*");

    private static MongoTemplate mongoTemplate; 
    
    private static AppConfig appConfig;
    
    private String subject;
    
    public AbstractMongoDbService()
    {
        logger.info("Initializing mongo config");
        if(appConfig == null)
        {
            appConfig = new AppConfig();
            
            if(mongoTemplate == null)
            {
                mongoTemplate = appConfig.getMongoTemplate();
            }
        }
    }

    protected void setSubject(String subject, String collectionPrefix) throws Exception
    {
        Matcher matcher = pattern.matcher(subject);
        
        if(matcher.matches())
        {
            this.subject = collectionPrefix + subject.toLowerCase();
        }
        else
        {
            throw new Exception("Only subjects having [a-zA-Z0-9_] characters are allowed.");
        }
    }
    
    protected String getSubject()
    {
        return this.subject;
    }
    
    protected MongoTemplate getMongoTemplete()
    {
        return mongoTemplate;
    }
    
    protected Pattern getPattern()
    {
        return pattern;
    }
}
