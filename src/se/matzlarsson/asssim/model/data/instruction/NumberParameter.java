package se.matzlarsson.asssim.model.data.instruction;

import se.matzlarsson.asssim.model.data.AssByte;
import se.matzlarsson.asssim.model.data.Machine;
import se.matzlarsson.asssim.model.data.SyntaxUtil;

public class NumberParameter extends BasicParameter{

	public NumberParameter(String storeName){
		super(storeName);
	}

	@Override
	public boolean isValid(Machine m, String input) {
		return SyntaxUtil.isConstant(m.getSyntax(), input) && SyntaxUtil.getNumericalType(m.getSyntax(), input)!=null;
	}

	@Override
	public void perform(Machine m, String input) {
		AssByte[] data = AssByte.getAssBytes(SyntaxUtil.getNumericalType(m.getSyntax(), input),
											 SyntaxUtil.getRealValue(m.getSyntax(), input));
		super.perform(m, data);
	}
	
}
