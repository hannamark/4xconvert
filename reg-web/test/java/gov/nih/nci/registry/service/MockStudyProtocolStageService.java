/**
 *
 */
package gov.nih.nci.registry.service;

import gov.nih.nci.coppa.services.LimitOffset;
import gov.nih.nci.coppa.services.TooManyResultsException;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.domain.StudyFundingStage;
import gov.nih.nci.pa.domain.StudyIndIdeStage;
import gov.nih.nci.pa.domain.StudyProtocolStage;
import gov.nih.nci.pa.enums.ActualAnticipatedTypeCode;
import gov.nih.nci.pa.enums.ExpandedAccessStatusCode;
import gov.nih.nci.pa.enums.GrantorCode;
import gov.nih.nci.pa.enums.HolderTypeCode;
import gov.nih.nci.pa.enums.IndldeTypeCode;
import gov.nih.nci.pa.enums.NciDivisionProgramCode;
import gov.nih.nci.pa.enums.NihInstituteCode;
import gov.nih.nci.pa.enums.PhaseCode;
import gov.nih.nci.pa.enums.PrimaryPurposeCode;
import gov.nih.nci.pa.enums.StudyStatusCode;
import gov.nih.nci.pa.enums.SummaryFourFundingCategoryCode;
import gov.nih.nci.pa.iso.convert.StudyFundingStageConverter;
import gov.nih.nci.pa.iso.convert.StudyIndIdeStageConverter;
import gov.nih.nci.pa.iso.convert.StudyProtocolStageConverter;
import gov.nih.nci.pa.iso.dto.DocumentDTO;
import gov.nih.nci.pa.iso.dto.StudyFundingStageDTO;
import gov.nih.nci.pa.iso.dto.StudyIndIdeStageDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolStageDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.StudyProtocolStageServiceLocal;
import gov.nih.nci.pa.util.PAUtil;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.fiveamsolutions.nci.commons.service.AbstractBaseSearchBean;

/**
 * @author Vrushali
 *
 */
