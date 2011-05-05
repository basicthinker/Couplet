package couplet;

/**
 * 符号表的顶层接口
 */
public interface SymbolTable<INFO_TYPE> {
	
	/**
	 * 向符号表中插入一个符号的信息
	 * @param symbol 符号名
	 * @param info 符号相关信息
	 */
	public void put(String symbol, INFO_TYPE info);
	
	/**
	 * 从符号表中获取某个符号的信息
	 * @param symbol 符号名
	 * @return 符号相关信息
	 */
	public Object get(String symbol);

	/**
	 * 删除表中的某个符号
	 * @param symbol 要删除 的表中对应的符号名
	 */
	public void del(String symbol);

	/**
	 * 判断某个符号是否在符号表中
	 * @param symbol 符号名
	 * @return 存在返回TRUE；不存在返回FALSE
	 */
	public boolean isExist(String symbol);
	
}
