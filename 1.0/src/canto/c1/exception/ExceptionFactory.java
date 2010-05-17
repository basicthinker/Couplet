/**
 * 
 */
package canto.c1.exception;

import canto.exception.*;

/**
 * @author WANGZHE
 *
 */
public class ExceptionFactory {
	
	/**
	 * 
	 * @param lineNumber 行号
	 * @param columnNumber 列号
	 * @param exceptionClass 异常分类(Lex, Parse, Semantic)
	 * @param exceptionType 异常信息类别
	 * @param exceptionLevel 异常级别（error, warning）
	 * @return
	 */
	public static CantoException newError(int lineNumber, int columnNumber, CantoExceptionClass exceptionClass,
			CantoExceptionType exceptionType, CantoExceptionLevel exceptionLevel){
		if (exceptionClass == CantoExceptionClass.Lex){
			return new LexException(lineNumber, columnNumber, exceptionType, "Find From XML", exceptionLevel);
		}			
		else if (exceptionClass == CantoExceptionClass.Parse){
			return null;
		}
		else{
			return null;
		}
			
	}

}
