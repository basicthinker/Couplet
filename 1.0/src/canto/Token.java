/**
 * 单词Token的通用接口，独立于语言版本
 */
package canto;

/**
 * @author basicthinker
 *
 */
public interface Token {

	public int getLineNum();
	
	public int getColumnNum();
	
	public String getLexeme();
	
	public Object getAttribute();
	
	public int getType();
	
}
