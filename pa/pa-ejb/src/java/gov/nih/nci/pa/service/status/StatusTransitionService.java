package gov.nih.nci.pa.service.status;

import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.status.json.AppName;
import gov.nih.nci.pa.service.status.json.TransitionFor;
import gov.nih.nci.pa.service.status.json.TrialType;

import java.util.Date;
import java.util.List;

/**
 * @author vinodh 
 * Copyright NCI 2008. All rights reserved. This code may not be
 * used without the express written permission of the copyright holder,
 * NCI.
 */
public interface StatusTransitionService {

    /**
     * Validates a single status transition as per the rules in
     * StatusTransitionConfig
     * 
     * @param appName
     *            - AppName enum
     * @param trialType
     *            - Trial Type enum
     * @param transitionFor
     *            - Transition for enum
     * @param fromStatus
     *            - from status string
     * @param fromStatusDt
     *            - from status date
     * @param toStatus
     *            - to status string
     * @return validation results as list of statuses
     * @throws PAException  - Any error
     */
    List<StatusDto> validateStatusTransition(AppName appName, TrialType trialType,
            TransitionFor transitionFor, String fromStatus, Date fromStatusDt, String toStatus) throws PAException;

    /**
     * Validates the list of status history as per the rules in
     * StatusTransitionConfig
     * 
     * @param appName
     *            - AppName enum
     * @param trialType
     *            - Trial Type enum
     * @param transitionFor
     *            - Transition for enum
     * @param statusList
     *            - list of status history
     * @return validation results as list of statuses
     * @throws PAException  - Any error
     */
    List<StatusDto> validateStatusHistory(AppName appName, TrialType trialType,
            TransitionFor transitionFor, List<StatusDto> statusList) throws PAException;

}