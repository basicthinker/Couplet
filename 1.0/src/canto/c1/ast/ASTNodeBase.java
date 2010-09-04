package canto.c1.ast;

import java.util.HashMap;
import java.util.Map;

/**
 * C1语言AST的基类
 */
public abstract class ASTNodeBase implements ASTNode {
	
	/** 存储该AST结点的父结点 */
	private ASTNode parent;
	
	/** 存储该AST结点所在的行号 */
	private final int line;
	
	/** 存储该AST结点所在的列号 */
	private final int column;
	
	/** 存储该AST结点属性的映射表 */
	private Map<String, Object> propertiesMap; 
	
	/**
	 * 构造一个AST结点
	 * @param line 该AST结点所在的行号
	 * @param column 该AST结点所在的列号
	 */
	public ASTNodeBase(int line, int column) {
		this.line = line;
		this.column = column;
		propertiesMap = new HashMap<String, Object>();
	}
	
	@Override
	public ASTNode getParent() {
		return parent;
	}
	
	@Override
	public int getLine() {
		return line;
	}

	@Override
	public int getColumn() {
		return column;
	}
	
	@Override
	public void setParent(ASTNode parent) {
		this.parent = parent;
	}
	
	@Override
	public Object getProperty(String name) {
		return propertiesMap.get(name);
	}
	
	@Override
	public void setProperty(String name, Object value) {
		propertiesMap.put(name, value);
	}

}
