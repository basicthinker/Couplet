package canto.c1.x86;

public class Label extends Instruction {

	private final String name;
	
	public Label(String name) {
		this.name = name;
	}

	@Override
	public Object accept(X86Visitor visitor) {
		return visitor.visit(this);
	}
	
	@Override
	public int getTCType() {
		return LABEL;
	}

	public String getName() {
		return name;
	}

}
