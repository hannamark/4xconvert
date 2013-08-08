package gov.nih.nci.pa.service.util;

import gov.nih.nci.pa.domain.CTGovImportLog;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.ctgov.ClinicalStudy;

import java.util.Date;
import java.util.List;

import javax.ejb.Local;

/**
 * @author Denis G. Krylov
 */
@Local
public interface CTGovSyncServiceLocal {

    /**
     * Searches for a study using CT.Gov Public API and, if found, returns it in
     * a "raw" format: JAXB classes.
     * 
     * @throws PAException
     *             PAException
     * @param nctID
     *            nctID
     * @return ClinicalStudy
     */
    ClinicalStudy getCtGovStudyByNctId(String nctID) throws PAException;

    /**
     * Searches for a study using CT.Gov Public API and, if found, returns an
     * adapter of it: {@link CTGovStudyAdapter}, mostly for purposes of easier
     * access to the underlying study elements.
     * 
     * @throws PAException
     *             PAException
     * @param nctID
     *            nctID
     * @return ClinicalStudy
     */
    CTGovStudyAdapter getAdaptedCtGovStudyByNctId(String nctID)
            throws PAException;

    /**
     * Imports a trial from CT.Gov and returns an NCI ID of the corresponding
     * trial in CTRP.
     * 
     * @param nctID
     *            nctID
     * @return NCI ID
     * @throws PAException
     *             PAException
     */
    String importTrial(String nctID) throws PAException;

    /**
     * 
     * @param onOrAfter
     *            startDate
     * @param onOrBefore
     *            endDate
     * @return list of log entries
     * @throws PAException
     *             PAException
     */
    List<CTGovImportLog> getLogEntries(Date onOrAfter, Date onOrBefore)
            throws PAException;

}
