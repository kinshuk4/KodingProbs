package com.vaani.gojek.parking.exceptions;

/**
 * Checked Custom Exception to be thrown by the API
 * Please note that I have introduced only one Checked Exception type in the code .
 * I have deliberately avoided introducing multiple exceptions  as mostly all the exceptions are for 
 * Validations . Introducing multiple exceptions lead to developers catching all exceptions which is a 
 * poor development Practice.
 * i.e. catch(Exception ex){
 * }
 * To restrict ppl who consume these API's in doing so and due to similar nature of all exceptions,
 * I have gone ahead with Single Exception. Though there are some merits to introduce multiple exceptions as well
 * for this , I have gone with above mentioned thought process.
 * @author kchandra
 *
 */
public class GoJekException extends Exception {

	
	private static final long serialVersionUID = 8192083372488142312L;

	public GoJekException(){

	}

	public GoJekException(String message){
		super(message);
	}

	public GoJekException(Throwable cause){
		super(cause);
	}

	public GoJekException(String message, Throwable cause){
		super(message, cause);
	}

}
