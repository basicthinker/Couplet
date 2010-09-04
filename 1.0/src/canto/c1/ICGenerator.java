package canto.c1;

import canto.c1.ast.ASTNode;
import canto.c1.ast.ASTScanner;
import canto.c1.ast.AddExpression;
import canto.c1.ast.AndExpression;
import canto.c1.ast.AssignmentStatement;
import canto.c1.ast.Block;
import canto.c1.ast.EqualExpression;
import canto.c1.ast.Expression;
import canto.c1.ast.ExpressionStatement;
import canto.c1.ast.GreaterEqualExpression;
import canto.c1.ast.GreaterExpression;
import canto.c1.ast.Identifier;
import canto.c1.ast.IfStatement;
import canto.c1.ast.InputStatement;
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
import canto.c1.error.ErrorRecord;
import canto.c1.error.CompileException;

import canto.c1.ic.Add;
import canto.c1.ic.Goto;
import canto.c1.ic.In;
import canto.c1.ic.InstructionList;
import canto.c1.ic.IntegerLiteral;
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
 * 
 * @author Goodness
 * 
 */
public class ICGenerator extends ASTScanner implements canto.ICGenerator {

	/** 输入的抽象语法树 */
	private canto.AbstractSyntaxTree ast;

	/** 产生的代码序列 */
	private InstructionList instructionList;

	/** C1的符号表，此处是个名值对照表 */
	private SymbolTable<Location> symbolTable;

	public ICGenerator() {
		instructionList = new InstructionList();
		symbolTable = new SymbolTable<Location>();
	}

	@Override
	public void generateIC() throws CompileException {
		try {
			((ASTNode) ast).accept(this);
		} catch (Exception e) {
			throw new CompileException(ErrorRecord.icGenerateError());
		}
	}

	@Override
	public InstructionList getIC() {
		return instructionList;
	}

	@Override
	public void setAST(canto.AbstractSyntaxTree AST) {
		this.ast = AST;
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
		super.visit(node);
		Operand src = (Operand) node.getExpression().getProperty("result");
		Location dst;
		if (symbolTable.isExist(node.getLocation().getName())) {
			dst = symbolTable.get(node.getLocation().getName());
		} else {
			dst = new Temp();
			symbolTable.put(node.getLocation().getName(), dst);
		}
		Mov mov = new Mov(src, dst);
		instructionList.add(mov);
	}

	// ExpressionStatement ?
	@Override
	public void visit(ExpressionStatement node) throws Exception {
		super.visit(node);
	}

	@Override
	public void visit(IfStatement node) throws Exception {

		// 新建条件正误时候跳转的Label,先设置子节点的falseLabel
		Label falseLabel = new Label();
		node.getCondition().setProperty("falseLabel", falseLabel);
		node.getCondition().accept(this);

		// 如果子节点需要trueLabel由子节点自己进行设置
		Label trueLabel = (Label) node.getCondition().getProperty("trueLabel");
		if (trueLabel != null) {
			instructionList.add(trueLabel);
		}
		Statement elseStatement = node.getElseStatement();
		// 结尾Label
		if (elseStatement != null) { // 有else语句
			node.getThenStatement().accept(this);

			// 走完if后去end
			Label endLabel = new Label();
			Goto gotoEnd = new Goto(endLabel);
			instructionList.add(gotoEnd);

			// else位置
			instructionList.add(falseLabel);
			elseStatement.accept(this);

			instructionList.add(endLabel);

		} else { // 没有else的语句

			// 符合条件时的走向
			node.getThenStatement().accept(this);
			instructionList.add(falseLabel);
		}

		// 结尾Label放入语句序列

	}

