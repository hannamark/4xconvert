package gov.nih.nci.po.service;

import gov.nih.nci.po.data.bo.Organization;
import gov.nih.nci.po.data.bo.OrganizationalContact;
import gov.nih.nci.po.data.bo.Overridable;
import gov.nih.nci.po.data.bo.Person;
import gov.nih.nci.security.authorization.domainobjects.User;

import java.util.List;
import java.util.Map;

import javax.jms.JMSException;

import com.fiveamsolutions.nci.commons.data.search.PageSortParams;
import com.fiveamsolutions.nci.commons.search.SearchCriteria;

public class OrganizationalContactServiceStub implements OrganizationalContactServiceLocal {

    public int getHotRoleCount(Person per) {
        return 0;
    }

    public int getScoperHotRoleCount(Organization org) {
        return 0;
    }

    public long create(OrganizationalContact structuralRole) throws EntityValidationException {
        return 0;
    }

    public void curate(OrganizationalContact correlation) throws JMSException {

    }

    public OrganizationalContact getById(long id) {
        OrganizationalContact o = new OrganizationalContact();
        o.setId(id);
        return o;
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

    public List<OrganizationalContact> getByPlayerIds(Long[] pids) {
        return null;
    }

    @Override
    public long createActiveWithFallback(OrganizationalContact structuralRole)
            throws EntityValidationException, JMSException {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void override(Overridable overridable, User overriddenBy) {
      
    }

}
