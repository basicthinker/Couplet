package canto.c1.ic;

/**
 * 中间代码转移指令的基类
 */
public abstract class Jump extends Instruction {
	
	private final Label target;
	
	public Jump(Label target) {
		this.target = target;
	}

	public Label getTarget() {
		return target;
	}
	
}