	@Override
	public void visit(WhileStatement node) throws Exception {

		// while循环开始处
		Label startLabel = new Label();
		node.getCondition().setProperty("startLabel", startLabel);
		instructionList.add(startLabel);

		// 新建条件符合或不符合的Label
		Label falseLabel = new Label();
		node.getCondition().setProperty("falseLabel", falseLabel);

		node.getCondition().accept(this);

		// 同if
		Label trueLabel = (Label) node.getCondition().getProperty("trueLabel");
		if (trueLabel != null) {
			instructionList.add(trueLabel);
		}
		node.getBody().accept(this);

		Goto unCondJump = new Goto(startLabel);
		instructionList.add(unCondJump);

		// 条件不符合后跳出的Label
		instructionList.add(falseLabel);
	}

	@Override
	public void visit(InputStatement node) throws Exception {
		super.visit(node);
		canto.c1.ast.Location astLocation = node.getLocation();
		Location icLocation = symbolTable.get(astLocation.getName());
		In in = new In(icLocation);
		instructionList.add(in);
	}

	@Override
	public void visit(OutputStatement node) throws Exception {
		super.visit(node);
		Operand operandResult = (Operand) node.getExpression().getProperty("result");
		Out out = new Out(operandResult);
		instructionList.add(out);
	}

	@Override
	public void visit(PosExpression node) throws Exception {
		super.visit(node);
		Label trueLabel = (Label) node.getProperty("trueLabel");
		Label falseLabel = (Label) node.getProperty("falseLabel");

		// 判断是否都有必要跳转
		if (trueLabel != null && falseLabel != null) {
			JNE jne = new JNE(
					(Operand) node.getOperand().getProperty("result"),
					new IntegerLiteral(0), trueLabel);
			instructionList.add(jne);
			Goto jmp = new Goto(falseLabel);
			instructionList.add(jmp);
		} else if (trueLabel != null) {
			JNE jne = new JNE(
					(Operand) node.getOperand().getProperty("result"),
					new IntegerLiteral(0), trueLabel);
			instructionList.add(jne);
		} else if (falseLabel != null) {
			JEQ jeq = new JEQ(
					(Operand) node.getOperand().getProperty("result"),
					new IntegerLiteral(0), falseLabel);
			instructionList.add(jeq);
		} else {
			Operand value = (Operand) node.getOperand().getProperty("result");
			node.setProperty("result", value);
		}
	}

	@Override
	public void visit(NegExpression node) throws Exception {
		super.visit(node);
		Operand operandResult = (Operand) node.getOperand().getProperty("result");
		Temp result = new Temp();
		Neg neg = new Neg(operandResult, result);
		node.setProperty("result", result);
		instructionList.add(neg);
		addLogicInstruction(node);
	}

	@Override
	public void visit(NotExpression node) throws Exception {
		Label trueLabel = (Label) node.getProperty("trueLabel");
		Label falseLabel = (Label) node.getProperty("falseLabel");
		if (trueLabel == null && falseLabel == null) {
			super.visit(node);
			Label lable = new Label();
			JEQ jeq = new JEQ((Operand) node.getOperand().getProperty("result"),
					new IntegerLiteral(0), lable);
			Temp result = new Temp();
			instructionList.add(new Mov(new IntegerLiteral(1),
					result));
			instructionList.add(jeq);
			instructionList.add(new Mov(new IntegerLiteral(0),
					result));
			instructionList.add(lable);
			node.setProperty("result", result);
		} else {
			node.getOperand().setProperty("trueLabel", falseLabel);
			node.getOperand().setProperty("falseLabel", trueLabel);
			super.visit(node);
		}
	}

	@Override
	public void visit(AddExpression node) throws Exception {
		super.visit(node);
		Operand leftResult = (Operand) node.getLeftOperand().getProperty("result");
		Operand rightResult = (Operand) node.getRightOperand().getProperty("result");
		Temp result = new Temp();
		Add add = new Add(leftResult, rightResult, result);
		instructionList.add(add);
		node.setProperty("result", result);
		addLogicInstruction(node);
	}

