/**
 * 
 */
package gov.nih.nci.pa.util;

import gov.nih.nci.coppa.iso.Cd;
import gov.nih.nci.coppa.iso.DSet;
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.pa.domain.Organization;
import gov.nih.nci.pa.domain.Person;
import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;
import gov.nih.nci.pa.dto.TrialDTO;
import gov.nih.nci.pa.dto.TrialFundingDTO;
import gov.nih.nci.pa.dto.TrialIndIdeDTO;
import gov.nih.nci.pa.enums.StudyContactRoleCode;
import gov.nih.nci.pa.enums.StudyParticipationContactRoleCode;
import gov.nih.nci.pa.enums.StudyParticipationFunctionalCode;
import gov.nih.nci.pa.iso.dto.StudyContactDTO;
import gov.nih.nci.pa.iso.dto.StudyIndldeDTO;
import gov.nih.nci.pa.iso.dto.StudyParticipationContactDTO;
import gov.nih.nci.pa.iso.dto.StudyParticipationDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.StudyResourcingDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.DSetConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.correlation.CorrelationUtils;
import gov.nih.nci.pa.service.correlation.PoPaServiceBeanLookup;


import java.util.ArrayList;
import java.util.List;

/**
 * @author vrushali
 *
 */
@SuppressWarnings({ "PMD.TooManyMethods" })
public class TrialUtil {
    private static final String SPONSOR = "sponsor";
    
    /**
     * Default constructor.
     */
    public TrialUtil() {
        super();
    }

    /**
     * 
     * @param spDTO sdto
     * @param trialDTO gdto
     */
    public void copy(StudyProtocolDTO spDTO, TrialDTO trialDTO) {
        trialDTO.setOfficialTitle(spDTO.getOfficialTitle().getValue());
        trialDTO.setAssignedIdentifier(spDTO.getAssignedIdentifier().getExtension());
        trialDTO.setPhaseCode(spDTO.getPhaseCode().getCode());
        trialDTO.setPhaseOtherText(spDTO.getPhaseOtherText().getValue());
        trialDTO.setPrimaryPurposeCode(spDTO.getPrimaryPurposeCode().getCode());
        trialDTO.setPrimaryPurposeOtherText(spDTO.getPrimaryPurposeOtherText().getValue());
        trialDTO.setStartDate(TsConverter.convertToTimestamp(spDTO.getStartDate()).toString());
        trialDTO.setStartDateType(spDTO.getStartDateTypeCode().getCode());
        trialDTO.setCompletionDate(TsConverter.convertToTimestamp(spDTO.getPrimaryCompletionDate()).toString());
        trialDTO.setCompletionDateType(spDTO.getPrimaryCompletionDateTypeCode().getCode());
    }

