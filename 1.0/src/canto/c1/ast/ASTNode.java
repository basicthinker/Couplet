package canto.c1.ast;

import java.util.HashMap;
import java.util.Map;

import canto.AbstractSyntaxTree;

/**
 * C1语言AST结点的基类 
 */
public abstract class ASTNode implements canto.AbstractSyntaxTree {
	
	/** 定义AST结点类型编号常量 */ 
	public static final int PROGRAM = 1;
	public static final int STATEMENT_LIST = 2;
	public static final int BLOCK = 3;
	public static final int ASSIGNMENT_STATEMENT = 4;
	public static final int EXPRESSION_STATEMENT = 5;
	public static final int IF_STATEMENT = 6;
	public static final int WHILE_STATEMENT = 7;
	public static final int BREAK_STATEMENT = 8;
	public static final int CONTINUE_STATEMENT = 9;
	public static final int INPUT_STATEMENT = 10;
	public static final int OUTPUT_STATEMENT = 11;
	public static final int UNARY_EXPRESSION = 12;
	public static final int BINARY_EXPRESSION = 13;
	public static final int	IDENTIFIER = 14;	
	public static final int INTEGER_LITERAL = 15;
	public static final int UNARY_OPERATOR = 16;
	public static final int BINARY_OPERATOR = 17;
	
	/** 存储该AST结点的父结点 */
	private ASTNode parent;
	
	/** 存储该AST结点所在的行号 */
	private final int line;
	
	/** 存储该AST结点所在的列号 */
	private final int column;
	
	/** 存储该AST结点属性的Map  */
	private Map<String, Object> attributesMap; 
	
	@Override
	public AbstractSyntaxTree getParent() {
		return parent;
	}
	
	public void setParent(ASTNode parent) {
		this.parent = parent;
	}
	
	@Override
	public int getLine() {
		return line;
	}

	@Override
	public int getColumn() {
		return column;
	}
	
	public ASTNode(int line, int column) {
		this.line = line;
		this.column = column;
		attributesMap = new HashMap<String, Object>();
	}
	
	/**
	 * 接受访问者访问该AST结点的方法
	 * @param visitor 访问者
	 */
	public abstract void accept(ASTVisitor visitor) throws Exception;
	
	/**
	 * 获取AST结点的某属性
	 * @param name 属性名称
	 * @return 属性值
	 */
	public Object getAttribute(String name) {
		return attributesMap.get(name);
	}
	
	/**
	 * 设置AST结点的某属性
	 * @param name 属性名称
	 * @param value 属性值
	 */
	public void setAttribute(String name, Object value) {
		attributesMap.put(name, value);
	}
	
}
