﻿package canto.c1.ast;

/**
 * 输入语句结点
 */
public class InputStatement extends Statement {

	/** 用于接受输入的标识符 */
	private final Identifier identifier;
	
	/**
	 * 构造一个输入语句
	 * @param identifier 接受输入的标识符
	 */
	public InputStatement(Identifier identifier) {
		this.identifier = identifier;
	}

	@Override
	public void accept(ASTVisitor visitor) throws Exception {
		visitor.visit(this);
	}

	@Override
	public int getNodeType() {
		return INPUT_STATEMENT;
	}

	/**
	 * 获取接受输入的标识符
	 * @return 接受输入的标识符
	 */
	public Identifier getIdentifier() {
		return identifier;
	}

}
