package gov.nih.nci.outcomes.svc.util;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import gov.nih.nci.accrual.outweb.enums.StagingMethods;
import gov.nih.nci.accrual.util.TestSchema;
import gov.nih.nci.outcomes.svc.AbstractOutcomesSvcTest;
import gov.nih.nci.outcomes.svc.dto.PatientSvcDto;
import gov.nih.nci.outcomes.svc.dto.StagingSvcDto;
import gov.nih.nci.pa.enums.StagingSystemCode;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.util.PAUtil;

import java.util.List;

import org.junit.Test;

/**
 * @author Kalpana Guthikonda
 * @since Mar 19, 2010
 *
 */

public class StagingSvcTest extends AbstractOutcomesSvcTest {
    
    @Test
    public void stagingTest() throws Exception {
        PatientSvcDto svcDto = new PatientSvcDto();
        svcDto.setIdentifier(IiConverter.convertToIi(TestSchema.studySubjects.get(1).getId()));
        
        StagingSvcDto staging = new StagingSvcDto();
        staging.setMethod(CdConverter.convertToCd(StagingMethods.CLINICAL));
        staging.setTt(StConverter.convertToSt("11"));
        staging.setMm(StConverter.convertToSt("12"));
        staging.setNn(StConverter.convertToSt("13"));
        staging.setStage(StConverter.convertToSt("6"));
        staging.setSystem(CdConverter.convertToCd(StagingSystemCode.WHITMORE_JEWETT));
        staging.setAction(SvcConstants.CREATE);
        
        svcDto.setStaging(staging);
        svcDto = osb.write(svcDto);
        assertFalse(PAUtil.isIiNull(svcDto.getStaging().getIdentifier()));
        
        svcDto.setStaging(new StagingSvcDto());
        List<PatientSvcDto> pSvcList = osb.get(svcDto);
        assertNotNull(pSvcList.get(0).getStaging().getIdentifier().getExtension());
        
        staging = pSvcList.get(0).getStaging();
        staging.setSystem(CdConverter.convertToCd(StagingSystemCode.AJCC));
        staging.setAction(SvcConstants.UPDATE);
        svcDto.setStaging(staging);
        svcDto = osb.write(svcDto);
        
        svcDto.setStaging(null);
        pSvcList = osb.get(svcDto);
        assertNull(pSvcList.get(0).getStaging());
    }
}
