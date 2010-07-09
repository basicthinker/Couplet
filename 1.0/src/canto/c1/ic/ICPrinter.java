/**
 * 
 */
package canto.c1.ic;

import java.util.List;

/**
 * @author Goodness
 * 打印中间代码的类
 */
public class ICPrinter implements ICVisitor {

	@Override
	public void visit(InstructionList ic) throws Exception {
		List<Instruction> icList=ic.getList();
		for(Instruction instruction: icList){
			instruction.accept(this);
		}
	}

	@Override
	public void visit(Label ic) throws Exception {
		System.out.println(ic+":");
	}

	@Override
	public void visit(In ic) throws Exception {
		System.out.println("In "+ic.getDst());
	}

	@Override
	public void visit(Out ic) throws Exception {
		System.out.println("Out "+ic.getSrc());
	}

	@Override
	public void visit(Goto ic) throws Exception {
		System.out.println("Goto "+ic.getTarget());
	}

	@Override
	public void visit(JEQ ic) throws Exception {
		System.out.println("JEQ "+ic.getOperand1()+", "
				+ic.getOperand2()+", "+ic.getTarget());
	}

	@Override
	public void visit(JNE ic) throws Exception {
		System.out.println("JNE "+ic.getOperand1()+", "
				+ic.getOperand2()+", "+ic.getTarget());
	}

	@Override
	public void visit(JLT ic) throws Exception {
		System.out.println("JLT "+ic.getOperand1()+", "
				+ic.getOperand2()+", "+ic.getTarget());
	}

	@Override
	public void visit(JLE ic) throws Exception {
		System.out.println("JLE "+ic.getOperand1()+", "
				+ic.getOperand2()+", "+ic.getTarget());
	}

	@Override
	public void visit(JGT ic) throws Exception {
		System.out.println("JGT "+ic.getOperand1()+", "
				+ic.getOperand2()+", "+ic.getTarget());
	}

	@Override
	public void visit(JGE ic) throws Exception {
		System.out.println("JGE "+ic.getOperand1()+", "
				+ic.getOperand2()+", "+ic.getTarget());
	}

	@Override
	public void visit(Mov ic) throws Exception {
		System.out.println("Mov "+ic.getSrc()+", "+ic.getDst());
	}

	@Override
	public void visit(Add ic) throws Exception {
		System.out.println("Add "+ic.getSrc1()+", "
				+ic.getSrc2()+", "
				+ic.getDst());
	}

	@Override
	public void visit(Sub ic) throws Exception {
		System.out.println("Sub "+ic.getSrc1()+", "
				+ic.getSrc2()+", "
				+ic.getDst());
	}

	@Override
	public void visit(Mul ic) throws Exception {
		System.out.println("Mul "+ic.getSrc1()+", "
				+ic.getSrc2()+", "
				+ic.getDst());
	}

	@Override
	public void visit(Neg ic) throws Exception {
		System.out.println("Neg "+ic.getSrc()+", "+ic.getDst());
	}

	@Override
	public void visit(Temp ic) throws Exception {
		System.out.println(ic);
	}

	@Override
	public void visit(IntegerLiteral ic) throws Exception {
		System.out.println(ic);
	}

	@Override
	public void visit(Variable ic) throws Exception {
		// TODO Auto-generated method stub
		
	}

}
