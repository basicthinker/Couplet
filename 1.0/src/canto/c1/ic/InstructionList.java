package canto.c1.ic;

import java.util.LinkedList;
import java.util.List;

/**
 * 中间代码的指令序列
 */
public class InstructionList extends IntermediateCode {

	private List<Instruction> list;
	
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
	
	public void addInstruction(Instruction instruction) {
		list.add(instruction);
	}

	public List<Instruction> getList() {
		return list;
	}

}
