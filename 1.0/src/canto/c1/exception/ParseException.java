package canto.c1.exception;

import canto.CantoException;
import canto.exception.*;

@SuppressWarnings("serial")
public class ParseException extends CantoException {

	public ParseException(int lineNumber, int columnNumber,
			int exceptionType,  int exceptionLevel) {
		
		super(lineNumber, columnNumber, exceptionType, exceptionLevel);
	}
	
	public ParseException(){
		//TODO exceptionMsg需要从XML文件读取
		super(0, 0, 1 , 1);
	}

	@Override
	public String getExceptionMsg() {
		// TODO Auto-generated method stub
		return null;
	}

}