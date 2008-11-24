package gov.nih.nci.pa.service.util;

import gov.nih.nci.coppa.iso.Bl;
import gov.nih.nci.coppa.iso.Cd;
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.coppa.iso.Int;
import gov.nih.nci.coppa.iso.St;
import gov.nih.nci.pa.domain.Organization;
import gov.nih.nci.pa.domain.Person;
import gov.nih.nci.pa.enums.BlindingRoleCode;
import gov.nih.nci.pa.enums.StudyParticipationContactRoleCode;
import gov.nih.nci.pa.enums.StudyParticipationFunctionalCode;
import gov.nih.nci.pa.iso.dto.ArmDTO;
import gov.nih.nci.pa.iso.dto.InterventionalStudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.ObservationalStudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.PlannedEligibilityCriterionDTO;
import gov.nih.nci.pa.iso.dto.StudyOutcomeMeasureDTO;
import gov.nih.nci.pa.iso.dto.StudyParticipationContactDTO;
import gov.nih.nci.pa.iso.dto.StudyParticipationDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.StudySiteAccrualStatusDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.DSetConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.correlation.CorrelationUtils;
import gov.nih.nci.pa.service.correlation.OrganizationCorrelationServiceBean;
import gov.nih.nci.pa.service.correlation.PoPaServiceBeanLookup;
import gov.nih.nci.pa.util.PAUtil;

import java.io.StringWriter;
import java.util.List;

import javax.ejb.Stateless;
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
@SuppressWarnings({ "PMD.CyclomaticComplexity", "PMD.ExcessiveMethodLength" , "PMD.TooManyMethods"  })
@Stateless
public class CTGovXmlGeneratorServiceBean implements  CTGovXmlGeneratorServiceRemote {

    private static final Logger LOG  = Logger.getLogger(CTGovXmlGeneratorServiceBean.class);
    private static final String TEXT_BLOCK = "textblock"; 
    private static final String YES = "yes"; 
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
            if (sponsor != null && sponsor.hasChildNodes()) {
                root.appendChild(sponsor);
            }
            appendElement(createElement("brief_summary" , doc), 
                    createElement(TEXT_BLOCK, spDTO.getPublicDescription(), doc), root);
            appendElement(createElement("detailed_description" , doc), 
                    createElement(TEXT_BLOCK, spDTO.getScientificDescription(), doc), root);
            
            //createElement("primary_compl_date" , spDTO.getPrimaryCompletionDate() , doc , root);
            
            appendElement(root, createElement("primary_compl_date_type" , 
                    spDTO.getPrimaryCompletionDateTypeCode() , doc));
            appendElement(root, createElement("phase" , spDTO.getPhaseCode() , doc));
            appendElement(root , createStudyDesign(spDTO, doc));
            
            List<StudyOutcomeMeasureDTO> somDtos = 
                PoPaServiceBeanLookup.getStudyOutcomeMeasureService().getByStudyProtocol(spDTO.getIdentifier());
            
