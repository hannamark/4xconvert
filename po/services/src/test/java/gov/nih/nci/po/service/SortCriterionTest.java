package gov.nih.nci.po.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.junit.Test;

import com.fiveamsolutions.nci.commons.data.persistent.PersistentObject;


public class SortCriterionTest {

    private void verifySortCriterion(String expectedName, PoSortCriterion<?> criterion) {
        assertEquals(expectedName, criterion.getOrderField());
        assertEquals(Collections.singletonList(criterion), criterion.getOrderByList());
    }

    public static void testSortCriteriaNull(PoSortCriterion<? extends PersistentObject> [] item) {
        for(PoSortCriterion<? extends PersistentObject> ind : item) {
            assertNull(ind.getLeftJoinField());
        }
    }


    @Test
    public void ClinicalResearchStaffSortCriterion_getOrderField() {
        verifySortCriterion("id", ClinicalResearchStaffSortCriterion.ID);
        verifySortCriterion("status", ClinicalResearchStaffSortCriterion.ROLE_STATUS);
        verifySortCriterion("statusDate", ClinicalResearchStaffSortCriterion.STATUS_DATE);
        verifySortCriterion("scoper.id", ClinicalResearchStaffSortCriterion.SCOPER_ID);
        verifySortCriterion("scoper.name", ClinicalResearchStaffSortCriterion.SCOPER_NAME);

        testSortCriteriaNull(ClinicalResearchStaffSortCriterion.values());

    }

    @Test
    public void HealthCareProviderSortCriterion_getOrderField() {
        verifySortCriterion("id", HealthCareProviderSortCriterion.ID);
        verifySortCriterion("status", HealthCareProviderSortCriterion.ROLE_STATUS);
        verifySortCriterion("statusDate", HealthCareProviderSortCriterion.STATUS_DATE);
        verifySortCriterion("scoper.id", HealthCareProviderSortCriterion.SCOPER_ID);
        verifySortCriterion("scoper.name", HealthCareProviderSortCriterion.SCOPER_NAME);

        testSortCriteriaNull(HealthCareProviderSortCriterion.values());
    }

    @Test
    public void OrganizationalContactSortCriterion_getOrderField() {
        verifySortCriterion("id", OrganizationalContactSortCriterion.ID);
        verifySortCriterion("status", OrganizationalContactSortCriterion.ROLE_STATUS);
        verifySortCriterion("statusDate", OrganizationalContactSortCriterion.STATUS_DATE);
        verifySortCriterion("scoper.id", OrganizationalContactSortCriterion.SCOPER_ID);
        verifySortCriterion("scoper.name", OrganizationalContactSortCriterion.SCOPER_NAME);

        testSortCriteriaNull(OrganizationalContactSortCriterion.values());
    }

    @Test
    public void ResearchOrganizationSortCriterion_getOrderField() {
        verifySortCriterion("id", ResearchOrganizationSortCriterion.ID);
        verifySortCriterion("status", ResearchOrganizationSortCriterion.ROLE_STATUS);
        verifySortCriterion("typeCode.code", ResearchOrganizationSortCriterion.TYPE_CODE);
        verifySortCriterion("typeCode.description", ResearchOrganizationSortCriterion.TYPE_DESC);
        verifySortCriterion("statusDate", ResearchOrganizationSortCriterion.STATUS_DATE);
        verifySortCriterion("fundingMechanism.code", ResearchOrganizationSortCriterion.FUNDING);

        testSortCriteriaNull(ResearchOrganizationSortCriterion.values());
    }

    @Test
    public void OversightCommitteeSortCriterion_getOrderField() {
        verifySortCriterion("id", OversightCommitteeSortCriterion.ID);
        verifySortCriterion("status", OversightCommitteeSortCriterion.ROLE_STATUS);
        verifySortCriterion("typeCode.code", OversightCommitteeSortCriterion.TYPE_CODE);
        verifySortCriterion("typeCode.description", OversightCommitteeSortCriterion.TYPE_DESC);
        verifySortCriterion("statusDate", OversightCommitteeSortCriterion.STATUS_DATE);

        testSortCriteriaNull(OversightCommitteeSortCriterion.values());
    }

