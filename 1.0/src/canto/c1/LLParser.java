package canto.c1;

import java.util.List;
import java.util.ListIterator;

import canto.CantoException;
import canto.c1.token.Token;
import canto.c1.ast.Access;
import canto.c1.ast.AddExpression;
import canto.c1.ast.AndExpression;
import canto.c1.ast.AssignmentStatement;
import canto.c1.ast.Block;
import canto.c1.ast.BreakStatement;
import canto.c1.ast.ContinueStatement;
import canto.c1.ast.EqualExpression;
import canto.c1.ast.Expression;
import canto.c1.ast.GreaterEqualExpression;
import canto.c1.ast.GreaterExpression;
import canto.c1.ast.Identifier;
import canto.c1.ast.IfStatement;
import canto.c1.ast.InputStatement;
import canto.c1.ast.IntegerLiteral;
import canto.c1.ast.LessEqualExpression;
import canto.c1.ast.LessExpression;
import canto.c1.ast.Literal;
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
import canto.c1.exception.ParseException;

public class LLParser implements canto.Parser {

	/** 生成的AST根结点 */
	private canto.AbstractSyntaxTree treeRoot;
	
	/** 输入的Token链 */
	private List<canto.Token> tokenList;
	
	/** 用于从前向后遍历Token链的迭代器 */
	private ListIterator<canto.Token> tokenIterator;
	
	/** 存放待分析的下一个Token */
	private canto.Token nextToken;
	
	/** 存放nextToken所在的行 */
	private int line;
	
	/** 存放nextToken所在的列 */
	private int column;
	
	/** 存放nextToken的类型编码 */
	private int tokenType;
	
	@Override
	public canto.AbstractSyntaxTree getAST() {
		return treeRoot;
	}

	@Override
	public void setTokenList(List<canto.Token> tokenList) {
		this.tokenList = tokenList;
	}

	@Override
	public canto.AbstractSyntaxTree parse() throws Exception {
		tokenIterator = tokenList.listIterator();
		move();
		try	{
			treeRoot = program();
		} catch(ParseException e) {
			System.out.println(e.getExceptionMsg());
		}
		return treeRoot;
	}
	
	/**
	 * 向后移动一个Token
	 */
	private void move() {
		if (tokenIterator.hasNext()) {
			// 获取下一个Token
			nextToken = tokenIterator.next();
			// 获取下一个Token所在的行列号
			line = nextToken.getLine();
			column = nextToken.getColumn();
			// 获取下一个Token的类型编码
			tokenType = nextToken.getTokenType();
		} else {
			nextToken = null;
		}
	}
	
	
	/**
	 * 匹配某一指定的Token，并且向后移动
	 * @param tokenType 指定的Token类型代码
	 * @throws ParseException
	 */
	private void match(int tokenType) throws ParseException {
		if (nextToken == null)
			throw new ParseException();
		if (nextToken.getTokenType() == tokenType) {
			move();
		} else {
			throw new ParseException();
		}
	}
	
	/**
	 * 向下推导非终极符program
	 * <program> ::= <block>
	 * @return 程序的AST结点
	 * @throws ParseException
	 */
	private Program program() throws ParseException {
		Program program =  new Program(block(), line, column);

		// 判断确实已经分析到Token链尾
		if (nextToken == null) return program;
		else throw new ParseException();
	}
	
	/**
	 * 向下推导非终极符block
	 * <block> ::= "{" <stmt_list> "}"
	 * @return BLOCK语句的AST结点
	 * @throws ParseException
	 */
	private Block block() throws ParseException {
		try
		{
			match(Token.L_BRACE);
		}
		catch(ParseException e)
		{
			ParseException pe = new ParseException(line, column, ParseException.MissingRightBrace, CantoException.LevelError);
			System.out.println(pe.getExceptionMsg());
		}
		Block block = new Block(stmt_list(), line, column);
		try
		{
			match(Token.R_BRACE);
		}
		catch(ParseException e)
		{
			ParseException pe = new ParseException(line, column, ParseException.MissingLeftBrace, CantoException.LevelError);
			System.out.println(pe.getExceptionMsg());
		}
		return block;
	}
	
	/**
	 * 向下推导非终极符stmt_list
	 * <stmt_list> ::= <stmt_list> <stmt> | ε
	 * @return 语句列表的AST结点
	 * @throws ParseException
	 */
	private StatementList stmt_list() throws ParseException {
		StatementList stmt_list = new StatementList(line, column);
		while (tokenType == Token.L_BRACE || tokenType == Token.ID || 
				tokenType == Token.IF || tokenType == Token.WHILE || 
				tokenType == Token.BREAK || tokenType == Token.CONTINUE || 
				tokenType == Token.INPUT || tokenType == Token.OUTPUT) {
			try{
				stmt_list.addStatement(stmt());
			}
			catch(ParseException e){
				ParseException pe = new ParseException(line, column, ParseException.MissingEQUAL, CantoException.LevelError);
				System.out.println(pe.getExceptionMsg());
				
				/*TODO 错误处理*/
				while(true){
					move();
					if(tokenType == Token.R_PARENT || tokenType == Token.R_BRACE || tokenType == Token.SEMI)
						break;
				}
				move();
			}
			
		}
		return stmt_list;
	}
	
