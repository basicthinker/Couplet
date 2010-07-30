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
	
	public void compile() throws Exception;
	
	public void outputErrors(OutputStream outStrm);
	
	public List<Token> getTokenList(); 
	
	public AbstractSyntaxTree getAST();
	
	public IntermediateCode getIC();
	
	public TargetCode getTC();
	
}
