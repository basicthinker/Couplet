package canto.c1.x86;

public class MOV extends Instruction {

	private final Location dst;
	
	private final Operand src;

	public MOV(Location dst, Operand src) {
		this.dst = dst;
		this.src = src;
	}

	@Override
	public Object accept(X86Visitor visitor) {
		return visitor.visit(this);
	}

	@Override
	public int getTCType() {
		return MOV;
	}

	public Location getDst() {
		return dst;
	}

	public Operand getSrc() {
		return src;
	}
	
}
