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
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.util.StudyProtocolStageServiceLocal;
import gov.nih.nci.pa.util.PAUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Vrushali
 *
 */
public class MockTempStudyProtocolService implements StudyProtocolStageServiceLocal {
    static List<StudyProtocolStage> list;
    static List<StudyFundingStage> tempStudyFundingList;
    static List<StudyIndIdeStage> tempStudyIndIdeList;
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
        sp.setPhaseCode(PhaseCode.II_III);
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
        tempStudyFundingList = new ArrayList<StudyFundingStage>();
        StudyFundingStage studyFunding = new StudyFundingStage();
        studyFunding.setFundingMechanismCode("fundingMechanismCode");
        studyFunding.setNciDivisionProgramCode(NciDivisionProgramCode.CCR);
        studyFunding.setSerialNumber("serialNumber");
        studyFunding.setNihInstituteCode("nihInstituteCode");
        studyFunding.setStudyProtocolStage(sp);
        tempStudyFundingList.add(studyFunding);
        
        tempStudyIndIdeList = new ArrayList<StudyIndIdeStage>();
        StudyIndIdeStage tempInd = new StudyIndIdeStage();
        tempInd.setIndIdeNumber("indIdeNumber");
        tempInd.setIndIdeTypeCode(IndldeTypeCode.IDE);
        tempInd.setGrantorCode(GrantorCode.CBER);
        tempInd.setHolderTypeCode(HolderTypeCode.INDUSTRY);
        tempInd.setExpandedAccessIndicator(Boolean.FALSE);
        tempInd.setStudyProtocolStage(sp);
        tempStudyIndIdeList.add(tempInd);
        tempInd = new StudyIndIdeStage();
        tempInd.setIndIdeNumber("indIdeNumber");
        tempInd.setIndIdeTypeCode(IndldeTypeCode.IDE);
        tempInd.setGrantorCode(GrantorCode.CBER);
        tempInd.setHolderTypeCode(HolderTypeCode.NCI);
        tempInd.setNciDivPrgHolderCode(NciDivisionProgramCode.CCR);
        tempInd.setExpandedAccessIndicator(Boolean.TRUE);
        tempInd.setExpandedAccessStatusCode(ExpandedAccessStatusCode.AVAILABLE);
        tempInd.setStudyProtocolStage(sp);
        tempStudyIndIdeList.add(tempInd);
        tempInd = new StudyIndIdeStage();
        tempInd.setIndIdeNumber("indIdeNumber");
        tempInd.setIndIdeTypeCode(IndldeTypeCode.IDE);
        tempInd.setGrantorCode(GrantorCode.CBER);
        tempInd.setHolderTypeCode(HolderTypeCode.NIH);
        tempInd.setNihInstHolderCode(NihInstituteCode.NEI);
        tempInd.setExpandedAccessIndicator(Boolean.TRUE);
        tempInd.setExpandedAccessStatusCode(ExpandedAccessStatusCode.AVAILABLE);
        tempInd.setStudyProtocolStage(sp);
        tempStudyIndIdeList.add(tempInd);
    }
    /* (non-Javadoc)
     * @see gov.nih.nci.pa.service.TempStudyProtocolService#createGrant(gov.nih.nci.pa.iso.dto.TempStudyFundingDTO)
     */
    public void createGrant(StudyFundingStage tempStudyFundingDTO)
            throws PAException {
        // TODO Auto-generated method stub

    }

    /* (non-Javadoc)
     * @see gov.nih.nci.pa.service.TempStudyProtocolService#createTempStudyProtocol(gov.nih.nci.pa.iso.dto.TempStudyProtocolDTO)
     */
    public Ii create(StudyProtocolStage ispDTO)
            throws PAException {
        // TODO Auto-generated method stub
        return IiConverter.convertToIi("1");
    }

    /* (non-Javadoc)
     * @see gov.nih.nci.pa.service.TempStudyProtocolService#delete(gov.nih.nci.iso21090.Ii)
     */
    public void delete(Ii ii) throws PAException {
        // TODO Auto-generated method stub

    }

    /* (non-Javadoc)
     * @see gov.nih.nci.pa.service.TempStudyProtocolService#deleteGrantsForTempStudyProtocol(gov.nih.nci.iso21090.Ii)
     */
    public void deleteGrants(Ii tempStudyProtocolIi)
            throws PAException {
        // TODO Auto-generated method stub

    }

    /* (non-Javadoc)
     * @see gov.nih.nci.pa.service.TempStudyProtocolService#getGrantsByTempStudyProtocol(gov.nih.nci.iso21090.Ii)
     */
    public List<StudyFundingStage> getGrants(
            Ii tempStudyProtocolIi) throws PAException {
        List<StudyFundingStage> retList = new ArrayList<StudyFundingStage>(); 
        for (StudyFundingStage tsf: tempStudyFundingList) {
            if(tsf.getStudyProtocolStage().getId().equals(IiConverter.convertToLong(tempStudyProtocolIi))) {
                retList.add(tsf);
            }
        }
        return retList;
    }

    /* (non-Javadoc)
     * @see gov.nih.nci.pa.service.TempStudyProtocolService#getTempStudyProtocol(gov.nih.nci.iso21090.Ii)
     */
    public StudyProtocolStage get(Ii ii) throws PAException {
        for (StudyProtocolStage tsp: list) {
            if(tsp.getId().equals(IiConverter.convertToLong(ii))) {
                return tsp;
            }
        }
        return new StudyProtocolStage();
    }

    /* (non-Javadoc)
     * @see gov.nih.nci.pa.service.TempStudyProtocolService#search(gov.nih.nci.pa.iso.dto.TempStudyProtocolDTO, gov.nih.nci.coppa.services.LimitOffset)
     */
    public List<StudyProtocolStage> search(StudyProtocolStage dto,
            LimitOffset pagingParams) throws PAException,
            TooManyResultsException {
        List<StudyProtocolStage> returnList = new ArrayList<StudyProtocolStage>();
        if(dto.getOfficialTitle() != null && dto.getOfficialTitle().equalsIgnoreCase("ThrowException")) {
            throw new PAException("Test");
        }
        for (StudyProtocolStage tempSp : list) {
            String phaseCode = "";
            if (dto.getPhaseCode() != null) {
                phaseCode = dto.getPhaseCode().getCode();
            }
            if(PAUtil.isNotEmpty(phaseCode) && tempSp.getPhaseCode().getCode().equalsIgnoreCase(phaseCode)) {
                returnList.add(tempSp);
            }
            if(PAUtil.isNotEmpty(dto.getOfficialTitle()) && tempSp.getOfficialTitle().equalsIgnoreCase(dto.getOfficialTitle())) {
                returnList.add(tempSp);
            }    
        }
        return returnList;
    }

    /* (non-Javadoc)
     * @see gov.nih.nci.pa.service.TempStudyProtocolService#updateTempStudyProtocol(gov.nih.nci.pa.iso.dto.TempStudyProtocolDTO)
     */
    public StudyProtocolStage update(StudyProtocolStage isoDTO) throws PAException {
        StudyProtocolStage isoDto = new StudyProtocolStage();
        isoDto.setId(1L);
        return isoDto;
    }

    public void createIndIde(StudyIndIdeStage tempStudyIndIdeDTO)
            throws PAException {
        // TODO Auto-generated method stub
        
    }

    public void deleteIndIde(Ii tempStudyProtocolIi)
            throws PAException {
        // TODO Auto-generated method stub
        
    }

    public List<StudyIndIdeStage> getIndIde(
            Ii tempStudyProtocolIi) throws PAException {
        List<StudyIndIdeStage> retList = new ArrayList<StudyIndIdeStage>(); 
        for (StudyIndIdeStage tsf: tempStudyIndIdeList) {
            if(tsf.getStudyProtocolStage().getId().equals(IiConverter.convertToLong(tempStudyProtocolIi))) {
                retList.add(tsf);
            }
        }
        return retList;
    }

}
