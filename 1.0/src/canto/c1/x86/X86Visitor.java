package canto.c1.x86;

/**
 * X86目标码的访问者接口
 */
public interface X86Visitor {
	
	public void visit(Program tc) throws Exception;
	public void visit(DataSegment tc) throws Exception;
	public void visit(CodeSegment tc) throws Exception;
	public void visit(DataDefine tc) throws Exception;
	public void visit(Label tc) throws Exception;
	public void visit(MOV tc) throws Exception;
	public void visit(PUSH tc) throws Exception;
	public void visit(POP tc) throws Exception;
	public void visit(ADD tc) throws Exception;
	public void visit(SUB tc) throws Exception;
	public void visit(IMUL tc) throws Exception;
	public void visit(NEG tc) throws Exception;
	public void visit(CMP tc) throws Exception;
	public void visit(JMP tc) throws Exception;
	public void visit(JE tc) throws Exception;
	public void visit(JNE tc) throws Exception;
	public void visit(JG tc) throws Exception;
	public void visit(JGE tc) throws Exception;
	public void visit(JL tc) throws Exception;
	public void visit(JLE tc) throws Exception;
	public void visit(InInteger tc) throws Exception;
	public void visit(OutInteger tc) throws Exception;
	public void visit(Immediate tc) throws Exception;
	public void visit(Symbol tc) throws Exception;
	public void visit(Register tc) throws Exception;
	public void visit(Pseudo tc) throws Exception;
	public void visit(DataType tc) throws Exception;
		
}
