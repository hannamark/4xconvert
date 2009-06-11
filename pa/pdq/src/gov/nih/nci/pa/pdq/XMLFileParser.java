package gov.nih.nci.pa.pdq;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * @author hreinhart
 *
 */
public class XMLFileParser {
    private static final Logger LOG = Logger.getLogger(XMLFileParser.class);
    private static XMLFileParser parser = new XMLFileParser();
    
    public static XMLFileParser getParser() { return parser; }
    
    /** Creates a new instance of ParseXMLFile */
    public void test(String xmlFileName, String targetFileName) {
        // parse XML file -> XML document will be build
        Document doc = parseFile(xmlFileName);
        // get root node of xml tree structure
        Node root = doc.getDocumentElement();
        // write node and its child nodes into System.out
        LOG.info("Statement of XML document...");
        writeDocumentToOutput(root,0);
        LOG.info("... end of statement");
        // write Document into XML file
        saveXMLDocument(targetFileName, doc);
    }
    
    /** Returns element value
     * @param elem element (it is XML tag)
     * @return Element value otherwise empty String
     */
    public final static String getElementValue( Node elem ) {
        Node kid;
        if( elem != null){
            if (elem.hasChildNodes()){
                for( kid = elem.getFirstChild(); kid != null; kid = kid.getNextSibling() ){
                    if( kid.getNodeType() == Node.TEXT_NODE  ){
                        return kid.getNodeValue();
                    }
                }
            }
        }
        return "";
    }
    
    private String getIndentSpaces(int indent) {
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < indent; i++) {
            buffer.append(" ");
        }
        return buffer.toString();
    }
    
    /** Writes node and all child nodes into System.out
     * @param node XML node from from XML tree wrom which will output statement start
     * @param indent number of spaces used to indent output
     */
    public void writeDocumentToOutput(Node node,int indent) {
        // get element name
        String nodeName = node.getNodeName();
        // get element value
        String nodeValue = getElementValue(node);
        // get attributes of element
        NamedNodeMap attributes = node.getAttributes();
        LOG.info(getIndentSpaces(indent) + "NodeName: " + nodeName + ", NodeValue: " + nodeValue);
        for (int i = 0; i < attributes.getLength(); i++) {
            Node attribute = attributes.item(i);
            LOG.info(getIndentSpaces(indent + 2) + "AttributeName: " + attribute.getNodeName() + ", attributeValue: " + attribute.getNodeValue());
        }
        // write all child nodes recursively
        NodeList children = node.getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
            Node child = children.item(i);
            if (child.getNodeType() == Node.ELEMENT_NODE) {
                writeDocumentToOutput(child,indent + 2);
            }
        }
    }
    
    /** Saves XML Document into XML file.
     * @param fileName XML file name
     * @param doc XML document to save
     * @return <B>true</B> if method success <B>false</B> otherwise
     */    
    public boolean saveXMLDocument(String fileName, Document doc) {
        LOG.info("Saving XML file... " + fileName);
        // open output stream where XML Document will be saved
        File xmlOutputFile = new File(fileName);
        FileOutputStream fos;
        Transformer transformer;
        try {
            fos = new FileOutputStream(xmlOutputFile);
        }
        catch (FileNotFoundException e) {
            LOG.error("Error occured: " + e.getMessage());
            return false;
        }
        // Use a Transformer for output
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        try {
            transformer = transformerFactory.newTransformer();
        }
        catch (TransformerConfigurationException e) {
            LOG.error("Transformer configuration error: " + e.getMessage());
            return false;
        }
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(fos);
        // transform source into result will do save
        try {
            transformer.transform(source, result);
        }
        catch (TransformerException e) {
            LOG.error("Error transform: " + e.getMessage());
        }
        LOG.info("XML file saved.");
        return true;
    }
    
    /** Parses XML file and returns XML document.
     * @param fileName XML file to parse
     * @return XML document or <B>null</B> if error occured
     */
    public Document parseFile(String fileName) {
//        LOG.info("Parsing XML file... " + fileName);
        DocumentBuilder docBuilder;
        Document doc = null;
        DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
        docBuilderFactory.setIgnoringElementContentWhitespace(true);
        try {
            docBuilder = docBuilderFactory.newDocumentBuilder();
        }
        catch (ParserConfigurationException e) {
            LOG.error("Wrong parser configuration: " + e.getMessage());
            return null;
        }
        File sourceFile = new File(fileName);
        try {
            doc = docBuilder.parse(sourceFile);
        }
        catch (SAXException e) {
            LOG.error("Wrong XML file structure: " + e.getMessage());
            return null;
        }
        catch (IOException e) {
            LOG.error("Could not read source file: " + e.getMessage());
        }
//        LOG.info("XML file parsed");
        return doc;
    }
}
