package canto.c1.x86;

public class DataType extends X86TargetCode {

	public static final int BYTE = 1;
	public static final int	WORD = 2;
	public static final int DOUBLE_WORD = 3;
	public static final int QUADRUPLE_WORD = 4;
	
	private final int type;
	
	public DataType(int type) {
		this.type = type;
	}

	public static DataType newByte() {
		return new DataType(BYTE);
	}
	
	public static DataType newWord() {
		return new DataType(WORD);
	}
	
	public static DataType newDoubleWord() {
		return new DataType(DOUBLE_WORD);
	}
	
	public static DataType newQuadrupleWord() {
		return new DataType(QUADRUPLE_WORD);
	}
	
	@Override
	public int getTCType() {
		return DATA_TYPE;
	}

	@Override
	public Object accept(X86Visitor visitor) {
		return visitor.visit(this);
	}

	public int getType() {
		return type;
	}

}
