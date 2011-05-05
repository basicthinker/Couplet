package couplet.c1.ic;

/**
 * 中间代码的访问者
 */
public interface ICVisitor {
	
	public void visit(InstructionList ic) throws Exception;
	public void visit(Label ic) throws Exception;
	public void visit(In ic) throws Exception;
	public void visit(Out ic) throws Exception;
	public void visit(Goto ic) throws Exception;
	public void visit(JEQ ic) throws Exception;
	public void visit(JNE ic) throws Exception;
	public void visit(JLT ic) throws Exception;
	public void visit(JLE ic) throws Exception;
	public void visit(JGT ic) throws Exception;
	public void visit(JGE ic) throws Exception;
	public void visit(Mov ic) throws Exception;
	public void visit(Add ic) throws Exception;
	public void visit(Sub ic) throws Exception;
	public void visit(Mul ic) throws Exception;
	public void visit(Neg ic) throws Exception;
	public void visit(Variable ic) throws Exception;
	public void visit(Temp ic) throws Exception;
	public void visit(IntegerLiteral ic) throws Exception;
	
}
