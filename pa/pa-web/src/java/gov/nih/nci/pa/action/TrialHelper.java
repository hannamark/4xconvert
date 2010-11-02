package gov.nih.nci.pa.action;

import gov.nih.nci.coppa.services.LimitOffset;
import gov.nih.nci.coppa.services.TooManyResultsException;
import gov.nih.nci.iso21090.Cd;
import gov.nih.nci.iso21090.DSet;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.iso21090.NullFlavor;
import gov.nih.nci.iso21090.Tel;
import gov.nih.nci.pa.domain.Organization;
import gov.nih.nci.pa.domain.OrganizationalContact;
import gov.nih.nci.pa.domain.Person;
import gov.nih.nci.pa.dto.GeneralTrialDesignWebDTO;
import gov.nih.nci.pa.dto.PAContactDTO;
import gov.nih.nci.pa.dto.PAOrganizationalContactDTO;
import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;
import gov.nih.nci.pa.enums.DocumentWorkflowStatusCode;
import gov.nih.nci.pa.enums.FunctionalRoleStatusCode;
import gov.nih.nci.pa.enums.StudyContactRoleCode;
import gov.nih.nci.pa.enums.StudySiteContactRoleCode;
import gov.nih.nci.pa.enums.StudySiteFunctionalCode;
import gov.nih.nci.pa.enums.StudyTypeCode;
import gov.nih.nci.pa.enums.SummaryFourFundingCategoryCode;
import gov.nih.nci.pa.iso.convert.OrganizationalContactConverter;
import gov.nih.nci.pa.iso.dto.StudyContactDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.StudyResourcingDTO;
import gov.nih.nci.pa.iso.dto.StudySiteContactDTO;
import gov.nih.nci.pa.iso.dto.StudySiteDTO;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.DSetConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.PAExceptionConstants;
import gov.nih.nci.pa.service.correlation.ClinicalResearchStaffCorrelationServiceBean;
import gov.nih.nci.pa.service.correlation.CorrelationUtils;
import gov.nih.nci.pa.service.correlation.PABaseCorrelation;
import gov.nih.nci.pa.service.correlation.PARelationServiceBean;
import gov.nih.nci.pa.service.util.PAServiceUtils;
import gov.nih.nci.pa.util.Constants;
import gov.nih.nci.pa.util.PAConstants;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.pa.util.PaRegistry;
import gov.nih.nci.pa.util.PoRegistry;
import gov.nih.nci.services.correlation.NullifiedRoleException;
import gov.nih.nci.services.correlation.OrganizationalContactDTO;
import gov.nih.nci.services.entity.NullifiedEntityException;
import gov.nih.nci.services.organization.OrganizationDTO;
import gov.nih.nci.services.person.PersonDTO;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;

/**
 *
 * @author Vrushali
 *
 */
