package canto.c1.x86;

public class JLE extends Jump {

	public JLE(LABEL target) {
		super(target);
	}
	
	@Override
	public void accept(X86Visitor visitor) {
		visitor.visit(this);
	}
	
	@Override
	public int getTCType() {
		// TODO Auto-generated method stub
		return JLE;
	}

}
