package couplet.c1.ic;

/**
 * C1语言中间代码的顶层接口
 */
public interface IntermediateCode extends couplet.IntermediateCode {
	
	/** 定义IC类型编号常量 */ 
	public static final int INSTRUCTION_LIST = 1;
	public static final int LABEL = 2;
	public static final int IN = 3;
	public static final int OUT = 4;
	public static final int UNCOND_JUMP = 5;
	public static final int EQ_JUMP = 6;
	public static final int NE_JUMP = 7;
	public static final int LT_JUMP = 8;
	public static final int LE_JUMP = 9;
	public static final int GT_JUMP = 10;
	public static final int GE_JUMP = 11;
	public static final int MOVE = 12;
	public static final int ADD = 13;
	public static final int SUB = 14;
	public static final int MUL = 15;
	public static final int NEG = 16;
	public static final int VARIABLE = 17;
	public static final int TEMP = 18;
	public static final int INTEGER_LITERAL = 19;
	
	/**
	 * 接受访问者访问IC的方法
	 * @param visitor 访问者
	 */
	public void accept(ICVisitor visitor) throws Exception;
	
}