public class TrialHelper {
    private static final String SPONSOR = "sponsor";
    private static final int OFFICIAL_TITLE = 4000;
    private static final int KEYWORD = 600;
    private static final String ABSTRACTION = "Abstraction";
    private static final String VALIDATION = "Validation";
    private final PAServiceUtils paServUtil = new PAServiceUtils();
    /**
     *
     * @param studyProtocolIi Ii
     * @param operation s
     * @return dto
     * @throws PAException e
     * @throws NullifiedRoleException e
     */
    public GeneralTrialDesignWebDTO getTrialDTO(Ii studyProtocolIi, String operation) throws PAException,
        NullifiedRoleException {
        GeneralTrialDesignWebDTO  gtdDTO = new GeneralTrialDesignWebDTO();
        StudyProtocolDTO spDTO = PaRegistry.getStudyProtocolService().getStudyProtocol(studyProtocolIi);
        Long extension = Long.valueOf(studyProtocolIi.getExtension());
        StudyProtocolQueryDTO spqDto = PaRegistry.getProtocolQueryService().getTrialSummaryByStudyProtocolId(extension);
        CorrelationUtils cUtils = new CorrelationUtils();
        copy(spDTO, gtdDTO);
        gtdDTO.setLocalProtocolIdentifier(spqDto.getLocalStudyProtocolIdentifier());
        copyLO(cUtils.getPAOrganizationByIi(IiConverter.convertToPaOrganizationIi(spqDto.getLeadOrganizationId())),
                gtdDTO);
        if (!BooleanUtils.toBoolean(gtdDTO.getProprietarytrialindicator())) {
            copyPI(cUtils.getPAPersonByIi(IiConverter.convertToPaPersonIi(spqDto.getPiId())), gtdDTO);
            copyResponsibleParty(studyProtocolIi, gtdDTO);
            copySponsor(studyProtocolIi, gtdDTO);
            if (ABSTRACTION.equalsIgnoreCase(operation)) {
                copyCentralContact(studyProtocolIi, gtdDTO);
            }
        }
        if (VALIDATION.equalsIgnoreCase(operation)) {
            copySummaryFour(PaRegistry.getStudyResourcingService().getSummary4ReportedResourcing(studyProtocolIi),
                    gtdDTO);
        }
        copyOtherTrialIdentifiers(spDTO, gtdDTO);
        return gtdDTO;
    }
    /**
     *
     * @param studyProtocolIi Ii
     * @param gtdDTO dto to save
     * @param operation who called
     * @throws PAException e
     * @throws NullifiedRoleException nre
     * @throws NullifiedEntityException ne
     */
    public void saveTrial(Ii studyProtocolIi, GeneralTrialDesignWebDTO  gtdDTO, String operation) throws PAException,
        NullifiedEntityException, NullifiedRoleException   {
        updateStudyProtocol(studyProtocolIi, gtdDTO);

        StudySiteDTO identifierDTO;
        identifierDTO = new StudySiteDTO();
        identifierDTO.setStudyProtocolIdentifier(studyProtocolIi);
        identifierDTO.setLocalStudyProtocolIdentifier(StConverter.convertToSt(gtdDTO.getLocalProtocolIdentifier()));
        identifierDTO.setFunctionalCode(CdConverter.convertToCd(StudySiteFunctionalCode.LEAD_ORGANIZATION));
        PaRegistry.getOrganizationCorrelationService().createResearchOrganizationCorrelations(
               gtdDTO.getLeadOrganizationIdentifier());
        identifierDTO.setResearchOrganizationIi(PaRegistry.getOrganizationCorrelationService().
                    getPoResearchOrganizationByEntityIdentifier(IiConverter.convertToPoOrganizationIi(
                        gtdDTO.getLeadOrganizationIdentifier())));
        paServUtil.manageStudyIdentifiers(identifierDTO);
        manageCtGovElement(studyProtocolIi, gtdDTO);
        if (ABSTRACTION.equalsIgnoreCase(operation)) {
            createOrUpdateCentralContact(studyProtocolIi, gtdDTO);
        }
        saveSummary4Information(studyProtocolIi, gtdDTO);
        //nct
        saveOtherTrialIdentifier(studyProtocolIi, gtdDTO.getNctIdentifier(), PAConstants.NCT_IDENTIFIER_TYPE);
        //copied from trial identification screen
        if (!BooleanUtils.toBoolean(gtdDTO.getProprietarytrialindicator())) {
            saveOtherTrialIdentifier(studyProtocolIi, gtdDTO.getCtepIdentifier(), PAConstants.CTEP_IDENTIFIER_TYPE);
            saveOtherTrialIdentifier(studyProtocolIi, gtdDTO.getDcpIdentifier(), PAConstants.DCP_IDENTIFIER_TYPE);
        }
    }

    private void manageCtGovElement(Ii studyProtocolIi, GeneralTrialDesignWebDTO gtdDTO) throws PAException {
        if (!BooleanUtils.toBoolean(gtdDTO.getProprietarytrialindicator()) && gtdDTO.getCtGovXmlRequired()) {
            OrganizationDTO sponsorOrgDto = new OrganizationDTO();
            sponsorOrgDto.setIdentifier(IiConverter.convertToPoOrganizationIi(gtdDTO.getSponsorIdentifier()));
            paServUtil.manageSponsor(studyProtocolIi, sponsorOrgDto);
            //Pi
            PersonDTO principalInvestigatorDto = new PersonDTO();
            principalInvestigatorDto.setIdentifier(IiConverter.convertToPoPersonIi(gtdDTO.getPiIdentifier()));
            OrganizationDTO leadOrgDto = new OrganizationDTO();
            leadOrgDto.setIdentifier(IiConverter.convertToPoOrganizationIi(
                    gtdDTO.getLeadOrganizationIdentifier()));
            paServUtil.managePrincipalInvestigator(studyProtocolIi, leadOrgDto,
                    principalInvestigatorDto, StudyTypeCode.INTERVENTIONAL);
            paServUtil.removeResponsibleParty(studyProtocolIi);
            createSponorContact(studyProtocolIi, gtdDTO);
        } else if (!BooleanUtils.toBoolean(gtdDTO.getProprietarytrialindicator()) && !gtdDTO.getCtGovXmlRequired()) {
            paServUtil.removeSponsor(studyProtocolIi);
            paServUtil.removeResponsibleParty(studyProtocolIi);
        }
    }

