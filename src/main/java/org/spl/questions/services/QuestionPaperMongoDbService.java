package org.spl.questions.services;

import java.util.ArrayList;
import java.util.List;
import org.spl.questions.models.MultipleChoiceQuestion;
import org.spl.questions.models.QuestionPaper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;


public class QuestionPaperMongoDbService extends AbstractMongoDbService
                                implements QuestionPaperDbService
{

    static final private Logger logger = LoggerFactory.getLogger(QuestionPaperMongoDbService.class);

    private static final String COLLECTION_PREFIX = "qp_";
    
    @Override
    public void setSubject(String subject) throws Exception
    {
        super.setSubject(subject, COLLECTION_PREFIX);
    }
    
    @Override
    public QuestionPaper create(QuestionPaper questionPaper)
    {
        logger.info("Subject : {}", getSubject());
        logger.info("Question : {}", questionPaper.getSetId() , questionPaper.getCalendarYear(), questionPaper.getAcademicYear(), questionPaper.getSemester());
        
        
        Criteria criteria = Criteria.where(QuestionPaper.SET_ID).is(questionPaper.getSetId())
                        .and(QuestionPaper.CALENDAR_YEAR).is(questionPaper.getCalendarYear())
                        .and(QuestionPaper.ACADEMIC_YEAR).is(questionPaper.getAcademicYear())
                        .and(QuestionPaper.SEMESTER).is(questionPaper.getSemester());
        
        Query query = new Query(criteria);
                
        List<QuestionPaper> questionPapers = getMongoTemplete().find(query, QuestionPaper.class, getSubject());
        
        QuestionPaper insertedQuestionPaper = null;
        
        if(questionPapers != null && questionPapers.size() >= 1)
        {
            logger.error("Unable to create. Question paper \"{}\" already exists.", questionPapers);
            
            throw new DuplicateKeyException("Unable to create. Question \"" + 
                    questionPapers + "\" already exists.");            
        }
        else
        {
            insertedQuestionPaper = this.getMongoTemplete().insert(questionPaper, getSubject());
            logger.info("Inserted question: {}", insertedQuestionPaper);
        }        
        
        return insertedQuestionPaper;
    }

    @Override
    public List<QuestionPaper> findQuestionPaperById(String id)
    {
        logger.info("Subject : {}", getSubject());
        logger.info("Question paper for : {} ",  id);
        
        Criteria criteria = Criteria.where(QuestionPaper.SET_ID).is(id);
        
        Query query = new Query(criteria);
        
        List<QuestionPaper> questionPapers = getMongoTemplete().find(query, QuestionPaper.class, getSubject());
        
        logger.info("Question papers for : {} is {}", id, questionPapers);
        
        return questionPapers;
    }
    
    @Override
    public List<QuestionPaper> findQuestionPapers(QuestionPaper questionPaper)
    {
        Integer setId = questionPaper.getSetId();
        Integer calendarYear = questionPaper.getCalendarYear();
        Integer academicYear = questionPaper.getAcademicYear();
        Integer semester = questionPaper.getSemester();
        
        logger.info("Subject : {}", getSubject());
        logger.info("Question paper for : {} {} {} {}",  setId, calendarYear, 
                academicYear, semester);
        
        
        Criteria criteria = Criteria.where(QuestionPaper.SET_ID).is(setId)
                        .and(QuestionPaper.CALENDAR_YEAR).is(calendarYear)
                        .and(QuestionPaper.ACADEMIC_YEAR).is(academicYear)
                        .and(QuestionPaper.SEMESTER).is(semester);
        
        Query query = new Query(criteria);
        
        List<QuestionPaper> questionPapers = getMongoTemplete().find(query, QuestionPaper.class, getSubject());
        
        logger.info("Question papers for : {} {} {} {} is {}", setId, calendarYear, academicYear, semester, questionPapers);
        
        return questionPapers;
    }

    @Override
    public List<MultipleChoiceQuestion> findAllMultipleChoiceQuestions(String id)
    {
        logger.info("Subject : {}", getSubject());
        
        List<QuestionPaper> questionPapers = findQuestionPaperById(id);
        
        List<MultipleChoiceQuestion> multipleChoiceQuestions = new ArrayList<MultipleChoiceQuestion>();
        
        for(QuestionPaper questionPaper : questionPapers)
        {
            List<MultipleChoiceQuestion> multipleChoiceQuestionsInQuestionPaper = questionPaper.getMultipleChoiceQuestions();
            multipleChoiceQuestions.addAll(multipleChoiceQuestions);
        }
        
        logger.info("Multiple choice questions for {} are {}",  id, multipleChoiceQuestions);
        
        return multipleChoiceQuestions;
    }

    @Override
    public List<QuestionPaper> findAllQuestionPapers()
    {
        logger.info("Find all question papers for {}", getSubject());
        return getMongoTemplete().findAll(QuestionPaper.class, getSubject());
    }
    
    @Override
    public QuestionPaper updateQuestionPaper(QuestionPaper questionPaper)
    {
        logger.info("Update question paper: {}", questionPaper);
        Query query = new Query(Criteria.where(QuestionPaper.ID).is(questionPaper.getId()));
        this.getMongoTemplete().findAndReplace(query, questionPaper, getSubject());
        
        List<QuestionPaper> updatedQuestionPapers = getMongoTemplete().find(query, QuestionPaper.class, getSubject());
        
        QuestionPaper replacedQuestionPaper = null;
        
        if(updatedQuestionPapers != null && updatedQuestionPapers.size() > 0)
        {
            replacedQuestionPaper = updatedQuestionPapers.get(0);
        }
        
        logger.info("Updated question paper : {}", replacedQuestionPaper);
        return replacedQuestionPaper;
    }

    @Override
    public QuestionPaper addMultipleChoiceQuestion(String id, MultipleChoiceQuestion multipleChoiceQuestion)
    {
        logger.info("Add multiple choice question {} in question paper: {}", multipleChoiceQuestion, id);
        Query query = new Query(Criteria.where(QuestionPaper.ID).is(id));
        
        Update update = new Update();
        update.push(QuestionPaper.MULTIPLE_CHOICE_QUESTIONS, multipleChoiceQuestion);
        
        UpdateResult updateMulti = this.getMongoTemplete().updateMulti(query, update, getSubject());
        
        QuestionPaper modifiedQuestionPaper = null;
        
        List<QuestionPaper> modifiedQuestionPapers = findQuestionPaperById(id);
        
        if(modifiedQuestionPapers != null && modifiedQuestionPapers.size() == 1)
        {
            modifiedQuestionPaper = modifiedQuestionPapers.get(0);
        }
        
        logger.info("{} question papers updated.",modifiedQuestionPaper);

        return modifiedQuestionPaper;
    }
    
    @Override
    public QuestionPaper removeMultipleChoiceQuestion(String id, MultipleChoiceQuestion multipleChoiceQuestion)
    {
        String multipleChoiceQuestionId = multipleChoiceQuestion.getId();

        Query query = new Query(Criteria.where(QuestionPaper.MCQ_ID).is(multipleChoiceQuestionId)
                .andOperator(Criteria.where(QuestionPaper.ID).is(id)));
        DeleteResult remove = this.getMongoTemplete().remove(query, MultipleChoiceQuestion.class, getSubject());
        
        logger.info("Is question removed ? {}", remove.wasAcknowledged());
        
        QuestionPaper modifiedQuestionPaper = null;
        List<QuestionPaper> modifiedQuestionPapers = findQuestionPaperById(id);
        
        if(modifiedQuestionPapers != null && modifiedQuestionPapers.size() == 1)
        {
            modifiedQuestionPaper = modifiedQuestionPapers.get(0);
        }
        
        logger.info("{} question papers updated.",modifiedQuestionPaper);

        return modifiedQuestionPaper;
    }
    

    @Override
    public boolean deleteAllQuestionPapers()
    {
        logger.info("Delete questions");
        this.getMongoTemplete().dropCollection(getSubject());
        return true;
    }

    @Override
    public boolean deleteQuestionPaper(String id)
    {
        logger.info("Delete question paper : {}", id);
        Query query = new Query(Criteria.where(QuestionPaper.ID).is(id));
        
        List<QuestionPaper> questionPapers = getMongoTemplete().find(query, QuestionPaper.class, getSubject());
        
        if(questionPapers != null && questionPapers.size() > 0)
        {
            QuestionPaper questionPaper = questionPapers.get(0);
            logger.info("Question paper to be deleted : {}", questionPaper);
            DeleteResult remove = this.getMongoTemplete().remove(questionPaper, getSubject());
            return remove.wasAcknowledged();
        }
        
        return false;
    }

    @Override
    public List<QuestionPaper> findQuestionPapersContainingQuestionsContainingPhrase(String phrase)
    {
        Query query = new Query(Criteria.where(QuestionPaper.MCQ_QUESTION).regex("*" + phrase + "*", "i"));
        List<QuestionPaper> questionPapers = this.getMongoTemplete().find(query, QuestionPaper.class, getSubject());
        logger.info("Question papers with questions having pattern : {} are {}", phrase, questionPapers);
        return questionPapers;
    }
}
