package gov.nih.nci.outcomes.svc.util;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import gov.nih.nci.accrual.outweb.enums.AutopsyPerformed;
import gov.nih.nci.accrual.util.TestSchema;
import gov.nih.nci.outcomes.svc.AbstractOutcomesSvcTest;
import gov.nih.nci.outcomes.svc.dto.DeathInformationSvcDto;
import gov.nih.nci.outcomes.svc.dto.PatientSvcDto;
import gov.nih.nci.pa.enums.AutopsyDeathCause;
import gov.nih.nci.pa.enums.DeathCause;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.util.PAUtil;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.junit.Test;

/**
 * @author Kalpana Guthikonda
 * @since Mar 30, 2010
 *
 */

public class DeathInformationSvcTest extends AbstractOutcomesSvcTest {
    
    @Test
    public void deathInformationTest() throws Exception {
        PatientSvcDto svcDto = new PatientSvcDto();
        svcDto.setIdentifier(IiConverter.convertToIi(TestSchema.studySubjects.get(1).getId()));
        
        DeathInformationSvcDto deathInformation = new DeathInformationSvcDto();
        deathInformation.setCause(CdConverter.convertToCd(DeathCause.DRUG_RELATED));
        deathInformation.setEventDate(TsConverter.convertToTs(new Timestamp(new Date().getTime())));
        deathInformation.setAutopsyInd(CdConverter.convertToCd(AutopsyPerformed.NO));
        deathInformation.setAction(SvcConstants.CREATE);
        
        svcDto.setDeathInformation(deathInformation);
        svcDto = osb.write(svcDto);
        assertFalse(PAUtil.isIiNull(svcDto.getDeathInformation().getIdentifier()));
        
        svcDto.setDeathInformation(new DeathInformationSvcDto());
        List<PatientSvcDto> pSvcList = osb.get(svcDto);
        assertNotNull(pSvcList.get(0).getDeathInformation().getIdentifier().getExtension());
        
        deathInformation = pSvcList.get(0).getDeathInformation();
        deathInformation.setAutopsyInd(CdConverter.convertToCd(AutopsyPerformed.YES));
        deathInformation.setAutopsySite(CdConverter.convertStringToCd("Abdomen"));
        deathInformation.setCauseByAutopsy(CdConverter.convertToCd(AutopsyDeathCause.INFECTION));
        deathInformation.setAction(SvcConstants.UPDATE);
        svcDto.setDeathInformation(deathInformation);
        svcDto = osb.write(svcDto);
        
        svcDto.setDeathInformation(null);
        pSvcList = osb.get(svcDto);
        assertNull(pSvcList.get(0).getDeathInformation());
    }
}
