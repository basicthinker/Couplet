package couplet;

/**
 * 单词Token的通用接口，独立于语言版本
 */
public interface Token {

	/**
	 * @return 单词所在行号，由1计起，如果为零表明行号未计算
	 */
	public int getLine();
	
	/**
	 * @return 单词所在列数，由1计起，如果为零表明列数未计算
	 */
	public int getColumn();
	
	/**
	 * 得到Token的相应显示字符串
	 * @return
	 */
	public String getLexeme();
	
	/**
	 * 得到Token对应的值
	 * @return
	 */
	public Object getAttribute();
	
	/**
	 * 得到Token的类型
	 * @return
	 */
	public int getTokenType();
	
}
