package canto.c1.ast;

public class IntegerType extends Type {

	@Override
	public void accept(ASTVisitor visitor) {
		visitor.visit(this);
	}

	@Override
	public NodeType getNodeType() {
		return NodeType.INTERGER_TYPE;
	}

}
