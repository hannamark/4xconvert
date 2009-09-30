package gov.nih.nci.registry.util;

import gov.nih.nci.coppa.iso.Cd;
import gov.nih.nci.coppa.iso.DSet;
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.coppa.iso.Tel;
import gov.nih.nci.pa.domain.Organization;
import gov.nih.nci.pa.domain.Person;
import gov.nih.nci.pa.dto.PAContactDTO;
import gov.nih.nci.pa.dto.PaOrganizationDTO;
import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;
import gov.nih.nci.pa.enums.ActualAnticipatedTypeCode;
import gov.nih.nci.pa.enums.DocumentTypeCode;
import gov.nih.nci.pa.enums.NciDivisionProgramCode;
import gov.nih.nci.pa.enums.PhaseCode;
import gov.nih.nci.pa.enums.PrimaryPurposeCode;
import gov.nih.nci.pa.enums.StudyContactRoleCode;
import gov.nih.nci.pa.enums.StudySiteContactRoleCode;
import gov.nih.nci.pa.enums.StudySiteFunctionalCode;
import gov.nih.nci.pa.enums.StudyStatusCode;
import gov.nih.nci.pa.iso.dto.DocumentDTO;
import gov.nih.nci.pa.iso.dto.InterventionalStudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.StudyContactDTO;
import gov.nih.nci.pa.iso.dto.StudyIndldeDTO;
import gov.nih.nci.pa.iso.dto.StudyOverallStatusDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.StudyRegulatoryAuthorityDTO;
import gov.nih.nci.pa.iso.dto.StudyResourcingDTO;
import gov.nih.nci.pa.iso.dto.StudySiteAccrualStatusDTO;
import gov.nih.nci.pa.iso.dto.StudySiteContactDTO;
import gov.nih.nci.pa.iso.dto.StudySiteDTO;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.DSetConverter;
import gov.nih.nci.pa.iso.util.EdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.IntConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.correlation.CorrelationUtils;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.registry.dto.RegulatoryAuthorityWebDTO;
import gov.nih.nci.registry.dto.StudyIndldeWebDTO;
import gov.nih.nci.registry.dto.TrialDTO;
import gov.nih.nci.registry.dto.TrialDocumentWebDTO;
import gov.nih.nci.registry.dto.TrialFundingWebDTO;
import gov.nih.nci.registry.dto.TrialIndIdeDTO;
import gov.nih.nci.services.correlation.NullifiedRoleException;
import gov.nih.nci.services.organization.OrganizationDTO;
import gov.nih.nci.services.person.PersonDTO;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

/**
 * The Class TrialUtil.
 * 
 * @author vrushali
 */
@SuppressWarnings({"PMD.CyclomaticComplexity", "PMD.TooManyMethods", "PMD.ExcessiveClassLength" })
public class TrialUtil {
    
    /** The Constant SPONSOR. */
    private static final String SPONSOR = "sponsor";
    private static final String YES = "Yes";
    
    /** The Constant MAXF. */
    private static final int MAXF = 1024;
    
    /**
     * Default constructor.
     */
    public TrialUtil() {
        super();
    }

    /**
     * Copy.
     * 
     * @param spDTO sdto
     * @param trialDTO gdto
     */
    private void copy(StudyProtocolDTO spDTO, TrialDTO trialDTO) {
        trialDTO.setOfficialTitle(spDTO.getOfficialTitle().getValue());
        trialDTO.setAssignedIdentifier(spDTO.getAssignedIdentifier().getExtension());
        trialDTO.setPhaseCode(spDTO.getPhaseCode().getCode());
        trialDTO.setPhaseOtherText(spDTO.getPhaseOtherText().getValue());
        trialDTO.setPrimaryPurposeCode(spDTO.getPrimaryPurposeCode().getCode());
        trialDTO.setPrimaryPurposeOtherText(spDTO.getPrimaryPurposeOtherText().getValue());
        trialDTO.setStartDate(PAUtil.normalizeDateString(TsConverter.convertToTimestamp(spDTO.getStartDate()).
                toString()));
        trialDTO.setStartDateType(spDTO.getStartDateTypeCode().getCode());
        trialDTO.setCompletionDate(PAUtil.normalizeDateString(TsConverter.
                convertToTimestamp(spDTO.getPrimaryCompletionDate()).toString()));
        trialDTO.setCompletionDateType(spDTO.getPrimaryCompletionDateTypeCode().getCode());
        trialDTO.setTrialType(spDTO.getStudyProtocolType().getValue());
        trialDTO.setIdentifier(spDTO.getIdentifier().getExtension());
        trialDTO.setProgramCodeText(StConverter.convertToString(spDTO.getProgramCodeText()));
        trialDTO.setSubmissionNumber(IntConverter.convertToString(spDTO.getSubmissionNumber()));
    }

    /**
     * Copy.
     * 
     * @param spqDTO sdto
     * @param trialDTO gdto
     */
    private void copy(StudyProtocolQueryDTO spqDTO, TrialDTO trialDTO) {
        trialDTO.setStudyProtocolId(spqDTO.getStudyProtocolId().toString());
        trialDTO.setLocalProtocolIdentifier(spqDTO.getLocalStudyProtocolIdentifier());
        trialDTO.setStatusCode(spqDTO.getStudyStatusCode().getCode());
        trialDTO.setStatusDate(PAUtil.normalizeDateString(spqDTO.getStudyStatusDate().toString()));
    }
    
    /**
     * Copy lo.
     * 
     * @param o o
     * @param trialDTO gdto
     */
    private void copyLO(Organization o, TrialDTO trialDTO) {
        trialDTO.setLeadOrganizationIdentifier(o.getIdentifier());
        trialDTO.setLeadOrganizationName(o.getName());
    }
    
    /**
     * Copy pi.
     * 
     * @param p p
     * @param trialDTO dto
     */
    private void copyPI(Person p, TrialDTO trialDTO) {
        trialDTO.setPiIdentifier(p.getIdentifier());
        trialDTO.setPiName(p.getFullName());
    }
    
