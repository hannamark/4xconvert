package gov.nih.nci.accrual.service;

import static org.junit.Assert.assertNotNull;
import gov.nih.nci.accrual.service.util.BaseLookUpBean;
import gov.nih.nci.accrual.service.util.BaseLookUpService;
import gov.nih.nci.pa.domain.DoseFrequency;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class BaseLookUpServiceTest extends AbstractServiceTest<BaseLookUpService> {

    @Override
    @Before
    public void instantiateServiceBean() throws Exception {
        bean = new BaseLookUpBean();
    }
    @Test
    public void search() throws Exception {
        DoseFrequency criteria = new DoseFrequency();
        criteria.setCode("Q");
        List<DoseFrequency> list = new ArrayList<DoseFrequency>();
        list.addAll(bean.search(criteria));
        assertNotNull(list);
        for (DoseFrequency df :  list) {
            assertNotNull(bean.getById(df));
            assertNotNull(bean.getByCode(df));
        }
    }
    
    @Test
    public void testValidateLookUp() throws Exception {
        bean.validateLookUp("Abdomen", "ANATOMIC_SITES", "CODE");
        bean.validateLookUp("Unit/g", "UNIT_OF_MEASUREMENT", "CODE");
        bean.validateLookUp("AURICULAR (OTIC)", "ROUTE_OF_ADMINISTRATION", "CODE");
        bean.validateLookUp("Mammary Gland", "LESION_LOCATION_ANATOMIC_SITE", "CODE");
        bean.validateLookUp("Phospho-HER-2/neu", "TUMOR_MARKER", "CODE");
        bean.validateLookUp("QIS", "DOSE_FREQUENCY", "CODE");
        bean.validateLookUp("2-D Echocardiogram", "ASSESSMENT_TYPE", "CODE");
    }
}
