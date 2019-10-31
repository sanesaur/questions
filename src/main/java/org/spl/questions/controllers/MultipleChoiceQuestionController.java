/**
 * 
 */
package org.spl.questions.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


import org.spl.questions.delegates.MultipleChoiceQuestionDelegate;
import org.spl.questions.models.MultipleChoiceQuestion;
import org.spl.questions.util.CustomError;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author sane
 *
 */

@RestController
public class MultipleChoiceQuestionController 
{
    private static final Logger logger = LoggerFactory.getLogger(MultipleChoiceQuestionController.class);
    
    
    @RequestMapping(value = "/mcqs", method = RequestMethod.POST)
    private ResponseEntity<?> create(
                                    @RequestParam("subject") String subject,
                                    @RequestParam("question") String question,
                                    @RequestParam("questionDiagrams") MultipartFile[] questionDiagrams,
                                    @RequestParam("answerDiagrams") MultipartFile[] answerDiagrams,
                                    @RequestParam("choices") List<Object> choices
                                ) throws IllegalStateException, IOException, Exception
    {
        
        if(subject != null && subject.length() > 0 
                && question != null && question.length() > 0
                && ((choices != null && choices.size() > 0) 
                        || answerDiagrams != null && answerDiagrams.length > 0))
        {
            logger.info("Subject : {}", subject);
            MultipleChoiceQuestionDelegate multiChoiceQuestionDelegate = new MultipleChoiceQuestionDelegate(subject);            
            MultipleChoiceQuestion multipleChoiceQuestion = getMultipleChoiceQuestion(question, choices, questionDiagrams, answerDiagrams);            
            MultipleChoiceQuestion createdMultipleChoiceQuestion = multiChoiceQuestionDelegate.create(multipleChoiceQuestion);
            logger.info("Created question: {}", createdMultipleChoiceQuestion);
            return new ResponseEntity(createdMultipleChoiceQuestion, HttpStatus.CREATED);
        }
        else
        {
            logger.error("Unable to create. Set all the required fields.");
            CustomError customError = new CustomError();
            customError.addErrorMessage("Unable to create. Set all the required fields.");
            return new ResponseEntity(customError.getErrorMessages(), HttpStatus.BAD_REQUEST);
        } 
    }



    @RequestMapping(value = "/mcqs/{subject}", method = RequestMethod.GET)
    private ResponseEntity<?> listQuestions(@PathVariable("subject") String subject) throws Exception
    {
        logger.info("Fetching all multiple choice questions.");	
        MultipleChoiceQuestionDelegate multiChoiceQuestionDelegate = new MultipleChoiceQuestionDelegate(subject);
        List<MultipleChoiceQuestion> allQuestions = multiChoiceQuestionDelegate.getAllQuestions();
        logger.info("All questions for {} : {}", subject, allQuestions);
        return new ResponseEntity<List<MultipleChoiceQuestion>>(allQuestions, HttpStatus.OK);
    }

