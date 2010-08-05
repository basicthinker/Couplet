package canto.c1.x86;

/**
 * X86目标码的CMP指令
 */
public class CMP extends Instruction {

	/** 参与比较的第一个操作数 */
	private final Operand operand1;

	/** 参与比较的第二个操作数 */
	private final Operand operand2;

	/** 构造一条CMP指令 */
	public CMP(Operand operand1, Operand operand2) {
		this.operand1 = operand1;
		this.operand2 = operand2;
	}
	
	@Override
	public Object accept(X86Visitor visitor) {
		return visitor.visit(this);		
	}

	@Override
	public int getTCType() {
		return CMP;
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
