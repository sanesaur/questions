/**
 * 
 */
package org.spl.questions.models;


import java.util.List;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author sane
 *
 */

@Document(collection="multipleChoiceQuestion")
public class MultipleChoiceQuestion 
{	
    @Id
    private String id;
    
    private String question;
    
    private List<Object> choices;
    
    private List<byte[]> questionDiagrams;
        
    private List<byte[]> answerDiagrams;
    
    public static final String ID = "id";
    
    public static final String QUESTION = "question";
    
    public MultipleChoiceQuestion()
    {
    
    }

    public MultipleChoiceQuestion(String id, String subject, String question, List<Object> choices, 
            List<byte[]> questionDiagrams, List<byte[]> answerDiagrams) 
    {
        super();
        this.id = id;
        this.question = question;
        this.choices = choices;
        this.questionDiagrams = questionDiagrams;
        this.answerDiagrams = answerDiagrams;
    }
    
    public String getId() 
    {
        return id;
    }
    
    public void setId(String id) 
    {
        this.id = id;
    }
    
    public String getQuestion() 
    {
        return question;
    }
    
    public void setQuestion(String question) 
    {
        this.question = question;
    }
    
    public List<Object> getChoices() 
    {
    	return choices;
    }
    
    public void setChoices(List<Object> choices) 
    {
        this.choices = choices;
    }
    
    public List<byte[]> getQuestionDiagrams() 
    {
        return this.questionDiagrams;
    }
    
    public void setQuestionDiagrams(List<byte[]> questionDiagrams) 
    {
        this.questionDiagrams = questionDiagrams;
    }
    
    public List<byte[]> getAnswerDiagrams() 
    {
        return this.answerDiagrams;
    }
    
    public void setAnswerDiagrams(List<byte[]> answerDiagrams) 
    {
        this.answerDiagrams = answerDiagrams;
    }
    
    @Override
    public String toString() 
    {
        return String.format("Question[id=%s, question=%s]", 
                this.id, this.question);
    }
}
