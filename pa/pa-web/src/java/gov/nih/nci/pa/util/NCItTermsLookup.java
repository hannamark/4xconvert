package gov.nih.nci.pa.util;
import gov.nih.nci.pa.dto.DiseaseWebDTO;
import gov.nih.nci.pa.dto.InterventionWebDTO;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

/**
 * Utility class for invoking LEX EVS webservices to retrieve term 
 * (intervention and disease) definition from NCI Thesaurus
 * @author gopal
 */
@SuppressWarnings({ "PMD.CyclomaticComplexity", "PMD.NPathComplexity", "PMD.ExcessiveMethodLength" })
public class NCItTermsLookup {

    private static final String ELEMENT_NAME_FIELD = "field";
    private static final String ATTR_NAME_NAME = "name";
    private static final String ELEMENT_NAME_CLASS = "class";

    
    /**
     * NCI Term
     * 
     * @author gopal
     */
    @SuppressWarnings("PMD")
    private static class NCItTerm {
        private String ncitCode; 
        private String preferredName;
        
        private List<NCItTerm> parentTerms = new ArrayList<NCItTerm>(); //NOPMD
        private List<NCItTermAlterName> alterNames = new ArrayList<NCItTermsLookup.NCItTermAlterName>();
        
    }

    /**
     * NCITerm Altname
     * 
     * @author gopal
     *
     */
    @SuppressWarnings("PMD")
    private static class NCItTermAlterName {
        static final Map<String, String> ALTNAMECODEMAP;
        static {
            Map<String, String> aMap = new HashMap<String, String>();
            aMap.put("SY", "Synonym");
            aMap.put("AB", "Abbreviation");
            aMap.put("BR", "US brand name");
            aMap.put("FB", "Foreign brand name");
            aMap.put("NY", "Chemical structure name");
            aMap.put("CN", "Code name");
            aMap.put("NSC_Code", "NSC number");
            aMap.put("CAS_Registry", "CAS Registry name");
            ALTNAMECODEMAP = Collections.unmodifiableMap(aMap);
        }
        
        private String alterName;
        private String code;

        NCItTermAlterName(String alterName, String codeAbbr) {
            this.alterName = alterName;
            this.code = ALTNAMECODEMAP.get(codeAbbr);
        }
       
    }

    /**
     *  LEXEVS CTS REST service URL
     */
    public static final String LEXVSCTSURL =
            "http://lexevscts2.nci.nih.gov/lexevscts2/codesystem/NCI_Thesaurus/entity/";
    /**
     * LEXEVS API XML service URL
     */
    public static final String LEXEVSAPIURL =
            "http://lexevsapi61.nci.nih.gov/lexevsapi61/GetXML?query=Entity[@_entityCode={CODE}]";

    /**
     * Lookup for disease by NCIt Code in NCI Thesaurus
     * 
     * @param ncitCode ncit code of the term
     * @return {@link DiseaseWebDTO} instance for matched disease,
     *         <code>null</code> if no match was found
     * @throws LEXEVSLookupException
     *             if there were error during the lookup
     */
    public InterventionWebDTO lookupIntervention(String ncitCode) throws LEXEVSLookupException {
        InterventionWebDTO intrv = null;
        NCItTerm term = retrieveNCItTermViaLexEVS(ncitCode);
        if (term != null) {
            intrv = new InterventionWebDTO();
            intrv.setName(term.preferredName);
            intrv.setNtTermIdentifier(term.ncitCode);
            List<String> altNames = new ArrayList<String>();
            for (Iterator<NCItTermAlterName> iterator = term.alterNames.iterator(); iterator.hasNext();) {
                NCItTermAlterName altName = iterator.next();
                altNames.add(altName.alterName);

            }
            intrv.setAlterNames(altNames);
        }
        return intrv;
    }

