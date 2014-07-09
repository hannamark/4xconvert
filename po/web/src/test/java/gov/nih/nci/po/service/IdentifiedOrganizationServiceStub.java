package gov.nih.nci.po.service;

import gov.nih.nci.po.data.bo.IdentifiedOrganization;
import gov.nih.nci.po.data.bo.Organization;
import gov.nih.nci.po.data.bo.Overridable;
import gov.nih.nci.security.authorization.domainobjects.User;

import java.util.List;
import java.util.Map;

import javax.jms.JMSException;

import com.fiveamsolutions.nci.commons.data.search.PageSortParams;
import com.fiveamsolutions.nci.commons.search.SearchCriteria;

public class IdentifiedOrganizationServiceStub implements IdentifiedOrganizationServiceLocal {

    public long create(IdentifiedOrganization structuralRole) throws EntityValidationException {
        return 0;
    }

    public IdentifiedOrganization getById(long id) {
        IdentifiedOrganization o = new IdentifiedOrganization();
        o.setId(id);
        return o;
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

    public List<IdentifiedOrganization> getByPlayerIds(Long[] pids) {
        return null;
    }

    @Override
    public void override(Overridable overridable, User overriddenBy) {
               
    }

    @Override
    public Organization setOrgCtepId(Organization org, String ctepId)
            throws JMSException, EntityValidationException {
       
        return null;
    }

    

}
