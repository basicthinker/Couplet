package canto.c1.ast;

/**
 * 该类用于打印AST结构
 *     以缩进方式打印，子结点跟在父结点之下，并比父结点多一个缩进。
 */
public class ASTPrinter extends ASTScanner {
	
	int depth = 0;
	
	@Override
	public void visit(Program node) {
		for (int i = 0; i < depth; i++) System.out.print("\t");
		System.out.println("Prgoram :");
		depth++;
		super.visit(node);
		depth--;
	}

	@Override
	public void visit(Block node) {
		for (int i = 0; i < depth; i++) System.out.print("\t");
		System.out.println("Block :");
		depth++;
		super.visit(node);
		depth--;
	}
	
	@Override
	public void visit(StatementList node) {
		for (int i = 0; i < depth; i++) System.out.print("\t");
		System.out.println("StatementList :");
		depth++;
		super.visit(node);
		depth--;
	}

	@Override
	public void visit(Declaration node) {
		for (int i = 0; i < depth; i++) System.out.print("\t");
		System.out.println("Declaration :");
		depth++;
		super.visit(node);
		depth--;
	}

	@Override
	public void visit(AssignmentStatement node) {
		for (int i = 0; i < depth; i++) System.out.print("\t");
		System.out.println("Assignment :");
		depth++;
		super.visit(node);
		depth--;
	}

	@Override
	public void visit(IfStatement node) {
		for (int i = 0; i < depth; i++) System.out.print("\t");
		System.out.println("If :");
		depth++;
		super.visit(node);
		depth--;
	}

	@Override
	public void visit(WhileStatement node) {
		for (int i = 0; i < depth; i++) System.out.print("\t");
		System.out.println("While :");
		depth++;
		super.visit(node);
		depth--;
	}

	@Override
	public void visit(InputStatement node) {
		for (int i = 0; i < depth; i++) System.out.print("\t");
		System.out.println("Input :");
		depth++;
		super.visit(node);
		depth--;
	}

	@Override
	public void visit(OutputStatement node) {
		for (int i = 0; i < depth; i++) System.out.print("\t");
		System.out.println("Output :");
		depth++;
		super.visit(node);
		depth--;
	}
	
	@Override
	public void visit(UnaryExpression node) {
		for (int i = 0; i < depth; i++) System.out.print("\t");
		System.out.println("Unary Expression :");
		depth++;
		super.visit(node);
		depth--;
	}

	@Override
	public void visit(BinaryExpression node) {
		for (int i = 0; i < depth; i++) System.out.print("\t");
		System.out.println("Binary Expression :");
		depth++;
		super.visit(node);
		depth--;
	}

	@Override
	public void visit(Identifier node) {
		for (int i = 0; i < depth; i++) System.out.print("\t");
		System.out.println("Identifier : " + node.getName() + "");
		depth++;
		super.visit(node);
		depth--;
	}

	@Override
	public void visit(IntegerLiteral node) {
		for (int i = 0; i < depth; i++) System.out.print("\t");
		System.out.println("Literal : " + node.getValue() + "");
		depth++;
		super.visit(node);
		depth--;
	}
	
	@Override
	public void visit(PrimitiveType node) {
		for (int i = 0; i < depth; i++) System.out.print("\t");
		System.out.println("Type : " + node.getTypeCode());
		depth++;
		super.visit(node);
		depth--;
	}


	@Override
	public void visit(UnaryOperator node) {
		for (int i = 0; i < depth; i++) System.out.print("\t");
		System.out.println("Unary Operator : " + node.getOperatorCode());
		depth++;
		super.visit(node);
		depth--;
	}

	@Override
	public void visit(BinaryOperator node) {
		for (int i = 0; i < depth; i++) System.out.print("\t");
		System.out.println("Binary Operator : " + node.getOperatorCode());
		depth++;
		super.visit(node);
		depth--;
	}
}