	@Override
	public void visit(SubExpression node) throws Exception {
		super.visit(node);
		Operand leftResult = (Operand) node.getLeftOperand().getProperty("result");
		Operand rightResult = (Operand) node.getRightOperand().getProperty("result");
		Temp result = new Temp();
		Sub sub = new Sub(leftResult, rightResult, result);
		instructionList.add(sub);
		node.setProperty("result", result);
		addLogicInstruction(node);
	}

	@Override
	public void visit(MulExpression node) throws Exception {
		super.visit(node);
		Operand leftResult = (Operand) node.getLeftOperand().getProperty("result");
		Operand rightResult = (Operand) node.getRightOperand().getProperty("result");
		Temp result = new Temp();
		Mul mul = new Mul(leftResult, rightResult, result);
		instructionList.add(mul);
		node.setProperty("result", result);
		addLogicInstruction(node);
	}

	@Override
	public void visit(LessExpression node) throws Exception {
		super.visit(node);
		Operand leftResult = (Operand) node.getLeftOperand().getProperty("result");
		Operand rightResult = (Operand) node.getRightOperand().getProperty("result");

		Label trueLabel = (Label) node.getProperty("trueLabel");
		Label falseLabel = (Label) node.getProperty("falseLabel");

		// 判断是否都有必要跳转
		if (trueLabel != null && falseLabel != null) {
			JLT jlt = new JLT(leftResult, rightResult, trueLabel);
			instructionList.add(jlt);
			Goto jmp = new Goto(falseLabel);
			instructionList.add(jmp);
		} else if (trueLabel != null) {
			JLT jlt = new JLT(leftResult, rightResult, trueLabel);
			instructionList.add(jlt);
		} else if (falseLabel != null) {
			JGE jge = new JGE(leftResult, rightResult, falseLabel);
			instructionList.add(jge);
		} else {// 如果trueLable和falseLable都没有，说明是表达式的一部分，返回表达式的结果
			Label lable = new Label();
			JLT jlt = new JLT(leftResult, rightResult, lable);
			Temp result = new Temp();
			instructionList.add(new Mov(new IntegerLiteral(1), result));
			instructionList.add(jlt);
			instructionList.add(new Mov(new IntegerLiteral(0), result));
			instructionList.add(lable);
			node.setProperty("result", result);
		}
	}

	@Override
	public void visit(LessEqualExpression node) throws Exception {
		super.visit(node);
		Operand leftResult = (Operand) node.getLeftOperand().getProperty("result");
		Operand rightResult = (Operand) node.getRightOperand().getProperty("result");

		Label trueLabel = (Label) node.getProperty("trueLabel");
		Label falseLabel = (Label) node.getProperty("falseLabel");

		// 判断是否都有必要跳转
		if (trueLabel != null && falseLabel != null) {
			JLE jle = new JLE(leftResult, rightResult, trueLabel);
			instructionList.add(jle);
			Goto jmp = new Goto(falseLabel);
			instructionList.add(jmp);
		} else if (trueLabel != null) {
			JLE jle = new JLE(leftResult, rightResult, trueLabel);
			instructionList.add(jle);
		} else if (falseLabel != null) {
			JGT jgt = new JGT(leftResult, rightResult, falseLabel);
			instructionList.add(jgt);
		} else {
			Label lable = new Label();
			JLE jle = new JLE(leftResult, rightResult, lable);
			Temp result = new Temp();
			instructionList.add(new Mov(new IntegerLiteral(1), result));
			instructionList.add(jle);
			instructionList.add(new Mov(new IntegerLiteral(0), result));
			instructionList.add(lable);
			node.setProperty("result", result);
		}
	}

