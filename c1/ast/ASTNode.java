package canto.c1.ast;

public abstract class ASTNode {
	
	public enum NodeType {
		PROGRAM,
		BLOCK,
		STATEMENT,
		STATEMENT_LIST,
		DECLARATION_STATEMENT,
		ASSIGNMENT_STATEMENT,
		EXPRESSION_STATEMENT,	
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
	
	public abstract void accept(ASTVisitor visitor);
	
	public abstract NodeType getType();
}
