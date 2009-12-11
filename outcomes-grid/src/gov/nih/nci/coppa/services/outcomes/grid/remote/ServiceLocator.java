package gov.nih.nci.coppa.services.outcomes.grid.remote;

import gov.nih.nci.accrual.service.ActivityRelationshipService;
import gov.nih.nci.accrual.service.BaseAccrualService;
import gov.nih.nci.accrual.service.BaseAccrualStudyService;
import gov.nih.nci.accrual.service.PerformedActivityService;
import gov.nih.nci.accrual.service.PerformedObservationResultService;
import gov.nih.nci.accrual.service.StudySubjectService;
import gov.nih.nci.accrual.service.SubmissionService;
import gov.nih.nci.accrual.service.util.PatientService;
import gov.nih.nci.pa.iso.dto.BaseDTO;

import javax.naming.NamingException;

/**
 * Interface for looking up remote EJB services.
 */
public interface ServiceLocator {

    /**
     * Gets the ActivityRelationshipService.
     *
     * @return ActivityRelationshipService
     * @throws NamingException on error looking up the service
     */
    ActivityRelationshipService getActivityRelationshipService() throws NamingException;

    /**
     * Gets the PerformedActivityService.
     *
     * @return PerformedActivityService
     * @throws NamingException on error looking up the service
     */
    PerformedActivityService getPerformedActivityService() throws NamingException;

    /**
     * Gets the PerformedObservationResultService.
     *
     * @return PerformedObservationResultService
     * @throws NamingException on error looking up the service
     */
    PerformedObservationResultService getPerformedObservationResultService() throws NamingException;

    /**
     * Gets the StudySubjectService.
     *
     * @return StudySubjectService
     * @throws NamingException on error looking up the service
     */
    StudySubjectService getStudySubjectService() throws NamingException;

    /**
     * Gets the StudySubjectService.
     *
     * @return StudySubjectService
     * @throws NamingException on error looking up the service
     */
    SubmissionService getSubmissionService() throws NamingException;

    /**
     * Gets the PatientService.
     *
     * @return PatientService
     * @throws NamingException on error looking up the service
     */
     PatientService getPatientService() throws NamingException;

    /**
     * Gets a base generic service.
     *
     * @param <B> Base DTO type
     * @param type DTO class
     * @return BaseAccrualService
     * @throws NamingException on error looking up the service
     */
    @SuppressWarnings("unchecked")
    <B extends BaseDTO> BaseAccrualService getBaseAccrualService(Class<B> type) throws NamingException;

    /**
     * Gets a study generic service.
     *
     * @param <B> DTO type
     * @param type DTO class
     * @return BaseAccrualStudyService
     * @throws NamingException on error looking up the service
     */
    @SuppressWarnings("unchecked")
    <B extends BaseDTO> BaseAccrualStudyService getBaseAccrualStudyService(Class<B> type) throws NamingException;

}
