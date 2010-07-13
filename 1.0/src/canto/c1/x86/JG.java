package canto.c1.x86;

public class JG extends Jump {

	public JG(Label target) {
		super(target);
	}
	
	@Override
	public Object accept(X86Visitor visitor) {
		return visitor.visit(this);
	}
	
	@Override
	public int getTCType() {
		return JG;
	}

}
