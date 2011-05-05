package couplet;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

/**
 * 词法分析器Lexer的通用接口，独立于语言版本
 */
public interface Lexer {

	public void open(InputStreamReader inStr) throws IOException;
	
	public void scan() throws CantoException;
	
	public List<Token> getTokenList();
	
}