    /**
     * Lookup for disease by NCIt Code in NCI Thesaurus
     * 
     * @param ncitCode NCIt code of the term
     * @return {@link DiseaseWebDTO} instance for matched disease,
     *         <code>null</code> if no match was found
     * @throws LEXEVSLookupException
     *             if there were error during the lookup
     */
    public DiseaseWebDTO lookupDisease(String ncitCode) throws LEXEVSLookupException {
        DiseaseWebDTO disease = null;
        NCItTerm term = retrieveNCItDiseaseTermViaLexEVSCTS(ncitCode, true);
        if (term != null) {
            List<NCItTerm> children = retrieveDiseaseChildren(ncitCode);
            disease = new DiseaseWebDTO();
            disease.setPreferredName(term.preferredName);
            disease.setNtTermIdentifier(term.ncitCode);
            for (Iterator<NCItTermAlterName> iterator = term.alterNames.iterator(); iterator.hasNext();) {
                NCItTermAlterName altName = iterator.next();
                disease.getAlterNameList().add(altName.alterName);

            }

            // Add parents
            for (Iterator<NCItTerm> iterator = term.parentTerms.iterator(); iterator.hasNext();) {
                NCItTerm ncItTerm = iterator.next();
                disease.getParentTermList().add(ncItTerm.ncitCode + ": " + ncItTerm.preferredName);
            }

            // Add children
            for (Iterator<NCItTerm> iterator = children.iterator(); iterator.hasNext();) {
                NCItTerm ncItTerm = iterator.next();
                disease.getChildTermList().add(ncItTerm.ncitCode + ": " + ncItTerm.preferredName);
            }
        }

        return disease;
    }

    /**
     * Retrieve a NCI term via LEX EVS CTS RESTful webservice
     * 
     * @param ncitCode
     * @return {@link NCItTerm}, <code>null</code> if term was not found
     * @throws LEXEVSLookupException
     *             if there were errors invoking the web service
     */
    private NCItTerm retrieveNCItDiseaseTermViaLexEVSCTS(String ncitCode, boolean getParent) 
            throws LEXEVSLookupException {
        Element termEl = invokeWebService(LEXVSCTSURL + ncitCode);
        
        if (termEl == null) { // Term not found
            return null;
        }
        
        NCItTerm term = new NCItTerm();
        term.ncitCode = ncitCode;
        try {
        // Term not found
        if (getChildElementsByName(termEl, "EntityDescription").size() == 0) {
            return null;
        }

        List<Element> designations = getChildElementsByName(
                getChildElementsByName(getChildElementsByName(termEl, "EntityDescription").get(0), "namedEntity")
                        .get(0), "designation");
        // Get preferred Name and synonyms
        for (Iterator<Element> iterator = designations.iterator(); iterator.hasNext();) {
            Element designation = iterator.next();
            if ("PREFERRED".equals(designation.getAttribute("designationRole"))) {
                term.preferredName = getChildElementsByName(designation, "core:value").get(0).getChildNodes().item(0)
                        .getNodeValue();
            } else if ("ALTERNATIVE".equals(designation.getAttribute("designationRole"))) {
                term.alterNames.add(new NCItTermAlterName(getChildElementsByName(designation, "core:value").get(0)
                        .getChildNodes().item(0).getNodeValue(), "Synonym"));
            }
        }

        // Get parent terms
        if (getParent) {
            List<Element> parents = getChildElementsByName(
                    getChildElementsByName(getChildElementsByName(termEl, "EntityDescription").get(0), "namedEntity")
                            .get(0), "parent");
            for (Iterator<Element> iterator = parents.iterator(); iterator.hasNext();) {
                String parentCode = getChildElementsByName(iterator.next(), "core:name").get(0).getChildNodes().item(0)
                        .getNodeValue();
                if (parentCode.charAt(0) == 'C') {
                    NCItTerm parentTerm = retrieveNCItDiseaseTermViaLexEVSCTS(parentCode, false);
                    if (parentTerm != null) {
                        term.parentTerms.add(parentTerm);
                    }
                }
            }
        }
        } catch (Exception e) {
            throw new LEXEVSLookupException("Exception while parsing LEX EVS service response", e);
        }
        return term;
    }

    /**
     * Retrieve a children terms of the given disease term
     * 
     * @param ncitCode
     * @return List of children terms
     * @throws LEXEVSLookupException
     *             if there were errors invoking the web service
     */
    private List<NCItTerm> retrieveDiseaseChildren(String ncitCode) throws LEXEVSLookupException {

        List<NCItTerm> children = new ArrayList<NCItTerm>();

        Element termEl = invokeWebService(LEXVSCTSURL + ncitCode + "/children");
        if (termEl == null) {
            return children;
        }
        
        try {
        List<Element> entries = getChildElementsByName(termEl, "entry");

        // Get preferred Name and synonyms
        for (Iterator<Element> iterator = entries.iterator(); iterator.hasNext();) {
            Element entry = iterator.next();
            String childCode = getChildElementsByName(getChildElementsByName(entry, "core:name").get(0), "core:name")
                    .get(0).getChildNodes().item(0).getNodeValue();
            if (childCode.charAt(0) == 'C') {
                String childName = getChildElementsByName(
                        getChildElementsByName(entry, "core:knownEntityDescription").get(0), "core:designation").get(0)
                        .getChildNodes().item(0).getNodeValue();
                NCItTerm t = new NCItTerm();
                t.ncitCode = childCode;
                t.preferredName = childName;
                children.add(t);
            }
        }
        } catch (Exception e) {
            throw new LEXEVSLookupException("Exception while parsing LEX EVS service response", e);
        }
        return children;
    }

