package canto.c1;

import canto.AbstractSyntaxTree;
import canto.CantoException;
import canto.IntermediateCode;
import canto.c1.ast.ASTNode;
import canto.c1.ast.ASTPrinter;
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
		node.setProperty("thenLabel", new Label());
		node.setProperty("elseLabel", new Label());
		super.visit(node);
	}

	@Override
	public void visit(WhileStatement node) throws CantoException {
		node.setProperty("bodyLabel", new Label());
		node.setProperty("outLabel", new Label());
		super.visit(node);
	}
	
	@Override
	public void visit(BreakStatement node) throws CantoException {
		
	}

	@Override
	public void visit(ContinueStatement node) throws CantoException {
		System.out.println("Continue");
	}
	
	@Override
	public void visit(InputStatement node) throws CantoException {
		System.out.println("Input :");
		super.visit(node);
	}

	@Override
	public void visit(OutputStatement node) throws CantoException {
		System.out.println("Output :");
		super.visit(node);
	}
	
	@Override
	public void visit(PosExpression node) throws CantoException {
		super.visit(node);
	}

	@Override
	public void visit(NegExpression node) throws CantoException {
		super.visit(node);
		Operand operand=(Operand)node.getOperand().getProperty("result");
		Location result=new Temp();
		Neg neg=new Neg(operand, result);		
		instructionList.addInstruction(neg);
	}
	
	@Override
	public void visit(NotExpression node) throws CantoException {
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
