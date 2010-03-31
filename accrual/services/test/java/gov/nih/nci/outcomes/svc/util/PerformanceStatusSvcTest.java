package gov.nih.nci.outcomes.svc.util;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import gov.nih.nci.accrual.util.TestSchema;
import gov.nih.nci.outcomes.svc.AbstractOutcomesSvcTest;
import gov.nih.nci.outcomes.svc.dto.PatientSvcDto;
import gov.nih.nci.outcomes.svc.dto.PerformanceStatusSvcDto;
import gov.nih.nci.pa.enums.KarnoskyPerformanceStatusCode;
import gov.nih.nci.pa.enums.PerformanceSystemCode;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.util.PAUtil;

import java.util.List;

import org.junit.Test;

/**
 * @author Kalpana Guthikonda
 * @since Mar 30, 2010
 *
 */

public class PerformanceStatusSvcTest extends AbstractOutcomesSvcTest {
    
    @Test
    public void performanceStatusTest() throws Exception {
        PatientSvcDto svcDto = new PatientSvcDto();
        svcDto.setIdentifier(IiConverter.convertToIi(TestSchema.studySubjects.get(1).getId()));
        
        PerformanceStatusSvcDto performance = new PerformanceStatusSvcDto();
        performance.setPerformanceSystem(CdConverter.convertToCd(PerformanceSystemCode.Karnofsky));
        performance.setPerformanceStatus(CdConverter.convertToCd(KarnoskyPerformanceStatusCode.HUNDRED));
        performance.setAction(SvcConstants.CREATE);
        
        svcDto.setPerformanceStatus(performance);
        svcDto = osb.write(svcDto);
        assertFalse(PAUtil.isIiNull(svcDto.getPerformanceStatus().getIdentifier()));
        
        svcDto.setPerformanceStatus(new PerformanceStatusSvcDto());
        List<PatientSvcDto> pSvcList = osb.get(svcDto);
        assertNotNull(pSvcList.get(0).getPerformanceStatus().getIdentifier().getExtension());
        
        performance = pSvcList.get(0).getPerformanceStatus();
        performance.setPerformanceStatus(CdConverter.convertToCd(KarnoskyPerformanceStatusCode.EIGHTY));
        performance.setAction(SvcConstants.UPDATE);
        svcDto.setPerformanceStatus(performance);
        svcDto = osb.write(svcDto);
        
        svcDto.setPerformanceStatus(null);
        pSvcList = osb.get(svcDto);
        assertNull(pSvcList.get(0).getPerformanceStatus());
    }
}
