package gov.nih.nci.pa.action;

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

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;

/**
 *
 * @author Vrushali
 *
 */
@SuppressWarnings({ "PMD.TooManyMethods", "PMD.ExcessiveMethodLength", "PMD.ExcessiveClassLength" ,
    "PMD.CyclomaticComplexity", "PMD.NPathComplexity" })
public class TrialHelper {
    private static final String SPONSOR = "sponsor";
    private static final int OFFICIAL_TITLE = 4000;
    private static final int KEYWORD = 600;
    private static final String FALSE = "FALSE";
    private static final String ABSTRACTION = "Abstraction";
    private static final String VALIDATION = "Validation";
    /**
     *
     * @param studyProtocolIi Ii
     * @param operation s
     * @return dto
     * @throws PAException e
     * @throws NullifiedRoleException e
     */
    public GeneralTrialDesignWebDTO getTrialDTO(Ii studyProtocolIi, String operation)
        throws PAException, NullifiedRoleException {
        GeneralTrialDesignWebDTO  gtdDTO = new GeneralTrialDesignWebDTO();
        StudyProtocolDTO spDTO = PaRegistry.getStudyProtocolService().getStudyProtocol(studyProtocolIi);
        Long extension = Long.valueOf(studyProtocolIi.getExtension());
        StudyProtocolQueryDTO spqDto = PaRegistry.getProtocolQueryService().getTrialSummaryByStudyProtocolId(extension);
        CorrelationUtils cUtils = new CorrelationUtils();
        copy(spDTO, gtdDTO);
        copy(spqDto, gtdDTO);
        copyLO(cUtils.getPAOrganizationByIi(IiConverter.convertToPaOrganizationIi(spqDto.getLeadOrganizationId())), 
                gtdDTO);
        if (gtdDTO.getProprietarytrialindicator() == null
                || gtdDTO.getProprietarytrialindicator().equalsIgnoreCase(FALSE)) {
            copyPI(cUtils.getPAPersonByIi(IiConverter.convertToPaPersonIi(spqDto.getPiId()))
                    , gtdDTO);
            copyResponsibleParty(studyProtocolIi, gtdDTO);
            copySponsor(studyProtocolIi, gtdDTO);
            if (ABSTRACTION.equalsIgnoreCase(operation)) {
                copyCentralContact(studyProtocolIi, gtdDTO);
            }
        }
        copyOtherIdentifiers(spDTO, gtdDTO);
        copyNctNummber(studyProtocolIi, gtdDTO);
        if (VALIDATION.equalsIgnoreCase(operation)) {
            copySummaryFour(PaRegistry.getStudyResourcingService()
                    .getsummary4ReportedResource(studyProtocolIi), gtdDTO);
        }
        copyIdentifier(studyProtocolIi, gtdDTO);
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
        PAServiceUtils paServUtil = new PAServiceUtils();
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
        if ((gtdDTO.getProprietarytrialindicator() == null
               || gtdDTO.getProprietarytrialindicator().equalsIgnoreCase(FALSE))
               && gtdDTO.getCtGovXmlRequired()) {
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
        }
        if (ABSTRACTION.equalsIgnoreCase(operation)) {
            createOrUpdateCentralContact(studyProtocolIi, gtdDTO);
        }
        //summ4 info
        if (PAUtil.isNotEmpty(gtdDTO.getSummaryFourOrgIdentifier())) {
            OrganizationDTO sum4OrgDto = new OrganizationDTO();
            sum4OrgDto.setIdentifier(IiConverter.convertToPoOrganizationIi(gtdDTO.getSummaryFourOrgIdentifier()));
            StudyResourcingDTO summary4ResoureDTO = new StudyResourcingDTO();
            if (PAUtil.isNotEmpty(gtdDTO.getSummaryFourFundingCategoryCode())) {
              summary4ResoureDTO.setTypeCode(CdConverter.convertToCd(SummaryFourFundingCategoryCode
                        .getByCode(gtdDTO.getSummaryFourFundingCategoryCode())));
            } else {
                Cd cd = new Cd();
                cd.setNullFlavor(NullFlavor.NI);
                summary4ResoureDTO.setTypeCode(cd);
            }
            paServUtil.manageSummaryFour(studyProtocolIi, sum4OrgDto, summary4ResoureDTO);
        }
        //nct
        String dbNctNumber = paServUtil.getStudyIdentifier(studyProtocolIi ,
                PAConstants.NCT_IDENTIFIER_TYPE);
        if (!dbNctNumber.equals(gtdDTO.getNctIdentifier())) {
            StudySiteDTO nctIdentifierDTO = new StudySiteDTO();
            nctIdentifierDTO.setLocalStudyProtocolIdentifier(StConverter.convertToSt(gtdDTO.getNctIdentifier()));
            nctIdentifierDTO.setStudyProtocolIdentifier(studyProtocolIi);
            nctIdentifierDTO.setFunctionalCode(CdConverter.convertToCd(StudySiteFunctionalCode.IDENTIFIER_ASSIGNER));
            String poOrgId = PaRegistry.getOrganizationCorrelationService().getPOOrgIdentifierByIdentifierType(
                PAConstants.NCT_IDENTIFIER_TYPE);
            nctIdentifierDTO.setResearchOrganizationIi(PaRegistry.getOrganizationCorrelationService().
                getPoResearchOrganizationByEntityIdentifier(IiConverter.convertToPoOrganizationIi(poOrgId)));
            paServUtil.manageStudyIdentifiers(nctIdentifierDTO);
        }
        //copied from trial identification screen
        if (gtdDTO.getProprietarytrialindicator() == null
                || gtdDTO.getProprietarytrialindicator().equalsIgnoreCase(FALSE)) {
            String dbCtepId =  paServUtil.getStudyIdentifier(studyProtocolIi, PAConstants.CTEP_IDENTIFIER_TYPE);
            if (!dbCtepId.equals(gtdDTO.getCtepIdentifier())) {
                identifierDTO = new StudySiteDTO();
                identifierDTO.setStudyProtocolIdentifier(studyProtocolIi);
                identifierDTO.setLocalStudyProtocolIdentifier(StConverter.convertToSt(gtdDTO.getCtepIdentifier()));
                identifierDTO.setFunctionalCode(CdConverter.convertToCd(
                        StudySiteFunctionalCode.IDENTIFIER_ASSIGNER));
                String poOrgId = PaRegistry.getOrganizationCorrelationService().getPOOrgIdentifierByIdentifierType(
                        PAConstants.CTEP_IDENTIFIER_TYPE);
                identifierDTO.setResearchOrganizationIi(PaRegistry.getOrganizationCorrelationService().
                        getPoResearchOrganizationByEntityIdentifier(
                        IiConverter.convertToPoOrganizationIi(poOrgId)));
                paServUtil.manageStudyIdentifiers(identifierDTO);
            }
            String dbDcpId = paServUtil.getStudyIdentifier(studyProtocolIi, PAConstants.DCP_IDENTIFIER_TYPE);
            if (!dbDcpId.equals(gtdDTO.getDcpIdentifier())) {
                identifierDTO = new StudySiteDTO();
                identifierDTO.setStudyProtocolIdentifier(studyProtocolIi);
                identifierDTO.setLocalStudyProtocolIdentifier(StConverter.convertToSt(gtdDTO.getDcpIdentifier()));
                identifierDTO.setFunctionalCode(CdConverter.convertToCd(
                        StudySiteFunctionalCode.IDENTIFIER_ASSIGNER));
                String poOrgId = PaRegistry.getOrganizationCorrelationService().getPOOrgIdentifierByIdentifierType(
                        PAConstants.DCP_IDENTIFIER_TYPE);
                identifierDTO.setResearchOrganizationIi(PaRegistry.getOrganizationCorrelationService().
                        getPoResearchOrganizationByEntityIdentifier(
                        IiConverter.convertToPoOrganizationIi(poOrgId)));
                paServUtil.manageStudyIdentifiers(identifierDTO);
            }
        }
    }
    
