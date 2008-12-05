package gov.nih.nci.po.service;

import static org.junit.Assert.assertEquals;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.junit.Test;


public class SortCriterionTest {
    
    private void verifySortCriterion(String expectedName, SortCriterion<?> criterion) {
        assertEquals(expectedName, criterion.getOrderField());
        assertEquals(Collections.singletonList(criterion), criterion.getOrderByList());
    }

    @Test
    public void ClinicalResearchStaffSortCriterion_getOrderField() {
        verifySortCriterion("id", ClinicalResearchStaffSortCriterion.ID);
        verifySortCriterion("status", ClinicalResearchStaffSortCriterion.ROLE_STATUS);
        verifySortCriterion("statusDate", ClinicalResearchStaffSortCriterion.STATUS_DATE);
        verifySortCriterion("scoper.id", ClinicalResearchStaffSortCriterion.SCOPER_ID);
        verifySortCriterion("scoper.name", ClinicalResearchStaffSortCriterion.SCOPER_NAME);
    }
    
    @Test
    public void HealthCareProviderSortCriterion_getOrderField() {
        verifySortCriterion("id", HealthCareProviderSortCriterion.ID);
        verifySortCriterion("status", HealthCareProviderSortCriterion.ROLE_STATUS);
        verifySortCriterion("statusDate", HealthCareProviderSortCriterion.STATUS_DATE);
        verifySortCriterion("scoper.id", HealthCareProviderSortCriterion.SCOPER_ID);
        verifySortCriterion("scoper.name", HealthCareProviderSortCriterion.SCOPER_NAME);
    }
    
    @Test
    public void OrganizationalContactSortCriterion_getOrderField() {
        verifySortCriterion("id", OrganizationalContactSortCriterion.ID);
        verifySortCriterion("status", OrganizationalContactSortCriterion.ROLE_STATUS);
        verifySortCriterion("statusDate", OrganizationalContactSortCriterion.STATUS_DATE);
        verifySortCriterion("scoper.id", OrganizationalContactSortCriterion.SCOPER_ID);
        verifySortCriterion("scoper.name", OrganizationalContactSortCriterion.SCOPER_NAME);
    }
    
    @Test
    public void ResearchOrganizationSortCriterion_getOrderField() {
        verifySortCriterion("id", ResearchOrganizationSortCriterion.ID);
        verifySortCriterion("status", ResearchOrganizationSortCriterion.ROLE_STATUS);
        verifySortCriterion("typeCode.code", ResearchOrganizationSortCriterion.TYPE_CODE);
        verifySortCriterion("typeCode.description", ResearchOrganizationSortCriterion.TYPE_DESC);
        verifySortCriterion("statusDate", ResearchOrganizationSortCriterion.STATUS_DATE);
        verifySortCriterion("fundingMechanism", ResearchOrganizationSortCriterion.FUNDING);
    }
    
    @Test
    public void OversightCommitteeSortCriterion_getOrderField() {
        verifySortCriterion("id", OversightCommitteeSortCriterion.ID);
        verifySortCriterion("status", OversightCommitteeSortCriterion.ROLE_STATUS);
        verifySortCriterion("typeCode.code", OversightCommitteeSortCriterion.TYPE_CODE);
        verifySortCriterion("typeCode.description", OversightCommitteeSortCriterion.TYPE_DESC);
        verifySortCriterion("statusDate", OversightCommitteeSortCriterion.STATUS_DATE);
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
    }
    
    @Test
    public void OrganizationSortCriterion_getOrderField() {
        verifySortCriterion("id", OrganizationSortCriterion.ORGANIZATION_ID);
        verifySortCriterion("name", OrganizationSortCriterion.ORGANIZATION_NAME);
        verifySortCriterion("statusCode", OrganizationSortCriterion.ORGANIZATION_STATUS);
    }
}
