package canto.c1;

import canto.AbstractSyntaxTree;
import canto.c1.ast.ASTNode;
import canto.c1.ast.ASTScanner;
import canto.c1.ast.BinaryExpression;
import canto.c1.ast.BinaryOperator;
import canto.c1.ast.Block;
import canto.c1.ast.BooleanType;
import canto.c1.ast.Declaration;
import canto.c1.ast.Identifier;
import canto.c1.ast.IfStatement;
import canto.c1.ast.IntegerLiteral;
import canto.c1.ast.IntegerType;
import canto.c1.ast.Type;
import canto.c1.ast.UnaryExpression;
import canto.c1.ast.UnaryOperator;
import canto.c1.ast.WhileStatement;
import canto.c1.exception.SemanticException;

/**
 * C1语言的语义检查器
 */
public class Checker extends ASTScanner implements canto.Checker {

	private AbstractSyntaxTree ast;
	
	private canto.SymbolTable symbolTable;
	
	@Override
	public AbstractSyntaxTree check() throws Exception {
		symbolTable = new SymbolTable();
		ast.accept(this);
		return ast;
	}

	@Override
	public AbstractSyntaxTree getAST()  {
		return ast;
	}

	@Override
	public void setAST(AbstractSyntaxTree ast) {
		this.ast = ast;
	}

	/**
	 * 检查Block语句
	 */
	@Override
	public void visit(Block node) throws Exception {
		symbolTable.enterScope();
		super.visit(node);
		symbolTable.exitScope();
	}
	
	/**
	 * 检查声明语句
	 */
	@Override
	public void visit(Declaration node) throws Exception {
		boolean succ = symbolTable.put(node.getIdentifier().getName(), node.getType());
		if (!succ) throw new SemanticException();
		super.visit(node);
	}
	
	@Override
	public void visit(IfStatement node) throws Exception {
		super.visit(node);
		if (node.getCondition().getType().getNodeType() != ASTNode.BOOLEAN_TYPE) 
			throw new SemanticException();
	}

	@Override
	public void visit(WhileStatement node) throws Exception {
		super.visit(node);
		if (node.getCondition().getType().getNodeType() != ASTNode.BOOLEAN_TYPE) 
			throw new SemanticException();
	}
	
	@Override
	public void visit(UnaryExpression node) throws Exception {
		super.visit(node);
		UnaryOperator operator = node.getOperator();
		Type oprandType = node.getOperand().getType();
		switch (operator.getOperatorCode()) {
		case UnaryOperator.NOT :
		// 针对操作符为!的单元表达式
			if (oprandType.getNodeType() != ASTNode.BOOLEAN_TYPE)
				throw new SemanticException();
			break;
		case UnaryOperator.POSITIVE : case UnaryOperator.NEGTIVE :
		// 针对操作符为+、-的单元表达式
			if (oprandType.getNodeType() != ASTNode.INTEGER_TYPE)
				throw new SemanticException();
			break;
		default :
			throw new SemanticException();
		}
		node.setType(oprandType);
	}

	@Override
	public void visit(BinaryExpression node) throws Exception {
		super.visit(node);
		BinaryOperator operator = node.getOperator();
		Type leftOprandType = node.getLeftOperand().getType();
		Type rightOprandType = node.getRightOperand().getType();
		switch (operator.getOperatorCode()) {
		case BinaryOperator.PLUS : case BinaryOperator.MINUS : case BinaryOperator.TIMES :
			if (leftOprandType.getNodeType() != rightOprandType.getNodeType() ||
					leftOprandType.getNodeType() != ASTNode.INTEGER_TYPE)
				throw new SemanticException();
			node.setType(leftOprandType);
			break;
		case BinaryOperator.GREATER : case BinaryOperator.GREATER_EQUALS : 
		case BinaryOperator.LESS : case BinaryOperator.LESS_EQUALS : 
		// 针对操作符为+、-、*、<、<=、>、>=的二元表达式
			if (leftOprandType.getNodeType() != rightOprandType.getNodeType() ||
					leftOprandType.getNodeType() != ASTNode.INTEGER_TYPE)
				throw new SemanticException();
			node.setType(new BooleanType());
			break;
		case BinaryOperator.EQUALS : case BinaryOperator.NOT_EQUALS :
		// 针对操作符为==、!=的二元表达式
			if (leftOprandType.getNodeType() != rightOprandType.getNodeType())
				throw new SemanticException();
			node.setType(new BooleanType());
			break;
		default :
			throw new SemanticException();
		}
	}

	@Override
	public void visit(Identifier node) throws Exception {
		Type type = (Type) symbolTable.get(node.getName());
		if (type == null) throw new SemanticException();
		node.setType(type);
	}

	@Override
	public void visit(IntegerLiteral node) {
		node.setType(new IntegerType());
	}

}
