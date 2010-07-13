package canto.c1.x86;

public class JLE extends Jump {

	public JLE(Label target) {
		super(target);
	}
	
	@Override
	public Object accept(X86Visitor visitor) {
		return visitor.visit(this);
	}
	
	@Override
	public int getTCType() {
		// TODO Auto-generated method stub
		return JLE;
	}

}
