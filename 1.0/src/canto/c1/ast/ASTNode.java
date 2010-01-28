package canto.c1.ast;

public abstract class ASTNode {
	
	/**
	 * AST node type.
	 */
	public enum NodeType {
		PROGRAM,
		BLOCK,
		STATEMENT,
		STATEMENT_LIST,
		DECLARATION_STATEMENT,
		ASSIGNMENT_STATEMENT,
		IF_STATEMENT,
		WHILE_STATEMENT,
		TYPE,
		INTERGER_TYPE,
		EXPRESSION,
		IDENTIFIER,		
		UNARY_EXPRESSION,
		BINARY_EXPRESSION,
		PARENTHESIZED_EXPRESSION,
		LITERAL,
		INTEGER_LITERAL,
	}
	
	/**
	 * Accepts the given visitor on a visit of the current node.
	 * @param visitor the visitor
	 */
	public abstract void accept(ASTVisitor visitor);
	
	/**
	 * Get the type of the AST node.
	 * @return the node type
	 */
	public abstract NodeType getNodeType();
}
