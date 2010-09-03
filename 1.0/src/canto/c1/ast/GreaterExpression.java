package canto.c1.ast;

public class GreaterExpression extends BinaryExpression {

	public GreaterExpression(Expression leftOperand, Expression rightOperand,
			int line, int column) {
		super(leftOperand, rightOperand, line, column);
	}

	@Override
	public void accept(ASTVisitor visitor) throws Exception {
		visitor.visit(this);
	}

	@Override
	public int getASTType() {
		return GREATER_EXPRESSION;
	}

}
