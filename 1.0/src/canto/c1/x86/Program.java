package canto.c1.x86;

public class Program extends X86TargetCode {

	private DataSegment dataSegment;
	
	private CodeSegment codeSegment;

	@Override
	public Object accept(X86Visitor visitor) {
		return visitor.visit(this);
	}

	@Override
	public int getTCType() {
		return PROGRAM;
	}

	public DataSegment getDataSegment() {
		return dataSegment;
	}

	public void setDataSegment(DataSegment dataSegment) {
		this.dataSegment = dataSegment;
	}

	public CodeSegment getCodeSegment() {
		return codeSegment;
	}

	public void setCodeSegment(CodeSegment codeSegment) {
		this.codeSegment = codeSegment;
	}

}