    private void saveSummary4Information(Ii studyProtocolIi, GeneralTrialDesignWebDTO gtdDTO) throws PAException {
        //summ4 info
        if (StringUtils.isNotEmpty(gtdDTO.getSummaryFourOrgIdentifier())) {
            OrganizationDTO sum4OrgDto = new OrganizationDTO();
            sum4OrgDto.setIdentifier(IiConverter.convertToPoOrganizationIi(gtdDTO.getSummaryFourOrgIdentifier()));
            StudyResourcingDTO summary4ResoureDTO = new StudyResourcingDTO();
            if (StringUtils.isNotEmpty(gtdDTO.getSummaryFourFundingCategoryCode())) {
              summary4ResoureDTO.setTypeCode(CdConverter.convertToCd(SummaryFourFundingCategoryCode
                        .getByCode(gtdDTO.getSummaryFourFundingCategoryCode())));
            } else {
                Cd cd = new Cd();
                cd.setNullFlavor(NullFlavor.NI);
                summary4ResoureDTO.setTypeCode(cd);
            }
            paServUtil.manageSummaryFour(studyProtocolIi, sum4OrgDto, summary4ResoureDTO);
        }
    }

    private void saveOtherTrialIdentifier(Ii studyProtocolIi, String otherTrialIdentifier,
            String otherTrialIdentifierType) throws PAException {
        String dbTrialIdentifier = paServUtil.getStudyIdentifier(studyProtocolIi, otherTrialIdentifierType);
        StudySiteDTO trialIdentifierDTO = new StudySiteDTO();
        trialIdentifierDTO.setLocalStudyProtocolIdentifier(StConverter.convertToSt(otherTrialIdentifier));
        trialIdentifierDTO.setStudyProtocolIdentifier(studyProtocolIi);
        trialIdentifierDTO.setFunctionalCode(CdConverter.convertToCd(StudySiteFunctionalCode.IDENTIFIER_ASSIGNER));
        String poOrgId = PaRegistry.getOrganizationCorrelationService().getPOOrgIdentifierByIdentifierType(
            otherTrialIdentifierType);
        trialIdentifierDTO.setResearchOrganizationIi(PaRegistry.getOrganizationCorrelationService().
            getPoResearchOrganizationByEntityIdentifier(IiConverter.convertToPoOrganizationIi(poOrgId)));
        if (StringUtils.isNotEmpty(otherTrialIdentifier) && !dbTrialIdentifier.equals(otherTrialIdentifier)) {
            paServUtil.manageStudyIdentifiers(trialIdentifierDTO);
        } else if (StringUtils.isNotEmpty(dbTrialIdentifier)
                && !StringUtils.equals(dbTrialIdentifier, otherTrialIdentifier)) {
            LimitOffset pagingParams = new LimitOffset(PAConstants.MAX_SEARCH_RESULTS, 0);
            //delete from db
            try {
                List<StudySiteDTO> spDtos = PaRegistry.getStudySiteService().search(trialIdentifierDTO, pagingParams);
                PaRegistry.getStudySiteService().delete(((StudySiteDTO) PAUtil.getFirstObj(spDtos)).getIdentifier());
            } catch (TooManyResultsException e) {
                throw new PAException(e);
            }
        }
    }

    private void copy(StudyProtocolDTO spDTO, GeneralTrialDesignWebDTO gtdDTO) {
        gtdDTO.setOfficialTitle(spDTO.getOfficialTitle().getValue());
        gtdDTO.setAssignedIdentifier(PAUtil.getAssignedIdentifierExtension(spDTO));
        gtdDTO.setAcronym(spDTO.getAcronym().getValue());
        gtdDTO.setKeywordText(spDTO.getKeywordText().getValue());
        gtdDTO.setPhaseCode(spDTO.getPhaseCode().getCode());
        gtdDTO.setPhaseAdditionalQualifierCode(spDTO.getPhaseAdditionalQualifierCode().getCode());
        gtdDTO.setPrimaryPurposeCode(spDTO.getPrimaryPurposeCode().getCode());
        gtdDTO.setPrimaryPurposeAdditionalQualifierCode(spDTO.getPrimaryPurposeAdditionalQualifierCode().getCode());
        gtdDTO.setPrimaryPurposeOtherText(spDTO.getPrimaryPurposeOtherText().getValue());
        if (!PAUtil.isBlNull(spDTO.getProprietaryTrialIndicator())) {
            gtdDTO.setProprietarytrialindicator(BlConverter.convertToString(spDTO.getProprietaryTrialIndicator()));
        }
        gtdDTO.setSubmissionNumber(spDTO.getSubmissionNumber().getValue());
        gtdDTO.setAmendmentReasonCode(CdConverter.convertCdToString(spDTO.getAmendmentReasonCode()));
        gtdDTO.setProgramCodeText(StConverter.convertToString(spDTO.getProgramCodeText()));
        gtdDTO.setStudyProtocolId(IiConverter.convertToString(spDTO.getIdentifier()));
        if (!PAUtil.isBlNull(spDTO.getCtgovXmlRequiredIndicator())) {
            gtdDTO.setCtGovXmlRequired(spDTO.getCtgovXmlRequiredIndicator().getValue().booleanValue());
        }
    }