    @Test
    public void IdentifiedOrganizationSortCriterion_getOrderField() {
        verifySortCriterion("id", IdentifiedOrganizationSortCriterion.ID);
        verifySortCriterion("status", IdentifiedOrganizationSortCriterion.ROLE_STATUS);
        verifySortCriterion("typeCode.code", IdentifiedOrganizationSortCriterion.TYPE_CODE);
        verifySortCriterion("typeCode.description", IdentifiedOrganizationSortCriterion.TYPE_DESC);
        verifySortCriterion("statusDate", IdentifiedOrganizationSortCriterion.STATUS_DATE);
        verifySortCriterion("scoper.id", IdentifiedOrganizationSortCriterion.SCOPER_ID);
        verifySortCriterion("scoper.name", IdentifiedOrganizationSortCriterion.SCOPER_NAME);

        testSortCriteriaNull(IdentifiedOrganizationSortCriterion.values());
    }

    @Test
    public void IdentifiedPersonSortCriterion_getOrderField() {
        verifySortCriterion("id", IdentifiedPersonSortCriterion.ID);
        verifySortCriterion("status", IdentifiedPersonSortCriterion.ROLE_STATUS);
        verifySortCriterion("typeCode.code", IdentifiedPersonSortCriterion.TYPE_CODE);
        verifySortCriterion("typeCode.description", IdentifiedPersonSortCriterion.TYPE_DESC);
        verifySortCriterion("statusDate", IdentifiedPersonSortCriterion.STATUS_DATE);
        verifySortCriterion("scoper.id", IdentifiedPersonSortCriterion.SCOPER_ID);
        verifySortCriterion("scoper.name", IdentifiedPersonSortCriterion.SCOPER_NAME);

        testSortCriteriaNull(IdentifiedPersonSortCriterion.values());
    }

    @Test
    public void PersonSortCriterion_getOrderField() {
        verifySortCriterion("firstName", PersonSortCriterion.PERSON_FIRSTNAME);
        assertEquals(null, PersonSortCriterion.PERSON_FULLNAME.getOrderField());
        List<PersonSortCriterion> fullNameList = PersonSortCriterion.PERSON_FULLNAME.getOrderByList();
        assertEquals(3, fullNameList.size());
        Iterator<PersonSortCriterion> fullNameListItr = fullNameList.iterator();
        assertEquals(PersonSortCriterion.PERSON_LASTNAME, fullNameListItr.next());
        assertEquals(PersonSortCriterion.PERSON_FIRSTNAME, fullNameListItr.next());
        assertEquals(PersonSortCriterion.PERSON_MIDDLENAME, fullNameListItr.next());
        verifySortCriterion("id", PersonSortCriterion.PERSON_ID);
        verifySortCriterion("lastName", PersonSortCriterion.PERSON_LASTNAME);
        verifySortCriterion("middleName", PersonSortCriterion.PERSON_MIDDLENAME);
        verifySortCriterion("prefix", PersonSortCriterion.PERSON_PREFIX);
        verifySortCriterion("statusCode", PersonSortCriterion.PERSON_STATUS);
        verifySortCriterion("suffix", PersonSortCriterion.PERSON_SUFFIX);

        testSortCriteriaNull(PersonSortCriterion.values());
    }

    @Test
    public void OrganizationSortCriterion_getOrderField() {
        verifySortCriterion("id", OrganizationSortCriterion.ORGANIZATION_ID);
        verifySortCriterion("name", OrganizationSortCriterion.ORGANIZATION_NAME);
        verifySortCriterion("statusCode", OrganizationSortCriterion.ORGANIZATION_STATUS);

        testSortCriteriaNull(OrganizationSortCriterion.values());
    }
}