public class MockStudyProtocolStageService extends AbstractBaseSearchBean<StudyProtocolStage>
    implements StudyProtocolStageServiceLocal {
    static List<StudyProtocolStage> list;
    static List<StudyFundingStage> studyFundingList;
    static List<StudyIndIdeStage> studyIndIdeList;
    static {
        list = new ArrayList<StudyProtocolStage>();
        StudyProtocolStage sp = new StudyProtocolStage();
        sp.setId(1L);
        sp.setStartDateTypeCode(ActualAnticipatedTypeCode.ACTUAL);
        sp.setStartDate(PAUtil.dateStringToTimestamp("01/20/2008"));
        sp.setPrimaryCompletionDateTypeCode(ActualAnticipatedTypeCode.ANTICIPATED);
        sp.setPrimaryCompletionDate(PAUtil.dateStringToTimestamp("01/20/2010"));
        sp.setOfficialTitle("officialTitle");
        sp.setLeadOrganizationIdentifier("1");
        sp.setLocalProtocolIdentifier("localProtocolIdentifier");
        sp.setPhaseCode(PhaseCode.I_II);
        sp.setPrimaryPurposeCode(PrimaryPurposeCode.TREATMENT);
        sp.setTrialStatusCode(StudyStatusCode.ACTIVE);
        sp.setPiIdentifier("2");
        sp.setSponsorIdentifier("1");
        sp.setSummaryFourFundingCategoryCode(SummaryFourFundingCategoryCode.NATIONAL);
        sp.setSummaryFourOrgIdentifier("1");
        sp.setOversightAuthorityCountryId("1");
        sp.setOversightAuthorityOrgId("1");
        list.add(sp);
        sp = new StudyProtocolStage();
        sp.setId(2L);
        sp.setStartDateTypeCode(ActualAnticipatedTypeCode.ACTUAL);
        sp.setStartDate(PAUtil.dateStringToTimestamp("01/20/2008"));
        sp.setPrimaryCompletionDateTypeCode(ActualAnticipatedTypeCode.ANTICIPATED);
        sp.setPrimaryCompletionDate(PAUtil.dateStringToTimestamp("01/20/2010"));
        sp.setOfficialTitle("officialTitle");
        sp.setLeadOrganizationIdentifier("1");
        sp.setLocalProtocolIdentifier("localProtocolIdentifier");
        sp.setPhaseCode(PhaseCode.I_II);
        sp.setPrimaryPurposeCode(PrimaryPurposeCode.TREATMENT);
        sp.setTrialStatusCode(StudyStatusCode.ACTIVE);
        sp.setPiIdentifier("2");
        sp.setSponsorIdentifier("1");
        sp.setSummaryFourFundingCategoryCode(SummaryFourFundingCategoryCode.NATIONAL);
        sp.setSummaryFourOrgIdentifier("1");
        sp.setFdaRegulatedIndicator(Boolean.TRUE);
        sp.setSection801Indicator(Boolean.TRUE);
        sp.setDelayedpostingIndicator(Boolean.TRUE);
        sp.setDataMonitoringCommitteeAppointedIndicator(Boolean.TRUE);
        sp.setOversightAuthorityCountryId("1");
        sp.setOversightAuthorityOrgId("1");
        list.add(sp);
        sp = new StudyProtocolStage();
        sp.setId(3L);
        sp.setStartDateTypeCode(ActualAnticipatedTypeCode.ACTUAL);
        sp.setStartDate(PAUtil.dateStringToTimestamp("01/20/2008"));
        sp.setPrimaryCompletionDateTypeCode(ActualAnticipatedTypeCode.ANTICIPATED);
        sp.setPrimaryCompletionDate(PAUtil.dateStringToTimestamp("01/20/2010"));
        sp.setOfficialTitle("officialTitle");
        sp.setLeadOrganizationIdentifier("1");
        sp.setLocalProtocolIdentifier("localProtocolIdentifier");
        sp.setFdaRegulatedIndicator(Boolean.FALSE);
        sp.setSection801Indicator(Boolean.FALSE);
        sp.setDelayedpostingIndicator(Boolean.FALSE);
        sp.setDataMonitoringCommitteeAppointedIndicator(Boolean.FALSE);
        sp.setResponsibleIdentifier("1");
        sp.setOversightAuthorityCountryId("1");
        sp.setOversightAuthorityOrgId("1");
        list.add(sp);
        studyFundingList = new ArrayList<StudyFundingStage>();
        StudyFundingStage studyFunding = new StudyFundingStage();
        studyFunding.setFundingMechanismCode("fundingMechanismCode");
        studyFunding.setNciDivisionProgramCode(NciDivisionProgramCode.CCR);
        studyFunding.setSerialNumber("serialNumber");
        studyFunding.setNihInstituteCode("nihInstituteCode");
        studyFunding.setStudyProtocolStage(sp);
        studyFundingList.add(studyFunding);

        studyIndIdeList = new ArrayList<StudyIndIdeStage>();
        StudyIndIdeStage indIde = new StudyIndIdeStage();
        indIde.setIndIdeNumber("indIdeNumber");
        indIde.setIndldeTypeCode(IndldeTypeCode.IDE);
        indIde.setGrantorCode(GrantorCode.CBER);
        indIde.setHolderTypeCode(HolderTypeCode.INDUSTRY);
        indIde.setExpandedAccessIndicator(Boolean.FALSE);
        indIde.setStudyProtocolStage(sp);
        studyIndIdeList.add(indIde);
        indIde = new StudyIndIdeStage();
        indIde.setIndIdeNumber("indIdeNumber");
        indIde.setIndldeTypeCode(IndldeTypeCode.IDE);
        indIde.setGrantorCode(GrantorCode.CBER);
        indIde.setHolderTypeCode(HolderTypeCode.NCI);
        indIde.setNciDivPrgHolderCode(NciDivisionProgramCode.CCR);
        indIde.setExpandedAccessIndicator(Boolean.TRUE);
        indIde.setExpandedAccessStatusCode(ExpandedAccessStatusCode.AVAILABLE);
        indIde.setStudyProtocolStage(sp);
        studyIndIdeList.add(indIde);
        indIde = new StudyIndIdeStage();
        indIde.setIndIdeNumber("indIdeNumber");
        indIde.setIndldeTypeCode(IndldeTypeCode.IDE);
        indIde.setGrantorCode(GrantorCode.CBER);
        indIde.setHolderTypeCode(HolderTypeCode.NIH);
        indIde.setNihInstHolderCode(NihInstituteCode.NEI);
        indIde.setExpandedAccessIndicator(Boolean.TRUE);
        indIde.setExpandedAccessStatusCode(ExpandedAccessStatusCode.AVAILABLE);
        indIde.setStudyProtocolStage(sp);
        studyIndIdeList.add(indIde);
    }
    /* (non-Javadoc)
     * @see gov.nih.nci.pa.service.TempStudyProtocolService#delete(gov.nih.nci.iso21090.Ii)
     */
    public void delete(Ii ii) throws PAException {
        // TODO Auto-generated method stub

    }

    public StudyProtocolStageDTO get(Ii ii) throws PAException {
        for (StudyProtocolStage tsp: list) {
            if(tsp.getId().equals(IiConverter.convertToLong(ii))) {
                return StudyProtocolStageConverter.convertFromDomainToDTO(tsp);
            }
        }
        return new StudyProtocolStageDTO();
    }

    public List<StudyFundingStageDTO> getGrantsByStudyProtocolStage(
            Ii studyProtocolStageIi) throws PAException {
        List<StudyFundingStageDTO> retList = new ArrayList<StudyFundingStageDTO>();
        for (StudyFundingStage tsf: studyFundingList) {
            if(tsf.getStudyProtocolStage().getId().equals(IiConverter.convertToLong(studyProtocolStageIi))) {
                retList.add(StudyFundingStageConverter.convertFromDomainToDTO(tsf));
            }
        }
        return retList;

    }

    public List<StudyIndIdeStageDTO> getIndIdesByStudyProtocolStage(
            Ii studyProtocolStageIi) throws PAException {
        List<StudyIndIdeStageDTO> retList = new ArrayList<StudyIndIdeStageDTO>();
        for (StudyIndIdeStage tsf: studyIndIdeList) {
            if(tsf.getStudyProtocolStage().getId().equals(IiConverter.convertToLong(studyProtocolStageIi))) {
                retList.add(StudyIndIdeStageConverter.convertFromDomainToDTO(tsf));
            }
        }
        return retList;
    }

    public List<StudyProtocolStageDTO> search(StudyProtocolStageDTO dto,
            LimitOffset pagingParams) throws PAException,
            TooManyResultsException {
        List<StudyProtocolStageDTO> returnList = new ArrayList<StudyProtocolStageDTO>();
        if(dto.getOfficialTitle() != null && dto.getOfficialTitle().getValue().equalsIgnoreCase("ThrowException")) {
            throw new PAException("Test");
        }
        for (StudyProtocolStage tempSp : list) {
            String phaseCode = CdConverter.convertCdToString(dto.getPhaseCode());
            if(StringUtils.isNotEmpty(phaseCode) && tempSp.getPhaseCode().getCode().equalsIgnoreCase(phaseCode)) {
                returnList.add(StudyProtocolStageConverter.convertFromDomainToDTO(tempSp));
            }
            if(!PAUtil.isStNull(dto.getOfficialTitle()) && tempSp.getOfficialTitle().equalsIgnoreCase(
                    StConverter.convertToString(dto.getOfficialTitle()))) {
                returnList.add(StudyProtocolStageConverter.convertFromDomainToDTO(tempSp));
            }
        }
        return returnList;
    }

    public Ii create(StudyProtocolStageDTO ispDTO, List<StudyFundingStageDTO> fundDTOs,
          List<StudyIndIdeStageDTO> indDTOs, List<DocumentDTO> docDTOs) throws PAException {
        // TODO Auto-generated method stub
        return IiConverter.convertToIi("1");
    }

    public List<DocumentDTO> getDocumentsByStudyProtocolStage(Ii studyProtocolStageIi) throws PAException {
        // TODO Auto-generated method stub
        return new ArrayList<DocumentDTO>();
    }

    public StudyProtocolStageDTO update(StudyProtocolStageDTO isoDTO, List<StudyFundingStageDTO> fundDTOs,
            List<StudyIndIdeStageDTO> indDTOs, List<DocumentDTO> docDTOs)
    throws PAException {
        StudyProtocolStageDTO isoDto = new StudyProtocolStageDTO();
        isoDto.setIdentifier(IiConverter.convertToIi("1"));
        return isoDto;
    }

}
