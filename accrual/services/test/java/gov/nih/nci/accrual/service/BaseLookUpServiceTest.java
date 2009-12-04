package gov.nih.nci.accrual.service;

import gov.nih.nci.pa.domain.DoseFrequency;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class BaseLookUpServiceTest extends AbstractServiceTest<BaseLookUpService> {

    @Override
    @Before
    public void instantiateServiceBean() throws Exception {
        bean = new BaseLookUpService<DoseFrequency>(DoseFrequency.class);
    }
    @Test
    public void search() throws Exception {
        DoseFrequency criteria = new DoseFrequency();
        criteria.setCode("Q");
        List<DoseFrequency> list = new ArrayList<DoseFrequency>();
        BaseLookUpService<DoseFrequency> lookUpService =
            new BaseLookUpService<DoseFrequency>(DoseFrequency.class);
        list.addAll(lookUpService.search(criteria));
        for (DoseFrequency df :  list) {
            df = lookUpService.getById(df.getId());
            df = lookUpService.getByCode(df.getCode());
        }
    }
}
