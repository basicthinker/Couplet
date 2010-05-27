package canto.c1;

import canto.AbstractSyntaxTree;
import canto.IntermediateCode;

/**
 * c1中生成中间代码的类
 * @author Goodness
 *
 */
public class ICGenerator implements canto.ICGenerator {

	/**输入的抽象语法树*/
	private AbstractSyntaxTree treeRoot;

	/**得到的中间代码*/
	private IntermediateCode IC;
	
	@Override
	public IntermediateCode generateIC() {
		
		return null;
	}

	@Override
	public IntermediateCode getIC() {		
		return IC;
	}

	@Override
	public void setAST(AbstractSyntaxTree AST) {
		this.treeRoot=AST;
	}

}
