/**
 * 
 */
package org.spl.questions.services;

import java.util.List;

import org.spl.questions.models.MultipleChoiceQuestion;

/**
 * @author sane
 *
 */
public interface MultipleChoiceQuestionDbService
{
    public MultipleChoiceQuestion create(MultipleChoiceQuestion multipleChoiceQuestion);
    
    public List<MultipleChoiceQuestion> getAllQuestions();
    
    public MultipleChoiceQuestion findQuestionById(String id);
    
    public List<MultipleChoiceQuestion> findQuestionsContainingPhrase(String phrase);
    
    public MultipleChoiceQuestion updateQuestion(MultipleChoiceQuestion multipleChoiceQuestion);
    
    public boolean deleteAllQuestions();
    
    public boolean deleteQuestion(String id);
    
    public void setSubject(String subject) throws Exception;
}
