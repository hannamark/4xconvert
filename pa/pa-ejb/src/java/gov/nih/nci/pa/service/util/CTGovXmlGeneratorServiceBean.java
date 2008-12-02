package gov.nih.nci.pa.service.util;

import gov.nih.nci.coppa.iso.Ad;
import gov.nih.nci.coppa.iso.Adxp;
import gov.nih.nci.coppa.iso.AdxpAl;
import gov.nih.nci.coppa.iso.AdxpCnt;
import gov.nih.nci.coppa.iso.AdxpCty;
import gov.nih.nci.coppa.iso.AdxpSta;
import gov.nih.nci.coppa.iso.AdxpZip;
import gov.nih.nci.coppa.iso.Bl;
import gov.nih.nci.coppa.iso.Cd;
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.coppa.iso.Int;
import gov.nih.nci.coppa.iso.St;
import gov.nih.nci.coppa.iso.TelEmail;
import gov.nih.nci.coppa.iso.TelPhone;
import gov.nih.nci.coppa.iso.Ts;
import gov.nih.nci.pa.domain.Country;
import gov.nih.nci.pa.domain.Organization;
import gov.nih.nci.pa.domain.Person;
import gov.nih.nci.pa.domain.RegulatoryAuthority;
import gov.nih.nci.pa.enums.BlindingRoleCode;
import gov.nih.nci.pa.enums.ReviewBoardApprovalStatusCode;
import gov.nih.nci.pa.enums.StudyContactRoleCode;
import gov.nih.nci.pa.enums.StudyParticipationContactRoleCode;
import gov.nih.nci.pa.enums.StudyParticipationFunctionalCode;
import gov.nih.nci.pa.enums.StudyStatusCode;
import gov.nih.nci.pa.iso.dto.ArmDTO;
import gov.nih.nci.pa.iso.dto.InterventionalStudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.ObservationalStudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.PlannedEligibilityCriterionDTO;
import gov.nih.nci.pa.iso.dto.StudyContactDTO;
//import gov.nih.nci.pa.iso.dto.StudyIndldeDTO;
import gov.nih.nci.pa.iso.dto.StudyOutcomeMeasureDTO;
import gov.nih.nci.pa.iso.dto.StudyOverallStatusDTO;
import gov.nih.nci.pa.iso.dto.StudyParticipationContactDTO;
import gov.nih.nci.pa.iso.dto.StudyParticipationDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.StudyRegulatoryAuthorityDTO;
import gov.nih.nci.pa.iso.dto.StudySiteAccrualStatusDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.DSetConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.correlation.CorrelationUtils;
import gov.nih.nci.pa.service.correlation.OrganizationCorrelationServiceBean;
import gov.nih.nci.pa.service.correlation.PoPaServiceBeanLookup;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.services.entity.NullifiedEntityException;
import gov.nih.nci.services.organization.OrganizationDTO;
//import gov.nih.nci.services.person.PersonDTO;

import java.io.StringWriter;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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
@SuppressWarnings({ "PMD.CyclomaticComplexity", "PMD.ExcessiveClassLength" , 
    "PMD.ExcessiveMethodLength" , "PMD.TooManyMethods" , "PMD.NPathComplexity"  })
@Stateless
public class CTGovXmlGeneratorServiceBean implements  CTGovXmlGeneratorServiceRemote {

    private static final Logger LOG  = Logger.getLogger(CTGovXmlGeneratorServiceBean.class);
    private static final String TEXT_BLOCK = "textblock"; 
    private static final String YES = "Yes";
    private static final String NO = "No";
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
            createIdInfo(spDTO, doc, root);
            createElement("is_fda_regulated" , 
                    convertBLToString(spDTO.getFdaRegulatedIndicator()) , doc , root);
            createElement("is_section_801" , 
                    convertBLToString(spDTO.getSection801Indicator()) , doc , root);
            createElement("delayed_posting" , 
                    convertBLToString(spDTO.getDelayedpostingIndicator()) , doc , root);
            ////// error createIndInfo(spDTO , doc , root);

            createElement("brief_title" , spDTO.getPublicTitle().getValue() , doc , root);
            createElement("acronym" , spDTO.getAcronym().getValue() , doc , root);
            createElement("official_title" , spDTO.getOfficialTitle().getValue() , doc , root);
            createSponsors(spDTO.getIdentifier() , doc , root);
            createOversightInfo(spDTO , doc , root);
            appendElement(createElement("brief_summary" , doc), 
                    createElement(TEXT_BLOCK, spDTO.getPublicDescription(), doc), root);
            appendElement(createElement("detailed_description" , doc), 
                    createElement(TEXT_BLOCK, spDTO.getScientificDescription(), doc), root);
            createOverallStatus(spDTO, doc, root);
            createElement("expanded_access_status", convertBLToString(spDTO.getExpandedAccessIndicator()), doc , root);
            appendElement(root ,  createElement("start_date", convertTsToYYYYMMFormart(spDTO.getStartDate()), doc));
            appendElement(root ,  createElement("primary_compl_date", convertTsToYYYYMMFormart(
                                spDTO.getPrimaryCompletionDate()), doc));
            appendElement(root ,  createElement("primary_compl_date_type", 
                    spDTO.getPrimaryCompletionDateTypeCode(), doc)); 

