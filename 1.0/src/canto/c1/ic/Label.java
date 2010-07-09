package canto.c1.ic;

/**
 * 中间代码的标号指令
 */
public class Label extends Instruction {

	private static int count = 0;
	
	private final int id;
	
	public Label() {
		this.id = ++count;
	}

	@Override
	public void accept(ICVisitor visitor) throws Exception {
		visitor.visit(this);
	}

	@Override
	public int getICType() {
		return LABEL;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null) return false;
		if (!(obj instanceof Label)) return false;
		if (id != ((Label) obj).getID()) return false;
		return true;
	}

	public int getID() {
		return id;
	}
	
	@Override
	public String toString() {
		return "L" + id;
	}

}
