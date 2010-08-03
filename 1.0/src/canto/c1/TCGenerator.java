package canto.c1;

import canto.IntermediateCode;
import canto.TargetCode;
import canto.c1.ic.Add;
import canto.c1.ic.Goto;
import canto.c1.ic.ICVisitor;
import canto.c1.ic.In;
import canto.c1.ic.InstructionList;
import canto.c1.ic.IntegerLiteral;
import canto.c1.ic.JEQ;
import canto.c1.ic.JGE;
import canto.c1.ic.JGT;
import canto.c1.ic.JLE;
import canto.c1.ic.JLT;
import canto.c1.ic.JNE;
import canto.c1.ic.Label;
import canto.c1.ic.Location;
import canto.c1.ic.Mov;
import canto.c1.ic.Mul;
import canto.c1.ic.Neg;
import canto.c1.ic.Out;
import canto.c1.ic.Sub;
import canto.c1.ic.Temp;
import canto.c1.ic.Variable;
import canto.c1.x86.CodeSegment;
import canto.c1.x86.DataSegment;
import canto.c1.x86.Immediate;
import canto.c1.x86.Memory;
import canto.c1.x86.Register;
import canto.c1.x86.Symbol;

public class TCGenerator implements canto.TCGenerator,ICVisitor  {


	/**输入的中间代码序列*/
	private canto.IntermediateCode ic;
	
	/**数据段*/
	private DataSegment dataSegment;
	
	/**代码段*/
	private CodeSegment codeSegment;
	
	static String[] regMap;

	public TCGenerator(){
		dataSegment=new DataSegment();
		codeSegment=new CodeSegment();
	}
	
	@Override
	public void setIC(IntermediateCode ic) {
		// TODO Auto-generated method stub

	}

	@Override
	public TargetCode generateTC() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TargetCode getTC() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void visit(InstructionList ic) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(Label ic) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(In ic) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(Out ic) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(Goto ic) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(JEQ ic) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(JNE ic) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(JLT ic) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(JLE ic) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(JGT ic) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(JGE ic) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(Mov ic) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(Add ic) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(Sub ic) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(Mul ic) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(Neg ic) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(Variable ic) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(Temp ic) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(IntegerLiteral ic) throws Exception {
		// TODO Auto-generated method stub
		
	}
	
	public static Register assign(Memory memory) {
		// TODO 为一个符号分配寄存器
		return null;
	}
	
	public static Register assign(Immediate imme) {
		// TODO 为一个立即数分配寄存器
		return null;
	}
	
	private static Register assign() {
		// TODO 寄存器分配算法
		return null;
	}
	
}
