package canto.c1.exception;

import canto.exception.CantoException;
import canto.exception.CantoExceptionLevel;
import canto.exception.CantoExceptionType;

@SuppressWarnings("serial")
public class SemanticException extends CantoException {

	public SemanticException(int lineNumber, int columnNumber,
			CantoExceptionType exceptionType, String exceptionMsg, CantoExceptionLevel exceptionLevel) {
		
		super(lineNumber, columnNumber, exceptionType, exceptionMsg, exceptionLevel);
	}
	
	public SemanticException(){
		super(0, 0, null, null, null);
	}

}