    /**
     * Copy responsible party.
     * 
     * @param studyProtocolIi ii
     * @param trialDTO dto
     * 
     * @throws PAException ex
     * @throws NullifiedRoleException the nullified role exception
     */
    private void copyResponsibleParty(Ii studyProtocolIi, TrialDTO trialDTO) throws PAException,
        NullifiedRoleException  {
        StudyContactDTO scDto = new StudyContactDTO();
        scDto.setRoleCode(CdConverter.convertToCd(StudyContactRoleCode.RESPONSIBLE_PARTY_STUDY_PRINCIPAL_INVESTIGATOR));
        List<StudyContactDTO> scDtos =  RegistryServiceLocator.getStudyContactService()
            .getByStudyProtocol(studyProtocolIi, scDto);
        DSet dset = null;
        if (scDtos != null && !scDtos.isEmpty()) {
            trialDTO.setResponsiblePartyType("pi");
            scDto = scDtos.get(0);
            dset = scDto.getTelecomAddresses();
        } else {
            StudySiteContactDTO spart = new StudySiteContactDTO();
            spart.setRoleCode(CdConverter.convertToCd(
                    StudySiteContactRoleCode.RESPONSIBLE_PARTY_SPONSOR_CONTACT));
            List<StudySiteContactDTO> spDtos = RegistryServiceLocator.getStudySiteContactService()
                .getByStudyProtocol(studyProtocolIi, spart);
            trialDTO.setResponsiblePartyType(SPONSOR);
            if (spDtos != null && !spDtos.isEmpty()) {
                trialDTO.setResponsiblePartyType(SPONSOR);
                spart = spDtos.get(0);
                dset = spart.getTelecomAddresses();
                CorrelationUtils cUtils = new CorrelationUtils();
                PAContactDTO paDto = cUtils.getContactByPAOrganizationalContactId((
                        Long.valueOf(spart.getOrganizationalContactIi().getExtension())));
                if (paDto.getFullName() != null) {
                    trialDTO.setResponsiblePersonName(paDto.getFullName());
                    trialDTO.setResponsiblePersonIdentifier(PAUtil.getIiExtension(paDto.getPersonIdentifier()));
                } 
                if (paDto.getTitle() != null)  {
                   trialDTO.setResponsibleGenericContactName(paDto.getTitle());
                   trialDTO.setResponsiblePersonIdentifier(PAUtil.getIiExtension(paDto.getSrIdentifier()));
                }
            }
        }
        copy(dset, trialDTO);
    }

/**
 * Copy.
 * 
 * @param dset set
 * @param trialDTO dto
 */
    private void copy(DSet dset, TrialDTO trialDTO) {
        if (dset == null) {
            return;
        }
        List<String> phones = DSetConverter.convertDSetToList(dset, "PHONE");
        List<String> emails = DSetConverter.convertDSetToList(dset, "EMAIL");
        if (phones != null && !phones.isEmpty()) {
            trialDTO.setContactPhone(phones.get(0));
        }
        if (emails != null && !emails.isEmpty()) {
            trialDTO.setContactEmail(emails.get(0));
        }
    }
    
    /**
     * Copy sponsor.
     * 
     * @param studyProtocolIi ii
     * @param trialDTO dto
     * 
     * @throws PAException ex
     */
    private void copySponsor(Ii studyProtocolIi, TrialDTO trialDTO) throws PAException {
        StudySiteDTO spart = new StudySiteDTO();
        spart.setFunctionalCode(CdConverter.convertToCd(StudySiteFunctionalCode.SPONSOR));
        List<StudySiteDTO> spDtos = RegistryServiceLocator.getStudySiteService()
            .getByStudyProtocol(studyProtocolIi, spart);
        if (spDtos != null && !spDtos.isEmpty()) {
            spart = spDtos.get(0);
            Organization o = new CorrelationUtils().getPAOrganizationByIi(spart.getResearchOrganizationIi());
            trialDTO.setSponsorName(o.getName());
            trialDTO.setSponsorIdentifier(o.getIdentifier());
        }

    }
    
    /**
     * Copy nct nummber.
     * 
     * @param studyProtocolIi ii
     * @param trialDTO dto
     * 
     * @throws PAException ex
     */
    private void copyNctNummber(Ii studyProtocolIi, TrialDTO trialDTO) throws PAException {
        StudySiteDTO spDto = getStudySite(studyProtocolIi,
                    StudySiteFunctionalCode.IDENTIFIER_ASSIGNER);
        if (spDto != null) {
            trialDTO.setNctIdentifier(StConverter.convertToString(spDto.getLocalStudyProtocolIdentifier()));
        }
    }

    /**
     * Gets the study site.
     * 
     * @param studyProtocolIi ii
     * @param spCode code
     * 
     * @return dto
     * 
     * @throws PAException ex
     */
    public StudySiteDTO getStudySite(Ii studyProtocolIi , StudySiteFunctionalCode spCode)
    throws PAException {
        if (studyProtocolIi == null) {
            throw new PAException(" StudyProtocol Ii is null");
        }
        StudySiteDTO spDto = new StudySiteDTO();
        Cd cd = CdConverter.convertToCd(spCode);
        spDto.setFunctionalCode(cd);

        List<StudySiteDTO> spDtos = RegistryServiceLocator.getStudySiteService()
        .getByStudyProtocol(studyProtocolIi, spDto);
        if (spDtos != null && spDtos.size() == 1) {
            return spDtos.get(0);
        } else if (spDtos != null && spDtos.size() > 1) {
            throw new PAException(" Found more than 1 record for a protocol id = "
                    + studyProtocolIi.getExtension() + " for a given status " + cd.getCode());

        }
        return null;

    }
    
    /**
     * Copy summary four.
     * 
     * @param srDTO sdto
     * @param trialDTO tdto
     * 
     * @throws PAException ex
     */
    private void copySummaryFour(StudyResourcingDTO srDTO, TrialDTO trialDTO) throws PAException {
        if (srDTO == null) {
            return;
        }
        if (srDTO.getTypeCode() != null) {
            trialDTO.setSummaryFourFundingCategoryCode(srDTO.getTypeCode().getCode());
        }
        if (srDTO.getOrganizationIdentifier() != null
                && PAUtil.isNotEmpty(srDTO.getOrganizationIdentifier().getExtension())) {
            CorrelationUtils cUtils = new CorrelationUtils();
            Organization o = cUtils.getPAOrganizationByIi(srDTO.getOrganizationIdentifier());
            trialDTO.setSummaryFourOrgIdentifier(o.getIdentifier());
            trialDTO.setSummaryFourOrgName(o.getName());
        }
    }
    
