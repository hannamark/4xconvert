package gov.nih.nci.pa.iso.convert;

import static org.junit.Assert.assertEquals;
import gov.nih.nci.coppa.iso.Ivl;
import gov.nih.nci.coppa.iso.Pq;
import gov.nih.nci.pa.domain.PlannedSubstanceAdministration;
import gov.nih.nci.pa.domain.StudyProtocol;
import gov.nih.nci.pa.enums.UnitsCode;
import gov.nih.nci.pa.iso.dto.PlannedSubstanceAdministrationDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.IvlConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.util.HibernateUtil;
import gov.nih.nci.pa.util.TestSchema;

import java.math.BigDecimal;

import org.hibernate.Session;
import org.junit.Before;
import org.junit.Test;

public class PlannedSubstanceAdministrationConverterTest {

    private Session sess;

    @Before
    public void setUp() throws Exception {
      TestSchema.reset1();
      TestSchema.primeData();
      sess = HibernateUtil.getCurrentSession();
    }

    @Test
    public void convertFromDomainToDTO() throws Exception {
      StudyProtocol sp = (StudyProtocol) sess.load(StudyProtocol.class, TestSchema.studyProtocolIds.get(0));
      PlannedSubstanceAdministration bo = new PlannedSubstanceAdministration();
      bo.setId(123L);
      bo.setDoseMinValue(new BigDecimal("12"));
      bo.setDoseMinUnit("10Milligrams");
      bo.setDoseMaxValue(new BigDecimal("24"));
      bo.setDoseMaxUnit("15Milligrams");
      bo.setDoseDescription("TestDose");
      bo.setDoseFormCode("TABLET");
      bo.setDoseFrequencyCode("BID");
      bo.setDoseRegimen("doseRegimen");
      bo.setDoseTotalMaxUnit("doseTotalUom");
      bo.setDoseTotalMaxValue(new BigDecimal("12"));
      bo.setRouteOfAdministrationCode("ORAL");
      bo.setStudyProtocol(sp);
      bo.setDoseDurationValue(new BigDecimal("24"));
      bo.setDoseDurationUnit("15Milligrams");
      PlannedSubstanceAdministrationDTO dto = PlannedSubstanceAdministrationConverter.convertFromDomainToDTO(bo);
      assertPlannedSubstanceAdministrationConverter(bo, dto);
    }

    private void assertPlannedSubstanceAdministrationConverter(PlannedSubstanceAdministration bo,
            PlannedSubstanceAdministrationDTO dto) {
      assertEquals(bo.getId(), IiConverter.convertToLong(dto.getIdentifier()));
      assertEquals(bo.getDoseDescription(),  dto.getDoseDescription().getValue());
      assertEquals(bo.getDoseRegimen(),  dto.getDoseRegimen().getValue());
      assertEquals(bo.getStudyProtocol().getId(), IiConverter.convertToLong(dto.getStudyProtocolIdentifier()));
      assertEquals(bo.getDoseMinValue(),  dto.getDose().getLow().getValue());
      assertEquals(bo.getDoseMinUnit(),  dto.getDose().getLow().getUnit());
      assertEquals(bo.getDoseTotalMinValue(),  dto.getDoseTotal().getLow().getValue());
      assertEquals(bo.getDoseTotalMinUnit(),  dto.getDoseTotal().getLow().getUnit());
      assertEquals(bo.getRouteOfAdministrationCode(),  dto.getRouteOfAdministrationCode().getCode());
      assertEquals(bo.getDoseDurationUnit(),  dto.getDoseDuration().getUnit());
      assertEquals(bo.getDoseDurationValue(),  dto.getDoseDuration().getValue());
    }

    @Test
    public void convertFromDTOToDomain() throws Exception {
      StudyProtocol sp = (StudyProtocol) sess.load(StudyProtocol.class, TestSchema.studyProtocolIds.get(0));
      PlannedSubstanceAdministrationDTO dto = new PlannedSubstanceAdministrationDTO();
      dto.setIdentifier(IiConverter.convertToIi((Long) null));
      dto.setDoseDescription(StConverter.convertToSt("Dose"));
      dto.setDoseRegimen(StConverter.convertToSt(">"));
      
      IvlConverter.JavaPq low  = new IvlConverter.JavaPq(UnitsCode.YEARS.getCode(), new BigDecimal("2"), null);
      IvlConverter.JavaPq high  = new IvlConverter.JavaPq(UnitsCode.YEARS.getCode(), new BigDecimal("8"), null);
      Ivl<Pq> ivl = IvlConverter.convertPq().convertToIvl(low, high);      
      dto.setDose(ivl);
      
      IvlConverter.JavaPq lowTotal  = new IvlConverter.JavaPq(UnitsCode.YEARS.getCode(), new BigDecimal("2"), null);
      IvlConverter.JavaPq highTotal  = new IvlConverter.JavaPq(UnitsCode.YEARS.getCode(), new BigDecimal("4"), null);
      Ivl<Pq> ivlTotal = IvlConverter.convertPq().convertToIvl(lowTotal, highTotal);
      dto.setDoseTotal(ivlTotal);
      
      dto.setDoseFormCode(CdConverter.convertStringToCd("TABLET"));
      dto.setDoseFrequencyCode(CdConverter.convertStringToCd("BID"));
      dto.setRouteOfAdministrationCode(CdConverter.convertStringToCd("ORAL"));
      dto.setStudyProtocolIdentifier(IiConverter.convertToIi(sp.getId()));
      Pq pqDuration = new Pq();
      pqDuration.setValue(new BigDecimal("4"));
      pqDuration.setUnit(UnitsCode.HOURS.getCode());
      dto.setDoseDuration(pqDuration);
      PlannedSubstanceAdministration bo = PlannedSubstanceAdministrationConverter.convertFromDTOToDomain(dto);
      assertPlannedSubstanceAdministrationConverter(bo, dto);
    }

}
