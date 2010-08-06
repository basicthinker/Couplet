package canto.c1.ast;

/**
 * 表达式结点的基类
 */
public abstract class Expression extends ASTNode {

	public Expression(int line, int column) {
		super(line, column);
	}
	
}
