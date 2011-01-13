/**
 *
 */
package gov.nih.nci.pa.service.util;

import gov.nih.nci.iso21090.Ts;
import gov.nih.nci.pa.enums.AccrualReportingMethodCode;
import gov.nih.nci.pa.enums.ActivityCategoryCode;
import gov.nih.nci.pa.enums.ArmTypeCode;
import gov.nih.nci.pa.enums.EligibleGenderCode;
import gov.nih.nci.pa.enums.InterventionTypeCode;
import gov.nih.nci.pa.enums.RecruitmentStatusCode;
import gov.nih.nci.pa.iso.dto.ArmDTO;
import gov.nih.nci.pa.iso.dto.DiseaseDTO;
import gov.nih.nci.pa.iso.dto.InterventionDTO;
import gov.nih.nci.pa.iso.dto.InterventionalStudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.PlannedEligibilityCriterionDTO;
import gov.nih.nci.pa.iso.dto.StudyOutcomeMeasureDTO;
import gov.nih.nci.pa.iso.dto.StudySiteAccrualStatusDTO;
import gov.nih.nci.pa.iso.util.AddressConverterUtil;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.DSetConverter;
import gov.nih.nci.pa.iso.util.EnOnConverter;
import gov.nih.nci.pa.iso.util.EnPnConverter;
import gov.nih.nci.pa.iso.util.IntConverter;
import gov.nih.nci.pa.iso.util.IvlConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.services.PoDto;
import gov.nih.nci.services.organization.OrganizationDTO;
import gov.nih.nci.services.person.PersonDTO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;
import org.apache.log4j.Logger;
import org.jdom.Element;

/**
 * @author vrushali
 *
 */
public class PDQAbstractionXMLParser extends AbstractPDQXmlParser {
    private List<OrganizationDTO> collaboratorOrgDTOs;
    private InterventionalStudyProtocolDTO ispDTO;
    private OrganizationDTO irbOrgDTO;
    private List<PlannedEligibilityCriterionDTO> eligibilityList;
    private List<StudyOutcomeMeasureDTO> outcomeMeasureDTOs;
    private List<DiseaseDTO> listOfDiseaseDTOs;
    private List<InterventionDTO> listOfInterventionsDTOS;
    private Map <InterventionDTO, List<ArmDTO>> armInterventionMap;
    private List<ArmDTO> listOfArmDTOS;
    private Map<OrganizationDTO, Map<StudySiteAccrualStatusDTO, Map<PoDto, String>>> locationsMap;
    private String healthyVolunteers;
    private PAServiceUtils paServiceUtils = new PAServiceUtils();

    private static final Logger LOG  = Logger.getLogger(PDQAbstractionXMLParser.class);


