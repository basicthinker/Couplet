package canto.c1.ic;

/**
 * 中间代码的整形常量操作数
 */
public class IntegerLiteral extends Literal {

	private final Integer value; 
	
	public IntegerLiteral(Integer value) {
		this.value = value;
	}

	@Override
	public void accept(ICVisitor visitor) throws Exception {
		visitor.visit(this);
	}

	@Override
	public int getICType() {
		return INTEGER_LITERAL;
	}

	public Integer getValue() {
		return value;
	}

}
