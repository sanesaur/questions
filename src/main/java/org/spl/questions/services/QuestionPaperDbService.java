package org.spl.questions.services;

import java.util.List;

import org.spl.questions.models.MultipleChoiceQuestion;
import org.spl.questions.models.QuestionPaper;

public interface QuestionPaperDbService
{
    public QuestionPaper create(QuestionPaper questionPaper);
    
    public List<QuestionPaper> findAllQuestionPapers();
    
    public List<QuestionPaper> findQuestionPaperById(String id);
    
    public List<QuestionPaper> findQuestionPapersContainingQuestionsContainingPhrase(String phrase);
    
    public List<QuestionPaper> findQuestionPapers(QuestionPaper questionPaper);
    
    public QuestionPaper updateQuestionPaper(QuestionPaper questionPaper);
    
    public boolean deleteAllQuestionPapers();
    
    public boolean deleteQuestionPaper(String id);
    
    public QuestionPaper addMultipleChoiceQuestion(String id, MultipleChoiceQuestion multipleChoiceQuestion);
    
    public QuestionPaper removeMultipleChoiceQuestion(String id, MultipleChoiceQuestion multipleChoiceQuestion);
    
    public List<MultipleChoiceQuestion> findAllMultipleChoiceQuestions(String id);


    
    public void setSubject(String subject) throws Exception;
}
