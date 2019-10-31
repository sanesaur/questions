package org.spl.questions.models;

import java.util.List;

import org.springframework.data.annotation.Id;

public class QuestionPaper 
{
	@Id
	public String id;
	
	private Integer setId;
	
	private Integer calendarYear;
	
	private Integer academicYear;
	
	private Integer semester;

	private List<MultipleChoiceQuestion> multipleChoiceQuestions;
	
	public static final String ID = "id";
	
	public static final String SET_ID = "setId";
	
	public static final String CALENDAR_YEAR = "calendarYear";
	
	public static final String ACADEMIC_YEAR = "academicYear";
	
	public static final String SEMESTER = "semester";
	
	public static final String MULTIPLE_CHOICE_QUESTIONS = "multipleChoiceQuestions";
	
	public static final String MCQ_QUESTION = MULTIPLE_CHOICE_QUESTIONS + "." + MultipleChoiceQuestion.QUESTION;
	
	public static final String MCQ_ID = MULTIPLE_CHOICE_QUESTIONS + "." + MultipleChoiceQuestion.ID;

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public Integer getSetId()
	{
		return setId;
	}

	public void setSetId(Integer setId)
	{
		this.setId = setId;
	}

	public Integer getCalendarYear()
	{
		return calendarYear;
	}

	public void setCalendarYear(Integer calendarYear)
	{
		this.calendarYear = calendarYear;
	}

	public Integer getAcademicYear()
	{
		return academicYear;
	}

	public void setAcademicYear(Integer academicYear)
	{
		this.academicYear = academicYear;
	}

	public Integer getSemester()
	{
		return semester;
	}

	public void setSemester(Integer semester)
	{
		this.semester = semester;
	}

	public List<MultipleChoiceQuestion> getMultipleChoiceQuestions()
	{
		return multipleChoiceQuestions;
	}

	public void setMultipleChoiceQuestions(List<MultipleChoiceQuestion> multipleChoiceQuestions)
	{
		this.multipleChoiceQuestions = multipleChoiceQuestions;
	}
}
