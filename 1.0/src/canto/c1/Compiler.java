package canto.c1;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.List;

public class Compiler implements canto.Compiler {

	private InputStreamReader sourceReader;
	private OutputStreamWriter targetWriter;
	private canto.Lexer lexer;
	private canto.Parser parser;
	private canto.ICGenerator icGenerator;
	private canto.TCGenerator tcGenerator;
	private canto.TCEmmiter emmiter;
	
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
			emmiter.setWriter(targetWriter);
			emmiter.emmit(tcGenerator.getTC());
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
	
}
