package canto.c1;

import java.util.List;

import canto.c1.error.ErrorRecord;
import canto.c1.error.CompileException;
import canto.c1.ic.Add;
import canto.c1.ic.Arithmetic;
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
import canto.c1.ic.UnaryArithmetic;
import canto.c1.ic.Variable;

import canto.c1.x86.ADD;
import canto.c1.x86.CMP;
import canto.c1.x86.CodeSegment;
import canto.c1.x86.DataDefine;
import canto.c1.x86.DataSegment;
import canto.c1.x86.DataType;
import canto.c1.x86.IMUL;
import canto.c1.x86.Immediate;
import canto.c1.x86.InInteger;
import canto.c1.x86.JE;
import canto.c1.x86.JG;
import canto.c1.x86.JL;
import canto.c1.x86.JMP;
import canto.c1.x86.Location;
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
 * 
 * @author Goodness
 */
public class TCGenerator implements canto.TCGenerator, ICVisitor {

	/** 输入的中间代码序列 */
	private InstructionList ic;

	/** 返回的目标代码的数据段 */
	private DataSegment dataSegment;

	/** 返回的目标代码的代码段 */
	private CodeSegment codeSegment;

	/** 返回的目标代码 */
	private Program program;

	/** 寄存器与存储地址间的对应关系 */
	private static String[] regMap;

	/** 中间代码中的变量与目标代码变量的对应关系表 */
	SymbolTable<Symbol> symbolTable;

	/** 中间代码变量与目标代码寄存器的对应关系表 */
	SymbolTable<Register> registerTable;

	/** 当前该用的寄存器对应的数字 */
	static int regPointer = 0;
	
	public TCGenerator() {
		program = new Program();
		dataSegment = new DataSegment();
		codeSegment = new CodeSegment();
		symbolTable = new SymbolTable<Symbol>();
		registerTable = new SymbolTable<Register>();
		regMap = new String[4];
	}


	@Override
	public void setIC(canto.IntermediateCode ic) {
		this.ic = (InstructionList) ic;
	}

	@Override
	public void generateTC() throws CompileException {
		try {
			visit(ic);
			program.setCodeSegment(codeSegment);
			program.setDataSegment(dataSegment);
		} catch (Exception e) {
			throw new CompileException(ErrorRecord.tcGenerateError());
		}
	}

	@Override
	public canto.TargetCode getTC() {
		return program;
	}

	@Override
	public void visit(InstructionList ic) throws Exception {
		List<Instruction> icList = ic.getList();
		for (Instruction instruction : icList) {
			instruction.accept(this);
		}
	}

	@Override
	public void visit(Label ic) throws Exception {
		canto.c1.x86.Label label = new canto.c1.x86.Label(ic.toString());
		codeSegment.add(label);
	}

	private Register assign(Memory memory) {
		// 为一个符号分配寄存器
		Register register = assign();
		regMap[register.getRegNum()] = memory.toString();
		registerTable.put(memory.toString(), register);
		return register;
	}

	private Register assign(Immediate imme) {
		// 为一个立即数分配寄存器
		Register register=assign();
		regMap[register.getRegNum()]=imme.toString();
		registerTable.put(imme.toString(), register);
		return register;
	}

	private Register assign() {
		// 寄存器分配算法

		// 有些操作寄存器用过之后会清空，以下为有空位置的情况
		for (int i = 0; i < 4; i++) {
			if (regMap[i] == null) {
				return new Register(i);
			}
		}

		// 没有空位置的情况，循环拿出寄存器
		Register register = new Register(regPointer);
		String regContent = regMap[regPointer];
		if (!symbolTable.isExist(regContent)) {
			// 在数据段加入需要挪入内存变量的定义
			DataDefine dataDefine = new DataDefine(regContent, DataType
					.newDoubleWord(), new Immediate[] { new Immediate(0) });
			dataSegment.addDataDefine(dataDefine);
			symbolTable.put(regContent, new Symbol(regContent));
		}
		// 代码段加入寄存器当前值挪入内存的代码
		Symbol symbol = symbolTable.get(regContent);
		codeSegment.add(new MOV(symbol, register));
		regMap[regPointer] = null;
		registerTable.del(regContent);
		regPointer = (regPointer + 1) % 4;// 只用前四个寄存器
		return register;
	}

