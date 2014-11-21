package se.matzlarsson.asssim.model.data.instruction;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import se.matzlarsson.asssim.model.data.Machine;

public class ParameterFactory {

	public static Parameter createParameter(Machine m, Node node){
		NamedNodeMap attr = node.getAttributes();
		RequestType type = RequestType.find(attr.getNamedItem("type").getNodeValue());
		String storeName = attr.getNamedItem("name").getNodeValue();
		switch(type){
			case MEMORY:		String address = attr.getNamedItem("address").getNodeValue();
								Node sizeNode = attr.getNamedItem("size");
								int size = (sizeNode!=null?Integer.parseInt(sizeNode.getNodeValue()):m.getMemoryAddressSize());
								Node byteSizeNode = attr.getNamedItem("bytes");
								int byteSize = (byteSizeNode!=null?Integer.parseInt(byteSizeNode.getNodeValue()):1);
								return new MemoryReferenceParameter(storeName, address, size, byteSize);
			case CONSTANT:		sizeNode = attr.getNamedItem("size");
								size = (sizeNode!=null?Integer.parseInt(sizeNode.getNodeValue()):2);
								Node ignoreMarkupNode = attr.getNamedItem("ignoreConstantPrefix");
								boolean ignoreConstantPrefix = (ignoreMarkupNode!=null?ignoreMarkupNode.getNodeValue().equals("true"):false);
								return new ConstantParameter(storeName, size, ignoreConstantPrefix);
			case REGISTER:		return new RegisterParameter(storeName);
			default:			throw new IllegalArgumentException("Invalid parameter declaration for '"+storeName+"'");
		}
	}
	
}
