package canto.c1.ast;

public class UnaryExpression extends Expression {

	public enum Operator {
		POSITIVE,
		NEGTIVE,
		NOT,
	}
	
	@Override
	public void accept(ASTVisitor visitor) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public NodeType getType() {
		// TODO Auto-generated method stub
		return null;
	}

}
