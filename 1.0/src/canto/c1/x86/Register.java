package canto.c1.x86;

public class Register extends Location {

	public static int EAX = 0;
	public static int EBX = 1;
	public static int ECX = 2;
	public static int EDX = 3;
	public static int EBP = 4;
	public static int ESP = 5;
	public static int EDI = 6;
	public static int ESI = 7;
	
	private final int regNum;
	
	static String[] regMap;
	
	static {
		regMap = new String[8];
	}
	
	public Register(int regNum) {
		this.regNum = regNum;
	}

	@Override
	public void accept(X86Visitor visitor) {
		visitor.visit(this);
	}

	@Override
	public int getTCType() {
		return REGISTER;
	}

	public int getRegNum() {
		return regNum;
	}
	
	public static Register assign(Symbol symbol) {
		// TODO 为一个符号分配寄存器
		return null;
	}
	
	public static Register assign(Immediate imme) {
		// TODO 为一个立即数分配寄存器
		return null;
	}
	
	private static Register assign() {
		// TODO 寄存器分配算法
		return null;
	}

}
