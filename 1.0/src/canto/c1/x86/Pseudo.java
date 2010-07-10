package canto.c1.x86;

public class Pseudo extends Instruction {

	private final String code;
	
	public Pseudo(String code) {
		this.code = code;
	}

	@Override
	public void accept(X86Visitor visitor) {
		visitor.visit(this);
	}

	@Override
	public int getTCType() {
		return PSEUDO;
	}

	public String getCode() {
		return code;
	}
	
}
