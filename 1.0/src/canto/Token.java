/**
 * ����Token��ͨ�ýӿڣ����������԰汾
 */
package canto;

/**
 * @author basicthinker
 *
 */
public interface Token {

	/**
	 * @return ���ص��������кţ���1�������Ϊ������к�δ����
	 */
	public int getLineNumber();
	
	/**
	 * @return ���ص���������������1�������Ϊ���������δ����
	 */
	public int getColumnNumber();
	
	public String getLexeme();
	
	public Object getAttribute();
	
	public int getType();
	
}
