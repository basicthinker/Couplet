package canto.c1;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.List;

import canto.c1.ast.ASTNode;
import canto.c1.ast.ASTPrinter;
import canto.c1.ic.ICPrinter;
import canto.c1.ic.IntermediateCode;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		try {
			
			// 检查文件名，并去除扩展名
			String fullName = args[0];
			String nonextName = null;
			if ((fullName != null) && (fullName.length() > 0)) {
	            int k = fullName.lastIndexOf('.');
	            if ((k >-1) && (k < (fullName.length()))) {
	                nonextName = fullName.substring(0, k);
	            }
	        } else {
	        	throw new Exception();
	        }
			
			Compiler compiler = new Compiler();
			
			// 读入源代码文件
			FileInputStream inFile = new FileInputStream(fullName);
			compiler.setSource(inFile);
			
			FileOutputStream outFile = new FileOutputStream(nonextName + ".asm");
			compiler.setTarget(outFile);

			// 编译
			compiler.compile();
			
			Process process;
			int status;
			InputStream feedback; 
			byte[] infoBuffer = new byte[1000];
			
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
			canto.AbstractSyntaxTree ast = compiler.getAST();
			System.out.println("Output AST :");
			((ASTNode)ast).accept(new ASTPrinter());
			System.out.println();
			
			// 输出IC
			System.out.println("Output Intermediate Code :");
			canto.IntermediateCode ic = compiler.getIC();
			((IntermediateCode)ic).accept(new ICPrinter());
			System.out.println();
						
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
