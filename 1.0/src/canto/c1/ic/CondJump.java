package canto.c1.ic;

/**
 * 中间代码的有条件转移指令的基类
 */
public abstract class CondJump extends Jump {
	
	private final Operand operand1;
	
	private final Operand operand2;
	
	public CondJump(int relation, Operand operand1, Operand operand2,
			Label target) {
		super(target);
		this.operand1 = operand1;
		this.operand2 = operand2;
	}

	public Operand getOperand1() {
		return operand1;
	}

	public Operand getOperand2() {
		return operand2;
	}

}
