package canto.c1.x86;

public class Symbol extends Memory {

	private final String name;
	
	public Symbol(String name) {
		this.name = name;
	}

	@Override
	public void accept(X86Visitor visitor) {
		visitor.visit(this);
	}

	@Override
	public int getTCType() {
		return SYMBOL;
	}

	public String getName() {
		return name;
	}

}
