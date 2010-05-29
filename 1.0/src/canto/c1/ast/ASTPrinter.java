package canto.c1.ast;

import canto.CantoException;

/**
 * 该类用于打印AST结构
 *     以缩进方式打印，子结点跟在父结点之下，并比父结点多一个缩进。
 */
public class ASTPrinter extends ASTScanner {
	
	int depth = 0;
	
	@Override
	public void visit(Program node) throws CantoException {
		for (int i = 0; i < depth; i++) System.out.print("\t");
		System.out.println("Prgoram :");
		depth++;
		super.visit(node);
		depth--;
	}

	@Override
	public void visit(StatementList node) throws CantoException {
		for (int i = 0; i < depth; i++) System.out.print("\t");
		System.out.println("StatementList :");
		depth++;
		super.visit(node);
		depth--;
	}
	
	@Override
	public void visit(Block node) throws CantoException {
		for (int i = 0; i < depth; i++) System.out.print("\t");
		System.out.println("Block :");
		depth++;
		super.visit(node);
		depth--;
	}

	@Override
	public void visit(AssignmentStatement node) throws CantoException {
		for (int i = 0; i < depth; i++) System.out.print("\t");
		System.out.println("Assignment :");
		depth++;
		super.visit(node);
		depth--;
	}
	
	@Override
	public void visit(ExpressionStatement node) throws CantoException {
		for (int i = 0; i < depth; i++) System.out.print("\t");
		System.out.println("Expression Statement :");
		depth++;
		super.visit(node);
		depth--;
	}

	@Override
	public void visit(IfStatement node) throws CantoException {
		for (int i = 0; i < depth; i++) System.out.print("\t");
		System.out.println("If :");
		depth++;
		super.visit(node);
		depth--;
	}

	@Override
	public void visit(WhileStatement node) throws CantoException {
		for (int i = 0; i < depth; i++) System.out.print("\t");
		System.out.println("While :");
		depth++;
		super.visit(node);
		depth--;
	}
	
	@Override
	public void visit(BreakStatement node) throws CantoException {
		for (int i = 0; i < depth; i++) System.out.print("\t");
		System.out.println("Break");
	}

	@Override
	public void visit(ContinueStatement node) throws CantoException {
		for (int i = 0; i < depth; i++) System.out.print("\t");
		System.out.println("Continue");
	}
	
	@Override
	public void visit(InputStatement node) throws CantoException {
		for (int i = 0; i < depth; i++) System.out.print("\t");
		System.out.println("Input :");
		depth++;
		super.visit(node);
		depth--;
	}

	@Override
	public void visit(OutputStatement node) throws CantoException {
		for (int i = 0; i < depth; i++) System.out.print("\t");
		System.out.println("Output :");
		depth++;
		super.visit(node);
		depth--;
	}
	
	@Override
	public void visit(PosExpression node) throws CantoException {
		for (int i = 0; i < depth; i++) System.out.print("\t");
		System.out.println("Pos :");
		depth++;
		super.visit(node);
		depth--;
	}

	@Override
	public void visit(NegExpression node) throws CantoException {
		for (int i = 0; i < depth; i++) System.out.print("\t");
		System.out.println("Neg :");
		depth++;
		super.visit(node);
		depth--;
	}
	
	@Override
	public void visit(NotExpression node) throws CantoException {
		for (int i = 0; i < depth; i++) System.out.print("\t");
		System.out.println("Not :");
		depth++;
		super.visit(node);
		depth--;
	}
	
	@Override
	public void visit(AddExpression node) throws CantoException {
		for (int i = 0; i < depth; i++) System.out.print("\t");
		System.out.println("Add :");
		depth++;
		super.visit(node);
		depth--;
	}
	
	@Override
	public void visit(SubExpression node) throws CantoException {
		for (int i = 0; i < depth; i++) System.out.print("\t");
		System.out.println("Sub :");
		depth++;
		super.visit(node);
		depth--;
	}
	
	@Override
	public void visit(MulExpression node) throws CantoException {
		for (int i = 0; i < depth; i++) System.out.print("\t");
		System.out.println("Mul :");
		depth++;
		super.visit(node);
		depth--;
	}

	@Override
	public void visit(LessExpression node) throws CantoException {
		for (int i = 0; i < depth; i++) System.out.print("\t");
		System.out.println("Less :");
		depth++;
		super.visit(node);
		depth--;
	}
	
	@Override
	public void visit(LessEqualExpression node) throws CantoException {
		for (int i = 0; i < depth; i++) System.out.print("\t");
		System.out.println("Less Equal :");
		depth++;
		super.visit(node);
		depth--;
	}

	@Override
	public void visit(GreaterExpression node) throws CantoException {
		for (int i = 0; i < depth; i++) System.out.print("\t");
		System.out.println("Greater :");
		depth++;
		super.visit(node);
		depth--;
	}

	@Override
	public void visit(GreaterEqualExpression node) throws CantoException {
		for (int i = 0; i < depth; i++) System.out.print("\t");
		System.out.println("Greater Equal :");
		depth++;
		super.visit(node);
		depth--;
	}

	@Override
	public void visit(EqualExpression node) throws CantoException {
		for (int i = 0; i < depth; i++) System.out.print("\t");
		System.out.println("Equal :");
		depth++;
		super.visit(node);
		depth--;
	}

	@Override
	public void visit(NotEqualExpression node) throws CantoException {
		for (int i = 0; i < depth; i++) System.out.print("\t");
		System.out.println("Not Equal :");
		depth++;
		super.visit(node);
		depth--;
	}

	@Override
	public void visit(AndExpression node) throws CantoException {
		for (int i = 0; i < depth; i++) System.out.print("\t");
		System.out.println("And :");
		depth++;
		super.visit(node);
		depth--;
	}
	
	@Override
	public void visit(OrExpression node) throws CantoException {
		for (int i = 0; i < depth; i++) System.out.print("\t");
		System.out.println("Or :");
		depth++;
		super.visit(node);
		depth--;
	}
	
	@Override
	public void visit(Identifier node) throws CantoException {
		for (int i = 0; i < depth; i++) System.out.print("\t");
		System.out.println("Identifier : " + node.getName() + "");
		depth++;
		super.visit(node);
		depth--;
	}

	@Override
	public void visit(IntegerLiteral node) throws CantoException {
		for (int i = 0; i < depth; i++) System.out.print("\t");
		System.out.println("Literal : " + node.getValue() + "");
		depth++;
		super.visit(node);
		depth--;
	}
	
}
