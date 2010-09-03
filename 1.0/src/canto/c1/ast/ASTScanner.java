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
	public void visit(StatementList node) throws Exception {
		for (Statement item : node.getList()) {
			item.accept(this);
		}
	}
	
	@Override
	public void visit(Block node) throws Exception {
		node.getStatementList().accept(this);
	}
	
	@Override
	public void visit(AssignmentStatement node) throws Exception {
		node.getAccess().accept(this);
		node.getExpression().accept(this);
	}
	
	@Override
	public void visit(ExpressionStatement node) throws Exception {
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
	public void visit(BreakStatement node) throws Exception {
	}

	@Override
	public void visit(ContinueStatement node) throws Exception {
	}
	
	@Override
	public void visit(InputStatement node) throws Exception {
		node.getAccess().accept(this);
	}

	@Override
	public void visit(OutputStatement node) throws Exception {
		node.getExpression().accept(this);
	}
	
	@Override
	public void visit(PosExpression node) throws Exception {
		node.getOperand().accept(this);		
	}

	@Override
	public void visit(NegExpression node) throws Exception {
		node.getOperand().accept(this);		
	}

	@Override
	public void visit(NotExpression node) throws Exception {
		node.getOperand().accept(this);		
	}

	@Override
	public void visit(AddExpression node) throws Exception {
		node.getLeftOperand().accept(this);
		node.getRightOperand().accept(this);		
	}

	@Override
	public void visit(SubExpression node) throws Exception {
		node.getLeftOperand().accept(this);
		node.getRightOperand().accept(this);		
	}

	@Override
	public void visit(MulExpression node) throws Exception {
		node.getLeftOperand().accept(this);
		node.getRightOperand().accept(this);		
	}

	@Override
	public void visit(LessExpression node) throws Exception {
		node.getLeftOperand().accept(this);
		node.getRightOperand().accept(this);		
	}

	@Override
	public void visit(LessEqualExpression node) throws Exception {
		node.getLeftOperand().accept(this);
		node.getRightOperand().accept(this);		
	}

	@Override
	public void visit(GreaterExpression node) throws Exception {
		node.getLeftOperand().accept(this);
		node.getRightOperand().accept(this);		
	}

	@Override
	public void visit(GreaterEqualExpression node) throws Exception {
		node.getLeftOperand().accept(this);
		node.getRightOperand().accept(this);		
	}

	@Override
	public void visit(EqualExpression node) throws Exception {
		node.getLeftOperand().accept(this);
		node.getRightOperand().accept(this);		
	}

	@Override
	public void visit(NotEqualExpression node) throws Exception {
		node.getLeftOperand().accept(this);
		node.getRightOperand().accept(this);		
	}

	@Override
	public void visit(AndExpression node) throws Exception {
		node.getLeftOperand().accept(this);
		node.getRightOperand().accept(this);		
	}

	@Override
	public void visit(OrExpression node) throws Exception {
		node.getLeftOperand().accept(this);
		node.getRightOperand().accept(this);		
	}
	
	@Override
	public void visit(Identifier node) throws Exception {
	}

	@Override
	public void visit(IntegerLiteral node) throws Exception {
	}

}
