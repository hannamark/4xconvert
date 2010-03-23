package gov.nih.nci.outcomes.svc.util;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import gov.nih.nci.accrual.outweb.enums.PathologyGradeSystems;
import gov.nih.nci.accrual.outweb.enums.PathologyGrades;
import gov.nih.nci.accrual.util.TestSchema;
import gov.nih.nci.outcomes.svc.AbstractOutcomesSvcTest;
import gov.nih.nci.outcomes.svc.dto.PathologySvcDto;
import gov.nih.nci.outcomes.svc.dto.PatientSvcDto;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.util.PAUtil;

import java.util.List;

import org.junit.Test;

/**
 * @author Kalpana Guthikonda
 * @since Mar 18, 2010
 *
 */

public class PathologySvcTest extends AbstractOutcomesSvcTest {
    
    @Test
    public void pathologyTest() throws Exception {
        PatientSvcDto svcDto = new PatientSvcDto();
        svcDto.setIdentifier(IiConverter.convertToIi(TestSchema.studySubjects.get(1).getId()));
        
        PathologySvcDto pathology = new PathologySvcDto();
        pathology.setGrade(CdConverter.convertToCd(PathologyGrades.ANAPLASTIC));
        pathology.setGradeSystem(CdConverter.convertToCd(PathologyGradeSystems.FUHRMAN));
        pathology.setDescription(StConverter.convertToSt("Pathology Description"));
        pathology.setAction(SvcConstants.CREATE);
        
        svcDto.setPathology(pathology);
        svcDto = osb.write(svcDto);
        assertFalse(PAUtil.isIiNull(svcDto.getPathology().getIdentifier()));
        
        svcDto.setPathology(new PathologySvcDto());
        List<PatientSvcDto> pSvcList = osb.get(svcDto);
        assertNotNull(pSvcList.get(0).getPathology().getIdentifier().getExtension());
        
        pathology = pSvcList.get(0).getPathology();
        pathology.setGradeSystem(CdConverter.convertToCd(PathologyGradeSystems.BLOOM_RICHARDSON));
        pathology.setAction(SvcConstants.UPDATE);
        svcDto.setPathology(pathology);
        svcDto = osb.write(svcDto);
        
        svcDto.setPathology(null);
        pSvcList = osb.get(svcDto);
        assertNull(pSvcList.get(0).getPathology());
    }
}
