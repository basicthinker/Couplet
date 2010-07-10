package canto.c1.x86;

public class Immediate extends Operand {
	
	private final Integer value;
	
	public Immediate(Integer value) {
		this.value = value;
	}

	@Override
	public void accept(X86Visitor visitor) {
		visitor.visit(this);
	}
	
	@Override
	public int getTCType() {
		// TODO Auto-generated method stub
		return IMMEDIATE;
	}

	public Integer getValue() {
		return value;
	}

}
