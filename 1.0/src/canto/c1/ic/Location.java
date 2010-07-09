package canto.c1.ic;

public abstract class Location extends Operand {

	protected static int count = 0;
	
	protected final int id;

	public Location() {
		this.id = count++;
	}
	
	public int getID() {
		return id;
	}
	
}
