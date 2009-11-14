package gov.nih.nci.coppa.services.pa.grid.remote;

import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.coppa.services.grid.remote.InvokeCoppaServiceException;
import gov.nih.nci.pa.iso.dto.PlannedActivityDTO;
import gov.nih.nci.pa.iso.dto.PlannedEligibilityCriterionDTO;
import gov.nih.nci.pa.iso.dto.PlannedProcedureDTO;
import gov.nih.nci.pa.iso.dto.PlannedSubstanceAdministrationDTO;
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

    /**
     * {@inheritDoc}
     */
    public PlannedProcedureDTO createPlannedProcedure(PlannedProcedureDTO arg0)
            throws PAException {
        throw new UnsupportedOperationException("Method not implemented");
    }

    /**
     * {@inheritDoc}
     */
    public PlannedSubstanceAdministrationDTO createPlannedSubstanceAdministration(
            PlannedSubstanceAdministrationDTO arg0) throws PAException {
        throw new UnsupportedOperationException("Method not implemented");
    }

    /**
     * {@inheritDoc}
     */
    public PlannedProcedureDTO getPlannedProcedure(Ii arg0) throws PAException {
        throw new UnsupportedOperationException("Method not implemented");
    }

    /**
     * {@inheritDoc}
     */
    public List<PlannedProcedureDTO> getPlannedProcedureByStudyProtocol(Ii arg0)
            throws PAException {
        throw new UnsupportedOperationException("Method not implemented");
    }

    /**
     * {@inheritDoc}
     */
    public PlannedSubstanceAdministrationDTO getPlannedSubstanceAdministration(
            Ii arg0) throws PAException {
        throw new UnsupportedOperationException("Method not implemented");
    }

    /**
     * {@inheritDoc}
     */
    public List<PlannedSubstanceAdministrationDTO> getPlannedSubstanceAdministrationByStudyProtocol(
            Ii arg0) throws PAException {
        throw new UnsupportedOperationException("Method not implemented");
    }

    /**
     * {@inheritDoc}
     */
    public PlannedProcedureDTO updatePlannedProcedure(PlannedProcedureDTO arg0)
            throws PAException {
        throw new UnsupportedOperationException("Method not implemented");
    }

    /**
     * {@inheritDoc}
     */
    public PlannedSubstanceAdministrationDTO updatePlannedSubstanceAdministration(
            PlannedSubstanceAdministrationDTO arg0) throws PAException {
        throw new UnsupportedOperationException("Method not implemented");
    }


}
