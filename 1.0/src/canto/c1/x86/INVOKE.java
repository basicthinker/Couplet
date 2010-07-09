package canto.c1.x86;

import java.util.List;

public class INVOKE extends Pseudo {

	private final String funName;
	
	private final List<Operand> paraList;
	
	public INVOKE(String funName, List<Operand> paraList) {
		this.funName = funName;
		this.paraList = paraList;
	}

	@Override
	public int getTCType() {
		// TODO Auto-generated method stub
		return 0;
	}

	public String getFunName() {
		return funName;
	}

	public List<Operand> getParaList() {
		return paraList;
	}

}