    @RequestMapping(value = "/mcqs/{subject}/{id}", method = RequestMethod.GET )
    private ResponseEntity<?> searchQuestionById(@PathVariable("subject") String subject, 
                                                    @PathVariable("id") String id)  throws Exception
    {
    	logger.info("Fetching multiple choice questions of {} with id {}.", subject, id);
    
        MultipleChoiceQuestionDelegate multiChoiceQuestionDelegate = new MultipleChoiceQuestionDelegate(subject);
        MultipleChoiceQuestion multipleChoiceQuestion = multiChoiceQuestionDelegate.getQuestionById(id);
    
        if(multipleChoiceQuestion == null)
        {
            logger.error("Question with id {} is not found.", id);
            CustomError customError = new CustomError();
            customError.addErrorMessage("Multiple choice questions with id " + id + " is not found.");
            return new ResponseEntity(customError.getErrorMessages(), HttpStatus.NOT_FOUND);
        }
        else
        {
            logger.info("Question with id {} is : {}", id, multipleChoiceQuestion);
            return new ResponseEntity<MultipleChoiceQuestion>(multipleChoiceQuestion, HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/mcqs/{subject}/question", params = "phrase", method = RequestMethod.GET )
    private ResponseEntity<?> searchQuestionsContainingPhrase(@PathVariable("subject") String subject,
                                                @RequestParam("phrase") String phrase)  throws Exception
    {
        logger.info("Fetching multiple choice questions of {} having phrase id {}.", subject, phrase);
        MultipleChoiceQuestionDelegate multiChoiceQuestionDelegate = new MultipleChoiceQuestionDelegate(subject);
        List<MultipleChoiceQuestion> questionsContainingPhrase = multiChoiceQuestionDelegate.getQuestionsContainingPhrase(phrase);
        logger.info("Questions for {} having phrase {} are: ", subject, phrase);
        return new ResponseEntity<List<MultipleChoiceQuestion>>(questionsContainingPhrase, HttpStatus.OK);
    }
    
    @RequestMapping (value = "/mcqs/{subject}/{id}", method = RequestMethod.PUT)
    private ResponseEntity<?> updateQuestion(@PathVariable("subject") String subject, 
                                                @PathVariable("id") String id , 
                                                @RequestParam("question") String question,
                                                @RequestParam("questionDiagrams") MultipartFile[] questionDiagrams,
                                                @RequestParam("answerDiagrams") MultipartFile[] answerDiagrams,                                                
                                                @RequestParam("choices") List<Object> choices) throws IOException, Exception
    {
        logger.info("Updating multiple choice questions of {} with id {}.", subject, id);
        MultipleChoiceQuestion multipleChoiceQuestion = getMultipleChoiceQuestion(question, choices, answerDiagrams, answerDiagrams);
        MultipleChoiceQuestionDelegate multiChoiceQuestionDelegate = new MultipleChoiceQuestionDelegate(subject);
        MultipleChoiceQuestion updatedMultipleChoiceQuestion = multiChoiceQuestionDelegate.updateQuestion(multipleChoiceQuestion);
        logger.info("Updated multiple choice questions : {}",updatedMultipleChoiceQuestion);
        return new ResponseEntity<MultipleChoiceQuestion>(updatedMultipleChoiceQuestion, HttpStatus.OK);
    }

    @RequestMapping(value = "/mcqs/{subject}", method = RequestMethod.DELETE)
    private ResponseEntity<?> deleteQuestions(@PathVariable("subject") String subject) throws Exception
    {
        logger.info("Deleting all multiple choice questions.");
        MultipleChoiceQuestionDelegate multiChoiceQuestionDelegate = new MultipleChoiceQuestionDelegate(subject);
        boolean areAllQuestionsDeleted = multiChoiceQuestionDelegate.deleteAllQuestions();
        logger.info("Are all questions deleted ? : {}",areAllQuestionsDeleted);
        return new ResponseEntity<MultipleChoiceQuestion>(HttpStatus.NO_CONTENT);
    }
    
    @RequestMapping(value = "/mcqs/{subject}/{id}", method = RequestMethod.DELETE)
    private ResponseEntity<?> deleteQuestion(@PathVariable("subject") String subject, @PathVariable("id") String id)
            throws Exception
    {	
        logger.info("Deleting multiple choice question for {} with id {}.", subject, id);
        MultipleChoiceQuestionDelegate multiChoiceQuestionDelegate = new MultipleChoiceQuestionDelegate(subject);
        boolean isQuestionDeleted = multiChoiceQuestionDelegate.deleteQuestion(id);
        logger.info("Is question deleted ? : {}",isQuestionDeleted);
        return new ResponseEntity<MultipleChoiceQuestion>(HttpStatus.NO_CONTENT);
    }
    

    
    
    private List<byte[]> convertMultiPartFilesToByteArray(MultipartFile[] diagrams)
            throws IOException
    {
        List<byte[]> diagramFiles = new ArrayList<byte[]>();
        
        if(diagrams != null && diagrams.length > 0)
        {   
            for(MultipartFile diagram: diagrams)
            {
                if(diagram != null)
                {
                    diagramFiles.add(diagram.getBytes()); 
                }
            }
        }
        return diagramFiles;
    }
    
    private MultipleChoiceQuestion getMultipleChoiceQuestion(String question, List<Object> choices,
            MultipartFile[] questionDiagrams, MultipartFile[] answerDiagrams ) throws IOException
    {
        MultipleChoiceQuestion multipleChoiceQuestion = new MultipleChoiceQuestion();
        multipleChoiceQuestion.setQuestion(question);
        multipleChoiceQuestion.setChoices(choices);
        
        List<byte[]> questionDiagramsFiles = convertMultiPartFilesToByteArray(questionDiagrams);
        
        if(questionDiagramsFiles != null && questionDiagramsFiles.size() > 0)
        {
            multipleChoiceQuestion.setQuestionDiagrams(questionDiagramsFiles);
        }
        
        List<byte[]> answerDiagramsFiles = convertMultiPartFilesToByteArray(answerDiagrams);
        
        if(answerDiagramsFiles != null && answerDiagramsFiles.size() > 0)
        {
            multipleChoiceQuestion.setAnswerDiagrams(answerDiagramsFiles);
        }        
        
        return multipleChoiceQuestion;
    }    
}
