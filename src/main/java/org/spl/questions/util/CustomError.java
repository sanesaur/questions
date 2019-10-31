package org.spl.questions.util;

import java.util.ArrayList;
import java.util.List;

public class CustomError 
{
	private List<String> errorMessages;

	public CustomError() 
	{
		this.errorMessages = new ArrayList<String>();
	}

	public List<String> getErrorMessages() 
	{
		return this.errorMessages;
	}
	
	public void addErrorMessage(String errorMessage)
	{
		this.errorMessages.add(errorMessage);
	}
}
