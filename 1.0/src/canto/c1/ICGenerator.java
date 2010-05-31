package canto.c1;

import canto.AbstractSyntaxTree;
import canto.CantoException;
import canto.c1.ast.ASTNode;
import canto.c1.ast.Access;
import canto.c1.ast.AddExpression;
import canto.c1.ast.AndExpression;
import canto.c1.ast.AssignmentStatement;
import canto.c1.ast.Block;
import canto.c1.ast.BreakStatement;
import canto.c1.ast.ContinueStatement;
import canto.c1.ast.EqualExpression;
import canto.c1.ast.ExpressionStatement;
import canto.c1.ast.GreaterEqualExpression;
import canto.c1.ast.GreaterExpression;
import canto.c1.ast.Identifier;
import canto.c1.ast.IfStatement;
import canto.c1.ast.InputStatement;
import canto.c1.ast.IntegerLiteral;
import canto.c1.ast.LessEqualExpression;
import canto.c1.ast.LessExpression;
import canto.c1.ast.MulExpression;
import canto.c1.ast.NegExpression;
import canto.c1.ast.NotEqualExpression;
import canto.c1.ast.NotExpression;
import canto.c1.ast.OrExpression;
import canto.c1.ast.OutputStatement;
import canto.c1.ast.PosExpression;
import canto.c1.ast.Program;
import canto.c1.ast.Statement;
import canto.c1.ast.StatementList;
import canto.c1.ast.SubExpression;
import canto.c1.ast.WhileStatement;

import canto.c1.ic.Add;
import canto.c1.ic.Arithmetic;
import canto.c1.ic.BinaryArithmetic;
import canto.c1.ic.CJump;
import canto.c1.ic.Goto;
import canto.c1.ic.HashTable;
import canto.c1.ic.In;
import canto.c1.ic.Instruction;
import canto.c1.ic.InstructionList;
import canto.c1.ic.JEQ;
import canto.c1.ic.JGE;
import canto.c1.ic.JGT;
import canto.c1.ic.JLE;
import canto.c1.ic.JLT;
import canto.c1.ic.JNE;
import canto.c1.ic.Jump;
import canto.c1.ic.Label;
import canto.c1.ic.Literal;
import canto.c1.ic.Location;
import canto.c1.ic.Mov;
import canto.c1.ic.Mul;
import canto.c1.ic.Neg;
import canto.c1.ic.Operand;
import canto.c1.ic.Out;
import canto.c1.ic.Sub;
import canto.c1.ic.Temp;
import canto.c1.ic.UnaryArithmetic;

/**
 * c1中生成中间代码的类
 * @author Goodness
 *
 */
public class ICGenerator extends canto.c1.ast.ASTScanner implements canto.ICGenerator {

	/**输入的抽象语法树*/
	private AbstractSyntaxTree ast;
	
	/**产生的代码序列*/
	private InstructionList instructionList;
	
	/**C1的符号表，此处是个名值对照表*/
	private HashTable hashTable;

	public ICGenerator(AbstractSyntaxTree abstractSyntaxTree){
		this.ast=abstractSyntaxTree;
		instructionList=new InstructionList();
		hashTable=new HashTable();
	}
	
	@Override
	public InstructionList generateIC() throws CantoException {
		((ASTNode) ast).accept(this);
		return instructionList;
	}

	@Override
	public InstructionList getIC() {		
		return instructionList;
	}

	@Override
	public void setAST(AbstractSyntaxTree AST) {
		this.ast=AST;
	}
	
	@Override
	public void visit(Program node) throws CantoException {
		super.visit(node);
	}

	@Override
	public void visit(StatementList node) throws CantoException {
		super.visit(node);
	}
	
	@Override
	public void visit(Block node) throws CantoException {
		super.visit(node);
	}

	@Override
	public void visit(AssignmentStatement node) throws CantoException {
		super.visit(node);
		Operand src=(Operand)node.getExpression().getProperty("result");
		Operand dst=hashTable.getLocation(node.getAccess());
		Mov mov = new Mov(src, dst);
		instructionList.addInstruction(mov);
	}
	
	//ExpressionStatement ?
	@Override
	public void visit(ExpressionStatement node) throws CantoException {
		super.visit(node);
	}

	@Override
	public void visit(IfStatement node) throws CantoException {
		
		//新建条件正误时候跳转的Label
		Label wrongLabel=new Label();
		node.setProperty("wrongLabel", wrongLabel);
		
		node.getCondition().accept(this);

		Statement elseStatement = node.getElseStatement();
		//结尾Label
		Label endLabel=new Label();
		if (elseStatement != null){ //有else语句
			node.getThenStatement().accept(this);
			
			//走完if后去end
			Goto gotoEnd=new Goto(endLabel);
			instructionList.addInstruction(gotoEnd);
			
			//else位置
			instructionList.addInstruction(wrongLabel);	
			elseStatement.accept(this);

			instructionList.addInstruction(endLabel);

		}
		else{ //没有else的语句
			
			//符合条件时的走向
			node.getThenStatement().accept(this);
			instructionList.addInstruction(wrongLabel);
		}
		
		//结尾Label放入语句序列
				
	}

