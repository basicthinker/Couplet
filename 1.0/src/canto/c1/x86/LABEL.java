package canto.c1.x86;

public class LABEL extends Instruction {

	private final String name;
	
	public LABEL(String name) {
		this.name = name;
	}

	@Override
	public void accept(X86Visitor visitor) {
		visitor.visit(this);
	}
	
	@Override
	public int getTCType() {
		return LABEL;
	}

	public String getName() {
		return name;
	}

}
