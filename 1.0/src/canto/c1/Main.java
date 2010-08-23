package canto.c1;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.List;

import canto.c1.ast.ASTPrinter;
import canto.c1.ic.ICPrinter;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		try {
			
			// 检查文件名，并去除扩展名
			String fullName = args.length > 0 ? args[0] : "sample\\C1-Sample.canto";
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
			
			// 以下部分为调试程序所输出的编译中间产物
			// 输出Token链
			List<canto.Token> tokenList = compiler.getTokenList();
			System.out.println("Output Token List :");
			for (canto.Token token : tokenList) {
				System.out.print("Line " + token.getLine() + " Column " + token.getColumn() + ": ");
				System.out.print(token.getLexeme() + "\twith ");
				if (token.getAttribute() == null) System.out.println("null");
				else System.out.println(token.getAttribute().toString());
			}
			System.out.println();			
			// 输出AST
			System.out.println("Output AST :");
			ASTPrinter astPrinter = new ASTPrinter();
			astPrinter.print(compiler.getAST());
			System.out.println();			
			// 输出IC
			System.out.println("Output Intermediate Code :");
			ICPrinter icPrinter = new ICPrinter();
			icPrinter.print(compiler.getIC());
			System.out.println();			
			// 输出TC
			System.out.println("Output Target Code : ");
			IntelEmitter emmit = new IntelEmitter();
			emmit.emmit(compiler.getTC());
			System.out.println("\n");
			
			
			// 以下是汇编、连接阶段 			
			Process process;
			int status;
			InputStream feedback; 
			byte[] infoBuffer = new byte[1000];			
			// 汇编
			process = Runtime.getRuntime().exec("tools\\ml /c /coff /Fo" + 
					nonextName + ".obj " + nonextName + ".asm");
			status = process.waitFor();
			if (status != 0) {
				feedback = process.getInputStream();			
				for (int i = 0; i > -1; i = feedback.read(infoBuffer)) {
	                System.err.print(new String(infoBuffer, 0, i));
	            }
				throw new Exception("汇编错误");
			}	
			// 连接
			process = Runtime.getRuntime().exec(
					"tools\\link /subsystem:console /OUT:" + 
					nonextName + ".exe " + nonextName + ".obj");
			status = process.waitFor();
			if (status != 0) {
				feedback = process.getInputStream();
				for (int i = 0; i > -1; i = feedback.read(infoBuffer)) {
	                System.err.print(new String(infoBuffer, 0, i));
	            }
				throw new Exception("连接错误");
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