    /**
     * {@inheritDoc}
     */
    @Override
    public void parse() throws PAException {
        super.parse();
        Element clinicalStudy = getDocument().getRootElement();
        readOutcomes(clinicalStudy);
        readConditons(clinicalStudy);
        readIrbInfo(clinicalStudy.getChild("oversight_info"));
        readEligibility(clinicalStudy.getChild("eligibility"));
        readStudyDesign(clinicalStudy);
        readInterventions(clinicalStudy);
        readArmGroups(clinicalStudy);
        readLocations(clinicalStudy);
        Element sponsorElmt = clinicalStudy.getChild("sponsors");
        setCollaboratorOrgDTOs(new ArrayList<OrganizationDTO>());
        List<Element> collaboratorElmtList = sponsorElmt.getChildren("collaborator");
        for (Element collaboratorElmt : collaboratorElmtList) {
            OrganizationDTO collaboratorDTO = new OrganizationDTO();
            collaboratorDTO.setName(EnOnConverter.convertToEnOn(getText(collaboratorElmt, "agency")));
            getCollaboratorOrgDTOs().add(collaboratorDTO);
        }

    }
    /**
     * @param parent
     */
    private void readLocations(Element parent) {
        Ts recrutingStatusDate = null;
        Element leadOrgStatusElmt = parent.getChild("lead_org_status");
        if (leadOrgStatusElmt != null && CollectionUtils.isNotEmpty(leadOrgStatusElmt.getContent())) {
            recrutingStatusDate = tsFromString("yyyy-MM-dd", leadOrgStatusElmt.getAttributeValue("status_date"));
        }
        List<Element> locationElmtList = parent.getChildren("location");
        for (Element locationElmt : locationElmtList) {
            setLocationsMap(new HashMap<OrganizationDTO, Map<StudySiteAccrualStatusDTO, Map<PoDto, String>>>());
            OrganizationDTO orgDTO = new OrganizationDTO();
            //read Facility/Org
            Element facitlityElmt = locationElmt.getChild("facility");
            String ctepId = facitlityElmt.getAttributeValue("ctep-id");
            if (StringUtils.isNotEmpty(ctepId)) {
                orgDTO.setIdentifier((paServiceUtils.getOrganizationByCtepId(ctepId)).getIdentifier());
            }
            orgDTO.setName(EnOnConverter.convertToEnOn(getText(facitlityElmt, "name")));
            Element addressElmt = facitlityElmt.getChild("address");
            orgDTO.setPostalAddress(AddressConverterUtil.create(null, null, getText(addressElmt, "city"),
                    getText(addressElmt, "state"), getText(addressElmt, "zip"),
                    getAlpha3CountryName(getText(addressElmt, "country"))));
            //read Status
            StudySiteAccrualStatusDTO siteStatus = new StudySiteAccrualStatusDTO();
            siteStatus.setStatusCode(CdConverter.convertToCd(RecruitmentStatusCode.getByCode(getText(locationElmt,
                    "status"))));
            siteStatus.setStatusDate(recrutingStatusDate);
            //read contact
            Map<StudySiteAccrualStatusDTO, Map<PoDto, String>> contactMap = new HashMap<StudySiteAccrualStatusDTO,
                Map<PoDto, String>>();
            contactMap.put(siteStatus, readContact(locationElmt));
            getLocationsMap().put(orgDTO, contactMap);
        }
    }
    /**
     * @param locationElmt
     * @param siteStatus
     * @return
     */
    private Map<PoDto, String> readContact(Element locationElmt) {
        Element contactElmt = locationElmt.getChild("contact");
        Map<PoDto, String> contactMap = new HashMap<PoDto, String>();
        if (contactElmt.getChild("first_name") != null) {
            PersonDTO contactDTO = new PersonDTO();
            contactDTO.setName(EnPnConverter.convertToEnPn(getText(contactElmt, "first_name"), null,
                    getText(contactElmt, "last_name"), null, null));
            List<String> phoneList = new ArrayList<String>();
            phoneList.add(getText(contactElmt, "phone"));
            contactDTO.setTelecomAddress(DSetConverter.convertListToDSet(phoneList, "PHONE", null));
            contactMap.put(contactDTO, StringUtils.equalsIgnoreCase(contactElmt.getAttributeValue("role"),
                    "Principal investigator")? "PI" : "");
        }

      if (contactElmt.getChild("last_name") != null) {
          LOG.info("No Generic Contact created for last_name: " + contactElmt.getChild("last_name"));
      }
        return contactMap;
    }

