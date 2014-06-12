package gov.nih.nci.po.webservices.service.simple.rest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses(value = { PersonRESTServiceTest.class,
        OrganizationRESTServiceTest.class, FamilyRESTServiceTest.class })
public class AllRestTests {

}
