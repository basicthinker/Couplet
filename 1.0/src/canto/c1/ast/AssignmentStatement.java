package canto.c1.ast;

/**
 * 赋值语句结点
 */
public class AssignmentStatement extends Statement {

	/** 赋值语句左侧的符号 */
	private final Access access;
	
	/** 赋值语句右侧的表达式 */
	private final Expression expression;
	
	/**
	 * 构造一个赋值语句
	 * @param access 左侧的符号
	 * @param expression 右侧的表达式
	 */
	public AssignmentStatement(Access access, Expression expression,
			int line, int column) {
		super(line, column);
		this.access = access;
		this.expression = expression;
		access.setParent(this);
		expression.setParent(this);
	}
	
	@Override
	public void accept(ASTVisitor visitor) throws Exception {
		visitor.visit(this);
	}

	@Override
	public int getNodeType() {
		return ASSIGNMENT_STATEMENT;
	}

	/**
	 * 获取赋值语句左侧的符号
	 * @return 左侧的符号
	 */
	public Access getAccess() {
		return access;
	}

	/**
	 * 获取赋值语句右侧的表达式
	 * @return 右侧的表达式
	 */
	public Expression getExpression() {
		return expression;
	}
	
}
