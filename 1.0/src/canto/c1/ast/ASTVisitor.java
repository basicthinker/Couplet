package canto.c1.ast;

/**
 * AST访问者接口
 *   每个具体的AST结点有一个对应的visit方法，将在该结点的accept方法中被调用
 */
public interface ASTVisitor {
	public void visit(Program node) throws Exception;
	public void visit(Block node) throws Exception;
	public void visit(StatementList node) throws Exception;
	public void visit(Declaration node) throws Exception;
	public void visit(AssignmentStatement node) throws Exception;
	public void visit(IfStatement node) throws Exception;
	public void visit(WhileStatement node) throws Exception;
	public void visit(InputStatement node) throws Exception;
	public void visit(OutputStatement node) throws Exception;
	public void visit(UnaryExpression node) throws Exception;
	public void visit(BinaryExpression node) throws Exception;
	public void visit(Identifier node) throws Exception;
	public void visit(IntegerLiteral node) throws Exception;
	public void visit(IntegerType node) throws Exception;
	public void visit(BooleanType node) throws Exception;
	public void visit(UnaryOperator node) throws Exception;
	public void visit(BinaryOperator node) throws Exception;
}
