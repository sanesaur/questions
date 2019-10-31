/**
 * 
 */
package org.spl.questions.controllers;

import java.io.IOException;
import java.util.List;

import org.spl.questions.delegates.MultipleChoiceQuestionDelegate;
import org.spl.questions.delegates.QuestionPaperDelegate;
import org.spl.questions.models.MultipleChoiceQuestion;
import org.spl.questions.models.QuestionPaper;
import org.spl.questions.util.CustomError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author sane
 *
 */
public class QuestionPaperController
{
    private static final Logger logger = LoggerFactory.getLogger(QuestionPaperController.class);
    
    @RequestMapping(value = "/qps", method = RequestMethod.POST)
    private ResponseEntity<?> create(
                                    @RequestParam("subject") String subject,
                                    @RequestParam("setId") Integer setId,
                                    @RequestParam("calendarYear") Integer calendarYear,
                                    @RequestParam("academicYear") Integer academicYear,
                                    @RequestParam("semester") Integer semester
                                ) throws IllegalStateException, IOException, Exception
    {
        
        if(setId != null  && calendarYear != null && academicYear != null && semester != null)
        {
            logger.info("Subject : {}", subject);
            
            QuestionPaper questionPaper = new QuestionPaper();
            questionPaper.setSetId(setId);
            questionPaper.setCalendarYear(calendarYear);
            questionPaper.setAcademicYear(academicYear);
            questionPaper.setSemester(semester);
            
            QuestionPaperDelegate questionPaperDelegate = new QuestionPaperDelegate(subject);

            QuestionPaper createdQuestionPaper = questionPaperDelegate.create(questionPaper);
            logger.info("Created question paper: {}", createdQuestionPaper);
            return new ResponseEntity(createdQuestionPaper, HttpStatus.CREATED);
        }
        else
        {
            logger.error("Unable to create. Set all the required fields.");
            CustomError customError = new CustomError();
            customError.addErrorMessage("Unable to create. Set all the required fields.");
            return new ResponseEntity(customError.getErrorMessages(), HttpStatus.BAD_REQUEST);
        } 
    }
    
    @RequestMapping(value = "/qps/{subject}", method = RequestMethod.GET)
    private ResponseEntity<?> listQuestionpapers(@PathVariable("subject") String subject) throws Exception
    {
        logger.info("Fetching all multiple choice questions."); 
        QuestionPaperDelegate questionPaperDelegate = new QuestionPaperDelegate(subject);
        List<QuestionPaper> allQuestionPapers = questionPaperDelegate.getAllQuestionPapers();
        logger.info("All questions for {} : {}", subject, allQuestionPapers);
        return new ResponseEntity<List<QuestionPaper>>(allQuestionPapers, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/qps/{subject}/{id}", method = RequestMethod.GET )
    private ResponseEntity<?> searchQuestionPaperById(@PathVariable("subject") String subject, 
                                                    @PathVariable("id") String id)  throws Exception
    {
        logger.info("Fetching question paper of {} with id {}.", subject, id);
    
        QuestionPaperDelegate questionPaperDelegate = new QuestionPaperDelegate(subject);
        List<QuestionPaper> questionPapers = questionPaperDelegate.getQuestionPaperById(id);
    
        if(questionPapers == null)
        {
            logger.error("Question paper with id {} is not found.", id);
            CustomError customError = new CustomError();
            customError.addErrorMessage("Question paper with id " + id + " is not found.");
            return new ResponseEntity(customError.getErrorMessages(), HttpStatus.NOT_FOUND);
        }
        else
        {
            logger.info("Question with id {} is : {}", id, questionPapers);
            return new ResponseEntity<List<QuestionPaper>>(questionPapers, HttpStatus.OK);
        }
    }
    
    @RequestMapping(value = "/qps/{subject}/question", params = "phrase", method = RequestMethod.GET )
    private ResponseEntity<?> searchQuestionPapersContainingQuestionsContainingPhrase(@PathVariable("subject") String subject,
                                                @RequestParam("phrase") String phrase)  throws Exception
    {
        logger.info("Fetching question papers {} having phrase {} in questions .", subject, phrase);
        QuestionPaperDelegate questionPaperDelegate = new QuestionPaperDelegate(subject);
        List<QuestionPaper> questionPapersContainingPhrase = questionPaperDelegate.getQuestionPapersContainingQuestionsContainingPhrase(phrase);
        logger.info("Questions for {} having phrase {} are: ", subject, phrase);
        return new ResponseEntity<List<QuestionPaper>>(questionPapersContainingPhrase, HttpStatus.OK);
    }
    
    @RequestMapping (value = "/qps/{subject}/{id}", method = RequestMethod.PUT)
    private ResponseEntity<?> updateQuestionPaper(@PathVariable("subject") String subject, 
                            @PathVariable("id") String id , 
                            @RequestParam("setId") Integer setId,
                            @RequestParam("calendarYear") Integer calendarYear,
                            @RequestParam("academicYear") Integer academicYear,
                            @RequestParam("semester") Integer semester) throws IOException, Exception
    {
        logger.info("Updating question paper of {} with id {}.", subject, id);
        QuestionPaper questionPaper = getQuestionPaper(setId, calendarYear, academicYear, semester);
        QuestionPaperDelegate questionPaperDelegate = new QuestionPaperDelegate(subject);
        QuestionPaper updatedQuestionPaper = questionPaperDelegate.updateQuestion(questionPaper);
        logger.info("Updated multiple choice questions : {}",updatedQuestionPaper);
        return new ResponseEntity<QuestionPaper>(updatedQuestionPaper, HttpStatus.OK);
    } 
    
    @RequestMapping(value = "/qps/{subject}", method = RequestMethod.DELETE)
    private ResponseEntity<?> deleteAllQuestionPapers(@PathVariable("subject") String subject) throws Exception
    {
        logger.info("Deleting all multiple choice questions.");
        QuestionPaperDelegate questionPaperDelegate = new QuestionPaperDelegate(subject);
        boolean areAllQuestionPapersDeleted = questionPaperDelegate.deleteAllQuestionPapers();
        logger.info("Are all questions deleted ? : {}",areAllQuestionPapersDeleted);
        return new ResponseEntity<QuestionPaper>(HttpStatus.NO_CONTENT);
    }
    
    @RequestMapping(value = "/mcqs/{subject}/{id}", method = RequestMethod.DELETE)
    private ResponseEntity<?> deleteQuestionPaper(@PathVariable("subject") String subject, @PathVariable("id") String id)
            throws Exception
    {   
        logger.info("Deleting multiple choice question for {} with id {}.", subject, id);
        QuestionPaperDelegate questionPaperDelegate = new QuestionPaperDelegate(subject);
        boolean isQuestionpaperDeleted = questionPaperDelegate.deleteQuestionPaper(id);
        logger.info("Is question deleted ? : {}",isQuestionpaperDeleted);
        return new ResponseEntity<QuestionPaper>(HttpStatus.NO_CONTENT);
    }
    
    
    private QuestionPaper getQuestionPaper(Integer setId, Integer calendarYear, Integer academicYear, Integer semester ) 
    {
        QuestionPaper questionPaper = new QuestionPaper();
        questionPaper.setSetId(setId);
        questionPaper.setCalendarYear(calendarYear);
        questionPaper.setAcademicYear(academicYear);
        questionPaper.setSemester(semester);
        return questionPaper;
    }     
}