            createPrimaryOutcome(somDtos , doc , root);
            createSecondaryOutcome(somDtos , doc , root);
            createArmGroup(spDTO, doc, root);
            appendElement(root, createEligibility(spDTO, doc));
            createLocation(spDTO.getIdentifier() , doc , root);

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
    private static Element createEligibility(StudyProtocolDTO spDTO , Document doc) throws PAException {
        List<PlannedEligibilityCriterionDTO> paECs = 
            PoPaServiceBeanLookup.getPlannedActivityService().
                getPlannedEligibilityCriterionByStudyProtocol(spDTO.getIdentifier());
        if (paECs == null || paECs.isEmpty()) {
            return null;
        }
        Element eligibility = doc.createElement("eligibility");
//        String genderCode = null;
//        String criterionName = null;
        String descriptionText = null;
        StringBuffer sb = new StringBuffer();
        for (PlannedEligibilityCriterionDTO paEC : paECs) {
//            criterionName = StConverter.convertToString(paEC.getCriterionName());
            descriptionText  = StConverter.convertToString(paEC.getDescriptionText());
//            if (criterionName != null && criterionName.equalsIgnoreCase("GENDER") 
//                    && paEC.getEligibleGenderCode() != null) {
//                genderCode = paEC.getEligibleGenderCode().getCode();
//            } else if (criterionName != null && criterionName.equalsIgnoreCase("MAXIMUM-AGE")) {
//                
//            } else if (criterionName != null && criterionName.equalsIgnoreCase("MAXIMUM-AGE")) {
//                
//            } else 
            if (descriptionText != null) {
                sb.append(descriptionText);
            }
        }
        if (sb.length() > 1) {
            appendElement(createElement("criteria" , doc), 
                    createElement(TEXT_BLOCK, sb.toString(), doc), eligibility);
        }
        return eligibility;
    }
    private static void createArmGroup(StudyProtocolDTO spDTO , Document doc , Element root) throws PAException {
        List<ArmDTO> arms = PoPaServiceBeanLookup.getArmService().getByStudyProtocol(spDTO.getIdentifier());
        if (arms == null || arms.isEmpty()) {
            return;
        }
        for (ArmDTO armDTO : arms) {
            Element armGroup = doc.createElement("arm_group");
            appendElement(armGroup, createElement("arm_group_label" , armDTO.getName() , doc));
            appendElement(armGroup, createElement("arm_type" , armDTO.getTypeCode() , doc));
            appendElement(createElement("arm_group_description" , doc), 
                    createElement(TEXT_BLOCK, spDTO.getPublicDescription(), doc), armGroup);
            if (armGroup.hasChildNodes()) {
                root.appendChild(armGroup);
            }
        }
    }
    private static Element createStudyDesign(StudyProtocolDTO spDTO , Document doc) throws PAException {
        Element studyDesign = doc.createElement("study_design");
        
        if (spDTO.getStudyProtocolType().getValue().equalsIgnoreCase("InterventionalStudyProtocol")) {
            appendElement(studyDesign, createElement("study_type", "Interventional", doc));
            appendElement(studyDesign, createInterventional(spDTO , doc));
        } else if (spDTO.getStudyProtocolType().getValue().equalsIgnoreCase("ObservationalStudyProtocol")) {
            appendElement(studyDesign, createElement("study_type", "Observational", doc));
            appendElement(studyDesign, createObservational(spDTO , doc));
        }
        return studyDesign;
    }
    private static Element createInterventional(StudyProtocolDTO spDTO , Document doc) throws PAException {
        InterventionalStudyProtocolDTO ispDTO = 
            PoPaServiceBeanLookup.getStudyProtocolService().getInterventionalStudyProtocol(spDTO.getIdentifier());
        Element invDesign = doc.createElement("interventional_design");
        appendElement(invDesign, createElement("interventional_subtype", ispDTO.getPrimaryPurposeCode(), doc));
        appendElement(invDesign, createElement("phase", ispDTO.getPhaseCode(), doc));
        appendElement(invDesign, createElement("allocation", ispDTO.getAllocationCode(), doc));
        appendElement(invDesign, createElement("masking", ispDTO.getBlindingSchemaCode(), doc));
        if (ispDTO.getBlindedRoleCode() != null) {
            List<Cd> cds =  DSetConverter.convertDsetToCdList(ispDTO.getBlindedRoleCode());
            if (cds != null) {
                for (Cd cd : cds) {
                    if (BlindingRoleCode.CAREGIVER.getCode().equals(cd.getCode())) {
                        appendElement(invDesign, createElement("masked_caregiver", YES, doc));
                    } else if (BlindingRoleCode.INVESTIGATOR.getCode().equals(cd.getCode())) {
                        appendElement(invDesign, createElement("masked_investigator", YES, doc));                    
                    } else if (BlindingRoleCode.OUTCOMES_ASSESSOR.getCode().equals(cd.getCode())) {
                        appendElement(invDesign, createElement("masked_assessor", YES, doc));                    
                    } else if (BlindingRoleCode.SUBJECT.getCode().equals(cd.getCode())) {
                        appendElement(invDesign, createElement("masked_subject", YES, doc));                    
                    }
                } // for
            } // if 
        } // if
        appendElement(invDesign, createElement("assignment", ispDTO.getDesignConfigurationCode(), doc));
        appendElement(invDesign, createElement("endpoint", ispDTO.getStudyClassificationCode(), doc));
        appendElement(invDesign, createElement("number_of_arms", ispDTO.getNumberOfInterventionGroups() , doc));
        return invDesign;
    }
    
    private static Element createObservational(StudyProtocolDTO spDTO , Document doc) throws PAException {
        ObservationalStudyProtocolDTO ospDTO = 
            PoPaServiceBeanLookup.getStudyProtocolService().getObservationalStudyProtocol(spDTO.getIdentifier());
        Element obsDesign = doc.createElement("observational_design");
        appendElement(obsDesign, createElement("timing", ospDTO.getTimePerspectiveCode(), doc));
        appendElement(obsDesign, createElement("observational_study_design", ospDTO.getStudyModelCode(), doc));
        appendElement(obsDesign, createElement("biospecimen_retention", ospDTO.getBiospecimenRetentionCode(), doc));
        appendElement(obsDesign, createElement("biospecimen_description", ospDTO.getBiospecimenDescription(), doc));
        appendElement(obsDesign, createElement("number_of_groups", ospDTO.getNumberOfGroups(), doc));
        return obsDesign;
    }

