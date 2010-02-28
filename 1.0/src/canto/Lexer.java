/**
 * 词法分析器Lexer的通用接口，独立于语言版本
 */
package canto;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

/**
 * @author basicthinker
 *
 */
public interface Lexer {

	public void open(InputStreamReader inStr) throws IOException;
	
	public List<canto.Token> scan() throws IOException;
	
	public List<canto.Token> getTokenList();
	
}
