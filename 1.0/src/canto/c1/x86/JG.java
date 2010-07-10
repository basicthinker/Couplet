package canto.c1.x86;

public class JG extends Jump {

	public JG(LABEL target) {
		super(target);
	}
	
	@Override
	public void accept(X86Visitor visitor) {
		visitor.visit(this);
	}
	
	@Override
	public int getTCType() {
		return JG;
	}

}
