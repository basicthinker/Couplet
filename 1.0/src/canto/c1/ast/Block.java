package canto.c1.ast;

import java.util.LinkedList;
import java.util.List;

public class Block extends Statement {

	/** The list to store statements and declarations. */
	private final List<Blockable> list;
	
	/**
	 * Construct a block.
	 * @param list the list to store statements and declarations
	 */
	public Block() {
		this.list = new LinkedList<Blockable>();
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

	/**
	 * Add a new blockable item to the list.
	 * @param item
	 */
	public void addBlockable(Blockable item) {
		list.add(item);
	}

}
