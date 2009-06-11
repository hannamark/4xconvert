package gov.nih.nci.pa.pdq;

import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * @author hreinhart
 *
 */
@SuppressWarnings("PMD.SignatureDeclareThrowsException")
public class Interpret {

    private static final Logger LOG = Logger.getLogger(Interpret.class);
    private static Interpret interpreter = new Interpret();
    public static Interpret getInterpreter() { return interpreter; }
    
    public Rule process(Document doc) throws PDQException {
        return getType(doc);
    }
    
    private Rule getType(Document doc) throws PDQException {
        Rule result = Rule.INVALID;
        boolean hasTerm = false;
        String term = "";
        boolean hasSemantic = false;
        ArrayList<String> semanticList = new ArrayList<String>();
        boolean hasPreferredName = false;
        String preferredName = "";
        
        Node root = doc.getDocumentElement();
        if (!root.getNodeName().equals(Rule.NODE_NAME_TERM)) {
            throw new PDQException("Invalid root node.  Name = " + doc.getDocumentElement().getNodeName());
        }
        NodeList children = doc.getDocumentElement().getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
            Node child = children.item(i);
            if (child.getNodeName() == Rule.NODE_NAME_TERM_TYPE_NAME) {
                if (hasTerm) {
                    throw new PDQException("Invalid PDQ file.  Multiple '" + Rule.NODE_NAME_TERM_TYPE_NAME 
                            + "' nodes found.  " + "First = " + term + "  Second = " + child.getTextContent());
                }
                hasTerm = true;
                term = child.getTextContent();
            }
            if (child.getNodeName() == Rule.NODE_NAME_SEMANTIC_TYPE) {
                hasSemantic = true;
                semanticList.add(child.getTextContent());
            }
            if (child.getNodeName() == Rule.NODE_NAME_PREFERRED_NAME) {
                if (hasPreferredName) {
                    throw new PDQException("Invalid PDQ file.  Multiple '" + Rule.NODE_NAME_PREFERRED_NAME
                            + "' nodes found.  First = " + preferredName + "  Second = " + child.getTextContent());
                }
                hasPreferredName = true;
                preferredName = child.getTextContent();
            }
        }
        if (!hasTerm && !hasSemantic) {
            throw new PDQException("Invalid PDQ file.  Neither 'TermTypeName' nor 'SemanticType' found.  ");
        }
        if (term.equals(Rule.TERM_TYPE_SEMANTIC) && Rule.diseaseList.contains(preferredName)) {
            result = Rule.RULE1;
        }
        if (term.equals(Rule.TERM_TYPE_SEMANTIC) && !Rule.diseaseList.contains(preferredName)) {
            result = Rule.RULE2;
        }
        if (term.equals(Rule.TERM_TYPE_OBSOLETE)) {
            result = Rule.RULE3;
        }
        if (result.equals(Rule.INVALID)) {
            if (hasSemantic) {
                result = Rule.RULE5;
                for (String semantic : semanticList) {
                    if (Rule.diseaseList.contains(semantic)) {
                        result = Rule.RULE4;
                    }
                }
            } else {
//                LOG.warn("Invalid file.  ");
            }
        }
//        LOG.info("term = " + term + "  semantic = " + semanticList + "  pn = " + preferredName);
        return result;
    }
}
