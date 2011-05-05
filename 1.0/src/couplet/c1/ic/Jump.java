package couplet.c1.ic;

/**
 * 中间代码转移指令的基类
 */
public abstract class Jump extends Instruction {
	
	/** 存储跳转的目的地 */
	private final Label target;
	
	/**
	 * 构造一条跳转指令的中间代码
	 * @param target 跳转的目的地
	 */
	public Jump(Label target) {
		this.target = target;
	}

	/**
	 * 获取跳转的目的地
	 * @return 跳转的目的地
	 */
	public Label getTarget() {
		return target;
	}
	
}