	private canto.c1.x86.Location checkSymbolTable(String name) {
		// 输入符号名字，从符号表中查，看是否有相应的Location
		if (registerTable.isExist(name)) {
			return registerTable.get(name);
		} else if (symbolTable.isExist(name)) {
			return symbolTable.get(name);
		} else {
			return null;
		}
	}

	/**
	 * 将中间代码的操作数转成目标代码的操作数
	 * 
	 * @param operand
	 *            输入的中间代码操作数
	 * @return 相应的目标代码操作数
	 */
	private Operand getTCOperand(canto.c1.ic.Operand operand) {
		if (operand.getICType() == IntermediateCode.INTEGER_LITERAL) {
			return new Immediate(((IntegerLiteral) operand).getValue());
		} else {
			String operandName = operand.toString();
			canto.c1.x86.Location nowLocation = checkSymbolTable(operandName);
			if (operand.getICType() == IntermediateCode.VARIABLE) {
				// 如果是Variable类型，则分配一个内存地址，此处不分配寄存器地址，
				// 寄存器地址在需要分配时调用assign分配
				if (nowLocation == null) {
					// 在数据段加入变量的定义
					DataDefine dataDefine = new DataDefine(operandName,
							DataType.newDoubleWord(),
							new Immediate[] { new Immediate(0) });
					dataSegment.addDataDefine(dataDefine);
					// 在symbolTable中加入新的名值对应
					Symbol symbol = new Symbol(operandName);
					symbolTable.put(operandName, symbol);
					return symbol;
				} else {
					return nowLocation;
				}
			} else {
				// 如果是Temp类型，则分配一个寄存器并返回
				if (nowLocation == null) {
					return assign(new Symbol(operandName));
				} else if (nowLocation.getTCType() != X86TargetCode.REGISTER) {
					Register register = assign((Symbol) nowLocation);
					codeSegment.add(new MOV(register, nowLocation));
					return register;
				} else {
					return nowLocation;
				}
			}
		}
	}

	@Override
	public void visit(In ic) throws Exception {
		canto.c1.x86.Location dst = (canto.c1.x86.Location) getTCOperand(ic
				.getDst());
		InInteger inInteger = new InInteger(dst);
		codeSegment.add(inInteger);
	}

	@Override
	public void visit(Out ic) throws Exception {
		Operand src = getTCOperand(ic.getSrc());
		OutInteger outInteger = new OutInteger(src);
		codeSegment.add(outInteger);
	}

	@Override
	public void visit(Goto ic) throws Exception {
		canto.c1.x86.Label target = new canto.c1.x86.Label(ic.getTarget()
				.toString());
		codeSegment.add(new JMP(target));
	}

	private void addCmp(CJump ic) {
		// 加入一个比较的目标代码语句
		Operand operand1 = getTCOperand(ic.getOperand1());
		Operand operand2 = getTCOperand(ic.getOperand2());
		if(operand1.getTCType()==X86TargetCode.REGISTER && operand2.getTCType()==X86TargetCode.REGISTER){
			codeSegment.add(new CMP(operand1, operand2));			
		}else if(operand1.getTCType()==X86TargetCode.SYMBOL && operand2.getTCType()==X86TargetCode.SYMBOL) {
			Register register = assign((Memory) operand1);
			codeSegment.add(new MOV(register, operand1));
			codeSegment.add(new CMP(register, operand2));
		}else if(operand1.getTCType()==X86TargetCode.IMMEDIATE && operand2.getTCType()==X86TargetCode.IMMEDIATE){
			Register register=assign((Immediate)operand1);
			codeSegment.add(new MOV(register, operand1));
			codeSegment.add(new CMP(register, operand2));
		}else {
			codeSegment.add(new CMP(operand1, operand2));
		}
	}

