package canto;

import java.util.List;

public interface Parser {
	
	public void open(List<Token> tokenList);
	
	public AbstractSyntaxTree parse() throws Exception;
	
	public AbstractSyntaxTree getAbstractSyntaxTree();
	
}
