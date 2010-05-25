package canto.c1.ic;

/**
 * 中间代码算术运算指令的基类
 */
public abstract class Arithmetic extends Instruction {

	private final Location result;

	public Arithmetic(Location result) {
		this.result = result;
	}

	public Operand getResult() {
		return result;
	}
	
}
