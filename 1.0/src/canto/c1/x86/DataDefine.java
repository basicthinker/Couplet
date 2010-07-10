package canto.c1.x86;

import java.util.List;

public class DataDefine extends X86TargetCode {

	enum DataType {
		BYTE,
		WORD,
		DOUBLE_WORD,
		QUADRUPLE_WORD
	}
	
	private final String name;
	
	private final DataType type;
	
	private final List<Immediate> immeList;

	public DataDefine(String name, DataType type, List<Immediate> immeList) {
		this.name = name;
		this.type = type;
		this.immeList = immeList;
	}
	
	@Override
	public void accept(X86Visitor visitor) {
		visitor.visit(this);
	}
	
	@Override
	public int getTCType() {
		return DATA_DEFINE;
	}

	public String getName() {
		return name;
	}

	public DataType getType() {
		return type;
	}

	public List<Immediate> getImmeList() {
		return immeList;
	}

}
