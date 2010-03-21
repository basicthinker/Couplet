package canto.c1.ast;

/**
 * 整型结点
 */
public class IntegerType extends Type {

	/**
	 * 构造一个整型结点
	 */
	public IntegerType() {
	}

	@Override
	public void accept(ASTVisitor visitor) {
		visitor.visit(this);
	}

	@Override
	public int getNodeType() {
		return INTEGER_TYPE;
	}

}
