package canto.c1.x86;

/**
 * X86目标码的访问者接口
 */
public interface X86Visitor {
	
	public Object visit(Program tc);
	public Object visit(DataSegment tc);
	public Object visit(CodeSegment tc);
	public Object visit(DataDefine tc);
	public Object visit(Label tc);
	public Object visit(MOV tc);
	public Object visit(PUSH tc);
	public Object visit(POP tc);
	public Object visit(ADD tc);
	public Object visit(SUB tc);
	public Object visit(IMUL tc);
	public Object visit(NEG tc);
	public Object visit(CMP tc);
	public Object visit(JMP tc);
	public Object visit(JE tc);
	public Object visit(JNE tc);
	public Object visit(JG tc);
	public Object visit(JGE tc);
	public Object visit(JL tc);
	public Object visit(JLE tc);
	public Object visit(InInteger tc);
	public Object visit(OutInteger tc);
	public Object visit(Immediate tc);
	public Object visit(Symbol tc);
	public Object visit(Register tc);
	public Object visit(Pseudo tc);
	public Object visit(DataType tc);
		
}
