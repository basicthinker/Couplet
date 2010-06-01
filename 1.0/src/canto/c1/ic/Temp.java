package canto.c1.ic;

/**
 * 中间代码的临时变量操作数
 */
public class Temp extends Location {
	
	private static int count = 0;
	
	private final int id;
	
	public Temp() {
		this.id = ++count;
	}
	
	@Override
	public void accept(ICVisitor visitor) throws Exception {
		visitor.visit(this);
	}

	@Override
	public int getICType() {
		return TEMP;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null) return false;
		if (!(obj instanceof Temp)) return false;
		if (id != ((Temp) obj).getID()) return false;
		return true;
	}
	
	public int getID() {
		return id;
	}

	@Override
	public String getName() {
		return "t"+id;
	}

}
