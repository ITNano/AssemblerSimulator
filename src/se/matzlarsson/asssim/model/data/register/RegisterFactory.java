package se.matzlarsson.asssim.model.data.register;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import se.matzlarsson.asssim.model.data.AssByte;
import se.matzlarsson.asssim.model.data.NumericalType;
import se.matzlarsson.asssim.util.XMLReader;

public class RegisterFactory {
	
	private static List<Register> registers = new ArrayList<Register>();

	public static Register createRegister(String data){
		Register reg = null;		
		String[] s = data.split(",");
		switch(s[0]){
			case "B":	reg = createBasicRegister(s);break;
			case "R":	reg = createReferenceRegister(s);break;
			default:	break;
		}
		
		addRegister(reg);
		return reg;
	}
	
	public static Register createRegisterFromXML(String filePath){
		return createRegisterFromXML(XMLReader.getDocument(filePath).getElementsByTagName("register").item(0));
	}
	
	public static Register createRegisterFromXML(Node rootNode){
		Register reg = null;
		String name = rootNode.getAttributes().getNamedItem("name").getNodeValue();
		NodeList references = ((Element)rootNode).getElementsByTagName("reference");
		if(references.getLength()>0){
			Register[] children = new Register[references.getLength()];
			String tmpName;
			for(int i = 0; i<children.length; i++){
				tmpName = references.item(i).getAttributes().getNamedItem("name").getNodeValue();
				children[i] = findRegister(tmpName);
			}
			
			reg = new ReferenceRegister(name, children);
		}else{
			NodeList values = ((Element)rootNode).getElementsByTagName("byte");
			AssByte[] bytes = new AssByte[values.getLength()];
			NamedNodeMap attr;
			for(int i = 0; i<bytes.length; i++){
				attr = values.item(i).getAttributes();
				bytes[i] = new AssByte(NumericalType.find(attr.getNamedItem("type").getNodeValue()), values.item(i).getFirstChild().getNodeValue());
			}
			
			reg = new BasicRegister(name, bytes);
		}
		
		addRegister(reg);
		return reg;
	}
	
	private static Register createBasicRegister(String[] source){
		String[] byteStrings = source[2].split("\\|");
		AssByte[] bytes = new AssByte[byteStrings.length];
		for(int i = 0; i<byteStrings.length; i++){
			bytes[i] = new AssByte(NumericalType.HEXADECIMAL, byteStrings[i]);
			
		}
		
		return new BasicRegister(source[1], bytes);
	}
	
	private static Register createReferenceRegister(String[] source){
		String[] childrenNames = source[2].split("\\|");
		Register[] children = new Register[childrenNames.length];
		for(int i = 0; i<childrenNames.length; i++){
			children[i] = findRegister(childrenNames[i]);
		}
		
		return new ReferenceRegister(source[1], children);
	}
	
	private static void addRegister(Register reg){
		if(reg != null){
			registers.add(reg);
		}
	}
	
	private static Register findRegister(String name){
		for(int i = 0; i<registers.size(); i++){
			if(registers.get(i).getName().equals(name)){
				return registers.get(i);
			}
		}
		
		return null;
	}
}
