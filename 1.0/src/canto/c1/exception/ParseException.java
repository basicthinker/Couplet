package canto.c1.exception;

import canto.CantoException;

@SuppressWarnings("serial")
public class ParseException extends CantoException {
	
	/**定义C1的语法分析异常类型*/
	public static final int ParseError = 2000;
	public static final int MissingRightBrace = 2001;
	public static final int MissingLeftBrace = 2002;
	public static final int IllegalStatement = 2003;
	public static final int MissingSemicolon = 2004;
	public static final int MissingEQUAL = 2005;
	public static final int MissingRightParenthesis = 2006;
	public static final int MissingLeftParenthesis = 2007;
	public static final int WrongExpression = 2008;
	public static final int WrongAddress = 2009;

	/**
	 * 
	 * @param lineNumber 行号
	 * @param columnNumber 列号
	 * @param exceptionType 异常类型
	 * @param exceptionLevel 异常等级
	 */
	public ParseException(int lineNumber, int columnNumber,
			int exceptionType,  int exceptionLevel) {		
		super(lineNumber, columnNumber, exceptionType, exceptionLevel);
		super.exceptionMsg = lineNumber +" "+ columnNumber +" "+ String.valueOf(exceptionType);
	}
	
	public ParseException(){
		//TODO exceptionMsg需要从XML文件读取
		super(0, 0, ParseException.ParseError , CantoException.LevelError);
	}

	@Override
	public String getExceptionMsg() {
		return super.exceptionMsg;
	}

}