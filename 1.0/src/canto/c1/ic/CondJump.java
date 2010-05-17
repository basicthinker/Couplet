package canto.c1.ic;

/**
 * 中间代码的有条件转移指令
 */
public class CondJump extends Jump {

	/**	关系类型的常量编号 */
	private static final int LESS = 1;
	private static final int LESS_EQUAL = 2;
	private static final int GREATER = 3;
	private static final int GREATER_EQUAL = 4;
	private static final int EQUAL_EQUAL = 5;
	private static final int NOT_EQUAL = 6;
	
	private final int relation;
	
	private final Operand operand1;
	
	private final Operand operand2;
	
	public CondJump(int relation, Operand operand1, Operand operand2,
			Label target) {
		super(target);
		this.relation = relation;
		this.operand1 = operand1;
		this.operand2 = operand2;
	}

	@Override
	public void accept(ICVisitor visitor) throws Exception {
		visitor.visit(this);
	}

	@Override
	public int getICType() {
		return COND_JUMP;
	}

	public int getRelation() {
		return relation;
	}

	public Operand getOperand1() {
		return operand1;
	}

	public Operand getOperand2() {
		return operand2;
	}

}
