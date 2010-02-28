/**
 * 单词Token的通用接口，独立于语言版本
 */
package canto;

/**
 * @author basicthinker
 *
 */
public interface Token {

	/**
	 * @return 单词所在行号，由1计起，如果为零表明行号未计算
	 */
	public int getLineNumber();
	
	/**
	 * @return 单词所在列数，由1计起，如果为零表明列数未计算
	 */
	public int getColumnNumber();
	
	public String getLexeme();
	
	public Object getAttribute();
	
	public int getType();
	
}
