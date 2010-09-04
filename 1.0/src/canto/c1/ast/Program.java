package canto.c1.ast;

/**
 * 程序结点
 */
public class Program extends ASTNodeBase {

	/** 程序体的Block */
	private final Block body;
	
	/**
	 * 构造一个程序结点
	 * @param body 程序体的Block
	 */
	public Program(Block body, int line, int column) {
		super(line, column);
		this.body = body;
		if (body != null) body.setParent(this);
	}
	
	@Override
	public void accept(ASTVisitor visitor) throws Exception {
		visitor.visit(this);
	}

	@Override
	public int getASTType() {
		return PROGRAM;
	}

	/**
	 * 获取程序体的Block
	 * @return 程序体的Block
	 */
	public Block getBody() {
		return body;
	}
	
}
