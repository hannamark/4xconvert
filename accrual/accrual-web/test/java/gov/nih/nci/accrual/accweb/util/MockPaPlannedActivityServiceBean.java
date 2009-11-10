package gov.nih.nci.accrual.accweb.util;

import java.util.List;
import java.util.Map;

import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.pa.iso.dto.PlannedActivityDTO;
import gov.nih.nci.pa.iso.dto.PlannedEligibilityCriterionDTO;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.PlannedActivityServiceRemote;

public class MockPaPlannedActivityServiceBean implements PlannedActivityServiceRemote{

    public void copyPlannedEligibilityStudyCriterions(Ii arg0, Ii arg1)
            throws PAException {
        // TODO Auto-generated method stub

    }

    public PlannedEligibilityCriterionDTO createPlannedEligibilityCriterion(
            PlannedEligibilityCriterionDTO arg0) throws PAException {
        // TODO Auto-generated method stub
        return null;
    }

    public void deletePlannedEligibilityCriterion(Ii arg0) throws PAException {
        // TODO Auto-generated method stub

    }

    public List<PlannedActivityDTO> getByArm(Ii arg0) throws PAException {
        // TODO Auto-generated method stub
        return null;
    }

    public PlannedEligibilityCriterionDTO getPlannedEligibilityCriterion(Ii arg0)
            throws PAException {
        // TODO Auto-generated method stub
        return null;
    }

    public List<PlannedEligibilityCriterionDTO> getPlannedEligibilityCriterionByStudyProtocol(
            Ii arg0) throws PAException {
        // TODO Auto-generated method stub
        return null;
    }

    public PlannedEligibilityCriterionDTO updatePlannedEligibilityCriterion(
            PlannedEligibilityCriterionDTO arg0) throws PAException {
        // TODO Auto-generated method stub
        return null;
    }

    public Map<Ii, Ii> copy(Ii arg0, Ii arg1) throws PAException {
        // TODO Auto-generated method stub
        return null;
    }

    public List<PlannedActivityDTO> getByStudyProtocol(Ii arg0)
            throws PAException {
        // TODO Auto-generated method stub
        return null;
    }

    public PlannedActivityDTO create(PlannedActivityDTO arg0)
            throws PAException {
        // TODO Auto-generated method stub
        return null;
    }

    public void delete(Ii arg0) throws PAException {
        // TODO Auto-generated method stub

    }

    public PlannedActivityDTO get(Ii arg0) throws PAException {
        // TODO Auto-generated method stub
        return null;
    }

    public PlannedActivityDTO update(PlannedActivityDTO arg0)
            throws PAException {
        // TODO Auto-generated method stub
        return null;
    }

    public void validate(PlannedActivityDTO arg0) throws PAException {
        // TODO Auto-generated method stub

    }

}