    /**
     * Retrieve an term by NCIt Code from NCI thesaurus via LEXEVS 6.1 API. This
     * is used for retrieving intervention term as this API returns the type of
     * the alternate names which is required for interventions in CTRP
     * 
     * @param ncitCode
     * @return {@link NCItTerm}, <code>null</code> if term was not found
     * @throws LEXEVSLookupException
     *             if there were errors invoking the web service
     */
    private NCItTerm retrieveNCItTermViaLexEVS(String ncitCode) throws LEXEVSLookupException {
        
        Element termEl = invokeWebService(LEXEVSAPIURL.replace("{CODE}", ncitCode));
        
        if (termEl == null) {
            return null;
        }
        
        NCItTerm term = new NCItTerm();
        term.ncitCode = ncitCode;

        try {
        // term not found
        if (getChildElementsByName(termEl, "queryResponse").size() == 0) {
            return null;
        }

        List<Element> classList = getChildElementsByName(getChildElementsByName(termEl, "queryResponse").get(0),
                ELEMENT_NAME_CLASS);
        Element entity = null;
        for (int i = 0; i < classList.size(); i++) {
            entity = (Element) classList.get(i);
            if (entity.getAttribute(ATTR_NAME_NAME).equals("org.LexGrid.concepts.Entity")) {
                break;
            }
        }

        // Parse presentations
        List<Element> fieldList = getChildElementsByName(entity, ELEMENT_NAME_FIELD);
        List<Element> presentations = null;
        for (int i = 0; i < fieldList.size(); i++) {
            if (((Element) fieldList.get(i)).getAttribute(ATTR_NAME_NAME).equals("_presentationList")) {
                presentations = getChildElementsByName(fieldList.get(i), ELEMENT_NAME_CLASS);
                break;
            }
        }

        for (int j = 0; j < presentations.size(); j++) {
            Element presentation = (Element) presentations.get(j);
            boolean isPrefrred = false;
            List<Element> fields = getChildElementsByName(presentation, ELEMENT_NAME_FIELD);
            String value = null, type = null;
            for (int k = 0; k < fields.size(); k++) {
                if (((Element) fields.get(k)).getAttribute(ATTR_NAME_NAME).equals("_isPreferred")) {
                    if (fields.get(k).getChildNodes().getLength() > 0
                            && fields.get(k).getChildNodes().item(0).getNodeValue() != null
                            && fields.get(k).getChildNodes().item(0).getNodeValue().equals("true")) {
                        isPrefrred = true;
                    }
                } else if (((Element) fields.get(k)).getAttribute(ATTR_NAME_NAME).equals("_value")) {
                    List<Element> values = getChildElementsByName(
                            getChildElementsByName(fields.get(k), ELEMENT_NAME_CLASS).get(0), ELEMENT_NAME_FIELD);
                    for (int l = 0; l < values.size(); l++) {
                        if (((Element) values.get(l)).getAttribute(ATTR_NAME_NAME).equals("_content") 
                                && values.get(l).getChildNodes().getLength() > 0
                                && values.get(l).getChildNodes().item(0).getNodeValue() != null) {
                            value = values.get(l).getChildNodes().item(0).getNodeValue().trim();
                        }
                    }

                } else if (((Element) fields.get(k)).getAttribute(ATTR_NAME_NAME).equals("_representationalForm") 
                        && fields.get(k).getChildNodes().getLength() > 0
                        && fields.get(k).getChildNodes().item(0).getNodeValue() != null) {
                        type = fields.get(k).getChildNodes().item(0).getNodeValue().trim();
                }

            }
            if (isPrefrred) {
                term.preferredName = value;
            } else if (NCItTermAlterName.ALTNAMECODEMAP.containsKey(type)) {
                term.alterNames.add(new NCItTermAlterName(value, type));
            }
        }

        // Parse properties
        List<Element> properties = null;
        fieldList = getChildElementsByName(termEl, ELEMENT_NAME_FIELD);
        for (int i = 0; i < fieldList.size(); i++) {
            if (((Element) fieldList.get(i)).getAttribute(ATTR_NAME_NAME).equals("_propertyList")) {
                properties = getChildElementsByName(fieldList.get(i), ELEMENT_NAME_CLASS);
            }
        }

        if (properties != null) {
            for (int j = 0; j < properties.size(); j++) {
                Element prop = (Element) properties.get(j);

                String value = null, type = null;
                NodeList fields = prop.getElementsByTagName(ELEMENT_NAME_FIELD);
                for (int k = 0; k < fields.getLength(); k++) {
                    if (((Element) fields.item(k)).getAttribute(ATTR_NAME_NAME).equals("_value")) {
                        NodeList values = ((Element) ((Element) fields.item(k)).
                                getElementsByTagName(ELEMENT_NAME_CLASS).item(0))
                                .getElementsByTagName(ELEMENT_NAME_FIELD);
                        for (int l = 0; l < values.getLength(); l++) {
                            if (((Element) values.item(l)).getAttribute(ATTR_NAME_NAME).equals("_content") 
                                    && values.item(l).getNodeValue() != null) {
                                value = values.item(l).getNodeValue().trim();
                            }
                        }

                    } else if (((Element) fields.item(k)).getAttribute(ATTR_NAME_NAME).equals("_propertyName")
                            && fields.item(k).getNodeValue() != null) {
                            type = fields.item(k).getNodeValue().trim();
                    }

                }
                if (NCItTermAlterName.ALTNAMECODEMAP.containsKey(type)) {
                    term.alterNames.add(new NCItTermAlterName(value, type));
                }
            }
        }
        } catch (Exception e) {
            throw new LEXEVSLookupException("Exception while parsing LEX EVS service response", e);
        }
        return term;
    }

