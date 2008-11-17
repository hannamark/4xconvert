package gov.nih.nci.pa.iso.dto;

import static org.junit.Assert.assertEquals;
import gov.nih.nci.pa.enums.AccrualReportingMethodCode;
import gov.nih.nci.pa.enums.ActualAnticipatedTypeCode;
import gov.nih.nci.pa.enums.AllocationCode;
import gov.nih.nci.pa.enums.PhaseCode;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.iso.util.TsConverter;

import java.sql.Timestamp;
import java.util.Date;

import org.junit.Test;

public class InterventionalStudyProtocolDTOTest {
    
    @Test
    public void interventionalStudyProtocolDTOTest() {
        InterventionalStudyProtocolDTO ispDTO = createInterventionalStudyProtocolDTOObj();
        assertEquals(ispDTO.getAccrualReportingMethodCode().getCode(),AccrualReportingMethodCode.ABBREVIATED.getCode()); 
        assertEquals(ispDTO.getAcronym().getValue(),"abcd");
        assertEquals(ispDTO.getAllocationCode().getCode(),AllocationCode.NA.getCode());
        assertEquals(ispDTO.getDelayedpostingIndicator().getValue(),Boolean.FALSE);
        assertEquals(ispDTO.getExpandedAccessIndicator().getValue(),Boolean.FALSE);
        assertEquals(ispDTO.getFdaRegulatedIndicator().getValue(),Boolean.TRUE);
        assertEquals(ispDTO.getOfficialTitle().getValue(),"Phase Ii trial");
        assertEquals(ispDTO.getPhaseCode().getCode(),PhaseCode.I.getCode());
    }
    
    public static InterventionalStudyProtocolDTO createInterventionalStudyProtocolDTOObj() {
        InterventionalStudyProtocolDTO ispDTO = new InterventionalStudyProtocolDTO();
        ispDTO.setAccrualReportingMethodCode(CdConverter.convertStringToCd(AccrualReportingMethodCode.ABBREVIATED.getCode()));
        ispDTO.setAcronym(StConverter.convertToSt("abcd"));
        ispDTO.setAllocationCode(CdConverter.convertStringToCd(AllocationCode.NA.getCode()));
        ispDTO.setDelayedpostingIndicator(BlConverter.convertToBl(Boolean.FALSE));
        ispDTO.setExpandedAccessIndicator(BlConverter.convertToBl(Boolean.FALSE));
        ispDTO.setFdaRegulatedIndicator(BlConverter.convertToBl(Boolean.TRUE));
        ispDTO.setOfficialTitle(StConverter.convertToSt("Phase Ii trial"));
        Timestamp now = new Timestamp((new Date()).getTime());
        ispDTO.setStartDate(TsConverter.convertToTs(now));
        ispDTO.setStartDateTypeCode(CdConverter.convertStringToCd(ActualAnticipatedTypeCode.ACTUAL.getCode()));
        ispDTO.setPrimaryCompletionDate(TsConverter.convertToTs(now));
        ispDTO.setPrimaryCompletionDateTypeCode(CdConverter.convertStringToCd(ActualAnticipatedTypeCode.ACTUAL.getCode()));
        ispDTO.setPhaseCode(CdConverter.convertStringToCd(PhaseCode.I.getCode()));
        return ispDTO;
    }

}
