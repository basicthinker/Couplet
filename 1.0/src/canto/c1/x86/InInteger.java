package canto.c1.x86;

public class InInteger extends In {

	private final Location dst;
	
	public InInteger(Location dst) {
		this.dst = dst;
	}

	@Override
	public Object accept(X86Visitor visitor) {
		return visitor.visit(this);
	}

	@Override
	public int getTCType() {
		return IN_INTEGER;
	}

	public Location getDst() {
		return dst;
	}

}
