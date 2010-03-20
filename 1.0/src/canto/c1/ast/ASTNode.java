package canto.c1.ast;

public abstract class ASTNode implements canto.AbstractSyntaxTree {
	
	/** The AST node type code of each kind of AST node. */ 
	public static final int PROGRAM = 1;
	public static final int BLOCK = 2;
	public static final int STATEMENT_LIST = 3;
	public static final int DECLARATION = 4;
	public static final int ASSIGNMENT_STATEMENT = 5;
	public static final int IF_STATEMENT = 6;
	public static final int WHILE_STATEMENT = 7;
	public static final int INPUT_STATEMENT = 8;
	public static final int OUTPUT_STATEMENT = 9;
	public static final int UNARY_EXPRESSION = 10;
	public static final int BINARY_EXPRESSION = 11;
	public static final int	IDENTIFIER = 12;	
	public static final int INTEGER_LITERAL = 13;
	public static final int PRIMITIVE_TYPE = 14;
	public static final int UNARY_OPERATOR = 15;
	public static final int BINARY_OPERATOR = 16;
	
	/**
	 * Accepts the given visitor on a visit of the current node.
	 * @param visitor the visitor
	 */
	public abstract void accept(ASTVisitor visitor);
	
	/**
	 * Get the type of the AST node.
	 * @return the node type
	 */
	public abstract int getNodeType();
	
}
