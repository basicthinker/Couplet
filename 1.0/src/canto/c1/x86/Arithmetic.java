package canto.c1.x86;

public abstract class Arithmetic extends Instruction {

	protected final Location dst;
	
	public Arithmetic(Location dst) {
		this.dst = dst;
	}
	
	Location getDst() {
		return dst;
	}
	
}
