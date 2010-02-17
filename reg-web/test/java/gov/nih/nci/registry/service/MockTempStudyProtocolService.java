/**
 * 
 */
package gov.nih.nci.registry.service;

import gov.nih.nci.coppa.services.LimitOffset;
import gov.nih.nci.coppa.services.TooManyResultsException;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.domain.TempStudyFunding;
import gov.nih.nci.pa.domain.TempStudyIndIde;
import gov.nih.nci.pa.domain.TempStudyProtocol;
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
import gov.nih.nci.pa.iso.convert.TempStudyFundingConverter;
import gov.nih.nci.pa.iso.convert.TempStudyIndIdeConverter;
import gov.nih.nci.pa.iso.convert.TempStudyProtocolConverter;
import gov.nih.nci.pa.iso.dto.TempStudyFundingDTO;
import gov.nih.nci.pa.iso.dto.TempStudyIndIdeDTO;
import gov.nih.nci.pa.iso.dto.TempStudyProtocolDTO;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.TempStudyProtocolServiceLocal;
import gov.nih.nci.pa.util.PAUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Vrushali
 *
 */
public class MockTempStudyProtocolService implements TempStudyProtocolServiceLocal {
    static List<TempStudyProtocol> list;
    static List<TempStudyFunding> tempStudyFundingList;
    static List<TempStudyIndIde> tempStudyIndIdeList;
    static {
        list = new ArrayList<TempStudyProtocol>();
        TempStudyProtocol sp = new TempStudyProtocol();
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
        list.add(sp);
        sp = new TempStudyProtocol();
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
        list.add(sp);
        sp = new TempStudyProtocol();
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
        list.add(sp);
        tempStudyFundingList = new ArrayList<TempStudyFunding>();
        TempStudyFunding studyFunding = new TempStudyFunding();
        studyFunding.setFundingMechanismCode("fundingMechanismCode");
        studyFunding.setNciDivisionProgramCode(NciDivisionProgramCode.CCR);
        studyFunding.setSerialNumber("serialNumber");
        studyFunding.setNihInstituteCode("nihInstituteCode");
        studyFunding.setTempStudyProtocol(sp);
        tempStudyFundingList.add(studyFunding);
        
        tempStudyIndIdeList = new ArrayList<TempStudyIndIde>();
        TempStudyIndIde tempInd = new TempStudyIndIde();
        tempInd.setIndIdeNumber("indIdeNumber");
        tempInd.setIndldeTypeCode(IndldeTypeCode.IDE);
        tempInd.setGrantorCode(GrantorCode.CBER);
        tempInd.setHolderTypeCode(HolderTypeCode.INDUSTRY);
        tempInd.setExpandedAccessIndicator(Boolean.FALSE);
        tempInd.setTempStudyProtocol(sp);
        tempStudyIndIdeList.add(tempInd);
        tempInd = new TempStudyIndIde();
        tempInd.setIndIdeNumber("indIdeNumber");
        tempInd.setIndldeTypeCode(IndldeTypeCode.IDE);
        tempInd.setGrantorCode(GrantorCode.CBER);
        tempInd.setHolderTypeCode(HolderTypeCode.NCI);
        tempInd.setNciDivPrgHolderCode(NciDivisionProgramCode.CCR);
        tempInd.setExpandedAccessIndicator(Boolean.TRUE);
        tempInd.setExpandedAccessStatusCode(ExpandedAccessStatusCode.AVAILABLE);
        tempInd.setTempStudyProtocol(sp);
        tempStudyIndIdeList.add(tempInd);
        tempInd = new TempStudyIndIde();
        tempInd.setIndIdeNumber("indIdeNumber");
        tempInd.setIndldeTypeCode(IndldeTypeCode.IDE);
        tempInd.setGrantorCode(GrantorCode.CBER);
        tempInd.setHolderTypeCode(HolderTypeCode.NIH);
        tempInd.setNihInstHolderCode(NihInstituteCode.NEI);
        tempInd.setExpandedAccessIndicator(Boolean.TRUE);
        tempInd.setExpandedAccessStatusCode(ExpandedAccessStatusCode.AVAILABLE);
        tempInd.setTempStudyProtocol(sp);
        tempStudyIndIdeList.add(tempInd);
    }
    /* (non-Javadoc)
     * @see gov.nih.nci.pa.service.TempStudyProtocolService#createGrant(gov.nih.nci.pa.iso.dto.TempStudyFundingDTO)
     */
    public void createGrant(TempStudyFundingDTO tempStudyFundingDTO)
            throws PAException {
        // TODO Auto-generated method stub

    }

