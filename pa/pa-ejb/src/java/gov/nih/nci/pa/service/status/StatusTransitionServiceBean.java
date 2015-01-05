/**
 * 
 */
package gov.nih.nci.pa.service.status;

import gov.nih.nci.coppa.services.interceptor.RemoteAuthorizationInterceptor;
import gov.nih.nci.pa.enums.StudyStatusCode;
import gov.nih.nci.pa.util.PaHibernateSessionInterceptor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.ejb.Stateless;
import javax.interceptor.Interceptors;

/**
 * @author dkrylov
 * 
 */
@Stateless
@Interceptors({ RemoteAuthorizationInterceptor.class,
        PaHibernateSessionInterceptor.class })
public class StatusTransitionServiceBean implements
        StatusTransitionServiceLocal {

    /*
     * (non-Javadoc)
     * 
     * @see gov.nih.nci.pa.service.status.StatusTransitionService#
     * validateStatusTransition(gov.nih.nci.pa.service.status.TrialType,
     * gov.nih.nci.pa.service.status.TransitionFor, java.lang.String,
     * java.lang.String)
     */
    @Override
    public List<StatusDto> validateStatusTransition(TrialType trialType,
            TransitionFor transitionFor, String fromStatus, String toStatus) {
        return new ArrayList<>();
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * gov.nih.nci.pa.service.status.StatusTransitionService#validateStatusHistory
     * (gov.nih.nci.pa.service.status.TrialType,
     * gov.nih.nci.pa.service.status.TransitionFor, java.util.List)
     */
    @Override
    public List<StatusDto> validateStatusHistory(TrialType trialType,
            TransitionFor transitionFor, List<StatusDto> statusList) {
        List<StatusDto> validatedList = new ArrayList<>();
        for (StatusDto status : statusList) {
            StatusDto prev = validatedList.isEmpty() ? null : validatedList
                    .get(validatedList.size() - 1);
            if (prev != null) {
                StudyStatusCode currCode = StudyStatusCode.getByCode(status
                        .getStatusCode());
                StudyStatusCode prevCode = StudyStatusCode.getByCode(prev
                        .getStatusCode());
                if (!prevCode.canTransitionTo(currCode)) {
                    ValidationError err = new ValidationError();
                    err.setErrorType(ErrorType.ERROR);
                    err.setErrorMessage(String.format(
                            "%s cannot transition to %s",
                            prevCode.getDisplayName(),
                            currCode.getDisplayName()));
                    status.setValidationErrors(Arrays.asList(err));
                }
            }
            validatedList.add(status);
        }
        return validatedList;
    }

}
