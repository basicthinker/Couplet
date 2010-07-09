package canto.c1.ic;

public class Variable extends Location {

	public Variable() {
		super();
	}
	
	@Override
	public int getICType() {
		return VARIABLE;
	}

	@Override
	public void accept(ICVisitor visitor) throws Exception {
		visitor.visit(this);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null) return false;
		if (!(obj instanceof Variable)) return false;
		if (id != ((Variable) obj).getID()) return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "v"+id;
	}

}
