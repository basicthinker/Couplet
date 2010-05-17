package canto.exception;



/**
 * 
 * @author WANGZHE
 *
 */
public abstract class CantoException extends Exception {
	
	private final int lineNumber;
	private final int columnNumber;
	private final CantoExceptionType exceptionType;
	private final String exceptionMsg;
	private final CantoExceptionLevel exceptionLevel;
	
	public CantoException(int lineNumber, int columnNumber, CantoExceptionType exceptionType, String exceptionMsg, CantoExceptionLevel exceptionLevel){
		this.lineNumber = lineNumber;
		this.columnNumber = columnNumber;
		this.exceptionType = exceptionType;
		this.exceptionMsg = exceptionMsg;
		this.exceptionLevel = exceptionLevel;
	}
	
	public int getLineNumber(){
		return lineNumber;
	}
	
	public int getColumnNumber(){
		return columnNumber;
	}
	
	public String getExceptionMsg(){
		return exceptionMsg;
	}

}
