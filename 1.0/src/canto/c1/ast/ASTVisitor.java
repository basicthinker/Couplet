package canto.c1.ast;

/**
 * AST访问者接口
 *   每个具体的AST结点有一个对应的visit方法，将在该结点的accept方法中被调用
 */
public interface ASTVisitor {
	
	public void visit(Program node) throws Exception;
	public void visit(StatementList node) throws Exception;
	public void visit(Block node) throws Exception;
	public void visit(AssignmentStatement node) throws Exception;
	public void visit(ExpressionStatement node) throws Exception;
	public void visit(IfStatement node) throws Exception;
	public void visit(WhileStatement node) throws Exception;
	public void visit(InputStatement node) throws Exception;
	public void visit(OutputStatement node) throws Exception;
	public void visit(PosExpression node) throws Exception;
	public void visit(NegExpression node) throws Exception;
	public void visit(NotExpression node) throws Exception;
	public void visit(AddExpression node) throws Exception;
	public void visit(SubExpression node) throws Exception;
	public void visit(MulExpression node) throws Exception;
	public void visit(LessExpression node) throws Exception;
	public void visit(LessEqualExpression node) throws Exception;
	public void visit(GreaterExpression node) throws Exception;
	public void visit(GreaterEqualExpression node) throws Exception;
	public void visit(EqualExpression node) throws Exception;
	public void visit(NotEqualExpression node) throws Exception;
	public void visit(AndExpression node) throws Exception;
	public void visit(OrExpression node) throws Exception;
	public void visit(Identifier node) throws Exception;
	public void visit(IntegerLiteral node) throws Exception;
	
}
