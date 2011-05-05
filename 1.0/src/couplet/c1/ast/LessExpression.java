package couplet.c1.ast;

public class LessExpression extends BinaryExpression {

	public LessExpression(Expression leftOperand, Expression rightOperand,
			int line, int column) {
		super(leftOperand, rightOperand, line, column);
	}

	@Override
	public void accept(ASTVisitor visitor) throws Exception {
		visitor.visit(this);
	}

	@Override
	public int getASTType() {
		return LESS_EXPRESSION;
	}

}
