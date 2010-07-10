package canto.c1.x86;

/**
 * X86目标码的基类
 */
public abstract class X86TargetCode implements canto.TargetCode {

	/** 定义TC类型编号常量 */ 
	public static final int PROGRAM = 1;
	public static final int DATA_SEGMENT = 2;
	public static final int CODE_SEGMENT = 3;
	public static final int DATA_DEFINE = 4;
	public static final int LABEL = 5;
	public static final int MOV = 6;
	public static final int PUSH = 7;
	public static final int POP = 8;
	public static final int ADD = 9;
	public static final int SUB = 10;
	public static final int IMUL = 11;
	public static final int NEG = 12;
	public static final int CMP = 13;
	public static final int JMP = 14;
	public static final int JE = 15;
	public static final int JNE = 16;
	public static final int JG = 17;
	public static final int JGE = 18;
	public static final int JL = 19;
	public static final int JLE = 20;
	public static final int IN_INTEGER = 21;
	public static final int OUT_INTEGER = 22;
	public static final int IMMEDIATE = 23;
	public static final int SYMBOL = 24;
	public static final int REGISTER = 25;
	public static final int PSEUDO = 26;
	
	/**
	 * 接受访问者访问TC的方法
	 * @param visitor 访问者
	 */
	public abstract void accept(X86Visitor visitor);
	
}