    /**
     * Invoke the LEXEVS RESTful service and return the parsed response
     * 
     * @param url
     * @return the parsed XML Document object
     * @throws LEXEVSLookupException
     *             when there were errors during the invocation or parsing the
     *             response XML
     */
    private Element invokeWebService(String url) throws LEXEVSLookupException {
        CloseableHttpResponse response = null;
        try {
            CloseableHttpClient httpclient = HttpClients.createDefault();
            HttpGet httpGet = new HttpGet(url);
            response = httpclient.execute(httpGet);

            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                HttpEntity entity = response.getEntity();
                DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = null;
                builder = builderFactory.newDocumentBuilder();
                StringWriter writer = new StringWriter();
                IOUtils.copy(entity.getContent(), writer);
                Document document = builder.parse(new InputSource(new StringReader(writer.toString())));
                return document.getDocumentElement();
            } else if (response.getStatusLine().getStatusCode() == HttpStatus.SC_NOT_FOUND) {
                return null; // term not found
            } else {
                throw new Exception("Error making LexEVS request: " + response.getStatusLine());
            }
        } catch (Exception e) {
            throw new LEXEVSLookupException("Exception while invoking LexEVS service ", e);
        } finally {
            if (response != null) {
                try {
                    response.close();    
                } catch (IOException e) { //NOPMD
                    //Ignore 
                }
            }
        }
    }

    /**
     * Get immediate child elements with the specified name
     * 
     * @param parent
     * @param name
     * @return list of matching child elements
     */
    private List<Element> getChildElementsByName(Element parent, String name) {
        List<Element> result = new ArrayList<Element>();
        Node child;
        for (child = parent.getFirstChild(); child != null; child = child.getNextSibling()) {
            if (child.getNodeType() == Node.ELEMENT_NODE && child.getNodeName().equals(name)) {
                result.add((Element) child);
            }
        }
        return result;
    }
    
    
 /**
 * @param ncitCode ncitCode
 * @param isParent true indicates if parents needs to fetch false indicates childs to be fetched
 * @return List<String>
 * @throws LEXEVSLookupException Exception
 */
public List<String> fetchTree(String ncitCode, boolean isParent) throws LEXEVSLookupException {
        List<String> ncitCodeList = new ArrayList<String>();
        NCItTerm term = null;
        List<NCItTerm> termsList = new ArrayList<NCItTerm>();
        if (isParent) {
           term = retrieveNCItDiseaseTermViaLexEVSCTS(ncitCode, true);
           termsList = term.parentTerms;
        } else {
          termsList = retrieveDiseaseChildren(ncitCode);
        }
       for (NCItTerm term2:termsList) {
         ncitCodeList.add(term2.ncitCode);
       }
       
       return ncitCodeList;
     }
 }