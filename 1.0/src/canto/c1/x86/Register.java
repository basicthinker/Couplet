package canto.c1.x86;

/**
 * X86目标码的寄存器操作数
 */
public class Register extends Location {

	/** 定义寄存器编号常量 */
	public static final int EAX = 0;
	public static final int EBX = 1;
	public static final int ECX = 2;
	public static final int EDX = 3;
	public static final int EBP = 4;
	public static final int ESP = 5;
	public static final int EDI = 6;
	public static final int ESI = 7;
	
	/** 存储寄存器编号 */
	private final int regNum;
	
	/**
	 * 构造一个寄存器操作数
	 * @param regNum 寄存器编号
	 */
	public Register(int regNum) {
		this.regNum = regNum;
	}

	@Override
	public Object accept(X86Visitor visitor) {
		return visitor.visit(this);
	}

	@Override
	public int getTCType() {
		return REGISTER;
	}

	/**
	 * 获取寄存器编号
	 * @return 寄存器编号
	 */
	public int getRegNum() {
		return regNum;
	}

}
