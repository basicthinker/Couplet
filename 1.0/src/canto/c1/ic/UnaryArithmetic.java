package canto.c1.ic;

/**
 * 中间代码的所有一元算术运算指令的基类
 */
public abstract class UnaryArithmetic extends Arithmetic {

	/** 存储源操作数 */
	private final Operand src;

	/**
	 * 构造一个中间代码的一元运算指令
	 * @param src 源操作数
	 * @param dst 运算结果的位置
	 */
	public UnaryArithmetic(Operand src, Location dst) {
		super(dst);
		this.src = src;
	}

	/**
	 * 获取源操作数
	 * @return 源操作数
	 */
	public Operand getSrc() {
		return src;
	}
	
}
