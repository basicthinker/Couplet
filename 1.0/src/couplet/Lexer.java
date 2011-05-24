package couplet;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

/**
 * 词法分析器Lexer的顶层接口，独立于语言版本
 */
public interface Lexer {

	/**
	 * 打开输入流
	 * @param inStr 得到的输入流
	 * @throws IOException 可抛出的输入输出异常
	 */
	public void open(InputStreamReader inStr) throws IOException;
	
	/**
	 * 浏览检查整个输入流，得到Token链表
	 * @throws CantoException
	 */
	public void scan() throws CantoException;
	
	/**
	 * 得到Token链表
	 * @return 链接而成的Token链表
	 */
	public List<Token> getTokenList();
	
}
