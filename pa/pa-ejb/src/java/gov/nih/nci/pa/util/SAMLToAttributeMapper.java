package gov.nih.nci.pa.util;

import gov.nih.nci.cagrid.opensaml.SAMLAssertion;
import gov.nih.nci.pa.service.PAException;

import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;

/**
 * Pulled from CGMM.
 * @author Hugh Reinhart
 * @since Feb 17, 2014
 */
public class SAMLToAttributeMapper {
    /** Email key. */
    public static final String EMAIL = "CGMM_EMAIL_ID";
    /** First name key. */
    public static final String FIRST_NAME = "CGMM_FIRST_NAME";
    /** Last name key. */
    public static final String LAST_NAME = "CGMM_LAST_NAME";
    private static final String EMAIL_EXP = 
            "/*[local-name()='Assertion']/*[local-name()='AttributeStatement']/*[local-name()='Attribute'"
            + " and @AttributeName='urn:mace:dir:attribute-def:mail']/*[local-name()='AttributeValue']/text()";
    private static final String FIRST_NAME_EXP = 
            "/*[local-name()='Assertion']/*[local-name()='AttributeStatement']/*[local-name()='Attribute'"
            + " and @AttributeName='urn:mace:dir:attribute-def:givenName']/*[local-name()='AttributeValue']/text()";
    private static final String LAST_NAME_EXP = 
            "/*[local-name()='Assertion']/*[local-name()='AttributeStatement']/*[local-name()='Attribute'" 
            + " and @AttributeName='urn:mace:dir:attribute-def:sn']/*[local-name()='AttributeValue']/text()";

    /**
     * @param samlAssertion the assertion
     * @return map of vlues
     * @throws PAException exception
     */
    public static synchronized Map<String, String> convertSAMLtoHashMap(SAMLAssertion samlAssertion)
            throws  PAException {
        HashMap<String, String> attributesMap = new HashMap<String, String>();
        DocumentBuilder documentBuilder = null;
        Document document = null;
        try {
            documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            document = documentBuilder.parse(new ByteArrayInputStream(samlAssertion.toString().getBytes()));
        } catch (Exception e) {
            throw new PAException("Exception parsing XML", e);
        }
        XPath xpathEngine = XPathFactory.newInstance().newXPath();
        try {
            String emailId = (String) xpathEngine.evaluate(EMAIL_EXP, document, XPathConstants.STRING);
            String firstName = (String) xpathEngine.evaluate(FIRST_NAME_EXP, document, XPathConstants.STRING);
            String lastName = (String) xpathEngine.evaluate(LAST_NAME_EXP, document, XPathConstants.STRING);
            attributesMap.put(EMAIL, emailId);
            attributesMap.put(FIRST_NAME, firstName);
            attributesMap.put(LAST_NAME , lastName);
        } catch (Exception e) {
            // throw new PAException("Error retrieving user attributes from the SAML.", e);
            // TODO: rewrite code above to work with new jars so default email and name are set for new users
            attributesMap.put(EMAIL, "");
            attributesMap.put(FIRST_NAME, "");
            attributesMap.put(LAST_NAME , "");
        }
        return attributesMap;
    }
}
