package canto.c1.x86;

public class JL extends Jump {

	public JL(Label target) {
		super(target);
	}
	
	@Override
	public Object accept(X86Visitor visitor) {
		return visitor.visit(this);
	}
	
	@Override
	public int getTCType() {
		return JL;
	}

}
