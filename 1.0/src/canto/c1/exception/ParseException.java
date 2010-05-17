package canto.c1.exception;

import canto.exception.*;

@SuppressWarnings("serial")
public class ParseException extends CantoException {

	public ParseException(int lineNumber, int columnNumber,
			CantoExceptionType exceptionType, String exceptionMsg, CantoExceptionLevel exceptionLevel) {
		
		super(lineNumber, columnNumber, exceptionType, exceptionMsg, exceptionLevel);
	}
	
	public ParseException(){
		//TODO exceptionMsg需要从XML文件读取
		super(0, 0, CantoExceptionType.TestType , "ReadFromXML", CantoExceptionLevel.error);
	}

}