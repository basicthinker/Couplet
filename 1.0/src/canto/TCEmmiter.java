package canto;

import java.io.IOException;
import java.io.OutputStreamWriter;

public interface TCEmmiter {

	public void setWriter(OutputStreamWriter outStr);
	
	public void emmit(TargetCode tc) throws IOException;
	
}