            appendElement(root , createStudyDesign(spDTO, doc));
            List<StudyOutcomeMeasureDTO> somDtos = 
                PoPaServiceBeanLookup.getStudyOutcomeMeasureService().getByStudyProtocol(spDTO.getIdentifier());
            createPrimaryOutcome(somDtos , doc , root);
            createSecondaryOutcome(somDtos , doc , root);
            appendElement(root ,  createElement("enrollment", spDTO.getMaximumTargetAccrualNumber(), doc)); 
            createArmGroup(spDTO, doc, root);
            //createIntervention(spDTO, doc, root);
            createEligibility(spDTO, doc , root);
            createOverallOfficial(spDTO, doc , root);
            createLocation(spDTO , doc , root);
            appendElement(root ,  createElement("keyword", spDTO.getKeywordText(), doc)); 
            appendElement(root ,  createElement("verification_date", convertTsToYYYYMMFormart(
                    spDTO.getRecordVerificationDate()), doc));

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

    private static void createOverallStatus(StudyProtocolDTO spDTO , Document doc , Element root) throws PAException {
        List<StudyOverallStatusDTO> soDTOs = PoPaServiceBeanLookup.getStudyOverallStatusService().
            getCurrentByStudyProtocol(spDTO.getIdentifier());
        if (soDTOs == null || soDTOs.isEmpty()) {
            return;
        }
        appendElement(root, createElement("overallStatus", soDTOs.get(0).getStatusCode() , doc));
        StudyStatusCode overStatusCode = StudyStatusCode.getByCode(soDTOs.get(0).getStatusCode().getCode());

        if (StudyStatusCode.WITHDRAWN.equals(overStatusCode) 
                || StudyStatusCode.TEMPORARILY_CLOSED_TO_ACCRUAL.equals(overStatusCode)
                || StudyStatusCode.TEMPORARILY_CLOSED_TO_ACCRUAL_AND_INTERVENTION.equals(overStatusCode)) {
            
            appendElement(root, createElement("why_stopped", soDTOs.get(0).getReasonText() , doc));
        }
    }

    private static void createOverallOfficial(StudyProtocolDTO spDTO , Document doc , Element root) throws PAException {

        List<StudyContactDTO> scDTOs = PoPaServiceBeanLookup.getStudyContactService().
                        getByStudyProtocol(spDTO.getIdentifier());
        for (StudyContactDTO scDTO : scDTOs) {
            if (StudyContactRoleCode.STUDY_PRINCIPAL_INVESTIGATOR.getCode().equals(scDTO.getRoleCode().getCode())) {
                Person p  = new CorrelationUtils().getPAPersonByPAClinicalResearchStaffId(
                                Long.valueOf(scDTO.getClinicalResearchStaffIi().getExtension()));
//                PersonDTO pDto = null;
//                try {
//                    pDto = PoPaServiceBeanLookup.getPersonEntityService().
//                                getPerson(IiConverter.converToPoPersonIi(p.getIdentifier()));
//                } catch (NullifiedEntityException e) {
//                    throw new PAException("Person is nullified " + p.getFullName() + " id = " + p.getIdentifier(), e);
//                }
                
                Element overallofficial = doc.createElement("overall_official");
                appendElement(overallofficial, createElement("first_name", p.getFirstName() , doc));
                appendElement(overallofficial, createElement("last_name", p.getLastName() , doc));
                appendElement(overallofficial, createElement("role", 
                        StudyContactRoleCode.STUDY_PRINCIPAL_INVESTIGATOR.getCode() , doc));
                
                
                if (overallofficial.hasChildNodes()) {
                    appendElement(root , overallofficial);
                }
            }
        }
    }
    
    private static void createOversightInfo(StudyProtocolDTO spDTO , Document doc , Element root) throws PAException {
        Element overSightInfo  = doc.createElement("oversight_info");
        appendElement(overSightInfo , createRegulatoryAuthority(spDTO , doc));
        appendElement(overSightInfo , createIrbInfo(spDTO ,  doc));
        appendElement(overSightInfo, createElement(
                "has_dmc", convertBLToString(spDTO.getDataMonitoringCommitteeAppointedIndicator()), doc));
        appendElement(root , overSightInfo);
    }
    
