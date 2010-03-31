package gov.nih.nci.outcomes.svc.util;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import gov.nih.nci.accrual.util.TestSchema;
import gov.nih.nci.iso21090.Pq;
import gov.nih.nci.iso21090.Ts;
import gov.nih.nci.outcomes.svc.AbstractOutcomesSvcTest;
import gov.nih.nci.outcomes.svc.dto.CycleSvcDto;
import gov.nih.nci.outcomes.svc.dto.PatientSvcDto;
import gov.nih.nci.outcomes.svc.dto.RadiationSvcDto;
import gov.nih.nci.outcomes.svc.dto.TreatmentRegimenSvcDto;
import gov.nih.nci.pa.enums.RadiationProcedureTypeCode;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.util.PAUtil;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;

/**
 * @author Kalpana Guthikonda
 * @since Mar 30, 2010
 *
 */

public class RadiationSvcTest extends AbstractOutcomesSvcTest {
    
    @Test
    public void radiationTest() throws Exception {
        PatientSvcDto svcDto = new PatientSvcDto();
        svcDto.setIdentifier(IiConverter.convertToIi(TestSchema.studySubjects.get(1).getId()));
        
        List<TreatmentRegimenSvcDto> trList = new ArrayList<TreatmentRegimenSvcDto>();
        TreatmentRegimenSvcDto treatmentRegimen = new TreatmentRegimenSvcDto();
        List<CycleSvcDto> cycles = new ArrayList<CycleSvcDto>();
        CycleSvcDto cycle = new CycleSvcDto();
        Ts ts = new Ts();
        ts.setValue(new Date());
        cycle.setIdentifier(IiConverter.convertToIi(TestSchema.performedActivities.get(1).getId()));
        
        List<RadiationSvcDto> radiations = new ArrayList<RadiationSvcDto>();
        RadiationSvcDto rad = new RadiationSvcDto();
        rad.setAction(SvcConstants.CREATE);
        Pq dose = new Pq();
        dose.setValue(new BigDecimal("2"));
        dose.setUnit("Unit/g");
        rad.setDose(dose);
        rad.setDoseFreq(CdConverter.convertStringToCd("QIS"));
        rad.setRadDate(ts);
        rad.setTotalDose(dose);
        rad.setType(CdConverter.convertToCd(RadiationProcedureTypeCode.EXTENSIVE_RADIATION));
        radiations.add(rad);
        
        cycle.setRadiations(radiations);
        cycles.add(cycle);
        treatmentRegimen.setIdentifier(IiConverter.convertToIi(TestSchema.performedActivities.get(0).getId()));
        treatmentRegimen.setCycles(cycles);
        trList.add(treatmentRegimen);
        svcDto.setTreatmentRegimens(trList);
        svcDto = osb.write(svcDto);
        assertFalse(PAUtil.isIiNull(svcDto.getTreatmentRegimens().get(0).getCycles().get(0).getRadiations().get(0).getIdentifier()));
        
        trList = new ArrayList<TreatmentRegimenSvcDto>();
        treatmentRegimen = new TreatmentRegimenSvcDto();
        treatmentRegimen.setIdentifier(IiConverter.convertToIi(TestSchema.performedActivities.get(0).getId()));
        cycles = new ArrayList<CycleSvcDto>(); 
        cycle = new CycleSvcDto(); 
        cycle.setIdentifier(IiConverter.convertToIi(TestSchema.performedActivities.get(1).getId()));
        cycle.setRadiations(new ArrayList<RadiationSvcDto>());
        cycles.add(cycle);
        treatmentRegimen.setCycles(cycles);
        trList.add(treatmentRegimen);
        svcDto.setTreatmentRegimens(trList);
        List<PatientSvcDto> pSvcList = osb.get(svcDto);
        assertNotNull(pSvcList.get(0).getTreatmentRegimens().get(0).getCycles().get(0).getRadiations().get(0).getIdentifier().getExtension());
        

        trList = new ArrayList<TreatmentRegimenSvcDto>();
        cycles = new ArrayList<CycleSvcDto>();
        treatmentRegimen = new TreatmentRegimenSvcDto();
        radiations = new ArrayList<RadiationSvcDto>();
        rad = pSvcList.get(0).getTreatmentRegimens().get(0).getCycles().get(0).getRadiations().get(0);
        rad.setType(CdConverter.convertToCd(RadiationProcedureTypeCode.BRACHYTHERAPY));
        rad.setAction(SvcConstants.UPDATE);
        cycle = new CycleSvcDto();
        cycle.setIdentifier(IiConverter.convertToIi(TestSchema.performedActivities.get(1).getId()));
        radiations.add(rad);        
        cycle.setRadiations(radiations);
        cycles.add(cycle);
        treatmentRegimen.setIdentifier(IiConverter.convertToIi(TestSchema.performedActivities.get(0).getId()));
        treatmentRegimen.setCycles(cycles);
        trList.add(treatmentRegimen);
        svcDto.setTreatmentRegimens(trList);
        svcDto = osb.write(svcDto);        
        pSvcList = osb.get(svcDto);
        
        svcDto.setTreatmentRegimens(null);
        pSvcList = osb.get(svcDto);
        assertNull(pSvcList.get(0).getTreatmentRegimens());
    }
}
