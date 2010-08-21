package canto.c1.x86;

/**
 * X86目标码的所有算术运算指令的基类
 */
public abstract class Arithmetic extends Instruction {

	/** 存储运算结果的目的操作位置 */
	protected final Location dst;
	
	/**
	 * 构造一条算术运算指令
	 * @param dst 目的操作位置
	 */
	public Arithmetic(Location dst) {
		this.dst = dst;
	}
	
	/**
	 * 获取目的操作位置
	 * @return 目的操作位置
	 */
	public Location getDst() {
		return dst;
	}
	
}