    /**
     * @param parent
     */
    private void readArmGroups(Element parent) {
        setListOfArmDTOS(new ArrayList<ArmDTO>());
        List<Element> armElmtList = parent.getChildren("arm_group");
        for (Element armElmt : armElmtList) {
            ArmDTO armDTO = new ArmDTO();
            armDTO.setName(StConverter.convertToSt(getText(armElmt, "arm_group_label")));
            armDTO.setTypeCode(CdConverter.convertToCd(ArmTypeCode.getByCode(WordUtils.capitalizeFully(
                    getText(armElmt, "arm_type")))));
            armDTO.setDescriptionText(StConverter.convertToSt(getFullText(
                  armElmt.getChild("arm_group_description"), "", "")));
            getListOfArmDTOS().add(armDTO);
        }
    }
    /**
     * @param parent
     */
    private void readInterventions(Element parent) {
        setListOfInterventionsDTOS(new ArrayList<InterventionDTO>());
        List<Element> interventionElmtList = parent.getChildren("intervention");
        setArmInterventionMap(new HashMap<InterventionDTO, List<ArmDTO>>());
        for (Element interventionElmt : interventionElmtList) {
            InterventionDTO interventionDto = new InterventionDTO();
            interventionDto.setPdqTermIdentifier(StConverter.convertToSt(interventionElmt.getAttributeValue("cdr-id")));
            interventionDto.setName(StConverter.convertToSt(getText(interventionElmt, "intervention_name")));
            interventionDto.setTypeCode(CdConverter.convertToCd(InterventionTypeCode.getByCode(getText(interventionElmt,
                    "intervention_type"))));
            interventionDto.setDescriptionText(StConverter.convertToSt(getFullText(interventionElmt.getChild(
                    "intervention_description"), "" , "")));
            listOfInterventionsDTOS.add(interventionDto);
            List<Element> armGrpLableElmtList = interventionElmt.getChildren("arm_group_label");
            List<ArmDTO> armList = new ArrayList<ArmDTO>();
            for (Element armGrpElmt : armGrpLableElmtList) {
                ArmDTO armDTO = new ArmDTO();
                armDTO.setName(StConverter.convertToSt(armGrpElmt.getText()));
                armList.add(armDTO);
            }
            getArmInterventionMap().put(interventionDto, armList);
        }

    }
    /**
     * @param clinicalStudy
     */
    private void readOutcomes(Element parent) {
        Element primaryOutcome =  parent.getChild("primary_outcome");
        setOutcomeMeasureDTOs(new ArrayList<StudyOutcomeMeasureDTO>());
        getOutcomeMeasure(primaryOutcome, Boolean.TRUE);
        List<Element> secondaryOutcomeList = parent.getChildren("secondary_outcome");
        for (Element secElt : secondaryOutcomeList) {
            getOutcomeMeasure(secElt, Boolean.FALSE);
        }

    }

    /**
     * @param outcomeNode
     */
    private void getOutcomeMeasure(Element outcomeNode, Boolean primaryIndicator) {
        if (outcomeNode == null) {
            return;
        }
        StudyOutcomeMeasureDTO outcomeMeasure = new StudyOutcomeMeasureDTO();
        outcomeMeasure.setName(StConverter.convertToSt(getText(outcomeNode, "outcome_measure")));
        outcomeMeasure.setSafetyIndicator(BlConverter.convertToBl(BooleanUtils.toBoolean(getText(outcomeNode,
                "outcome_safety_issue"))));
        outcomeMeasure.setPrimaryIndicator(BlConverter.convertToBl(primaryIndicator));
        getOutcomeMeasureDTOs().add(outcomeMeasure);
    }
    /**
     * @param parent
     */
    private void readConditons(Element parent) {
        List<Element> conditionList = parent.getChildren("condition");
        setListOfDiseaseDTOs(new ArrayList<DiseaseDTO>());
        for (Element conditionElt : conditionList) {
            DiseaseDTO dDto = new DiseaseDTO();
            dDto.setDiseaseCode(StConverter.convertToSt(conditionElt.getAttribute("cdr-id").getValue()));
            dDto.setPreferredName(StConverter.convertToSt(conditionElt.getText()));
            getListOfDiseaseDTOs().add(dDto);
        }

    }

    /**
     * @param parent
     */
    private void readIrbInfo(Element parent) {
        if (parent != null) {
            Element irbInfoElement = parent.getChild("irb_info");
            String orgName = getText(irbInfoElement, "name");
            String phone = getText(irbInfoElement, "phone");
            String email = getText(irbInfoElement, "email");
            getText(irbInfoElement, "full_address");
            if (StringUtils.isNotEmpty(orgName)) {
                setIrbOrgDTO(new OrganizationDTO());
                getIrbOrgDTO().setName(EnOnConverter.convertToEnOn(orgName));
                getIrbOrgDTO().setTelecomAddress(getDset(email, phone));
            }
        }
    }

