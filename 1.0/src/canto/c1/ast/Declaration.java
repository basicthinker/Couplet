package canto.c1.ast;

/**
 * 声明结点
 */
public class Declaration extends Listable {

	/** 声明的类型 */
	private final Type type;
	
	/** 声明的标识符 */
	private final Identifier identifier;

	/**
	 * 构造一个声明
	 * @param type 声明的类型
	 * @param identifier 声明的标识符
	 */
	public Declaration(Type type, Identifier identifier) {
		this.type = type;
		this.identifier = identifier;
	}
	
	@Override
	public void accept(ASTVisitor visitor) {
		visitor.visit(this);
	}

	@Override
	public int getNodeType() {
		return DECLARATION;
	}

	/**
	 * 获取声明的类型
	 * @return 声明的类型
	 */
	public Type getType() {
		return type;
	}

	/**
	 * 获取声明的标识符
	 * @return 声明的标识符
	 */
	public Identifier getIdentifier() {
		return identifier;
	}

	
}
