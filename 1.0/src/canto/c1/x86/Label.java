package canto.c1.x86;

/**
 * X86目标码的标号语句
 */
public class Label extends Instruction {

	/** 存储标号名 */
	private final String name;
	
	/** 构造一个标号 */
	public Label(String name) {
		this.name = name;
	}

	@Override
	public void accept(X86Visitor visitor) throws Exception {
		visitor.visit(this);
	}
	
	@Override
	public int getTCType() {
		return LABEL;
	}

	/**
	 * 获取标号名
	 * @return 标号名
	 */
	public String getName() {
		return name;
	}

}
