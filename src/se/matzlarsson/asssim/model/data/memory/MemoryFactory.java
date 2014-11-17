package se.matzlarsson.asssim.model.data.memory;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import se.matzlarsson.asssim.util.Converter;

public class MemoryFactory {

	public static Memory createMemory(Node rootNode){
		Memory memory = new Memory(Integer.parseInt(rootNode.getAttributes().getNamedItem("addressSize").getNodeValue()));
		
		NodeList components = ((Element)rootNode).getElementsByTagName("component");
		String name;
		int startAddress, endAddress;
		boolean readable, writeable;
		NamedNodeMap attr;
		for(int i = 0; i<components.getLength(); i++){
			attr = components.item(i).getAttributes();
			name = attr.getNamedItem("name").getNodeValue();
			startAddress = Converter.hexToDecimal(attr.getNamedItem("startAddress").getNodeValue());
			endAddress = Converter.hexToDecimal(attr.getNamedItem("endAddress").getNodeValue());
			readable = attr.getNamedItem("readable").getNodeValue().equals("true");
			writeable = attr.getNamedItem("writeable").getNodeValue().equals("true");
			
			memory.addMemoryComponent(new MemoryComponent(name, startAddress, endAddress-startAddress+1, readable, writeable));
		}
		
		return memory;
	}
	
}
