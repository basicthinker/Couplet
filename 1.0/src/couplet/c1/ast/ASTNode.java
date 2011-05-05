package couplet.c1.ast;

/**
 * C1语言AST结点的顶层接口
 */
public interface ASTNode extends couplet.AbstractSyntaxTree {
	
	/** 定义AST结点类型编号常量 */ 
	public static final int PROGRAM = 1;
	public static final int STATEMENT_LIST = 2;
	public static final int BLOCK = 3;
	public static final int ASSIGNMENT_STATEMENT = 4;
	public static final int EXPRESSION_STATEMENT = 5;
	public static final int IF_STATEMENT = 6;
	public static final int WHILE_STATEMENT = 7;
	public static final int INPUT_STATEMENT = 8;
	public static final int OUTPUT_STATEMENT = 9;
	public static final int POS_EXPRESSION = 10;
	public static final int NEG_EXPRESSION = 11;
	public static final int NOT_EXPRESSION = 12;
	public static final int ADD_EXPRESSION = 13;
	public static final int SUB_EXPRESSION = 14;
	public static final int MUL_EXPRESSION = 15;
	public static final int LESS_EXPRESSION = 16;
	public static final int LESS_EQUAL_EXPRESSION = 17;
	public static final int GREATER_EXPRESSION = 18;
	public static final int GREATER_EQUAL_EXPRESSION = 19;
	public static final int EQUAL_EXPRESSION = 20;
	public static final int NOT_EQUAL_EXPRESSION = 21;
	public static final int AND_EXPRESSION = 22;
	public static final int OR_EXPRESSION = 23;
	public static final int	IDENTIFIER = 24;	
	public static final int INTEGER_LITERAL = 25;
	
	/**
	 * 设置该AST结点的父结点
	 * @param parent 父结点 
	 */
	public void setParent(ASTNode parent);
	
	/**
	 * 获取AST结点的某属性
	 * @param name 属性名称
	 * @return 属性值
	 */
	public Object getProperty(String name);
	
	/**
	 * 设置AST结点的某属性
	 * @param name 属性名称
	 * @param value 属性值
	 */
	public void setProperty(String name, Object value);

	/**
	 * 接受访问者访问该AST结点的方法
	 * @param visitor 访问者
	 */
	public void accept(ASTVisitor visitor) throws Exception;
	
}