	@Override
	public void visit(GreaterExpression node) throws Exception {
		super.visit(node);
		Operand leftResult = (Operand) node.getLeftOperand().getProperty("result");
		Operand rightResult = (Operand) node.getRightOperand().getProperty("result");

		Label trueLabel = (Label) node.getProperty("trueLabel");
		Label falseLabel = (Label) node.getProperty("falseLabel");

		// 判断是否都有必要跳转
		if (trueLabel != null && falseLabel != null) {
			JGT jgt = new JGT(leftResult, rightResult, trueLabel);
			instructionList.add(jgt);
			Goto jmp = new Goto(falseLabel);
			instructionList.add(jmp);
		} else if (trueLabel != null) {
			JGT jgt = new JGT(leftResult, rightResult, trueLabel);
			instructionList.add(jgt);
		} else if (falseLabel != null) {
			JLE jle = new JLE(leftResult, rightResult, falseLabel);
			instructionList.add(jle);
		} else {
			Label lable = new Label();
			JGT jgt = new JGT(leftResult, rightResult, lable);
			Temp result = new Temp();
			instructionList.add(new Mov(new IntegerLiteral(1), result));
			instructionList.add(jgt);
			instructionList.add(new Mov(new IntegerLiteral(0), result));
			instructionList.add(lable);
			node.setProperty("result", result);
		}
	}

	@Override
	public void visit(GreaterEqualExpression node) throws Exception {
		super.visit(node);
		Operand leftResult = (Operand) node.getLeftOperand().getProperty("result");
		Operand rightResult = (Operand) node.getRightOperand().getProperty("result");

		Label trueLabel = (Label) node.getProperty("trueLabel");
		Label falseLabel = (Label) node.getProperty("falseLabel");

		// 判断是否都有必要跳转
		if (trueLabel != null && falseLabel != null) {
			JGE jge = new JGE(leftResult, rightResult, trueLabel);
			instructionList.add(jge);
			Goto jmp = new Goto(falseLabel);
			instructionList.add(jmp);
		} else if (trueLabel != null) {
			JGE jge = new JGE(leftResult, rightResult, trueLabel);
			instructionList.add(jge);
		} else if (falseLabel != null) {
			JLT jlt = new JLT(leftResult, rightResult, falseLabel);
			instructionList.add(jlt);
		} else {
			Label lable = new Label();
			JGE jge = new JGE(leftResult, rightResult, lable);
			Temp result = new Temp();
			instructionList.add(new Mov(new IntegerLiteral(1), result));
			instructionList.add(jge);
			instructionList.add(new Mov(new IntegerLiteral(0), result));
			instructionList.add(lable);
			node.setProperty("result", result);
		}
	}

	@Override
	public void visit(EqualExpression node) throws Exception {
		super.visit(node);
		Operand leftResult = (Operand) node.getLeftOperand().getProperty("result");
		Operand rightResult = (Operand) node.getRightOperand().getProperty("result");

		Label trueLabel = (Label) node.getProperty("trueLabel");
		Label falseLabel = (Label) node.getProperty("falseLabel");

		// 判断是否都有必要跳转
		if (trueLabel != null && falseLabel != null) {
			JEQ jeq = new JEQ(leftResult, rightResult, trueLabel);
			instructionList.add(jeq);
			Goto jmp = new Goto(falseLabel);
			instructionList.add(jmp);
		} else if (trueLabel != null) {
			JEQ jeq = new JEQ(leftResult, rightResult, trueLabel);
			instructionList.add(jeq);
		} else if (falseLabel != null) {
			JNE jne = new JNE(leftResult, rightResult, falseLabel);
			instructionList.add(jne);
		} else {
			Label lable = new Label();
			JEQ jeq = new JEQ(leftResult, rightResult, lable);
			Temp result = new Temp();
			instructionList.add(new Mov(new IntegerLiteral(1), result));
			instructionList.add(jeq);
			instructionList.add(new Mov(new IntegerLiteral(0), result));
			instructionList.add(lable);
			node.setProperty("result", result);
		}
	}

