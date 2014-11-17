package se.matzlarsson.asssim.model.data;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class SyntaxFactory {

	public static Syntax createSyntax(Node rootNode){
		Syntax syntax = new Syntax();
		
		NodeList inputTypes = ((Element)rootNode).getElementsByTagName("inputtype");
		String name, value;
		Node current, quotes;
		for(int i = 0; i<inputTypes.getLength(); i++){
			current = inputTypes.item(i);
			name = current.getAttributes().getNamedItem("name").getNodeValue();
			value = current.getFirstChild()==null?"":current.getFirstChild().getNodeValue();
			quotes = current.getAttributes().getNamedItem("quotes");
			syntax.addRule(name, value, (quotes==null?false:quotes.getNodeValue().equals("true")));
		}
		
		return syntax;
	}
	
}
