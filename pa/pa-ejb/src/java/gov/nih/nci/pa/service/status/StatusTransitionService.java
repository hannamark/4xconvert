package gov.nih.nci.pa.service.status;

import java.util.List;

/**
 * @author vinodh copyright NCI 2008. All rights reserved. This code may not be
 *         used without the express written permission of the copyright holder,
 *         NCI.
 */
public interface StatusTransitionService {

    /**
     * Validates a single status transition as per the rules in
     * StatusTransitionConfig
     * 
     * @param trialType
     *            - Trial Type enum
     * @param transitionFor
     *            - Transition for enum
     * @param fromStatus
     *            - from status string
     * @param toStatus
     *            - to status string
     * @return validation results as list of statuses
     */
    List<StatusDto> validateStatusTransition(TrialType trialType,
            TransitionFor transitionFor, String fromStatus, String toStatus);

    /**
     * Validates the list of status history as per the rules in
     * StatusTransitionConfig
     * 
     * @param trialType
     *            - Trial Type enum
     * @param transitionFor
     *            - Transition for enum
     * @param statusList
     *            - list of status history
     * @return validation results as list of statuses
     */
    List<StatusDto> validateStatusHistory(TrialType trialType,
            TransitionFor transitionFor, List<StatusDto> statusList);

}