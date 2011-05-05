package couplet.c1.ast;

public abstract class Location extends Expression {
	
	/** 标识符的名称 */
	private final String name;
	
	public Location(String name, int line, int column) {
		super(line, column);
		this.name = name;
	}
	
	/**
	 * 获取标识符的名称
	 * @return 标识符的名称
	 */
	public String getName() {
		return name;
	}
	
}
