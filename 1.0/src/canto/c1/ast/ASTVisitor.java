package canto.c1.ast;

/**
 * AST访问者接口
 *   每个具体的AST结点有一个对应的visit方法，将在该结点的accept方法中被调用
 */
public interface ASTVisitor {
	public void visit(Program node);
	public void visit(Block node);
	public void visit(StatementList node);
	public void visit(Declaration node);
	public void visit(AssignmentStatement node);
	public void visit(IfStatement node);
	public void visit(WhileStatement node);
	public void visit(InputStatement node);
	public void visit(OutputStatement node);
	public void visit(UnaryExpression node);
	public void visit(BinaryExpression node);
	public void visit(Identifier node);
	public void visit(IntegerLiteral node);
	public void visit(IntegerType node);
	public void visit(BooleanType node);
	public void visit(UnaryOperator node);
	public void visit(BinaryOperator node);
}
