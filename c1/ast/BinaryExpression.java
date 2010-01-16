package canto.c1.ast;

public class BinaryExpression extends Expression {

	public enum Operator {
		PLUS,
		MINUS,
		TIMES,
		LESS,
		GREATER,
		LESS_EQUALS,
		GREATER_EQUALS,
		EQUALS,
		NOT_EQUALS,
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
