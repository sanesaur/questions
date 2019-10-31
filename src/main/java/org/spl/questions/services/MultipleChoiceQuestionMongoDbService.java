/**
 * 
 */
package org.spl.questions.services;

import java.util.List;



import org.spl.questions.models.MultipleChoiceQuestion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;
import com.mongodb.client.result.DeleteResult;

/**
 * @author sane
 *
 */
@Component
public class MultipleChoiceQuestionMongoDbService extends AbstractMongoDbService
                                        implements MultipleChoiceQuestionDbService
{
    static final private Logger logger = LoggerFactory.getLogger(MultipleChoiceQuestionMongoDbService.class);
    
    private static final String COLLECTION_PREFIX = "qb_";
    
    @Override
    public void setSubject(String subject) throws Exception
    {
        super.setSubject(subject, COLLECTION_PREFIX);
    }
    
    @Override
    public MultipleChoiceQuestion create(MultipleChoiceQuestion multipleChoiceQuestion)
                                            throws DuplicateKeyException 
    {
        logger.info("Subject : {}", getSubject());
        logger.info("Question : {}", multipleChoiceQuestion.getQuestion());
        
        
        Query query = new Query(Criteria.where(MultipleChoiceQuestion.QUESTION).is(multipleChoiceQuestion.getQuestion()));
                
        List<MultipleChoiceQuestion> multipleChoiceQuestions = getMongoTemplete().find(query, MultipleChoiceQuestion.class, getSubject());
        
        MultipleChoiceQuestion insertedMultipleChoiceQuestion = null;
        
        if(multipleChoiceQuestions != null && multipleChoiceQuestions.size() >= 1)
        {
            logger.error("Unable to create. Multiple choice questions \"{}\" already exists.", multipleChoiceQuestions);
            
            throw new DuplicateKeyException("Unable to create. Question \"" + 
                    multipleChoiceQuestions + "\" already exists.");            
        }
        else
        {
            insertedMultipleChoiceQuestion = this.getMongoTemplete().insert(multipleChoiceQuestion, getSubject());
            logger.info("Inserted question: {}", insertedMultipleChoiceQuestion);
        }        
        
        return insertedMultipleChoiceQuestion;
    }

    @Override
    public List<MultipleChoiceQuestion> getAllQuestions()
    {   
        List<MultipleChoiceQuestion> multipleChoiceQuestions = this.getMongoTemplete().findAll(MultipleChoiceQuestion.class, getSubject());
        
        logger.info("All questions: {}", multipleChoiceQuestions);
        
        return multipleChoiceQuestions;
    }
    
    @Override
    public MultipleChoiceQuestion findQuestionById(String id)
    {
        logger.info("Get question for id: {}", id);
        MultipleChoiceQuestion multipleChoiceQuestion = this.getMongoTemplete().findById(id, MultipleChoiceQuestion.class, getSubject());
        logger.info("Question for id: {} is {}", id, multipleChoiceQuestion);
        return multipleChoiceQuestion;
    }
    
    @Override
    public List<MultipleChoiceQuestion> findQuestionsContainingPhrase(String phrase)
    {
        Query query = new Query(Criteria.where(MultipleChoiceQuestion.QUESTION).regex("*" + phrase + "*", "i"));
        List<MultipleChoiceQuestion> multipleChoiceQuestions = this.getMongoTemplete().find(query, MultipleChoiceQuestion.class, getSubject());
        logger.info("Questions with pattern : {} are {}", phrase, multipleChoiceQuestions);
        return multipleChoiceQuestions;
        
    }
    
    @Override
    public MultipleChoiceQuestion updateQuestion(MultipleChoiceQuestion multipleChoiceQuestion)
    {
        logger.info("Update question : {}", multipleChoiceQuestion);
        Query query = new Query(Criteria.where(MultipleChoiceQuestion.ID).is(multipleChoiceQuestion.getId()));
        this.getMongoTemplete().findAndReplace(query, multipleChoiceQuestion, getSubject());
        
        List<MultipleChoiceQuestion> updatedMultipleChoiceQuestions = getMongoTemplete().find(query, MultipleChoiceQuestion.class, getSubject());
        
        MultipleChoiceQuestion replacedMultipleChoiceQuestion = null;
        
        if(updatedMultipleChoiceQuestions != null && updatedMultipleChoiceQuestions.size() > 0)
        {
            replacedMultipleChoiceQuestion = updatedMultipleChoiceQuestions.get(0);
        }
        
        logger.info("Updated question : {}", replacedMultipleChoiceQuestion);
        return replacedMultipleChoiceQuestion;
    }
    
    @Override
    public boolean deleteAllQuestions()
    {
        logger.info("Delete questions");
        Query query = new Query();
        DeleteResult deleteResult = this.getMongoTemplete().remove(query, getSubject());  
        boolean wasAcknowledged = deleteResult.wasAcknowledged();
        logger.info("Are questions deleted ? : {}", wasAcknowledged);
        return wasAcknowledged;
    }
    
    @Override
    public boolean deleteQuestion(String id)
    {
        logger.info("Delete question : {}", id);
        Query query = new Query(Criteria.where(MultipleChoiceQuestion.ID).is(id));
        
        List<MultipleChoiceQuestion> multipleChoiceQuestions = getMongoTemplete().find(query, MultipleChoiceQuestion.class, getSubject());
        
        
        if(multipleChoiceQuestions != null && multipleChoiceQuestions.size() > 0)
        {
            MultipleChoiceQuestion multipleChoiceQuestion = multipleChoiceQuestions.get(0);
            logger.info("Question to be deleted : {}", multipleChoiceQuestion);
            DeleteResult remove = this.getMongoTemplete().remove(multipleChoiceQuestion, getSubject());
            return remove.wasAcknowledged();
        }
        
        return false;
    }
}
