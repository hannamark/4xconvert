package gov.nih.nci.pa.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import gov.nih.nci.coppa.iso.Cd;
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.po.service.EntityValidationException;
import gov.nih.nci.services.correlation.HealthCareFacilityDTO;
import gov.nih.nci.services.correlation.NullifiedRoleException;
import gov.nih.nci.services.correlation.ResearchOrganizationCorrelationServiceRemote;
import gov.nih.nci.services.correlation.ResearchOrganizationDTO;

public class MockPoResearchOrganizationCorrelationService implements ResearchOrganizationCorrelationServiceRemote {

    public Ii createCorrelation(ResearchOrganizationDTO arg0)
            throws EntityValidationException {
        // TODO Auto-generated method stub
        return null;
    }

    public ResearchOrganizationDTO getCorrelation(Ii ii)
            throws NullifiedRoleException {
        if ("NULLIFY".equals(ii.getIdentifierName())) {
            Map<Ii, Ii> nullifiedEntities = new HashMap<Ii, Ii>();
            throw new NullifiedRoleException(nullifiedEntities);
        }
        ResearchOrganizationDTO ro = new ResearchOrganizationDTO();
        ro.setIdentifier(ii);
        ro.setPlayerIdentifier(IiConverter.converToPoOrganizationIi("1"));
        ro.setStatus(CdConverter.convertStringToCd("ACTIVE"));
        return ro;
    }

    public List<ResearchOrganizationDTO> getCorrelations(Ii[] arg0)
            throws NullifiedRoleException {
        // TODO Auto-generated method stub
        return null;
    }

    public List<ResearchOrganizationDTO> search(ResearchOrganizationDTO arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    public void updateCorrelation(ResearchOrganizationDTO arg0)
            throws EntityValidationException {
        // TODO Auto-generated method stub
        
    }

    public void updateCorrelationStatus(Ii arg0, Cd arg1)
            throws EntityValidationException {
        // TODO Auto-generated method stub
        
    }

    public Map<String, String[]> validate(ResearchOrganizationDTO arg0) {
        // TODO Auto-generated method stub
        return null;
    }
    

}