    private static Element createRegulatoryAuthority(StudyProtocolDTO spDTO , Document doc) throws PAException {
        StudyRegulatoryAuthorityDTO sraDTO = PoPaServiceBeanLookup.getStudyRegulatoryAuthorityService().
            getByStudyProtocol(spDTO.getIdentifier());
        if (sraDTO == null) {
            return null;
        }
        String data = null;
        RegulatoryAuthority ra = PoPaServiceBeanLookup.getRegulatoryInformationService().
                get(Long.valueOf(sraDTO.getRegulatoryAuthorityIdentifier().getExtension()));
        
        Country country =  PoPaServiceBeanLookup.getRegulatoryInformationService().getRegulatoryAuthorityCountry(
                Long.valueOf(sraDTO.getRegulatoryAuthorityIdentifier().getExtension()));
        if (country != null && ra != null) {
            data = country.getName() + " : " + ra.getAuthorityName();
        } else if (country != null) {
            data = country.getName();
        } else if (ra != null) {
            data = ra.getAuthorityName();
        }
        return createElement("regulatory_authority" ,  data , doc);

    }
    
    private static Element createIrbInfo(StudyProtocolDTO spDTO , Document doc) throws PAException {
        Element irbInfo  = doc.createElement("irb_info");
        List<StudyParticipationDTO> spDTOs = 
            PoPaServiceBeanLookup.getStudyParticipationService().getByStudyProtocol(spDTO.getIdentifier());
        for (StudyParticipationDTO spart : spDTOs) {
            appendElement(irbInfo , createElement("approval_status" ,  spart.getReviewBoardApprovalStatusCode() , doc));
            if (ReviewBoardApprovalStatusCode.SUBMISSION_NOT_REQUIRED.getCode().equals(
                    spart.getReviewBoardApprovalStatusCode().getCode())) {
                break;
            }
            if (ReviewBoardApprovalStatusCode.SUBMITTED_APPROVED.getCode().equals(
                        spart.getReviewBoardApprovalStatusCode().getCode())
                        || ReviewBoardApprovalStatusCode.SUBMITTED_EXEMPT.getCode().equals(
                        spart.getReviewBoardApprovalStatusCode().getCode())) {
                CorrelationUtils cUtils = new CorrelationUtils();
                Organization paOrg = cUtils.getPAOrganizationByPAOversightCommitteeId(
                        IiConverter.convertToLong(spart.getOversightCommitteeIi()));     
                if (paOrg != null) {
                    OrganizationDTO poOrg = null;
                    try {
                        poOrg = PoPaServiceBeanLookup.getOrganizationEntityService().
                            getOrganization(IiConverter.converToPoOrganizationIi(paOrg.getIdentifier()));
                    } catch (NullifiedEntityException e) {
                        throw new PAException(" Po Identifier is nullified " + paOrg.getIdentifier() , e);
                    }
                    appendElement(irbInfo , createElement("name" ,  paOrg.getName() , doc));
                    Organization affOrg = cUtils.getPAOrganizationByPAHealthCareFacilityId(
                            IiConverter.convertToLong(spart.getHealthcareFacilityIi()));      
                    if (affOrg != null) {
                        appendElement(irbInfo , createElement("affiliation" ,  affOrg.getName() , doc));
                    }
                    Object[] telList = poOrg.getTelecomAddress().getItem().toArray();
                    for (Object tel : telList) {
                        if (tel instanceof TelPhone) {
                            appendElement(irbInfo , 
                                    createElement("phone" , ((TelPhone) tel).getValue().getSchemeSpecificPart(), doc));
                            break;
                        }
                    }

                    for (Object tel : telList) {
                        if (tel instanceof TelEmail) {
                            appendElement(irbInfo , createElement("email"  ,  
                                    ((TelEmail) tel).getValue().getSchemeSpecificPart(), doc));
                            break;
                        }
                    } // for
                    
                    appendElement(irbInfo , createElement("full_address" , 
                            convertToAddress(poOrg.getPostalAddress()) , doc));
                } // po org
            } // pa org
        } // for
        
        return irbInfo;
    } // method 
  
