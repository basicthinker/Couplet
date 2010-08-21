package canto.c1.x86;

import java.io.IOException;

/**
 * X86目标码的伪指令（可用于以字符串构造任意的X86目标码） 
 */
public class Pseudo extends Instruction {

	/** 指令内容 */
	private final String code;
	
	/**
	 * 构造一条伪指令
	 * @param code 指令的字符串
	 */
	public Pseudo(String code) {
		this.code = code;
	}

	@Override
	public void accept(X86Visitor visitor) throws IOException {
		visitor.visit(this);
	}

	@Override
	public int getTCType() {
		return PSEUDO;
	}

	/**
	 * 获取指令内容
	 * @return 指令内容
	 */
	public String getCode() {
		return code;
	}
	
}
