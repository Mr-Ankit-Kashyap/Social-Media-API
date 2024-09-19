package com.api.exceptions;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ResourceNotFoundExceptionById extends RuntimeException {
	
	
	private static final long serialVersionUID = 1L;

	String resourceName;

	String fieldName;

	long fieldValue;
	

	public ResourceNotFoundExceptionById(String resourceName, String fieldName, long fieldValue) {
		super(String.format("%s not found this %s : %s", resourceName, fieldName, fieldValue));
		this.resourceName = resourceName;
		this.fieldName = fieldName;
		this.fieldValue = fieldValue;
	}

}
