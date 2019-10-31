package org.spl.questions.services;

import org.spl.questions.configs.AppConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

abstract public class AbstractDbServiceLookUp
{
    private static final Logger logger = LoggerFactory.getLogger(AbstractDbServiceLookUp.class);
    
    protected static final String db;

    static
    {
        logger.info("Initialize app config");
        AppConfig appConfig = new AppConfig();
        db = appConfig.getCustomConfiguration().getDb(); 
    }
}