    private void copyLO(Organization o, GeneralTrialDesignWebDTO gtdDTO) {
        gtdDTO.setLeadOrganizationIdentifier(o.getIdentifier());
        gtdDTO.setLeadOrganizationName(o.getName());
    }

    private void copyPI(Person p, GeneralTrialDesignWebDTO gtdDTO) {
        gtdDTO.setPiIdentifier(p.getIdentifier());
        gtdDTO.setPiName(p.getFullName());
    }

    private void copyResponsibleParty(Ii studyProtocolIi, GeneralTrialDesignWebDTO gtdDTO) throws PAException,
            NullifiedRoleException {
        StudyContactDTO scDto = new StudyContactDTO();
        scDto.setRoleCode(CdConverter.convertToCd(StudyContactRoleCode.RESPONSIBLE_PARTY_STUDY_PRINCIPAL_INVESTIGATOR));
        List<StudyContactDTO> scDtos = PaRegistry.getStudyContactService().getByStudyProtocol(studyProtocolIi, scDto);
        DSet<Tel> dset = null;
        if (scDtos != null && !scDtos.isEmpty()) {
            gtdDTO.setResponsiblePartyType("pi");
            scDto = scDtos.get(0);
            dset = scDto.getTelecomAddresses();
        } else {
            StudySiteContactDTO spart = new StudySiteContactDTO();
            spart.setRoleCode(CdConverter.convertToCd(
                    StudySiteContactRoleCode.RESPONSIBLE_PARTY_SPONSOR_CONTACT));
            List<StudySiteContactDTO> spDtos = PaRegistry.getStudySiteContactService()
                .getByStudyProtocol(studyProtocolIi, spart);
            gtdDTO.setResponsiblePartyType(SPONSOR);
            if (spDtos != null && !spDtos.isEmpty()) {
                dset = copyResponsiblePartySponsor(gtdDTO, spDtos);
            }
        }
        copy(dset, gtdDTO);
    }

    private DSet<Tel> copyResponsiblePartySponsor(GeneralTrialDesignWebDTO gtdDTO, List<StudySiteContactDTO> spDtos)
            throws PAException, NullifiedRoleException {
        DSet<Tel> dset;
        StudySiteContactDTO spart;
        gtdDTO.setResponsiblePartyType(SPONSOR);
        spart = spDtos.get(0);
        dset = spart.getTelecomAddresses();
        CorrelationUtils cUtils = new CorrelationUtils();
        PAContactDTO contactDto = cUtils.getContactByPAOrganizationalContactId((
                Long.valueOf(spart.getOrganizationalContactIi().getExtension())));
        if (contactDto.getFullName() != null) {
         gtdDTO.setResponsiblePersonName(contactDto.getFullName());
         gtdDTO.setResponsiblePersonIdentifier(PAUtil.getIiExtension(contactDto.getPersonIdentifier()));
        }
        if (contactDto.getTitle() != null) {
            gtdDTO.setResponsiblePersonIdentifier(PAUtil.getIiExtension(contactDto.getSrIdentifier()));
            gtdDTO.setResponsibleGenericContactName(contactDto.getTitle());
        }
        return dset;
    }

    private void copy(DSet<Tel> dset, GeneralTrialDesignWebDTO gtdDTO) {
        if (dset == null) {
            return;
        }
        List<String> phones = DSetConverter.convertDSetToList(dset, "PHONE");
        List<String> emails = DSetConverter.convertDSetToList(dset, "EMAIL");
        if (phones != null && !phones.isEmpty()) {
           gtdDTO.setContactPhone(PAUtil.getPhone(phones.get(0)));
           gtdDTO.setContactPhoneExtn(PAUtil.getPhoneExtn(phones.get(0)));
        }
        if (emails != null && !emails.isEmpty()) {
            gtdDTO.setContactEmail(emails.get(0));
        }
    }

