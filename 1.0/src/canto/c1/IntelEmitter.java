package canto.c1;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

import canto.TargetCode;
import canto.c1.x86.ADD;
import canto.c1.x86.CMP;
import canto.c1.x86.CodeSegment;
import canto.c1.x86.DataDefine;
import canto.c1.x86.DataSegment;
import canto.c1.x86.DataType;
import canto.c1.x86.IMUL;
import canto.c1.x86.Immediate;
import canto.c1.x86.InInteger;
import canto.c1.x86.Instruction;
import canto.c1.x86.JE;
import canto.c1.x86.JG;
import canto.c1.x86.JGE;
import canto.c1.x86.JL;
import canto.c1.x86.JLE;
import canto.c1.x86.JMP;
import canto.c1.x86.JNE;
import canto.c1.x86.Label;
import canto.c1.x86.MOV;
import canto.c1.x86.NEG;
import canto.c1.x86.OutInteger;
import canto.c1.x86.POP;
import canto.c1.x86.PUSH;
import canto.c1.x86.Program;
import canto.c1.x86.Pseudo;
import canto.c1.x86.Register;
import canto.c1.x86.SUB;
import canto.c1.x86.Symbol;
import canto.c1.x86.X86TargetCode;
import canto.c1.x86.X86Visitor;

/**
 * X86目标码的Intel汇编格式的发射器
 *   实现了X86目标码的访问者接口
 *   用于将X86目标码的数据结构输出成字符串
 */
public class IntelEmitter implements canto.TCEmmiter, X86Visitor {

	private BufferedWriter outBuf = 
		new BufferedWriter(new OutputStreamWriter(System.out));
	
	@Override
	public void setWriter(OutputStreamWriter outStr) {
		this.outBuf = new BufferedWriter(outStr);
	}

	@Override
	public void emmit(TargetCode tc) throws IOException {
		((X86TargetCode)tc).accept(this);
		outBuf.close();
	}
	
	@Override
	public void visit(Program tc) throws IOException {
		// 为了写入准确的库文件位置，获取系统当前路径
		String currentPath = System.getProperty("user.dir");
		outBuf.write(".586\n");
		outBuf.write(".MODEL FLAT, STDCALL\n");
		outBuf.write("INCLUDE " + currentPath + "\\libs\\msvcrt.inc\n");
		outBuf.write("INCLUDELIB " + currentPath + "\\libs\\msvcrt.lib\n");
		tc.getDataSegment().accept(this);
		tc.getCodeSegment().accept(this);		
	}

	@Override
	public void visit(DataSegment tc) throws IOException {
		outBuf.write(".DATA\n");
		outBuf.write("INT_IN_FMT BYTE \"%d\", 0\n");
		outBuf.write("INT_OUT_FMT BYTE \"%d\", 13, 10, 0\n");
		for (DataDefine dd : tc.getDataDefineList()) {
			dd.accept(this);
			outBuf.write("\n");
		}
	}

	@Override
	public void visit(CodeSegment tc) throws IOException {
		outBuf.write(".CODE\n");
		outBuf.write("START:\n");
		for (Instruction instr : tc.getInstructionList()) {
			instr.accept(this);
			outBuf.write("\n");
		}
		outBuf.write("RET\n");
		outBuf.write("END START\n");
	}

	@Override
	public void visit(DataDefine tc) throws IOException {
		outBuf.write(tc.getName() + " ");
		tc.getType().accept(this);
		boolean isFirst = true;
		for (Immediate imme : tc.getImmeList()) {
			String immeStr = (imme == null) ? 
					"?" : (String) imme.getValue().toString();
			if (isFirst) {
				outBuf.write(" " + immeStr);
				isFirst = false;
			} else {
				outBuf.write("," + immeStr);
			}
		}
	}
	
	@Override
	public void visit(Label tc) throws IOException {
		outBuf.write(tc.getName() + ":");
	}
	
	@Override
	public void visit(MOV tc) throws IOException {
		outBuf.write("MOV ");
		tc.getDst().accept(this);
		outBuf.write(", ");
		tc.getSrc().accept(this);
	}

