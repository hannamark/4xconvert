/**
 * 
 */
package gov.nih.nci.pa.service;

import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.pa.iso.dto.PlannedActivityDTO;
import gov.nih.nci.pa.iso.dto.PlannedEligibilityCriterionDTO;

import java.util.List;

import javax.ejb.Remote;

/**
 * @author Hugh Reinhart
 * @since 10/29/2008
 * 
 * copyright NCI 2008.  All rights reserved.
 * This code may not be used without the express written permission of the
 * copyright holder, NCI.
 */
@Remote
public interface PlannedActivityServiceRemote 
        extends StudyPaService<PlannedActivityDTO> {
    /**
     * @param ii index of arm
     * @return list of planned activities associated w/arm
     * @throws PAException exception
     */
    List<PlannedActivityDTO> getByArm(Ii ii) throws PAException;
    /**
     * @param ii index of PlannedEligibilityCriterion
     * @return list of PlannedEligibilityCriterion 
     * @throws PAException exception
     */
    List<PlannedEligibilityCriterionDTO> getPlannedEligibilityCriterionByStudyProtocol(Ii ii)
    throws PAException;
    /**
     * @param ii index
     * @return the PlannedEligibilityCriterion
     * @throws PAException exception.
     */
    PlannedEligibilityCriterionDTO getPlannedEligibilityCriterion(Ii ii) throws PAException;
    /**
     * @param dto PlannedEligibilityCriterion to create
     * @return the created PlannedEligibilityCriterion
     * @throws PAException exception.
     */
    PlannedEligibilityCriterionDTO createPlannedEligibilityCriterion(
            PlannedEligibilityCriterionDTO dto) throws PAException;
    /**
     * @param dto PlannedEligibilityCriterion to update
     * @return the updated PlannedEligibilityCriterion
     * @throws PAException exception.
     */
    PlannedEligibilityCriterionDTO updatePlannedEligibilityCriterion(
            PlannedEligibilityCriterionDTO dto) throws PAException;
    /**
     * @param ii index
     * @throws PAException exception.
     */
    void deletePlannedEligibilityCriterion(Ii ii) throws PAException;
}
