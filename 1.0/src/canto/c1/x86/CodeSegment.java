package canto.c1.x86;

import java.util.LinkedList;
import java.util.List;

public class CodeSegment extends X86TargetCode {

	private List<Instruction> instructionList;

	public CodeSegment() {
		this.instructionList = new LinkedList<Instruction>();
	}
	
	@Override
	public Object accept(X86Visitor visitor) {
		return visitor.visit(this);		
	}
	
	@Override
	public int getTCType() {
		return CODE_SEGMENT;
	}
	
	public List<Instruction> getInstructionList() {
		return instructionList;
	}

	public void add(Instruction instruction) {
		instructionList.add(instruction);
	}

}
