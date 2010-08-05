package canto.c1.x86;

import java.util.List;

/**
 * X86目标码的数据定义
 */
public class DataDefine extends X86TargetCode {

	/** 存储所定义数据的符号名 */
	private final String name;
	
	/** 存储所定义数据的类型 */
	private final DataType type;
	
	/** 存储所定义数据的初始值列表 */
	private final List<Immediate> immeList;

	/**
	 * 构造一个数据定义
	 * @param name 数据的符号名
	 * @param type 数据的类型
	 * @param immeList 数据的初始值列表
	 */
	public DataDefine(String name, DataType type, List<Immediate> immeList) {
		this.name = name;
		this.type = type;
		this.immeList = immeList;
	}
	
	@Override
	public Object accept(X86Visitor visitor) {
		return visitor.visit(this);
	}
	
	@Override
	public int getTCType() {
		return DATA_DEFINE;
	}

	/**
	 * 获取数据的符号名
	 * @return 数据的符号名
	 */
	public String getName() {
		return name;
	}

	/**
	 * 获取数据的类型
	 * @return 数据的类型
	 */
	public DataType getType() {
		return type;
	}

	/**
	 * 获取数据的初始值列表
	 * @return 数据的初始值列表
	 */
	public List<Immediate> getImmeList() {
		return immeList;
	}

}
