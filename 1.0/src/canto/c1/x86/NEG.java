package canto.c1.x86;

public class NEG extends UnaryArithmetic {

	public NEG(Location dst) {
		super(dst);
	}

	@Override
	public Object accept(X86Visitor visitor) {
		return visitor.visit(this);
	}

	@Override
	public int getTCType() {
		return NEG;
	}

}
