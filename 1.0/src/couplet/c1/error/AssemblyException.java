package couplet.c1.error;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import couplet.CantoException;


@SuppressWarnings("serial")
public class AssemblyException extends CantoException {

	private String info;
	
	public AssemblyException(InputStream feedback) throws IOException {
		info = readMessage(feedback);
	}
	
	public AssemblyException() throws IOException {
		info = "未知的汇编或连接错误";
	}
	
	private static String readMessage(InputStream feedback) throws IOException {
		byte[] infoBuffer = new byte[1000];
		String msg = "";
		for (int i = 0; i > -1; i = feedback.read(infoBuffer)) {
            msg += new String(infoBuffer, 0, i);
        }
		return msg;
	}

	@Override
	public void outputInfo(OutputStream outStrm) throws IOException {
		BufferedWriter outBuf = 
			new BufferedWriter(new OutputStreamWriter(outStrm));
		outBuf.write("[汇编连接错误]" + info);
		outBuf.close();
	}

}
