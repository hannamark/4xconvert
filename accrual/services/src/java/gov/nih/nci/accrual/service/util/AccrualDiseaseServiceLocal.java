package gov.nih.nci.accrual.service.util;

import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.domain.AccrualDisease;

import java.util.List;

import javax.ejb.Local;

/**
 * @author Hugh Reinhart
 * @since Dec 14, 2012
 */
@Local
public interface AccrualDiseaseServiceLocal {

    /**
     * Get using internal id.
     * @param id id
     * @return the object
     */
    AccrualDisease get(Long id);
    /**
     * Get using iso Ii.
     * @param ii iso identifier
     * @return the object
     */
    AccrualDisease get(Ii ii);
    /**
     * Get using code.
     * @param diseaseCode the code to search for (any code system)
     * @return the object
     */
    AccrualDisease getByCode(String diseaseCode);
    /**
     * Search for desired disease.
     * @param searchCriteria criteria
     * @return all matching diseases
     */
    List<AccrualDisease> search(AccrualDisease searchCriteria);
    /**
     * Return the disease code system (e.g. ICD9) for a given trial
     * @param spId the StudyProtcol.id
     * @return the code system, null if code system not yet defined
     */
    String getTrialCodeSystem(Long spId);
    /**
     * Return the disease code system (e.g. ICD9) for a given trial
     * @param spId the StudyProtcol.id
     * @return the code system, null if code system not yet defined
     */
    List<String> getValidCodeSystems(Long spId);
    /**
     * Return if the disease code is mandatory for patients on a given trial.
     * @param spId spId the StudyProtcol.id
     * @return true if mandatory
     */
    boolean diseaseCodeMandatory(Long spId);
}
