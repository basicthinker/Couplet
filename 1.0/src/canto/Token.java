/**
 * ����Token��ͨ�ýӿڣ����������԰汾
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
