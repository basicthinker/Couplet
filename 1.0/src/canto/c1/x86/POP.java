package canto.c1.x86;

public class POP extends Instruction {

	private final Location dst;
	
	public POP(Location dst) {
		this.dst = dst;
	}

	@Override
	public Object accept(X86Visitor visitor) {
		return visitor.visit(this);
	}

	@Override
	public int getTCType() {
		return POP;
	}

	public Location getDst() {
		return dst;
	}

}
