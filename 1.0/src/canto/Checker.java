package canto;

/**
 * 语义检查器的顶层接口
 */
public interface Checker {

	/**
	 * 设置待检查的AST
	 * @param ast
	 */
	void setAST(AbstractSyntaxTree ast);

	/**
	 * 获取检查后的AST
	 * @return 检查后的AST
	 */
	AbstractSyntaxTree getAST();


	
	/**
	 * 进行语义检查
	 * @return 检查后的AST
	 * @throws Exception 
	 */
	AbstractSyntaxTree check() throws Exception;

}
