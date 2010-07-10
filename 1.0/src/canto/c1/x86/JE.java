package canto.c1.x86;

public class JE extends Jump {

	public JE(LABEL target) {
		super(target);
	}
	
	@Override
	public void accept(X86Visitor visitor) {
		visitor.visit(this);
	}
	
	@Override
	public int getTCType() {
		return JE;
	}

}
