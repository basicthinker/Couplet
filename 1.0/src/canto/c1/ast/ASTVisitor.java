package canto.c1.ast;

import canto.CantoException;

/**
 * AST访问者接口
 *   每个具体的AST结点有一个对应的visit方法，将在该结点的accept方法中被调用
 */
public interface ASTVisitor {
	
	public void visit(Program node) throws CantoException;
	public void visit(StatementList node) throws CantoException;
	public void visit(Block node) throws CantoException;
	public void visit(AssignmentStatement node) throws CantoException;
	public void visit(ExpressionStatement node) throws CantoException;
	public void visit(IfStatement node) throws CantoException;
	public void visit(WhileStatement node) throws CantoException;
	public void visit(InputStatement node) throws CantoException;
	public void visit(BreakStatement node) throws CantoException;
	public void visit(ContinueStatement node) throws CantoException;
	public void visit(OutputStatement node) throws CantoException;
	public void visit(PosExpression node) throws CantoException;
	public void visit(NegExpression node) throws CantoException;
	public void visit(NotExpression node) throws CantoException;
	public void visit(AddExpression node) throws CantoException;
	public void visit(SubExpression node) throws CantoException;
	public void visit(MulExpression node) throws CantoException;
	public void visit(LessExpression node) throws CantoException;
	public void visit(LessEqualExpression node) throws CantoException;
	public void visit(GreaterExpression node) throws CantoException;
	public void visit(GreaterEqualExpression node) throws CantoException;
	public void visit(EqualExpression node) throws CantoException;
	public void visit(NotEqualExpression node) throws CantoException;
	public void visit(AndExpression node) throws CantoException;
	public void visit(OrExpression node) throws CantoException;
	public void visit(Identifier node) throws CantoException;
	public void visit(IntegerLiteral node) throws CantoException;
	
}
