package canto.c1.exception;

import canto.exception.CantoException;
import canto.exception.CantoExceptionLevel;
import canto.exception.CantoExceptionType;

/**
 * @author WANGZHE
 *
 */
@SuppressWarnings("serial")
public class LexException extends CantoException {
	
	/**
	 * 
	 * @param lineNumber
	 * @param columnNumber
	 * @param exceptionType
	 * @param exceptionMsg
	 * @param exceptionLevel
	 */
	public LexException(int lineNumber, int columnNumber, CantoExceptionType exceptionType,
			String exceptionMsg, CantoExceptionLevel exceptionLevel) {
		
		super(lineNumber, columnNumber, exceptionType, exceptionMsg, exceptionLevel);
	}
	
	public LexException(){
		super(0, 0, null, null, null);
	}

}


