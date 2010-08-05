package canto.c1.x86;

/**
 * X86目标码的Intel汇编格式的发射器
 *   实现了X86目标码的访问者接口
 *   用于将X86目标码的数据结构输出成字符串
 */
public class IntelEmitter implements X86Visitor {

	private X86TargetCode tc;
	
	public String emit() {
		return tc.accept(this).toString();
	}
	
	@Override
	public String visit(Program tc) {
		String codeString = "";
		codeString += ".586\n";
		codeString += ".MODEL FLAT, STDCALL\n";
		codeString += "INCLUDE msvcrt.inc\n";
		codeString += "INCLUDELIB msvcrt.lib\n";
		codeString += tc.getCodeSegment();
		codeString += tc.getDataSegment();
		return codeString;
	}

	@Override
	public String visit(DataSegment tc) {
		String codeString = "";
		codeString += ".DATA\n";
		codeString += "INT_FMT BYTE \"%d\", 0";
		for (DataDefine dd : tc.getDataDefineList()) {
			codeString += dd.accept(this) + "\n"; 
		}
		return codeString;
	}

	@Override
	public String visit(CodeSegment tc) {
		String codeString = "";
		codeString += ".CODE\n";
		for (Instruction instr : tc.getInstructionList()) {
			codeString += instr.accept(this) + "\n";
		}
		return codeString;
	}

	@Override
	public String visit(DataDefine tc) {
		String codeString = "";
		codeString += tc.getName() + " " + tc.getType().accept(this);
		boolean isFirst = true;
		for (Immediate imme : tc.getImmeList()) {
			String immeStr = (imme == null) ? "?" : (String) imme.accept(this);
			if (isFirst) {
				codeString += " " + immeStr;
				isFirst = false;
			} else {
				codeString += "," + immeStr;
			}
		}
		return codeString;
	}
	
	@Override
	public String visit(Label tc) {
		return tc.getName() + ":";
	}
	
	@Override
	public String visit(MOV tc) {
		return "MOV " + tc.getDst() + " " + tc.getSrc(); 
	}

	@Override
	public String visit(PUSH tc) {
		return "PUSH " + tc.getSrc(); 
	}

	@Override
	public String visit(POP tc) {
		return "POP " + tc.getDst(); 
	}

	@Override
	public String visit(ADD tc) {
		return "ADD " + tc.getDst() + " " + tc.getSrc(); 
	}

	@Override
	public String visit(SUB tc) {
		return "SUB " + tc.getDst() + " " + tc.getSrc(); 
	}

	@Override
	public String visit(IMUL tc) {
		return "IMUL " + tc.getDst() + " " + tc.getSrc(); 
	}

	@Override
	public String visit(NEG tc) {
		return "NEG " + tc.getDst(); 
	}

	@Override
	public String visit(CMP tc) {
		return "CMP " + tc.getOperand1() + " " + tc.getOperand2(); 
	}

	@Override
	public String visit(JMP tc) {
		return "JMP " + tc.getTarget().getName(); 
	}

	@Override
	public String visit(JE tc) {
		return "JE " + tc.getTarget().getName(); 
	}

	@Override
	public String visit(JNE tc) {
		return "JNE " + tc.getTarget().getName(); 
	}

	@Override
	public String visit(JG tc) {
		return "JG " + tc.getTarget().getName(); 
	}

	@Override
	public String visit(JGE tc) {
		return "JGE " + tc.getTarget().getName(); 
	}

	@Override
	public String visit(JL tc) {
		return "JL " + tc.getTarget().getName(); 
	}

	@Override
	public String visit(JLE tc) {
		return "JLE " + tc.getTarget().getName(); 
	}

	@Override
	public String visit(InInteger tc) {
		return "INVOKE crt_scanf, ADDR INT_FMT, ADDR " + tc.getDst().accept(this); 
	}

	@Override
	public String visit(OutInteger tc) {
		return "INVOKE crt_printf, ADDR INT_FMT, " + tc.getSrc().accept(this);
	}
	
	@Override
	public String visit(Immediate tc) {
		return tc.getValue().toString();
	}

	@Override
	public String visit(Symbol tc) {
		return tc.getName();
	}

	@Override
	public String visit(Register tc) {
		switch (tc.getRegNum()) {
		case Register.EAX : return "EAX";
		case Register.EBX : return "EBX";
		case Register.ECX : return "ECX";
		case Register.EDX : return "EDX";
		case Register.EBP : return "EBP";
		case Register.ESP : return "ESP";
		case Register.EDI : return "EDI";
		case Register.ESI : return "ESI";
		default : return null;
		}
	}

	@Override
	public String visit(Pseudo tc) {
		return tc.getCode();
	}

	@Override
	public Object visit(DataType tc) {
		switch (tc.getType()) {
		case DataType.BYTE : return "DB";
		case DataType.WORD : return "DW";
		case DataType.DOUBLE_WORD : return "DD";
		case DataType.QUADRUPLE_WORD : return "DQ";
		default: return null;
		}
	}

}
