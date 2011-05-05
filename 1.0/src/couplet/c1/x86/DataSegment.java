package couplet.c1.x86;

import java.util.LinkedList;
import java.util.List;

/**
 * X86目标码的数据段
 */
public class DataSegment implements X86TargetCode {

	/** 存储数据定义序列的链表 */
	private List<DataDefine> dataDefineList;
	
	/**
	 * 构造一个数据段
	 */
	public DataSegment() {
		dataDefineList = new LinkedList<DataDefine>();
	}

	@Override
	public void accept(X86Visitor visitor) throws Exception {
		visitor.visit(this);		
	}
	
	@Override
	public int getTCType() {
		return DATA_SEGMENT;
	}

	/**
	 * 获取数据定义序列链表
	 * @return 数据定义序列链表
	 */
	public List<DataDefine> getDataDefineList() {
		return dataDefineList;
	}

	/**
	 * 向数据定义序列链表中增加一个数据定义
	 * @param dataDefine 新增的数据定义
	 */
	public void add(DataDefine dataDefine) {
		dataDefineList.add(dataDefine);
	}

}
