package se.matzlarsson.asssim.model.data.instruction;

import org.w3c.dom.Node;

public class ParameterFactory {

	public static Parameter createParameter(Node node){
		String type = node.getAttributes().getNamedItem("type").getNodeValue();
		String storeName = node.getAttributes().getNamedItem("name").getNodeValue();
		switch(type){
			case "MEM":		return new MemoryReferenceParameter(storeName);
			case "NUM":		return new NumberParameter(storeName);
			case "REG":		return new RegisterParameter(storeName);
			default:		throw new IllegalArgumentException("Invalid parameter declaration for '"+storeName+"'");
		}
	}
	
}
