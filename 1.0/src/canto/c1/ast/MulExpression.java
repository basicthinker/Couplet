package canto.c1.ast;

public class MulExpression extends BinaryExpression {

	public MulExpression(Expression leftOperand, Expression rightOperand,
			int line, int column) {
		super(leftOperand, rightOperand, line, column);
	}

	@Override
	public void accept(ASTVisitor visitor) throws Exception {
		visitor.visit(this);
	}

	@Override
	public int getNodeType() {
		return MUL_EXPRESSION;
	}

}
