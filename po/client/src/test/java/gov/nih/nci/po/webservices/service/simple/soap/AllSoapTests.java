package gov.nih.nci.po.webservices.service.simple.soap;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses(value = { PersonServiceTest.class, OrganizationServiceTest.class,
        FamilyServiceTest.class })
public class AllSoapTests {

}