    private void copySponsor(Ii studyProtocolIi, GeneralTrialDesignWebDTO gtdDTO) throws PAException {
        StudySiteDTO spart = new StudySiteDTO();
        spart.setFunctionalCode(CdConverter.convertToCd(StudySiteFunctionalCode.SPONSOR));
        List<StudySiteDTO> spDtos = PaRegistry.getStudySiteService()
                        .getByStudyProtocol(studyProtocolIi, spart);
        if (spDtos != null && !spDtos.isEmpty()) {
            spart = spDtos.get(0);
            Organization o = new CorrelationUtils().getPAOrganizationByIi(spart.getResearchOrganizationIi());
            gtdDTO.setSponsorName(o.getName());
            gtdDTO.setSponsorIdentifier(o.getIdentifier());
        }

    }
    private void copyOtherTrialIdentifiers(StudyProtocolDTO spDTO, GeneralTrialDesignWebDTO gtdDTO) throws PAException {
        String nctNumber = paServUtil.getStudyIdentifier(spDTO.getIdentifier(), PAConstants.NCT_IDENTIFIER_TYPE);
        if (StringUtils.isNotEmpty(nctNumber)) {
            gtdDTO.setNctIdentifier(nctNumber);
        }
        String ctepId = paServUtil.getStudyIdentifier(spDTO.getIdentifier(), PAConstants.CTEP_IDENTIFIER_TYPE);
        if (StringUtils.isNotEmpty(ctepId)) {
            gtdDTO.setCtepIdentifier(ctepId);
        }
        String dcpId = paServUtil.getStudyIdentifier(spDTO.getIdentifier(), PAConstants.DCP_IDENTIFIER_TYPE);
        if (StringUtils.isNotEmpty(dcpId)) {
            gtdDTO.setDcpIdentifier(dcpId);
        }
        List<Ii> otherIdentifiers = PAUtil.getOtherIdentifiers(spDTO);
        Ii assignedIdentifier = PAUtil.getAssignedIdentifier(spDTO);
        gtdDTO.setOtherIdentifiers(otherIdentifiers);
        gtdDTO.setNonOtherIdentifiers(assignedIdentifier);
    }

    private void copyCentralContact(Ii studyProtocolIi, GeneralTrialDesignWebDTO gtdDTO) throws PAException,
            NullifiedRoleException {
        StudyContactDTO scDto = new StudyContactDTO();
        scDto.setRoleCode(CdConverter.convertToCd(StudyContactRoleCode.CENTRAL_CONTACT));
        List<StudyContactDTO> srDtos = PaRegistry.getStudyContactService().getByStudyProtocol(studyProtocolIi, scDto);
        if (srDtos != null && !srDtos.isEmpty()) {
            CorrelationUtils cUtils = new CorrelationUtils();
            scDto = srDtos.get(0);
            if (!PAUtil.isIiNull(scDto.getClinicalResearchStaffIi())) {
                Person p = cUtils.getPAPersonByIi(scDto.getClinicalResearchStaffIi());
                gtdDTO.setCentralContactIdentifier(p.getIdentifier());
                gtdDTO.setCentralContactName(p.getFullName());
            }
            if (!PAUtil.isIiNull(scDto.getOrganizationalContactIi())) {
                PAContactDTO pcontact = cUtils.getContactByPAOrganizationalContactId(
                    Long.valueOf(scDto.getOrganizationalContactIi().getExtension()));
                gtdDTO.setCentralContactIdentifier(PAUtil.getIiExtension(pcontact.getSrIdentifier()));
                gtdDTO.setCentralContactTitle(pcontact.getTitle());
            }
            copyCentralContactEmailAndPhone(gtdDTO, scDto);
        }
   }

    private void copyCentralContactEmailAndPhone(GeneralTrialDesignWebDTO gtdDTO, StudyContactDTO scDto) {
        DSet<Tel> dset = scDto.getTelecomAddresses();
        List<String> phones = DSetConverter.convertDSetToList(dset, "PHONE");
        List<String> emails = DSetConverter.convertDSetToList(dset, "EMAIL");
        if (phones != null && !phones.isEmpty()) {
            gtdDTO.setCentralContactPhone(PAUtil.getPhone(phones.get(0)));
            gtdDTO.setCentralContactPhoneExtn(PAUtil.getPhoneExtn(phones.get(0)));
        }
        if (emails != null && !emails.isEmpty()) {
            gtdDTO.setCentralContactEmail(emails.get(0));
        }
    }

