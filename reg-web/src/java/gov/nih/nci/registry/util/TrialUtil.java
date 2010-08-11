package gov.nih.nci.registry.util;

import gov.nih.nci.iso21090.DSet;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.domain.Organization;
import gov.nih.nci.pa.domain.Person;
import gov.nih.nci.pa.dto.PAContactDTO;
import gov.nih.nci.pa.dto.PaOrganizationDTO;
import gov.nih.nci.pa.dto.PaPersonDTO;
import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;
import gov.nih.nci.pa.enums.StudyContactRoleCode;
import gov.nih.nci.pa.enums.StudySiteContactRoleCode;
import gov.nih.nci.pa.enums.StudySiteFunctionalCode;
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
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.IntConverter;
import gov.nih.nci.pa.iso.util.IvlConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.correlation.CorrelationUtils;
import gov.nih.nci.pa.util.CommonsConstant;
import gov.nih.nci.pa.util.PAConstants;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.pa.util.PaRegistry;
import gov.nih.nci.registry.dto.BaseTrialDTO;
import gov.nih.nci.registry.dto.ProprietaryTrialDTO;
import gov.nih.nci.registry.dto.StudyIndldeWebDTO;
import gov.nih.nci.registry.dto.SubmittedOrganizationDTO;
import gov.nih.nci.registry.dto.TrialDTO;
import gov.nih.nci.registry.dto.TrialFundingWebDTO;
import gov.nih.nci.registry.dto.TrialIndIdeDTO;
import gov.nih.nci.services.correlation.NullifiedRoleException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.struts2.ServletActionContext;

/**
 * The Class TrialUtil.
 *
 * @author vrushali
 */