    /**
     * @param parent
     */
    private void readEligibility(Element parent) {
        setHealthyVolunteers(parent.getChildText("healthy_volunteers"));
        PlannedEligibilityCriterionDTO pEligibiltyCriterionDTO;
        setEligibilityList(new ArrayList<PlannedEligibilityCriterionDTO>());
        String eligibleGenderCode = getText(parent, "gender");
        if (StringUtils.isNotEmpty(eligibleGenderCode)) {
            pEligibiltyCriterionDTO = new PlannedEligibilityCriterionDTO();
            pEligibiltyCriterionDTO.setCriterionName(StConverter.convertToSt("GENDER"));
            pEligibiltyCriterionDTO.setEligibleGenderCode(CdConverter.convertToCd(EligibleGenderCode
                .getByCode(eligibleGenderCode)));
            pEligibiltyCriterionDTO.setCategoryCode(CdConverter.convertToCd(
                    ActivityCategoryCode.ELIGIBILITY_CRITERION));
            pEligibiltyCriterionDTO.setInclusionIndicator(BlConverter.convertToBl(Boolean.TRUE));
            eligibilityList.add(pEligibiltyCriterionDTO);
        }
        String minimumValue = getText(parent, "minimum_age");
        String maximumValue = getText(parent, "maximum_age");
        if (minimumValue != null || maximumValue != null) {
            pEligibiltyCriterionDTO = new PlannedEligibilityCriterionDTO();
            pEligibiltyCriterionDTO.setCriterionName(StConverter.convertToSt("AGE"));
            pEligibiltyCriterionDTO.setValue(convertToIvlPq("Years", minimumValue, "Years", maximumValue));
            pEligibiltyCriterionDTO.setCategoryCode(CdConverter.convertToCd(
                    ActivityCategoryCode.ELIGIBILITY_CRITERION));
            pEligibiltyCriterionDTO.setInclusionIndicator(BlConverter.convertToBl(Boolean.TRUE));
            eligibilityList.add(pEligibiltyCriterionDTO);
        }

    }
    private void readStudyDesign(Element parent) {
        setIspDTO(new InterventionalStudyProtocolDTO());
        ispDTO.setPublicTitle(StConverter.convertToSt(getText(parent, "brief_title")));
        ispDTO.setPublicDescription(StConverter.convertToSt(getFullText(parent.getChild("brief_summary"), "" , "")));
        ispDTO.setScientificDescription(StConverter.convertToSt(
                getFullText(parent.getChild("detailed_description"), "", "")));
        ispDTO.setRecordVerificationDate(tsFromString("yyyy-MM-dd", parent.getChildText("verification_date")));
        ispDTO.setTargetAccrualNumber(IvlConverter.convertInt().convertToIvl(getText(parent, "enrollment"), null));
        List<Element> keywordList = parent.getChildren("keyword");
        StringBuffer keyWord = new StringBuffer();
        for (Element keywordElt : keywordList) {
            keyWord.append(keywordElt.getText()).append('\n');
        }
        ispDTO.setKeywordText(StConverter.convertToSt(keyWord.toString()));

        Element studyDesignElmt = parent.getChild("study_design");
        if (studyDesignElmt == null) {
            return;
        }
        Element interventionalElement = studyDesignElmt.getChild("interventional_design");
        if (interventionalElement == null) {
            return;
        }
        ispDTO.setPrimaryPurposeCode(CdConverter.convertStringToCd(getText(interventionalElement,
                "interventional_subtype")));
        ispDTO.setAllocationCode(CdConverter.convertStringToCd(getText(interventionalElement, "allocation")));
        ispDTO.setBlindingSchemaCode(CdConverter.convertStringToCd(getText(interventionalElement, "masking")));
        ispDTO.setDesignConfigurationCode(CdConverter.convertStringToCd(getText(interventionalElement, "assignment")));
        ispDTO.setStudyClassificationCode(CdConverter.convertStringToCd(getText(interventionalElement, "endpoint")));
        ispDTO.setNumberOfInterventionGroups(IntConverter.convertToInt(getText(interventionalElement,
                "number_of_arms")));
        ispDTO.setAccrualReportingMethodCode(CdConverter.convertToCd(AccrualReportingMethodCode.ABBREVIATED));
    }
    /**
     * @param ispDTO the ispDTO to set
     */
    public void setIspDTO(InterventionalStudyProtocolDTO ispDTO) {
        this.ispDTO = ispDTO;
    }

    /**
     * @return the ispDTO
     */
    public InterventionalStudyProtocolDTO getIspDTO() {
        return ispDTO;
    }

    /**
     * @return the outcomeMeasureDTO
     */
    public List<StudyOutcomeMeasureDTO> getOutcomeMeasureDTOs() {
        return outcomeMeasureDTOs;
    }

    /**
     * @return the irbOrgDTO
     */
    public OrganizationDTO getIrbOrgDTO() {
        return irbOrgDTO;
    }

