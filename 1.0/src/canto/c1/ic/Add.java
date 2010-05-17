package canto.c1.ic;

/**
 * 中间代码的加法指令
 */
public class Add extends Arithmetic {

	private final Operand operand1;
	
	private final Operand operand2;

	private final Operand result;
	
	public Add(Operand operand1, Operand operand2, Operand result) {
		this.operand1 = operand1;
		this.operand2 = operand2;
		this.result = result;
	}

	@Override
	public void accept(ICVisitor visitor) throws Exception {
		visitor.visit(this);		
	}

	@Override
	public int getICType() {
		return ADD;
	}
	
	public Operand getOperand1() {
		return operand1;
	}

	public Operand getOperand2() {
		return operand2;
	}

	public Operand getResult() {
		return result;
	}

}
