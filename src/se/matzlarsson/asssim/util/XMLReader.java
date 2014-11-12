package se.matzlarsson.asssim.util;

import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.validation.SchemaFactory;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
/**
 * A class to read xml documents
 * @author Patrik Haar
 * @version 1.0
 *
 */
public class XMLReader {
	
	static final String JAXP_SCHEMA_LANGUAGE = "http://java.sun.com/xml/jaxp/properties/schemaLanguage";
	static final String W3C_XML_SCHEMA = "http://www.w3.org/2001/XMLSchema";


	/**
	 * Finds and returns the xml file on the path provided.
	 * @param filePath The path to the file.
	 * @return A Document made from the xml file.
	 */
	public static Document getDocument(String filePath) {
		return getDocument(filePath, null);
	}
	
	/**
	 * Finds and returns the xml file on the path provided.
	 * @param filePath The path to the file.
	 * @param schemaPath Path to the schema to use. Null to use default set up.
	 * @return A Document made from the xml file.
	 */
	public static Document getDocument(String filePath, String schemaPath) {
		return getDocument(filePath, schemaPath, true);
	}
	
	/**
	 * Finds and returns the xml file on the path provided.
	 * @param filePath The path to the file.
	 * @param insideProject If the file exists within the classpath
	 * @return A Document made from the xml file.
	 */
	public static Document getDocument(String filePath, boolean insideProject) {
		return getDocument(filePath, null, insideProject);
	}

	/**
	 * Finds and returns the xml file on the path provided.
	 * @param filePath The path to the file.
	 * @param schemaPath Path to the schema to use. Null to use default set up.
	 * @param insideProject If the file exists within the classpath
	 * @return A Document made from the xml file.
	 */
	public static Document getDocument(String filePath, String schemaPath, boolean insideProject){
		try {
			// API used to convert XML into a DOM object tree
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			// Ignore all of the comments
			factory.setIgnoringComments(true);
			// Ignore white space in elements
			factory.setIgnoringElementContentWhitespace(true);
			//Set up schema to access id's
			if(schemaPath != null){
				setSchema(factory, schemaPath);
			}
			// Provides access to the documents data
			DocumentBuilder builder = factory.newDocumentBuilder();
			builder.setErrorHandler(new DocumentErrorHandler(DocumentErrorHandler.LEVEL_FATAL_ERROR));
			// Takes the document
			if(insideProject){
				InputStream fileStream = ClassLoader.getSystemResourceAsStream(filePath);
				return builder.parse(new InputSource(fileStream));
			}else{
				return builder.parse(new InputSource(filePath));
			}
		} catch (Exception ex) {
			Log.e("Could not read XML: "+ex.getMessage());
		}
		return null;
	}
	
	/**
	 * Sets the schema of the given factory
	 * @param factory The factory to affect
	 * @param schemaPath The path to the schema file
	 * @throws SAXException If anything goes wrong
	 */
	private static void setSchema(DocumentBuilderFactory factory, String schemaPath) throws SAXException{
		factory.setNamespaceAware(true);
		factory.setValidating(true);
	    factory.setSchema(SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema").newSchema(ClassLoader.getSystemResource(schemaPath)));
	}
}