    private void updateStudyProtocol(Ii studyProtocolIi, GeneralTrialDesignWebDTO gtdDTO) throws PAException {
        StudyProtocolDTO spDTO = new StudyProtocolDTO();
        spDTO = PaRegistry.getStudyProtocolService().getStudyProtocol(studyProtocolIi);
        spDTO.setIdentifier(spDTO.getIdentifier());
        spDTO.setOfficialTitle(StConverter.convertToSt(StringUtils.left(gtdDTO.getOfficialTitle(), OFFICIAL_TITLE)));
        spDTO.setAcronym(StConverter.convertToSt(gtdDTO.getAcronym()));
        spDTO.setKeywordText(StConverter.convertToSt(StringUtils.left(gtdDTO.getKeywordText(), KEYWORD)));
        if (gtdDTO.getSubmissionNumber() > 1) {
            spDTO.setAmendmentReasonCode(CdConverter.convertStringToCd(gtdDTO.getAmendmentReasonCode()));
        }
        if (gtdDTO.getProprietarytrialindicator() != null) {
            if (!StringUtils.equalsIgnoreCase(gtdDTO.getProprietarytrialindicator(), "true")) {
                spDTO.setCtgovXmlRequiredIndicator(BlConverter.convertToBl(gtdDTO.getCtGovXmlRequired()));
            }
            setPhaseAndPurpose(gtdDTO, spDTO);
            Set<Ii> allIdentifiers = new HashSet<Ii>();
            allIdentifiers.add(gtdDTO.getNonOtherIdentifiers());
            List<Ii> secondaryIds = (List<Ii>) ServletActionContext.getRequest().getSession()
                    .getAttribute(Constants.OTHER_IDENTIFIERS_LIST);
            if (secondaryIds != null) {
                allIdentifiers.addAll(secondaryIds);
            }
            spDTO.setSecondaryIdentifiers(null);
            spDTO.setSecondaryIdentifiers(DSetConverter.convertIiSetToDset(allIdentifiers));
        }
        PaRegistry.getStudyProtocolService().updateStudyProtocol(spDTO);
    }

    private void setPhaseAndPurpose(GeneralTrialDesignWebDTO gtdDTO, StudyProtocolDTO spDTO) {
        spDTO.setPhaseCode(CdConverter.convertStringToCd(gtdDTO.getPhaseCode()));
        if (PAUtil.isPhaseCodeNA(gtdDTO.getPhaseCode())) {
            spDTO.setPhaseAdditionalQualifierCode(CdConverter.convertStringToCd(
                   gtdDTO.getPhaseAdditionalQualifierCode()));
        } else {
            spDTO.setPhaseAdditionalQualifierCode(CdConverter.convertStringToCd(null));
        }
        spDTO.setPrimaryPurposeCode(CdConverter.convertStringToCd(gtdDTO.getPrimaryPurposeCode()));
        if (PAUtil.isPrimaryPurposeCodeOther(gtdDTO.getPrimaryPurposeCode())) {
            spDTO.setPrimaryPurposeAdditionalQualifierCode(CdConverter.convertStringToCd(
                   gtdDTO.getPrimaryPurposeAdditionalQualifierCode()));
        } else {
            spDTO.setPrimaryPurposeAdditionalQualifierCode(CdConverter.convertStringToCd(null));
        }
        if (PAUtil.isPrimaryPurposeAdditionQualifierCodeOther(gtdDTO.getPrimaryPurposeCode(),
                gtdDTO.getPrimaryPurposeAdditionalQualifierCode())) {
            spDTO.setPrimaryPurposeOtherText(StConverter.convertToSt(gtdDTO.getPrimaryPurposeOtherText()));
        } else {
            spDTO.setPrimaryPurposeOtherText(StConverter.convertToSt(null));
        }
    }
    /**
     *
     * @param studyProtocolIi li
     * @param gtdDTO dto
     * @throws PAException e
     * @throws NullifiedEntityException e
     * @throws NullifiedRoleException r
     */
    public void createOrUpdateCentralContact(Ii studyProtocolIi, GeneralTrialDesignWebDTO gtdDTO) throws PAException,
        NullifiedEntityException, NullifiedRoleException {
        StudyContactDTO scDto = new StudyContactDTO();
        scDto.setStudyProtocolIdentifier(studyProtocolIi);
        scDto.setRoleCode(CdConverter.convertToCd(StudyContactRoleCode.CENTRAL_CONTACT));
        List<StudyContactDTO> srDtos = PaRegistry.getStudyContactService().getByStudyProtocol(studyProtocolIi, scDto);
        if (srDtos != null && !srDtos.isEmpty()) {
            scDto = srDtos.get(0);
            if (StringUtils.isNotEmpty(gtdDTO.getCentralContactIdentifier())) {
                PaRegistry.getStudyContactService().update(createStudyContactObj(studyProtocolIi, scDto, gtdDTO));
            } else {
                //this mean lead org is changed and not selected the central contact so delete
                PaRegistry.getStudyContactService().delete(scDto.getIdentifier());
            }
        } else if (StringUtils.isNotEmpty(gtdDTO.getCentralContactIdentifier())) {
            PaRegistry.getStudyContactService().create(createStudyContactObj(studyProtocolIi, scDto, gtdDTO));
        }
    }

