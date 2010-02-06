/**
 * 
 */
package canto;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

/**
 * @author basicthinker
 *
 */
public interface Compiler {

	public void setSource(InputStream inStrm);
	
	public void setTarget(OutputStream outStrm);
	
	public void compile();
	
	public void outputErrors(OutputStream outStrm);
	
	public List<Token> getTokenList(); 
	
	public SyntaxTree getSyntaxTree();
	
	public IntermediateCode getIntermediateCode();
	
}