	@Override
	public void visit(NotEqualExpression node) throws Exception {
		super.visit(node);
		Operand leftOperand = (Operand) node.getLeftOperand().getProperty(
				"result");
		Operand rightOperand = (Operand) node.getRightOperand().getProperty(
				"result");

		Label trueLabel = (Label) node.getProperty("trueLabel");
		Label falseLabel = (Label) node.getProperty("falseLabel");

		// 判断是否都有必要跳转
		if (trueLabel != null && falseLabel != null) {
			JNE jne = new JNE(leftOperand, rightOperand, trueLabel);
			instructionList.add(jne);
			Goto jmp = new Goto(falseLabel);
			instructionList.add(jmp);
		} else if (trueLabel != null) {
			JNE jne = new JNE(leftOperand, rightOperand, trueLabel);
			instructionList.add(jne);
		} else if (falseLabel != null) {
			JEQ jeq = new JEQ(leftOperand, rightOperand, falseLabel);
			instructionList.add(jeq);
		} else {
			Label lable = new Label();
			JNE jne = new JNE(leftOperand, rightOperand, lable);
			Temp result = new Temp();
			instructionList.add(new Mov(new IntegerLiteral(1), result));
			instructionList.add(jne);
			instructionList.add(new Mov(new IntegerLiteral(0), result));
			instructionList.add(lable);
			node.setProperty("result", result);
		}
	}

	@Override
	public void visit(AndExpression node) throws Exception {
		Label trueLabel = (Label) node.getProperty("trueLable");
		Label falseLabel = (Label) node.getProperty("falseLabel");
		Expression leftOperand = node.getLeftOperand();
		Expression rightOperand = node.getRightOperand();
		if (trueLabel == null && falseLabel == null) {
			/*
			 * 如果trueLabel和falseLabel都是null，说明是表达式的一部分 实现方式伪代码如下： result=0
			 * if(leftOperand==false) goto label if(rightOperand==false) goto
			 * labell result=1 label：
			 */
			Temp result = new Temp();
			instructionList.add(new Mov(new IntegerLiteral(0), result));
			Label label = new Label();
			leftOperand.setProperty("falseLabel", label);
			leftOperand.accept(this);
			Label leftTrue = (Label) leftOperand.getProperty("trueLabel");
			if (leftTrue != null) {
				instructionList.add(leftTrue);
			}
			rightOperand.setProperty("falseLabel", label);
			rightOperand.accept(this);
			instructionList.add(new Mov(new IntegerLiteral(1), result));
			instructionList.add(label);
			node.setProperty("result", result);
		} else {
			// trueLabel和falseLabel有一个为空，则为条件判断的一部分
			// 左结点的false就是结果的false
			// 如果结果的false是空，就是可穿越，需要新建
			if (falseLabel == null) falseLabel = new Label();
			
			node.setProperty("falseLabel", falseLabel);
			leftOperand.setProperty("falseLabel", falseLabel);
			leftOperand.accept(this);

			Label leftTrue = (Label) leftOperand.getProperty("trueLabel");

			// 不一定自然可进入右结点，得看右结点是否需要Label
			if (leftTrue != null) instructionList.add(leftTrue);

			rightOperand.setProperty("trueLabel", trueLabel);
			rightOperand.setProperty("falseLabel", falseLabel);
			rightOperand.accept(this);
		}
	}

