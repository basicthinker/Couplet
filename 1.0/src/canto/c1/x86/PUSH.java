package canto.c1.x86;

public class PUSH extends Instruction {

	private final Operand src;
	
	public PUSH(Operand src) {
		this.src = src;
	}

	@Override
	public int getTCType() {
		// TODO Auto-generated method stub
		return 0;
	}

	public Operand getSrc() {
		return src;
	}

}
