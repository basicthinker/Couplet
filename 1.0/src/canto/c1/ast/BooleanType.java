package canto.c1.ast;

/**
 * 布尔型结点
 */
public class BooleanType extends Type {

	@Override
	public void accept(ASTVisitor visitor) throws Exception {
		visitor.visit(this);
	}

	@Override
	public int getNodeType() {
		return BOOLEAN_TYPE;
	}

}
