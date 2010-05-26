package canto.c1.exception;

import canto.CantoException;

/**
 * @author WANGZHE
 *
 */
@SuppressWarnings("serial")
public class LexException extends CantoException {
	
	/**定义C1的语法分析异常类型*/
	public static final int IllegleCharactor = 1001;
	
	
	/**
	 * C1的词法分析错误构造函数
	 * @param lineNumber 行号
	 * @param columnNumber 列号
	 * @param exceptionType 异常类型
	 * @param exceptionLevel 异常等级
	 */
	public LexException(int lineNumber, int columnNumber, int exceptionType, int exceptionLevel) {
		super(lineNumber, columnNumber, exceptionType, exceptionLevel);
		super.exceptionMsg = "Fine from xml";
	}
	
	public String getExceptionMsg(){
		return "[Lex "+ (super.exceptionLevel == 0 ? "error":"warning") +"]Line " + super.lineNumber + ", Column " + super.columnNumber + " : " + super.exceptionMsg;
	}

}


