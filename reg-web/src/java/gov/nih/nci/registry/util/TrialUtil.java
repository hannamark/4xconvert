package gov.nih.nci.registry.util;

import gov.nih.nci.iso21090.DSet;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.iso21090.Tel;
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
import gov.nih.nci.pa.enums.SummaryFourFundingCategoryCode;
import gov.nih.nci.pa.iso.dto.DocumentDTO;
import gov.nih.nci.pa.iso.dto.InterventionalStudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.StudyContactDTO;
import gov.nih.nci.pa.iso.dto.StudyFundingStageDTO;
import gov.nih.nci.pa.iso.dto.StudyIndIdeStageDTO;
import gov.nih.nci.pa.iso.dto.StudyIndldeDTO;
import gov.nih.nci.pa.iso.dto.StudyOverallStatusDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolStageDTO;
import gov.nih.nci.pa.iso.dto.StudyRegulatoryAuthorityDTO;
import gov.nih.nci.pa.iso.dto.StudyResourcingDTO;
import gov.nih.nci.pa.iso.dto.StudySiteAccrualStatusDTO;
import gov.nih.nci.pa.iso.dto.StudySiteContactDTO;
import gov.nih.nci.pa.iso.dto.StudySiteDTO;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.DSetConverter;
import gov.nih.nci.pa.iso.util.EdConverter;
import gov.nih.nci.pa.iso.util.EnOnConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.IntConverter;
import gov.nih.nci.pa.iso.util.IvlConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.correlation.CorrelationUtils;
import gov.nih.nci.pa.service.util.PAServiceUtils;
import gov.nih.nci.pa.util.CommonsConstant;
import gov.nih.nci.pa.util.PAConstants;
import gov.nih.nci.pa.util.PADomainUtils;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.pa.util.PaRegistry;
import gov.nih.nci.registry.dto.BaseTrialDTO;
import gov.nih.nci.registry.dto.ProprietaryTrialDTO;
import gov.nih.nci.registry.dto.StudyIndldeWebDTO;
import gov.nih.nci.registry.dto.SubmittedOrganizationDTO;
import gov.nih.nci.registry.dto.TrialDTO;
import gov.nih.nci.registry.dto.TrialDocumentWebDTO;
import gov.nih.nci.registry.dto.TrialFundingWebDTO;
import gov.nih.nci.registry.dto.TrialIndIdeDTO;
import gov.nih.nci.services.correlation.NullifiedRoleException;
import gov.nih.nci.services.correlation.OrganizationalContactDTO;
import gov.nih.nci.services.organization.OrganizationDTO;
import gov.nih.nci.services.person.PersonDTO;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;

/**
 * The Class TrialUtil.
 *
 * @author vrushali
 */
@SuppressWarnings({"PMD.CyclomaticComplexity", "PMD.TooManyMethods",
    "PMD.ExcessiveClassLength", "PMD.NPathComplexity", "PMD.ExcessiveMethodLength" })
public class TrialUtil {

    private static final String NULLIFIED_PERSON = "Nullified Person";
    private static final String NULLIFIED_ORGANIZATION = "Nullified Organization";
    /** The Constant SPONSOR. */
    private static final String SPONSOR = "sponsor";
    /** The Constant MAXF. */
    private static final int MAXF = 1024;
    private final PAServiceUtils paServiceUtil = new PAServiceUtils();
    /**
     * Default constructor.
     */
    public TrialUtil() {
        super();
    }

    /**
     * Copy.
     * @param spDTO sdto
     * @param trialDTO gdto
     */
    private void copy(StudyProtocolDTO spDTO, TrialDTO trialDTO) {
        trialDTO.setOfficialTitle(spDTO.getOfficialTitle().getValue());
        trialDTO.setAssignedIdentifier(PAUtil.getAssignedIdentifierExtension(spDTO));
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
        if (!PAUtil.isBlNull(spDTO.getCtgovXmlRequiredIndicator())
            && spDTO.getCtgovXmlRequiredIndicator().getValue().booleanValue()) {
            trialDTO.setXmlRequired(true);
        } else {
           trialDTO.setXmlRequired(false);
        }
        if (spDTO.getSecondaryIdentifiers() != null && spDTO.getSecondaryIdentifiers().getItem() != null) {
            List<Ii> listIi = new ArrayList<Ii>();
            for (Ii ii : spDTO.getSecondaryIdentifiers().getItem()) {
              if (!IiConverter.STUDY_PROTOCOL_ROOT.equals(ii.getRoot())) {
                listIi.add(ii);
              }
            }
            trialDTO.setSecondaryIdentifierList(listIi);
        }
    }
    /**
     * Adds the secondary identifiers.
     * @param spDTO the sp dto
     * @param trialDTO the trial dto
     */
    public void addSecondaryIdentifiers(StudyProtocolDTO spDTO, TrialDTO trialDTO) {
        List <Ii> secondaryIis = new ArrayList<Ii>();
        if (trialDTO.getSecondaryIdentifierList() != null && !trialDTO.getSecondaryIdentifierList().isEmpty()) {
          secondaryIis.addAll(trialDTO.getSecondaryIdentifierList());
        }
        if (trialDTO.getSecondaryIdentifierAddList() != null && !trialDTO.getSecondaryIdentifierAddList().isEmpty()) {
          secondaryIis.addAll(trialDTO.getSecondaryIdentifierAddList());
        }
        if (spDTO.getSecondaryIdentifiers() != null && spDTO.getSecondaryIdentifiers().getItem() != null) {
          for (Ii ii : spDTO.getSecondaryIdentifiers().getItem()) {
            if (IiConverter.STUDY_PROTOCOL_ROOT.equals(ii.getRoot())) {
              secondaryIis.add(ii);
              break;
            }
          }
        }

         spDTO.getSecondaryIdentifiers().setItem(null);
         trialDTO.getSecondaryIdentifierList().clear();
         trialDTO.setSecondaryIdentifierList(secondaryIis);
      }

    /**
     * Copy.
     * @param spqDTO sdto
     * @param trialDTO gdto
     */
    private void copy(StudyProtocolQueryDTO spqDTO, TrialDTO trialDTO) {
        trialDTO.setStudyProtocolId(spqDTO.getStudyProtocolId().toString());
        trialDTO.setLeadOrgTrialIdentifier(spqDTO.getLocalStudyProtocolIdentifier());
        trialDTO.setStatusCode(spqDTO.getStudyStatusCode().getCode());
        trialDTO.setStatusDate(PAUtil.normalizeDateString(spqDTO.getStudyStatusDate().toString()));
    }
    /**
     * Copy lo.
     * @param o o
     * @param trialDTO gdto
     */
    private void copyLO(Organization o, BaseTrialDTO trialDTO) {
        trialDTO.setLeadOrganizationIdentifier(o.getIdentifier());
        trialDTO.setLeadOrganizationName(o.getName());
    }

