package se.matzlarsson.asssim;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import se.matzlarsson.asssim.model.data.Register;
import se.matzlarsson.asssim.model.data.RegisterFactory;
import se.matzlarsson.asssim.util.XMLReader;

public class Main {

	public static void main(String[] args){
		System.out.println("Simulator is loading...");
		Node computer = XMLReader.getDocument("res/hc12.xml", false).getElementsByTagName("computer").item(0);
		System.out.println("Testing computer: "+computer.getAttributes().getNamedItem("name").getNodeValue());
		NodeList registers = ((Element)computer).getElementsByTagName("register");
		Register a = RegisterFactory.createRegisterFromXML(registers.item(0));
		Register b = RegisterFactory.createRegisterFromXML(registers.item(1));
		Register d = RegisterFactory.createRegisterFromXML(registers.item(2));
		System.out.println(a);
		System.out.println(b);
		System.out.println(d);
	}
	
}
