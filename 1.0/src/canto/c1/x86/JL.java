package canto.c1.x86;

public class JL extends Jump {

	public JL(LABEL target) {
		super(target);
	}
	
	@Override
	public void accept(X86Visitor visitor) {
		visitor.visit(this);
	}
	
	@Override
	public int getTCType() {
		return JL;
	}

}
