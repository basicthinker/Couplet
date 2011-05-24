package couplet;

/**
 * @author Goodness
 * 中间代码产生器顶层接口，包涵生成中间代码过程中所需要的一般方法
 */
public interface ICGenerator {

	/**
	 * 设置待分析的抽象语法树
	 * @param ast 待分析的抽象语法树
	 */
	public void setAST(AbstractSyntaxTree ast);
	
	/**
	 * 得到中间代码
	 * @return 中间代码生成是否成功
	 * @throws Exception 
	 */
	public void generateIC() throws CantoException;
	
	/**
	 * 获取生成的中间代码
	 * @return 生成的中间代码
	 */
	public IntermediateCode getIC();
}
