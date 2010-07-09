package canto.c1.x86;

public abstract class Jump extends Instruction {

	public Jump(LABEL target) {
		this.target = target;
	}

	protected final LABEL target; 
	
}
