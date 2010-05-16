package canto;

import canto.c1.ast.ASTVisitor;

/**
 * AST的顶层接口
 */
public interface AbstractSyntaxTree {
	
	/**
	 * 获取该AST结点的父结点
	 * @return 该AST结点的父结点
	 */
	public AbstractSyntaxTree getParent();

	/**
	 * 获取该AST结点的类型
	 * @return 该AST结点类型编号
	 */
	public int getNodeType();
	
	/**
	 * 获取该AST结点所在的行号
	 * @return 该段代码首个单词所在的行号
	 */
	public int getLine();
	
	/**
	 * 获取该AST结点所在的列号
	 * @return 该段代码首个单词所在的列号
	 */
	public int getColumn();
	
}
