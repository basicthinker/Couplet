package couplet.c1.error;

import java.util.HashMap;
import java.util.Map;

import couplet.c1.token.TokenBase;


/**
 * C1语言的错误记录
 */
public class ErrorRecord implements couplet.ErrorRecord {

	// 以下定义编译过程中的错误类型编号
	// 整个编译阶段的错误
	public static final int COMPILE_ERROR = 0000;
	public static final int IO_ERROR = 0001;
	// 词法分析阶段的错误
	public static final int LEX_ERROR = 1000;
	public static final int ILLEGAL_CHARACTER = 1001;
	public static final int ILLEGAL_IDENTIFIER = 1002;
	// 语法分析阶段的错误
	public static final int PARSE_ERROR = 2000;
	public static final int MISSING_TOKEN = 2001;
	public static final int UNEXPECTED_TOKEN = 2002;
	public static final int WRONG_STATEMENT = 2003;
	public static final int WRONG_EXPRESSION = 2004;
	public static final int WRONG_ADDRESS = 2005;
	public static final int REDUNDANT_TOKENS = 2006;
	public static final int INCOMPELTE_PROGRAM = 2007; 
	// 中间代码生成阶段的错误	
	public static final int IC_GENERATE_ERROR = 3000;
	// 目标代码生成阶段的错误
	public static final int TC_GENERATE_ERROR = 4000;

	/** 存储错误所在位置的行号 */
	protected final int line;
	/** 存储错误所在位置的列号 */
	protected final int column;
	/** 存储错误的类型编号 */
	protected final int type;
	/** 存储错误的相关信息 */
	protected final String extraInfo;
	/** 存储错误信息相关的 */
	protected static Map<Integer, String> infoMap;

	static {
		// 类型初始化时载入错误信息
		infoMap = new HashMap<Integer, String>();
		infoMap.put(COMPILE_ERROR, "未知的编译错误");
		infoMap.put(IO_ERROR, "编译过程中的I/O错误");
		infoMap.put(LEX_ERROR, "未知的词法分析错误");
		infoMap.put(ILLEGAL_CHARACTER, "词法错误：非法的字符");
		infoMap.put(ILLEGAL_IDENTIFIER, "词法错误：非法的标识符");
		infoMap.put(PARSE_ERROR, "未知的语法分析错误");
		infoMap.put(MISSING_TOKEN, "语法错误：丢失期待的字符");
		infoMap.put(UNEXPECTED_TOKEN, "语法错误：未期待的字符");
		infoMap.put(REDUNDANT_TOKENS, "语法错误：程序末尾有多余的字符");
		infoMap.put(REDUNDANT_TOKENS, "语法错误：程序末尾不完整");
		infoMap.put(WRONG_STATEMENT, "语法错误：错误的语句");
		infoMap.put(WRONG_EXPRESSION, "语法错误：错误的表达式");
		infoMap.put(IC_GENERATE_ERROR, "未知的中间代码生成错误");
		infoMap.put(IC_GENERATE_ERROR, "未知的目标代码生成错误");
	}

	/**
	 * 构造一条只有错误类型的错误记录
	 * @param type 错误类型编号
	 */
	private ErrorRecord(int type) {
		this.line = 0;
		this.column = 0;
		this.type = type;
		this.extraInfo = "";
	}
	
	/**
	 * 构造一条具有错误类型、行列号的错误记录
	 * @param type 错误类型编号
	 * @param line 错误所在位置的行号
	 * @param column 错误所在位置的列号
	 */
	private ErrorRecord(int type, int line, int column) {
		this.type = type;
		this.line = line;
		this.column = column;		
		this.extraInfo = "";
	}
	
	/**
	 * 构造一条具有错误类型、行列号、附加信息的错误记录
	 * @param type 错误类型编号
	 * @param line 错误所在位置的行号
	 * @param column 错误所在位置的列号
	 * @param extraInfo 错误的附加信息
	 */
	private ErrorRecord(int type, int line, int column, String extraInfo) {
		this.type = type;
		this.line = line;
		this.column = column;
		this.extraInfo = extraInfo;
	}
	
	@Override
	public String getInfo() {
		// 错误信息由错误类型决定的固有信息和附加信息组成
		return infoMap.get(type) + extraInfo;
	}

	/**
	 * 获取错误所在位置的行号
	 * @return 错误所在位置的行号
	 */
	public int getLine(){
		return line;
	}
	
	/**
	 * 获取错误所在位置的列号
	 * @return 错误所在位置的列号
	 */
	public int getColumn(){
		return column;
	}
	
	/**
	 * 获取错误的类型编号
	 * @return 错误的类型编号
	 */
	public int getType() {
		return type;
	}
	
