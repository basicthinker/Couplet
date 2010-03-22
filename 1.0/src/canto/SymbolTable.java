package canto;

/**
 * 符号表的顶层接口
 */
public interface SymbolTable {

	/**
	 * 进行新的作用域
	 */
	void enterScope();

	/**
	 * 退出当前作用域
	 */
	void exitScope();

	/**
	 * 获取指定名称符号的信息
	 * @param name 符号名称
	 * @return 符号的信息
	 */
	Object get(String name);

	/**
	 * 添加指定名称符号的信息
	 * @param name 符号名称
	 * @param info 符号的信息
	 * @return 添加成功返回true；添加失败返回false 
	 */
	boolean put(String name, Object info);

}
