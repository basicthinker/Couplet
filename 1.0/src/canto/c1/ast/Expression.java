package canto.c1.ast;

/**
 * 表达式结点的基类
 */
public abstract class Expression extends ASTNode {
	
	/** 记录表达式的类型 */
	private Type type;
	
	/**
	 * 设置表达式的类型
	 * @param type 表达式的类型
	 */
	public void setType(Type type) {
		this.type = type;
	}
	
	/**
	 * 获取表达式的类型
	 * @return 表达式的类型
	 */
	public Type getType() {
		return type;
	}
	
}
