package gov.nih.nci.po.service;

import gov.nih.nci.po.data.bo.Organization;
import gov.nih.nci.po.data.bo.Overridable;
import gov.nih.nci.po.data.bo.ResearchOrganization;
import gov.nih.nci.security.authorization.domainobjects.User;

import java.util.List;
import java.util.Map;

import javax.jms.JMSException;

import com.fiveamsolutions.nci.commons.data.search.PageSortParams;
import com.fiveamsolutions.nci.commons.search.SearchCriteria;

public class ResearchOrganizationServiceStub implements ResearchOrganizationServiceLocal {

    public void curate(ResearchOrganization org) throws JMSException {

    }

    public long create(ResearchOrganization structuralRole) throws EntityValidationException {
        return 0;
    }

    public ResearchOrganization getById(long id) {
        ResearchOrganization o = new ResearchOrganization();
        o.setId(id);
        return o;
    }

    public List<ResearchOrganization> getByIds(Long[] ids) {
        return null;
    }

    public void update(ResearchOrganization updated) {

    }

    public Map<String, String[]> validate(ResearchOrganization entity) {
        return null;
    }

    public int count(SearchCriteria<ResearchOrganization> criteria) {
        return 0;
    }

    public List<ResearchOrganization> search(SearchCriteria<ResearchOrganization> criteria) {
        return null;
    }

    public List<ResearchOrganization> search(SearchCriteria<ResearchOrganization> criteria,
            PageSortParams<ResearchOrganization> pageSortParams) {
        return null;
    }

    public int getHotRoleCount(Organization org) {
        return 0;
    }

    public List<ResearchOrganization> getByPlayerIds(Long[] pids) {
        return null;
    }

    @Override
    public void curate(ResearchOrganization researchOrganization, String ctepId)
            throws JMSException {
        
    }

    @Override
    public void override(Overridable overridable, User overriddenBy) {
               
    }

}
