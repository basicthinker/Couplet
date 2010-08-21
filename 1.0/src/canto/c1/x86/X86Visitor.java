package canto.c1.x86;

import java.io.IOException;

/**
 * X86目标码的访问者接口
 */
public interface X86Visitor {
	
	public void visit(Program tc) throws IOException;
	public void visit(DataSegment tc) throws IOException;
	public void visit(CodeSegment tc) throws IOException;
	public void visit(DataDefine tc) throws IOException;
	public void visit(Label tc) throws IOException;
	public void visit(MOV tc) throws IOException;
	public void visit(PUSH tc) throws IOException;
	public void visit(POP tc) throws IOException;
	public void visit(ADD tc) throws IOException;
	public void visit(SUB tc) throws IOException;
	public void visit(IMUL tc) throws IOException;
	public void visit(NEG tc) throws IOException;
	public void visit(CMP tc) throws IOException;
	public void visit(JMP tc) throws IOException;
	public void visit(JE tc) throws IOException;
	public void visit(JNE tc) throws IOException;
	public void visit(JG tc) throws IOException;
	public void visit(JGE tc) throws IOException;
	public void visit(JL tc) throws IOException;
	public void visit(JLE tc) throws IOException;
	public void visit(InInteger tc) throws IOException;
	public void visit(OutInteger tc) throws IOException;
	public void visit(Immediate tc) throws IOException;
	public void visit(Symbol tc) throws IOException;
	public void visit(Register tc) throws IOException;
	public void visit(Pseudo tc) throws IOException;
	public void visit(DataType tc) throws IOException;
		
}
