package couplet;

import java.io.IOException;
import java.io.OutputStream;

/**
 * couplet异常类的顶层抽象类
 */
@SuppressWarnings("serial")
public abstract class CoupletException extends Exception {
	
	/**
	 * 输出异常信息
	 * @param outStrm 输出流
	 * @throws IOException 输出异常
	 */
	public abstract void outputInfo(OutputStream outStrm) throws IOException;
	
}
