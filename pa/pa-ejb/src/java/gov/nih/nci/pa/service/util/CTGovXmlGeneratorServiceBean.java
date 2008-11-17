package gov.nih.nci.pa.service.util;

import gov.nih.nci.coppa.iso.Bl;
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.pa.domain.Organization;
import gov.nih.nci.pa.enums.StudyParticipationFunctionalCode;
import gov.nih.nci.pa.iso.dto.StudyOutcomeMeasureDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.correlation.OrganizationCorrelationServiceBean;
import gov.nih.nci.pa.service.correlation.PoPaServiceBeanLookup;
import gov.nih.nci.pa.util.PAUtil;

import java.io.StringWriter;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

/** 
* service bean for generating ct.gov.xml.
* 
* @author Naveen Amiruddin
* @since 06/26/2008
* copyright NCI 2007.  All rights reserved.
* This code may not be used without the express written permission of the
* copyright holder, NCI.
*/
@SuppressWarnings({ "PMD.ExcessiveMethodLength" , "PMD.UnusedPrivateMethod" , "PMD.TooManyMethods" })

public class CTGovXmlGeneratorServiceBean {
//implements  CTGovXmlGeneratorServiceRemote {

    private static final Logger LOG  = Logger.getLogger(CTGovXmlGeneratorServiceBean.class);
    /**
     * @param studyProtocolIi ii of studyprotocol
     * @return String xml output
     * @throws PAException on error
     */
    public String generateCTGovXml(Ii studyProtocolIi) throws PAException {
        if (studyProtocolIi == null) {
            throw new PAException("Study Protocol Identifer is null");
        }
        LOG.debug("Entering generateCTGovXml");
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            
            StudyProtocolDTO spDTO = getStudyProtocol(studyProtocolIi);
            
            DocumentBuilder docBuilder = factory.newDocumentBuilder();
            Document doc = docBuilder.newDocument();
            //create the root element
            Element root = createElement("clinical_study" , doc);
            doc.appendChild(root);
            root.appendChild(createIndInfo(spDTO , doc));
            createElement("is_fda_regulated" , 
                    convertBLToString(spDTO.getFdaRegulatedIndicator()) , doc , root);
            createElement("is_section_801" , 
                    convertBLToString(spDTO.getSection801Indicator()) , doc , root);
            createElement("delayed_posting" , 
                    convertBLToString(spDTO.getDelayedpostingIndicator()) , doc , root);
            createElement("brief_title" , spDTO.getPublicTitle().getValue() , doc , root);
            createElement("acronym" , spDTO.getAcronym().getValue() , doc , root);
            createElement("official_title" , spDTO.getOfficialTitle().getValue() , doc , root);
            // sponsor 
            Element sponsor = createSponsors(spDTO.getIdentifier() , doc);
            if (sponsor != null) {
                root.appendChild(sponsor);
            }
            appendElement(createElement("brief_summary" , doc), 
                    createElement("textblock", spDTO.getPublicDescription().getValue(), doc), root);
            appendElement(createElement("detailed_description" , doc), 
                    createElement("textblock", spDTO.getScientificDescription().getValue(), doc), root);
            
            //createElement("primary_compl_date" , spDTO.getPrimaryCompletionDate() , doc , root);
            createElement("primary_compl_date_type" , spDTO.getPrimaryCompletionDateTypeCode().getCode() , doc , root);
            createElement("phase" , spDTO.getPhaseCode().getCode() , doc , root);
            
            List<StudyOutcomeMeasureDTO> somDtos = 
                PoPaServiceBeanLookup.getStudyOutcomeMeasureService().getByStudyProtocol(spDTO.getIdentifier());
            
            createPrimaryOutcome(somDtos , root , doc);
            createSecondaryOutcome(somDtos , root , doc);
            
//            TransformerFactory tranFactory = TransformerFactory.newInstance(); 
//            Transformer aTransformer = tranFactory.newTransformer(); 
//
//            Source src = new DOMSource(doc); 
//            File f = new File("c://sam.xml");
//            Result dest = new StreamResult(f);
//            
//            aTransformer.transform(src, dest);         
           
            //return src.toString();

            DOMSource domSource = new DOMSource(doc);
            StringWriter writer = new StringWriter();
            StreamResult result = new StreamResult(writer);
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            transformer.transform(domSource, result);
            return writer.toString();
        } catch (Exception e) {
            LOG.error("Error while generating CT.GOV.xml "  , e);
            throw new PAException("Error while generating CT.GOV.xml "  , e);
        }
    }

    
    private static Element createIndInfo(StudyProtocolDTO spDTO , Document doc) {
        //create info element
        Element idInfo = doc.createElement("id_info");
        //appendElement(idInfo , createElement("provider_name" , "provider_name data...", doc));
        //appendElement(idInfo , createElement("provider_study_id" , "provider_study_id data...", doc));
        appendElement(idInfo , createElement("org_name" , "NCI", doc));
        appendElement(idInfo , createElement("org_study_id" , spDTO.getAssignedIdentifier().getExtension(), doc));
        //appendElement(idInfo , createElement("secondary_id" , "NCI..333.", doc));
        //@todo : handle a way to create secondary contact
        return idInfo;
        
    }
    
    private static void createPrimaryOutcome(List<StudyOutcomeMeasureDTO> somDtos , Element root , Document doc) 
    throws PAException {
        if (somDtos == null || somDtos.isEmpty()) {
            return;
        }
        for (StudyOutcomeMeasureDTO smDTO : somDtos) {
            if (smDTO.getPrimaryIndicator().getValue().booleanValue()) {
                Element po = createElement("primary_outcome", doc);
                appendElement(po, createElement("outcome_measure", smDTO.getName().getValue(), doc));
                appendElement(po, 
                        createElement("outcome_safety_issue", convertBLToString(smDTO.getSafetyIndicator()), doc));
                appendElement(po, createElement("outcome_time_frame", smDTO.getTimeFrame().getValue(), doc));
                if (po.hasChildNodes()) {
                    appendElement(root, po);
                }
            }
        }
    }
    private static void createSecondaryOutcome(List<StudyOutcomeMeasureDTO> somDtos , Element root , Document doc) 
    throws PAException {
        if (somDtos == null || somDtos.isEmpty()) {
            return;
        }
        for (StudyOutcomeMeasureDTO smDTO : somDtos) {
            if (!smDTO.getPrimaryIndicator().getValue().booleanValue()) {
                Element om = createElement("secondary_outcome", doc);
                appendElement(om, createElement("outcome_measure", smDTO.getName().getValue(), doc));
                appendElement(om, 
                        createElement("outcome_safety_issue", convertBLToString(smDTO.getSafetyIndicator()), doc));
                appendElement(om, createElement("outcome_time_frame", smDTO.getTimeFrame().getValue(), doc));
                if (om.hasChildNodes()) {
                    appendElement(root, om);
                }
            }
        }
    }
    
    private static Element createSponsors(Ii studyProtocolIi  , Document doc) throws PAException {
        Element sponsors = doc.createElement("sponsors");
        Element lead = createLeadSponsor(studyProtocolIi , doc);
        boolean added = false;
        if (lead != null) {
            appendElement(sponsors , lead);
            added = true;
        }
        Element collaborator = createCollaborator(studyProtocolIi , doc);
        if (collaborator != null) {
            appendElement(sponsors , collaborator);
            added = true;
        }
        if (added) {
            return sponsors;
        } else {
            return null;
        }
    }
    
    private static Element createLeadSponsor(Ii studyProtocolIi , Document doc) throws PAException {
        OrganizationCorrelationServiceBean osb = new OrganizationCorrelationServiceBean(); 
        
        List<Organization> orgs = osb.getOrganizationByStudyParticipation(
                Long.valueOf(studyProtocolIi.getExtension()), StudyParticipationFunctionalCode.LEAD_ORAGANIZATION);
                
        if (orgs == null || orgs.isEmpty()) {
            return null;
        }
        Element lead = doc.createElement("lead_sponsor");
        for (Organization org : orgs) {
            appendElement(lead , createElement("agency" , org.getName(), doc));
        }
        return lead;
    }

    private static Element createCollaborator(Ii studyProtocolIi , Document doc) throws PAException {
        OrganizationCorrelationServiceBean osb = new OrganizationCorrelationServiceBean(); 
        
        List<Organization> orgs = osb.getOrganizationByStudyParticipation(
                Long.valueOf(studyProtocolIi.getExtension()), StudyParticipationFunctionalCode.COLLABORATORS);
                
        if (orgs == null || orgs.isEmpty()) {
            return null;
        }
        Element collaborator = doc.createElement("collaborator");
        for (Organization org : orgs) {
            appendElement(collaborator , createElement("agency" , org.getName(), doc));
        }
        return collaborator;
    }
    
    private static Element createLocation(Ii studyProtocolIi , Document doc) throws PAException {
        OrganizationCorrelationServiceBean osb = new OrganizationCorrelationServiceBean(); 
        
        List<Organization> orgs = osb.getOrganizationByStudyParticipation(
                Long.valueOf(studyProtocolIi.getExtension()), StudyParticipationFunctionalCode.TREATING_SITE);
                
        if (orgs == null || orgs.isEmpty()) {
            return null;
        }
        Element collaborator = doc.createElement("location");
        for (Organization org : orgs) {
            appendElement(collaborator , createElement("agency" , org.getName(), doc));
        }
        return collaborator;
    }
    
    private static Element createElement(final String elementName , final Document doc) throws PAException {
        if (PAUtil.isEmpty(elementName)) {
            LOG.error("Elementname is null");
            throw new PAException("Element name is null");
        }
        return doc.createElement(elementName);
    }

    private static Element createElement(String elementName , String data , Document doc) {
        if (data == null || elementName == null) {
            return null;
        }
        Element element = doc.createElement(elementName);
        Text text = doc.createTextNode(data);
        element.appendChild(text);
        return element;
    }

    private static void  createElement(final String elementName , String data , Document doc ,  Element root) {
        Element element = createElement(elementName , data , doc);
        if (element != null) {
            root.appendChild(element);
        }
    }    
    private static void appendElement(Element parent, Element child) {
        if (parent != null && child != null) {
            parent.appendChild(child);
        }
    }
    private static void appendElement(Element parent, Element child , Element root) {
        if (parent != null && child != null && root != null) {
            parent.appendChild(child);
            root.appendChild(parent);
        }
    }
    
    
    ///////////////////////////////////// methods for get the data 
    
    private static StudyProtocolDTO getStudyProtocol(Ii studyProtocolIi) throws PAException {
        StudyProtocolDTO spDTO = PoPaServiceBeanLookup.getStudyProtocolService().getStudyProtocol(studyProtocolIi);
        if (spDTO == null) {
            throw new PAException("Study Protocol is not available for given id = " + studyProtocolIi.getExtension());
        }
        return spDTO;
    }
    
    ///  utilitiy methods
    private static String convertBLToString(Bl bl) {
        if (bl == null) {
            return null;
        }
        Boolean b = bl.getValue();
        if (b != null && b.booleanValue()) {
            return "Yes";
        } else {
            return "No";
        }
    }
}
