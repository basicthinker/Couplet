package canto.c1.ast;

import canto.CantoException;

public class NotExpression extends UnaryExpression {

	public NotExpression(Expression operand, int line, int column) {
		super(operand, line, column);
	}

	@Override
	public void accept(ASTVisitor visitor) throws CantoException {
		visitor.visit(this);
	}

	@Override
	public int getASTType() {
		return NOT_EXPRESSION;
	}

}
