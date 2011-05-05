package couplet.c1.ic;

/**
 * 中间代码的所有二元算术运算指令的基类
 */
public abstract class BinaryArithmetic extends Arithmetic {

	/** 存储第一个源操作数 */
	private final Operand src1;
	
	/** 存储第二个源操作数 */
	private final Operand src2;

	/**
	 * 构造一条二元算术运算的中间代码
	 * @param src1 第一个源操作数
	 * @param src2 第二个源操作数
	 * @param dst 运算结果的位置
	 */
	public BinaryArithmetic(Operand src1, Operand src2, Location dst) {
		super(dst);
		this.src1 = src1;
		this.src2 = src2;
	}

	/**
	 * 获取第一个源操作数
	 * @return 第一个源操作数
	 */
	public Operand getSrc1() {
		return src1;
	}

	/**
	 * 获取第二个源操作数
	 * @return 第二个源操作数
	 */
	public Operand getSrc2() {
		return src2;
	}
	
}
