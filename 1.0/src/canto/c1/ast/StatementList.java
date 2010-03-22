package canto.c1.ast;

import java.util.LinkedList;
import java.util.List;

/**
 * 语句列表结点（该列表中可存放声明和语句） 
 */
public class StatementList extends ASTNode {

	/** 用于存放声明和语句的List容器 */
	private final List<Listable> list;
	
	/**
	 * 构造一个语句列表结点
	 */
	public StatementList() {
		this.list = new LinkedList<Listable>();
	}

	@Override
	public void accept(ASTVisitor visitor) throws Exception {
		visitor.visit(this);
	}

	@Override
	public int getNodeType() {
		return STATEMENT_LIST;
	}
	
	/**
	 * 获取存放声明和语句的List容器
	 * @return 用于存放声明和语句的List容器
	 */
	public List<Listable> getList() {
		return list;
	}

	/**
	 * 向语句列表中加入一个元素
	 * @param item 一个可放入列表的元素（声明或语句）
	 */
	public void addListable(Listable item) {
		list.add(item);
	}

}
