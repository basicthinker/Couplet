package canto.c1.ic;

import java.util.LinkedList;
import java.util.List;

/**
 * 中间代码的指令序列
 */
public class InstructionList implements IntermediateCode {

	/** 存储一序列中间代码指令的列表 */
	private List<Instruction> list;
	
	/**
	 * 构造一个中间代码指令序列
	 */
	public InstructionList() {
		list = new LinkedList<Instruction>();
	}

	@Override
	public void accept(ICVisitor visitor) throws Exception {
		visitor.visit(this);
	}

	@Override
	public int getICType() {
		return INSTRUCTION_LIST;
	}
	
	/**
	 * 向中间代码指令序列的末尾添加一条指令
	 * @param instruction 新增的指令
	 */
	public void add(Instruction instruction) {
		list.add(instruction);
	}

	/**
	 * 获取中间代码指令的列表
	 * @return 中间代码指令的列表
	 */
	public List<Instruction> getList() {
		return list;
	}

}