    /**
     *
     * @param studyProtocolIi li
     * @param scDTO scdto
     * @param gtdDTO dto
     * @return sto
     * @throws PAException e
     * @throws NullifiedEntityException ne
     * @throws NullifiedRoleException nr
     */
    public StudyContactDTO createStudyContactObj(Ii studyProtocolIi, StudyContactDTO scDTO,
            GeneralTrialDesignWebDTO gtdDTO) throws PAException, NullifiedEntityException, NullifiedRoleException {
        ClinicalResearchStaffCorrelationServiceBean crbb = new ClinicalResearchStaffCorrelationServiceBean();
        String phone = gtdDTO.getCentralContactPhone().trim();
        if (StringUtils.isNotEmpty(gtdDTO.getCentralContactPhoneExtn())) {
            StringBuffer phoneWithExtn = new StringBuffer();
            phoneWithExtn.append(phone).append("extn").append(gtdDTO.getCentralContactPhoneExtn());
            phone = phoneWithExtn.toString();
        }
        Ii selectedCenteralContactIi  = null;
        if (StringUtils.isNotEmpty(gtdDTO.getCentralContactIdentifier())) {
            PersonDTO isoPerDTO = PoRegistry.getPersonEntityService().getPerson(
                    IiConverter.convertToPoPersonIi(gtdDTO.getCentralContactIdentifier()));
            if (isoPerDTO == null) {
                DSet<Ii> iiDset = PoRegistry.getOrganizationalContactCorrelationService().getCorrelation(
                  IiConverter.convertToPoOrganizationalContactIi(gtdDTO.getCentralContactIdentifier())).getIdentifier();
                  selectedCenteralContactIi = DSetConverter.convertToIi(iiDset);
            } else {
                selectedCenteralContactIi = isoPerDTO.getIdentifier();
            }
        }
        if (IiConverter.PERSON_ROOT.equalsIgnoreCase(selectedCenteralContactIi.getRoot())) {
            //create crs only if contact is person
            Long crs = crbb.createClinicalResearchStaffCorrelations(
                gtdDTO.getLeadOrganizationIdentifier(), selectedCenteralContactIi.getExtension());
            scDTO.setClinicalResearchStaffIi(IiConverter.convertToIi(crs));
            scDTO.setOrganizationalContactIi(IiConverter.convertToIi(""));
        }
        if (IiConverter.ORGANIZATIONAL_CONTACT_ROOT.equalsIgnoreCase(selectedCenteralContactIi.getRoot())) {
            //create crs only if contact is person
            PABaseCorrelation<PAOrganizationalContactDTO , OrganizationalContactDTO , OrganizationalContact ,
            OrganizationalContactConverter> oc = new PABaseCorrelation<PAOrganizationalContactDTO ,
            OrganizationalContactDTO , OrganizationalContact , OrganizationalContactConverter>(
               PAOrganizationalContactDTO.class, OrganizationalContact.class, OrganizationalContactConverter.class);
            PAOrganizationalContactDTO orgContacPaDto = new PAOrganizationalContactDTO();
            orgContacPaDto.setOrganizationIdentifier(IiConverter.convertToPoOrganizationIi(
                    gtdDTO.getLeadOrganizationIdentifier()));
            orgContacPaDto.setIdentifier(selectedCenteralContactIi);
            Long ocId = oc.create(orgContacPaDto);
            scDTO.setOrganizationalContactIi(IiConverter.convertToIi(ocId));
            scDTO.setClinicalResearchStaffIi(IiConverter.convertToIi(""));
        }
        scDTO.setStudyProtocolIdentifier(studyProtocolIi);
        List<String> phones = new ArrayList<String>();
        phones.add(phone);
        List<String> emails = new ArrayList<String>();
        emails.add(gtdDTO.getCentralContactEmail());
        DSet<Tel> dsetList = null;
        dsetList =  DSetConverter.convertListToDSet(phones, "PHONE", dsetList);
        dsetList =  DSetConverter.convertListToDSet(emails, "EMAIL", dsetList);
        scDTO.setTelecomAddresses(dsetList);
        scDTO.setStatusCode(CdConverter.convertToCd(FunctionalRoleStatusCode.PENDING));
        return scDTO;
    }