public class TrialUtil extends TrialConvertUtils {

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
        trialDTO.setPhaseAdditonalQualifier(spDTO.getPhaseAdditionalQualifierCode().getCode());
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
        String nctNumber = getPaServiceUtil().getStudyIdentifier(studyProtocolIi, PAConstants.NCT_IDENTIFIER_TYPE);
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
                && StringUtils.isNotEmpty(srDTO.getOrganizationIdentifier().getExtension())) {
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
    * Gets the trial dto from db.
    *
    * @param studyProtocolIi Ii
    * @param trialDTO TrailDTO
    *
    * @throws PAException ex
    * @throws NullifiedRoleException e
    */
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
    * @param studyProtocolIi the study protocol ii
    * @param trialDTO the trial dto
    * @throws PAException the PA exception
    */
   private void copyParticipatingSites(Ii studyProtocolIi, TrialDTO trialDTO) throws PAException {
       CorrelationUtils cUtils = new CorrelationUtils();
       List<PaOrganizationDTO> participatingSitesList = new ArrayList<PaOrganizationDTO>();
       StudySiteDTO srDTO = new StudySiteDTO();
       srDTO.setFunctionalCode(CdConverter.convertStringToCd(StudySiteFunctionalCode.TREATING_SITE
                       .getCode()));
       List<StudySiteDTO> spList = PaRegistry.getStudySiteService()
                                               .getByStudyProtocol(studyProtocolIi, srDTO);
       for (StudySiteDTO sp : spList) {
           StudySiteAccrualStatusDTO ssas = PaRegistry.getStudySiteAccrualStatusService()
                                              .getCurrentStudySiteAccrualStatusByStudySite(sp.getIdentifier());
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
    * Copy participating sites for proprietary trial.
    *
    * @param studyProtocolIi the study protocol ii
    * @param trialDTO the trial dto
    *
    * @throws PAException the PA exception
    */
   public void copyParticipatingSites(Ii studyProtocolIi, ProprietaryTrialDTO trialDTO) throws PAException {

       CorrelationUtils cUtils = new CorrelationUtils();
       List<SubmittedOrganizationDTO> organizationList = new ArrayList<SubmittedOrganizationDTO>();
       StudySiteDTO srDTO = new StudySiteDTO();
       srDTO.setFunctionalCode(CdConverter.convertStringToCd(StudySiteFunctionalCode.TREATING_SITE.getCode()));
       List<StudySiteDTO> spList = PaRegistry.getStudySiteService().getByStudyProtocol(studyProtocolIi, srDTO);
       for (StudySiteDTO sp : spList) {
           StudySiteAccrualStatusDTO ssas = PaRegistry.getStudySiteAccrualStatusService()
                               .getCurrentStudySiteAccrualStatusByStudySite(sp.getIdentifier());
           Organization orgBo = cUtils.getPAOrganizationByIi(sp.getHealthcareFacilityIi());
           SubmittedOrganizationDTO orgWebDTO = new SubmittedOrganizationDTO(sp, ssas, orgBo);
           List<PaPersonDTO> principalInvresults = PaRegistry.getPAHealthCareProviderService()
                       .getPersonsByStudySiteId(Long.valueOf(sp.getIdentifier().getExtension().toString()),
                   StudySiteContactRoleCode.PRINCIPAL_INVESTIGATOR.getName());
           if (!principalInvresults.isEmpty()) {
               for (PaPersonDTO per : principalInvresults) {
                   orgWebDTO.setInvestigator(per.getFullName() != null ? per.getFullName() : "");
               }
           }
           orgWebDTO.setNameInvestigator(orgWebDTO.getName() + " - " + orgWebDTO.getInvestigator());
           organizationList.add(orgWebDTO);
       }
       trialDTO.setParticipatingSitesList(organizationList);
   }

   /**
   * Copy regulatory information.
   * @param studyProtocolIi the study protocol ii
   * @param trialDTO the trial dto
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
           setOversgtInfo(trialDTO);
       }
   }



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
        if (StringUtils.isEmpty(trialDTO.getSelectedRegAuth())) {
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
            if (NumberUtils.isNumber(trialDTO.getLst())) {
                trialDTO.setRegIdAuthOrgList(PaRegistry.getRegulatoryInformationService().getRegulatoryAuthorityNameId(
                        Long.valueOf(trialDTO.getLst())));
            }
        } catch (PAException e) {
            //do nothing
        }
    }


    /**
     * Copy dcp nummber.
     * @param studyProtocolIi ii
     * @param trialDTO dto
     * @throws PAException ex
     */
    private void copyDcpIdentifier(Ii studyProtocolIi, TrialDTO trialDTO) throws PAException {
        String dcpId = getPaServiceUtil().getStudyIdentifier(studyProtocolIi, PAConstants.DCP_IDENTIFIER_TYPE);
        if (StringUtils.isNotEmpty(dcpId)) {
            trialDTO.setDcpIdentifier(dcpId);
        }
    }
    /**
     * Copy dcp nummber.
     * @param studyProtocolIi ii
     * @param trialDTO dto
     * @throws PAException ex
     */
    private void copyCtepIdentifier(Ii studyProtocolIi, TrialDTO trialDTO) throws PAException {
        String ctepId = getPaServiceUtil().getStudyIdentifier(studyProtocolIi, PAConstants.CTEP_IDENTIFIER_TYPE);
        if (StringUtils.isNotEmpty(ctepId)) {
            trialDTO.setCtepIdentifier(ctepId);
        }
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
            if (StringUtils.isEmpty(trialDTO.getPropritaryTrialIndicator())
                    || trialDTO.getPropritaryTrialIndicator().equalsIgnoreCase(CommonsConstant.NO)) {
                populateRegulatoryList((TrialDTO) trialDTO);
            }
        return trialDTO;
    }
    /**
     * @param trialDTO dto
     * @return dto
     * @throws PAException on err
     */
    @SuppressWarnings("unchecked")
    public BaseTrialDTO saveDraft(BaseTrialDTO trialDTO) throws PAException {
        StringBuffer errMsg =  new StringBuffer();
         //lead org local id and lead org is mandatory
        if (StringUtils.isEmpty(trialDTO.getLeadOrgTrialIdentifier())) {
            errMsg.append("Lead Organization Trial Identifier is required.");
        }
        if (StringUtils.isEmpty(trialDTO.getLeadOrganizationIdentifier())) {
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
        if (CollectionUtils.isNotEmpty(grantList)) {
                for (TrialFundingWebDTO fundingDto : grantList) {
                    fundingDTOS.add(convertToStudyFundingStage(fundingDto));
                }
        }
        //inds
        if (CollectionUtils.isNotEmpty(indList)) {
            for (TrialIndIdeDTO indDto : indList) {
                indDTOS.add(convertToStudyIndIdeStage(indDto));
            }
        }

        StudyProtocolStageDTO spStageDto = convertToStudyProtocolStageDTO(trialDTO);
        if (StringUtils.isNotEmpty(trialDTO.getStudyProtocolId())) {
            StudyProtocolStageDTO dto = PaRegistry.getStudyProtocolStageService().update(
                    spStageDto, fundingDTOS, indDTOS);
            tempStudyProtocolIi =  dto.getIdentifier();
        } else {
            tempStudyProtocolIi = PaRegistry.getStudyProtocolStageService().create(
                    spStageDto, fundingDTOS, indDTOS);
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
        trialDTO.setPhaseAdditonalQualifier(spDTO.getPhaseAdditionalQualifierCode().getCode());
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
        copyParticipatingSites(studyProtocolIi, trialDTO);
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
    /**
    * @param trialDTO2 trialDTO
    */
   public void removeAssignedIdentifierFromSecondaryIds(TrialDTO trialDTO2) {
       for (Iterator<Ii> iter = trialDTO2.getSecondaryIdentifierList().iterator(); iter.hasNext();) {
           Ii ii = iter.next();
           if (IiConverter.STUDY_PROTOCOL_ROOT.equals(ii.getRoot())) {
               iter.remove();
           } else {
               ii.setRoot(IiConverter.STUDY_PROTOCOL_OTHER_IDENTIFIER_ROOT);
           }
       }
   }
}
