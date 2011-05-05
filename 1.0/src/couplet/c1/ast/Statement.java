package couplet.c1.ast;

/**
 * 语句结点的基类
 */
public abstract class Statement extends ASTNodeBase {

	public Statement(int line, int column) {
		super(line, column);
	}

}
