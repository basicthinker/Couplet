package canto;

public interface TCGenerator {
	
	/**
	 * 设置待生成的中间代码
	 * @param AST 待生成的中间代码
	 */
	public void setIC(IntermediateCode ic);
	
	/**
	 * 生成目标代码
	 * @return 目标代码生成是否成功
	 * @throws Exception 
	 */
	public void generateTC() throws CantoException;
	
	/**
	 * 获取生成的目标代码
	 * @return 生成的目标代码
	 */
	public TargetCode getTC();
	
}
