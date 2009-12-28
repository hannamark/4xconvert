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
}
