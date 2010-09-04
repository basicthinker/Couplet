package canto.c1.x86;

/**
 * 表示整个程序的X86目标码的数据结构
 */
public class Program implements X86TargetCode {

	/** 存储程序的数据段 */
	private DataSegment dataSegment;
	
	/** 存储程序的代码段 */
	private CodeSegment codeSegment;

	@Override
	public void accept(X86Visitor visitor) throws Exception {
		visitor.visit(this);
	}

	@Override
	public int getTCType() {
		return PROGRAM;
	}

	/**
	 * 获取程序的数据段
	 * @return 数据段
	 */
	public DataSegment getDataSegment() {
		return dataSegment;
	}

	/**
	 * 设置程序的数据段
	 * @param dataSegment 数据段
	 */
	public void setDataSegment(DataSegment dataSegment) {
		this.dataSegment = dataSegment;
	}

	/**
	 * 获取程序的代码段
	 * @return 代码段
	 */
	public CodeSegment getCodeSegment() {
		return codeSegment;
	}

	/**
	 * 设置程序的代码段
	 * @param codeSegment 代码段
	 */
	public void setCodeSegment(CodeSegment codeSegment) {
		this.codeSegment = codeSegment;
	}

}
