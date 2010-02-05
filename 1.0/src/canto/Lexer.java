/**
 * �ʷ�������Lexer��ͨ�ýӿڣ����������԰汾
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

	public void open(InputStreamReader in_str) throws IOException;
	
	public List<canto.Token> scan();
	
}
