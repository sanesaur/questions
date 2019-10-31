/**
 * 
 */
package org.spl.questions.delegates;

import java.util.List;

import org.spl.questions.models.MultipleChoiceQuestion;
import org.spl.questions.services.MultiChoiceQuestionDbServiceLookUp;
import org.spl.questions.services.MultipleChoiceQuestionDbService;

/**
 * @author sane
 *
 */
public class MultipleChoiceQuestionDelegate
{
    private MultipleChoiceQuestionDbService multipleChoiceQuestionDbService;
    
    public MultipleChoiceQuestionDelegate(String subject) throws Exception
    {
        this.multipleChoiceQuestionDbService = MultiChoiceQuestionDbServiceLookUp.getMultiChoiceQuestionDbService();
        this.multipleChoiceQuestionDbService.setSubject(subject);
    }
    
    public MultipleChoiceQuestion create(MultipleChoiceQuestion multipleChoiceQuestion)
    {
        return this.multipleChoiceQuestionDbService.create(multipleChoiceQuestion);
    }
    
    public List<MultipleChoiceQuestion> getAllQuestions()
    {
        return this.multipleChoiceQuestionDbService.getAllQuestions();
    }
    
    public MultipleChoiceQuestion getQuestionById(String id)
    {
        return this.multipleChoiceQuestionDbService.findQuestionById(id);
    }
    
    public List<MultipleChoiceQuestion> getQuestionsContainingPhrase(String phrase)
    {
        return this.multipleChoiceQuestionDbService.findQuestionsContainingPhrase(phrase);
    }
    
    public MultipleChoiceQuestion updateQuestion(MultipleChoiceQuestion multipleChoiceQuestion)
    {
        return this.multipleChoiceQuestionDbService.updateQuestion(multipleChoiceQuestion);
    }
    
    public boolean deleteAllQuestions()
    {
        return this.multipleChoiceQuestionDbService.deleteAllQuestions();
    }
    
    public boolean deleteQuestion(String id)
    {
        return this.multipleChoiceQuestionDbService.deleteQuestion(id);
    }
}
