package com.api.exceptions;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ResourceNotFoundExceptionByUsername extends RuntimeException{
	
	
	private static final long serialVersionUID = 1L;

	String resourceName;

	String fieldName;

	String fieldValue;
	

	public ResourceNotFoundExceptionByUsername(String resourceName, String fieldName, String fieldValue) {
		super(String.format("%s not found this %s : %s", resourceName, fieldName, fieldValue));
		this.resourceName = resourceName;
		this.fieldName = fieldName;
		this.fieldValue = fieldValue;
	}
	
	
	

}
