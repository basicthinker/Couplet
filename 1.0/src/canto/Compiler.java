package canto;

import java.io.IOException;
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
	
	public void compile() throws CantoException;
	
	public void outputErrors(OutputStream outStrm) throws IOException;
	
	public List<Token> getTokenList(); 
	
	public AbstractSyntaxTree getAST();
	
	public IntermediateCode getIC();
	
	public TargetCode getTC();
	
}