	@Override
	public void visit(JEQ ic) throws Exception {
		addCmp(ic);
		canto.c1.x86.Label target = new canto.c1.x86.Label(ic.getTarget()
				.toString());
		codeSegment.add(new JE(target));
	}

	@Override
	public void visit(JNE ic) throws Exception {
		addCmp(ic);
		canto.c1.x86.Label target = new canto.c1.x86.Label(ic.getTarget()
				.toString());
		codeSegment.add(new canto.c1.x86.JNE(target));
	}

	@Override
	public void visit(JLT ic) throws Exception {
		addCmp(ic);
		canto.c1.x86.Label target = new canto.c1.x86.Label(ic.getTarget()
				.toString());
		codeSegment.add(new JL(target));
	}

	@Override
	public void visit(JLE ic) throws Exception {
		addCmp(ic);
		canto.c1.x86.Label target = new canto.c1.x86.Label(ic.getTarget()
				.toString());
		codeSegment.add(new canto.c1.x86.JLE(target));
	}

	@Override
	public void visit(JGT ic) throws Exception {
		addCmp(ic);
		canto.c1.x86.Label target = new canto.c1.x86.Label(ic.getTarget()
				.toString());
		codeSegment.add(new JG(target));
	}

	@Override
	public void visit(JGE ic) throws Exception {
		addCmp(ic);
		canto.c1.x86.Label target = new canto.c1.x86.Label(ic.getTarget()
				.toString());
		codeSegment.add(new canto.c1.x86.JGE(target));
	}

	/**
	 * 清除中间代码操作数和寄存器间的关系
	 * 
	 * @param icOperand
	 *            中间代码操作数
	 */
	private void freeReg(canto.c1.ic.Operand icOperand) {
		String name = icOperand.toString();
		if (icOperand.getICType() == IntermediateCode.TEMP
				&& registerTable.isExist(name)) {
			int regNum = registerTable.get(name).getRegNum();
			regMap[regNum] = null;
			registerTable.del(name);
		}
	}

	@Override
	public void visit(Mov ic) throws Exception {
		Operand src = getTCOperand(ic.getSrc());
		canto.c1.x86.Location dst = (canto.c1.x86.Location) getTCOperand(ic
				.getDst());
		codeSegment.add(new MOV(dst, src));
		freeReg(ic.getSrc());
	}
	
	
	/**
	 * 根据中间代码类型，加入不同的运算
	 * 
	 * @param ic
	 *            中间代码
	 * @param dst
	 *            目的操作数
	 * @param src
	 *            源操作数
	 */
	private void caseAdd(Arithmetic ic, Location dst, Operand src) {
		switch (ic.getICType()) {
		case IntermediateCode.ADD:
			codeSegment.add(new ADD((Register) dst, src));
			break;
		case IntermediateCode.SUB:
			codeSegment.add(new SUB((Register) dst, src));
			break;
		case IntermediateCode.MUL:
			codeSegment.add(new IMUL((Register) dst, src));
			break;
		case IntermediateCode.NEG:
			codeSegment.add(new NEG(dst));
			break;
		}
	}
	
