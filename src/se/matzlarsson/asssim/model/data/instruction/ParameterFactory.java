package se.matzlarsson.asssim.model.data.instruction;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

public class ParameterFactory {

	public static Parameter createParameter(Node node){
		NamedNodeMap attr = node.getAttributes();
		RequestType type = RequestType.find(attr.getNamedItem("type").getNodeValue());
		String storeName = attr.getNamedItem("name").getNodeValue();
		switch(type){
			case MEMORY:		String address = attr.getNamedItem("address").getNodeValue();
								return new MemoryReferenceParameter(storeName, address);
			case CONSTANT:		Node sizeNode = attr.getNamedItem("size");
								int size = (sizeNode!=null?Integer.parseInt(sizeNode.getNodeValue()):2);
								return new ConstantParameter(storeName, size);
			case REGISTER:		return new RegisterParameter(storeName);
			default:			throw new IllegalArgumentException("Invalid parameter declaration for '"+storeName+"'");
		}
	}
	
}
