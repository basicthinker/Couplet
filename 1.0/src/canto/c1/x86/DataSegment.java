package canto.c1.x86;

import java.util.LinkedList;
import java.util.List;

public class DataSegment extends X86TargetCode {

	private List<DataDefine> dataDefineList;
	
	public DataSegment() {
		dataDefineList = new LinkedList<DataDefine>();
	}

	@Override
	public void accept(X86Visitor visitor) {
		visitor.visit(this);		
	}
	
	@Override
	public int getTCType() {
		return DATA_SEGMENT;
	}

	public List<DataDefine> getDataDefineList() {
		return dataDefineList;
	}

	public void addDataDefine(DataDefine dataDefine) {
		dataDefineList.add(dataDefine);
	}

}