	@Override
	public void visit(WhileStatement node) throws CantoException {
		
		//while循环开始处
		Label startLabel=new Label();
		node.setProperty("startLabel", startLabel);
		instructionList.addInstruction(startLabel);
		
		//新建条件符合或不符合的Label
		Label wrongLabel=new Label();
		node.setProperty("wrongLabel", wrongLabel);		
		
		node.getCondition().accept(this);
		
		node.getBody().accept(this);
		
		Goto unCondJump=new Goto(startLabel);
		instructionList.addInstruction(unCondJump);
		
		//条件不符合后跳出的Label
		instructionList.addInstruction(wrongLabel);
	}
	
	@Override
	public void visit(BreakStatement node) throws CantoException {
		Statement whileLocator=node;
		while(whileLocator.getNodeType()!=ASTNode.WHILE_STATEMENT)
		{
			whileLocator=(Statement)whileLocator.getParent();
		}
		Label label =(Label)whileLocator.getProperty("wrongLabel");
		Goto unCondJump=new Goto(label);
		instructionList.addInstruction(unCondJump);
	}

	@Override
	public void visit(ContinueStatement node) throws CantoException {
		Statement whileLocator=node;
		while(whileLocator.getNodeType()!=ASTNode.WHILE_STATEMENT)
		{
			whileLocator=(Statement)whileLocator.getParent();
		}
		Label label =(Label)whileLocator.getProperty("startLabel");
		Goto unCondJump=new Goto(label);
		instructionList.addInstruction(unCondJump);
	}
	
	@Override
	public void visit(InputStatement node) throws CantoException {
		super.visit(node);
		Access access=node.getAccess();
		Temp temp;
		if(hashTable.isExist(access)){
			temp=(Temp)hashTable.getLocation(access);
		}
		else{
			temp=new Temp();
			hashTable.insertSymbol(access, temp);
		}
		In in=new In(temp);
		instructionList.addInstruction(in);
	}

	@Override
	public void visit(OutputStatement node) throws CantoException {
		super.visit(node);
		Operand operand=(Operand)node.getExpression().getProperty("result");
		Out out=new Out(operand);
		instructionList.addInstruction(out);
	}
	
	@Override
	public void visit(PosExpression node) throws CantoException {
		super.visit(node);
		Operand value=(Operand) node.getOperand().getProperty("result");
		node.setProperty("result", value);
	}

	@Override
	public void visit(NegExpression node) throws CantoException {
		super.visit(node);
		Operand operand=(Operand)node.getOperand().getProperty("result");
		Location result=new Temp();
		Neg neg=new Neg(operand, result);
		node.setProperty("result", result);
		instructionList.addInstruction(neg);
	}
	
	@Override
	public void visit(NotExpression node) throws CantoException {
		Label wrongLabel= (Label)node.getParent().getProperty("wrongLabel");
		canto.c1.ic.IntegerLiteral zero=new canto.c1.ic.IntegerLiteral(0);
		//JNE jne=new JNE(0,0,new Label());
		super.visit(node);
	}
	
	@Override
	public void visit(AddExpression node) throws CantoException {
		super.visit(node);
	}
	
	@Override
	public void visit(SubExpression node) throws CantoException {
		System.out.println("Sub :");
		super.visit(node);
	}
	
	@Override
	public void visit(MulExpression node) throws CantoException {
		System.out.println("Mul :");
		super.visit(node);
	}

	@Override
	public void visit(LessExpression node) throws CantoException {
		System.out.println("Less :");
		super.visit(node);
	}
	
	@Override
	public void visit(LessEqualExpression node) throws CantoException {
		System.out.println("Less Equal :");
		super.visit(node);
	}

	@Override
	public void visit(GreaterExpression node) throws CantoException {
		System.out.println("Greater :");
		super.visit(node);
	}

	@Override
	public void visit(GreaterEqualExpression node) throws CantoException {
		System.out.println("Greater Equal :");
		super.visit(node);
	}

	@Override
	public void visit(EqualExpression node) throws CantoException {
		System.out.println("Equal :");
		super.visit(node);
	}

	@Override
	public void visit(NotEqualExpression node) throws CantoException {
		System.out.println("Not Equal :");
		super.visit(node);
	}

	@Override
	public void visit(AndExpression node) throws CantoException {
		System.out.println("And :");
		super.visit(node);
	}
	
	@Override
	public void visit(OrExpression node) throws CantoException {
		System.out.println("Or :");
		super.visit(node);
	}
	
	@Override
	public void visit(Identifier node) throws CantoException {
		System.out.println("Identifier : " + node.getName() + "");
		super.visit(node);
	}

	@Override
	public void visit(IntegerLiteral node) throws CantoException {
		System.out.println("Literal : " + node.getValue() + "");
		super.visit(node);
		}
	

}
