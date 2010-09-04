package canto.c1;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.List;

import canto.c1.ast.ASTPrinter;
import canto.c1.error.ErrorRecord;
import canto.c1.error.CompileException;
import canto.c1.ic.ICPrinter;

public class Compiler implements canto.Compiler {

	private InputStreamReader sourceReader;
	private OutputStreamWriter targetWriter;
	private canto.Lexer lexer;
	private canto.Parser parser;
	private canto.ICGenerator icGenerator;
	private canto.TCGenerator tcGenerator;
	private canto.TCEmmiter emmiter;
	private CompileException exception;
	
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
		emmiter = new IntelEmitter();
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
	public void compile() throws CompileException {
		// TODO Auto-generated method stub
		try {
			if (sourceReader == null) {
				throw new CompileException(ErrorRecord.compileError());
			}
			
			lexer.open(sourceReader);
			lexer.scan();

			// 输出Token链
			List<canto.Token> tokenList = getTokenList();
			System.out.println("Output Token List :");
			for (canto.Token token : tokenList) {
				System.out.print("Line " + token.getLine() + " Column " + token.getColumn() + ": \t");
				System.out.print(token.getClass().getSimpleName() + " ");
				System.out.println("\"" + token.getLexeme() + "\"");
			}
			System.out.println();
			
			parser.setTokenList(lexer.getTokenList());
			parser.parse();

			// 输出AST
			System.out.println("Output AST :");
			ASTPrinter astPrinter = new ASTPrinter();
			astPrinter.print(getAST());
			System.out.println();
			
			icGenerator.setAST(parser.getAST());
			icGenerator.generateIC();

			// 输出IC
			System.out.println("Output Intermediate Code :");
			ICPrinter icPrinter = new ICPrinter();
			icPrinter.print(getIC());
			System.out.println();
			
			tcGenerator.setIC(icGenerator.getIC());
			tcGenerator.generateTC();

			// 输出TC
			System.out.println("Output Target Code : ");
			IntelEmitter emmit = new IntelEmitter();
			emmit.emmit(getTC());
			System.out.println("\n");
			emmiter.setWriter(targetWriter);
			emmiter.emmit(tcGenerator.getTC());
			
		} catch (CompileException e) {
			exception = e;
		} catch (Exception e) {
			e.printStackTrace();
			exception = new CompileException(ErrorRecord.compileError());
		} finally {
			if (exception != null) throw exception;
		}
	}

	/* (non-Javadoc)
	 * @see canto.Compiler#outputErrors(java.io.OutputStream)
	 */
	@Override
	public void outputErrors(OutputStream outStrm) throws IOException {
		exception.outputInfo(outStrm);
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
	
}
