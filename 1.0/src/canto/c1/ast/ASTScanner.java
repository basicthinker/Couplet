package canto.c1.ast;

import canto.CantoException;

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
	public void visit(Program node) throws CantoException {
		node.getBody().accept(this);
	}

	@Override
	public void visit(StatementList node) throws CantoException {
		for (Statement item : node.getList()) {
			item.accept(this);
		}
	}
	
	@Override
	public void visit(Block node) throws CantoException {
		node.getStatementList().accept(this);
	}
	
	@Override
	public void visit(AssignmentStatement node) throws CantoException {
		node.getAccess().accept(this);
		node.getExpression().accept(this);
	}
	
	@Override
	public void visit(ExpressionStatement node) throws CantoException {
		node.getExpression().accept(this);
	}

	@Override
	public void visit(IfStatement node) throws CantoException {
		node.getCondition().accept(this);
		node.getThenStatement().accept(this);
		Statement elseStatement = node.getElseStatement();
		if (elseStatement != null) elseStatement.accept(this);
	}

	@Override
	public void visit(WhileStatement node) throws CantoException {
		node.getCondition().accept(this);
		node.getBody().accept(this);
	}
	
	@Override
	public void visit(BreakStatement node) throws CantoException {
	}

	@Override
	public void visit(ContinueStatement node) throws CantoException {
	}
	
	@Override
	public void visit(InputStatement node) throws CantoException {
		node.getAccess().accept(this);
	}

	@Override
	public void visit(OutputStatement node) throws CantoException {
		node.getExpression().accept(this);
	}
	
	@Override
	public void visit(PosExpression node) throws CantoException {
		node.getOperand().accept(this);		
	}

	@Override
	public void visit(NegExpression node) throws CantoException {
		node.getOperand().accept(this);		
	}

	@Override
	public void visit(NotExpression node) throws CantoException {
		node.getOperand().accept(this);		
	}

	@Override
	public void visit(AddExpression node) throws CantoException {
		node.getLeftOperand().accept(this);
		node.getRightOperand().accept(this);		
	}

	@Override
	public void visit(SubExpression node) throws CantoException {
		node.getLeftOperand().accept(this);
		node.getRightOperand().accept(this);		
	}

	@Override
	public void visit(MulExpression node) throws CantoException {
		node.getLeftOperand().accept(this);
		node.getRightOperand().accept(this);		
	}

	@Override
	public void visit(LessExpression node) throws CantoException {
		node.getLeftOperand().accept(this);
		node.getRightOperand().accept(this);		
	}

	@Override
	public void visit(LessEqualExpression node) throws CantoException {
		node.getLeftOperand().accept(this);
		node.getRightOperand().accept(this);		
	}

	@Override
	public void visit(GreaterExpression node) throws CantoException {
		node.getLeftOperand().accept(this);
		node.getRightOperand().accept(this);		
	}

	@Override
	public void visit(GreaterEqualExpression node) throws CantoException {
		node.getLeftOperand().accept(this);
		node.getRightOperand().accept(this);		
	}

	@Override
	public void visit(EqualExpression node) throws CantoException {
		node.getLeftOperand().accept(this);
		node.getRightOperand().accept(this);		
	}

	@Override
	public void visit(NotEqualExpression node) throws CantoException {
		node.getLeftOperand().accept(this);
		node.getRightOperand().accept(this);		
	}

	@Override
	public void visit(AndExpression node) throws CantoException {
		node.getLeftOperand().accept(this);
		node.getRightOperand().accept(this);		
	}

	@Override
	public void visit(OrExpression node) throws CantoException {
		node.getLeftOperand().accept(this);
		node.getRightOperand().accept(this);		
	}
	
	@Override
	public void visit(Identifier node) throws CantoException {
	}

	@Override
	public void visit(IntegerLiteral node) throws CantoException {
	}

}
