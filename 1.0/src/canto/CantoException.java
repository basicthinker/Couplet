package canto;



/**
 * 
 * @author WANGZHE
 *
 */
@SuppressWarnings("serial")
public abstract class CantoException extends Exception {
	
	protected final int lineNumber;
	protected final int columnNumber;
	protected final int exceptionType;
	protected final int exceptionLevel;
	protected String exceptionMsg;
	
	/**定义异常的等级*/
	public static final int LevelError = 0;
	public static final int LevelWarning = 1;
	
	//protected final String exceptionMsg;
	
	public CantoException(int lineNumber, int columnNumber, int exceptionType, int exceptionLevel){
		this.lineNumber = lineNumber;
		this.columnNumber = columnNumber;
		this.exceptionType = exceptionType;
		this.exceptionMsg = null;
		this.exceptionLevel = exceptionLevel;
	}
	
	public int getLineNumber(){
		return lineNumber;
	}
	
	public int getColumnNumber(){
		return columnNumber;
	}
	
	public abstract String getExceptionMsg();

}
