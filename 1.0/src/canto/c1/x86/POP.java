package canto.c1.x86;

public class POP extends Instruction {

	private final Location dst;
	
	public POP(Location dst) {
		this.dst = dst;
	}

	@Override
	public int getTCType() {
		// TODO Auto-generated method stub
		return 0;
	}

	public Location getDst() {
		return dst;
	}

}
