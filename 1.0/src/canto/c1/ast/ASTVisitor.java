package canto.c1.ast;

/**
 * The interface of the visitor for AST.
 *     Each method of the interface is a visit for a special kind of AST node.
 *     Each kind of the AST node has a accept method, it will invoke the 
 * corresponding visit method of the interface.
 */
public interface ASTVisitor {
	  public void visit(Program node);
	  public void visit(Block node);
	  public void visit(StatementList node);
	  public void visit(DeclarationStatement node);
	  public void visit(AssignmentStatement node);
	  public void visit(IfStatement node);
	  public void visit(WhileStatement node);
	  public void visit(PrimitiveType node);
	  public void visit(Identifier node);
	  public void visit(UnaryExpression node);
	  public void visit(BinaryExpression node);
	  public void visit(ParenthesizedExpression node);
	  public void visit(IntegerLiteral node);
}
