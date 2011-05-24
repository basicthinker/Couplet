package couplet.c1;

import java.io.FileInputStream;
import java.io.FileOutputStream;

import couplet.CantoException;
import couplet.c1.error.AssemblyException;


public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {

		try {			

			// 检查文件名，并去除扩展名
			String fullName = args.length > 0 ? args[0] : "sample/C1-Sample.couplet";
			String nonextName = null;
			
			if ((fullName != null) && (fullName.length() > 0)) {
	            int k = fullName.lastIndexOf('.');
	            if ((k >-1) && (k < (fullName.length()))) {
	                nonextName = fullName.substring(0, k);
	            }
	        } else {
	        	throw new Exception();
	        }
			
			// 创建编译器
			Compiler compiler = new Compiler();
			
			// 读入源代码文件
			FileInputStream inFile = new FileInputStream(fullName);
			compiler.setSource(inFile);
			// 设置目标代码文件
			FileOutputStream outFile = new FileOutputStream(nonextName + ".asm");
			compiler.setTarget(outFile);

			// 编译
			compiler.compile();

			// 以下是汇编、连接阶段 			
			Process process;
			int status;
			
			// 汇编
			process = Runtime.getRuntime().exec("tools/ml /c /coff /Fo" + 
					nonextName + ".obj " + nonextName + ".asm");
			status = process.waitFor();
			if (status != 0) {
				throw new AssemblyException(process.getInputStream());
			}

			// 连接
			process = Runtime.getRuntime().exec(
					"tools/link /subsystem:console /OUT:" + 
					nonextName + ".exe " + nonextName + ".obj");
			status = process.waitFor();
			if (status != 0) {
				throw new AssemblyException(process.getInputStream());
			}
			
			System.out.println("编译成功！");
			
		} catch (CantoException e) {
			e.outputInfo(System.out);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
