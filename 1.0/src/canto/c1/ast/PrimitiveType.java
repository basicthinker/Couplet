package canto.c1.ast;

/**
 * 原始类型结点
 */
public class PrimitiveType extends Type {

	/** 定义原始类型编码常量 */
	public static final int INTEGER = 1;
	
	/** 原始类型编码 */
	private final int code;
	
	/**
	 * 构造一个原始类型结点
	 * @param type 原始类型编码
	 */
	public PrimitiveType(int code) {
		this.code = code;
	}

	@Override
	public void accept(ASTVisitor visitor) {
		visitor.visit(this);
	}

	@Override
	public int getNodeType() {
		return PRIMITIVE_TYPE;
	}

	/**
	 * 获取原始类型编码
	 * @return 原始类型编码
	 */
	public int getTypeCode() {
		return code;
	}

}
