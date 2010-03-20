package canto.c1.ast;

/**
 * 赋值语句结点
 */
public class AssignmentStatement extends Statement {

	/** 赋值语句左侧的标识符 */
	private final Identifier identifier;
	
	/** 赋值语句右侧的表达式 */
	private final Expression expression;
	
	/**
	 * 构造一个赋值语句
	 * @param identifier 左侧的标识符
	 * @param expression 右侧的表达式
	 */
	public AssignmentStatement(Identifier identifier, Expression expression) {
		this.identifier = identifier;
		this.expression = expression;
	}
	
	@Override
	public void accept(ASTVisitor visitor) {
		visitor.visit(this);
	}

	@Override
	public int getNodeType() {
		return ASSIGNMENT_STATEMENT;
	}

	/**
	 * 获取赋值语句左侧的标识符
	 * @return 左侧的标识符
	 */
	public Identifier getIdentifier() {
		return identifier;
	}

	/**
	 * 获取赋值语句右侧的表达式
	 * @return 右侧的表达式
	 */
	public Expression getExpression() {
		return expression;
	}
	
}
