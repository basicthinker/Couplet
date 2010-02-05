/**
 * 
 */
package canto.c1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author basicthinker
 *
 */
public class LinenumberedBuffer {
	
	private BufferedReader bufReader;

	private int lineNum;
	private int columnNum;
	private int lastLine;
	
	public LinenumberedBuffer(InputStreamReader inStr) throws IOException {
		bufReader = new BufferedReader(inStr);
		bufReader.ready();
		lineNum = 1;
		columnNum = 0;
		lastLine = 0;
	}
	
	public char nextChar() throws IOException {
		int next = bufReader.read();
		if (next == -1);
		
		if (next == '\n') {
			++lineNum;
			lastLine = columnNum;
			columnNum = 0;
		} else {
			++columnNum;
		}
		
		return (char)next;
	}

	public void back(char current) {
		if (current == '\n') {
			
		}
	}
	
	/**
	 * @return the lineNum
	 */
	public int getLineNum() {
		return lineNum;
	}

	/**
	 * @return the columnNum
	 */
	public int getColumnNum() {
		return columnNum;
	}
	
}
