package gov.nih.nci.accrual.service.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import gov.nih.nci.accrual.service.StudySubjectServiceLocal;
import gov.nih.nci.accrual.util.PaServiceLocator;
import gov.nih.nci.accrual.util.ServiceLocatorPaInterface;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.domain.AccrualDisease;
import gov.nih.nci.pa.domain.StudySubject;
import gov.nih.nci.pa.enums.PrimaryPurposeCode;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.StudyProtocolServiceRemote;
import gov.nih.nci.pa.util.AbstractHibernateTestCase;
import gov.nih.nci.pa.util.TestSchema;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class AccrualDiseaseServiceTest  extends AbstractHibernateTestCase {

    AccrualDisease criteria;
    AccrualDiseaseBeanLocal bean;
    StudySubjectServiceLocal ssBean;
    SearchStudySiteService sssBean;
    StudyProtocolServiceRemote spBean;

    @Before
    public void setUp() throws Exception {
        ssBean = mock(StudySubjectServiceLocal.class);
        sssBean = mock(SearchStudySiteService.class);
        spBean = mock(StudyProtocolServiceRemote.class);
        bean = new AccrualDiseaseBeanLocal();
        bean.setStudySubjectSvc(ssBean);
        bean.setSearchStudySiteSvc(sssBean);
        ServiceLocatorPaInterface svcLocator = mock(ServiceLocatorPaInterface.class);
        when(svcLocator.getStudyProtocolService()).thenReturn(spBean);
        PaServiceLocator.getInstance().setServiceLocator(svcLocator);
        TestSchema.primeData();
        criteria = new AccrualDisease();
    }

    @Test
    public void getTest() {
        assertNull(bean.get(-1L));
        assertNotNull(bean.get(1L));
    }

    @Test
    public void getIiTest() {
        assertNull(bean.get(IiConverter.convertToIi(-1L)));
        assertNotNull(bean.get(IiConverter.convertToIi(1L)));
        Ii ii = new Ii();
        ii.setExtension("xyzzy");
        ii.setIdentifierName("ICD9");
        assertNull(bean.get(ii));
        ii.setExtension("code2");
        assertNotNull(bean.get(ii));
        ii.setIdentifierName("SDC");
        ii.setExtension("SDC01");
        assertNotNull(bean.get(ii));
    }

    @Test
    public void getByCode() {
        assertNull(bean.getByCode("ode1"));
        assertNotNull(bean.getByCode("code1"));
    }

    @Test
    public void searchTest() {
        criteria.setId(3L);
        assertEquals(1, bean.search(criteria).size());
        criteria.setId(null);
        criteria.setCodeSystem("ICD9");
        assertEquals(5, bean.search(criteria).size());
        criteria.setCodeSystem(null);
        criteria.setDiseaseCode("ode1");
        assertEquals(0, bean.search(criteria).size());
        criteria.setDiseaseCode("code1");
        assertEquals(1, bean.search(criteria).size());
        criteria.setDiseaseCode(null);
        criteria.setPreferredName("name2");
        assertEquals(1, bean.search(criteria).size());
        criteria.setPreferredName("ame2");
        assertEquals(1, bean.search(criteria).size());
    }

    @Test
    public void getTrialCodeSystemTest() throws Exception {
        assertNull(bean.getTrialCodeSystem(null));
        assertNull(bean.getTrialCodeSystem(1L));

        StudySubject ss = new StudySubject();
        AccrualDisease ad = new AccrualDisease();
        ss.setDisease(ad);
        ad.setCodeSystem("xyzzy");
        when(ssBean.searchActiveByStudyProtocol(anyLong())).thenReturn(ss);
        assertEquals("xyzzy", bean.getTrialCodeSystem(1L));

        when(ssBean.searchActiveByStudyProtocol(anyLong())).thenThrow(new PAException());
        assertNull(bean.getTrialCodeSystem(1L));
    }

    @Test
    public void getValidCodeSystemsTest() throws Exception {
        List<String> csList = bean.getValidCodeSystems(1L);
        assertEquals("ICD9", csList.get(0));
        assertEquals("SDC", csList.get(1));

        StudySubject ss = new StudySubject();
        AccrualDisease ad = new AccrualDisease();
        ss.setDisease(ad);
        ad.setCodeSystem("xyzzy");
        when(ssBean.searchActiveByStudyProtocol(anyLong())).thenReturn(ss);
        csList = bean.getValidCodeSystems(1L);
        assertEquals(1, csList.size());
        assertEquals("xyzzy", csList.get(0));
    }

    @Test
    public void diseaseCodeMandatoryTest() throws Exception {
        // DCP
        when(sssBean.isStudySiteHasDCPId(any(Ii.class))).thenReturn(true);
        assertFalse(bean.diseaseCodeMandatory(1L));

        // Prevention trial
        when(sssBean.isStudySiteHasDCPId(any(Ii.class))).thenReturn(false);
        StudyProtocolDTO sp = new StudyProtocolDTO();
        sp.setPrimaryPurposeCode(CdConverter.convertToCd(PrimaryPurposeCode.PREVENTION));
        when(spBean.getStudyProtocol(any(Ii.class))).thenReturn(sp);
        assertFalse(bean.diseaseCodeMandatory(1L));

        // Disease mandatory
        sp.setPrimaryPurposeCode(CdConverter.convertToCd(PrimaryPurposeCode.TREATMENT));
        assertTrue(bean.diseaseCodeMandatory(1L));

        // Exception
        when(spBean.getStudyProtocol(any(Ii.class))).thenThrow(new PAException());
        assertTrue(bean.diseaseCodeMandatory(1L));
    }
}
