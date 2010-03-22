package canto.c1;

import java.util.HashSet;
import java.util.Hashtable;
import java.util.Set;
import java.util.Stack;

/**
 * 通用的栈式哈希符号表
 *   采用哈希表对符号字符串进行映射
 *   每个哈希表的位置上采用栈式存储嵌套作用域上的重名符号
 */
public class SymbolTable implements canto.SymbolTable {

	/** 存储符号信息的哈希表 */
	private Hashtable<String, Stack<Object>> table;
	
	/** 存储嵌套的各作用域的局部符号的栈 */
	private Stack<Set<String>> symbolSetStack;
	
	/** 构造一个符号表 */
	public SymbolTable() {
		table = new Hashtable<String, Stack<Object>>();
		symbolSetStack = new Stack<Set<String>>();
		enterScope();
	}
	
	@Override
	public void enterScope() {
		symbolSetStack.push(new HashSet<String>());
	}

	@Override
	public void exitScope() {
		for (String symbol : symbolSetStack.pop()) {
			table.get(symbol).pop();
		}
	}

	@Override
	public Object get(String name) {
		Stack<Object> stack = table.get(name);
		if (stack != null) {
			return table.get(name).peek();
		} else {
			return null;
		}
	}

	@Override
	public boolean put(String name, Object info) {
		Set<String> symbolSet = symbolSetStack.peek();
		if (!symbolSet.contains(name)) {
			symbolSet.add(name);
			Stack<Object> stack = table.get(name);
			if (stack == null) {
				stack = new Stack<Object>();
				table.put(name, stack);
			}
			stack.push(info);
			return true;
		} else {
			return false;
		}
	}

}
