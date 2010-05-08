package canto.c1.ast;

/**
 * 该类用于打印AST结构
 *     以缩进方式打印，子结点跟在父结点之下，并比父结点多一个缩进。
 */
public class ASTPrinter extends ASTScanner {
	
	int depth = 0;
	
	@Override
	public void visit(Program node) throws Exception {
		for (int i = 0; i < depth; i++) System.out.print("\t");
		System.out.println("Prgoram :");
		depth++;
		super.visit(node);
		depth--;
	}

	@Override
	public void visit(StatementList node) throws Exception {
		for (int i = 0; i < depth; i++) System.out.print("\t");
		System.out.println("StatementList :");
		depth++;
		super.visit(node);
		depth--;
	}
	
	@Override
	public void visit(Block node) throws Exception {
		for (int i = 0; i < depth; i++) System.out.print("\t");
		System.out.println("Block :");
		depth++;
		super.visit(node);
		depth--;
	}

	@Override
	public void visit(AssignmentStatement node) throws Exception {
		for (int i = 0; i < depth; i++) System.out.print("\t");
		System.out.println("Assignment :");
		depth++;
		super.visit(node);
		depth--;
	}
	
	@Override
	public void visit(ExpressionStatement node) throws Exception {
		for (int i = 0; i < depth; i++) System.out.print("\t");
		System.out.println("Expression Statement :");
		depth++;
		super.visit(node);
		depth--;
	}

	@Override
	public void visit(IfStatement node) throws Exception {
		for (int i = 0; i < depth; i++) System.out.print("\t");
		System.out.println("If :");
		depth++;
		super.visit(node);
		depth--;
	}

	@Override
	public void visit(WhileStatement node) throws Exception {
		for (int i = 0; i < depth; i++) System.out.print("\t");
		System.out.println("While :");
		depth++;
		super.visit(node);
		depth--;
	}
	
	@Override
	public void visit(BreakStatement node) throws Exception {
		for (int i = 0; i < depth; i++) System.out.print("\t");
		System.out.println("Break");
	}

	@Override
	public void visit(ContinueStatement node) throws Exception {
		for (int i = 0; i < depth; i++) System.out.print("\t");
		System.out.println("Continue");
	}
	
	@Override
	public void visit(InputStatement node) throws Exception {
		for (int i = 0; i < depth; i++) System.out.print("\t");
		System.out.println("Input :");
		depth++;
		super.visit(node);
		depth--;
	}

	@Override
	public void visit(OutputStatement node) throws Exception {
		for (int i = 0; i < depth; i++) System.out.print("\t");
		System.out.println("Output :");
		depth++;
		super.visit(node);
		depth--;
	}
	
	@Override
	public void visit(UnaryExpression node) throws Exception {
		for (int i = 0; i < depth; i++) System.out.print("\t");
		System.out.println("Unary Expression :");
		depth++;
		super.visit(node);
		depth--;
	}

	@Override
	public void visit(BinaryExpression node) throws Exception {
		for (int i = 0; i < depth; i++) System.out.print("\t");
		System.out.println("Binary Expression :");
		depth++;
		super.visit(node);
		depth--;
	}

	@Override
	public void visit(Identifier node) throws Exception {
		for (int i = 0; i < depth; i++) System.out.print("\t");
		System.out.println("Identifier : " + node.getName() + "");
		depth++;
		super.visit(node);
		depth--;
	}

	@Override
	public void visit(IntegerLiteral node) throws Exception {
		for (int i = 0; i < depth; i++) System.out.print("\t");
		System.out.println("Literal : " + node.getValue() + "");
		depth++;
		super.visit(node);
		depth--;
	}
	
	@Override
	public void visit(UnaryOperator node) throws Exception {
		for (int i = 0; i < depth; i++) System.out.print("\t");
		System.out.println("Unary Operator : " + node.getOperatorCode());
		depth++;
		super.visit(node);
		depth--;
	}

	@Override
	public void visit(BinaryOperator node) throws Exception {
		for (int i = 0; i < depth; i++) System.out.print("\t");
		System.out.println("Binary Operator : " + node.getOperatorCode());
		depth++;
		super.visit(node);
		depth--;
	}
}
