package canto.c1.x86;

/**
 * X86目标码的所有跳转指令的基类
 */
public abstract class Jump extends Instruction {

	/** 存储跳转目的地 */
	protected final Label target;
	
	/**
	 * 构造一条跳转指令
	 * @param target 跳转目的地
	 */
	public Jump(Label target) {
		this.target = target;
	}

	/**
	 * 获取跳转目的地
	 * @return 跳转目的地
	 */
	public Label getTarget() {
		return target;
	} 
	
}
