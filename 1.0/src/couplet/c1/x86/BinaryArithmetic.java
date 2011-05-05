package couplet.c1.x86;

/**
 * X86目标码的所有二元运算指令的基类
 */
public abstract class BinaryArithmetic extends Arithmetic {

	/** 存储参与运算的源操作数 */
	public final Operand src;
	
	/**
	 * 构造一条二元运算指令
	 * @param dst 目的操作位置
	 * @param src 源操作数
	 */
	public BinaryArithmetic(Location dst, Operand src) {
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
