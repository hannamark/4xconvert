package gov.nih.nci.pa.service;

import gov.nih.nci.coppa.iso.Cd;
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.coppa.iso.NullFlavor;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.po.service.EntityValidationException;
import gov.nih.nci.services.correlation.HealthCareFacilityCorrelationServiceRemote;
import gov.nih.nci.services.correlation.HealthCareFacilityDTO;
import gov.nih.nci.services.correlation.NullifiedRoleException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MockPoHealthCareFacilityCorrelationService implements HealthCareFacilityCorrelationServiceRemote {

    public Ii createCorrelation(HealthCareFacilityDTO arg0)
            throws EntityValidationException {
        return IiConverter.converToPoHealthCareFacilityIi("1");
    }

    public HealthCareFacilityDTO getCorrelation(Ii ii)
            throws NullifiedRoleException {
        if (NullFlavor.NA.equals(ii.getNullFlavor())) {
            Map<Ii, Ii> nullifiedEntities = new HashMap<Ii, Ii>();
            nullifiedEntities.put(ii, IiConverter.converToPoHealthCareFacilityIi("1"));
            throw new NullifiedRoleException(nullifiedEntities);
        }
        HealthCareFacilityDTO hcf = new HealthCareFacilityDTO();
        hcf.setIdentifier(ii);
        hcf.setPlayerIdentifier(IiConverter.converToPoOrganizationIi("584"));
        hcf.setStatus(CdConverter.convertStringToCd("ACTIVE"));
        return hcf;
    }

    public List<HealthCareFacilityDTO> getCorrelations(Ii[] arg0)
            throws NullifiedRoleException {
        // TODO Auto-generated method stub
        return null;
    }

    public List<HealthCareFacilityDTO> search(HealthCareFacilityDTO hcfDto) {
        List<HealthCareFacilityDTO> ar = new ArrayList<HealthCareFacilityDTO>();
        HealthCareFacilityDTO hcf = new HealthCareFacilityDTO();
        hcf.setIdentifier(IiConverter.converToPoHealthCareFacilityIi("1"));
        hcf.setPlayerIdentifier(IiConverter.converToPoOrganizationIi("1"));
        hcf.setStatus(CdConverter.convertStringToCd("ACTIVE"));       
        ar.add(hcf);
        return ar;
    }

    public void updateCorrelation(HealthCareFacilityDTO arg0)
            throws EntityValidationException {
        // TODO Auto-generated method stub
        
    }

    public void updateCorrelationStatus(Ii arg0, Cd arg1)
            throws EntityValidationException {
        // TODO Auto-generated method stub
        
    }

    public Map<String, String[]> validate(HealthCareFacilityDTO arg0) {
        // TODO Auto-generated method stub
        return null;
    }

}
