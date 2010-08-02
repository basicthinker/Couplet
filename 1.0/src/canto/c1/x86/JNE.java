package canto.c1.x86;

public class JNE extends Jump {

	public JNE(Label target) {
		super(target);
	}
	
	@Override
	public Object accept(X86Visitor visitor) {
		return visitor.visit(this);
	}
	
	@Override
	public int getTCType() {
		return JNE;
	}

}