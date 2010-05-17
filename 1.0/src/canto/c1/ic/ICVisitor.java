package canto.c1.ic;

/**
 * 中间代码的访问者
 */
public interface ICVisitor {
	
	public void visit(InstructionList ic) throws Exception;
	public void visit(Label ic) throws Exception;
	public void visit(CondJump ic) throws Exception;
	public void visit(UncondJump ic) throws Exception;
	public void visit(Move ic) throws Exception;
	public void visit(Add ic) throws Exception;
	public void visit(Sub ic) throws Exception;
	public void visit(Mul ic) throws Exception;
	public void visit(Neg ic) throws Exception;
	public void visit(Temp ic) throws Exception;
	public void visit(IntegerLiteral ic) throws Exception;
	
}
