package canto;

import java.util.List;

/**
 * 语法分析器的顶层接口 
 */
public interface Parser {
	
	/**
	 * 设置待分析的Token链
	 * @param tokenList
	 */
	public void setTokenList(List<Token> tokenList);
	
	/**
	 * 进行语法分析
	 * @return 生成的AST
	 * @throws Exception
	 */
	public AbstractSyntaxTree parse() throws Exception;
	
	/**
	 * 获取生成的AST
	 * @return 生成的AST
	 */
	public AbstractSyntaxTree getAST();
	
}
