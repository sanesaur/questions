package org.spl.questions.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class QuestionPaperDbServiceLookUp extends AbstractDbServiceLookUp
{
    public static final Logger logger = LoggerFactory.getLogger(QuestionPaperDbServiceLookUp.class);

    public static QuestionPaperDbService getQuestionPaperDbService()
    {
        QuestionPaperDbService questionPaperDbService = null;
        
        if(MultipleChoiceQuestionMongoDbService.MONGO_DB.equalsIgnoreCase(db))
        {
            questionPaperDbService = new QuestionPaperMongoDbService();
            
            logger.info("MultipleChoiceQuestionMongoDbService object is created.");
        }
        
        return questionPaperDbService;
    }

}
