package gov.nih.nci.po.service;

import gov.nih.nci.po.data.bo.IdentifiedOrganization;

import gov.nih.nci.po.data.bo.Organization;
import java.util.List;
import java.util.Map;

import javax.jms.JMSException;

public class IdentifiedOrganizationServiceStub implements IdentifiedOrganizationServiceLocal {

    public long create(IdentifiedOrganization structuralRole) throws EntityValidationException {
        return 0;
    }

    public IdentifiedOrganization getById(long id) {
        return null;
    }

    public List<IdentifiedOrganization> getByIds(Long[] ids) {
        return null;
    }

    public void update(IdentifiedOrganization updated) {

    }

    public Map<String, String[]> validate(IdentifiedOrganization entity) {
        return null;
    }

    public int count(SearchCriteria<IdentifiedOrganization> criteria) {
        return 0;
    }

    public List<IdentifiedOrganization> search(SearchCriteria<IdentifiedOrganization> criteria) {
        return null;
    }

    public List<IdentifiedOrganization> search(SearchCriteria<IdentifiedOrganization> criteria,
            PageSortParams<IdentifiedOrganization> pageSortParams) {
        return null;
    }

    public void curate(IdentifiedOrganization correlation) throws JMSException {
        
    }

    public int getHotRoleCount(Organization org) {
        return 0;
    }

}
