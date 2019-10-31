/**
 * 
 */
package org.spl.questions.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author sane
 *
 */

public class MultiChoiceQuestionDbServiceLookUp extends AbstractDbServiceLookUp
{
    public static final Logger logger = LoggerFactory.getLogger(MultiChoiceQuestionDbServiceLookUp.class);
    
    public static MultipleChoiceQuestionDbService getMultiChoiceQuestionDbService()
    {
        MultipleChoiceQuestionDbService multipleChoiceQuestionDbService = null;
        
        if(MultipleChoiceQuestionMongoDbService.MONGO_DB.equalsIgnoreCase(db))
        {
            multipleChoiceQuestionDbService = new MultipleChoiceQuestionMongoDbService();
            
            logger.info("MultipleChoiceQuestionMongoDbService object is created.");
        }
        
        return multipleChoiceQuestionDbService;
    }
}
