package gov.nih.nci.accrual.service;

import gov.nih.nci.accrual.service.util.AccrualCsmUtil;
import gov.nih.nci.pa.domain.RegistryUser;

import org.junit.Before;
import org.junit.Test;

public class AccrualCsmUtilTest extends AbstractServiceTest<AccrualCsmUtil> {

    @Override
    @Before
    public void instantiateServiceBean() throws Exception {
        bean = new AccrualCsmUtil();
    }
    @Test
    public void getTest() throws Exception {
        try{
            bean.getCSMUser("loginName");
        } catch (Exception cse) { 
          //expected
        } 
    }
    @Test
    public void updateTest() throws Exception {
        try{
            RegistryUser dto = new RegistryUser();
            dto.setFirstName("firstName");
            dto.setLastName("lastName");
            dto.setCsmUserId(1L);
            dto.setId(1L);
            bean.updateCSMUser(dto,"loginName");
        } catch (Exception cse) { 
          //expected
        } 
    }
    @Test
    public void createTest() throws Exception {
        try{
            RegistryUser dto = new RegistryUser();
            dto.setFirstName("firstName");
            dto.setLastName("lastName");
            dto.setCsmUserId(1L);
            dto.setId(1L);            
            bean.createCSMUser(dto,"loginName");
        } catch (Exception cse) { 
          //expected
        } 
    }
}
