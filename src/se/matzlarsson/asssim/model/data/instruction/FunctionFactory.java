package se.matzlarsson.asssim.model.data.instruction;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

public class FunctionFactory {

	public static Function createFunction(Node node){
		String name = node.getNodeName().toLowerCase();
		NamedNodeMap attr = node.getAttributes();
		switch(name){
			case "load":
				RequestType loadType = RequestType.find(attr.getNamedItem("type").getNodeValue());
				String loadInput = attr.getNamedItem("input").getNodeValue();
				String loadOutput = attr.getNamedItem("output").getNodeValue();
				Node variable = attr.getNamedItem("variable");
				boolean inputIsVariable = (variable!=null?variable.getNodeValue().equals("true"):false);
				return new LoadFunction(loadType, loadInput, loadOutput, inputIsVariable);
				
			case "store":
				RequestType storeType = RequestType.find(attr.getNamedItem("type").getNodeValue());
				String storeInput = attr.getNamedItem("input").getNodeValue();
				String storeOutput = attr.getNamedItem("output").getNodeValue();
				return new StoreFunction(storeType, storeInput, storeOutput);
			
			default:
				return null;
		}
	}
	
}
