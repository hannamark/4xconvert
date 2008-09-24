package gov.nih.nci.pa.iso.dto;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import gov.nih.nci.pa.enums.AccrualReportingMethodCode;
import gov.nih.nci.pa.enums.AllocationCode;
import gov.nih.nci.pa.enums.PhaseCode;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.StConverter;

public class InterventionalStudyProtocolDTOTest {
    
    @Test
    public void interventionalStudyProtocolDTOTest() {
        InterventionalStudyProtocolDTO ispDTO = createInterventionalStudyProtocolDTOObj();
        assertEquals(ispDTO.getAccrualReportingMethodCode().getCode(),AccrualReportingMethodCode.ABBREVIATED.getCode()); 
        assertEquals(ispDTO.getAcronym().getValue(),"abcd");
        assertEquals(ispDTO.getAllocationCode().getCode(),AllocationCode.NA.getCode());
        assertEquals(ispDTO.getDataMonitoringCommitteInd().getValue(),Boolean.TRUE);
        assertEquals(ispDTO.getDelayedpostingIndicator().getValue(),Boolean.FALSE);
        assertEquals(ispDTO.getExpandedAccessIndicator().getValue(),Boolean.FALSE);
        assertEquals(ispDTO.getFdaRegulatedIndicator().getValue(),Boolean.TRUE);
        assertEquals(ispDTO.getIndIdeIndicator().getValue(),Boolean.TRUE);
        assertEquals(ispDTO.getOfficialTitle().getValue(),"Phase Ii trial");
        assertEquals(ispDTO.getPhaseCode().getCode(),PhaseCode.I.getCode());
    }
    
    public static InterventionalStudyProtocolDTO createInterventionalStudyProtocolDTOObj() {
        InterventionalStudyProtocolDTO ispDTO = new InterventionalStudyProtocolDTO();
        ispDTO.setAccrualReportingMethodCode(CdConverter.convertStringToCd(AccrualReportingMethodCode.ABBREVIATED.getCode()));
        ispDTO.setAcronym(StConverter.convertToSt("abcd"));
        ispDTO.setAllocationCode(CdConverter.convertStringToCd(AllocationCode.NA.getCode()));
        ispDTO.setDataMonitoringCommitteInd(BlConverter.convertToBl(Boolean.TRUE));
        ispDTO.setDelayedpostingIndicator(BlConverter.convertToBl(Boolean.FALSE));
        ispDTO.setExpandedAccessIndicator(BlConverter.convertToBl(Boolean.FALSE));
        ispDTO.setFdaRegulatedIndicator(BlConverter.convertToBl(Boolean.TRUE));
        ispDTO.setIndIdeIndicator(BlConverter.convertToBl(Boolean.TRUE));
        ispDTO.setOfficialTitle(StConverter.convertToSt("Phase Ii trial"));
        ispDTO.setPhaseCode(CdConverter.convertStringToCd(PhaseCode.I.getCode()));
        return ispDTO;
    }

}