	@Override
	public void visit(OrExpression node) throws Exception {
		Label trueLabel = (Label) node.getProperty("trueLabel");
		Label falseLabel = (Label) node.getProperty("falseLabel");
		Expression leftOperand = node.getLeftOperand();
		Expression rightOperand = node.getRightOperand();
		if (trueLabel == null && falseLabel == null) {
			/*
			 * 如果trueLabel和falseLabel都是null，说明是表达式的一部分 实现方式伪代码如下 result=1
			 * if(leftOperand==true) goto label if(rightOperand==true) goto
			 * label result=0 label：
			 */
			Temp result = new Temp();
			instructionList.add(new Mov(new IntegerLiteral(1), result));
			Label label = new Label();
			leftOperand.setProperty("trueLabel", label);
			leftOperand.accept(this);
			Label leftFalse = (Label) node.getLeftOperand().getProperty("falseLabel");
			if (leftFalse != null) instructionList.add(leftFalse);
			rightOperand.setProperty("trueLabel", label);
			rightOperand.accept(this);
			instructionList.add(new Mov(new IntegerLiteral(0), result));
			instructionList.add(label);
			node.setProperty("result", result);
		} else {
			// 左结点的true就是结果的true
			// 如果结果的true是空，就是可穿越，需要新建
			if (trueLabel == null) trueLabel = new Label();

			node.setProperty("trueLabel", trueLabel);
			leftOperand.setProperty("trueLabel", trueLabel);
			leftOperand.accept(this);

			Label leftFalse = (Label) node.getLeftOperand().getProperty("falseLabel");

			// 不一定自然进入右结点，要看左节点是否需要相应的Label，需要的话就加上
			if (leftFalse != null) instructionList.add(leftFalse);
			rightOperand.setProperty("trueLabel", trueLabel);
			rightOperand.setProperty("falseLabel", falseLabel);
			rightOperand.accept(this);
		}
	}

	@Override
	public void visit(Identifier node) throws Exception {
		Label trueLabel = (Label) node.getProperty("trueLabel");
		Label falseLabel = (Label) node.getProperty("falseLabel");
		if (trueLabel != null && falseLabel != null) {
			if (symbolTable.isExist(node.getName())) {
				Location location = symbolTable.get(node.getName());
				JNE jne = new JNE(location, new IntegerLiteral(0), trueLabel);
				instructionList.add(jne);
				Goto jmp = new Goto(falseLabel);
				instructionList.add(jmp);
			} else {
				Variable variable = new Variable();
				symbolTable.put(node.getName(), variable);
				Goto jmp = new Goto(falseLabel);
				instructionList.add(jmp);
			}
		} else if (trueLabel != null) {
			if (symbolTable.isExist(node.getName())) {
				Location location = symbolTable.get(node.getName());
				JNE jne = new JNE(location, new IntegerLiteral(0), trueLabel);
				instructionList.add(jne);
			}
		} else if (falseLabel != null) {
			if (symbolTable.isExist(node.getName())) {
				Location location = symbolTable.get(node.getName());
				JEQ jeq = new JEQ(location, new IntegerLiteral(0), falseLabel);
				instructionList.add(jeq);
			} else {
				Variable variable = new Variable();
				symbolTable.put(node.getName(), variable);
				Goto jmp = new Goto(falseLabel);
				instructionList.add(jmp);
			}
		} else {
			if (symbolTable.isExist(node.getName())) {
				Location location = symbolTable.get(node.getName());
				node.setProperty("result", location);
			} else {
				Variable variable = new Variable();
				symbolTable.put(node.getName(), variable);
				node.setProperty("result", variable);
			}
		}
	}

	@Override
	public void visit(canto.c1.ast.IntegerLiteral node) throws Exception {
		IntegerLiteral integerLiteral = new IntegerLiteral(node.getValue());
		node.setProperty("result", integerLiteral);
	}
	
	/**
	 * 在表达式的末尾增加将其看作逻辑值的控制流语句
	 * @param node 表达式结点
	 */
	private void addLogicInstruction(Expression node) {
		Label trueLabel = (Label) node.getProperty("trueLabel");
		Label falseLabel = (Label) node.getProperty("falseLabel");
		Operand result = (Operand) node.getProperty("result");
		if (trueLabel != null && falseLabel != null) {
			JNE jne = new JNE(result, new IntegerLiteral(0), trueLabel);
			instructionList.add(jne);
			Goto jmp = new Goto(falseLabel);
			instructionList.add(jmp);
		} else if (trueLabel != null) {
			JNE jne = new JNE(result, new IntegerLiteral(0), trueLabel);
			instructionList.add(jne);
		} else if (falseLabel != null) {
			JEQ jeq = new JEQ(result, new IntegerLiteral(0), falseLabel);
			instructionList.add(jeq);
		}
	}
	
}
