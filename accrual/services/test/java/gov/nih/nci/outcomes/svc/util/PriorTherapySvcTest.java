package gov.nih.nci.outcomes.svc.util;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertEquals;
import gov.nih.nci.accrual.util.TestSchema;
import gov.nih.nci.iso21090.Pq;
import gov.nih.nci.outcomes.svc.AbstractOutcomesSvcTest;
import gov.nih.nci.outcomes.svc.dto.AbstractPriorTherapiesItemDto;
import gov.nih.nci.outcomes.svc.dto.PatientSvcDto;
import gov.nih.nci.outcomes.svc.dto.PriorTherapiesItemSvcDto;
import gov.nih.nci.outcomes.svc.dto.PriorTherapySvcDto;
import gov.nih.nci.pa.enums.PriorTherapyTypeCode;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.util.PAUtil;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

/**
 * @author Kalpana Guthikonda
 * @since Mar 31, 2010
 *
 */

public class PriorTherapySvcTest extends AbstractOutcomesSvcTest {
    
    @Test
    public void priorTherapyTest() throws Exception {
        PatientSvcDto svcDto = new PatientSvcDto();
        svcDto.setIdentifier(IiConverter.convertToIi(TestSchema.studySubjects.get(1).getId()));
        
        PriorTherapySvcDto priorTherapy = new PriorTherapySvcDto();
        priorTherapy.setHasPrior(true);
        Pq pq = new Pq();
        pq.setValue(new BigDecimal("1"));
        priorTherapy.setTotalRegimenNum(pq);
        pq = new Pq();
        pq.setValue(new BigDecimal("0"));
        priorTherapy.setChemoRegimenNum(pq);
        List<AbstractPriorTherapiesItemDto> itemList = new ArrayList<AbstractPriorTherapiesItemDto>();
        PriorTherapiesItemSvcDto item = new PriorTherapiesItemSvcDto();
        item.setAction(SvcConstants.CREATE);
        item.setType(CdConverter.convertToCd(PriorTherapyTypeCode.BONE_MARROW_TRANSPLANT));
        item.setDescription(StConverter.convertToSt("description"));
        itemList.add(item);
        priorTherapy.setItemList(itemList);
        priorTherapy.setAction(SvcConstants.CREATE);
        
        svcDto.setPriorTherapy(priorTherapy);
        svcDto = osb.write(svcDto);
        assertFalse(PAUtil.isIiNull(svcDto.getPriorTherapy().getIdentifier()));
        
        svcDto.setPriorTherapy(new PriorTherapySvcDto());
        List<PatientSvcDto> pSvcList = osb.get(svcDto);
        assertNotNull(pSvcList.get(0).getPriorTherapy().getIdentifier().getExtension());
        assertEquals(true, pSvcList.get(0).getPriorTherapy().getHasPrior());
        
        priorTherapy = pSvcList.get(0).getPriorTherapy();
        priorTherapy.setAction(SvcConstants.UPDATE);
        svcDto.setPriorTherapy(priorTherapy);
        svcDto = osb.write(svcDto);
        
        svcDto.setPriorTherapy(null);
        pSvcList = osb.get(svcDto);
        assertNull(pSvcList.get(0).getPriorTherapy());
    }
}
