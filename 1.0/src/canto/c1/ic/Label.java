package canto.c1.ic;

/**
 * 中间代码的标号指令
 */
public class Label extends Instruction {

	/** 统计标号的数目 */
	private static int count = 0;
	
	/** 存储标号的编号 */
	private final int id;
	
	/**
	 * 构造一个中间代码的标号
	 */
	public Label() {
		this.id = ++count;
	}

	@Override
	public void accept(ICVisitor visitor) throws Exception {
		visitor.visit(this);
	}

	@Override
	public int getICType() {
		return LABEL;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null) return false;
		if (!(obj instanceof Label)) return false;
		if (id != ((Label) obj).getID()) return false;
		return true;
	}

	@Override
	public String toString() {
		return "L" + id;
	}

	/**
	 * 获取标号的编号
	 * @return 标号的编号
	 */
	public int getID() {
		return id;
	}
	
}
