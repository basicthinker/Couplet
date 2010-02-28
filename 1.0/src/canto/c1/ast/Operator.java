package canto.c1.ast;

public abstract class Operator extends ASTNode {
	
	/** The operator code indicating the kind of the operator. */
	private final int code;
	
	/**
	 * Construct an operator.
	 * @param code the operator code
	 */
	public Operator(int code) {
		this.code = code;
	}
	
	/**
	 * Get the operator code.
	 * @return the operator code
	 */
	public int getOperatorCode() {
		return code;
	}
	
}
