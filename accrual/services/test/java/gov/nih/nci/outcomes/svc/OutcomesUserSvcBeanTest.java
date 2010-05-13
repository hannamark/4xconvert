package gov.nih.nci.outcomes.svc;

import static org.junit.Assert.assertNotNull;
import gov.nih.nci.accrual.service.AbstractServiceTest;
import gov.nih.nci.accrual.service.util.CsmUtil;
import gov.nih.nci.accrual.service.util.MockCsmUtil;
import gov.nih.nci.accrual.service.util.MockPaServiceLocator;
import gov.nih.nci.accrual.util.MockPoServiceLocator;
import gov.nih.nci.accrual.util.PoRegistry;
import gov.nih.nci.outcomes.svc.dto.UserSvcDto;
import gov.nih.nci.outcomes.svc.exception.OutcomesException;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.util.PaRegistry;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Kalpana Guthikonda
 * @since Mar 30, 2010
 *
 */

public class OutcomesUserSvcBeanTest extends AbstractServiceTest<OutcomesUserSvcBeanLocal> {

    @Override
    @Before
    public void instantiateServiceBean() throws Exception {
        PoRegistry.getInstance().setPoServiceLocator(new MockPoServiceLocator());
        PaRegistry.getInstance().setServiceLocator(new MockPaServiceLocator());
        CsmUtil csmUtil = new MockCsmUtil();
        bean = new OutcomesUserSvcBeanLocal();
    }
    
    @Test
    public void createTest() throws Exception {
        UserSvcDto svcDto = new UserSvcDto();
        svcDto.setIdentity(StConverter.convertToSt("test@mail.nih.gov"));
        svcDto.setFirstName(StConverter.convertToSt("firstName"));
        svcDto.setMiddleName(StConverter.convertToSt("middleName"));
        svcDto.setLastName(StConverter.convertToSt("lastName"));
        svcDto.setAddress(StConverter.convertToSt("address"));
        svcDto.setAffiliateOrg(StConverter.convertToSt("testOrg"));
        svcDto.setCity(StConverter.convertToSt("Dallas"));
        svcDto.setCountry(StConverter.convertToSt("USA"));
        svcDto.setPhone(StConverter.convertToSt("lastName"));
        svcDto.setTreatmentSiteIdentifier(IiConverter.convertToIi("1"));
        svcDto.setPhysicianIdentifier(IiConverter.convertToIi("1"));
        svcDto.setPostalCode(StConverter.convertToSt("postAdd"));
        svcDto.setPrsOrg(StConverter.convertToSt("prsOrg"));
        svcDto.setState(StConverter.convertToSt("TX"));
        UserSvcDto r = bean.createUser(svcDto);
        assertNotNull(r);
        assertNotNull(r.getIdentifier().getExtension());
    }
    
    @Test
    public void createTestException() {
        UserSvcDto svcDto = new UserSvcDto();
        svcDto.setIdentity(StConverter.convertToSt("user1@mail.nih.gov"));
        svcDto.setFirstName(StConverter.convertToSt("firstName"));
        svcDto.setMiddleName(StConverter.convertToSt("middleName"));
        svcDto.setLastName(StConverter.convertToSt("lastName"));
        svcDto.setAddress(StConverter.convertToSt("address"));
        svcDto.setAffiliateOrg(StConverter.convertToSt("testOrg"));
        svcDto.setCity(StConverter.convertToSt("Dallas"));
        svcDto.setCountry(StConverter.convertToSt("USA"));
        svcDto.setPhone(StConverter.convertToSt("lastName"));
        svcDto.setTreatmentSiteIdentifier(IiConverter.convertToIi("1"));
        svcDto.setPhysicianIdentifier(IiConverter.convertToIi("1"));
        svcDto.setPostalCode(StConverter.convertToSt("postAdd"));
        svcDto.setPrsOrg(StConverter.convertToSt("prsOrg"));
        svcDto.setState(StConverter.convertToSt("TX"));
        try {
            bean.createUser(svcDto);
        } catch (OutcomesException e) {
            // excepted
        }
    }
    
    @Test
    public void updateTest() throws Exception {
        UserSvcDto svcDto = new UserSvcDto();
        svcDto.setIdentity(StConverter.convertToSt("user1@mail.nih.gov"));
        svcDto.setFirstName(StConverter.convertToSt("firstName"));
        svcDto.setMiddleName(StConverter.convertToSt("middleName"));
        svcDto.setLastName(StConverter.convertToSt("lastName"));
        svcDto.setAddress(StConverter.convertToSt("address"));
        svcDto.setAffiliateOrg(StConverter.convertToSt("testOrg"));
        svcDto.setCity(StConverter.convertToSt("Dallas"));
        svcDto.setCountry(StConverter.convertToSt("USA"));
        svcDto.setPhone(StConverter.convertToSt("lastName"));
        svcDto.setTreatmentSiteIdentifier(IiConverter.convertToIi("1"));
        svcDto.setPhysicianIdentifier(IiConverter.convertToIi("1"));
        svcDto.setPostalCode(StConverter.convertToSt("postAdd"));
        svcDto.setPrsOrg(StConverter.convertToSt("prsOrg"));
        svcDto.setState(StConverter.convertToSt("TX"));
        svcDto.setIdentifier(IiConverter.convertToIi("1"));
        UserSvcDto r = bean.updateUser(svcDto);
        assertNotNull(r);
        assertNotNull(r.getIdentifier().getExtension());
    }
    
    @Test
    public void updateTestException() {
        UserSvcDto svcDto = new UserSvcDto();
        svcDto.setIdentity(StConverter.convertToSt("test@mail.nih.gov"));
        svcDto.setFirstName(StConverter.convertToSt("firstName"));
        svcDto.setMiddleName(StConverter.convertToSt("middleName"));
        svcDto.setLastName(StConverter.convertToSt("lastName"));
        svcDto.setAddress(StConverter.convertToSt("address"));
        svcDto.setAffiliateOrg(StConverter.convertToSt("testOrg"));
        svcDto.setCity(StConverter.convertToSt("Dallas"));
        svcDto.setCountry(StConverter.convertToSt("USA"));
        svcDto.setPhone(StConverter.convertToSt("lastName"));
        svcDto.setTreatmentSiteIdentifier(IiConverter.convertToIi("1"));
        svcDto.setPhysicianIdentifier(IiConverter.convertToIi("1"));
        svcDto.setPostalCode(StConverter.convertToSt("postAdd"));
        svcDto.setPrsOrg(StConverter.convertToSt("prsOrg"));
        svcDto.setState(StConverter.convertToSt("TX"));
        svcDto.setIdentifier(IiConverter.convertToIi("1"));
        try {
            bean.updateUser(svcDto);
        } catch (OutcomesException e) {
            // excepted
        }
    }
}
