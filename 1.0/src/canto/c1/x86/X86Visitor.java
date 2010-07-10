package canto.c1.x86;

public interface X86Visitor {
	
	public void visit(Program tc);
	public void visit(DataSegment tc);
	public void visit(CodeSegment tc);
	public void visit(DataDefine tc);
	public void visit(LABEL tc);
	public void visit(MOV tc);
	public void visit(PUSH tc);
	public void visit(POP tc);
	public void visit(ADD tc);
	public void visit(SUB tc);
	public void visit(IMUL tc);
	public void visit(NEG tc);
	public void visit(CMP tc);
	public void visit(JMP tc);
	public void visit(JE tc);
	public void visit(JNE tc);
	public void visit(JG tc);
	public void visit(JGE tc);
	public void visit(JL tc);
	public void visit(JLE tc);
	public void visit(InInteger tc);
	public void visit(OutInteger tc);
	public void visit(Immediate tc);
	public void visit(Symbol tc);
	public void visit(Register tc);
	public void visit(Pseudo tc);
		
}
