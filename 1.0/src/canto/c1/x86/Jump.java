package canto.c1.x86;

public abstract class Jump extends Instruction {

	public Jump(Label target) {
		this.target = target;
	}

	protected final Label target;

	public Label getTarget() {
		return target;
	} 
	
}
