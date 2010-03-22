package canto.c1.ast;

/**
 * 整型结点
 */
public class IntegerType extends Type {

	@Override
	public void accept(ASTVisitor visitor) throws Exception {
		visitor.visit(this);
	}

	@Override
	public int getNodeType() {
		return INTEGER_TYPE;
	}

}