    /**
     * Copy indide list.
     * 
     * @param studyIndldeDTOList iiDto
     * @param trialDTO dto
     * 
     * @throws PAException ex
     */
    private void copyINDIDEList(List<StudyIndldeDTO> studyIndldeDTOList, TrialDTO trialDTO) throws PAException {
        if (studyIndldeDTOList == null) {
            return;
        }
        List<TrialIndIdeDTO> indList = new ArrayList<TrialIndIdeDTO>();
        //loop thru the iso dto
        for (StudyIndldeDTO isoDto : studyIndldeDTOList) {
            indList.add(new TrialIndIdeDTO(isoDto));
        }
        trialDTO.setIndIdeDtos(indList);
    }
    
    /**
     * Copy indide update list.
     * 
     * @param studyIndldeDTOList iiDto
     * @param trialDTO dto
     * 
     * @throws PAException ex
     */
    private void copyINDIDEUpdateList(List<StudyIndldeDTO> studyIndldeDTOList, TrialDTO trialDTO) throws PAException {
        if (studyIndldeDTOList == null) {
            return;
        }
        List<StudyIndldeWebDTO> indList = new ArrayList<StudyIndldeWebDTO>();
        //loop thru the iso dto
        for (StudyIndldeDTO isoDto : studyIndldeDTOList) {
            indList.add(new StudyIndldeWebDTO(isoDto));
        }
        trialDTO.setIndIdeUpdateDtos(indList);
    }
    
    /**
     * Copy grant list.
     * 
     * @param isoGrantlist iso
     * @param trialDTO dto
     */
    private void copyGrantList(List<StudyResourcingDTO> isoGrantlist, TrialDTO trialDTO) {
        if (isoGrantlist == null) {
            return;
        }
        List<TrialFundingWebDTO> grantList = new ArrayList<TrialFundingWebDTO>(); 
        //loop thru iso
        TrialFundingWebDTO webDto = null;
        for (StudyResourcingDTO isoDto : isoGrantlist) {
            webDto = new TrialFundingWebDTO(isoDto);
            webDto.setRowId(UUID.randomUUID().toString());
            grantList.add(webDto);
        }
        trialDTO.setFundingDtos(grantList);
    }
    
    /**
     * Convert to study protocol dto for amendment.
     * 
     * @param trialDTO dtotoConvert
     * 
     * @return isoDto
     * 
     * @throws PAException on error
     */
    public StudyProtocolDTO convertToStudyProtocolDTOForAmendment(TrialDTO trialDTO) throws PAException {
        StudyProtocolDTO isoDto = null;
        
        if (trialDTO.getTrialType().equalsIgnoreCase("Observational")) {
            isoDto = RegistryServiceLocator.getStudyProtocolService().getObservationalStudyProtocol(
                        IiConverter.convertToStudyProtocolIi(Long.parseLong(trialDTO.getIdentifier())));
        } else {
            isoDto  = RegistryServiceLocator.getStudyProtocolService().getInterventionalStudyProtocol(
                    IiConverter.convertToStudyProtocolIi(Long.parseLong(trialDTO.getIdentifier())));
        }
        isoDto = convertToStudyProtocolDTO(trialDTO, isoDto);
        isoDto.setAssignedIdentifier(IiConverter.convertToIi(trialDTO.getAssignedIdentifier()));
        isoDto.setAmendmentDate(TsConverter.convertToTs(PAUtil.dateStringToTimestamp(trialDTO.getAmendmentDate())));
        isoDto.setAmendmentNumber(StConverter.convertToSt(trialDTO.getLocalAmendmentNumber()));
        return isoDto;
    }

    /**
     * Convert to study protocol dto.
     * 
     * @param trialDTO the trial dto
     * @param isoDto the iso dto
     * 
     * @return the study protocol dto
     */
    private StudyProtocolDTO convertToStudyProtocolDTO(TrialDTO trialDTO,
            StudyProtocolDTO isoDto) {
        if (PAUtil.isNotEmpty(trialDTO.getOfficialTitle())) {
            isoDto.setOfficialTitle(StConverter.convertToSt(trialDTO.getOfficialTitle()));
        } 
        isoDto.setPhaseCode(CdConverter.convertToCd(PhaseCode.getByCode(trialDTO.getPhaseCode())));
        if (PAUtil.isNotEmpty(trialDTO.getPhaseOtherText())) {
            isoDto.setPhaseOtherText(StConverter.convertToSt(trialDTO.getPhaseOtherText()));
        }
        isoDto.setPrimaryPurposeCode(CdConverter.convertToCd(
                PrimaryPurposeCode.getByCode(trialDTO.getPrimaryPurposeCode())));
        
        if (PAUtil.isNotEmpty(trialDTO.getPrimaryPurposeOtherText())) {
            isoDto.setPrimaryPurposeOtherText(
                    StConverter.convertToSt(trialDTO.getPrimaryPurposeOtherText()));
        }
        isoDto.setStartDate(TsConverter.convertToTs(PAUtil.dateStringToTimestamp(trialDTO.getStartDate())));
        isoDto.setStartDateTypeCode(CdConverter.convertToCd(ActualAnticipatedTypeCode.getByCode(trialDTO
                .getStartDateType())));
        isoDto.setPrimaryCompletionDate(TsConverter.convertToTs(PAUtil.dateStringToTimestamp(trialDTO
                .getCompletionDate())));
        isoDto.setPrimaryCompletionDateTypeCode(CdConverter.convertToCd(ActualAnticipatedTypeCode
                .getByCode(trialDTO.getCompletionDateType())));
        isoDto.setStudyProtocolType(StConverter.convertToSt(trialDTO.getTrialType()));
        isoDto.setProgramCodeText(StConverter.convertToSt(trialDTO.getProgramCodeText()));
        boolean fdaRegIndicator = false;
        if (trialDTO.getFdaRegulatoryInformationIndicator() != null 
                && (trialDTO.getFdaRegulatoryInformationIndicator().equalsIgnoreCase(YES)
                    || trialDTO.getFdaRegulatoryInformationIndicator().equalsIgnoreCase("true"))) {
            fdaRegIndicator = true;
        }
        isoDto.setFdaRegulatedIndicator(BlConverter.convertToBl(fdaRegIndicator));
        boolean section801Indicator = false;
        if (trialDTO.getSection801Indicator() != null 
                && (trialDTO.getSection801Indicator().equalsIgnoreCase(YES)
                    || trialDTO.getSection801Indicator().equalsIgnoreCase("true"))) {
            section801Indicator = true;
        }
        isoDto.setSection801Indicator(BlConverter.convertToBl(section801Indicator));
        isoDto.setProprietaryTrialIndicator(BlConverter.convertToBl(Boolean.FALSE));
        return isoDto;
    }
    
