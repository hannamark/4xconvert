package gov.nih.nci.coppa.services.pa.grid.remote;

import gov.nih.nci.pa.service.ArmServiceRemote;
import gov.nih.nci.pa.service.StudyOutcomeMeasureServiceRemote;
import gov.nih.nci.pa.service.StudyParticipationContactServiceRemote;
import gov.nih.nci.pa.service.StudyProtocolServiceRemote;
import gov.nih.nci.pa.service.StudyRecruitmentStatusServiceRemote;
import gov.nih.nci.pa.service.StudyRegulatoryAuthorityServiceRemote;
import gov.nih.nci.pa.service.StudyResourcingServiceRemote;
import gov.nih.nci.pa.service.StudySiteAccrualStatusServiceRemote;

import javax.naming.NamingException;

/**
 * Locator interface for PA services.
 */
/**
 * @author Steve Lustbader
 */
public interface ServiceLocator {

    /**
     * @return the remote Arm service
     * @throws NamingException if unable to lookup
     */
    ArmServiceRemote getArmService() throws NamingException;

    /**
     * @return the remote StudyProtocol service
     * @throws NamingException if unable to lookup
     */
    StudyProtocolServiceRemote getStudyProtocolService() throws NamingException;

    /**
     * Gets the StudyResourcing service.
     * @return the remote StudyResourcingService
     * @throws NamingException if unable to lookup
     */
    StudyResourcingServiceRemote getStudyResourcingService() throws NamingException;

    /**
     * Gets the StudyRegulatoryAuthority service.
     * @return the remote StudyRegulatoryAuthorityService
     * @throws NamingException if unable to lookup
     */
    StudyRegulatoryAuthorityServiceRemote getStudyRegulatoryAuthorityService() throws NamingException;

    /**
     * Gets the StudyRecruitmentStatusService.
     * @return the remote StudyRecruitmentStatusService.
     * @throws NamingException if unable to lookup.
     */
    StudyRecruitmentStatusServiceRemote getStudyRecruitmentStatusService() throws NamingException;

    /**
     * Gets the StudySiteAccrualStatus service.
     * @return the remote StudySiteAccrualStatusService
     * @throws NamingException if unable to lookup
     */
    StudySiteAccrualStatusServiceRemote getStudySiteAccrualStatusService() throws NamingException;

    /**
     * Gets the StudyParticipationContact service.
     * @return the remote StudyParticipationContactService
     * @throws NamingException if unable to lookup.
     */
    StudyParticipationContactServiceRemote getStudyParticipationContactService() throws NamingException;

    /**
     * Gets the StudyOutcomeMeasure Service.
     * @return the remote StudyOutcomeMeasureService
     * @throws NamingException if unable to lookup.
     */
    StudyOutcomeMeasureServiceRemote getStudyOutcomeMeasureService() throws NamingException;

}