	/**
	 * 向下推导非终极符stmt
	 * <stmt> ::= <block> | <assign_stmt> | <if_stmt> | <while_stmt> | <input_stmt> | <output_stmt>
	 * @return 语句的AST结点
	 * @throws ParseException
	 */
	private Statement stmt() throws ParseException {
		switch (tokenType) {
		case Token.L_BRACE :
			return block();
		case Token.ID :
			return assign_stmt();
		case Token.IF :
			return if_stmt();
		case Token.WHILE :
			return while_stmt();
		case Token.BREAK :
			return break_stmt();
		case Token.CONTINUE :
			return continue_stmt();
		case Token.INPUT :			
			return input_stmt();
		case Token.OUTPUT :
			return output_stmt();
		default:
			/*TODO 完成错误恢复代码 */

			throw new ParseException(line, column, ParseException.IllegalStatement, CantoException.LevelError);
			
		}
	}
	
	/**
	 * 向下推导非终极符assign_stmt
	 * <assign_stmt> ::= <access> "=" <expr> ";"
	 * @return 赋值语句的AST结点
	 * @throws ParseException
	 */
	private AssignmentStatement assign_stmt() throws ParseException {
		Access access = access();

		try{
			match(Token.EQUAL);
		}
		catch(ParseException e){
			ParseException pe = new ParseException(line, column, ParseException.MissingEQUAL, CantoException.LevelError);
			System.out.println(pe.getExceptionMsg());
		}
		
		AssignmentStatement stmt = new AssignmentStatement(access, expr(), line, column);
	
		try{
			match(Token.SEMI);
		}
		catch(ParseException e){
			ParseException pe = new ParseException(line, column, ParseException.MissingSemicolon, CantoException.LevelError);
			System.out.println(pe.getExceptionMsg());
		}
		return stmt;
	}
	
	/**
	 * 向下推导非终极符if_stmt
	 * "if" "(" <expr> ")" <stmt> | "if" "(" <expr> ")" <stmt> "else" <stmt>
	 * @return IF语句的AST结点
	 * @throws ParseException
	 */
	private IfStatement if_stmt() throws ParseException {
		match(Token.IF);
		try
		{
			match(Token.L_PARENT);
		}
		catch(ParseException e)
		{
			ParseException pe = new ParseException(line, column, ParseException.MissingLeftParenthesis, CantoException.LevelError);
			System.out.println(pe.getExceptionMsg());
		}
		Expression expr = expr();
		try
		{
			match(Token.R_PARENT);
		}
		catch(ParseException e)
		{
			ParseException pe = new ParseException(line, column, ParseException.MissingRightParenthesis, CantoException.LevelError);
			System.out.println(pe.getExceptionMsg());
		}
		Statement thenStmt = stmt();
		Statement elseStmt = null;
		if (tokenType == Token.ELSE) {
			match(Token.ELSE);
			elseStmt = stmt();
			return new IfStatement(expr, thenStmt, elseStmt, line, column);
		} else {
			return new IfStatement(expr, thenStmt, line, column);
		}
		
	}
	
	/**
	 * 向下推导非终极符while_stmt
	 * <while_stmt> ::= "while" "(" <expr> ")" <stmt>
	 * @return WHILE语句的AST结点
	 * @throws ParseException
	 */
	private WhileStatement while_stmt() throws ParseException {
		match(Token.WHILE);
		try{
			match(Token.L_PARENT);
		}
		catch(ParseException e){
			ParseException pe = new ParseException(line, column, ParseException.MissingLeftParenthesis, CantoException.LevelError);
			System.out.println(pe.getExceptionMsg());
		}
		
		Expression expr = expr();
		
		try{
			match(Token.R_PARENT);
		}
		catch(ParseException e){
			ParseException pe = new ParseException(line, column, ParseException.MissingRightParenthesis, CantoException.LevelError);
			System.out.println(pe.getExceptionMsg());
		}
		
		if(tokenType != Token.L_BRACE){
			throw new ParseException(line, column, ParseException.MissingLeftBrace, CantoException.LevelError);
		}
		Statement body = stmt();
		return new WhileStatement(expr, body, line, column);
	}
	