    /**
     * 
     * @param spqDTO sdto
     * @param trialDTO gdto
    */
    public void copy(StudyProtocolQueryDTO spqDTO, TrialDTO trialDTO) {
        trialDTO.setLocalProtocolIdentifier(spqDTO.getLocalStudyProtocolIdentifier());
        trialDTO.setStatusCode(spqDTO.getStudyStatusCode().getCode());
        trialDTO.setStatusDate(spqDTO.getStudyStatusDate().toString());
    }
    /**
     * 
     * @param o o
     * @param trialDTO gdto
     */
    public void copyLO(Organization o, TrialDTO trialDTO) {
        trialDTO.setLeadOrganizationIdentifier(o.getIdentifier());
        trialDTO.setLeadOrganizationName(o.getName());
    }
    /**
     * 
     * @param p p
     * @param trialDTO dto
     */
    public void copyPI(Person p, TrialDTO trialDTO) {
        trialDTO.setPiIdentifier(p.getIdentifier());
        trialDTO.setPiName(p.getFullName());
    }
    /**
     * 
     * @param studyProtocolIi ii
     * @param trialDTO dto
     * @throws PAException ex
     */
    public void copyResponsibleParty(Ii studyProtocolIi, TrialDTO trialDTO) throws PAException {
        StudyContactDTO scDto = new StudyContactDTO();
        scDto.setRoleCode(CdConverter.convertToCd(StudyContactRoleCode.RESPONSIBLE_PARTY_STUDY_PRINCIPAL_INVESTIGATOR));
        List<StudyContactDTO> scDtos =  PoPaServiceBeanLookup.getStudyContactService()
            .getByStudyProtocol(studyProtocolIi, scDto);
        DSet dset = null;
        if (scDtos != null && !scDtos.isEmpty()) {
            trialDTO.setResponsiblePartyType("pi");
            scDto = scDtos.get(0);
            dset = scDto.getTelecomAddresses();
        } else {
            StudyParticipationContactDTO spart = new StudyParticipationContactDTO();
            spart.setRoleCode(CdConverter.convertToCd(
                    StudyParticipationContactRoleCode.RESPONSIBLE_PARTY_SPONSOR_CONTACT));
            List<StudyParticipationContactDTO> spDtos = PoPaServiceBeanLookup.getStudyParticipationContactService()
                .getByStudyProtocol(studyProtocolIi, spart);
            trialDTO.setResponsiblePartyType(SPONSOR);
            if (spDtos != null && !spDtos.isEmpty()) {
                trialDTO.setResponsiblePartyType(SPONSOR);
                spart = spDtos.get(0);
                dset = spart.getTelecomAddresses();
                CorrelationUtils cUtils = new CorrelationUtils();
                Person p = cUtils.getPAPersonByPAOrganizationalContactId((
                        Long.valueOf(spart.getOrganizationalContactIi().getExtension())));
                trialDTO.setResponsiblePersonIdentifier(p.getIdentifier());
                 trialDTO.setResponsiblePersonName(p.getFirstName() + ", " + p.getLastName());


            }
        }
        copy(dset, trialDTO);
    }
/**
 * 
 * @param dset set
 * @param trialDTO dto
 */
    public void copy(DSet dset, TrialDTO trialDTO) {
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
     * 
     * @param studyProtocolIi ii
     * @param trialDTO dto
     * @throws PAException ex
     */
    public void copySponsor(Ii studyProtocolIi, TrialDTO trialDTO) throws PAException {
        StudyParticipationDTO spart = new StudyParticipationDTO();
        spart.setFunctionalCode(CdConverter.convertToCd(StudyParticipationFunctionalCode.SPONSOR));
        List<StudyParticipationDTO> spDtos = PoPaServiceBeanLookup.getStudyParticipationService()
            .getByStudyProtocol(studyProtocolIi, spart);
        if (spDtos != null && !spDtos.isEmpty()) {
            spart = spDtos.get(0);
            Organization o = new CorrelationUtils().getPAOrganizationByPAResearchOrganizationId(

                        Long.valueOf(spart.getResearchOrganizationIi().getExtension()));
            trialDTO.setSponsorName(o.getName());
            trialDTO.setSponsorIdentifier(o.getIdentifier());
        }

    }
    /**
     * 
     * @param studyProtocolIi ii
     * @param trialDTO dto
     * @throws PAException ex
     */
    public void copyNctNummber(Ii studyProtocolIi, TrialDTO trialDTO) throws PAException {
        StudyParticipationDTO spDto = getStudyParticipation(studyProtocolIi,
                    StudyParticipationFunctionalCode.IDENTIFIER_ASSIGNER);
        if (spDto != null) {
            trialDTO.setNctIdentifier(StConverter.convertToString(spDto.getLocalStudyProtocolIdentifier()));
        }
    }

    /**
     * 
     * @param studyProtocolIi ii
     * @param spCode code
     * @return dto
     * @throws PAException ex
     */
    public StudyParticipationDTO getStudyParticipation(Ii studyProtocolIi , StudyParticipationFunctionalCode spCode)
    throws PAException {
        if (studyProtocolIi == null) {
            throw new PAException(" StudyProtocol Ii is null");
        }
        StudyParticipationDTO spDto = new StudyParticipationDTO();
        Cd cd = CdConverter.convertToCd(spCode);
        spDto.setFunctionalCode(cd);

        List<StudyParticipationDTO> spDtos = PoPaServiceBeanLookup.getStudyParticipationService()
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
     * 
     * @param srDTO sdto
     * @param trialDTO tdto
     * @throws PAException ex
     */
    public void copySummaryFour(StudyResourcingDTO srDTO, TrialDTO trialDTO) throws PAException {
        if (srDTO == null) {
            return;
        }
        if (srDTO.getTypeCode() != null) {
            trialDTO.setSummaryFourFundingCategoryCode(srDTO.getTypeCode().getCode());
        }
        if (srDTO.getOrganizationIdentifier() != null
                && PAUtil.isNotEmpty(srDTO.getOrganizationIdentifier().getExtension())) {
            CorrelationUtils cUtils = new CorrelationUtils();
            Organization o = cUtils.getPAOrganizationByIndetifers(Long.valueOf(srDTO.getOrganizationIdentifier()
                    .getExtension()), null);
            trialDTO.setSummaryFourOrgIdentifier(o.getIdentifier());
            trialDTO.setSummaryFourOrgName(o.getName());
        }
    }
    /**
     * 
     * @param studyIndldeDTOList iiDto
     * @param trialDTO dto
     * @throws PAException ex
     */
    public void copyINDIDEList(List<StudyIndldeDTO> studyIndldeDTOList, TrialDTO trialDTO) throws PAException {
        if (studyIndldeDTOList == null) {
            return;
        }
        List<TrialIndIdeDTO> indList = new ArrayList<TrialIndIdeDTO>();
        //loop thru the iso dto
        for (StudyIndldeDTO isoDto : studyIndldeDTOList) {
            indList.add(new TrialIndIdeDTO(isoDto));
        }
        trialDTO.setIndDtos(indList);
    }
    /**
     * 
     * @param isoGrantlist iso
     * @param trialDTO dto
     */
    public void copyGrantList(List<StudyResourcingDTO> isoGrantlist, TrialDTO trialDTO) {
        if (isoGrantlist == null) {
            return;
        }
        List<TrialFundingDTO> grantList = new ArrayList<TrialFundingDTO>(); 
        //loop thru iso
        for (StudyResourcingDTO isoDto : isoGrantlist) {
            grantList.add(new TrialFundingDTO(isoDto));
        }
        trialDTO.setFundingDtos(grantList);
    }
}
