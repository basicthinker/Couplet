package couplet;

import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 * 目标代码发射器顶层接口，将生成的目标代码链表打印出来
 * @author Goodness
 *
 */
public interface TCEmmiter {

	/**
	 * 传入输出到的媒介
	 * @param outStr 输出到的媒介
	 */
	public void setWriter(OutputStreamWriter outStr);
	
	/**
	 * 开始将目标代码转化成字符流，并发射到相应媒介中
	 * @param tc 源目标代码
	 * @throws CoupletException 需抛出的和语言相关的异常
	 * @throws IOException 需抛出的输入输出异常
	 */
	public void emmit(TargetCode tc) throws CoupletException, IOException;
	
}
