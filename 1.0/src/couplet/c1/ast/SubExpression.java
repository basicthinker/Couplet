package couplet.c1.ast;

public class SubExpression extends BinaryExpression {

	public SubExpression(Expression leftOperand, Expression rightOperand,
			int line, int column) {
		super(leftOperand, rightOperand, line, column);
	}

	@Override
	public void accept(ASTVisitor visitor) throws Exception {
		visitor.visit(this);
	}

	@Override
	public int getASTType() {
		return SUB_EXPRESSION;
	}

}
