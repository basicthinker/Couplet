package canto.c1.ast;

/**
 * 标识符结点
 */
public class Identifier extends Access {

	/** 标识符的名称 */
	private final String name;
	
	/**
	 * 构造一个标识符
	 * @param name 标识符的名称
	 */
	public Identifier(String name, int line, int column) {
		super(line, column);
		this.name = name;
	}

	@Override
	public void accept(ASTVisitor visitor) throws Exception {
		visitor.visit(this);
	}

	@Override
	public int getNodeType() {
		return IDENTIFIER;
	}

	/**
	 * 获取标识符的名称
	 * @return 标识符的名称
	 */
	public String getName() {
		return name;
	}

}
