package canto;

import canto.c1.ast.ASTVisitor;

/**
 * AST的顶层接口
 */
public interface AbstractSyntaxTree {
	
	/**
	 * 接受访问者访问该AST结点的方法
	 * @param visitor 访问者
	 */
	public void accept(ASTVisitor visitor) throws Exception;
	
	/**
	 * 获取AST结点的类型
	 * @return AST结点类型编号
	 */
	public int getNodeType();
	
}
