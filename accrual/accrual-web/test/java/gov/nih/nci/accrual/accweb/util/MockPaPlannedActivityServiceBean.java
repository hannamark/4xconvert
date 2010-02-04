package gov.nih.nci.accrual.accweb.util;

import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.enums.PatientGenderCode;
import gov.nih.nci.pa.iso.dto.PlannedActivityDTO;
import gov.nih.nci.pa.iso.dto.PlannedEligibilityCriterionDTO;
import gov.nih.nci.pa.iso.dto.PlannedProcedureDTO;
import gov.nih.nci.pa.iso.dto.PlannedSubstanceAdministrationDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.PlannedActivityServiceRemote;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MockPaPlannedActivityServiceBean implements PlannedActivityServiceRemote{

    private List<PlannedEligibilityCriterionDTO> pecList;
    {
        pecList = new ArrayList<PlannedEligibilityCriterionDTO>();
        PlannedEligibilityCriterionDTO dto= new PlannedEligibilityCriterionDTO();
        dto.setCriterionName(StConverter.convertToSt("GENDER"));
        dto.setEligibleGenderCode(CdConverter.convertToCd(PatientGenderCode.FEMALE));        
        pecList.add(dto);
    }
    /**
     * {@inheritDoc}
     */
    public void copyPlannedEligibilityStudyCriterions(Ii fromStudyProtocolIi, Ii toStudyProtocolIi) throws PAException {
        // TODO Auto-generated method stub

    }

    /**
     * {@inheritDoc}
     */
    public PlannedEligibilityCriterionDTO createPlannedEligibilityCriterion(PlannedEligibilityCriterionDTO dto)
            throws PAException {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * {@inheritDoc}
     */
    public PlannedProcedureDTO createPlannedProcedure(PlannedProcedureDTO dto) throws PAException {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * {@inheritDoc}
     */
    public PlannedSubstanceAdministrationDTO createPlannedSubstanceAdministration(PlannedSubstanceAdministrationDTO dto)
            throws PAException {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * {@inheritDoc}
     */
    public void deletePlannedEligibilityCriterion(Ii ii) throws PAException {
        // TODO Auto-generated method stub

    }

    /**
     * {@inheritDoc}
     */
    public List<PlannedActivityDTO> getByArm(Ii ii) throws PAException {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * {@inheritDoc}
     */
    public PlannedEligibilityCriterionDTO getPlannedEligibilityCriterion(Ii ii) throws PAException {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * {@inheritDoc}
     */
    public List<PlannedEligibilityCriterionDTO> getPlannedEligibilityCriterionByStudyProtocol(Ii ii) throws PAException {
        return pecList;
    }

    /**
     * {@inheritDoc}
     */
    public PlannedProcedureDTO getPlannedProcedure(Ii ii) throws PAException {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * {@inheritDoc}
     */
    public List<PlannedProcedureDTO> getPlannedProcedureByStudyProtocol(Ii ii) throws PAException {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * {@inheritDoc}
     */
    public PlannedSubstanceAdministrationDTO getPlannedSubstanceAdministration(Ii ii) throws PAException {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * {@inheritDoc}
     */
    public List<PlannedSubstanceAdministrationDTO> getPlannedSubstanceAdministrationByStudyProtocol(Ii ii)
            throws PAException {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * {@inheritDoc}
     */
    public PlannedEligibilityCriterionDTO updatePlannedEligibilityCriterion(PlannedEligibilityCriterionDTO dto)
            throws PAException {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * {@inheritDoc}
     */
    public PlannedProcedureDTO updatePlannedProcedure(PlannedProcedureDTO dto) throws PAException {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * {@inheritDoc}
     */
    public PlannedSubstanceAdministrationDTO updatePlannedSubstanceAdministration(PlannedSubstanceAdministrationDTO dto)
            throws PAException {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * {@inheritDoc}
     */
    public PlannedActivityDTO create(PlannedActivityDTO dto) throws PAException {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * {@inheritDoc}
     */
    public void delete(Ii ii) throws PAException {
        // TODO Auto-generated method stub

    }

    /**
     * {@inheritDoc}
     */
    public PlannedActivityDTO get(Ii ii) throws PAException {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * {@inheritDoc}
     */
    public PlannedActivityDTO update(PlannedActivityDTO dto) throws PAException {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * {@inheritDoc}
     */
    public void validate(PlannedActivityDTO dto) throws PAException {
        // TODO Auto-generated method stub

    }

    /**
     * {@inheritDoc}
     */
    public Map<Ii, Ii> copy(Ii fromStudyProtocolIi, Ii toStudyProtocolIi) throws PAException {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * {@inheritDoc}
     */
    public List<PlannedActivityDTO> getByStudyProtocol(Ii ii) throws PAException {
        // TODO Auto-generated method stub
        return null;
    }
}
