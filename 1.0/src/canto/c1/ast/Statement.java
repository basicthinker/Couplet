package canto.c1.ast;

/**
 * 语句结点的基类
 */
public abstract class Statement extends ASTNode {

	public Statement(int line, int column) {
		super(line, column);
	}

}