    private void copySummaryFour(StudyResourcingDTO srDTO, GeneralTrialDesignWebDTO gtdDTO) throws PAException {
        if (srDTO == null) {
            return;
        }
        if (!PAUtil.isCdNull(srDTO.getTypeCode())) {
            gtdDTO.setSummaryFourFundingCategoryCode(srDTO.getTypeCode().getCode());
        }
        if (PAUtil.isIiNotNull(srDTO.getOrganizationIdentifier())) {
            CorrelationUtils cUtils = new CorrelationUtils();
            Organization o = cUtils.getPAOrganizationByIi(srDTO.getOrganizationIdentifier());
            gtdDTO.setSummaryFourOrgIdentifier(o.getIdentifier());
            gtdDTO.setSummaryFourOrgName(o.getName());
        }
    }
    /**
     *
     * @param studyProtocolIi ii
     * @param gtdDTO gtdDto
     * @throws PAException e
     */
    public void createSponorContact(Ii studyProtocolIi, GeneralTrialDesignWebDTO gtdDTO) throws PAException {
        PARelationServiceBean parb = new PARelationServiceBean();
        String phone = gtdDTO.getContactPhone().trim();
        if (StringUtils.isNotEmpty(gtdDTO.getContactPhoneExtn())) {
            StringBuffer phoneWithExtn = new StringBuffer();
            phoneWithExtn.append(phone).append("extn").append(gtdDTO.getContactPhoneExtn());
            phone = phoneWithExtn.toString();
        }
        if (gtdDTO.getResponsiblePartyType() == null || gtdDTO.getResponsiblePartyType().equals("pi")) {
            parb.createPIAsResponsiblePartyRelations(
                    gtdDTO.getLeadOrganizationIdentifier(), gtdDTO.getPiIdentifier(),
                    Long.valueOf(studyProtocolIi.getExtension()),  gtdDTO.getContactEmail(), phone);
        } else if (gtdDTO.getResponsiblePartyType().equals(SPONSOR)) {
            saveResponsibleSponsorContact(studyProtocolIi, gtdDTO, parb, phone);
        }
    }

    private void saveResponsibleSponsorContact(Ii studyProtocolIi, GeneralTrialDesignWebDTO gtdDTO,
            PARelationServiceBean parb, String phone) throws PAException {
        PAContactDTO contactDto = new PAContactDTO();
        contactDto.setOrganizationIdentifier(IiConverter.convertToPoOrganizationIi(gtdDTO.getSponsorIdentifier()));
        contactDto.setStudyProtocolIdentifier(studyProtocolIi);
        contactDto.setEmail(gtdDTO.getContactEmail());
        contactDto.setPhone(phone);
        if (StringUtils.isNotEmpty(gtdDTO.getResponsiblePersonName())) {
            contactDto.setPersonIdentifier(IiConverter.convertToPoPersonIi(gtdDTO.getResponsiblePersonIdentifier()));
        }
        if (StringUtils.isNotEmpty(gtdDTO.getResponsibleGenericContactName())) {
            contactDto.setSrIdentifier(IiConverter.convertToPoOrganizationalContactIi(
                    gtdDTO.getResponsiblePersonIdentifier()));
        }
        try {
            parb.createSponsorAsPrimaryContactRelations(contactDto);
        } catch (PAException pae) {
            if (pae.getMessage().contains(PAExceptionConstants.NULLIFIED_PERSON)) {
                throw new PAException(PAServiceUtils.SPONSOR + pae.getMessage(), pae);
            }
            throw pae;
        }
    }

    /**
     *
     * @param dwsCode cd
     * @return string
     */
    public String setMenuLinks(DocumentWorkflowStatusCode dwsCode) {
        String action = "";
        if (DocumentWorkflowStatusCode.REJECTED.equals(dwsCode)) {
            action = DocumentWorkflowStatusCode.REJECTED.getCode();
        } else if (DocumentWorkflowStatusCode.SUBMITTED.equals(dwsCode)
                   || DocumentWorkflowStatusCode.AMENDMENT_SUBMITTED.equals(dwsCode)) {
            action = DocumentWorkflowStatusCode.SUBMITTED.getCode();
        } else {
            action = DocumentWorkflowStatusCode.ACCEPTED.getCode();
        }
        return action;
    }
}
