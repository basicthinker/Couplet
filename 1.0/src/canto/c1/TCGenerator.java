package canto.c1;

import java.util.List;

import canto.c1.ic.Add;
import canto.c1.ic.BinaryArithmetic;
import canto.c1.ic.CJump;
import canto.c1.ic.IntermediateCode;
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
import canto.c1.ic.Mov;
import canto.c1.ic.Mul;
import canto.c1.ic.Neg;
import canto.c1.ic.Out;
import canto.c1.ic.Sub;
import canto.c1.ic.Temp;
import canto.c1.ic.Variable;

import canto.c1.x86.ADD;
import canto.c1.x86.CMP;
import canto.c1.x86.CodeSegment;
import canto.c1.x86.DataDefine;
import canto.c1.x86.DataSegment;
import canto.c1.x86.DataType;
import canto.c1.x86.Immediate;
import canto.c1.x86.InInteger;
import canto.c1.x86.JE;
import canto.c1.x86.JG;
import canto.c1.x86.JL;
import canto.c1.x86.JMP;
import canto.c1.x86.MOV;
import canto.c1.x86.Memory;
import canto.c1.x86.NEG;
import canto.c1.x86.Operand;
import canto.c1.x86.OutInteger;
import canto.c1.x86.Program;
import canto.c1.x86.Register;
import canto.c1.x86.SUB;
import canto.c1.x86.Symbol;
import canto.c1.x86.X86TargetCode;

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
	
	/**寄存器与存储地址间的对应关系*/
	private static String[] regMap;
	
	/**中间代码中的变量与目标代码变量的对应关系表*/
	SymbolTable<Symbol> symbolTable;
	
	/**中间代码变量与目标代码寄存器的对应关系表*/
	SymbolTable<Register> registerTable;

	/**当前该用的寄存器对应的数字*/
	static int regPointer=0;
	
	public TCGenerator(){
		program=new Program();
		dataSegment=new DataSegment();
		codeSegment=new CodeSegment();
		symbolTable=new SymbolTable<Symbol>();
		registerTable=new SymbolTable<Register>();
		regMap=new String[4];
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
	
	private Register assign(Memory memory) {
		//为一个内存分配寄存器
		Register register=assign();
		registerTable.put(memory.toString(), register);
		return register;
	}
	
	private Register assign(Immediate imme) {
		// TODO 为一个立即数分配寄存器
		return null;
	}
	
	private Register assign() {
		//寄存器分配算法
		
		//有些操作寄存器用过之后会清空，以下为有空位置的情况
		for(int i=0;i<4;i++){
			if(regMap[i]==null){
				return new Register(i);
			}
		}
		
		//没有空位置的情况，循环拿出寄存器
		Register register=new Register(regPointer);
		String regContent=regMap[regPointer];
		if(!symbolTable.isExist(regContent)){
			//在数据段加入需要挪入内存变量的定义
			DataDefine dataDefine=new DataDefine(regContent, DataType.newDoubleWord(), new Immediate[]{new Immediate(0)});
			dataSegment.addDataDefine(dataDefine);
			symbolTable.put(regContent, new Symbol(regContent));			
		}
		//代码段加入寄存器当前值挪入内存的代码
		Symbol symbol=symbolTable.get(regContent);
		codeSegment.add(new MOV(symbol, register));
		regMap[regPointer]=null;
		registerTable.del(regContent);
		regPointer=(regPointer+1)%4;//只用前四个寄存器
		return register;
	}
	
	private canto.c1.x86.Location checkSymbolTable(String name){
		//输入符号名字，从符号表中查，看是否有相应的Location
		if(registerTable.isExist(name)){
			return registerTable.get(name);
		}
		else if(symbolTable.isExist(name)){
			return symbolTable.get(name);
		}
		else{
			return null;
		}
	}
	
	private Operand getTCOperand(canto.c1.ic.Operand operand){
		//将中间代码的操作数转成目标代码的操作数
		if(operand.getICType()==IntermediateCode.INTEGER_LITERAL){
			return new Immediate(((IntegerLiteral)operand).getValue());
		}
		else{
			String operandName=operand.toString();
			canto.c1.x86.Location nowLocation=checkSymbolTable(operandName);
			if(operand.getICType()==IntermediateCode.VARIABLE){
				//如果是Variable类型，则分配一个内存地址，此处不分配寄存器地址，寄存器地址在需要分配时调用assign分配
				if(nowLocation==null){
					//在数据段加入变量的定义
					DataDefine dataDefine=new DataDefine(operandName, DataType.newDoubleWord(), new Immediate[]{new Immediate(0)});
					dataSegment.addDataDefine(dataDefine);
					//在symbolTable中加入新的名值对应
					Symbol symbol=new Symbol(operandName);
					symbolTable.put(operandName,symbol);
					return symbol;
				}
				else{
					return nowLocation;
				}
			}
			else{
				//如果是Temp类型，则分配一个寄存器并返回
				if(nowLocation==null){
					return assign(new Symbol(operandName));
				}
				else{
					return nowLocation;
				}
			}
		}
	}

	@Override
	public void visit(In ic) throws Exception {
		canto.c1.x86.Location dst=(canto.c1.x86.Location)getTCOperand(ic.getDst());
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
		codeSegment.add(new JMP(target));
	}
	
	private void addCmp(CJump ic){
		//加入一个比较的目标代码语句
		Operand operand1=getTCOperand(ic.getOperand1());
		Operand operand2=getTCOperand(ic.getOperand2());
		codeSegment.add(new CMP(operand1, operand2));
	}
		
	@Override
	public void visit(JEQ ic) throws Exception {
		addCmp(ic);
		canto.c1.x86.Label target= new canto.c1.x86.Label(ic.getTarget().toString());
		codeSegment.add(new JE(target));
	}
	
	@Override
	public void visit(JNE ic) throws Exception {
		addCmp(ic);
		canto.c1.x86.Label target= new canto.c1.x86.Label(ic.getTarget().toString());
		codeSegment.add(new canto.c1.x86.JNE(target));
	}

	@Override
	public void visit(JLT ic) throws Exception {
		addCmp(ic);
		canto.c1.x86.Label target= new canto.c1.x86.Label(ic.getTarget().toString());
		codeSegment.add(new JL(target));
	}

	@Override
	public void visit(JLE ic) throws Exception {
		addCmp(ic);
		canto.c1.x86.Label target= new canto.c1.x86.Label(ic.getTarget().toString());
		codeSegment.add(new canto.c1.x86.JLE(target));
	}

	@Override
	public void visit(JGT ic) throws Exception {
		addCmp(ic);
		canto.c1.x86.Label target= new canto.c1.x86.Label(ic.getTarget().toString());
		codeSegment.add(new JG(target));
	}

	@Override
	public void visit(JGE ic) throws Exception {
		addCmp(ic);
		canto.c1.x86.Label target= new canto.c1.x86.Label(ic.getTarget().toString());
		codeSegment.add(new canto.c1.x86.JGE(target));
	}
	
	private void freeReg(canto.c1.ic.Operand icOperand){
		String name=icOperand.toString();
		if(icOperand.getICType()==IntermediateCode.TEMP
				&&registerTable.isExist(name)){
			int regNum=registerTable.get(name).getRegNum();
			regMap[regNum]=null;
			registerTable.del(name);
		}
	}

	@Override
	public void visit(Mov ic) throws Exception {
		Operand src=getTCOperand(ic.getSrc());
		canto.c1.x86.Location dst=(canto.c1.x86.Location)getTCOperand(ic.getDst());
		codeSegment.add(new MOV(dst, src));
		freeReg(ic.getSrc());
	}
	
	//加入二元运算
	private void addBinaryArithmetic(BinaryArithmetic ic) {
		canto.c1.x86.Location getDst=(canto.c1.x86.Location)getTCOperand(ic.getResult());
		Register dst;
		if (getDst.getTCType()==X86TargetCode.REGISTER) {
			dst=(Register)getDst;
		} else {
			dst=assign((Symbol)getDst);
		}
		canto.c1.ic.Operand icSrc1=ic.getSrc1();
		Operand src=getTCOperand(icSrc1);
		codeSegment.add(new MOV(dst, src));
		freeReg(icSrc1);
		
		canto.c1.ic.Operand icSrc2=ic.getSrc2();
		src=getTCOperand(icSrc2);
		if(ic.getICType()==IntermediateCode.ADD){
			codeSegment.add(new ADD(dst, src));
		}else if(ic.getICType()==IntermediateCode.SUB){
			codeSegment.add(new SUB(dst, src));
		}else{
			codeSegment.add(new canto.c1.x86.IMUL(dst, src));
		}
		freeReg(icSrc2);
	}

	@Override
	public void visit(Add ic) throws Exception {
		addBinaryArithmetic(ic);
	}

	@Override
	public void visit(Sub ic) throws Exception {
		addBinaryArithmetic(ic);
	}

	@Override
	public void visit(Mul ic) throws Exception {
		addBinaryArithmetic(ic);
	}

	@Override
	public void visit(Neg ic) throws Exception {
		canto.c1.x86.Location dst=(canto.c1.x86.Location) getTCOperand(ic.getResult());
		Operand src=getTCOperand(ic.getSrc());
		codeSegment.add(new MOV(dst, src));
		codeSegment.add(new NEG(dst));
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
}
