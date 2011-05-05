package couplet.c1.ic;

/**
 * 中间代码的临时变量操作数（此类变量为表达式运算中产生的中间临时变量）
 */
public class Temp extends Location {
	
	/** 统计临时变量的数目 */
	private static int count = 0;
	
	/** 存储临时变量的编号 */
	private final int id;
	
	/**
	 * 构造一个临时变量
	 */
	public Temp() {
		this.id = ++count;
	}
	
	@Override
	public void accept(ICVisitor visitor) throws Exception {
		visitor.visit(this);
	}

	@Override
	public int getICType() {
		return TEMP;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null) return false;
		if (!(obj instanceof Temp)) return false;
		if (id != ((Temp) obj).getID()) return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "t"+id;
	}

	/**
	 * 获取临时变量的编号
	 * @return 临时变量的编号
	 */
	public int getID() {
		return id;
	}
	
}
