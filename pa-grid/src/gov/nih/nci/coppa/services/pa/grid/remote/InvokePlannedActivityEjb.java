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
public class InvokePlannedActivityEjb extends InvokeStudyPaServiceEjb<PlannedActivityDTO> implements
        PlannedActivityServiceRemote {

    /**
     * Const.
     */
    public InvokePlannedActivityEjb() {
        super(PlannedActivityDTO.class);
    }

    /**
     * {@inheritDoc}
     */
    public void copyPlannedEligibilityStudyCriterions(Ii from, Ii to) throws PAException {
        try {
            GridSecurityJNDIServiceLocator.newInstance().getPlannedActivityService().copy(from, to);
        } catch (PAException pe) {
            throw pe;
        } catch (Exception e) {
            throw new InvokeCoppaServiceException(e.toString(), e);
        }

    }

    /**
     * {@inheritDoc}
     */
    public PlannedEligibilityCriterionDTO createPlannedEligibilityCriterion(PlannedEligibilityCriterionDTO id)
            throws PAException {
        try {
            PlannedEligibilityCriterionDTO result = GridSecurityJNDIServiceLocator.newInstance()
                    .getPlannedActivityService().createPlannedEligibilityCriterion(id);
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
            GridSecurityJNDIServiceLocator.newInstance().getPlannedActivityService().deletePlannedEligibilityCriterion(
                    id);
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
            List<PlannedActivityDTO> result = GridSecurityJNDIServiceLocator.newInstance().getPlannedActivityService()
                    .getByArm(id);
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
    public PlannedEligibilityCriterionDTO getPlannedEligibilityCriterion(Ii id) throws PAException {
        try {
            PlannedEligibilityCriterionDTO result = GridSecurityJNDIServiceLocator.newInstance()
                    .getPlannedActivityService().getPlannedEligibilityCriterion(id);
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
    public List<PlannedEligibilityCriterionDTO> getPlannedEligibilityCriterionByStudyProtocol(Ii id) 
        throws PAException {
        try {
            List<PlannedEligibilityCriterionDTO> result = GridSecurityJNDIServiceLocator.newInstance()
                    .getPlannedActivityService().getPlannedEligibilityCriterionByStudyProtocol(id);
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
    public PlannedEligibilityCriterionDTO updatePlannedEligibilityCriterion(PlannedEligibilityCriterionDTO id)
            throws PAException {
        try {
            PlannedEligibilityCriterionDTO result = GridSecurityJNDIServiceLocator.newInstance()
                    .getPlannedActivityService().updatePlannedEligibilityCriterion(id);
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
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * {@inheritDoc}
     */
    public PlannedSubstanceAdministrationDTO createPlannedSubstanceAdministration(
            PlannedSubstanceAdministrationDTO arg0) throws PAException {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * {@inheritDoc}
     */
    public PlannedProcedureDTO getPlannedProcedure(Ii arg0) throws PAException {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * {@inheritDoc}
     */
    public List<PlannedProcedureDTO> getPlannedProcedureByStudyProtocol(Ii arg0)
            throws PAException {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * {@inheritDoc}
     */
    public PlannedSubstanceAdministrationDTO getPlannedSubstanceAdministration(
            Ii arg0) throws PAException {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * {@inheritDoc}
     */
    public List<PlannedSubstanceAdministrationDTO> getPlannedSubstanceAdministrationByStudyProtocol(
            Ii arg0) throws PAException {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * {@inheritDoc}
     */
    public PlannedProcedureDTO updatePlannedProcedure(PlannedProcedureDTO arg0)
            throws PAException {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * {@inheritDoc}
     */
    public PlannedSubstanceAdministrationDTO updatePlannedSubstanceAdministration(
            PlannedSubstanceAdministrationDTO arg0) throws PAException {
        // TODO Auto-generated method stub
        return null;
    }

}
