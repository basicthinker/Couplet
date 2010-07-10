package canto.c1.x86;

public class JGE extends Jump {

	public JGE(LABEL target) {
		super(target);
	}
	
	@Override
	public void accept(X86Visitor visitor) {
		visitor.visit(this);
	}
	
	@Override
	public int getTCType() {
		return JGE;
	}

}
