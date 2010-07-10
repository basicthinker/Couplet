package canto.c1.x86;

public class OutInteger extends Out {

	private final Operand src;

	public OutInteger(Operand src) {
		this.src = src;
	}
	
	@Override
	public void accept(X86Visitor visitor) {
		visitor.visit(this);
	}
	
	@Override
	public int getTCType() {
		return OUT_INTEGER;
	}

	public Operand getSrc() {
		return src;
	}

}