    /**
     * Convert to study overall status dto.
     * 
     * @param trialDTO Dto
     * 
     * @return isoDto
     */
    public StudyOverallStatusDTO convertToStudyOverallStatusDTO(TrialDTO trialDTO) {
        StudyOverallStatusDTO isoDto = new StudyOverallStatusDTO();
        isoDto.setStatusCode(CdConverter.convertToCd(StudyStatusCode.getByCode(trialDTO
                .getStatusCode())));
        isoDto.setReasonText(StConverter.convertToSt(trialDTO.getReason()));
        isoDto.setStatusDate(TsConverter.convertToTs(PAUtil.dateStringToTimestamp(trialDTO
                .getStatusDate())));
        return isoDto;
    }
    
    /**
     * Convert to study overall status dto.
     * 
     * @param isoDto StudyOverallStatusDTO
     * @param trialDTO Dto
     */
    public void convertToStudyOverallStatusDTO(TrialDTO trialDTO, StudyOverallStatusDTO isoDto) {
        isoDto.setStatusCode(CdConverter.convertToCd(StudyStatusCode.getByCode(trialDTO
                .getStatusCode())));
        isoDto.setReasonText(StConverter.convertToSt(trialDTO.getReason()));
        isoDto.setStatusDate(TsConverter.convertToTs(PAUtil.dateStringToTimestamp(trialDTO
                .getStatusDate())));
      }
    
    /**
     * Convert to summary4 org dto.
     * 
     * @param trialDTO do
     * 
     * @return iso
     */
    public OrganizationDTO convertToSummary4OrgDTO(TrialDTO trialDTO) {
        if (trialDTO.getSummaryFourOrgIdentifier() == null 
                || trialDTO.getSummaryFourOrgIdentifier().equals("")) {
            return null;
        }
        OrganizationDTO isoDto = new OrganizationDTO();
        isoDto.setIdentifier(IiConverter.convertToIi(trialDTO.getSummaryFourOrgIdentifier()));
        return isoDto;
    }
    
    /**
     * Convert to summary4 org dto.
     * 
     * @param isoDto OrganizationDTO
     * @param trialDTO do
     */
    public void convertToSummary4OrgDTO(TrialDTO trialDTO, OrganizationDTO isoDto) {
        if (trialDTO != null && isoDto != null && trialDTO.getSummaryFourOrgIdentifier() != null 
                && !trialDTO.getSummaryFourOrgIdentifier().equals("")) {
           
        isoDto.setIdentifier(IiConverter.convertToIi(trialDTO.getSummaryFourOrgIdentifier()));
        } 
       
    }
    
    /**
     * Convert to lead org dto.
     * 
     * @param trialDTO do
     * 
     * @return iso
     */
    public OrganizationDTO convertToLeadOrgDTO(TrialDTO trialDTO) {
        OrganizationDTO isoDto = new OrganizationDTO();
        isoDto.setIdentifier(IiConverter.convertToIi(trialDTO.getLeadOrganizationIdentifier()));
        return isoDto;
    }
    
    /**
     * Convert to sponsor org dto.
     * 
     * @param trialDTO do
     * 
     * @return iso
     */
    public OrganizationDTO convertToSponsorOrgDTO(TrialDTO trialDTO) {
        OrganizationDTO isoDto = new OrganizationDTO();
        isoDto.setIdentifier(IiConverter.convertToIi(trialDTO.getSponsorIdentifier()));
        return isoDto;
    }
    
    /**
     * Convert to lead pi.
     * 
     * @param trialDTO dto
     * 
     * @return iso
     */
    public PersonDTO convertToLeadPI(TrialDTO trialDTO) {
        PersonDTO  isoDto = new PersonDTO();
        isoDto.setIdentifier(IiConverter.convertToIi(trialDTO.getPiIdentifier()));
        return isoDto;
    }
    
    /**
     * Convert to responsible party contact dto.
     * 
     * @param trialDTO dto
     * 
     * @return iso
     */
    public PersonDTO convertToResponsiblePartyContactDTO(TrialDTO trialDTO) {
        PersonDTO  isoDto = new PersonDTO();
        isoDto.setIdentifier(IiConverter.convertToIi(trialDTO.getResponsiblePersonIdentifier()));
        return isoDto;
    }
    
    /**
     * Convert to summary4 study resourcing dto.
     * 
     * @param trialDTO dto
     * 
     * @return iso
     */
    public StudyResourcingDTO convertToSummary4StudyResourcingDTO(TrialDTO trialDTO) {
        StudyResourcingDTO isoDto = null; 
        if (PAUtil.isNotEmpty(trialDTO.getSummaryFourFundingCategoryCode())) {
            isoDto = new StudyResourcingDTO();
            isoDto.setTypeCode(CdConverter.convertStringToCd(trialDTO.getSummaryFourFundingCategoryCode()));
        }
        return isoDto;
    }
    
    /**
     * Convert to summary4 study resourcing dto.
     * 
     * @param trialDTO dto
     * @param isDTO StudyResourcingDTO
     */
    public void convertToSummary4StudyResourcingDTO(TrialDTO trialDTO, StudyResourcingDTO isDTO) {
        isDTO.setTypeCode(CdConverter.convertStringToCd(trialDTO.getSummaryFourFundingCategoryCode()));
        
    }
    
    /**
     * Convert to study site dto.
     * 
     * @param trialDTO dto
     * 
     * @return iso
     */
    public StudySiteDTO convertToStudySiteDTO(TrialDTO trialDTO) {
        StudySiteDTO isoDto = new StudySiteDTO();
        isoDto.setLocalStudyProtocolIdentifier(StConverter.convertToSt(trialDTO.getLocalProtocolIdentifier()));
        return isoDto;
    }
    
    /**
     * Convert to nct study site dto.
     * 
     * @param trialDTO dto
     * 
     * @return iso
     */
    public StudySiteDTO convertToNCTStudySiteDTO(TrialDTO trialDTO) {
        StudySiteDTO isoDto = new StudySiteDTO();
        isoDto.setLocalStudyProtocolIdentifier(StConverter.convertToSt(trialDTO.getNctIdentifier()));
        return isoDto;
    }