	/**
	 * 向下推导非终极符break_stmt
	 * <break_stmt> ::= "break" ";"
	 * @return BREAK语句的AST结点
	 * @throws ParseException
	 */
	private BreakStatement break_stmt() throws ParseException {
		match(Token.BREAK);
		try
		{
			match(Token.SEMI);
		}
		catch(ParseException e)
		{
			ParseException pe = new ParseException(line, column, ParseException.MissingSemicolon, CantoException.LevelError);
			System.out.println(pe.getExceptionMsg());
		}
		return new BreakStatement(line, column);
	}
	
	/**
	 * 向下推导非终极符continue_stmt
	 * <continue_stmt> ::= "continue" ";"
	 * @return CONTINUE语句的AST结点
	 * @throws ParseException
	 */
	private ContinueStatement continue_stmt() throws ParseException {
		match(Token.CONTINUE);
		try
		{
			match(Token.SEMI);
		}
		catch(ParseException e)
		{
			ParseException pe = new ParseException(line, column, ParseException.MissingSemicolon, CantoException.LevelError);
			System.out.println(pe.getExceptionMsg());
		}
		return new ContinueStatement(line, column);
	}
	
	/**
	 * 向下推导非终极符input_stmt
	 * <input_stmt> ::= "input" "(" <access> ")" ";"
	 * @return 输入语句的AST结点
	 * @throws ParseException
	 */
	private InputStatement input_stmt() throws ParseException {
		match(Token.INPUT);
		try{
			match(Token.L_PARENT);
		}
		catch(ParseException e){
			ParseException pe = new ParseException(line, column, ParseException.MissingLeftParenthesis, CantoException.LevelError);
			System.out.println(pe.getExceptionMsg());
		}
		
		Access access = access();
		
		try{
			match(Token.R_PARENT);
		}
		catch(ParseException e){
			ParseException pe = new ParseException(line, column, ParseException.MissingRightParenthesis, CantoException.LevelError);
			System.out.println(pe.getExceptionMsg());
		}
		
		try{
			match(Token.SEMI);
		}
		catch(ParseException e){
			ParseException pe = new ParseException(line, column, ParseException.MissingSemicolon, CantoException.LevelError);
			System.out.println(pe.getExceptionMsg());
		}
		return new InputStatement(access, line, column);
	}
	
	/**
	 * 向下推导非终极符output_stmt
	 * <output_stmt> ::= "output" "(" <expr> ")" ";"
	 * @return 输出语句的AST结点
	 * @throws ParseException
	 */
	private OutputStatement output_stmt() throws ParseException {
		match(Token.OUTPUT);
		try{
			match(Token.L_PARENT);
		}
		catch(ParseException e){
			ParseException pe = new ParseException(line, column, ParseException.MissingLeftParenthesis, CantoException.LevelError);
			System.out.println(pe.getExceptionMsg());
		}
		
		Expression expr = expr();
		
		try{
			match(Token.R_PARENT);
		}
		catch(ParseException e){
			ParseException pe = new ParseException(line, column, ParseException.MissingRightParenthesis, CantoException.LevelError);
			System.out.println(pe.getExceptionMsg());
		}
		
		try{
			match(Token.SEMI);
		}
		catch(ParseException e){
			ParseException pe = new ParseException(line, column, ParseException.MissingSemicolon, CantoException.LevelError);
			System.out.println(pe.getExceptionMsg());
		}
		return new OutputStatement(expr, line, column);
	}
	
	/**
	 * 向下推导非终极符expr
	 * <expr> ::= <expr> <bi_op_1> <expr_1> | <expr_1>
	 * @return 表达式的AST结点
	 * @throws ParseException
	 */
	private Expression expr() throws ParseException {
		Expression expr = null;
		expr = expr_1();
		while (tokenType == Token.OR_OR) {		
			move();
			expr = new OrExpression(expr, expr_1(), line, column); 
		}
		return expr;
	}
	
	/**
	 * 向下推导非终极符expr_1
	 * <expr_1> ::= <expr_1> <bi_op_2> <expr_2> | <expr_2>
	 * @return 表达式的AST结点
	 * @throws ParseException
	 */	
	private Expression expr_1() throws ParseException {
		Expression expr;
		expr = expr_2();
		while (tokenType == Token.AND_AND) {	
			move();
			expr = new AndExpression(expr, expr_2(), line, column); 
		}
		return expr;
	}
	
	/**
	 * 向下推导非终极符expr_2
	 * <expr_2> ::= <expr_2> <bi_op_3> <expr_3> | <expr_3>
	 * @return 表达式的AST结点
	 * @throws ParseException
	 */	
	private Expression expr_2() throws ParseException {
		Expression expr;
		expr = expr_3();
		boolean flag = true;
		while (flag) {
			switch (tokenType) {
			case Token.EQUAL_EQUAL :
				move();
				expr = new EqualExpression(expr, expr_3(), line, column); 
				break;
			case Token.NOT_EQUAL :
				move();
				expr = new NotEqualExpression(expr, expr_3(), line, column); 
				break;
			default :
				flag = false;
				break;	
			}			
		}
		return expr;
	}
	
