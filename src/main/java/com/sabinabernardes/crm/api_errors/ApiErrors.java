package com.sabinabernardes.crm.api_errors;

import java.util.Arrays;
import java.util.List;



public class ApiErrors {

	
	public ApiErrors(List<String> errors) {}
	public ApiErrors(String message) {Arrays.asList(message);}
}
