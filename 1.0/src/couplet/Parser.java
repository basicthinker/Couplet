package couplet;

import java.util.List;

/**
 * 语法分析器的顶层接口，独立于语言版本
 */
public interface Parser {
	
	/**
	 * 设置待分析的Token链
	 * @param tokenList
	 */
	public void setTokenList(List<Token> tokenList);
	
	/**
	 * 进行语法分析
	 * @return 编译是否成功
	 * @throws Exception
	 */
	public void parse() throws CoupletException;
	
	/**
	 * 获取生成的AST
	 * @return 生成的AST
	 */
	public AbstractSyntaxTree getAST();
	
}
