package gov.nih.nci.po.service;

import gov.nih.nci.po.data.bo.Organization;
import java.util.List;
import java.util.Map;

import javax.jms.JMSException;

import gov.nih.nci.po.data.bo.ResearchOrganization;
import gov.nih.nci.po.service.EntityValidationException;
import gov.nih.nci.po.service.PageSortParams;
import gov.nih.nci.po.service.ResearchOrganizationServiceLocal;
import gov.nih.nci.po.service.SearchCriteria;

public class ResearchOrganizationServiceStub implements ResearchOrganizationServiceLocal {

    public void curate(ResearchOrganization org) throws JMSException {

    }

    public long create(ResearchOrganization structuralRole) throws EntityValidationException {
        return 0;
    }

    public ResearchOrganization getById(long id) {
        return null;
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

}
