package canto.c1.ic;

import java.util.HashMap;
import java.util.Map;

import canto.c1.SymbolTable;
import canto.c1.ast.Access;

/**
 * @author Goodness
 * 继承自符号表的C1的名值对照表
 */
public class HashTable extends SymbolTable {
	/**变量名和临时变量的对照表*/
	private Map<Access, Location> symbolToTemp;
	
	/**
	 * 符号表的构造函数，初始化一个对照表
	 */
	public HashTable(){
		symbolToTemp=new HashMap<Access, Location>();
	}
	
	public void insertSymbol(Access access,Location location){
		symbolToTemp.put(access, location);
	}
	
	public boolean isExist(Access access){
		return symbolToTemp.containsKey(access);
	}
	
	public Location getLocation(Access access){
		return symbolToTemp.get(access);
	}

}
