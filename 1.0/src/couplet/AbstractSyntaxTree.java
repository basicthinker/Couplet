package couplet;

/**
 * 抽象语法树（AST）的顶层接口
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
	public int getASTType();
	
	/**
	 * 获取该AST结点所在的行号（相应源代码结构的起始位置）
	 * @return 该AST结点所在的行号
	 */
	public int getLine();
	
	/**
	 * 获取该AST结点所在的列号（相应源代码结构的起始位置）
	 * @return 该AST结点所在的列号
	 */
	public int getColumn();
	
}