    /**
     * Convert to nct study site dto.
     * 
     * @param trialDTO dto
     * @param isoDto StudySiteDTO
     */
    public void convertToNCTStudySiteDTO(TrialDTO trialDTO, StudySiteDTO isoDto) {
        isoDto.setLocalStudyProtocolIdentifier(StConverter.convertToSt(trialDTO.getNctIdentifier()));
        
    }
    
    /**
     * Convert to study contact dto.
     * 
     * @param trialDTO dto
     * 
     * @return iso
     */
    public StudyContactDTO convertToStudyContactDTO(TrialDTO trialDTO) {
        StudyContactDTO iso = new StudyContactDTO();
        iso.setTelecomAddresses(getTelecomAddress(trialDTO));
        return iso;
    }

    /**
     * Convert to study contact dto.
     * 
     * @param trialDTO the trial dto
     * @param iso the iso
     */
    public void convertToStudyContactDTO(TrialDTO trialDTO, StudyContactDTO iso) {
        iso.setTelecomAddresses(getTelecomAddress(trialDTO));
     }
    
    /**
     * Convert to study site contact dto.
     * 
     * @param trialDTO dto
     * 
     * @return iso
     */
   public StudySiteContactDTO convertToStudySiteContactDTO(TrialDTO trialDTO) {
       StudySiteContactDTO iso = new StudySiteContactDTO();
       iso.setTelecomAddresses(getTelecomAddress(trialDTO));
       return iso;
   }

   /**
    * Convert to study site contact dto.
    * 
    * @param trialDTO dto
    * @param iso StudySiteContactDTO
    */
  public void convertToStudySiteContactDTO(TrialDTO trialDTO, StudySiteContactDTO iso) {
      iso.setTelecomAddresses(getTelecomAddress(trialDTO));
      
  }
  
   /**
    * Gets the telecom address.
    * 
    * @param trialDTO the trial dto
    * 
    * @return the telecom address
    */
   private DSet<Tel> getTelecomAddress(TrialDTO trialDTO) {
       List<String> phones = new ArrayList<String>();
       phones.add(trialDTO.getContactPhone());
       List<String> emails = new ArrayList<String>();
       emails.add(trialDTO.getContactEmail());
       DSet<Tel> dsetList = null;
       dsetList =  DSetConverter.convertListToDSet(phones, "PHONE", dsetList);
       dsetList =  DSetConverter.convertListToDSet(emails, "EMAIL", dsetList);
       return dsetList;
   }
   
   /**
    * Convert to iso document list.
    * 
    * @param docList dto
    * 
    * @return isoDTOList
    * 
    * @throws PAException ex
    */
   public List<DocumentDTO> convertToISODocumentList(List<TrialDocumentWebDTO> docList) throws PAException {
       List<DocumentDTO> studyDocDTOList = new ArrayList<DocumentDTO>();
       //loop thru the iso dto
       for (TrialDocumentWebDTO dto : docList) {
           DocumentDTO isoDTO = new DocumentDTO();
           isoDTO.setTypeCode(CdConverter.convertStringToCd(dto.getTypeCode()));
           isoDTO.setFileName(StConverter.convertToSt(dto.getFileName()));
           isoDTO.setText(EdConverter.convertToEd(dto.getText()));
           if (PAUtil.isNotEmpty(dto.getStudyProtocolId())) {
               isoDTO.setStudyProtocolIdentifier(IiConverter.convertToIi(dto.getStudyProtocolId()));
           }
           if (PAUtil.isNotEmpty(dto.getId())) {
               isoDTO.setIdentifier(IiConverter.convertToIi(dto.getId()));
           }
           studyDocDTOList.add(isoDTO);
       }
       return studyDocDTOList;
   }
   
   
   /**
    * Convert to iso irb document .
    * 
    * @param docList the doc list
    * @param studyProtocolIi the study protocol ii
    * 
    * @return the trial document web dto
    * 
    * @throws PAException the PA exception
    */
   public DocumentDTO convertToISODocument(List<TrialDocumentWebDTO> docList, Ii studyProtocolIi) 
   throws PAException {
       List<DocumentDTO> docs = RegistryServiceLocator.getDocumentService().
                                   getDocumentsByStudyProtocol(studyProtocolIi);
       DocumentDTO docToUpdate = null;
       if (docList != null && !docList.isEmpty()) {  
        for (DocumentDTO doc : docs) {
         if (DocumentTypeCode.IRB_APPROVAL_DOCUMENT.getCode().equals(
                       CdConverter.convertCdToString(doc.getTypeCode()))) {
            docToUpdate = doc;
            break;
         }
        }  
        TrialDocumentWebDTO dto = docList.get(0);
        docToUpdate.setFileName(StConverter.convertToSt(dto.getFileName()));
        docToUpdate.setText(EdConverter.convertToEd(dto.getText()));
       }
       return docToUpdate;
   }
   /**
    * Convert to document dto.
    * 
    * @param docTypeCode doc
    * @param fileName file
    * @param file file
    * 
    * @return isoDto
    * 
    * @throws IOException io
    */
   public TrialDocumentWebDTO convertToDocumentDTO(String docTypeCode, String fileName, File file) throws IOException {
       TrialDocumentWebDTO docDTO = new TrialDocumentWebDTO();
       docDTO.setTypeCode(docTypeCode);
       docDTO.setFileName(fileName);
       docDTO.setText((readInputStream(new FileInputStream(file))));
       return docDTO;
   }
   
   
      
   /**
    * Read an input stream in its entirety into a byte array.
    * 
    * @param inputStream the input stream
    * 
    * @return the byte[]
    * 
    * @throws IOException Signals that an I/O exception has occurred.
    */
   private static byte[] readInputStream(InputStream inputStream) throws IOException {
       int bufSize = MAXF * MAXF;
       byte[] content;
       List<byte[]> parts = new LinkedList();
       InputStream in = new BufferedInputStream(inputStream);
       byte[] readBuffer = new byte[bufSize];
       byte[] part = null;
       int bytesRead = 0;
       // read everyting into a list of byte arrays
       while ((bytesRead = in.read(readBuffer, 0, bufSize)) != -1) {
           part = new byte[bytesRead];
           System.arraycopy(readBuffer, 0, part, 0, bytesRead);
           parts.add(part);
       }
       // calculate the total size
       int totalSize = 0;
       for (byte[] partBuffer : parts) {
           totalSize += partBuffer.length;
       }
       // allocate the array
       content = new byte[totalSize];
       int offset = 0;
       for (byte[] partBuffer : parts) {
           System.arraycopy(partBuffer, 0, content, offset, partBuffer.length);
           offset += partBuffer.length;
       }
       return content;
   }
   
