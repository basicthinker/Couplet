/**
 * 
 */
package canto.c1;

/**
 * @author basicthinker
 *
 */
public interface Token {

	public int getLine();
	
	public int getColumn();
	
	public String getLexeme();
	
	public Object getAttribute();
	
}
