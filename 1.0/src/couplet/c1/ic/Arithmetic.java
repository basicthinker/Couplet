package couplet.c1.ic;

/**
 * 中间代码的所有算术运算指令的基类
 */
public abstract class Arithmetic extends Instruction {

	/** 存储运算结果的位置 */
	private final Location result;

	/**
	 * 构造一条算术运算的中间代码
	 * @param result 运算结果的位置
	 */
	public Arithmetic(Location result) {
		this.result = result;
	}

	/**
	 * 获取运算结果的位置
	 * @return 获取运算结果的位置
	 */
	public Operand getResult() {
		return result;
	}
	
}
