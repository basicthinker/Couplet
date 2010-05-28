package canto.c1.ic;

import canto.c1.ast.ASTScanner;
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

import canto.c1.ic.Mov;
import canto.c1.ic.InstructionList;


/**
 * AST到中间代码的转化
 * @author Goodness
 *
 */
public class ASTToIC extends ASTScanner {

	/**产生的代码序列*/
	private InstructionList instructionList;
	
	public InstructionList getInstructionList(){
		return instructionList;
	}
	
	@Override
	public void visit(Program node) throws Exception {
		super.visit(node);
	}

	@Override
	public void visit(StatementList node) throws Exception {
		super.visit(node);
	}
	
	@Override
	public void visit(Block node) throws Exception {
		super.visit(node);
	}

	@Override
	public void visit(AssignmentStatement node) throws Exception {
		instructionList=new InstructionList();
		super.visit(node);
	}
	
	@Override
	public void visit(ExpressionStatement node) throws Exception {
		System.out.println("Expression Statement :");
		super.visit(node);
	}

	@Override
	public void visit(IfStatement node) throws Exception {
		System.out.println("If :");
		super.visit(node);
	}

	@Override
	public void visit(WhileStatement node) throws Exception {
		System.out.println("While :");
		super.visit(node);
	}
	
	@Override
	public void visit(BreakStatement node) throws Exception {
		System.out.println("Break");
	}

	@Override
	public void visit(ContinueStatement node) throws Exception {
		System.out.println("Continue");
	}
	
	@Override
	public void visit(InputStatement node) throws Exception {
		System.out.println("Input :");
		super.visit(node);
	}

	@Override
	public void visit(OutputStatement node) throws Exception {
		System.out.println("Output :");
		super.visit(node);
	}
	
	@Override
	public void visit(PosExpression node) throws Exception {
		System.out.println("Pos :");
		super.visit(node);
	}

	@Override
	public void visit(NegExpression node) throws Exception {
		System.out.println("Neg :");
		super.visit(node);
	}
	
	@Override
	public void visit(NotExpression node) throws Exception {
		System.out.println("Not :");
		super.visit(node);
	}
	
	@Override
	public void visit(AddExpression node) throws Exception {
		System.out.println("Add :");
		super.visit(node);
	}
	
	@Override
	public void visit(SubExpression node) throws Exception {
		System.out.println("Sub :");
		super.visit(node);
	}
	
	@Override
	public void visit(MulExpression node) throws Exception {
		System.out.println("Mul :");
		super.visit(node);
	}

	@Override
	public void visit(LessExpression node) throws Exception {
		System.out.println("Less :");
		super.visit(node);
	}
	
	@Override
	public void visit(LessEqualExpression node) throws Exception {
		System.out.println("Less Equal :");
		super.visit(node);
	}

	@Override
	public void visit(GreaterExpression node) throws Exception {
		System.out.println("Greater :");
		super.visit(node);
	}

	@Override
	public void visit(GreaterEqualExpression node) throws Exception {
		System.out.println("Greater Equal :");
		super.visit(node);
	}

	@Override
	public void visit(EqualExpression node) throws Exception {
		System.out.println("Equal :");
		super.visit(node);
	}

	@Override
	public void visit(NotEqualExpression node) throws Exception {
		System.out.println("Not Equal :");
		super.visit(node);
	}

	@Override
	public void visit(AndExpression node) throws Exception {
		System.out.println("And :");
		super.visit(node);
	}
	
	@Override
	public void visit(OrExpression node) throws Exception {
		System.out.println("Or :");
		super.visit(node);
	}
	
	@Override
	public void visit(Identifier node) throws Exception {
		System.out.println("Identifier : " + node.getName() + "");
		super.visit(node);
	}

	@Override
	public void visit(IntegerLiteral node) throws Exception {
		System.out.println("Literal : " + node.getValue() + "");
		super.visit(node);
		}
	
}