package couplet.c1.ic;

/**
 * 中间代码的所有有条件转移指令的基类
 *   该类指令依据两个操作数的关系进行跳转（关系的类型由其子类的类型决定）
 */
public abstract class CJump extends Jump {
	
	/** 存储第一个操作数 */
	private final Operand operand1;
	
	/** 存储第二个操作数 */
	private final Operand operand2;
	
	/**
	 * 构造一条有条件跳转的中间代码
	 * @param operand1 第一个操作数
	 * @param operand2 第二个操作数
	 * @param target 跳转目的地
	 */
	public CJump(Operand operand1, Operand operand2, Label target) {
		super(target);
		this.operand1 = operand1;
		this.operand2 = operand2;
	}

	/**
	 * 获取第一个操作数
	 * @return 第一个操作数
	 */
	public Operand getOperand1() {
		return operand1;
	}

	/**
	 * 获取第二个操作数
	 * @return 第二个操作数
	 */
	public Operand getOperand2() {
		return operand2;
	}

}
