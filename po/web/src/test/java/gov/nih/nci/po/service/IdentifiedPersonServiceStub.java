package gov.nih.nci.po.service;

import gov.nih.nci.po.data.bo.IdentifiedPerson;
import gov.nih.nci.po.data.bo.Person;

import java.util.List;
import java.util.Map;

import javax.jms.JMSException;

import com.fiveamsolutions.nci.commons.data.search.PageSortParams;
import com.fiveamsolutions.nci.commons.search.SearchCriteria;

public class IdentifiedPersonServiceStub implements IdentifiedPersonServiceLocal {

    public int getHotRoleCount(Person per) {
        return 0;
    }

    public long create(IdentifiedPerson structuralRole) throws EntityValidationException {
        return 0;
    }

    public void curate(IdentifiedPerson correlation) throws JMSException {

    }

    public IdentifiedPerson getById(long id) {
        IdentifiedPerson o = new IdentifiedPerson();
        o.setId(id);
        return o;
    }

    public List<IdentifiedPerson> getByIds(Long[] ids) {
        return null;
    }

    public void update(IdentifiedPerson updated) {

    }

    public Map<String, String[]> validate(IdentifiedPerson entity) {
        return null;
    }

    public int count(SearchCriteria<IdentifiedPerson> criteria) {
        return 0;
    }

    public List<IdentifiedPerson> search(SearchCriteria<IdentifiedPerson> criteria) {
        return null;
    }

    public List<IdentifiedPerson> search(SearchCriteria<IdentifiedPerson> criteria,
            PageSortParams<IdentifiedPerson> pageSortParams) {
        return null;
    }

    public List<IdentifiedPerson> getByPlayerIds(Long[] pids) {
        return null;
    }

}
