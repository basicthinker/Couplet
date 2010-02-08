package canto.c1.ast;

/**
 * The scanner for AST.
 *    It implement the visitor for AST.
 *    Each visit method will visit a node and its descendants by pre-order.
 *    It will do nothing when visit a node.
 *    Subclasses may reimplement each method to add affairs during visit. You 
 * may put super() after your affairs so implement pre-order traversal. You may 
 * put super() before your affairs so implement post-order traversal.
 */
public class ASTScanner implements ASTVisitor {

	@Override
	public void visit(Program node) {
		node.getBody().accept(this);
	}

	@Override
	public void visit(Block node) {
	}

	@Override
	public void visit(Declaration node) {
		node.getType().accept(this);
		node.getIdentifier().accept(this);
	}

	@Override
	public void visit(AssignmentStatement node) {
		node.getIdentifier().accept(this);
		node.getExpression().accept(this);
	}

	@Override
	public void visit(IfStatement node) {
		node.getCondition().accept(this);
		node.getThenStatement().accept(this);
	}

	@Override
	public void visit(WhileStatement node) {
		node.getBody().accept(this);
	}

	@Override
	public void visit(InputStatement node) {
		node.getIdentifier().accept(this);
	}

	@Override
	public void visit(OutputStatement node) {
		node.getExpression().accept(this);
	}
	
	@Override
	public void visit(UnaryExpression node) {
		node.getOperand().accept(this);
	}

	@Override
	public void visit(BinaryExpression node) {
		node.getLeftOperand().accept(this);
		node.getRightOperand().accept(this);
	}

	@Override
	public void visit(ParenthesizedExpression node) {
		node.getBody().accept(this);
	}
	
	@Override
	public void visit(Identifier node) {
	}

	@Override
	public void visit(IntegerLiteral node) {
	}
	
	@Override
	public void visit(PrimitiveType node) {		
	}


	@Override
	public void visit(UnaryOperator node) {
	}

	@Override
	public void visit(BinaryOperator node) {
	}

}