    private static void createPrimaryOutcome(List<StudyOutcomeMeasureDTO> somDtos ,  Document doc , Element root) 
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
    private static void createSecondaryOutcome(List<StudyOutcomeMeasureDTO> somDtos , Document doc , Element root) 
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
        if (lead != null && lead.hasChildNodes()) {
            appendElement(sponsors , lead);
        }
        Element collaborator = createCollaborator(studyProtocolIi , doc);
        if (collaborator != null && collaborator.hasChildNodes()) {
            appendElement(sponsors , collaborator);
        }
        if (sponsors.hasChildNodes()) {
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
    
    private static void createLocation(Ii studyProtocolIi , Document doc , Element root) throws PAException {

        StudyParticipationDTO srDTO = new StudyParticipationDTO();
        srDTO.setFunctionalCode(CdConverter.convertToCd(StudyParticipationFunctionalCode.TREATING_SITE));
        List<StudyParticipationDTO> spList =  
            PoPaServiceBeanLookup.getStudyParticipationService().getByStudyProtocol(studyProtocolIi, srDTO);
        CorrelationUtils cUtils = new CorrelationUtils();
        for (StudyParticipationDTO sp : spList) {
            Element location = doc.createElement("location");
            Element facility = doc.createElement("facility");
            Element address = doc.createElement("address");
            
            List<StudySiteAccrualStatusDTO> ssasList = PoPaServiceBeanLookup.getStudySiteAccrualStatusService()
            .getCurrentStudySiteAccrualStatusByStudyParticipation(sp.getIdentifier());
            
            Organization orgBo = cUtils.getPAOrganizationByPAHealthCareFacilityId(
                    IiConverter.convertToLong(sp.getHealthcareFacilityIi()));
            
            appendElement(facility , createElement("name" , orgBo.getName() , doc));
            appendElement(address , createElement("city" , orgBo.getCity() , doc));
            appendElement(address , createElement("state" , orgBo.getState() , doc));
            appendElement(address , createElement("zip" , orgBo.getPostalCode() , doc));
            appendElement(address , createElement("country" , orgBo.getCountryName() , doc));
            appendElement(facility , address);
            appendElement(location , facility);
            if (ssasList != null && (!ssasList.isEmpty())) {
                appendElement(location , createElement("status" , ssasList.get(0).getStatusCode() , doc));
            }

            List<StudyParticipationContactDTO> spcDTOs = PoPaServiceBeanLookup.
                getStudyParticipationContactService().getByStudyParticipation(sp.getIdentifier());
            
            appendElement(root , location);
            createContact(spcDTOs , location , doc);
            createInvestigators(spcDTOs , location , doc);
            createContact(spcDTOs , location , doc);
        }
    }
    
    private static void createInvestigators(List<StudyParticipationContactDTO> spcDTOs, Element location, Document doc) 
    throws PAException {
        CorrelationUtils corr = new CorrelationUtils();
        for (StudyParticipationContactDTO spcDTO : spcDTOs) {
            if (StudyParticipationContactRoleCode.STUDY_PRIMARY_CONTACT.getCode().
                    equals(spcDTO.getRoleCode().getCode())) {
                continue;
            }
            Person p = corr.getPAPersonByPAClinicalResearchStaffId(
                    Long.valueOf(spcDTO.getClinicalResearchStaffIi().getExtension()));
            Element investigator = doc.createElement("investigator");
            appendElement(investigator , createElement("first_name" , p.getFirstName() , doc));
            appendElement(investigator , createElement("middle_name" , p.getMiddleName() , doc));
            appendElement(investigator , createElement("last_name" , p.getLastName() , doc));
            appendElement(investigator , createElement("role" , spcDTO.getRoleCode() , doc));
            if (investigator.hasChildNodes()) {
                appendElement(location , investigator); 
            }
        }
    }

    private static void createContact(List<StudyParticipationContactDTO> spcDTOs, Element location, Document doc) 
    throws PAException {
        CorrelationUtils corr = new CorrelationUtils();
        for (StudyParticipationContactDTO spcDTO : spcDTOs) {
            
            if (!StudyParticipationContactRoleCode.STUDY_PRIMARY_CONTACT.getCode().
                    equals(spcDTO.getRoleCode().getCode())) {
                continue;
            }
            List<String> phones = DSetConverter.convertDSetToList(spcDTO.getTelecomAddresses(), "PHONE");
            List<String> emails = DSetConverter.convertDSetToList(spcDTO.getTelecomAddresses(), "EMAIL");
            Person p = corr.getPAPersonByPAClinicalResearchStaffId(
                    Long.valueOf(spcDTO.getClinicalResearchStaffIi().getExtension()));
            Element contact = doc.createElement("contact");
            appendElement(contact , createElement("first_name" , p.getFirstName() , doc));
            appendElement(contact , createElement("middle_name" , p.getMiddleName() , doc));
            appendElement(contact , createElement("last_name" , p.getLastName() , doc));
            if (phones != null && !phones.isEmpty()) {
                appendElement(contact , createElement("phone" , phones.get(0) , doc));
            }
            if (emails != null && !emails.isEmpty()) {
                appendElement(contact , createElement("email" , emails.get(0) , doc));
            }
            if (contact.hasChildNodes()) {
                appendElement(location , contact); 
            }
        }
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
    private static Element createElement(String elementName , Cd cd , Document doc) {
        if (cd == null || elementName == null || cd.getCode() == null) {
            return null;
        }
        return createElement(elementName  , cd.getCode() , doc);
    }
    private static Element createElement(String elementName , St st , Document doc) {
        if (st == null || elementName == null || st.getValue() == null) {
            return null;
        }
        return createElement(elementName  , st.getValue() , doc);
    }
    private static Element createElement(String elementName , Int i , Document doc) {
        if (i == null || elementName == null || i.getValue() == null) {
            return null;
        }
        return createElement(elementName  , i.getValue().toString() , doc);
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
