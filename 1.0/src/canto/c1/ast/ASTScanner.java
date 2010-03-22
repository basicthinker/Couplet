package canto.c1.ast;

/**
 * AST的扫描器
 *   该扫描器实现了AST访问者接口；
 *   每个结点的visit方法仅递归访问它的所有子结点；
 *   该类的子类可以重写每个visit方法以加入在访问该结点时的操作，将super.visit()置于
 * 访问操作代码之后可以实现一个先序遍历，将super.visit()置于访问操作代码之前可以实现
 * 一个后序遍历。 
 */
public class ASTScanner implements ASTVisitor {

	@Override
	public void visit(Program node) throws Exception {
		node.getBody().accept(this);
	}

	@Override
	public void visit(Block node) throws Exception {
		node.getStatementList().accept(this);
	}
	
	@Override
	public void visit(StatementList node) throws Exception {
		for (Listable item : node.getList()) {
			if (item instanceof Declaration) {
				((Declaration) item).accept(this);
			} else {
				((Statement) item).accept(this);
			}
		}
	}
	
	@Override
	public void visit(Declaration node) throws Exception {
		node.getType().accept(this);
		node.getIdentifier().accept(this);
	}

	@Override
	public void visit(AssignmentStatement node) throws Exception {
		node.getIdentifier().accept(this);
		node.getExpression().accept(this);
	}

	@Override
	public void visit(IfStatement node) throws Exception {
		node.getCondition().accept(this);
		node.getThenStatement().accept(this);
		Statement elseStatement = node.getElseStatement();
		if (elseStatement != null) elseStatement.accept(this);
	}

	@Override
	public void visit(WhileStatement node) throws Exception {
		node.getCondition().accept(this);
		node.getBody().accept(this);
	}

	@Override
	public void visit(InputStatement node) throws Exception {
		node.getIdentifier().accept(this);
	}

	@Override
	public void visit(OutputStatement node) throws Exception {
		node.getExpression().accept(this);
	}
	
	@Override
	public void visit(UnaryExpression node) throws Exception {
		node.getOperator().accept(this);
		node.getOperand().accept(this);
	}

	@Override
	public void visit(BinaryExpression node) throws Exception {
		node.getLeftOperand().accept(this);
		node.getOperator().accept(this);
		node.getRightOperand().accept(this);
	}

	@Override
	public void visit(Identifier node) throws Exception {
	}

	@Override
	public void visit(IntegerLiteral node) throws Exception {
	}
	
	@Override
	public void visit(IntegerType node) throws Exception {		
	}

	@Override
	public void visit(BooleanType node) throws Exception {
	}

	@Override
	public void visit(UnaryOperator node) throws Exception {
	}

	@Override
	public void visit(BinaryOperator node) throws Exception {
	}

}
