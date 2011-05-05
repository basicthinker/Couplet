package couplet.c1.ic;

/**
 * 中间代码的变量操作数（此类变量为在源代码中声明的变量）
 */
public class Variable extends Location {
	
	/** 统计变量的数目 */
	private static int count = 0;
	
	/** 存储变量的编号 */
	private final int id;
	
	/**
	 * 构造一个中间代码的变量
	 */
	public Variable() {
		this.id = ++count;
	}
	
	@Override
	public int getICType() {
		return VARIABLE;
	}
	
	@Override
	public void accept(ICVisitor visitor) throws Exception {
		visitor.visit(this);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null) return false;
		if (!(obj instanceof Variable)) return false;
		if (id != ((Variable) obj).getID()) return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "v"+id;
	}

	/**
	 * 获取变量的编号
	 * @return 变量的编号
	 */
	public int getID() {
		return id;
	}
	
}