	/**
	 * 目标代码加入一个二元运算
	 * 
	 * @param ic
	 *            输入的中间代码
	 * @param flag
	 *            标志参数，0表示中间代码的两个源操作数均不是Temp类型，1表示第一个是，2表示第二个是
	 */
	private void addBinaryArithmetic(BinaryArithmetic ic, int flag) {
		canto.c1.ic.Operand icSrc1 = ic.getSrc1();
		canto.c1.ic.Operand icSrc2 = ic.getSrc2();
		canto.c1.ic.Operand res = ic.getResult();
		Register dst; // 二元运算符的目的操作数
		Operand src; // 二元运算符的源操作数
		if (flag == 1) {
			dst = (Register) getTCOperand(icSrc1);
			freeReg(icSrc1);// 将inSrc1和寄存器间的关系清除
			// 建立res和寄存器间的对应关系
			regMap[dst.getRegNum()] = res.toString();
			registerTable.put(res.toString(), dst);
			src = getTCOperand(icSrc2);// 取出源操作数
			caseAdd(ic, dst, src);
			freeReg(icSrc2);// 加过之后源操作数如果是Temp就不会再被使用，所以清除寄存器与icSrc2间关系
		} else if (flag == 2) {
			dst = (Register) getTCOperand(icSrc2);
			freeReg(icSrc2);
			regMap[dst.getRegNum()] = res.toString();
			registerTable.put(res.toString(), dst);
			src = getTCOperand(icSrc1);
			caseAdd(ic, dst, src);
			freeReg(icSrc1);
		} else {
			canto.c1.x86.Location getDst = (canto.c1.x86.Location) getTCOperand(ic
					.getResult());
			if (getDst.getTCType() == X86TargetCode.REGISTER) {
				dst = (Register) getDst;
			} else {
				dst = assign((Symbol) getDst);
			}
			src = getTCOperand(icSrc1);
			codeSegment.add(new MOV(dst, src));
			freeReg(icSrc1);
			src = getTCOperand(icSrc2);
			caseAdd(ic, dst, src);
			freeReg(icSrc2);
		}
	}

	@Override
	public void visit(Add ic) throws Exception {
		if (ic.getSrc1().getICType() == IntermediateCode.TEMP) {
			addBinaryArithmetic(ic, 1);
		} else if (ic.getSrc2().getICType() == IntermediateCode.TEMP) {
			addBinaryArithmetic(ic, 2);
		} else {
			addBinaryArithmetic(ic, 0);
		}
	}

	@Override
	public void visit(Sub ic) throws Exception {
		if (ic.getSrc1().getICType() == IntermediateCode.TEMP) {
			addBinaryArithmetic(ic, 1);
		} else {
			addBinaryArithmetic(ic, 0);
		}
	}

	@Override
	public void visit(Mul ic) throws Exception {
		if (ic.getSrc1().getICType() == IntermediateCode.TEMP) {
			addBinaryArithmetic(ic, 1);
		} else if (ic.getSrc2().getICType() == IntermediateCode.TEMP) {
			addBinaryArithmetic(ic, 2);
		} else {
			addBinaryArithmetic(ic, 0);
		}
	}
	
	/**
	 * 加入一个一元运算符
	 * 
	 * @param ic
	 *            输入的中间代码
	 * @param flag
	 *            标志参数，为1表示源操作数为Temp，2表示为Variable，0表示为常数
	 */
	private void addUnaryArithmetic(UnaryArithmetic ic, int flag) {
		canto.c1.ic.Operand icSrc = ic.getSrc();
		canto.c1.ic.Operand res = ic.getResult();
		if (flag == 1) {
			Register dst = (Register) getTCOperand(icSrc);
			freeReg(icSrc);// 将inSrc和寄存器间的关系清除
			// 建立res和寄存器间的对应关系
			regMap[dst.getRegNum()] = res.toString();
			registerTable.put(res.toString(), dst);
			caseAdd(ic, dst, null);
		} else if (flag == 2) {
			Location dst = (Location) getTCOperand(res);
			codeSegment.add(new MOV(dst, getTCOperand(icSrc)));
			codeSegment.add(new NEG(dst));
		} else {
			Location dst = (Location) getTCOperand(res);
			Immediate src = new Immediate(-((IntegerLiteral) icSrc).getValue());
			codeSegment.add(new MOV(dst, src));
		}
	}

	@Override
	public void visit(Neg ic) throws Exception {
		if (ic.getSrc().getICType() == IntermediateCode.TEMP) {
			addUnaryArithmetic(ic, 1);
		} else if (ic.getSrc().getICType() == IntermediateCode.VARIABLE) {
			addUnaryArithmetic(ic, 2);
		} else {
			addUnaryArithmetic(ic, 0);
		}
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