    /* (non-Javadoc)
     * @see gov.nih.nci.pa.service.TempStudyProtocolService#createTempStudyProtocol(gov.nih.nci.pa.iso.dto.TempStudyProtocolDTO)
     */
    public Ii createTempStudyProtocol(TempStudyProtocolDTO ispDTO)
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
    public void deleteGrantsForTempStudyProtocol(Ii tempStudyProtocolIi)
            throws PAException {
        // TODO Auto-generated method stub

    }

    /* (non-Javadoc)
     * @see gov.nih.nci.pa.service.TempStudyProtocolService#getGrantsByTempStudyProtocol(gov.nih.nci.iso21090.Ii)
     */
    public List<TempStudyFundingDTO> getGrantsByTempStudyProtocol(
            Ii tempStudyProtocolIi) throws PAException {
        List<TempStudyFundingDTO> retList = new ArrayList<TempStudyFundingDTO>(); 
        for (TempStudyFunding tsf: tempStudyFundingList) {
            if(tsf.getTempStudyProtocol().getId().equals(IiConverter.convertToLong(tempStudyProtocolIi))) {
                retList.add(TempStudyFundingConverter.convertFromDomainToDTO(tsf));
            }
        }
        return retList;
    }

    /* (non-Javadoc)
     * @see gov.nih.nci.pa.service.TempStudyProtocolService#getTempStudyProtocol(gov.nih.nci.iso21090.Ii)
     */
    public TempStudyProtocolDTO getTempStudyProtocol(Ii ii) throws PAException {
        for (TempStudyProtocol tsp: list) {
            if(tsp.getId().equals(IiConverter.convertToLong(ii))) {
                return TempStudyProtocolConverter.convertFromDomainToDTO(tsp);
            }
        }
        return new TempStudyProtocolDTO();
    }

    /* (non-Javadoc)
     * @see gov.nih.nci.pa.service.TempStudyProtocolService#search(gov.nih.nci.pa.iso.dto.TempStudyProtocolDTO, gov.nih.nci.coppa.services.LimitOffset)
     */
    public List<TempStudyProtocolDTO> search(TempStudyProtocolDTO dto,
            LimitOffset pagingParams) throws PAException,
            TooManyResultsException {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see gov.nih.nci.pa.service.TempStudyProtocolService#updateTempStudyProtocol(gov.nih.nci.pa.iso.dto.TempStudyProtocolDTO)
     */
    public TempStudyProtocolDTO updateTempStudyProtocol(
            TempStudyProtocolDTO isoDTO) throws PAException {
        TempStudyProtocolDTO isoDto = new TempStudyProtocolDTO();
        isoDto.setIdentifier(IiConverter.convertToIi("1"));
        return isoDto;
    }

    public void createIndIde(TempStudyIndIdeDTO tempStudyIndIdeDTO)
            throws PAException {
        // TODO Auto-generated method stub
        
    }

    public void deleteIndIdeForTempStudyProtocol(Ii tempStudyProtocolIi)
            throws PAException {
        // TODO Auto-generated method stub
        
    }

    public List<TempStudyIndIdeDTO> getIndIdeByTempStudyProtocol(
            Ii tempStudyProtocolIi) throws PAException {
        List<TempStudyIndIdeDTO> retList = new ArrayList<TempStudyIndIdeDTO>(); 
        for (TempStudyIndIde tsf: tempStudyIndIdeList) {
            if(tsf.getTempStudyProtocol().getId().equals(IiConverter.convertToLong(tempStudyProtocolIi))) {
                retList.add(TempStudyIndIdeConverter.convertFromDomainToDTO(tsf));
            }
        }
        return retList;
    }

}
