package couplet.c1.token;

/**
 * C1语言Token的顶层接口
 */
public interface Token extends couplet.Token {

	/** 终极符编号常量 */
	public static final int ID = 1;
	public static final int INTEGER_LITERAL = 2;
	public static final int IF = 3;
	public static final int ELSE = 4;
	public static final int WHILE = 5;
	public static final int INPUT = 6;
	public static final int OUTPUT = 7;
	public static final int L_BRACE = 8;
	public static final int R_BRACE = 9;
	public static final int L_PARENT = 10;
	public static final int R_PARENT  = 11;
	public static final int SEMI = 12;
	public static final int EQUAL = 13;
	public static final int NOT = 14;
	public static final int PLUS = 15;
	public static final int MINUS = 16;
	public static final int TIMES = 17;
	public static final int LESS = 18;
	public static final int LESS_EQUAL = 19;
	public static final int GREATER = 20;
	public static final int GREATER_EQUAL = 21;
	public static final int EQUAL_EQUAL = 22;
	public static final int NOT_EQUAL = 23;
	public static final int AND_AND = 24;
	public static final int OR_OR = 25;

}
