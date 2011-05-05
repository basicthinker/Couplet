package couplet;

/**
 * @author Goodness
 *
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
