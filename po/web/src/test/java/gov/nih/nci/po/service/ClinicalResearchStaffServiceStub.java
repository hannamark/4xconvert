package gov.nih.nci.po.service;

import gov.nih.nci.po.data.bo.ClinicalResearchStaff;
import gov.nih.nci.po.data.bo.Person;

import java.util.List;
import java.util.Map;

import javax.jms.JMSException;

import com.fiveamsolutions.nci.commons.data.search.PageSortParams;
import com.fiveamsolutions.nci.commons.search.SearchCriteria;

public class ClinicalResearchStaffServiceStub implements ClinicalResearchStaffServiceLocal {

    public long create(ClinicalResearchStaff structuralRole) throws EntityValidationException {
        return 0;
    }

    public void curate(ClinicalResearchStaff correlation) throws JMSException {

    }

    public ClinicalResearchStaff getById(long id) {
        ClinicalResearchStaff o = new ClinicalResearchStaff();
        o.setId(id);
        return o;
    }

    public List<ClinicalResearchStaff> getByIds(Long[] ids) {
        return null;
    }

    public void update(ClinicalResearchStaff updated) {

    }

    public Map<String, String[]> validate(ClinicalResearchStaff entity) {
        return null;
    }

    public int count(SearchCriteria<ClinicalResearchStaff> criteria) {
        return 0;
    }

    public List<ClinicalResearchStaff> search(SearchCriteria<ClinicalResearchStaff> criteria) {
        return null;
    }

    public List<ClinicalResearchStaff> search(SearchCriteria<ClinicalResearchStaff> criteria,
            PageSortParams<ClinicalResearchStaff> pageSortParams) {
        return null;
    }

    public int getHotRoleCount(Person per) {
        return 0;
    }

    public List<ClinicalResearchStaff> getByPlayerIds(Long[] pids) {
        return null;
    }

    @Override
    public long createActiveWithFallback(ClinicalResearchStaff structuralRole)
            throws EntityValidationException, JMSException {
        // TODO Auto-generated method stub
        return 0;
    }

}
