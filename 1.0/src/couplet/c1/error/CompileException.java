package couplet.c1.error;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.LinkedList;
import java.util.List;

import couplet.CantoException;


@SuppressWarnings("serial")
public class CompileException extends CantoException {

	protected List<ErrorRecord> errorList;
	
	public CompileException() {
		errorList = new LinkedList<ErrorRecord>();
	}
	
	public CompileException(ErrorRecord error) {
		errorList = new LinkedList<ErrorRecord>();
		errorList.add(error);
	}
	
	public List<ErrorRecord> getErrorList() {
		return errorList;
	}
	
	public boolean containError() {
		if (errorList != null && errorList.size() > 0) return true;
		else return false;
	}
	
	public void add(ErrorRecord error) {
		errorList.add(error);
	}
	
	@Override
	public void outputInfo(OutputStream outStrm) throws IOException {
		BufferedWriter outBuf = 
			new BufferedWriter(new OutputStreamWriter(outStrm));
		for (ErrorRecord error : getErrorList()) {
			int line = error.getLine();
			int column = error.getColumn();
			String info = error.getInfo();
			outBuf.write("[编译错误]");	
			if (line != 0 && column != 0) 
				outBuf.write("第" + line + "行，第" + column + "列：");
			if (info != null) 
				outBuf.write(info);
			outBuf.newLine();
		}
		outBuf.close();
	}
	
}
