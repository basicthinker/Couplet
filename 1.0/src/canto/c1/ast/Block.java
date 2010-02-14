package canto.c1.ast;

import java.util.List;

public class Block extends Statement {

	/** The list to store statements and declarations. */
	private final List<Blockable> list;
	
	/**
	 * Construct a block.
	 * @param list the list to store statements and declarations
	 */
	public Block(List<Blockable> list) {
		this.list = list;
	}
	
	@Override
	public void accept(ASTVisitor visitor) {
		visitor.visit(this);
	}

	@Override
	public int getNodeType() {
		return BLOCK;
	}

	/**
	 * Get the list in the block.
	 * @return the list to store statements and declarations
	 */
	public List<Blockable> getList() {
		return list;
	}

}
