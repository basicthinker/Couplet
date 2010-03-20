package canto.c1.ast;

import java.util.LinkedList;
import java.util.List;

public class StatementList extends ASTNode {

	/** The list to store statements and declarations. */
	private final List<Listable> list;
	
	/**
	 * Construct a statement list.
	 */
	public StatementList() {
		this.list = new LinkedList<Listable>();
	}

	@Override
	public void accept(ASTVisitor visitor) {
		visitor.visit(this);
	}

	@Override
	public int getNodeType() {
		return STATEMENT_LIST;
	}
	
	/**
	 * Get the list.
	 * @return the list to store statements and declarations
	 */
	public List<Listable> getList() {
		return list;
	}

	/**
	 * Add a new listable item to the list.
	 * @param item
	 */
	public void addListable(Listable item) {
		list.add(item);
	}

}
