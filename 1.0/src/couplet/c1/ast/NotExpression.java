package couplet.c1.ast;

public class NotExpression extends UnaryExpression {

	public NotExpression(Expression operand, int line, int column) {
		super(operand, line, column);
	}

	@Override
	public void accept(ASTVisitor visitor) throws Exception {
		visitor.visit(this);
	}

	@Override
	public int getASTType() {
		return NOT_EXPRESSION;
	}

}
