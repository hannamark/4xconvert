package gov.nih.nci.coppa.services.pa.grid.remote;

import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.coppa.services.grid.remote.InvokeCoppaServiceException;
import gov.nih.nci.pa.iso.dto.PlannedActivityDTO;
import gov.nih.nci.pa.iso.dto.PlannedEligibilityCriterionDTO;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.PlannedActivityServiceRemote;

import java.util.List;

/**
 * Wrapper class for invoking the PlannedActivity remote EJB.
 */
public class InvokePlannedActivityEjb
    extends InvokeStudyPaServiceEjb<PlannedActivityDTO>
    implements PlannedActivityServiceRemote {

    private final ServiceLocator locator = JNDIServiceLocator.getInstance();

    /**
     * Const.
     */
    public InvokePlannedActivityEjb() {
        super(PlannedActivityDTO.class);
    }

    /**
     * {@inheritDoc}
     */
    public void copyPlannedEligibilityStudyCriterions(Ii from, Ii to)
            throws PAException {
        try {
            locator.getPlannedActivityService().copy(from, to);
        } catch (PAException pe) {
            throw pe;
        } catch (Exception e) {
            throw new InvokeCoppaServiceException(e.toString(), e);
        }

    }

    /**
     * {@inheritDoc}
     */
    public PlannedEligibilityCriterionDTO createPlannedEligibilityCriterion(
            PlannedEligibilityCriterionDTO id) throws PAException {
        try {
            PlannedEligibilityCriterionDTO result =
                locator.getPlannedActivityService().createPlannedEligibilityCriterion(id);
            return result;
        } catch (PAException pe) {
            throw pe;
        } catch (Exception e) {
            throw new InvokeCoppaServiceException(e.toString(), e);
        }
    }

    /**
     * {@inheritDoc}
     */
    public void deletePlannedEligibilityCriterion(Ii id) throws PAException {
        try {
                locator.getPlannedActivityService().deletePlannedEligibilityCriterion(id);
        } catch (PAException pe) {
            throw pe;
        } catch (Exception e) {
            throw new InvokeCoppaServiceException(e.toString(), e);
        }
    }

    /**
     * {@inheritDoc}
     */
    public List<PlannedActivityDTO> getByArm(Ii id) throws PAException {
        try {
            List<PlannedActivityDTO> result =
                locator.getPlannedActivityService().getByArm(id);
            return result;
        } catch (PAException pe) {
            throw pe;
        } catch (Exception e) {
            throw new InvokeCoppaServiceException(e.toString(), e);
        }
    }

    /**
     * {@inheritDoc}
     */
    public PlannedEligibilityCriterionDTO getPlannedEligibilityCriterion(Ii id)
            throws PAException {
        try {
            PlannedEligibilityCriterionDTO result =
                locator.getPlannedActivityService().getPlannedEligibilityCriterion(id);
            return result;
        } catch (PAException pe) {
            throw pe;
        } catch (Exception e) {
            throw new InvokeCoppaServiceException(e.toString(), e);
        }
    }

    /**
     * {@inheritDoc}
     */
    public List<PlannedEligibilityCriterionDTO> getPlannedEligibilityCriterionByStudyProtocol(
            Ii id) throws PAException {
        try {
            List<PlannedEligibilityCriterionDTO> result =
                locator.getPlannedActivityService().getPlannedEligibilityCriterionByStudyProtocol(id);
            return result;
        } catch (PAException pe) {
            throw pe;
        } catch (Exception e) {
            throw new InvokeCoppaServiceException(e.toString(), e);
        }
    }

    /**
     * {@inheritDoc}
     */
    public PlannedEligibilityCriterionDTO updatePlannedEligibilityCriterion(
            PlannedEligibilityCriterionDTO id) throws PAException {
        try {
            PlannedEligibilityCriterionDTO result =
                locator.getPlannedActivityService().updatePlannedEligibilityCriterion(id);
            return result;
        } catch (PAException pe) {
            throw pe;
        } catch (Exception e) {
            throw new InvokeCoppaServiceException(e.toString(), e);
        }
    }


}