    private void copyOtherIdentifiers(StudyProtocolDTO spDTO, GeneralTrialDesignWebDTO gtdDTO) {
        List<Ii> otherIdentifiers = PAUtil.getOtherIdentifiers(spDTO);
        Ii assignedIdentifier = PAUtil.getAssignedIdentifier(spDTO);
        gtdDTO.setOtherIdentifiers(otherIdentifiers);
        gtdDTO.setNonOtherIdentifiers(assignedIdentifier);
    }
    
    /**
     *
     * @param spDTO spDto
     * @param gtdDTO dto
     */
    private void copy(StudyProtocolDTO spDTO, GeneralTrialDesignWebDTO gtdDTO) {
        gtdDTO.setOfficialTitle(spDTO.getOfficialTitle().getValue());
        gtdDTO.setAssignedIdentifier(PAUtil.getAssignedIdentifierExtension(spDTO));
        gtdDTO.setAcronym(spDTO.getAcronym().getValue());
        gtdDTO.setKeywordText(spDTO.getKeywordText().getValue());
        gtdDTO.setPhaseCode(spDTO.getPhaseCode().getCode());
        gtdDTO.setPhaseOtherText(spDTO.getPhaseOtherText().getValue());
        gtdDTO.setPrimaryPurposeCode(spDTO.getPrimaryPurposeCode().getCode());
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

    /**
     *
     * @param spqDTO spDto
     * @param gtdDTO gtdDto
     */
    private void copy(StudyProtocolQueryDTO spqDTO, GeneralTrialDesignWebDTO gtdDTO) {
        gtdDTO.setLocalProtocolIdentifier(spqDTO.getLocalStudyProtocolIdentifier());
    }
    /**
     *
     * @param o org
     * @param gtdDTO gtddto
     */
    private void copyLO(Organization o, GeneralTrialDesignWebDTO gtdDTO) {
        gtdDTO.setLeadOrganizationIdentifier(o.getIdentifier());
        gtdDTO.setLeadOrganizationName(o.getName());
    }
    /**
     *
     * @param p person
     * @param gtdDTO dto
     */
    private void copyPI(Person p, GeneralTrialDesignWebDTO gtdDTO) {
        gtdDTO.setPiIdentifier(p.getIdentifier());
        gtdDTO.setPiName(p.getFullName());
    }
    /**
     *
     * @param studyProtocolIi li
     * @param gtdDTO dto
     * @throws PAException pa
     * @throws NullifiedRoleException e
     */
    private void copyResponsibleParty(Ii studyProtocolIi, GeneralTrialDesignWebDTO gtdDTO)
    throws PAException, NullifiedRoleException {
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

            }
        }
        copy(dset, gtdDTO);
    }
    /**
     *
     * @param dset dset
     * @param gtdDTO gtdDTO
     */
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
    /**
     *
     * @param studyProtocolIi ii
     * @param gtdDTO gtdDto
     * @throws PAException e
     */
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
    /**
     *
     * @param studyProtocolIi li
     * @param gtdDTO gtdto
     * @throws PAException e
     */
    private void copyNctNummber(Ii studyProtocolIi, GeneralTrialDesignWebDTO gtdDTO) throws PAException {
        String nctNumber = new PAServiceUtils().getStudyIdentifier(studyProtocolIi, PAConstants.NCT_IDENTIFIER_TYPE);
        if (PAUtil.isNotEmpty(nctNumber)) {
            gtdDTO.setNctIdentifier(nctNumber);
        }
    }
    private void copyIdentifier(Ii studyProtocolIi, GeneralTrialDesignWebDTO gtdDTO) throws PAException {
        String ctepId = new PAServiceUtils().getStudyIdentifier(studyProtocolIi, PAConstants.CTEP_IDENTIFIER_TYPE);
        if (StringUtils.isNotEmpty(ctepId)) {
            gtdDTO.setCtepIdentifier(ctepId);
        }
        String dcpId = new PAServiceUtils().getStudyIdentifier(studyProtocolIi, PAConstants.DCP_IDENTIFIER_TYPE);
        if (StringUtils.isNotEmpty(dcpId)) {
            gtdDTO.setDcpIdentifier(dcpId);
        }
    }
    /**
     *
     * @param studyProtocolIi li
     * @param gtdDTO dto
     * @throws PAException e
     * @throws NullifiedRoleException e
     */
    private void copyCentralContact(Ii studyProtocolIi, GeneralTrialDesignWebDTO gtdDTO)
    throws PAException, NullifiedRoleException {
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
   }

    /**
     *
     * @param studyProtocolIi li
     * @param gtdDTO dto
     * @param operation o
     * @throws PAException li
     */
    private void updateStudyProtocol(Ii studyProtocolIi, GeneralTrialDesignWebDTO gtdDTO)
    throws PAException {
        StudyProtocolDTO spDTO = new StudyProtocolDTO();
        spDTO = PaRegistry.getStudyProtocolService().getStudyProtocol(studyProtocolIi);
        spDTO.setIdentifier(spDTO.getIdentifier());
        spDTO.setOfficialTitle(StConverter.convertToSt(PAUtil.stringSetter(gtdDTO.getOfficialTitle(), OFFICIAL_TITLE)));
        spDTO.setAcronym(StConverter.convertToSt(gtdDTO.getAcronym()));
        spDTO.setKeywordText(StConverter.convertToSt(PAUtil.stringSetter(gtdDTO.getKeywordText(), KEYWORD)));
        if (gtdDTO.getSubmissionNumber() > 1) {
            spDTO.setAmendmentReasonCode(CdConverter.convertStringToCd(gtdDTO.getAmendmentReasonCode()));
        }
        if (gtdDTO != null && gtdDTO.getProprietarytrialindicator() != null) {
                if (gtdDTO.getProprietarytrialindicator().equalsIgnoreCase("true")) {
                   spDTO.setPhaseCode(CdConverter.convertStringToCd(gtdDTO.getPhaseCode()));
                   spDTO.setPrimaryPurposeCode(CdConverter.convertStringToCd(gtdDTO.getPrimaryPurposeCode()));
                } else {
                   spDTO.setCtgovXmlRequiredIndicator(BlConverter.convertToBl(gtdDTO.getCtGovXmlRequired()));
                }
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
            if (PAUtil.isNotEmpty(gtdDTO.getCentralContactIdentifier())) {
                PaRegistry.getStudyContactService().update(createStudyContactObj(studyProtocolIi, scDto, gtdDTO));
            } else {
                //this mean lead org is changed and not selected the central contact so delete
                PaRegistry.getStudyContactService().delete(scDto.getIdentifier());
            }
        } else if (PAUtil.isNotEmpty(gtdDTO.getCentralContactIdentifier())) {
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
            GeneralTrialDesignWebDTO gtdDTO)
    throws PAException, NullifiedEntityException, NullifiedRoleException {

        ClinicalResearchStaffCorrelationServiceBean crbb = new ClinicalResearchStaffCorrelationServiceBean();
        String phone = gtdDTO.getCentralContactPhone().trim();
        if (PAUtil.isNotEmpty(gtdDTO.getCentralContactPhoneExtn())) {
            StringBuffer phoneWithExtn = new StringBuffer();
            phoneWithExtn.append(phone).append("extn").append(gtdDTO.getCentralContactPhoneExtn());
            phone = phoneWithExtn.toString();
        }
        Ii selectedCenteralContactIi  = null;
        if (PAUtil.isNotEmpty(gtdDTO.getCentralContactIdentifier())) {
            PersonDTO isoPerDTO = PoRegistry.getPersonEntityService().getPerson(
                    IiConverter.convertToPoPersonIi(gtdDTO.getCentralContactIdentifier()));
            if (isoPerDTO == null) {
                    DSet<Ii> iiDset = PoRegistry.getOrganizationalContactCorrelationService().getCorrelation(
                  IiConverter.convertToPoOrganizationalContactIi(
                          gtdDTO.getCentralContactIdentifier())).getIdentifier();
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
    /**
     *
     * @param srDTO srDTO
     * @param gtdDTO gtdDTO
     * @throws PAException e
     */
    private void copySummaryFour(StudyResourcingDTO srDTO,
            GeneralTrialDesignWebDTO gtdDTO) throws PAException {
        if (srDTO == null) {
            return;
        }
        if (srDTO.getTypeCode() != null) {
            gtdDTO.setSummaryFourFundingCategoryCode(srDTO.getTypeCode().getCode());
        }
        if (srDTO.getOrganizationIdentifier() != null
                && PAUtil.isNotEmpty(srDTO.getOrganizationIdentifier().getExtension())) {
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
    public void createSponorContact(Ii studyProtocolIi,
            GeneralTrialDesignWebDTO gtdDTO) throws PAException {
        PARelationServiceBean parb = new PARelationServiceBean();
        String phone = gtdDTO.getContactPhone().trim();
        if (PAUtil.isNotEmpty(gtdDTO.getContactPhoneExtn())) {
            StringBuffer phoneWithExtn = new StringBuffer();
            phoneWithExtn.append(phone).append("extn").append(gtdDTO.getContactPhoneExtn());
            phone = phoneWithExtn.toString();
        }
        if (gtdDTO.getResponsiblePartyType() == null || gtdDTO.getResponsiblePartyType().equals("pi")) {
            parb.createPIAsResponsiblePartyRelations(
                    gtdDTO.getLeadOrganizationIdentifier(), gtdDTO.getPiIdentifier(),
                    Long.valueOf(studyProtocolIi.getExtension()),  gtdDTO.getContactEmail(), phone);
        } else if (gtdDTO.getResponsiblePartyType().equals(SPONSOR)) {
            PAContactDTO contactDto = new PAContactDTO();
            contactDto.setOrganizationIdentifier(IiConverter.convertToPoOrganizationIi(gtdDTO.getSponsorIdentifier()));
            contactDto.setStudyProtocolIdentifier(studyProtocolIi);
            contactDto.setEmail(gtdDTO.getContactEmail());
            contactDto.setPhone(phone);
            if (!PAUtil.isEmpty(gtdDTO.getResponsiblePersonName())) {
                contactDto.setPersonIdentifier(IiConverter.convertToPoPersonIi(
                        gtdDTO.getResponsiblePersonIdentifier()));
              }
              if (!PAUtil.isEmpty(gtdDTO.getResponsibleGenericContactName())) {
                  contactDto.setSrIdentifier(IiConverter.convertToPoOrganizationalContactIi(
                          gtdDTO.getResponsiblePersonIdentifier()));
              }
            parb.createSponsorAsPrimaryContactRelations(contactDto);
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
