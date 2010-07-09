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
import canto.c1.ic.Goto;
import canto.c1.ic.In;
import canto.c1.ic.InstructionList;
import canto.c1.ic.JEQ;
import canto.c1.ic.JGE;
import canto.c1.ic.JGT;
import canto.c1.ic.JLE;
import canto.c1.ic.JLT;
import canto.c1.ic.JNE;
import canto.c1.ic.Label;
import canto.c1.ic.Location;
import canto.c1.ic.Mov;
import canto.c1.ic.Mul;
import canto.c1.ic.Neg;
import canto.c1.ic.Operand;
import canto.c1.ic.Out;
import canto.c1.ic.Sub;
import canto.c1.ic.Temp;
import canto.c1.ic.Variable;

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
	private SymbolTable<Location> symbolTable;

	public ICGenerator(AbstractSyntaxTree abstractSyntaxTree){
		this.ast=abstractSyntaxTree;
		instructionList=new InstructionList();
		symbolTable=new SymbolTable<Location>();
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
		Location dst;
		if(symbolTable.isExist(node.getAccess().getName())){
			dst=symbolTable.get(node.getAccess().getName());
		}
		else{
			dst=new Temp();
			symbolTable.put(node.getAccess().getName(), dst);
		}
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
		
		//新建条件正误时候跳转的Label,先设置子节点的falseLabel
		Label falseLabel=new Label();
		node.getCondition().setProperty("falseLabel", falseLabel);
		node.getCondition().accept(this);
		
		//如果子节点需要trueLabel由子节点自己进行设置
		Label trueLabel=(Label) node.getCondition().getProperty("trueLabel");
		if(trueLabel!=null){
			instructionList.addInstruction(trueLabel);
		}
		Statement elseStatement = node.getElseStatement();
		//结尾Label
		if (elseStatement != null){ //有else语句
			node.getThenStatement().accept(this);
			
			//走完if后去end
			Label endLabel=new Label();
			Goto gotoEnd=new Goto(endLabel);
			instructionList.addInstruction(gotoEnd);
			
			//else位置
			instructionList.addInstruction(falseLabel);	
			elseStatement.accept(this);

			instructionList.addInstruction(endLabel);

		}
		else{ //没有else的语句
			
			//符合条件时的走向
			node.getThenStatement().accept(this);
			instructionList.addInstruction(falseLabel);
		}
		
		//结尾Label放入语句序列
				
	}

	@Override
	public void visit(WhileStatement node) throws CantoException {
		
		//while循环开始处
		Label startLabel=new Label();
		node.getCondition().setProperty("startLabel", startLabel);
		instructionList.addInstruction(startLabel);
		
		//新建条件符合或不符合的Label
		Label falseLabel=new Label();
		node.getCondition().setProperty("falseLabel", falseLabel);
		
		
		node.getCondition().accept(this);
		
		//同if
		Label trueLabel=(Label) node.getCondition().getProperty("trueLabel");
		if(trueLabel!=null){
			instructionList.addInstruction(trueLabel);
		}
		node.getBody().accept(this);
		
		Goto unCondJump=new Goto(startLabel);
		instructionList.addInstruction(unCondJump);
		
		//条件不符合后跳出的Label
		instructionList.addInstruction(falseLabel);
	}
	
	@Override
	public void visit(BreakStatement node) throws CantoException {
		ASTNode whileLocator=node;
		//找到上层While语句
		while(whileLocator.getNodeType()!=ASTNode.WHILE_STATEMENT)
		{
			whileLocator=whileLocator.getParent();
		}
		//转化成一个WhileStatement
		Label label =(Label)((WhileStatement)whileLocator).getCondition().getProperty("falseLabel");
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
		Label label =(Label)((WhileStatement)whileLocator).getCondition().getProperty("startLabel");
		Goto unCondJump=new Goto(label);
		instructionList.addInstruction(unCondJump);
	}
	
	@Override
	public void visit(InputStatement node) throws CantoException {
		super.visit(node);
		Access access=node.getAccess();
		Location location=symbolTable.get(access.getName());
		In in=new In(location);
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
		node.getOperand().setProperty("trueLabel", (Label)node.getProperty("falseLabel"));
		node.getOperand().setProperty("falseLabel", (Label)node.getProperty("trueLabel"));
		super.visit(node);
	}
	
	@Override
	public void visit(AddExpression node) throws CantoException {
		super.visit(node);
		Operand leftOperand=(Operand)node.getLeftOperand().getProperty("result");
		Operand rightOperand=(Operand)node.getRightOperand().getProperty("result");
		Temp result=new Temp();
		Add add=new Add(leftOperand, rightOperand, result);
		instructionList.addInstruction(add);
		node.setProperty("result", result);
	}
	
	@Override
	public void visit(SubExpression node) throws CantoException {
		super.visit(node);
		Operand leftOperand=(Operand)node.getLeftOperand().getProperty("result");
		Operand rightOperand=(Operand)node.getRightOperand().getProperty("result");
		Temp result=new Temp();
		Sub sub=new Sub(leftOperand, rightOperand, result);
		instructionList.addInstruction(sub);
		node.setProperty("result", result);
	}
	
	@Override
	public void visit(MulExpression node) throws CantoException {
		super.visit(node);
		Operand leftOperand=(Operand)node.getLeftOperand().getProperty("result");
		Operand rightOperand=(Operand)node.getRightOperand().getProperty("result");
		Temp result=new Temp();
		Mul mul=new Mul(leftOperand, rightOperand, result);
		instructionList.addInstruction(mul);
		node.setProperty("result", result);
	}

	@Override
	public void visit(LessExpression node) throws CantoException {
		super.visit(node);
		Operand leftOperand=(Operand)node.getLeftOperand().getProperty("result");
		Operand rightOperand=(Operand)node.getRightOperand().getProperty("result");
		
		Label trueLabel=(Label)node.getProperty("trueLabel");
		Label falseLabel=(Label)node.getProperty("falseLabel");
		
		
		//判断是否都有必要跳转
		if(trueLabel!=null&&falseLabel!=null){
			JLT jlt=new JLT(leftOperand, rightOperand, trueLabel);
			instructionList.addInstruction(jlt);
			Goto jmp=new Goto(falseLabel);
			instructionList.addInstruction(jmp);
		}
		else if(trueLabel!=null){
			JLT jlt=new JLT(leftOperand, rightOperand, trueLabel);
			instructionList.addInstruction(jlt);
		}
		else if(falseLabel!=null){
			JGE jge=new JGE(leftOperand, rightOperand, falseLabel);
			instructionList.addInstruction(jge);			
		}
	}
	
	@Override
	public void visit(LessEqualExpression node) throws CantoException {
		super.visit(node);
		Operand leftOperand=(Operand)node.getLeftOperand().getProperty("result");
		Operand rightOperand=(Operand)node.getRightOperand().getProperty("result");
		
		Label trueLabel=(Label)node.getProperty("trueLabel");
		Label falseLabel=(Label)node.getProperty("falseLabel");
		
		
		//判断是否都有必要跳转
		if(trueLabel!=null&&falseLabel!=null){
			JLE jle=new JLE(leftOperand, rightOperand, trueLabel);
			instructionList.addInstruction(jle);
			Goto jmp=new Goto(falseLabel);
			instructionList.addInstruction(jmp);
		}
		else if(trueLabel!=null){
			JLE jle=new JLE(leftOperand, rightOperand, trueLabel);
			instructionList.addInstruction(jle);
		}
		else if(falseLabel!=null){
			JGT jgt=new JGT(leftOperand, rightOperand, falseLabel);
			instructionList.addInstruction(jgt);			
		}

	}

	@Override
	public void visit(GreaterExpression node) throws CantoException {
		super.visit(node);
		Operand leftOperand=(Operand)node.getLeftOperand().getProperty("result");
		Operand rightOperand=(Operand)node.getRightOperand().getProperty("result");
		
		Label trueLabel=(Label)node.getProperty("trueLabel");
		Label falseLabel=(Label)node.getProperty("falseLabel");
		
		
		//判断是否都有必要跳转
		if(trueLabel!=null&&falseLabel!=null){
			JGT jgt=new JGT(leftOperand, rightOperand, trueLabel);
			instructionList.addInstruction(jgt);
			Goto jmp=new Goto(falseLabel);
			instructionList.addInstruction(jmp);
		}
		else if(trueLabel!=null){
			JGT jgt=new JGT(leftOperand, rightOperand, trueLabel);
			instructionList.addInstruction(jgt);
		}
		else if(falseLabel!=null){
			JLE jle=new JLE(leftOperand, rightOperand, falseLabel);
			instructionList.addInstruction(jle);			
		}
	}

	@Override
	public void visit(GreaterEqualExpression node) throws CantoException {
		super.visit(node);
		Operand leftOperand=(Operand)node.getLeftOperand().getProperty("result");
		Operand rightOperand=(Operand)node.getRightOperand().getProperty("result");
		
		Label trueLabel=(Label)node.getProperty("trueLabel");
		Label falseLabel=(Label)node.getProperty("falseLabel");
		
		
		//判断是否都有必要跳转
		if(trueLabel!=null&&falseLabel!=null){
			JGE jge=new JGE(leftOperand, rightOperand, trueLabel);
			instructionList.addInstruction(jge);
			Goto jmp=new Goto(falseLabel);
			instructionList.addInstruction(jmp);
		}
		else if(trueLabel!=null){
			JGE jge=new JGE(leftOperand, rightOperand, trueLabel);
			instructionList.addInstruction(jge);
		}
		else if(falseLabel!=null){
			JLT jlt=new JLT(leftOperand, rightOperand, falseLabel);
			instructionList.addInstruction(jlt);			
		}


	}

	@Override
	public void visit(EqualExpression node) throws CantoException {
		super.visit(node);
		Operand leftOperand=(Operand)node.getLeftOperand().getProperty("result");
		Operand rightOperand=(Operand)node.getRightOperand().getProperty("result");
		
		Label trueLabel=(Label)node.getProperty("trueLabel");
		Label falseLabel=(Label)node.getProperty("falseLabel");
		
		
		//判断是否都有必要跳转
		if(trueLabel!=null&&falseLabel!=null){
			JEQ jeq=new JEQ(leftOperand, rightOperand, trueLabel);
			instructionList.addInstruction(jeq);
			Goto jmp=new Goto(falseLabel);
			instructionList.addInstruction(jmp);
		}
		else if(trueLabel!=null){
			JEQ jeq=new JEQ(leftOperand, rightOperand, trueLabel);
			instructionList.addInstruction(jeq);
		}
		else if(falseLabel!=null){
			JNE jne=new JNE(leftOperand, rightOperand, falseLabel);
			instructionList.addInstruction(jne);			
		}

	}

	@Override
	public void visit(NotEqualExpression node) throws CantoException {
		super.visit(node);
		Operand leftOperand=(Operand)node.getLeftOperand().getProperty("result");
		Operand rightOperand=(Operand)node.getRightOperand().getProperty("result");
		
		Label trueLabel=(Label)node.getProperty("trueLabel");
		Label falseLabel=(Label)node.getProperty("falseLabel");
		
		
		//判断是否都有必要跳转
		if(trueLabel!=null&&falseLabel!=null){
			JNE jne=new JNE(leftOperand, rightOperand, trueLabel);
			instructionList.addInstruction(jne);
			Goto jmp=new Goto(falseLabel);
			instructionList.addInstruction(jmp);
		}
		else if(trueLabel!=null){
			JNE jne=new JNE(leftOperand, rightOperand, trueLabel);
			instructionList.addInstruction(jne);
		}
		else if(falseLabel!=null){
			JEQ jeq=new JEQ(leftOperand, rightOperand, falseLabel);
			instructionList.addInstruction(jeq);			
		}

	}

	@Override
	public void visit(AndExpression node) throws CantoException {
		//左结点的false就是结果的false
		Label falseLabel=(Label)node.getProperty("falseLabel");
		//如果结果的false是空，就是可穿越，需要新建
		if(falseLabel==null){
			falseLabel=new Label();
		}
		node.setProperty("falseLabel", falseLabel);
		node.getLeftOperand().setProperty("falseLabel", falseLabel);
		node.getLeftOperand().accept(this);

		Label leftTrue=(Label)node.getLeftOperand().getProperty("trueLabel");
		node.getLeftOperand().setProperty("Label", leftTrue);
		
		node.getRightOperand().setProperty("trueLabel", node.getProperty("trueLabel"));
		node.getRightOperand().setProperty("falseLabel", node.getProperty("falseLabel"));
		
		//设置下层需要此层设置过Label，如果此层的上层是if语句或者while语句，说明没设置过，则进行设置
		if(node.getParent().getNodeType()==ASTNode.IF_STATEMENT
				||node.getParent().getNodeType()==ASTNode.WHILE_STATEMENT){
			node.setProperty("Label", node.getProperty("trueLabel"));
		}
		node.getRightOperand().setProperty("Label",node.getProperty("Label"));

		//不一定自然可进入右结点，得看右结点是否需要Label
		if(leftTrue!=null){
			instructionList.addInstruction(leftTrue);
		}
		node.getRightOperand().accept(this);
	}
	
	@Override
	public void visit(OrExpression node) throws CantoException {
		//左结点的true就是结果的true
		Label trueLabel=(Label)node.getProperty("trueLabel");
		//如果结果的true是空，就是可穿越，需要新建
		if(trueLabel==null){
			trueLabel=new Label();
		}
		node.setProperty("trueLabel", trueLabel);
		node.getLeftOperand().setProperty("trueLabel", trueLabel);
		node.getLeftOperand().accept(this);

		Label leftFalse=(Label)node.getLeftOperand().getProperty("falseLabel");
		node.getLeftOperand().setProperty("Label", leftFalse);

		node.getRightOperand().setProperty("trueLabel", node.getProperty("trueLabel"));
		node.getRightOperand().setProperty("falseLabel", node.getProperty("falseLabel"));
		
		//设置下层需要此层设置过Label，如果此层的上层是if语句或者while语句，说明没设置过，则进行设置
		if(node.getParent().getNodeType()==ASTNode.IF_STATEMENT
				||node.getParent().getNodeType()==ASTNode.WHILE_STATEMENT){
			node.setProperty("Label", node.getProperty("trueLabel"));
		}
		node.getRightOperand().setProperty("Label", node.getProperty("Label"));

		//不一定自然进入右结点，要看左节点是否需要相应的Label，需要的话就加上
		if(leftFalse!=null){
			instructionList.addInstruction(leftFalse);
		}
		node.getRightOperand().accept(this);
	}
	
	@Override
	public void visit(Identifier node) throws CantoException {
		if(symbolTable.isExist(node.getName())){
			Location location=symbolTable.get(node.getName());
			node.setProperty("result", location);
		}
		else{
			Variable variable=new Variable();
			symbolTable.put(node.getName(), variable);
			node.setProperty("result", variable);
		}
	}

	@Override
	public void visit(IntegerLiteral node) throws CantoException {
		canto.c1.ic.IntegerLiteral integerLiteral=new canto.c1.ic.IntegerLiteral(node.getValue());		
		node.setProperty("result", integerLiteral);
		}
}
