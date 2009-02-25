package gov.nih.nci.coppa.test.remoteapi;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses(value = {
        OrganizationEntityServiceSearchTest.class,
        OrganizationEntityServiceTest.class,
        PersonEntityServiceSearchTest.class,
        PersonEntityServiceTest.class,
        ClinicalResearchStaffCorrelationServiceTest.class,
        HealthCareFacilityCorrelationServiceTest.class,
        HealthCareProviderCorrelationServiceTest.class,
        IdentifiedOrganizationCorrelationServiceTest.class,
        IdentifiedPersonCorrelationServiceTest.class,
        OrganizationalContactCorrelationServiceTest.class,
        OversightCommitteeCorrelationServiceTest.class,
        ResearchOrganizationCorrelationServiceTest.class,
        OrganizationTelecomAddressTest.class
})
public class AllApiTests {


    public static class VerifyAllApiTestsSuite {

        @Test
        public void verifyAllTestClassesInSuite() {
            SuiteClasses suiteClasses = AllApiTests.class.getAnnotation(SuiteClasses.class);
            List<Class<?>> suiteClassesList = Arrays.asList(suiteClasses.value());
            List<Class<?>> classPathClassesList = getClassesList();
            assertTrue("@SuiteClasses is missing a *Test.class", suiteClassesList.containsAll(classPathClassesList));
        }

        public static Class<?>[] getClasses() {
            List<Class<?>> result = getClassesList();
            Class<?>[] clzz = new Class[result.size()];
            return result.toArray(clzz);
        }

        private static List<Class<?>> getClassesList() {
            Class<?>[] classes = org.openqa.jtc.junit.Locator.findClasses(AllApiTests.class, Object.class, AllApiTests.class
                    .getPackage().getName()
                    + ".", (Set<?>) null);
            List<Class<?>> result = new ArrayList<Class<?>>();

            for (Class<?> clz : classes) {
                if (clz.getName().endsWith("Test")) {
                    result.add(clz);
                }
            }
            return result;
        }
    }
}
