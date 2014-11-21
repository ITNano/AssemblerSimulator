package se.matzlarsson.asssim.model.data.instruction;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import se.matzlarsson.asssim.model.data.Machine;

public class InstructionFactory {

	public static Instruction createInstruction(Machine m, Node rootNode){
		NamedNodeMap attr = rootNode.getAttributes();
		String id = attr.getNamedItem("id").getNodeValue();
		String name = attr.getNamedItem("name").getNodeValue();
		String help = attr.getNamedItem("help").getNodeValue();
		
		Instruction instr = new Instruction(id, name, help);
		
		NodeList params = ((Element)rootNode).getElementsByTagName("parameter");
		for(int i = 0; i<params.getLength(); i++){
			instr.addParameter(ParameterFactory.createParameter(m, params.item(i)));
		}
		
		NodeList functions = ((Element)rootNode).getElementsByTagName("functions").item(0).getChildNodes();
		for(int i = 0; i<functions.getLength(); i++){
			if(!functions.item(i).getNodeName().equals("#text")){
				instr.addFunction(FunctionFactory.createFunction(functions.item(i)));
			}
		}
		
		return instr;
	}
	
}
