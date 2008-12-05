package gov.nih.nci.po.service;

import gov.nih.nci.po.data.bo.OrganizationalContact;
import gov.nih.nci.po.data.bo.Person;

import java.util.List;
import java.util.Map;

import javax.jms.JMSException;

public class OrganizationalContactServiceStub implements OrganizationalContactServiceLocal {

    public int getHotRoleCount(Person per) {
        return 0;
    }

    public long create(OrganizationalContact structuralRole) throws EntityValidationException {
        return 0;
    }

    public void curate(OrganizationalContact correlation) throws JMSException {

    }

    public OrganizationalContact getById(long id) {
        return null;
    }

    public List<OrganizationalContact> getByIds(Long[] ids) {
        return null;
    }

    public void update(OrganizationalContact updated) {

    }

    public Map<String, String[]> validate(OrganizationalContact entity) {
        return null;
    }

    public int count(SearchCriteria<OrganizationalContact> criteria) {
        return 0;
    }

    public List<OrganizationalContact> search(SearchCriteria<OrganizationalContact> criteria) {
        return null;
    }

    public List<OrganizationalContact> search(SearchCriteria<OrganizationalContact> criteria,
            PageSortParams<OrganizationalContact> pageSortParams) {
        return null;
    }

}
