package couplet.c1;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Goodness
 * 继承自符号表的C1的名值对照表
 */
public class SymbolTable<INFO_TYPE> implements couplet.SymbolTable<INFO_TYPE> {
	
	/**变量名和临时变量的对照表*/

	private Map<String, INFO_TYPE> table;
	
	/**
	 * 符号表的构造函数，初始化一个对照表
	 */
	public SymbolTable(){
		table=new HashMap<String, INFO_TYPE>();
	}
	
	@Override
	public void put(String symbol, INFO_TYPE info){
		table.put(symbol, info);
	}
	
	@Override
	public INFO_TYPE get(String symbol){
		return table.get(symbol);
	}
	
	@Override
	public void del(String symbol) {
		table.remove(symbol);
	}

	@Override
	public boolean isExist(String symbol){
		return table.containsKey(symbol);
	}

}
