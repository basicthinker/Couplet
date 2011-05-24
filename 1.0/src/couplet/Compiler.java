package couplet;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

/**
 * @author basicthinker
 * 编译器顶层接口，通过实现各方法，得到有各自特点的编译器
 */
public interface Compiler {

	/**
	 * 获取该AST结点的类型
	 */
	public void setSource(InputStream inStrm);
	
	/**
	 * 设置代码输入流，来自文件或其他媒介
	 */
	public void setTarget(OutputStream outStrm);
	
	/**
	 * 开始编译，编译的入口程序
	 * @throws CantoException 抛出编译过程中产生的异常
	 */
	public void compile() throws CantoException;
	
	/**
	 * 输出错误
	 * @param outStrm 输出流，指定想要输出的文件或其他媒介
	 * @throws IOException 输入输出异常，输入输出中遇到的异常时抛出
	 */
	public void outputErrors(OutputStream outStrm) throws IOException;
	
	/**
	 * 得到Token链接成的链表
	 * @return 返回由Token组成的链表
	 */
	public List<Token> getTokenList(); 
	
	/**
	 * 得到抽象语法树
	 * @return 返回自己定义的抽象语法树的对象
	 */
	public AbstractSyntaxTree getAST();
	
	/**
	 * 得到中间代码
	 * @return 返回自己定义的中间代码的对象
	 */
	public IntermediateCode getIC();
	
	/**
	 * 得到目标代码
	 * @return 返回自自己定义的目标代码的对象
	 */
	public TargetCode getTC();
	
}
