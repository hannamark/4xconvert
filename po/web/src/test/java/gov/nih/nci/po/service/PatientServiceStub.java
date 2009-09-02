package gov.nih.nci.po.service;

import gov.nih.nci.po.data.bo.Patient;
import gov.nih.nci.po.data.bo.Person;

import java.util.List;
import java.util.Map;

import javax.jms.JMSException;

import com.fiveamsolutions.nci.commons.data.search.PageSortParams;
import com.fiveamsolutions.nci.commons.search.SearchCriteria;

public class PatientServiceStub implements PatientServiceLocal {

    public long create(Patient structuralRole) throws EntityValidationException {
        return 0;
    }

    public void curate(Patient correlation) throws JMSException {

    }

    public Patient getById(long id) {
        Patient o = new Patient();
        o.setId(id);
        return o;
    }

    public List<Patient> getByIds(Long[] ids) {
        return null;
    }

    public void update(Patient updated) {

    }

    public Map<String, String[]> validate(Patient entity) {
        return null;
    }

    public int count(SearchCriteria<Patient> criteria) {
        return 0;
    }

    public List<Patient> search(SearchCriteria<Patient> criteria) {
        return null;
    }

    public List<Patient> search(SearchCriteria<Patient> criteria,
            PageSortParams<Patient> pageSortParams) {
        return null;
    }

    public int getHotRoleCount(Person per) {
        return 0;
    }

    public List<Patient> getByPlayerIds(Long[] pids) {
        return null;
    }

}
