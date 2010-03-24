package gov.nih.nci.outcomes.svc.util;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import gov.nih.nci.accrual.util.TestSchema;
import gov.nih.nci.iso21090.Pq;
import gov.nih.nci.outcomes.svc.AbstractOutcomesSvcTest;
import gov.nih.nci.outcomes.svc.dto.PatientSvcDto;
import gov.nih.nci.outcomes.svc.dto.TumorMarkerSvcDto;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.util.PAUtil;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

/**
 * @author Kalpana Guthikonda
 * @since Mar 19, 2010
 *
 */

public class TumorMarkerSvcTest extends AbstractOutcomesSvcTest {
    
    @Test
    public void tumorMarkerTest() throws Exception {
        PatientSvcDto svcDto = new PatientSvcDto();
        svcDto.setIdentifier(IiConverter.convertToIi(TestSchema.studySubjects.get(1).getId()));
        
        List<TumorMarkerSvcDto> listOfSvcDto = new ArrayList<TumorMarkerSvcDto>();
        TumorMarkerSvcDto tumor = new TumorMarkerSvcDto();
        tumor.setTumorMarker(CdConverter.convertStringToCd("Phospho-HER-2/neu"));
        Pq pq = new Pq();
        pq.setUnit("Unit/g");
        tumor.setTumorMarkerValue(StConverter.convertToSt("2"));
        tumor.setTmvUom(pq);
        tumor.setAction(SvcConstants.CREATE);
        
        listOfSvcDto.add(tumor);
        
        tumor = new TumorMarkerSvcDto();
        tumor.setTumorMarker(CdConverter.convertStringToCd("HER-2/neu"));
        tumor.setTumorMarkerValue(StConverter.convertToSt("12"));
        tumor.setAction(SvcConstants.CREATE);
        
        listOfSvcDto.add(tumor);
        svcDto.setTumorMarkers(listOfSvcDto);
        svcDto = osb.write(svcDto);
        assertFalse(PAUtil.isIiNull(svcDto.getTumorMarkers().get(0).getIdentifier()));
        
        svcDto.setTumorMarkers(new ArrayList<TumorMarkerSvcDto>());
        List<PatientSvcDto> pSvcList = osb.get(svcDto);
        assertNotNull(pSvcList.get(0).getTumorMarkers());
        
        svcDto.setTumorMarkers(null);
        pSvcList = osb.get(svcDto);
        assertNull(pSvcList.get(0).getTumorMarkers());
    }
}
