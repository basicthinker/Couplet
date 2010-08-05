package canto.c1.x86;

/**
 * X86目标码的所有一元运算指令的基类
 */
public abstract class UnaryArithmetic extends Arithmetic {

	/**
	 * 构造一条一远运算指令
	 * @param dst 源操作数
	 */
	public UnaryArithmetic(Location dst) {
		super(dst);
	}

}
