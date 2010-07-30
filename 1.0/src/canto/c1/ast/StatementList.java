package canto.c1.ast;

import java.util.LinkedList;
import java.util.List;

import canto.CantoException;

/**
 * 语句列表结点
 */
public class StatementList extends ASTNode {

	/** 用于存放语句的List容器 */
	private final List<Statement> list;
	
	/**
	 * 构造一个语句列表结点
	 */
	public StatementList(int line, int column) {
		super(line, column);
		this.list = new LinkedList<Statement>();
	}

	@Override
	public void accept(ASTVisitor visitor) throws CantoException {
		visitor.visit(this);
	}

	@Override
	public int getASTType() {
		return STATEMENT_LIST;
	}
	
	/**
	 * 获取存放语句的List容器
	 * @return 用于存放语句的List容器
	 */
	public List<Statement> getList() {
		return list;
	}

	/**
	 * 向语句列表中加入一个语句
	 * @param statement 待加入的语句
	 */
	public void addStatement(Statement statement) {
		list.add(statement);
		statement.setParent(this);
	}

}