    private static void createIdInfo(StudyProtocolDTO spDTO , Document doc , Element root) throws PAException {
        //create info element
        
        Element idInfo = doc.createElement("id_info");
        appendElement(idInfo , createElement("org_study_id" , spDTO.getAssignedIdentifier().getExtension(), doc));
        
        StudyParticipationDTO spartDTO = new StudyParticipationDTO();
        spartDTO.setFunctionalCode(CdConverter.convertToCd(StudyParticipationFunctionalCode.LEAD_ORAGANIZATION));
        List<StudyParticipationDTO> sParts = PoPaServiceBeanLookup.
            getStudyParticipationService().getByStudyProtocol(spDTO.getIdentifier(), spartDTO);
        for (StudyParticipationDTO spart : sParts) {
            appendElement(idInfo , createElement("secondary_Id" , spart.getLocalStudyProtocolIdentifier() , doc));
            break;
        }
        if (idInfo.hasChildNodes()) {
            appendElement(root, idInfo);
        }
        
    }

    
    /*
    private static void createIndInfo(StudyProtocolDTO spDTO , Document doc , Element root) throws PAException {
        //create info element
        
        List<StudyIndldeDTO> ideDtos = 
            PoPaServiceBeanLookup.getStudyIndldeService().getByStudyProtocol(spDTO.getIdentifier());
        
        StudyIndldeDTO ideDTO = ideDtos.get(0);
        if (ideDtos == null || ideDtos.isEmpty()) {
            appendElement(root , createElement("is_ind_study" , NO, doc));
            return;
        }
        appendElement(root , createElement("is_ind_study" , YES, doc));
        Element idInfo = doc.createElement("id_info");
        appendElement(idInfo , createElement("ind_grantor" , ideDTO.getGrantorCode(), doc));
        appendElement(idInfo , createElement("ind_number" , ideDTO.getIndldeNumber(), doc));
        appendElement(idInfo , createElement("has_expanded_access" , 
                convertBLToString(ideDTO.getExpandedAccessIndicator()), doc));

        if (idInfo.hasChildNodes()) {
            appendElement(root, idInfo);
        }
        
    }*/
 
    private static void createEligibility(StudyProtocolDTO spDTO , Document doc , Element root) throws PAException {
        List<PlannedEligibilityCriterionDTO> paECs = 
            PoPaServiceBeanLookup.getPlannedActivityService().
                getPlannedEligibilityCriterionByStudyProtocol(spDTO.getIdentifier());
        if (paECs == null || paECs.isEmpty()) {
            return;
        }
        Element eligibility = doc.createElement("eligibility");
//        String genderCode = null;
//        String criterionName = null;
        String descriptionText = null;
        StringBuffer sb = new StringBuffer();
        for (PlannedEligibilityCriterionDTO paEC : paECs) {
//            criterionName = StConverter.convertToString(paEC.getCriterionName());
            descriptionText  = StConverter.convertToString(paEC.getTextDescription());
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
        if (eligibility.hasChildNodes()) {
            appendElement(root , eligibility);
        }
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
/*
    private static void createIntervention(StudyProtocolDTO spDTO , Document doc , Element root) throws PAException {
        // @ todo :
        Element studyDesign = doc.createElement("study_design");
        return;
    }
*/    
    
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
    
    private static void createSponsors(Ii studyProtocolIi  , Document doc , Element root) throws PAException {
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
            appendElement(root , sponsors);
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
    
    private static void createLocation(StudyProtocolDTO spDTO , Document doc , Element root) throws PAException {

        StudyParticipationDTO srDTO = new StudyParticipationDTO();
        srDTO.setFunctionalCode(CdConverter.convertToCd(StudyParticipationFunctionalCode.TREATING_SITE));
        List<StudyParticipationDTO> spList =  
            PoPaServiceBeanLookup.getStudyParticipationService().getByStudyProtocol(spDTO.getIdentifier(), srDTO);
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
            return YES;
        } else {
            return NO;
        }
    }

    private static String convertToAddress(Ad ad) {
        
        if (ad == null || ad.getPart() == null || ad.getPart().isEmpty()) {
            return null;
        }
        List<Adxp> adxpList = ad.getPart();
        StringBuffer sb = new StringBuffer();
        for (Adxp adxp : adxpList) {
            
            if (adxp instanceof AdxpAl) {
                sb.append(adxp.getValue()).append(',');
            }
            if (adxp instanceof AdxpCty) {
                sb.append(adxp.getValue()).append(',');
            }
            if (adxp instanceof AdxpSta) {
                sb.append(adxp.getValue()).append(',');
            }
            if (adxp instanceof AdxpZip) {
                sb.append(adxp.getValue()).append(',');
            }
            if (adxp instanceof AdxpCnt) {
                sb.append(adxp.getCode());
            }
        }
        if (sb.lastIndexOf(",") > 0) {
            sb.deleteCharAt(sb.lastIndexOf(","));
        }
        return sb.toString();
        
    }
    
    private static String convertTsToYYYYMMFormart(Ts isoTs) {
        String yyyyMM = "";
        Timestamp ts = TsConverter.convertToTimestamp(isoTs);
        if (ts == null) {
            return  null;
        }
        String dateStr  = PAUtil.normalizeDateString(ts.toString());
        DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault());
        DateFormat formatter1 =  new SimpleDateFormat("yyyy-MM" , Locale.getDefault());
        Date date;
        try {
            date = (Date) formatter.parse(dateStr);
            yyyyMM = formatter1.format(date);
        } catch (ParseException e) {
            yyyyMM = "";
        }
        return yyyyMM;
    }

}