    /**
     * Copy pi.
     * @param p p
     * @param trialDTO dto
     */
    private void copyPI(Person p, TrialDTO trialDTO) {
        trialDTO.setPiIdentifier(p.getIdentifier());
        trialDTO.setPiName(p.getFullName());
    }
    /**
     * Copy responsible party.
     * @param studyProtocolIi ii
     * @param trialDTO dto
     * @throws PAException ex
     * @throws NullifiedRoleException the nullified role exception
     */
    private void copyResponsibleParty(Ii studyProtocolIi, TrialDTO trialDTO) throws PAException,
        NullifiedRoleException  {
        StudyContactDTO scDto = new StudyContactDTO();
        scDto.setRoleCode(CdConverter.convertToCd(StudyContactRoleCode.RESPONSIBLE_PARTY_STUDY_PRINCIPAL_INVESTIGATOR));
        List<StudyContactDTO> scDtos =  PaRegistry.getStudyContactService()
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
            List<StudySiteContactDTO> spDtos = PaRegistry.getStudySiteContactService()
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
            trialDTO.setContactPhone(PAUtil.getPhone(phones.get(0)));
            trialDTO.setContactPhoneExtn(PAUtil.getPhoneExtn(phones.get(0)));
        }
        if (emails != null && !emails.isEmpty()) {
            trialDTO.setContactEmail(emails.get(0));
        }
    }
    /**
     * Copy sponsor.
     * @param studyProtocolIi ii
     * @param trialDTO dto
     * @throws PAException ex
     */
    private void copySponsor(Ii studyProtocolIi, TrialDTO trialDTO) throws PAException {
        StudySiteDTO spart = new StudySiteDTO();
        spart.setFunctionalCode(CdConverter.convertToCd(StudySiteFunctionalCode.SPONSOR));
        List<StudySiteDTO> spDtos = PaRegistry.getStudySiteService()
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
     * @param studyProtocolIi ii
     * @param trialDTO dto
     * @throws PAException ex
     */
    private void copyNctNummber(Ii studyProtocolIi, BaseTrialDTO trialDTO) throws PAException {
        String nctNumber = paServiceUtil.getStudyIdentifier(studyProtocolIi, PAConstants.NCT_IDENTIFIER_TYPE);
        if (nctNumber != null) {
            trialDTO.setNctIdentifier(nctNumber);
        }
    }
    /**
     * Copy summary four.
     * @param srDTO sdto
     * @param trialDTO tdto
     * @throws PAException ex
     */
    private void copySummaryFour(StudyResourcingDTO srDTO, BaseTrialDTO trialDTO) throws PAException {
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
     * @param studyIndldeDTOList iiDto
     * @param trialDTO dto
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
     * @param trialDTO dtotoConvert
     * @return isoDto
     * @throws PAException on error
     */
    public StudyProtocolDTO convertToStudyProtocolDTOForAmendment(BaseTrialDTO trialDTO) throws PAException {
        StudyProtocolDTO isoDto = null;

        if (trialDTO.getTrialType().equalsIgnoreCase("Observational")) {
            isoDto = PaRegistry.getStudyProtocolService().getObservationalStudyProtocol(
                        IiConverter.convertToStudyProtocolIi(Long.parseLong(trialDTO.getIdentifier())));
        } else {
            isoDto  = PaRegistry.getStudyProtocolService().getInterventionalStudyProtocol(
                    IiConverter.convertToStudyProtocolIi(Long.parseLong(trialDTO.getIdentifier())));
        }
        addSecondaryIdentifiers(isoDto, (TrialDTO) trialDTO);
        isoDto = convertToStudyProtocolDTO(trialDTO, isoDto);
        if (isoDto.getSecondaryIdentifiers() != null && isoDto.getSecondaryIdentifiers().getItem() != null) {
            for (Ii ii : isoDto.getSecondaryIdentifiers().getItem()) {
                if (IiConverter.STUDY_PROTOCOL_ROOT.equals(ii.getRoot())) {
                    ii.setExtension(trialDTO.getAssignedIdentifier());
                }
            }
        }
        isoDto.setAmendmentDate(TsConverter.convertToTs(PAUtil.dateStringToTimestamp(
                ((TrialDTO) trialDTO).getAmendmentDate())));
        isoDto.setAmendmentNumber(StConverter.convertToSt(((TrialDTO) trialDTO).getLocalAmendmentNumber()));
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
    @SuppressWarnings({"PMD.NPathComplexity", "PMD.ExcessiveMethodLength" })
    public StudyProtocolDTO convertToStudyProtocolDTO(BaseTrialDTO trialDTO,
            StudyProtocolDTO isoDto) {
        if (PAUtil.isNotEmpty(trialDTO.getOfficialTitle())) {
            isoDto.setOfficialTitle(StConverter.convertToSt(trialDTO.getOfficialTitle()));
        }
        isoDto.setPhaseCode(CdConverter.convertToCd(PhaseCode.getByCode(trialDTO.getPhaseCode())));
        if (PAUtil.isNotEmpty(trialDTO.getPhaseCode())
             && PhaseCode.OTHER.getCode().equals(trialDTO.getPhaseCode())
             && PAUtil.isNotEmpty(trialDTO.getPhaseOtherText())) {
                isoDto.setPhaseOtherText(StConverter.convertToSt(trialDTO.getPhaseOtherText()));
        } else {
            isoDto.setPhaseOtherText(StConverter.convertToSt(null));
        }
        isoDto.setPrimaryPurposeCode(CdConverter.convertToCd(
                PrimaryPurposeCode.getByCode(trialDTO.getPrimaryPurposeCode())));

        if (PAUtil.isNotEmpty(trialDTO.getPrimaryPurposeCode())
                && PrimaryPurposeCode.OTHER.getCode().equals(trialDTO.getPrimaryPurposeCode())
                && PAUtil.isNotEmpty(trialDTO.getPrimaryPurposeOtherText())) {
            isoDto.setPrimaryPurposeOtherText(
                    StConverter.convertToSt(trialDTO.getPrimaryPurposeOtherText()));
        } else {
            isoDto.setPrimaryPurposeOtherText(
                    StConverter.convertToSt(null));
        }
        if (trialDTO.getPropritaryTrialIndicator() == null) {
            isoDto.setProprietaryTrialIndicator(BlConverter.convertToBl(null));
        } else if  (CommonsConstant.YES.equalsIgnoreCase(trialDTO.getPropritaryTrialIndicator())) {
            isoDto.setProprietaryTrialIndicator(BlConverter.convertToBl(Boolean.TRUE));
        } else {
            isoDto.setProprietaryTrialIndicator(BlConverter.convertToBl(Boolean.FALSE));
        }
        if (trialDTO instanceof TrialDTO) {
            convertToStudyProtocolDTO((TrialDTO) trialDTO, isoDto);
        }
        return isoDto;
    }

    /**
     * @param trialDTO
     * @param isoDto
     */
    private void convertToStudyProtocolDTO(TrialDTO trialDTO,
            StudyProtocolDTO isoDto) {
        isoDto.setStartDate(TsConverter.convertToTs(PAUtil.dateStringToTimestamp(trialDTO.getStartDate())));
        isoDto.setStartDateTypeCode(CdConverter.convertToCd(ActualAnticipatedTypeCode.getByCode(trialDTO
                .getStartDateType())));
        isoDto.setPrimaryCompletionDate(TsConverter.convertToTs(PAUtil.dateStringToTimestamp(trialDTO
                .getCompletionDate())));
        isoDto.setPrimaryCompletionDateTypeCode(CdConverter.convertToCd(ActualAnticipatedTypeCode
                .getByCode(trialDTO.getCompletionDateType())));
        isoDto.setStudyProtocolType(StConverter.convertToSt(trialDTO.getTrialType()));
        isoDto.setProgramCodeText(StConverter.convertToSt(trialDTO.getProgramCodeText()));
        if (trialDTO.getFdaRegulatoryInformationIndicator() == null) {
            isoDto.setFdaRegulatedIndicator(BlConverter.convertToBl(null));
        } else if  (CommonsConstant.YES.equalsIgnoreCase(trialDTO.getFdaRegulatoryInformationIndicator())) {
            isoDto.setFdaRegulatedIndicator(BlConverter.convertToBl(Boolean.TRUE));
        } else {
            isoDto.setFdaRegulatedIndicator(BlConverter.convertToBl(Boolean.FALSE));
        }
        if (trialDTO.getSection801Indicator() == null) {
            isoDto.setSection801Indicator(BlConverter.convertToBl(null));
        } else if (CommonsConstant.YES.equalsIgnoreCase(trialDTO.getSection801Indicator())) {
            isoDto.setSection801Indicator(BlConverter.convertToBl(Boolean.TRUE));
        } else {
            isoDto.setSection801Indicator(BlConverter.convertToBl(Boolean.FALSE));
        }

        isoDto.setProprietaryTrialIndicator(BlConverter.convertToBl(Boolean.FALSE));
        if (trialDTO.getDelayedPostingIndicator() == null) {
            isoDto.setDelayedpostingIndicator(BlConverter.convertToBl(null));
        } else if (CommonsConstant.YES.equalsIgnoreCase(trialDTO.getDelayedPostingIndicator())) {
            isoDto.setDelayedpostingIndicator(BlConverter.convertToBl(Boolean.TRUE));
        } else {
            isoDto.setDelayedpostingIndicator(BlConverter.convertToBl(Boolean.FALSE));
        }
        if (trialDTO.getDataMonitoringCommitteeAppointedIndicator() == null) {
            isoDto.setDataMonitoringCommitteeAppointedIndicator(BlConverter.convertToBl(null));
        } else if (CommonsConstant.YES.equalsIgnoreCase(trialDTO.getDataMonitoringCommitteeAppointedIndicator())) {
            isoDto.setDataMonitoringCommitteeAppointedIndicator(BlConverter.convertToBl(Boolean.TRUE));
        } else {
            isoDto.setDataMonitoringCommitteeAppointedIndicator(BlConverter.convertToBl(Boolean.FALSE));
        }
        if  (trialDTO.getXmlRequired()) {
            isoDto.setCtgovXmlRequiredIndicator(BlConverter.convertToBl(Boolean.TRUE));
        } else {
            isoDto.setCtgovXmlRequiredIndicator(BlConverter.convertToBl(Boolean.FALSE));
        }
        isoDto.setPrimaryPurposeCode(CdConverter.convertToCd(PrimaryPurposeCode.getByCode(
                trialDTO.getPrimaryPurposeCode())));
        isoDto.setPrimaryPurposeOtherText(StConverter.convertToSt(trialDTO.getPrimaryPurposeOtherText()));
        if (trialDTO.getSecondaryIdentifierList() != null && !trialDTO.getSecondaryIdentifierList().isEmpty()) {
           List<Ii> iis = new ArrayList<Ii>();
           for (Ii sps : trialDTO.getSecondaryIdentifierList()) {
            if (IiConverter.STUDY_PROTOCOL_ROOT.equals(sps.getRoot())) {
              iis.add(IiConverter.convertToAssignedIdentifierIi(sps.getExtension()));
            } else {
               iis.add(IiConverter.convertToOtherIdentifierIi(sps.getExtension()));
            }
           }
           isoDto.setSecondaryIdentifiers(DSetConverter.convertIiSetToDset(new HashSet<Ii>(iis)));
        }
    }
    /**
     * Convert to study overall status dto.
     * @param trialDTO Dto
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
     * @param trialDTO do
     * @return iso
     */
    public OrganizationDTO convertToSummary4OrgDTO(BaseTrialDTO trialDTO) {
        OrganizationDTO isoDto = null;
        if (PAUtil.isNotEmpty(trialDTO.getSummaryFourOrgIdentifier())) {
            isoDto = new OrganizationDTO();
            isoDto.setIdentifier(IiConverter.convertToPoOrganizationIi(trialDTO.getSummaryFourOrgIdentifier()));
        }
        return isoDto;
    }
    /**
     * Convert to lead org dto.
     * @param trialDTO do
     * @return iso
     */
    public OrganizationDTO convertToLeadOrgDTO(BaseTrialDTO trialDTO) {
        OrganizationDTO isoDto = new OrganizationDTO();
        isoDto.setIdentifier(IiConverter.convertToPoOrganizationIi(trialDTO.getLeadOrganizationIdentifier()));
        return isoDto;
    }

    /**
     * Convert to sponsor org dto.
     * @param trialDTO do
     * @return iso
     */
    public OrganizationDTO convertToSponsorOrgDTO(TrialDTO trialDTO) {
        OrganizationDTO isoDto = new OrganizationDTO();
        isoDto.setIdentifier(IiConverter.convertToPoOrganizationIi(trialDTO.getSponsorIdentifier()));
        return isoDto;
    }

    /**
     * Convert to lead pi.
     * @param trialDTO dto
     * @return iso
     */
    public PersonDTO convertToLeadPI(TrialDTO trialDTO) {
        PersonDTO  isoDto = new PersonDTO();
        isoDto.setIdentifier(IiConverter.convertToPoPersonIi(trialDTO.getPiIdentifier()));
        return isoDto;
    }

    /**
     * Convert to responsible party contact dto.
     * @param trialDTO dto
     * @return iso
     */
    public PersonDTO convertToResponsiblePartyContactDTO(TrialDTO trialDTO) {
        PersonDTO  isoDto = new PersonDTO();
        isoDto.setIdentifier(IiConverter.convertToIi(trialDTO.getResponsiblePersonIdentifier()));
        return isoDto;
    }

    /**
     * Convert to summary4 study resourcing dto.
     * @param trialDTO dto
     * @param studyProtocolIi ii
     * @return iso
     * @throws PAException e
     */
    public StudyResourcingDTO convertToSummary4StudyResourcingDTO(BaseTrialDTO trialDTO, Ii studyProtocolIi)
        throws PAException {
        StudyResourcingDTO isoDto = null;
        if (PAUtil.isNotEmpty(trialDTO.getSummaryFourFundingCategoryCode())) {
            isoDto = new StudyResourcingDTO();
            if (PAUtil.isIiNotNull(studyProtocolIi)) {
                StudyResourcingDTO summary4studyResourcingDTO = PaRegistry.getStudyResourcingService()
                    .getsummary4ReportedResource(studyProtocolIi);
                if (summary4studyResourcingDTO != null) {
                    isoDto = summary4studyResourcingDTO;
                }
                isoDto.setStudyProtocolIdentifier(studyProtocolIi);
            }
            isoDto.setTypeCode(CdConverter.convertStringToCd(trialDTO.getSummaryFourFundingCategoryCode()));
        }
        return isoDto;
    }
    /**
     * Convert to study site dto.
     * @param trialDTO dto
     * @return iso
     */
    public StudySiteDTO convertToleadOrgSiteIdDTO(BaseTrialDTO trialDTO) {
        StudySiteDTO isoDto = new StudySiteDTO();
        isoDto.setLocalStudyProtocolIdentifier(StConverter.convertToSt(trialDTO.getLeadOrgTrialIdentifier()));
        return isoDto;
    }
    /**
     * Convert to nct study site dto.
     *
     * @param trialDTO dto
     * @param studyProtocolIi Ii
     * @return iso
     * @throws PAException e
     */
    public StudySiteDTO convertToNCTStudySiteDTO(BaseTrialDTO trialDTO, Ii studyProtocolIi) throws PAException {
        StudySiteDTO isoDto = new StudySiteDTO();
        Ii nctROIi = null;
        String poOrgId = PaRegistry.getOrganizationCorrelationService().getPOOrgIdentifierByIdentifierType(
                PAConstants.NCT_IDENTIFIER_TYPE);
        nctROIi = PaRegistry.getOrganizationCorrelationService().getPoResearchOrganizationByEntityIdentifier(
                IiConverter.convertToPoOrganizationIi(String.valueOf(poOrgId)));
        if (PAUtil.isNotEmpty(trialDTO.getNctIdentifier())) {
            if (PAUtil.isIiNotNull(studyProtocolIi)) {
                //find if the NCT number is there
                StudySiteDTO criteriaNCTStudySite = new StudySiteDTO();
                criteriaNCTStudySite.setStudyProtocolIdentifier(studyProtocolIi);
                criteriaNCTStudySite.setFunctionalCode(CdConverter.convertToCd(
                        StudySiteFunctionalCode.IDENTIFIER_ASSIGNER));

                criteriaNCTStudySite.setResearchOrganizationIi(nctROIi);
                StudySiteDTO ssNctIdDto = PAUtil.getFirstObj(paServiceUtil.getStudySite(criteriaNCTStudySite,
                        true));
                if (ssNctIdDto != null) {
                    isoDto = ssNctIdDto;
                }
            }
            isoDto.setResearchOrganizationIi(nctROIi);
            isoDto.setLocalStudyProtocolIdentifier(StConverter.convertToSt(trialDTO.getNctIdentifier()));
        }
        return isoDto;
    }

    /**
     * Convert to study contact dto.
     * @param trialDTO dto
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
       if (PAUtil.isNotEmpty(trialDTO.getContactPhone())) {
            String phone = trialDTO.getContactPhone().trim();
            if (PAUtil.isNotEmpty(trialDTO.getContactPhoneExtn())) {
                 StringBuffer phoneWithExtn = new StringBuffer();
                 phoneWithExtn.append(phone).append("extn").append(trialDTO.getContactPhoneExtn());
                 phone = phoneWithExtn.toString();
            }
            phones.add(phone);
       }
       List<String> emails = new ArrayList<String>();
       if (PAUtil.isNotEmpty(trialDTO.getContactEmail())) {
            emails.add(trialDTO.getContactEmail());
       }
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
               isoDTO.setStudyProtocolIdentifier(IiConverter.convertToStudyProtocolIi(Long.valueOf(
                       dto.getStudyProtocolId())));
           }
           if (PAUtil.isNotEmpty(dto.getId())) {
               isoDTO.setIdentifier(IiConverter.convertToDocumentIi(Long.valueOf((dto.getId()))));
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
       List<DocumentDTO> docs = PaRegistry.getDocumentService().
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
       List<byte[]> parts = new LinkedList<byte[]>();
       InputStream in = new BufferedInputStream(inputStream);
       byte[] readBuffer = new byte[bufSize];
       byte[] part = null;
       int bytesRead = 0;
       // read everything into a list of byte arrays
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
           if (dto.getExpandedAccess().equalsIgnoreCase(CommonsConstant.YES)) {
               isoDTO.setExpandedAccessIndicator(BlConverter.convertToBl(Boolean.TRUE));
           } else {
               isoDTO.setExpandedAccessIndicator(BlConverter.convertToBl(Boolean.FALSE));
           }
           if (!"-".equalsIgnoreCase(dto.getExpandedAccessType())) {
               isoDTO.setExpandedAccessStatusCode(CdConverter.convertStringToCd(dto.getExpandedAccessType()));
           }
           if (PAUtil.isNotEmpty(dto.getStudyProtocolId())) {
               isoDTO.setStudyProtocolIdentifier(IiConverter.convertToStudyProtocolIi(Long.valueOf(
                       dto.getStudyProtocolId())));
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
           if (dto.getExpandedAccess().equalsIgnoreCase(CommonsConstant.YES)) {
               isoDTO.setExpandedAccessIndicator(BlConverter.convertToBl(Boolean.TRUE));
           } else {
               isoDTO.setExpandedAccessIndicator(BlConverter.convertToBl(Boolean.FALSE));
           }
           if (!"-".equalsIgnoreCase(dto.getExpandedAccessType())) {
               isoDTO.setExpandedAccessStatusCode(CdConverter.convertStringToCd(dto.getExpandedAccessType()));
           }
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
               isoDTO.setStudyProtocolIdentifier(IiConverter.convertToStudyProtocolIi(Long.valueOf(
                       dto.getStudyProtocolId())));
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
       StudyProtocolDTO spDTO = PaRegistry.getStudyProtocolService().getStudyProtocol(studyProtocolIi);
       StudyProtocolQueryDTO spqDto = PaRegistry.getProtocolQueryService().getTrialSummaryByStudyProtocolId(
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
       copySummaryFour(PaRegistry.getStudyResourcingService().
                   getsummary4ReportedResource(studyProtocolIi), trialDTO);
       //Copy IND's
       List<StudyIndldeDTO> studyIndldeDTOList = PaRegistry.getStudyIndldeService()
           .getByStudyProtocol(studyProtocolIi);
       if (!(studyIndldeDTOList.isEmpty())) {
            copyINDIDEList(studyIndldeDTOList, trialDTO);
       }
       // query the study grants
       List<StudyResourcingDTO> isoList = PaRegistry.getStudyResourcingService()
                   .getstudyResourceByStudyProtocol(studyProtocolIi);
       if (!(isoList.isEmpty())) {
           copyGrantList(isoList, trialDTO);
       }
       //copy the reason for trial
       StudyOverallStatusDTO sosDto = null;
       sosDto = PaRegistry.getStudyOverallStatusService().getCurrentByStudyProtocol(studyProtocolIi);
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
       List<StudyIndldeDTO> studyIndldeUpdateDTOList = PaRegistry.getStudyIndldeService()
           .getByStudyProtocol(studyProtocolIi);
       if (!(studyIndldeUpdateDTOList.isEmpty())) {
            copyINDIDEUpdateList(studyIndldeUpdateDTOList, trialDTO);
       }
       //copy Dcp
       copyDcpIdentifier(studyProtocolIi, trialDTO);
       //copy ctep
       copyCtepIdentifier(studyProtocolIi, trialDTO);
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
       List<StudySiteDTO> spList = PaRegistry.getStudySiteService()
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
       List<StudySiteDTO> spList = PaRegistry.getStudySiteService()
                                               .getByStudyProtocol(studyProtocolIi, srDTO);
       for (StudySiteDTO sp : spList) {
           StudySiteAccrualStatusDTO ssas = PaRegistry.getStudySiteAccrualStatusService()
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
       StudyRegulatoryAuthorityDTO authorityDTO = PaRegistry.getStudyRegulatoryAuthorityService()
           .getCurrentByStudyProtocol(studyProtocolIi);
       trialDTO.setCountryList(PaRegistry.getRegulatoryInformationService().getDistinctCountryNames());
       if (authorityDTO != null) { // load values from database
           StudyProtocolDTO spDTO = PaRegistry.getStudyProtocolService().getStudyProtocol(studyProtocolIi);
           if (spDTO.getSection801Indicator().getValue() != null) {
               trialDTO.setSection801Indicator(BlConverter.convertBLToString(spDTO.getSection801Indicator()));
           }
           if (spDTO.getFdaRegulatedIndicator().getValue() != null) {
               trialDTO.setFdaRegulatoryInformationIndicator(BlConverter.convertBLToString(spDTO
                                   .getFdaRegulatedIndicator()));
           }
           if (spDTO.getDelayedpostingIndicator().getValue() != null) {
               trialDTO.setDelayedPostingIndicator(BlConverter.convertBLToString(spDTO.getDelayedpostingIndicator()));
           }
           if (spDTO.getDataMonitoringCommitteeAppointedIndicator().getValue() != null) {
               trialDTO.setDataMonitoringCommitteeAppointedIndicator((BlConverter.convertBLToString(spDTO
                               .getDataMonitoringCommitteeAppointedIndicator())));
           }
           StudyRegulatoryAuthorityDTO sraFromDatabaseDTO = PaRegistry.getStudyRegulatoryAuthorityService()
                             .getCurrentByStudyProtocol(studyProtocolIi);
           if (sraFromDatabaseDTO != null) {
               Long sraId = Long.valueOf(sraFromDatabaseDTO.getRegulatoryAuthorityIdentifier().getExtension());
               List<Long> regInfo = PaRegistry.getRegulatoryInformationService().getRegulatoryAuthorityInfo(sraId);
               trialDTO.setLst(regInfo.get(1).toString());
               //set selected the name of the regulatory authority chosen
               trialDTO.setRegIdAuthOrgList(PaRegistry.getRegulatoryInformationService().
                                                getRegulatoryAuthorityNameId(Long.valueOf(regInfo.get(1).toString())));
               trialDTO.setSelectedRegAuth(regInfo.get(0).toString());
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
   public InterventionalStudyProtocolDTO convertToInterventionalStudyProtocolDTO(BaseTrialDTO trialDTO)
       throws PAException {
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
    /**
     * Gets the study reg auth.
     * @param studyProtocolIi the study protocol ii
     * @param trialDTO trialDTO
     * @return the study reg auth
     * @throws PAException the PA exception
     */
    public StudyRegulatoryAuthorityDTO getStudyRegAuth(Ii studyProtocolIi, TrialDTO trialDTO) throws PAException {

        StudyRegulatoryAuthorityDTO sraFromDatabaseDTO = null;
        StudyRegulatoryAuthorityDTO sraDTO = new StudyRegulatoryAuthorityDTO();
        if (PAUtil.isIiNotNull(studyProtocolIi)) {
            sraFromDatabaseDTO = PaRegistry.getStudyRegulatoryAuthorityService().getCurrentByStudyProtocol(
                    studyProtocolIi);
            sraDTO.setStudyProtocolIdentifier(studyProtocolIi);
        }
        if (PAUtil.isEmpty(trialDTO.getSelectedRegAuth())) {
            return sraFromDatabaseDTO;
        }
        if (sraFromDatabaseDTO == null) {
            sraDTO.setRegulatoryAuthorityIdentifier(IiConverter.convertToIi(trialDTO.getSelectedRegAuth()));
            return sraDTO;
        }
        sraFromDatabaseDTO.setRegulatoryAuthorityIdentifier(IiConverter.convertToIi(trialDTO.getSelectedRegAuth()));
        return sraFromDatabaseDTO;
    }

    /**
     * @param trialDTO trialDTO
     */
    @SuppressWarnings({"PMD" })
    public void populateRegulatoryList(TrialDTO trialDTO) {
        try {
            trialDTO.setCountryList(PaRegistry.getRegulatoryInformationService().getDistinctCountryNames());
            if (PAUtil.isNotEmpty(trialDTO.getLst()) &&  PAUtil.isNumber(trialDTO.getLst())) {
                trialDTO.setRegIdAuthOrgList(PaRegistry.getRegulatoryInformationService().getRegulatoryAuthorityNameId(
                        Long.valueOf(trialDTO.getLst())));
            }
        } catch (PAException e) {
            //do nothing
        }
    }

    /**
     * Convert to Ctep study site dto.
     * @param trialDTO dto
     * @param studyProtocolIi Ii
     * @return iso
     * @throws PAException e
     */
    public StudySiteDTO convertToCTEPStudySiteDTO(TrialDTO trialDTO, Ii studyProtocolIi) throws PAException {
        StudySiteDTO isoDto = new StudySiteDTO();
        Ii ctepROIi = null;
        if (PAUtil.isNotEmpty(trialDTO.getCtepIdentifier())) {
            String poOrgId = PaRegistry.getOrganizationCorrelationService().getPOOrgIdentifierByIdentifierType(
                    PAConstants.CTEP_IDENTIFIER_TYPE);
            ctepROIi = PaRegistry.getOrganizationCorrelationService().getPoResearchOrganizationByEntityIdentifier(
                    IiConverter.convertToPoOrganizationIi(String.valueOf(poOrgId)));
            if (PAUtil.isIiNotNull(studyProtocolIi)) {
                //find if the CTEP Identifier is there
                StudySiteDTO criteriaCTEPStudySite = new StudySiteDTO();
                criteriaCTEPStudySite.setStudyProtocolIdentifier(studyProtocolIi);
                criteriaCTEPStudySite.setFunctionalCode(CdConverter.convertToCd(
                        StudySiteFunctionalCode.IDENTIFIER_ASSIGNER));

                criteriaCTEPStudySite.setResearchOrganizationIi(ctepROIi);
                StudySiteDTO ssCTEPIdDto = PAUtil.getFirstObj(paServiceUtil.getStudySite(criteriaCTEPStudySite,
                        true));
                if (ssCTEPIdDto != null) {
                    isoDto = ssCTEPIdDto;
                }
            }
            isoDto.setResearchOrganizationIi(ctepROIi);
            isoDto.setLocalStudyProtocolIdentifier(StConverter.convertToSt(trialDTO.getCtepIdentifier()));

        }
        return isoDto;
    }
    /**
     * Convert to Dcp study site dto.
     * @param trialDTO dto
     * @param studyProtocolIi Ii
     * @return iso
     * @throws PAException e
     */
    public StudySiteDTO convertToDCPStudySiteDTO(TrialDTO trialDTO, Ii studyProtocolIi) throws PAException {
        StudySiteDTO isoDto = new StudySiteDTO();
        if (PAUtil.isNotEmpty(trialDTO.getDcpIdentifier())) {
            Ii dcpRoIi = null;
            String poOrgId = PaRegistry.getOrganizationCorrelationService().getPOOrgIdentifierByIdentifierType(
                    PAConstants.DCP_IDENTIFIER_TYPE);
            dcpRoIi =  PaRegistry.getOrganizationCorrelationService()
            .getPoResearchOrganizationByEntityIdentifier(IiConverter.convertToPoOrganizationIi(
                    String.valueOf(poOrgId)));
            if (PAUtil.isIiNotNull(studyProtocolIi)) {
                //find if the DCP Identifier is there
                StudySiteDTO criteriaDCPStudySite = new StudySiteDTO();
                criteriaDCPStudySite.setStudyProtocolIdentifier(studyProtocolIi);
                criteriaDCPStudySite.setFunctionalCode(CdConverter.convertToCd(
                        StudySiteFunctionalCode.IDENTIFIER_ASSIGNER));
                criteriaDCPStudySite.setResearchOrganizationIi(dcpRoIi);
                StudySiteDTO ssDcpIdDto = PAUtil.getFirstObj(paServiceUtil.getStudySite(criteriaDCPStudySite,
                        true));
                if (ssDcpIdDto != null) {
                    isoDto = ssDcpIdDto;
                }
            }
            isoDto.setResearchOrganizationIi(dcpRoIi);
            isoDto.setLocalStudyProtocolIdentifier(StConverter.convertToSt(trialDTO.getDcpIdentifier()));

        }
        return isoDto;
    }
    /**
     * Copy dcp nummber.
     * @param studyProtocolIi ii
     * @param trialDTO dto
     * @throws PAException ex
     */
    private void copyDcpIdentifier(Ii studyProtocolIi, TrialDTO trialDTO) throws PAException {
        String dcpId = paServiceUtil.getStudyIdentifier(studyProtocolIi, PAConstants.DCP_IDENTIFIER_TYPE);
        if (PAUtil.isNotEmpty(dcpId)) {
            trialDTO.setDcpIdentifier(dcpId);
        }
    }
    /**
     * Copy dcp nummber.
     *
     * @param studyProtocolIi ii
     * @param trialDTO dto
     *
     * @throws PAException ex
     */
    private void copyCtepIdentifier(Ii studyProtocolIi, TrialDTO trialDTO) throws PAException {
        String ctepId = paServiceUtil.getStudyIdentifier(studyProtocolIi, PAConstants.CTEP_IDENTIFIER_TYPE);
        if (PAUtil.isNotEmpty(ctepId)) {
            trialDTO.setCtepIdentifier(ctepId);
        }
    }
    /**
     *
     * @param trialDto dot
     * @return isoDto
     */
    public StudyProtocolStageDTO convertToStudyProtocolStageDTO(BaseTrialDTO trialDto) {
        StudyProtocolStageDTO spStageDTO = new StudyProtocolStageDTO();
        spStageDTO.setIdentifier(IiConverter.convertToIi(trialDto.getStudyProtocolId()));
        spStageDTO.setNctIdentifier(StConverter.convertToSt(trialDto.getNctIdentifier()));
        spStageDTO.setOfficialTitle(StConverter.convertToSt(trialDto.getOfficialTitle()));
        spStageDTO.setPhaseCode(CdConverter.convertToCd(PhaseCode.getByCode(trialDto.getPhaseCode())));
        spStageDTO.setPhaseOtherText(StConverter.convertToSt(trialDto.getPhaseOtherText()));
        spStageDTO.setPrimaryPurposeCode(CdConverter.convertToCd(
                PrimaryPurposeCode.getByCode(trialDto.getPrimaryPurposeCode())));
        spStageDTO.setPrimaryPurposeOtherText(StConverter.convertToSt(trialDto.getPrimaryPurposeOtherText()));
        spStageDTO.setLocalProtocolIdentifier(StConverter.convertToSt(trialDto.getLeadOrgTrialIdentifier()));
        spStageDTO.setLeadOrganizationIdentifier(IiConverter.convertToIi(trialDto.getLeadOrganizationIdentifier()));
        spStageDTO.setSummaryFourOrgIdentifier(IiConverter.convertToIi(trialDto.getSummaryFourOrgIdentifier()));
        spStageDTO.setSummaryFourFundingCategoryCode(CdConverter.convertToCd(SummaryFourFundingCategoryCode.getByCode(
                trialDto.getSummaryFourFundingCategoryCode())));
        spStageDTO.setTrialType(StConverter.convertToSt(trialDto.getTrialType()));
        if (trialDto instanceof TrialDTO) {
            convertNonPropDtoToStage((TrialDTO) trialDto, spStageDTO);
        } else if (trialDto instanceof ProprietaryTrialDTO) {
            convertPropDtoToStage((ProprietaryTrialDTO) trialDto, spStageDTO);
        }
        spStageDTO.setUserLastCreated(StConverter.convertToSt(ServletActionContext
                .getRequest().getRemoteUser()));
        return spStageDTO;
    }

    private void convertPropDtoToStage(ProprietaryTrialDTO trialDto,
            StudyProtocolStageDTO spStageDTO) {
        spStageDTO.setSubmitterOrganizationIdentifier(IiConverter.convertToIi(
                trialDto.getSiteOrganizationIdentifier()));
        spStageDTO.setSiteProtocolIdentifier(IiConverter.convertToIi(trialDto.getLocalSiteIdentifier()));
        spStageDTO.setSitePiIdentifier(IiConverter.convertToIi(trialDto.getSitePiIdentifier()));
        spStageDTO.setSiteSummaryFourOrgIdentifier(IiConverter.convertToIi(trialDto.getSummaryFourOrgIdentifier()));
        spStageDTO.setSiteSummaryFourFundingTypeCode(CdConverter.convertStringToCd(
                trialDto.getSummaryFourFundingCategoryCode()));
        spStageDTO.setSiteProgramCodeText(StConverter.convertToSt(trialDto.getSiteProgramCodeText()));
        spStageDTO.setSiteRecruitmentStatus(CdConverter.convertStringToCd(trialDto.getSiteStatusCode()));
        spStageDTO.setSiteRecruitmentStatusDate(TsConverter.convertToTs(PAUtil.dateStringToTimestamp(
                trialDto.getSiteStatusDate())));
        spStageDTO.setOpendedForAccrualDate(TsConverter.convertToTs(PAUtil.dateStringToTimestamp(
                trialDto.getDateOpenedforAccrual())));
        spStageDTO.setClosedForAccrualDate(TsConverter.convertToTs(PAUtil.dateStringToTimestamp(
                trialDto.getDateClosedforAccrual())));
        spStageDTO.setProprietaryTrialIndicator(BlConverter.convertToBl(Boolean.TRUE));
        trialDto.setPropritaryTrialIndicator(CommonsConstant.YES);
    }
    /**
     * @param trialDto
     * @param spStageDTO
     */
    private void convertNonPropDtoToStage(TrialDTO trialDto,
            StudyProtocolStageDTO spStageDTO) {
        spStageDTO.setPiIdentifier(IiConverter.convertToIi(trialDto.getPiIdentifier()));
        spStageDTO.setSponsorIdentifier(IiConverter.convertToIi(trialDto.getSponsorIdentifier()));
        spStageDTO.setResponsiblePartyType(StConverter.convertToSt(trialDto.getResponsiblePartyType()));
        spStageDTO.setResponsibleIdentifier(IiConverter.convertToIi(trialDto.getResponsiblePersonIdentifier()));
        spStageDTO.setContactEmail(StConverter.convertToSt(trialDto.getContactEmail()));
        spStageDTO.setContactPhone(StConverter.convertToSt(trialDto.getContactPhone()));
        spStageDTO.setProgramCodeText(StConverter.convertToSt(trialDto.getProgramCodeText()));
        spStageDTO.setTrialStatusCode(CdConverter.convertToCd(StudyStatusCode.getByCode(trialDto.getStatusCode())));
        spStageDTO.setTrialStatusDate(TsConverter.convertToTs(PAUtil.dateStringToTimestamp(trialDto.getStatusDate())));
        spStageDTO.setStatusReason(StConverter.convertToSt(trialDto.getReason()));
        spStageDTO.setPrimaryCompletionDate(TsConverter.convertToTs(PAUtil.dateStringToTimestamp(
                trialDto.getCompletionDate())));
        spStageDTO.setPrimaryCompletionDateTypeCode(CdConverter.convertToCd(
                ActualAnticipatedTypeCode.getByCode(trialDto.getCompletionDateType())));
        spStageDTO.setStartDate(TsConverter.convertToTs(PAUtil.dateStringToTimestamp(trialDto.getStartDate())));
        spStageDTO.setStartDateTypeCode(CdConverter.convertToCd(
                ActualAnticipatedTypeCode.getByCode(trialDto.getStartDateType())));

        if (trialDto.getFdaRegulatoryInformationIndicator() == null) {
            spStageDTO.setFdaRegulatedIndicator(BlConverter.convertToBl(null));
        } else if  (CommonsConstant.YES.equalsIgnoreCase(trialDto.getFdaRegulatoryInformationIndicator())) {
            spStageDTO.setFdaRegulatedIndicator(BlConverter.convertToBl(Boolean.TRUE));
        } else {
            spStageDTO.setFdaRegulatedIndicator(BlConverter.convertToBl(Boolean.FALSE));
        }
        if (trialDto.getSection801Indicator() == null) {
            spStageDTO.setSection801Indicator(BlConverter.convertToBl(null));
        } else if (CommonsConstant.YES.equalsIgnoreCase(trialDto.getSection801Indicator())) {
            spStageDTO.setSection801Indicator(BlConverter.convertToBl(Boolean.TRUE));
        } else {
            spStageDTO.setSection801Indicator(BlConverter.convertToBl(Boolean.FALSE));
        }
        if (trialDto.getDelayedPostingIndicator() == null) {
            spStageDTO.setDelayedpostingIndicator(BlConverter.convertToBl(null));
        } else if (CommonsConstant.YES.equalsIgnoreCase(trialDto.getDelayedPostingIndicator())) {
            spStageDTO.setDelayedpostingIndicator(BlConverter.convertToBl(Boolean.TRUE));
        } else {
            spStageDTO.setDelayedpostingIndicator(BlConverter.convertToBl(Boolean.FALSE));
        }
        if (trialDto.getDataMonitoringCommitteeAppointedIndicator() == null) {
            spStageDTO.setDataMonitoringCommitteeAppointedIndicator(BlConverter.convertToBl(null));
        } else if (CommonsConstant.YES.equalsIgnoreCase(trialDto.getDataMonitoringCommitteeAppointedIndicator())) {
            spStageDTO.setDataMonitoringCommitteeAppointedIndicator(BlConverter.convertToBl(Boolean.TRUE));
        } else {
            spStageDTO.setDataMonitoringCommitteeAppointedIndicator(BlConverter.convertToBl(Boolean.FALSE));
        }
        spStageDTO.setOversightAuthorityCountryId(IiConverter.convertToIi(trialDto.getLst()));
        spStageDTO.setOversightAuthorityOrgId(IiConverter.convertToIi(trialDto.getSelectedRegAuth()));
        spStageDTO.setProprietaryTrialIndicator(BlConverter.convertToBl(Boolean.FALSE));
        trialDto.setPropritaryTrialIndicator(CommonsConstant.NO);
        spStageDTO.setCtgovXmlRequiredIndicator(trialDto.getXmlRequired()
                ? BlConverter.convertToBl(Boolean.TRUE) : BlConverter.convertToBl(Boolean.FALSE));

    }
    /**
     * @param spStageDTO isoDto
     * @return webDto
     * @throws NullifiedRoleException on err
     * @throws PAException on err
     */
    public BaseTrialDTO convertToTrialDTO(StudyProtocolStageDTO spStageDTO)
        throws NullifiedRoleException, PAException {
        BaseTrialDTO trialDto = new BaseTrialDTO();
        if (!PAUtil.isBlNull(spStageDTO.getProprietaryTrialIndicator())
                && BlConverter.covertToBoolean(spStageDTO.getProprietaryTrialIndicator())) {
            trialDto = convertToPropTrialDTO(spStageDTO);
        } else {
            trialDto = convertToNonPropTrialDTO(spStageDTO);
        }
        trialDto.setStudyProtocolId(IiConverter.convertToString(spStageDTO.getIdentifier()));
        trialDto.setNctIdentifier(StConverter.convertToString(spStageDTO.getNctIdentifier()));
        trialDto.setOfficialTitle(StConverter.convertToString(spStageDTO.getOfficialTitle()));
        if (!PAUtil.isCdNull(spStageDTO.getPhaseCode())) {
            trialDto.setPhaseCode(PhaseCode.getByCode(
                    CdConverter.convertCdToString(spStageDTO.getPhaseCode())).getCode());
        }
        trialDto.setPhaseOtherText(StConverter.convertToString(spStageDTO.getPhaseOtherText()));
        if (!PAUtil.isCdNull(spStageDTO.getPrimaryPurposeCode())) {
            trialDto.setPrimaryPurposeCode(PrimaryPurposeCode.getByCode(CdConverter.convertCdToString(
                spStageDTO.getPrimaryPurposeCode())).getCode());
        }
        trialDto.setPrimaryPurposeOtherText(StConverter.convertToString(spStageDTO.getPrimaryPurposeOtherText()));
        trialDto.setLeadOrgTrialIdentifier(StConverter.convertToString(spStageDTO.getLocalProtocolIdentifier()));
        trialDto.setLeadOrganizationIdentifier(IiConverter.convertToString(spStageDTO.getLeadOrganizationIdentifier()));
        if (PAUtil.isIiNotNull(spStageDTO.getLeadOrganizationIdentifier())) {
            trialDto.setLeadOrganizationName(getOrgName(spStageDTO.getLeadOrganizationIdentifier()));
            if (trialDto.getLeadOrganizationName().equalsIgnoreCase(NULLIFIED_ORGANIZATION)) {
                trialDto.setLeadOrganizationIdentifier(null);
            }
        }
        return trialDto;
    }

    private ProprietaryTrialDTO convertToPropTrialDTO(StudyProtocolStageDTO spStageDTO) {
        ProprietaryTrialDTO trialDto = new ProprietaryTrialDTO();
        trialDto.setSiteOrganizationIdentifier(IiConverter.convertToString(
                spStageDTO.getSubmitterOrganizationIdentifier()));
        if (PAUtil.isIiNotNull(spStageDTO.getSubmitterOrganizationIdentifier())) {
           trialDto.setSiteOrganizationName(getOrgName(spStageDTO.getSubmitterOrganizationIdentifier()));
           if (trialDto.getSiteOrganizationName().equalsIgnoreCase(NULLIFIED_ORGANIZATION)) {
               trialDto.setSiteOrganizationIdentifier(null);
           }
        }
        trialDto.setLocalSiteIdentifier(IiConverter.convertToString(spStageDTO.getSiteProtocolIdentifier()));
        trialDto.setSitePiIdentifier(IiConverter.convertToString(spStageDTO.getSitePiIdentifier()));
        if (PAUtil.isIiNotNull(spStageDTO.getSitePiIdentifier())) {
            trialDto.setSitePiName(getPersonName(spStageDTO.getSitePiIdentifier()));
            if (trialDto.getSitePiName().equalsIgnoreCase(NULLIFIED_PERSON)) {
                trialDto.setSitePiIdentifier(null);
            }
        }
        trialDto.setSiteProgramCodeText(StConverter.convertToString(spStageDTO.getSiteProgramCodeText()));
        trialDto.setSummaryFourOrgIdentifier(IiConverter.convertToString(
                spStageDTO.getSiteSummaryFourOrgIdentifier()));
        if (PAUtil.isIiNotNull(spStageDTO.getSiteSummaryFourOrgIdentifier())) {
            trialDto.setSummaryFourOrgName(getOrgName(spStageDTO.getSiteSummaryFourOrgIdentifier()));
            if (trialDto.getSummaryFourOrgName().equalsIgnoreCase(NULLIFIED_ORGANIZATION)) {
                trialDto.setSummaryFourOrgIdentifier(null);
            }
        }
        trialDto.setSummaryFourFundingCategoryCode(CdConverter.convertCdToString(
                   spStageDTO.getSiteSummaryFourFundingTypeCode()));
        if (!PAUtil.isCdNull(spStageDTO.getSiteRecruitmentStatus())) {
            trialDto.setSiteStatusCode(CdConverter.convertCdToString(spStageDTO.getSiteRecruitmentStatus()));
        }
        trialDto.setSiteStatusDate(TsConverter.convertToString(spStageDTO.getSiteRecruitmentStatusDate()));
        trialDto.setDateOpenedforAccrual(TsConverter.convertToString(spStageDTO.getOpendedForAccrualDate()));
        trialDto.setDateClosedforAccrual(TsConverter.convertToString(spStageDTO.getClosedForAccrualDate()));
        trialDto.setPropritaryTrialIndicator(CommonsConstant.YES);
        return trialDto;
    }

    /**
     * @param spStageDTO
     * @param trialDto
     * @throws PAException
     */
    private TrialDTO convertToNonPropTrialDTO(StudyProtocolStageDTO spStageDTO) throws PAException {
        TrialDTO trialDto = new TrialDTO();
        trialDto.setPiIdentifier(IiConverter.convertToString(spStageDTO.getPiIdentifier()));
        trialDto.setSponsorIdentifier(IiConverter.convertToString(spStageDTO.getSponsorIdentifier()));
        trialDto.setResponsiblePartyType(StConverter.convertToString(spStageDTO.getResponsiblePartyType()));

        trialDto.setResponsiblePersonIdentifier(IiConverter.convertToString(spStageDTO.getResponsibleIdentifier()));
        trialDto.setContactEmail(StConverter.convertToString(spStageDTO.getContactEmail()));
        trialDto.setContactPhone(StConverter.convertToString(spStageDTO.getContactPhone()));
        trialDto.setProgramCodeText(StConverter.convertToString(spStageDTO.getProgramCodeText()));
        if (!PAUtil.isCdNull(spStageDTO.getTrialStatusCode())) {
            trialDto.setStatusCode(StudyStatusCode.getByCode(CdConverter.convertCdToString(
                    spStageDTO.getTrialStatusCode())).getCode());
        }
        trialDto.setStatusDate(TsConverter.convertToString(spStageDTO.getTrialStatusDate()));
        trialDto.setReason(StConverter.convertToString(spStageDTO.getStatusReason()));
        trialDto.setCompletionDate(TsConverter.convertToString(
                spStageDTO.getPrimaryCompletionDate()));
        trialDto.setCompletionDateType(CdConverter.convertCdToString(
                spStageDTO.getPrimaryCompletionDateTypeCode()));
        trialDto.setStartDate(TsConverter.convertToString(spStageDTO.getStartDate()));
        trialDto.setStartDateType(CdConverter.convertCdToString(spStageDTO.getStartDateTypeCode()));
        trialDto.setTrialType(StConverter.convertToString(spStageDTO.getTrialType()));

        trialDto.setLst(IiConverter.convertToString(spStageDTO.getOversightAuthorityCountryId()));
        trialDto.setSelectedRegAuth(IiConverter.convertToString(spStageDTO.getOversightAuthorityOrgId()));

        Boolean fdaRegIndicator  = BlConverter.covertToBoolean(spStageDTO.getFdaRegulatedIndicator());
        if (fdaRegIndicator == null) {
            trialDto.setFdaRegulatoryInformationIndicator("");
        } else if (fdaRegIndicator) {
            trialDto.setFdaRegulatoryInformationIndicator(CommonsConstant.YES);
        } else {
            trialDto.setFdaRegulatoryInformationIndicator(CommonsConstant.NO);
        }
        Boolean sec801Indicator = BlConverter.covertToBoolean(spStageDTO.getSection801Indicator());
        if (sec801Indicator == null) {
            trialDto.setSection801Indicator("");
        } else if (sec801Indicator) {
            trialDto.setSection801Indicator(CommonsConstant.YES);
        } else {
            trialDto.setSection801Indicator(CommonsConstant.NO);
        }
        Boolean delayedPostIndicator  = BlConverter.covertToBoolean(spStageDTO.getDelayedpostingIndicator());
        if (delayedPostIndicator == null) {
            trialDto.setDelayedPostingIndicator("");
        } else if (delayedPostIndicator) {
            trialDto.setDelayedPostingIndicator(CommonsConstant.YES);
        } else {
            trialDto.setDelayedPostingIndicator(CommonsConstant.NO);
        }
        Boolean dataMonitoringIndicator = BlConverter.covertToBoolean(
                spStageDTO.getDataMonitoringCommitteeAppointedIndicator());
        if (dataMonitoringIndicator == null) {
            trialDto.setDataMonitoringCommitteeAppointedIndicator("");
        } else if (dataMonitoringIndicator) {
            trialDto.setDataMonitoringCommitteeAppointedIndicator(CommonsConstant.YES);
        } else {
            trialDto.setDataMonitoringCommitteeAppointedIndicator(CommonsConstant.NO);
        }
        if (PAUtil.isIiNotNull(spStageDTO.getSummaryFourOrgIdentifier())) {
            trialDto.setSummaryFourOrgName(getOrgName(spStageDTO.getSummaryFourOrgIdentifier()));
            if (trialDto.getSummaryFourOrgName().equalsIgnoreCase(NULLIFIED_ORGANIZATION)) {
                trialDto.setSummaryFourOrgIdentifier(null);
            }
        }
        if (PAUtil.isIiNotNull(spStageDTO.getPiIdentifier())) {
            trialDto.setPiName(getPersonName(spStageDTO.getPiIdentifier()));
            if (trialDto.getPiName().equalsIgnoreCase(NULLIFIED_PERSON)) {
                trialDto.setPiIdentifier(null);
            }
        }
        if (PAUtil.isIiNotNull(spStageDTO.getSponsorIdentifier())) {
            trialDto.setSponsorName(getOrgName(spStageDTO.getSponsorIdentifier()));
            if (trialDto.getSponsorName().equalsIgnoreCase(NULLIFIED_ORGANIZATION)) {
               trialDto.setSponsorIdentifier(null);
            }
        }
        if (PAUtil.isIiNotNull(spStageDTO.getResponsibleIdentifier())) {
            PersonDTO perDto = paServiceUtil.getPoPersonEntity(IiConverter.convertToPoPersonIi(
                    spStageDTO.getResponsibleIdentifier().getExtension()));
            if (perDto != null) {
                trialDto.setResponsiblePersonName(PADomainUtils.convertToPaPersonDTO(perDto).getFullName());
            } else {
               OrganizationalContactDTO orgContactDTO = (OrganizationalContactDTO) paServiceUtil.getCorrelationByIi(
                 IiConverter.convertToPoOrganizationalContactIi(spStageDTO.getResponsibleIdentifier().getExtension()));
                 if (orgContactDTO != null) {
                     trialDto.setResponsibleGenericContactName(StConverter.convertToString(orgContactDTO.getTitle()));
                 }
            }
            if (StringUtils.isEmpty(trialDto.getResponsibleGenericContactName())
                 && StringUtils.isEmpty(trialDto.getResponsiblePersonName())) {
                   trialDto.setResponsiblePersonIdentifier(null);
            }
        }
        trialDto.setSummaryFourOrgIdentifier(IiConverter.convertToString(spStageDTO.getSummaryFourOrgIdentifier()));
        trialDto.setSummaryFourFundingCategoryCode(CdConverter.convertCdToString(
                spStageDTO.getSummaryFourFundingCategoryCode()));
        trialDto.setPropritaryTrialIndicator(CommonsConstant.NO);
        if (PAUtil.isBlNull(spStageDTO.getCtgovXmlRequiredIndicator())
                || spStageDTO.getCtgovXmlRequiredIndicator().getValue().booleanValue()) {
                 trialDto.setXmlRequired(true);
        } else {
               trialDto.setXmlRequired(false);
        }
        return trialDto;
    }
    /**
     * @param grant webDto
     * @return isoDto
     */
    public StudyFundingStageDTO convertToStudyFundingStage(TrialFundingWebDTO grant) {
        StudyFundingStageDTO tempFundingDTO = new StudyFundingStageDTO();
        tempFundingDTO.setFundingMechanismCode(CdConverter.convertStringToCd(grant.getFundingMechanismCode()));
        tempFundingDTO.setNciDivisionProgramCode(CdConverter.convertStringToCd(grant.getNciDivisionProgramCode()));
        tempFundingDTO.setNihInstitutionCode(CdConverter.convertStringToCd(grant.getNihInstitutionCode()));
        tempFundingDTO.setSerialNumber(StConverter.convertToSt(grant.getSerialNumber()));
        return tempFundingDTO;
    }
    /**
     * @param isoDTO isoDto
     * @return webDto
     */
    public TrialFundingWebDTO  convertToTrialFundingWebDTO(StudyFundingStageDTO isoDTO) {
        TrialFundingWebDTO  retDTO = new TrialFundingWebDTO();
        retDTO.setFundingMechanismCode(CdConverter.convertCdToString(isoDTO.getFundingMechanismCode()));
        retDTO.setNciDivisionProgramCode(CdConverter.convertCdToString(isoDTO.getNciDivisionProgramCode()));
        retDTO.setNihInstitutionCode(CdConverter.convertCdToString(isoDTO.getNihInstitutionCode()));
        retDTO.setSerialNumber(StConverter.convertToString(isoDTO.getSerialNumber()));
        retDTO.setRowId(UUID.randomUUID().toString());
        return retDTO;
    }
    /**
     * @param indDto indIdedto
     * @return tempStudyIndIdeDto
     */
    public StudyIndIdeStageDTO convertToStudyIndIdeStage(TrialIndIdeDTO indDto) {

        StudyIndIdeStageDTO isoDTO = new StudyIndIdeStageDTO();
        isoDTO.setIndldeTypeCode(CdConverter.convertStringToCd(indDto.getIndIde()));
        isoDTO.setIndldeNumber(StConverter.convertToSt(indDto.getNumber()));
        isoDTO.setGrantorCode(CdConverter.convertStringToCd(indDto.getGrantor()));
        isoDTO.setHolderTypeCode(CdConverter.convertStringToCd(indDto.getHolderType()));
        if (indDto.getHolderType().equalsIgnoreCase("NIH")) {
            isoDTO.setNihInstHolderCode(CdConverter.convertStringToCd(indDto.getProgramCode()));
        }
        if (indDto.getHolderType().equalsIgnoreCase("NCI")) {
            isoDTO.setNciDivProgHolderCode(CdConverter.convertStringToCd(indDto.getProgramCode()));
        }
        if (indDto.getExpandedAccess().equalsIgnoreCase(CommonsConstant.YES)) {
            isoDTO.setExpandedAccessIndicator(BlConverter.convertToBl(Boolean.TRUE));
        } else {
            isoDTO.setExpandedAccessIndicator(BlConverter.convertToBl(Boolean.FALSE));
        }
        if (!"-".equalsIgnoreCase(indDto.getExpandedAccessType())) {
            isoDTO.setExpandedAccessStatusCode(CdConverter.convertStringToCd(indDto.getExpandedAccessType()));
        }
        return isoDTO;
    }
    /**
     *
     * @param isoDto isoDto
     * @return webDto
     */
    public TrialIndIdeDTO convertToTrialIndIdeDTO(StudyIndIdeStageDTO isoDto) {
        TrialIndIdeDTO webDto = new TrialIndIdeDTO();
        webDto.setExpandedAccessType(CdConverter.convertCdToString(isoDto.getExpandedAccessStatusCode()));
        if (isoDto.getExpandedAccessIndicator().getValue() != null) {
            if (isoDto.getExpandedAccessIndicator().getValue().toString().equalsIgnoreCase("true")) {
                webDto.setExpandedAccess("Yes");
            } else {
                webDto.setExpandedAccess("No");
            }
          }
        webDto.setGrantor(CdConverter.convertCdToString(isoDto.getGrantorCode()));
        webDto.setHolderType(CdConverter.convertCdToString(isoDto.getHolderTypeCode()));
        if (!PAUtil.isCdNull(isoDto.getNihInstHolderCode())) {
            webDto.setProgramCode(CdConverter.convertCdToString(isoDto.getNihInstHolderCode()));
        }
        if (!PAUtil.isCdNull(isoDto.getNciDivProgHolderCode())) {
            webDto.setProgramCode(CdConverter.convertCdToString(isoDto.getNciDivProgHolderCode()));
        }
        webDto.setNumber(StConverter.convertToString(isoDto.getIndldeNumber()));
        webDto.setIndIde(CdConverter.convertCdToString(isoDto.getIndldeTypeCode()));
        webDto.setRowId(UUID.randomUUID().toString());
        return webDto;
    }
    /**
     *
     * @param tempStudyProtocolId ii
     * @return trialDTO
     * @throws NullifiedRoleException on err
     * @throws PAException on err
     */
    public BaseTrialDTO getTrialDTOForPartiallySumbissionById(String tempStudyProtocolId)
        throws NullifiedRoleException,
        PAException {
            BaseTrialDTO trialDTO = new BaseTrialDTO();
            trialDTO =  convertToTrialDTO(PaRegistry.getStudyProtocolStageService()
                    .get(IiConverter.convertToIi(tempStudyProtocolId)));
            List<StudyFundingStageDTO> fundingIsoDtos  = PaRegistry.getStudyProtocolStageService()
                .getGrantsByStudyProtocolStage(IiConverter.convertToIi(trialDTO.getStudyProtocolId()));
            List<TrialFundingWebDTO> webDTOs = new ArrayList<TrialFundingWebDTO>();
            for (StudyFundingStageDTO fundingDto : fundingIsoDtos) {
                webDTOs.add(convertToTrialFundingWebDTO(fundingDto));
            }
            trialDTO.setFundingDtos(webDTOs);
            List<TrialIndIdeDTO> webIndDtos = new ArrayList<TrialIndIdeDTO>();
            List <StudyIndIdeStageDTO> indIdeIsoDtos = PaRegistry.getStudyProtocolStageService().
                getIndIdesByStudyProtocolStage(IiConverter.convertToIi(trialDTO.getStudyProtocolId()));
            for (StudyIndIdeStageDTO isoIndDto : indIdeIsoDtos) {
                webIndDtos.add(convertToTrialIndIdeDTO(isoIndDto));
            }
            trialDTO.setIndIdeDtos(webIndDtos);
            if (PAUtil.isEmpty(trialDTO.getPropritaryTrialIndicator())
                    || trialDTO.getPropritaryTrialIndicator().equalsIgnoreCase(CommonsConstant.NO)) {
                populateRegulatoryList((TrialDTO) trialDTO);
            }
        return trialDTO;
    }
    /**
     *
     * @param trialDTO dto
     * @return dto
     * @throws PAException on err
     */
    @SuppressWarnings("unchecked")
    public BaseTrialDTO saveDraft(BaseTrialDTO trialDTO) throws PAException {
        StringBuffer errMsg =  new StringBuffer();
         //lead org local id and lead org is mandatory
        if (PAUtil.isEmpty(trialDTO.getLeadOrgTrialIdentifier())) {
            errMsg.append("Lead Organization Trial Identifier is required.");
        }
        if (PAUtil.isEmpty(trialDTO.getLeadOrganizationIdentifier())) {
            errMsg.append("Lead Organization is required.");
        }
        if (errMsg.length() > 1) {
            throw new PAException(errMsg.toString());
        }
        Ii tempStudyProtocolIi = null;
        List<TrialFundingWebDTO> grantList = (List<TrialFundingWebDTO>) ServletActionContext.getRequest()
            .getSession().getAttribute(Constants.GRANT_LIST);
        List<TrialIndIdeDTO> indList = (List<TrialIndIdeDTO>) ServletActionContext.getRequest()
            .getSession().getAttribute(Constants.INDIDE_LIST);
        List<StudyFundingStageDTO> fundingDTOS = new ArrayList<StudyFundingStageDTO>();
        List<StudyIndIdeStageDTO> indDTOS = new ArrayList<StudyIndIdeStageDTO>();
        if (PAUtil.isListNotEmpty(grantList)) {
                for (TrialFundingWebDTO fundingDto : grantList) {
                    fundingDTOS.add(convertToStudyFundingStage(fundingDto));
                }
        }
        //inds
        if (PAUtil.isListNotEmpty(indList)) {
            for (TrialIndIdeDTO indDto : indList) {
                indDTOS.add(convertToStudyIndIdeStage(indDto));
            }
        }
        if (PAUtil.isNotEmpty(trialDTO.getStudyProtocolId())) {
            StudyProtocolStageDTO dto = PaRegistry.getStudyProtocolStageService().update(
                    convertToStudyProtocolStageDTO(trialDTO), fundingDTOS, indDTOS);
            tempStudyProtocolIi =  dto.getIdentifier();
        } else {
            tempStudyProtocolIi = PaRegistry.getStudyProtocolStageService().create(
                    convertToStudyProtocolStageDTO(trialDTO), fundingDTOS, indDTOS);
        }
       ServletActionContext.getRequest().getSession().removeAttribute("indIdeList");
       ServletActionContext.getRequest().getSession().removeAttribute("grantList");
       if (indList != null) {
           trialDTO.setIndIdeDtos(indList);
       }
       if (grantList != null) {
           trialDTO.setFundingDtos(grantList);
       }
       if (trialDTO instanceof TrialDTO) {
            setOversgtInfo((TrialDTO) trialDTO);
       }
       trialDTO.setStudyProtocolId(tempStudyProtocolIi.getExtension());
        return trialDTO;
    }
    /**
     *
     * @param entityIi ii
     * @return name
     */
    public String getOrgName(Ii entityIi) {
        OrganizationDTO orgDto = paServiceUtil.getPOOrganizationEntity(
                entityIi);
        if (orgDto == null) {
            return NULLIFIED_ORGANIZATION;
        }
        return EnOnConverter.convertEnOnToString(
                orgDto.getName());
    }
    /**
     * @param entityIi ii
     * @return name
     */
    public String getPersonName(Ii entityIi) {
        PersonDTO perDto = paServiceUtil.getPoPersonEntity(
                entityIi);
        if (perDto == null) {
            return NULLIFIED_PERSON;
        }
        return PADomainUtils.convertToPaPersonDTO(perDto).getFullName();
    }
    /**
     *
     * @param trialDTO dto
     * @throws PAException on err
     */
    public void setOversgtInfo(TrialDTO trialDTO) throws PAException {
       if (trialDTO.getSelectedRegAuth() != null) {
            String orgName = PaRegistry.getRegulatoryInformationService().getCountryOrOrgName(Long.valueOf(
                trialDTO.getSelectedRegAuth()), "RegulatoryAuthority");
            trialDTO.setTrialOversgtAuthOrgName(orgName);
       }
       if (trialDTO.getLst() != null) {
            String countryName = PaRegistry.getRegulatoryInformationService().getCountryOrOrgName(
               Long.valueOf(trialDTO.getLst()), "Country");
            trialDTO.setTrialOversgtAuthCountryName(countryName);
       }
    }
    /**
     * Gets the Proprietary trial dto from db.
     * @param studyProtocolIi the study protocol ii
     * @param trialDTO the trial dto
     * @throws PAException the PA exception
     * @throws NullifiedRoleException the nullified role exception
     */
    public void getProprietaryTrialDTOFromDb(Ii studyProtocolIi, ProprietaryTrialDTO trialDTO)
    throws PAException, NullifiedRoleException {
        StudyProtocolDTO spDTO = PaRegistry.getStudyProtocolService().getStudyProtocol(studyProtocolIi);
        StudyProtocolQueryDTO spqDto = PaRegistry.getProtocolQueryService().getTrialSummaryByStudyProtocolId(
                                                Long.valueOf(studyProtocolIi.getExtension()));
        trialDTO.setOfficialTitle(spDTO.getOfficialTitle().getValue());
        trialDTO.setAssignedIdentifier(PAUtil.getAssignedIdentifierExtension(spDTO));
        trialDTO.setPhaseCode(spDTO.getPhaseCode().getCode());
        trialDTO.setPhaseOtherText(spDTO.getPhaseOtherText().getValue());
        trialDTO.setPrimaryPurposeCode(spDTO.getPrimaryPurposeCode().getCode());
        trialDTO.setPrimaryPurposeOtherText(spDTO.getPrimaryPurposeOtherText().getValue());
        trialDTO.setTrialType(spDTO.getStudyProtocolType().getValue());
        trialDTO.setIdentifier(spDTO.getIdentifier().getExtension());
        trialDTO.setStudyProtocolId(spqDto.getStudyProtocolId().toString());
        trialDTO.setLeadOrgTrialIdentifier(spqDto.getLocalStudyProtocolIdentifier());
        CorrelationUtils cUtils = new CorrelationUtils();
        copyLO(cUtils.getPAOrganizationByIi(
                IiConverter.convertToPaOrganizationIi(spqDto.getLeadOrganizationId())), trialDTO);
        copyNctNummber(studyProtocolIi, trialDTO);
        copySummaryFour(PaRegistry.getStudyResourcingService().
                getsummary4ReportedResource(studyProtocolIi), trialDTO);
    }

    /**
     * Convert to iso document list.
     * @param docList the doc list
     * @param studyProtocolIi the study protocol ii
     * @return the list< document dto>
     * @throws PAException the PA exception
     */
    public List<DocumentDTO> convertToISODocumentList(List<TrialDocumentWebDTO> docList, Ii studyProtocolIi)
    throws PAException {
        List<DocumentDTO> docs = PaRegistry.getDocumentService().getDocumentsByStudyProtocol(studyProtocolIi);
        DocumentDTO protocolDocToUpdate = null;
        DocumentDTO otherDocToUpdate = null;
        List<DocumentDTO> docToUpdateList = new ArrayList<DocumentDTO>();
        if (docList != null && !docList.isEmpty()) {
            for (DocumentDTO doc : docs) {
                if (DocumentTypeCode.PROTOCOL_DOCUMENT.getCode().equals(
                        CdConverter.convertCdToString(doc.getTypeCode()))) {
                    protocolDocToUpdate = doc;
                } else if (DocumentTypeCode.OTHER.getCode().equals(
                        CdConverter.convertCdToString(doc.getTypeCode()))) {
                    otherDocToUpdate = doc;
                }
            }
            for (TrialDocumentWebDTO dto : docList) {
                if (DocumentTypeCode.PROTOCOL_DOCUMENT.getCode().equals(dto.getTypeCode())) {
                    if (protocolDocToUpdate != null) {
                        protocolDocToUpdate.setFileName(StConverter.convertToSt(dto.getFileName()));
                        protocolDocToUpdate.setText(EdConverter.convertToEd(dto.getText()));
                    } else {
                        protocolDocToUpdate = new DocumentDTO();
                        protocolDocToUpdate.setTypeCode(CdConverter.convertStringToCd(dto.getTypeCode()));
                        protocolDocToUpdate.setFileName(StConverter.convertToSt(dto.getFileName()));
                        protocolDocToUpdate.setText(EdConverter.convertToEd(dto.getText()));
                    }
                } else if (DocumentTypeCode.OTHER.getCode().equals(dto.getTypeCode())) {
                    if (otherDocToUpdate != null) {
                        otherDocToUpdate.setFileName(StConverter.convertToSt(dto.getFileName()));
                        otherDocToUpdate.setText(EdConverter.convertToEd(dto.getText()));
                    } else {
                        otherDocToUpdate = new DocumentDTO();
                        otherDocToUpdate.setTypeCode(CdConverter.convertStringToCd(dto.getTypeCode()));
                        otherDocToUpdate.setFileName(StConverter.convertToSt(dto.getFileName()));
                        otherDocToUpdate.setText(EdConverter.convertToEd(dto.getText()));
                    }
                }
            }
            if (protocolDocToUpdate != null && protocolDocToUpdate.getText() != null) {
                docToUpdateList.add(protocolDocToUpdate);
            }
            if (otherDocToUpdate != null && otherDocToUpdate.getText() != null) {
                docToUpdateList.add(otherDocToUpdate);
            }
        }
        return docToUpdateList;
    }
    /**
     * Gets the study site to update.
     * @param ps the ps
     * @return the study site to update
     * @throws PAException the PA exception
     */
    public List<StudySiteDTO> getStudySiteToUpdate(List<SubmittedOrganizationDTO> ps)
    throws PAException {
        List<StudySiteDTO> ssDTO = new ArrayList<StudySiteDTO>();
        for (SubmittedOrganizationDTO dto : ps) {
            StudySiteDTO sp = PaRegistry.getStudySiteService().get(IiConverter.convertToIi(dto.getId()));
            sp.setProgramCodeText(StConverter.convertToSt(dto.getProgramCode()));
            sp.setLocalStudyProtocolIdentifier(StConverter.convertToSt(dto.getSiteLocalTrialIdentifier()));
            if (StringUtils.isNotEmpty(dto.getDateOpenedforAccrual())
                    && StringUtils.isNotEmpty(dto.getDateClosedforAccrual())) {
                sp.setAccrualDateRange(IvlConverter.convertTs().convertToIvl(dto.getDateOpenedforAccrual(),
                        dto.getDateClosedforAccrual()));
            }
            if (StringUtils.isNotEmpty(dto.getDateOpenedforAccrual())
                    && StringUtils.isEmpty(dto.getDateClosedforAccrual())) {
                sp.setAccrualDateRange(IvlConverter.convertTs().convertToIvl(dto.getDateOpenedforAccrual(), null));
            }
            ssDTO.add(sp);
        }
         return ssDTO;
    }
}
