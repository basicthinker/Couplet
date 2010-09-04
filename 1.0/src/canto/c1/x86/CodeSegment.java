package canto.c1.x86;

import java.util.LinkedList;
import java.util.List;

/**
 * X86目标码的代码段
 */
public class CodeSegment implements X86TargetCode {

	/** 存储指令序列的链表 */
	private List<Instruction> instructionList;

	/**
	 * 构造一个代码段
	 */
	public CodeSegment() {
		this.instructionList = new LinkedList<Instruction>();
	}
	
	@Override
	public void accept(X86Visitor visitor) throws Exception {
		visitor.visit(this);		
	}
	
	@Override
	public int getTCType() {
		return CODE_SEGMENT;
	}
	
	/**
	 * 获取指令序列链表
	 * @return 指令序列链表
	 */
	public List<Instruction> getInstructionList() {
		return instructionList;
	}

	/**
	 * 向指令序列末尾增加一条指令
	 * @param instruction 新增的指令
	 */
	public void add(Instruction instruction) {
		instructionList.add(instruction);
	}

}