	/**
	 * 向下推导非终极符expr_3
	 * <expr_3> ::= <expr_3> <bi_op_4> <expr_4> | <expr_4>
	 * @return 表达式的AST结点
	 * @throws ParseException
	 */	
	private Expression expr_3() throws ParseException {
		Expression expr;
		expr = expr_4();
		boolean flag = true; 
		while (flag) {
			switch (tokenType) {
			case Token.LESS :
				move();
				expr = new LessExpression(expr, expr_4(), line, column);
				break;
			case Token.LESS_EQUAL :
				move();
				expr = new LessEqualExpression(expr, expr_4(), line, column);
				break;
			case Token.GREATER :
				move();
				expr = new GreaterExpression(expr, expr_4(), line, column);
				break;
			case Token.GREATER_EQUAL :
				move();
				expr = new GreaterEqualExpression(expr, expr_4(), line, column);
				break;
			default :
				flag = false;
				break;
			}			 
		}
		return expr;
	}
	
	/**
	 * 向下推导非终极符expr_4
	 * <expr_4> ::= <expr_4> <bi_op_5> <expr_5> | <expr_5>
	 * @return 表达式的AST结点
	 * @throws ParseException
	 */	
	private Expression expr_4() throws ParseException {
		Expression expr;
		expr = expr_5();
		boolean flag = true;
		while (flag) {		
			switch (tokenType) {
			case Token.PLUS :
				move();
				expr = new AddExpression(expr, expr_5(), line, column); 
				break;
			case Token.MINUS :
				move();
				expr = new SubExpression(expr, expr_5(), line, column); 
				break;
			default :
				flag = false;
				break;
			}			
		}
		return expr;
	}
	
	/**
	 * 向下推导非终极符expr_5
	 * <expr_5> ::= <expr_5> <bi_op_6> <expr_6> | <expr_6>
	 * @return 表达式的AST结点
	 * @throws ParseException
	 */	
	private Expression expr_5() throws ParseException {
		Expression expr;
		expr = expr_6();
		while (tokenType == Token.TIMES) {
			move();
			expr = new MulExpression(expr, expr_6(), line, column); 
		}
		return expr;
	}
	
	/**
	 * 向下推导非终极符expr_6
	 * <expr_6> ::= <un_op> <expr_7> | <expr_7>
	 * @return 表达式的AST结点
	 * @throws ParseException
	 */	
	private Expression expr_6() throws ParseException {
		Expression expr;
		switch (tokenType) {
		case Token.PLUS :
			move();
			expr = new PosExpression(expr_7(), line, column);
			break;
		case Token.MINUS : 
			move();
			expr = new NegExpression(expr_7(), line, column);
			break;
		case Token.NOT :
			move();
			expr = new NotExpression(expr_7(), line, column);
			break;
		case Token.L_PARENT : case Token.ID : case Token.INTEGER_LITERAL :
			expr = expr_7();
			break;
		default :
			throw new ParseException();
		}
		return expr;
	}
	
	/**
	 * 向下推导非终极符expr_7
	 * <expr_7> ::= "(" <expr> ")" | <access> | <literal>
	 * @return 表达式的AST结点
	 * @throws ParseException
	 */	
	private Expression expr_7() throws ParseException {
		Expression expr;
		switch (tokenType) {
		case Token.L_PARENT :
			move();
			expr = expr();
			match(Token.R_PARENT);
			break;
		case Token.ID :
			expr = access();
			break;
		case Token.INTEGER_LITERAL :
			expr = literal();
			break;
		default :
			throw new ParseException();
		}
		return expr;
	}
	
	/**
	 * 向下推导非终极符access
	 * <access> ::= id
	 * @return 
	 * @throws ParseException
	 */
	private Access access() throws ParseException {
		if (tokenType == Token.ID) return id();
		else throw new ParseException();
	}
	
	/**
	 * 向下推导非终极符literal
	 * <literal> ::= integer_literal
	 * @return 常量的AST结点
	 * @throws ParseException
	 */
	private Literal literal() throws ParseException {
		if (tokenType == Token.INTEGER_LITERAL) {
			Literal literal = new IntegerLiteral((Integer)nextToken.getAttribute(), line, column);
			move();
			return literal;
		} else {
			throw new ParseException();
		}
	}
	
	/**
	 * 读入非终极符id，将其转换化标识符的AST结点
	 * @return 标识符的AST结点
	 * @throws ParseException
	 */
	private Identifier id() throws ParseException {
		if (tokenType == Token.ID) {
			Identifier id = new Identifier(nextToken.getLexeme(), line, column);
			move();
			return id;
		} else {
			throw new ParseException();
		}
	}

}
