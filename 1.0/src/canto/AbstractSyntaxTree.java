/**
 * 
 */
package canto;

import canto.c1.ast.ASTVisitor;

/**
 * @author
 *
 */
public interface AbstractSyntaxTree {
	
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
