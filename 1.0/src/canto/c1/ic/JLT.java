package canto.c1.ic;

/**
 * 中间代码的小于跳转指令（当第一个操作数小于第二个操作数时跳转到目的地）
 */
public class JLT extends CJump {

	/**
	 * 构造一条小于跳转的中间代码
	 * @see canto.c1.ic.CJump#CJump(Operand, Operand, Label)
	 */
	public JLT(Operand operand1, Operand operand2, Label target) {
		super(operand1, operand2, target);
	}

	@Override
	public void accept(ICVisitor visitor) throws Exception {
		visitor.visit(this);
	}

	@Override
	public int getICType() {
		return LT_JUMP;
	}

}
