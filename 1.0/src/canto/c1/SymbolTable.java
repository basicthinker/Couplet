package canto.c1;

import java.util.HashMap;
import java.util.Map;

import canto.c1.ic.Location;

/**
 * @author Goodness
 * 继承自符号表的C1的名值对照表
 */
public class SymbolTable implements canto.SymbolTable {
	
	/**变量名和临时变量的对照表*/
	
	private Map<String, Location> symbolToTemp;
	
	/**
	 * 符号表的构造函数，初始化一个对照表
	 */
	public SymbolTable(){
		symbolToTemp=new HashMap<String, Location>();
	}
	
	public void insertSymbol(String name,Location location){
		symbolToTemp.put(name, location);
	}
	
	public boolean isExist(String name){
		return symbolToTemp.containsKey(name);
	}
	
	public Location getLocation(String name){
		return symbolToTemp.get(name);
	}

}
