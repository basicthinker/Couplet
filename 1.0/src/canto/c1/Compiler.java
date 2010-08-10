package canto.c1;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.List;

import canto.TargetCode;
import canto.c1.ast.ASTNode;
import canto.c1.ast.ASTPrinter;
import canto.c1.ic.ICPrinter;
import canto.c1.ic.IntermediateCode;
import canto.c1.x86.IntelEmitter;
import canto.c1.x86.X86TargetCode;

/**
 * @author basicthinker
 *
 */
public class Compiler implements canto.Compiler {

	private InputStreamReader sourceReader;
	private OutputStreamWriter targetWriter;
	private canto.Lexer lexer;
	private canto.Parser parser;
	private canto.ICGenerator icGenerator;
	private canto.TCGenerator tcGenerator;
	
	/**
	 * 构造一个编译器
	 */
	public Compiler() {
		sourceReader = null;
		targetWriter = null;
		lexer = new Lexer();
		parser = new LLParser();
		icGenerator = new ICGenerator();
		tcGenerator = new TCGenerator();
	}

	/* (non-Javadoc)
	 * @see canto.Compiler#setSource(java.io.InputStream)
	 */
	@Override
	public void setSource(InputStream inStrm) {
		sourceReader = new InputStreamReader(inStrm);
	}

	/* (non-Javadoc)
	 * @see canto.Compiler#setTarget(java.io.OutputStream)
	 */
	@Override
	public void setTarget(OutputStream outStrm) {
		targetWriter = new OutputStreamWriter(outStrm);
	}
	
	/* (non-Javadoc)
	 * @see canto.Compiler#compile()
	 */
	@Override
	public void compile() throws Exception {
		// TODO Auto-generated method stub
		if (sourceReader == null) return;		
		try {
			lexer.open(sourceReader);
			lexer.scan();
			parser.setTokenList(lexer.getTokenList());
			parser.parse();
			icGenerator.setAST(parser.getAST());
			icGenerator.generateIC();
			tcGenerator.setIC(icGenerator.getIC());
			tcGenerator.generateTC();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/* (non-Javadoc)
	 * @see canto.Compiler#outputErrors(java.io.OutputStream)
	 */
	@Override
	public void outputErrors(OutputStream outStrm) {
		// TODO 输出错误
	}

	/* (non-Javadoc)
	 * @see canto.Compiler#getTokenList()
	 */
	@Override
	public List<canto.Token> getTokenList() {
		return lexer.getTokenList();
	}

	/* (non-Javadoc)
	 * @see canto.Compiler#getSyntaxTree()
	 */
	@Override
	public canto.AbstractSyntaxTree getAST() {
		return parser.getAST();
	}
	
	/* (non-Javadoc)
	 * @see canto.Compiler#getIntermediateCode()
	 */
	@Override
	public canto.IntermediateCode getIC() {
		return icGenerator.getIC();
	}
	
	@Override
	public canto.TargetCode getTC() {
		return tcGenerator.getTC();
	}

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		try {
			
			// 读入源代码文件
			FileInputStream inFile = new FileInputStream("C1-Sample.canto");
			Compiler compiler = new Compiler();
			compiler.setSource(inFile);

			// 编译
			compiler.compile();
			
			// 输出Token链
			List<canto.Token> tokenList = compiler.getTokenList();
			System.out.println("Output Token List");
			for (canto.Token token : tokenList) {
				System.out.print("Line " + token.getLine() + " Column " + token.getColumn() + ": ");
				System.out.print(token.getLexeme() + "\twith ");
				if (token.getAttribute() == null) System.out.println("null");
				else System.out.println(token.getAttribute().toString());
			}
			System.out.println();
			
			// 输出AST
			canto.AbstractSyntaxTree ast = compiler.getAST();
			System.out.println("Output AST");
			((ASTNode)ast).accept(new ASTPrinter());
			System.out.println();
			
			// 输出IC
			System.out.println("Output Intermediate Code");
			canto.IntermediateCode ic = compiler.getIC();
			((IntermediateCode)ic).accept(new ICPrinter());
			System.out.println();
			
			// 输出TC
			System.out.println("Output Target Code");
			canto.TargetCode tc=compiler.getTC();
			System.out.println(((X86TargetCode)tc).accept(new IntelEmitter()));			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
