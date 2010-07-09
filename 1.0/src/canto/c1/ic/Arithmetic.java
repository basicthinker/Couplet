package canto.c1.ic;

/**
 * 中间代码算术运算指令的基类
 */
public abstract class Arithmetic extends Instruction {

	private final Location dst;

	public Arithmetic(Location dst) {
		this.dst = dst;
	}

	public Operand getDst() {
		return dst;
	}
	
}
