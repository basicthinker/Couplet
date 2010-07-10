package canto.c1.x86;

public class PUSH extends Instruction {

	private final Operand src;
	
	public PUSH(Operand src) {
		this.src = src;
	}

	@Override
	public void accept(X86Visitor visitor) {
		visitor.visit(this);
	}

	@Override
	public int getTCType() {
		return PUSH;
	}

	public Operand getSrc() {
		return src;
	}

}
