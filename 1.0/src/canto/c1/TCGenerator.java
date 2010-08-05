package canto.c1;

import java.util.List;

import canto.c1.ic.IntermediateCode;
import canto.c1.ic.Add;
import canto.c1.ic.Goto;
import canto.c1.ic.ICVisitor;
import canto.c1.ic.In;
import canto.c1.ic.Instruction;
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

import canto.c1.x86.CMP;
import canto.c1.x86.CodeSegment;
import canto.c1.x86.DataDefine;
import canto.c1.x86.DataSegment;
import canto.c1.x86.DataType;
import canto.c1.x86.Immediate;
import canto.c1.x86.InInteger;
import canto.c1.x86.JE;
import canto.c1.x86.JMP;
import canto.c1.x86.Memory;
import canto.c1.x86.Operand;
import canto.c1.x86.OutInteger;
import canto.c1.x86.Program;
import canto.c1.x86.Register;
import canto.c1.x86.Symbol;

/**
 * c1的目标代码生成器
 * @author Goodness
 */

public class TCGenerator implements canto.TCGenerator,ICVisitor  {


	/**输入的中间代码序列*/
	private InstructionList ic;
	
	/**返回的目标代码的数据段*/
	private DataSegment dataSegment;

	/**返回的目标代码的代码段*/
	private CodeSegment codeSegment;

	
	/**返回的目标代码*/
	private Program program;
	
	static String[] regMap;
	SymbolTable<List<canto.c1.x86.Location>> symbolTable;

	public TCGenerator(){
		program=new Program();
		dataSegment=new DataSegment();
		codeSegment=new CodeSegment();
		symbolTable=new SymbolTable<List<canto.c1.x86.Location>>();
	}

	@Override
	public void setIC(canto.IntermediateCode ic) {
		this.ic=(InstructionList) ic;
	}

	@Override
	public canto.TargetCode generateTC() throws Exception {
		visit(ic);
		program.setCodeSegment(codeSegment);
		program.setDataSegment(dataSegment);
		return program;
	}

	@Override
	public canto.TargetCode getTC() {
		return program;
	}

	@Override
	public void visit(InstructionList ic) throws Exception {
		List<Instruction> icList=ic.getList();
		for(Instruction instruction: icList){
			instruction.accept(this);
		}
	}

	@Override
	public void visit(Label ic) throws Exception {
		canto.c1.x86.Label label=new canto.c1.x86.Label(ic.toString());
		codeSegment.add(label);
	}

	@Override
	public void visit(In ic) throws Exception {
		Symbol dst=new Symbol(ic.getDst().toString());
		DataDefine dataDefine=new DataDefine(dst.getName(), DataType.newDoubleWord(), new Immediate[]{null});
		dataSegment.addDataDefine(dataDefine);
		InInteger inInteger=new InInteger(dst);
		codeSegment.add(inInteger);
	}

	@Override
	public void visit(Out ic) throws Exception {		
		Symbol src=new Symbol(ic.getSrc().toString());
		OutInteger outInteger=new OutInteger(src);
		codeSegment.add(outInteger);
	}

	@Override
	public void visit(Goto ic) throws Exception {
		canto.c1.x86.Label target= new canto.c1.x86.Label(ic.getTarget().toString());
		JMP jmp=new JMP(target);
		codeSegment.add(jmp);
	}
	
	/**
	 * 
	 * @param operand 中间代码的操作数
	 * @return 中间代码
	 */
	private Operand getTCOperand(canto.c1.ic.Operand operand){
		if(operand.getICType()==IntermediateCode.INTEGER_LITERAL){
			return new Immediate(((IntegerLiteral)operand).getValue());
		}
		if(operand.getICType()==IntermediateCode.VARIABLE){
			return new Symbol(operand.toString());
		}
	}

	@Override
	public void visit(JEQ ic) throws Exception {
		Operand operand1=getCurrentLocation(ic.getOperand1());
		Operand operand2=getCurrentLocation(ic.getOperand2());
		CMP cmp=new CMP(operand1, operand2);
		codeSegment.add(cmp);
		canto.c1.x86.Label target= new canto.c1.x86.Label(ic.getTarget().toString());
		JE je=new JE(target);
		codeSegment.add(je);
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
