package gov.nih.nci.pa.pdq;

import gov.nih.nci.pa.domain.Intervention;
import gov.nih.nci.pa.domain.InterventionAlternateName;
import gov.nih.nci.pa.enums.InterventionTypeCode;
import gov.nih.nci.pa.util.PAUtil;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * @author hreinhart
 *
 */
public class PDQIntervention extends AbstractPDQProcessor {
    private static final Logger LOG = Logger.getLogger(PDQIntervention.class);

    /**
     * @param doc
     * @param rule
     */
    public void process(Document doc, Rule rule, String user) {
        this.doc = doc;
        this.rule = rule;
        this.user = user;
        if (rule.equals(Rule.RULE2)) { // no obsolete per Nellie || (rule.equals(Rule.RULE3))) {
//            rule2or3();
        }
        if (rule.equals(Rule.RULE5)) {
            rule5();
        }
    }
    
    private void rule2or3() {
        Intervention i = new Intervention();
        List<InterventionAlternateName> ianList = new ArrayList<InterventionAlternateName>();
        
        Node node = doc.getDocumentElement();
        NodeList children = node.getChildNodes();
        for (int x = 0; x < children.getLength(); x++) {
            Node child = children.item(x);
            if (child.getNodeName().equals(Rule.NODE_NAME_PREFERRED_NAME)) {
                i.setName(child.getTextContent());
            }
            if (child.getNodeName().equals(Rule.NODE_NAME_OTHER_NAME)) {
                NodeList others = child.getChildNodes();
                for (int y = 0; y < others.getLength(); y++) {
                    Node other = others.item(y);
                    if(other.getNodeName().equals(Rule.NODE_NAME_OTHER_TERM_NAME)) {
                        InterventionAlternateName ian = new InterventionAlternateName();
                        ian.setName(other.getTextContent());
                        ianList.add(ian);
                    }
                }
            }
        }
        if (PAUtil.isEmpty(i.getName())) {
            LOG.error("Error determining name from: ");
            XMLFileParser.getParser().writeDocumentToOutput(doc.getDocumentElement(), 0);
            System.exit(0);
        }
        NamedNodeMap attributes = node.getAttributes();
        for (int x = 0; x < attributes.getLength(); x++) {
            Node attribute = attributes.item(x);
            if(Rule.ATTR_NAME_ID.equals(attribute.getNodeName())) {
                i.setPdqTermIdentifier(attribute.getNodeValue());
            }
        }
        i.setTypeCode(InterventionTypeCode.OTHER);
//        if(rule.equals(Rule.RULE2)) {
//            i.setDescriptionText(Rule.TERM_TYPE_SEMANTIC);
//        }
//        if(rule.equals(Rule.RULE3)) {
//            i.setDescriptionText(Rule.TERM_TYPE_OBSOLETE);
//        }
        InterventionScript.get().add(i, ianList, user);
//        XMLFileParser.getParser().writeDocumentToOutput(doc.getDocumentElement(), 0);
    }
    
    private void rule5() {
        Intervention i = new Intervention();
        List<InterventionAlternateName> ianList = new ArrayList<InterventionAlternateName>();
        Node node = doc.getDocumentElement();
        NodeList children = node.getChildNodes();
        for (int x = 0; x < children.getLength(); x++) {
            Node child = children.item(x);
            if (child.getNodeName().equals(Rule.NODE_NAME_PREFERRED_NAME)) {
                i.setName(child.getTextContent());
            }
            if (child.getNodeName().equals(Rule.NODE_NAME_DEFINITION)) {
                NodeList defs = child.getChildNodes();
                for (int y = 0; y < defs.getLength(); y++) {
                    Node def = defs.item(y);
                    if(def.getNodeName().equals(Rule.NODE_NAME_DEFINITION_TEXT)) {
                        i.setDescriptionText(def.getTextContent());
                    }
                }
                i.setDescriptionText(child.getTextContent());
            }
            if (child.getNodeName().equals(Rule.NODE_NAME_OTHER_NAME)) {
                NodeList others = child.getChildNodes();
                for (int y = 0; y < others.getLength(); y++) {
                    Node other = others.item(y);
                    if(other.getNodeName().equals(Rule.NODE_NAME_OTHER_TERM_NAME)) {
                        InterventionAlternateName ian = new InterventionAlternateName();
                        ian.setName(other.getTextContent());
                        ianList.add(ian);
                    }
                }
            }
            if (child.getNodeName().equals(Rule.NODE_NAME_SEMANTIC_TYPE)) {
                i.setTypeCode(decodeType(child.getTextContent()));
            }
        }
        if (PAUtil.isEmpty(i.getName())) {
            LOG.error("Error determining name from: ");
            XMLFileParser.getParser().writeDocumentToOutput(doc.getDocumentElement(), 0);
            System.exit(0);
        }
        NamedNodeMap attributes = node.getAttributes();
        for (int x = 0; x < attributes.getLength(); x++) {
            Node attribute = attributes.item(x);
            if(Rule.ATTR_NAME_ID.equals(attribute.getNodeName())) {
                i.setPdqTermIdentifier(attribute.getNodeValue());
            }
            if (Rule.ATTR_NAME_NCI_TERM.equals(attribute.getNodeName())) {
                i.setNtTermIdentifier(attribute.getNodeValue());
            }
        }
        InterventionScript.get().add(i, ianList, user);
//        XMLFileParser.getParser().writeDocumentToOutput(doc.getDocumentElement(), 0);
    }
    
    private InterventionTypeCode decodeType(String value) {
        InterventionTypeCode cd = null;
        if (PAUtil.isNotEmpty(value)) {
            if(value.equals("Drug/agent")) {
                cd = InterventionTypeCode.DRUG;
            }
            if(value.equals("Drug/agent category")) {
                cd = InterventionTypeCode.DRUG;
            }
            if(value.equals("Gene")) {
                cd = InterventionTypeCode.GENETIC;
            }
            if(value.equals("Drug/agent combination")) {
                cd = InterventionTypeCode.DRUG;
            }
            if(value.equals("Research activity")) {
                cd = InterventionTypeCode.OTHER;
            }
            if(value.equals("Classification")) {
                cd = InterventionTypeCode.OTHER;
            }
            if(value.equals("Cancer therapy modality")) {
                cd = InterventionTypeCode.OTHER;
            }
            if(value.equals("Other health status")) {
                cd = InterventionTypeCode.OTHER;
            }
            if(value.equals("Intervention or procedure")) {
                cd = InterventionTypeCode.PROCEDURE_SURGERY;
            }
            if (cd == null) {
                LOG.error("Unexpected semantic type found.  ");
            }
            
        }
        return (cd == null) ? InterventionTypeCode.OTHER : cd;
    }
}
