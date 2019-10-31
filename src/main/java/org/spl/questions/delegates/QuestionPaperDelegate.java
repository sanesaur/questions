/**
 * 
 */
package org.spl.questions.delegates;

import java.util.List;

import org.spl.questions.models.MultipleChoiceQuestion;
import org.spl.questions.models.QuestionPaper;
import org.spl.questions.services.MultiChoiceQuestionDbServiceLookUp;
import org.spl.questions.services.MultipleChoiceQuestionDbService;
import org.spl.questions.services.QuestionPaperDbService;
import org.spl.questions.services.QuestionPaperDbServiceLookUp;

/**
 * @author sane
 *
 */
public class QuestionPaperDelegate
{
    private QuestionPaperDbService questionPaperDbService;
    
    public QuestionPaperDelegate(String subject) throws Exception
    {
        this.questionPaperDbService = QuestionPaperDbServiceLookUp.getQuestionPaperDbService();
        this.questionPaperDbService.setSubject(subject);
    }
 
    public QuestionPaper create(QuestionPaper questionPaper)
    {
        return this.questionPaperDbService.create(questionPaper);
    }
    
    public List<QuestionPaper> getAllQuestionPapers()
    {
        return this.questionPaperDbService.findAllQuestionPapers();
    }
    
    public List<QuestionPaper> getQuestionPaperById(String id)
    {
        return this.questionPaperDbService.findQuestionPaperById(id);
    }
    
    public List<QuestionPaper> getQuestionPapersContainingQuestionsContainingPhrase(String phrase)
    {
        return this.questionPaperDbService.findQuestionPapersContainingQuestionsContainingPhrase(phrase);
    }
    
    public List<QuestionPaper> getQuestionPapers(QuestionPaper questionPaper)
    {
        return this.questionPaperDbService.findQuestionPapers(questionPaper);
    }
    
    public QuestionPaper updateQuestion(QuestionPaper questionPaper)
    {
        return this.questionPaperDbService.updateQuestionPaper(questionPaper);
    }
    
    public boolean deleteAllQuestionPapers()
    {
        return this.questionPaperDbService.deleteAllQuestionPapers();
    }
    
    public boolean deleteQuestionPaper(String id)
    {
        return this.questionPaperDbService.deleteQuestionPaper(id);
    }
    
    public QuestionPaper addMultipleChoiceQuestion(String id, MultipleChoiceQuestion multipleChoiceQuestion)
    {
        return this.questionPaperDbService.addMultipleChoiceQuestion(id, multipleChoiceQuestion);
    }
    
    public QuestionPaper removeMultipleChoiceQuestion(String id, MultipleChoiceQuestion multipleChoiceQuestion)
    {
        return this.questionPaperDbService.removeMultipleChoiceQuestion(id, multipleChoiceQuestion);
    }
    
    public List<MultipleChoiceQuestion> getAllMultipleChoiceQuestions(String id)
    {
        return this.questionPaperDbService.findAllMultipleChoiceQuestions(id);
    }
}
