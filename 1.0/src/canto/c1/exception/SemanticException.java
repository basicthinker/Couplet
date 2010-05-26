package canto.c1.exception;

import canto.CantoException;

@SuppressWarnings("serial")
public class SemanticException extends CantoException {

	public SemanticException(int lineNumber, int columnNumber,
			int exceptionType, int exceptionLevel) {
		
		super(lineNumber, columnNumber, exceptionType, exceptionLevel);
	}
	
	public SemanticException(){
		super(0, 0, 1, 1);
	}

	@Override
	public String getExceptionMsg() {
		// TODO Auto-generated method stub
		return null;
	}

}