   /**
    * Convert isoindide list.
    * 
    * @param indList ind
    * 
    * @return isoList
    */
   public List<StudyIndldeDTO>  convertISOINDIDEList(List<TrialIndIdeDTO> indList) {
       if (indList == null || indList.isEmpty()) {
           return null;
       }
       List<StudyIndldeDTO>  studyIndldeDTOList = new ArrayList<StudyIndldeDTO>();
       //loop thru the non-iso dto
       StudyIndldeDTO isoDTO = null;
       for (TrialIndIdeDTO dto : indList) {
           isoDTO = new StudyIndldeDTO();
           isoDTO.setIndldeTypeCode(CdConverter.convertStringToCd(dto.getIndIde()));
           isoDTO.setIndldeNumber(StConverter.convertToSt(dto.getNumber()));
           isoDTO.setGrantorCode(CdConverter.convertStringToCd(dto.getGrantor()));
           isoDTO.setHolderTypeCode(CdConverter.convertStringToCd(dto.getHolderType()));
           if (dto.getHolderType().equalsIgnoreCase("NIH")) {
               isoDTO.setNihInstHolderCode(CdConverter.convertStringToCd(dto.getProgramCode()));
           }
           if (dto.getHolderType().equalsIgnoreCase("NCI")) {
               isoDTO.setNciDivProgHolderCode(CdConverter.convertStringToCd(dto.getProgramCode()));
           }
           if (dto.getExpandedAccess().equalsIgnoreCase(YES)) {
               isoDTO.setExpandedAccessIndicator(BlConverter.convertToBl(Boolean.TRUE));
           } else {
               isoDTO.setExpandedAccessIndicator(BlConverter.convertToBl(Boolean.FALSE));
           }
           isoDTO.setExpandedAccessStatusCode(CdConverter.convertStringToCd(dto.getExpandedAccessType()));
           if (PAUtil.isNotEmpty(dto.getStudyProtocolId())) {
               isoDTO.setStudyProtocolIdentifier(IiConverter.convertToIi(dto.getStudyProtocolId()));
           }
           if (PAUtil.isNotEmpty(dto.getIndIdeId())) {
               isoDTO.setIdentifier(IiConverter.convertToIi(dto.getIndIdeId()));            
           }
           studyIndldeDTOList.add(isoDTO);
       }
       return studyIndldeDTOList;
   }
   
   /**
    * Convert isoindide list.
    * 
    * @param indList ind
    * @param studyProtocolIi Ii
    * 
    * @return isoList
    */
   public List<StudyIndldeDTO>  convertISOINDIDEList(List<TrialIndIdeDTO> indList, Ii studyProtocolIi) {
       if (indList != null && indList.isEmpty()) {
           return null;
       }
       List<StudyIndldeDTO>  studyIndldeDTOList = new ArrayList<StudyIndldeDTO>();
       //loop thru the non-iso dto
       StudyIndldeDTO isoDTO = null;
       for (TrialIndIdeDTO dto : indList) {
           isoDTO = new StudyIndldeDTO();
           isoDTO.setStudyProtocolIdentifier(studyProtocolIi);
           isoDTO.setIndldeTypeCode(CdConverter.convertStringToCd(dto.getIndIde()));
           isoDTO.setIndldeNumber(StConverter.convertToSt(dto.getNumber()));
           isoDTO.setGrantorCode(CdConverter.convertStringToCd(dto.getGrantor()));
           isoDTO.setHolderTypeCode(CdConverter.convertStringToCd(dto.getHolderType()));
           if (dto.getHolderType().equalsIgnoreCase("NIH")) {
               isoDTO.setNihInstHolderCode(CdConverter.convertStringToCd(dto.getProgramCode()));
           }
           if (dto.getHolderType().equalsIgnoreCase("NCI")) {
               isoDTO.setNciDivProgHolderCode(CdConverter.convertStringToCd(dto.getProgramCode()));
           }
           if (dto.getExpandedAccess().equalsIgnoreCase(YES)) {
               isoDTO.setExpandedAccessIndicator(BlConverter.convertToBl(Boolean.TRUE));
           } else {
               isoDTO.setExpandedAccessIndicator(BlConverter.convertToBl(Boolean.FALSE));
           }
           isoDTO.setExpandedAccessStatusCode(CdConverter.convertStringToCd(dto.getExpandedAccessType()));
           studyIndldeDTOList.add(isoDTO);
       }
       return studyIndldeDTOList;
   }
   
   /**
    * Convert iso grants list.
    * 
    * @param grantList list
    * 
    * @return isoList
    */
   public List<StudyResourcingDTO>  convertISOGrantsList(List<TrialFundingWebDTO> grantList) {
       if (grantList == null ||  grantList.isEmpty()) {
           return null;
       }
       List<StudyResourcingDTO>  grantsDTOList = new ArrayList<StudyResourcingDTO>();
       StudyResourcingDTO isoDTO = null;
       for (TrialFundingWebDTO dto : grantList) {
           isoDTO = new StudyResourcingDTO();
           isoDTO.setSummary4ReportedResourceIndicator(BlConverter.convertToBl(Boolean.FALSE));
           isoDTO.setFundingMechanismCode(CdConverter.convertStringToCd(dto.getFundingMechanismCode()));
           isoDTO.setNciDivisionProgramCode(CdConverter.convertToCd(
                               NciDivisionProgramCode.getByCode(dto.getNciDivisionProgramCode())));
           isoDTO.setNihInstitutionCode(CdConverter.convertStringToCd(dto.getNihInstitutionCode()));
           isoDTO.setSerialNumber(StConverter.convertToSt(dto.getSerialNumber()));
           if (PAUtil.isNotEmpty(dto.getStudyProtocolId())) {
               isoDTO.setStudyProtocolIdentifier(IiConverter.convertToIi(dto.getStudyProtocolId()));            
           }
           if (PAUtil.isNotEmpty(dto.getId())) {
               isoDTO.setIdentifier(IiConverter.convertToIi(dto.getId()));            
           }
           grantsDTOList.add(isoDTO);
       }
       return grantsDTOList;
   }
   
