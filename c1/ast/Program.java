package canto.c1.ast;

public class Program extends ASTNode {

	/** The body block of the program. */
	private final Block body;
	
	/**
	 * Construct a program.
	 * @param body the body block
	 */
	public Program(Block body) {
		this.body = body;
	}
	
	@Override
	public void accept(ASTVisitor visitor) {
		visitor.visit(this);
	}

	@Override
	public NodeType getNodeType() {
		return NodeType.PROGRAM;
	}

	/**
	 * Get the body block of the program.
	 * @return the body block
	 */
	public Block getBody() {
		return body;
	}
	
}
