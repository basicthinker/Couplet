package canto.c1.x86;

import java.io.IOException;

/**
 * X86目标码的数据类型
 */
public class DataType extends X86TargetCode {

	/** 定义数据类型编号常量 */
	public static final int BYTE = 1;
	public static final int	WORD = 2;
	public static final int DOUBLE_WORD = 3;
	public static final int QUADRUPLE_WORD = 4;
	
	/** 存储数据类型编号 */
	private final int type;
	
	/**
	 * 构造一个数据类型
	 * @param type 数据类型编号
	 */
	public DataType(int type) {
		this.type = type;
	}

	/**
	 * 构造一个字节数据类型
	 * @return 字节数据类型
	 */
	public static DataType newByte() {
		return new DataType(BYTE);
	}
	
	/**
	 * 构造一个字数据类型
	 * @return 字数据类型
	 */	
	public static DataType newWord() {
		return new DataType(WORD);
	}
	
	/**
	 * 构造一个双字数据类型
	 * @return 双字数据类型
	 */
	public static DataType newDoubleWord() {
		return new DataType(DOUBLE_WORD);
	}
	
	/**
	 * 构造一个四字数据类型
	 * @return 四字数据类型
	 */
	public static DataType newQuadrupleWord() {
		return new DataType(QUADRUPLE_WORD);
	}
	
	@Override
	public void accept(X86Visitor visitor) throws IOException {
		visitor.visit(this);
	}

	@Override
	public int getTCType() {
		return DATA_TYPE;
	}
	
	/**
	 * 获取数据类型编码
	 * @return 数据类型编码
	 */
	public int getType() {
		return type;
	}

}