    /**
     * @return the collaboratorOrgDTOs
     */
    public List<OrganizationDTO> getCollaboratorOrgDTOs() {
        return collaboratorOrgDTOs;
    }

    /**
     * @return the listOfDiseaseDTOs
     */
    public List<DiseaseDTO> getListOfDiseaseDTOs() {
        return listOfDiseaseDTOs;
    }

    /**
     * @return the listOfInterventionsDTOS
     */
    public List<InterventionDTO> getListOfInterventionsDTOS() {
        return listOfInterventionsDTOS;
    }
    /**
     * @param listOfInterventionsDTOS the listOfInterventionsDTOS to set
     */
    public void setListOfInterventionsDTOS(
            List<InterventionDTO> listOfInterventionsDTOS) {
        this.listOfInterventionsDTOS = listOfInterventionsDTOS;
    }
    /**
     * @return the armInterventionMap
     */
    public Map <InterventionDTO, List<ArmDTO>> getArmInterventionMap() {
        return armInterventionMap;
    }
    /**
     * @return the listOfArmDTOS
     */
    public List<ArmDTO> getListOfArmDTOS() {
        return listOfArmDTOS;
    }
    /**
     * @param eligibilityList the eligibilityList to set
     */
    public void setEligibilityList(List<PlannedEligibilityCriterionDTO> eligibilityList) {
        this.eligibilityList = eligibilityList;
    }
    /**
     * @return the eligibilityList
     */
    public List<PlannedEligibilityCriterionDTO> getEligibilityList() {
        return eligibilityList;
    }
    /**
     * @param collaboratorOrgDTOs the collaboratorOrgDTOs to set
     */
    public void setCollaboratorOrgDTOs(List<OrganizationDTO> collaboratorOrgDTOs) {
        this.collaboratorOrgDTOs = collaboratorOrgDTOs;
    }
    /**
     * @param irbOrgDTO the irbOrgDTO to set
     */
    public void setIrbOrgDTO(OrganizationDTO irbOrgDTO) {
        this.irbOrgDTO = irbOrgDTO;
    }
    /**
     * @param outcomeMeasureDTOs the outcomeMeasureDTOs to set
     */
    public void setOutcomeMeasureDTOs(List<StudyOutcomeMeasureDTO> outcomeMeasureDTOs) {
        this.outcomeMeasureDTOs = outcomeMeasureDTOs;
    }
    /**
     * @param listOfDiseaseDTOs the listOfDiseaseDTOs to set
     */
    public void setListOfDiseaseDTOs(List<DiseaseDTO> listOfDiseaseDTOs) {
        this.listOfDiseaseDTOs = listOfDiseaseDTOs;
    }
    /**
     * @param armInterventionMap the armInterventionMap to set
     */
    public void setArmInterventionMap(Map <InterventionDTO, List<ArmDTO>> armInterventionMap) {
        this.armInterventionMap = armInterventionMap;
    }
    /**
     * @param listOfArmDTOS the listOfArmDTOS to set
     */
    public void setListOfArmDTOS(List<ArmDTO> listOfArmDTOS) {
        this.listOfArmDTOS = listOfArmDTOS;
    }
    /**
     * @param healthyVolunteers the healthyVolunteers to set
     */
    public void setHealthyVolunteers(String healthyVolunteers) {
        this.healthyVolunteers = healthyVolunteers;
    }
    /**
     * @return the healthyVolunteers
     */
    public String getHealthyVolunteers() {
        return healthyVolunteers;
    }
    /**
     * @param paServiceUtils the paServiceUtils to set
     */
    @Override
    public void setPaServiceUtils(PAServiceUtils paServiceUtils) {
        this.paServiceUtils = paServiceUtils;
    }
    /**
     * @return the paServiceUtils
     */
    @Override
    public PAServiceUtils getPaServiceUtils() {
        return paServiceUtils;
    }
    /**
     * @param locationsMap the locationsMap to set
     */
    public void setLocationsMap(Map<OrganizationDTO, Map<StudySiteAccrualStatusDTO, Map<PoDto, String>>> locationsMap) {
        this.locationsMap = locationsMap;
    }
    /**
     * @return the locationsMap
     */
    public Map<OrganizationDTO, Map<StudySiteAccrualStatusDTO, Map<PoDto, String>>> getLocationsMap() {
        return locationsMap;
    }
}