	@Override
	public void visit(PUSH tc) throws IOException {
		outBuf.write("PUSH ");
		tc.getSrc().accept(this);
	}

	@Override
	public void visit(POP tc) throws IOException {
		outBuf.write("POP ");
		tc.getDst().accept(this); 
	}

	@Override
	public void visit(ADD tc) throws IOException {
		outBuf.write("ADD ");
		tc.getDst().accept(this);
		outBuf.write(", ");
		tc.getSrc().accept(this); 
	}

	@Override
	public void visit(SUB tc) throws IOException {
		outBuf.write("SUB ");
		tc.getDst().accept(this);
		outBuf.write(", ");
		tc.getSrc().accept(this); 
	}

	@Override
	public void visit(IMUL tc) throws IOException {
		outBuf.write("IMUL ");
		tc.getDst().accept(this);
		outBuf.write(", ");
		tc.getSrc().accept(this); 
	}

	@Override
	public void visit(NEG tc) throws IOException {
		outBuf.write("NEG ");
		tc.getDst().accept(this); 
	}

	@Override
	public void visit(CMP tc) throws IOException {
		outBuf.write("CMP ");
		tc.getOperand1().accept(this);
		outBuf.write(", ");
		tc.getOperand2().accept(this);
	}

	@Override
	public void visit(JMP tc) throws IOException {
		outBuf.write("JMP " + tc.getTarget().getName());
	}

	@Override
	public void visit(JE tc) throws IOException {
		outBuf.write("JE " + tc.getTarget().getName());
	}

	@Override
	public void visit(JNE tc) throws IOException {
		outBuf.write("JNE " + tc.getTarget().getName());
	}

	@Override
	public void visit(JG tc) throws IOException {
		outBuf.write("JG " + tc.getTarget().getName());
	}

	@Override
	public void visit(JGE tc) throws IOException {
		outBuf.write("JGE " + tc.getTarget().getName());
	}

	@Override
	public void visit(JL tc) throws IOException {
		outBuf.write("JL " + tc.getTarget().getName());
	}

	@Override
	public void visit(JLE tc) throws IOException {
		outBuf.write("JLE " + tc.getTarget().getName());
	}

	@Override
	public void visit(InInteger tc) throws IOException {
		outBuf.write("INVOKE crt_scanf, ADDR INT_IN_FMT, ADDR ");
		tc.getDst().accept(this); 
	}

	@Override
	public void visit(OutInteger tc) throws IOException {
		outBuf.write("INVOKE crt_printf, ADDR INT_OUT_FMT, ");
		tc.getSrc().accept(this);
	}
	
	@Override
	public void visit(Immediate tc) throws IOException {
		outBuf.write(tc.getValue().toString());
	}

	@Override
	public void visit(Symbol tc) throws IOException {
		outBuf.write(tc.toString());
	}

	@Override
	public void visit(Register tc) throws IOException {
		switch (tc.getRegNum()) {
		case Register.EAX : outBuf.write("EAX"); break;
		case Register.EBX : outBuf.write("EBX"); break;
		case Register.ECX : outBuf.write("ECX"); break;
		case Register.EDX : outBuf.write("EDX"); break;
		case Register.EBP : outBuf.write("EBP"); break;
		case Register.ESP : outBuf.write("ESP"); break;
		case Register.EDI : outBuf.write("EDI"); break;
		case Register.ESI : outBuf.write("ESI"); break;
		default : return;
		}
	}

	@Override
	public void visit(Pseudo tc) throws IOException {
		outBuf.write(tc.getCode() + "\n");
	}

	@Override
	public void visit(DataType tc) throws IOException {
		switch (tc.getType()) {
		case DataType.BYTE : outBuf.write("DB"); break;
		case DataType.WORD : outBuf.write("DW"); break;
		case DataType.DOUBLE_WORD : outBuf.write("DD"); break;
		case DataType.QUADRUPLE_WORD : outBuf.write("DQ"); break;
		default: return;
		}
	}

}
