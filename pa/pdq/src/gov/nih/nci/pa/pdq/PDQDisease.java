package gov.nih.nci.pa.pdq;

import gov.nih.nci.pa.domain.Disease;
import gov.nih.nci.pa.domain.DiseaseAltername;
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
public class PDQDisease extends AbstractPDQProcessor {
    private static final Logger LOG = Logger.getLogger(PDQDisease.class);

    /**
     * @param doc
     * @param rule
     */
    public void process(Document doc, Rule rule, String user) {
        this.doc = doc;
        this.rule = rule;
        this.user = user;
        if (rule.equals(Rule.RULE1)) {
            rule1or4();
        }
        if (rule.equals(Rule.RULE4)) {
            rule1or4();
        }
    }
    
    private void rule1or4() {
        Disease d = new Disease();
        List<DiseaseAltername> danList = new ArrayList<DiseaseAltername>();
        
        Node node = doc.getDocumentElement();
        
        // set disease code (PDQ ID)
        NamedNodeMap attributes = node.getAttributes();
        for (int x = 0; x < attributes.getLength(); x++) {
            Node attribute = attributes.item(x);
            if(Rule.ATTR_NAME_ID.equals(attribute.getNodeName())) {
                d.setDiseaseCode(attribute.getNodeValue());
            }
        }

        NodeList children = node.getChildNodes();

        // set preferred name
        for (int x = 0; x < children.getLength(); x++) {
            Node child = children.item(x);
            
            if (child.getNodeName().equals(Rule.NODE_NAME_PREFERRED_NAME)) {
                d.setPreferredName(child.getTextContent());
            }
        }
        for (int x = 0; x < children.getLength(); x++) {
            Node child = children.item(x);
            
            // set menu display name
            if (child.getNodeName().equals(Rule.NODE_NAME_MENU_INFO)) {
                NodeList ddd = child.getChildNodes();
                for (int y = 0; y < ddd.getLength(); y++) {
                    Node eee = ddd.item(y);
                    if (eee.getNodeName().equals(Rule.NODE_NAME_MENU_ITEM)) {
                        NodeList fff = eee.getChildNodes();
                        for (int z = 0; z < fff.getLength(); z++) {
                            Node ggg = fff.item(z);
                            if (ggg.getNodeName().equals(Rule.NODE_NAME_MENU_DISPLAY_NAME)) {
                                d.setMenuDisplayName(ggg.getTextContent());
//                                LOG.info("Found menu DisplayName.");
                            }
                        }
                    }
                }
                if(PAUtil.isEmpty(d.getMenuDisplayName())) {
                    d.setMenuDisplayName(d.getPreferredName());
//                    LOG.info("Had to use preferred name for menu display name.");
                }
            }
            
            // find parent association
            if (child.getNodeName().equals(Rule.NODE_NAME_TERM_RELATIONSHIP)) {
                String parentCode = null;
                String childCode = d.getDiseaseCode();
                String type = null;
                NodeList pars = child.getChildNodes();
                for (int y = 0; y < pars.getLength(); y++) {
                    Node aaa = pars.item(y);
                    if (aaa.getNodeName().equals(Rule.NODE_NAME_PARENT_TERM)) {
                        NodeList bbb = aaa.getChildNodes();
                        for (int z = 0; z < bbb.getLength(); z++) {
                            Node ccc = bbb.item(z);
                            if(ccc.getNodeName().equals(Rule.NODE_NAME_PARENT_TYPE)) {
                                type = ccc.getTextContent();
                            }
                            if(ccc.getNodeName().equals(Rule.NODE_NAME_PARENT_TERM_NAME)) {
                                NamedNodeMap attrs = ccc.getAttributes();
                                for (int xx = 0; xx < attrs.getLength(); xx++) {
                                    Node attr = attrs.item(xx);
                                    if(Rule.ATTR_NAME_REF.equals(attr.getNodeName())) {
                                        parentCode = attr.getNodeValue();
                                    }
                                }
                            }
                        }
                    }
                }
                if(PAUtil.isNotEmpty(parentCode) && PAUtil.isNotEmpty(childCode)) {
                    DiseaseScript.get().addParent(childCode, parentCode, type);
                }
            }
            // find alternate names
            if (child.getNodeName().equals(Rule.NODE_NAME_OTHER_NAME)) {
                NodeList others = child.getChildNodes();
                for (int y = 0; y < others.getLength(); y++) {
                    Node other = others.item(y);
                    if(other.getNodeName().equals(Rule.NODE_NAME_OTHER_TERM_NAME)) {
                        DiseaseAltername dan = new DiseaseAltername();
                        dan.setAlternateName(other.getTextContent());
                        danList.add(dan);
                    }
                }
            }
        }
        if (PAUtil.isEmpty(d.getPreferredName())) {
            LOG.error("Error determining name from: ");
            XMLFileParser.getParser().writeDocumentToOutput(doc.getDocumentElement(), 0);
            System.exit(0);
        }
        DiseaseScript.get().add(d, danList, user);
//        XMLFileParser.getParser().writeDocumentToOutput(doc.getDocumentElement(), 0);
    }
    
}