   /**
    * Convert iso grants list.
    * 
    * @param grantList list
    * @param studyProtocolIi Ii
    * 
    * @return isoList
    */
   public List<StudyResourcingDTO>  convertISOGrantsList(List<TrialFundingWebDTO> grantList, Ii studyProtocolIi) {
       if (grantList != null && grantList.isEmpty()) {
           return null;
       }
       List<StudyResourcingDTO>  grantsDTOList = new ArrayList<StudyResourcingDTO>();
       StudyResourcingDTO isoDTO = null;
       for (TrialFundingWebDTO dto : grantList) {
           isoDTO = new StudyResourcingDTO();
           isoDTO.setStudyProtocolIdentifier(studyProtocolIi);
           isoDTO.setSummary4ReportedResourceIndicator(BlConverter.convertToBl(Boolean.FALSE));
           isoDTO.setFundingMechanismCode(CdConverter.convertStringToCd(dto.getFundingMechanismCode()));
           isoDTO.setNciDivisionProgramCode(CdConverter.convertToCd(
                               NciDivisionProgramCode.getByCode(dto.getNciDivisionProgramCode())));
           isoDTO.setNihInstitutionCode(CdConverter.convertStringToCd(dto.getNihInstitutionCode()));
           isoDTO.setSerialNumber(StConverter.convertToSt(dto.getSerialNumber()));
           grantsDTOList.add(isoDTO);
       }
       return grantsDTOList;
   }
   
   /**
    * Gets the trial dto from db.
    * 
    * @param studyProtocolIi Ii
    * @param trialDTO TrailDTO
    * 
    * @throws PAException ex
    * @throws NullifiedRoleException e
    */
   @SuppressWarnings({"PMD.ExcessiveMethodLength" })
   public void getTrialDTOFromDb(Ii studyProtocolIi, TrialDTO trialDTO) throws PAException, NullifiedRoleException {
       StudyProtocolDTO spDTO = RegistryServiceLocator.getStudyProtocolService().getStudyProtocol(studyProtocolIi);
       StudyProtocolQueryDTO spqDto = RegistryServiceLocator.getProtocolQueryService().getTrialSummaryByStudyProtocolId(
                                               Long.valueOf(studyProtocolIi.getExtension()));
       CorrelationUtils cUtils = new CorrelationUtils();
       copy(spDTO, trialDTO);
       copy(spqDto, trialDTO);
       copyLO(cUtils.getPAOrganizationByIi(
               IiConverter.convertToPaOrganizationIi(spqDto.getLeadOrganizationId())), trialDTO);
       //
       copyPI(cUtils.getPAPersonByIi(IiConverter.convertToPaPersonIi(spqDto.getPiId())), trialDTO);
       copyResponsibleParty(studyProtocolIi, trialDTO);
       copySponsor(studyProtocolIi, trialDTO);
       copyNctNummber(studyProtocolIi, trialDTO);
       copySummaryFour(RegistryServiceLocator.getStudyResourcingService().
                   getsummary4ReportedResource(studyProtocolIi), trialDTO);
       //Copy IND's
       List<StudyIndldeDTO> studyIndldeDTOList = RegistryServiceLocator.getStudyIndldeService()
           .getByStudyProtocol(studyProtocolIi);
       if (!(studyIndldeDTOList.isEmpty())) {
            copyINDIDEList(studyIndldeDTOList, trialDTO);
       }
       // query the study grants
       List<StudyResourcingDTO> isoList = RegistryServiceLocator.getStudyResourcingService()
                   .getstudyResourceByStudyProtocol(studyProtocolIi);
       if (!(isoList.isEmpty())) {
           copyGrantList(isoList, trialDTO);
       }
       //copy the reason for trial
       StudyOverallStatusDTO sosDto = null;
       sosDto = RegistryServiceLocator.getStudyOverallStatusService().getCurrentByStudyProtocol(studyProtocolIi);
       if (sosDto != null) {
           trialDTO.setReason(StConverter.convertToString(sosDto.getReasonText()));
       } else {
           trialDTO.setReason(null);
       }
       
       //get the regulatory information.
        copyRegulatoryInformation(studyProtocolIi, trialDTO);
       
       //get the collaborators information.
       copyCollaborators(studyProtocolIi, trialDTO);
       
       //get the participating sites information.
       copyParticipatingSites(studyProtocolIi, trialDTO);
       
       //Copy IND's
       List<StudyIndldeDTO> studyIndldeUpdateDTOList = RegistryServiceLocator.getStudyIndldeService()
           .getByStudyProtocol(studyProtocolIi);
       if (!(studyIndldeUpdateDTOList.isEmpty())) {
            copyINDIDEUpdateList(studyIndldeUpdateDTOList, trialDTO);
       }
       
     // return trialDTO;
   }
   
   /**
    * Copy collaborators.
    * 
    * @param studyProtocolIi the study protocol ii
    * @param trialDTO the trial dto
    * 
    * @throws PAException the PA exception
    */
   private void copyCollaborators(Ii studyProtocolIi, TrialDTO trialDTO) throws PAException {
       ArrayList<StudySiteDTO> criteriaList = new ArrayList<StudySiteDTO>();
       for (StudySiteFunctionalCode cd : StudySiteFunctionalCode.values()) {
           if (cd.isCollaboratorCode()) {
               StudySiteDTO searchCode = new StudySiteDTO();
               searchCode.setFunctionalCode(CdConverter.convertToCd(cd));
               criteriaList.add(searchCode);
           }
       }
       List<PaOrganizationDTO> collaboratorsList = new ArrayList<PaOrganizationDTO>();
       List<StudySiteDTO> spList = RegistryServiceLocator.getStudySiteService()
                                               .getByStudyProtocol(studyProtocolIi, criteriaList);
       for (StudySiteDTO sp : spList) {
           CorrelationUtils cUtils = new CorrelationUtils();
           Organization orgBo = cUtils.getPAOrganizationByIi(sp.getResearchOrganizationIi());
           PaOrganizationDTO orgWebDTO = new PaOrganizationDTO();
           orgWebDTO.setId(IiConverter.convertToString(sp.getIdentifier()));
           orgWebDTO.setName(orgBo.getName());
           orgWebDTO.setNciNumber(orgBo.getIdentifier());
           orgWebDTO.setFunctionalRole(sp.getFunctionalCode().getCode());
           orgWebDTO.setStatus(sp.getStatusCode().getCode());
           collaboratorsList.add(orgWebDTO);
       }
       trialDTO.setCollaborators(collaboratorsList);
      
   }
   