	/**
	 * 创建一条具体信息未知的通用的编译错误
	 * @return 通用的编译错误
	 */
	public static ErrorRecord compileError() {
		return new ErrorRecord(COMPILE_ERROR);
	}
	
	/**
	 * 创建一条编译过程中的I/O错误
	 * @return 编译过程中的I/O错误
	 */
	public static ErrorRecord ioError() {
		return new ErrorRecord(IO_ERROR);
	}
	
	/**
	 * 创建一条具体信息未知的通用的词法分析错误
	 * @return 通用的词法分析错误
	 */
	public static ErrorRecord lexError() {
		return new ErrorRecord(COMPILE_ERROR);
	}
	
	/**
	 * 创建一条词法分析时遇到非法字符的错误
	 * @param line 错误所在位置的行号
	 * @param column 错误所在位置的列号
	 * @param ch 错误所涉及的字符
	 * @return 非法字符错误
	 */
	public static ErrorRecord illegalCharacter(int line, int column, char ch) {
		return new ErrorRecord(ILLEGAL_CHARACTER, line, column, 
				"\"" + ch + "\"");
	}
	
	/**
	 * 创建一条词法分析时遇到非法标识符的错误
	 * @param line 错误所在位置的行号
	 * @param column 错误所在位置的列号
	 * @param ch 错误所涉及的标识符
	 * @return 非法标识符错误
	 */
	public static ErrorRecord illegalIdentifier(int line, int column, String id) {
		return new ErrorRecord(ILLEGAL_IDENTIFIER, line, column, 
				"\"" + id + "\"");
	}	
	/**
	 * 创建一条具体信息未知的通用的语法分析错误
	 * @return 通用的语法分析错误
	 */
	public static ErrorRecord parseError() {
		return new ErrorRecord(PARSE_ERROR);
	}
	
	/**
	 * 创建一条语法分析时的丢失Token的错误
	 * @param line 错误所在位置的行号
	 * @param column 错误所在位置的列号
	 * @param tokenType 错误所涉及的Token类型
	 * @return 丢失Token错误
	 */
	public static ErrorRecord missingToken(int line, int column, int tokenType) {
		return new ErrorRecord(MISSING_TOKEN, line, column, 
				"\"" + TokenBase.getCodeToken(tokenType) + "\"");
	}
	
	/**
	 * 创建一条语法分析时的未期待的Token的错误
	 * @param line 错误所在位置的行号
	 * @param column 错误所在位置的列号
	 * @param lexme 错误所涉及的Token字符
	 * @return 未期待的Token错误
	 */
	public static ErrorRecord unexpectedToken(int line, int column, String lexme) {
		return new ErrorRecord(UNEXPECTED_TOKEN, line, column, 
				"\"" + lexme + "\"");
	}
	
	/**
	 * 创建一条语法分析时的语句错误
	 * @param line 错误所在位置的行号
	 * @param column 错误所在位置的列号
	 * @return 语句错误
	 */
	public static ErrorRecord wrongStatement(int line, int column) {
		return new ErrorRecord(WRONG_STATEMENT, line, column);
	}
	
	/**
	 * 创建一条语法分析时的表达式错误
	 * @param line 错误所在位置的行号
	 * @param column 错误所在位置的列号
	 * @return 表达式错误
	 */
	public static ErrorRecord wrongExpression(int line, int column) {
		return new ErrorRecord(WRONG_EXPRESSION, line, column);
	}
	
	/**
	 * 创建一条语法分析时的末尾冗余Token的错误
	 * @param line 错误所在位置的行号
	 * @param column 错误所在位置的列号
	 * @return 末尾冗余Token错误
	 */
	public static ErrorRecord redundantTokens(int line, int column) {
		return new ErrorRecord(REDUNDANT_TOKENS, line, column);
	}
	
	/**
	 * 创建一条语法分析时的程序末尾不完整的错误
	 * @param line 错误所在位置的行号
	 * @param column 错误所在位置的列号
	 * @return 程序末尾不完整错误
	 */
	public static ErrorRecord incompleteProgram(int line, int column) {
		return new ErrorRecord(INCOMPELTE_PROGRAM, line, column);
	}
	
	/**
	 * 创建一条具体信息未知的通用的中间代码生成错误
	 * @return 通用的中间代码生成错误
	 */
	public static ErrorRecord icGenerateError() {
		return new ErrorRecord(IC_GENERATE_ERROR);
	}
	
	/**
	 * 创建一条具体信息未知的通用的目标代码生成错误
	 * @return 通用的目标代码生成错误
	 */
	public static ErrorRecord tcGenerateError() {
		return new ErrorRecord(TC_GENERATE_ERROR);
	}
	
}
