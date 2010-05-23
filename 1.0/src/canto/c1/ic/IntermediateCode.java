package canto.c1.ic;

/**
 * C1语言中间代码的基类
 */
public abstract class IntermediateCode implements canto.IntermediateCode {
	
	/** 定义IC类型编号常量 */ 
	public static final int INSTRUCTION_LIST = 1;
	public static final int LABEL = 2;
	public static final int UNCOND_JUMP = 3;
	public static final int EQ_JUMP = 4;
	public static final int NE_JUMP = 5;
	public static final int LT_JUMP = 6;
	public static final int LE_JUMP = 7;
	public static final int GT_JUMP = 8;
	public static final int GE_JUMP = 9;
	public static final int MOVE = 10;
	public static final int ADD = 11;
	public static final int SUB = 12;
	public static final int MUL = 13;
	public static final int NEG = 14;
	public static final int TEMP = 15;
	public static final int INTEGER_LITERAL = 16;
	
	/**
	 * 接受访问者访问IC的方法
	 * @param visitor 访问者
	 */
	public abstract void accept(ICVisitor visitor) throws Exception;
	
}