   /**
    * Copy participating sites.
    * 
    * @param studyProtocolIi the study protocol ii
    * @param trialDTO the trial dto
    * 
    * @throws PAException the PA exception
    */
   private void copyParticipatingSites(Ii studyProtocolIi, TrialDTO trialDTO) throws PAException {
       
       List<PaOrganizationDTO> participatingSitesList = new ArrayList<PaOrganizationDTO>();
       StudySiteDTO srDTO = new StudySiteDTO();
       srDTO.setFunctionalCode(CdConverter.convertStringToCd(StudySiteFunctionalCode.TREATING_SITE
                       .getCode()));
       List<StudySiteDTO> spList = RegistryServiceLocator.getStudySiteService()
                                               .getByStudyProtocol(studyProtocolIi, srDTO);
       for (StudySiteDTO sp : spList) {
           StudySiteAccrualStatusDTO ssas = RegistryServiceLocator.getStudySiteAccrualStatusService()
                                              .getCurrentStudySiteAccrualStatusByStudySite(sp.getIdentifier());
           CorrelationUtils cUtils = new CorrelationUtils();
           Organization orgBo = cUtils.getPAOrganizationByIi(sp.getHealthcareFacilityIi());
           PaOrganizationDTO orgWebDTO = new PaOrganizationDTO();
           orgWebDTO.setId(IiConverter.convertToString(sp.getIdentifier()));
           orgWebDTO.setName(orgBo.getName());
           orgWebDTO.setProgramCode(StConverter.convertToString(sp.getProgramCodeText()));
           orgWebDTO.setNciNumber(orgBo.getIdentifier());
           if (ssas == null || ssas.getStatusCode() == null || ssas.getStatusDate() == null) {
               orgWebDTO.setRecruitmentStatus("unknown");
               orgWebDTO.setRecruitmentStatusDate("unknown");
           } else {
               orgWebDTO.setRecruitmentStatus(CdConverter.convertCdToString(ssas.getStatusCode()));
               orgWebDTO.setRecruitmentStatusDate(PAUtil.normalizeDateString(TsConverter.convertToTimestamp(
                       ssas.getStatusDate()).toString()));
           }
           participatingSitesList.add(orgWebDTO);
       }    
       trialDTO.setParticipatingSites(participatingSitesList);
   }
   
   /**
    * Copy regulatory information.
    * 
    * @param studyProtocolIi the study protocol ii
    * @param trialDTO the trial dto
    * 
    * @throws PAException the PA exception
    */
   private void copyRegulatoryInformation(Ii studyProtocolIi, TrialDTO trialDTO) throws PAException {
         StudyRegulatoryAuthorityDTO authorityDTO = 
             RegistryServiceLocator.getStudyRegulatoryAuthorityService().getCurrentByStudyProtocol(studyProtocolIi);
           trialDTO.setCountryList(RegistryServiceLocator.getRegulatoryInformationService().getDistinctCountryNames());
           if (authorityDTO != null) { // load values from database
               RegulatoryAuthorityWebDTO webDTO = new RegulatoryAuthorityWebDTO(); 
                       StudyProtocolDTO spDTO = RegistryServiceLocator.getStudyProtocolService()
                                                           .getStudyProtocol(studyProtocolIi);
                       if (spDTO.getSection801Indicator().getValue() != null) {
                           webDTO.setSection801Indicator(BlConverter.convertToString(spDTO.getSection801Indicator()));
                           trialDTO.setSection801Indicator(BlConverter.convertToString(spDTO.getSection801Indicator()));
                       }
                       if (spDTO.getFdaRegulatedIndicator().getValue() != null) {
                           webDTO.setFdaRegulatedInterventionIndicator(BlConverter.convertToString(spDTO
                               .getFdaRegulatedIndicator()));
                           trialDTO.setFdaRegulatoryInformationIndicator(BlConverter.convertToString(spDTO
                                   .getFdaRegulatedIndicator()));
                       }
                       if (spDTO.getDelayedpostingIndicator().getValue() != null) {
                           webDTO.setDelayedPostingIndicator(
                                  BlConverter.convertToString(spDTO.getDelayedpostingIndicator()));
                       }
                       if (spDTO.getDataMonitoringCommitteeAppointedIndicator().getValue() != null) {
                           webDTO.setDataMonitoringIndicator((BlConverter.convertToString(spDTO
                               .getDataMonitoringCommitteeAppointedIndicator())));
                       }
                      StudyRegulatoryAuthorityDTO sraFromDatabaseDTO = 
                                          RegistryServiceLocator.getStudyRegulatoryAuthorityService()
                                                      .getCurrentByStudyProtocol(studyProtocolIi);
                     if (sraFromDatabaseDTO != null) {
                       Long sraId = Long.valueOf(sraFromDatabaseDTO.getRegulatoryAuthorityIdentifier().getExtension());
                       List<Long> regInfo = RegistryServiceLocator.getRegulatoryInformationService()
                                                      .getRegulatoryAuthorityInfo(sraId);
                       trialDTO.setLst(regInfo.get(1).toString());
                       //set selected the name of the regulatory authority chosen
                       trialDTO.setRegIdAuthOrgList(RegistryServiceLocator.getRegulatoryInformationService().
                                                getRegulatoryAuthorityNameId(Long.valueOf(regInfo.get(1).toString())));
                       trialDTO.setSelectedRegAuth(regInfo.get(0).toString());
                       trialDTO.setRegulatoryAuthority(webDTO);
                       
                   } 
                }
        
       }

   /**
    * Convert to interventional study protocol dto.
    * 
    * @param trialDTO dtotoConvert
    * 
    * @return isoDto
    * 
    * @throws PAException on error
    */
   public InterventionalStudyProtocolDTO convertToInterventionalStudyProtocolDTO(TrialDTO trialDTO) throws PAException {
       InterventionalStudyProtocolDTO isoDto =  new InterventionalStudyProtocolDTO();       
       isoDto = (InterventionalStudyProtocolDTO) convertToStudyProtocolDTO(trialDTO, isoDto);
       return isoDto;
   }
  
   /**
    * Methods used by update trial.
    * 
    * @param spdto the spdto
    * @param trialDTO the trial dto
    * 
    * @throws PAException the PA exception
    */
   /**
    * updates the studyprocol dto with the trail details and status information.
    * @param trialDTO TrialDTO
    * @param spdto StudyProtocolDTO
    * @throws PAException on error
    */
    public void updateStudyProtcolDTO(StudyProtocolDTO spdto, TrialDTO trialDTO) throws PAException {
        
        convertToStudyProtocolDTO(trialDTO, spdto);
     
    }
   
}
