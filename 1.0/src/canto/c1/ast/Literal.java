package canto.c1.ast;

/**
 * 字面常量结点的基类
 */
public abstract class Literal extends Expression {

	public Literal(int line, int column) {
		super(line, column);
	}

}
