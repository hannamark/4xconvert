package gov.nih.nci.service;

import gov.nih.nci.coppa.iso.Cd;
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.coppa.iso.NullFlavor;
import gov.nih.nci.coppa.services.LimitOffset;
import gov.nih.nci.coppa.services.TooManyResultsException;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.DSetConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.po.data.CurationException;
import gov.nih.nci.po.service.EntityValidationException;
import gov.nih.nci.services.correlation.NullifiedRoleException;
import gov.nih.nci.services.correlation.PatientCorrelationServiceRemote;
import gov.nih.nci.services.correlation.PatientDTO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MockPoPatientCorrelationService implements PatientCorrelationServiceRemote{

    public Ii createCorrelation(PatientDTO arg0)
            throws EntityValidationException, CurationException {
        return IiConverter.convertToPOPatientIi(1L);   
    }

    public PatientDTO getCorrelation(Ii ii) throws NullifiedRoleException {
        if (NullFlavor.NA.equals(ii.getNullFlavor())) {
            Map<Ii, Ii> nullifiedEntities = new HashMap<Ii, Ii>();
            nullifiedEntities.put(ii, IiConverter.convertToPoOrganizationalContactIi("1"));
            throw new NullifiedRoleException(nullifiedEntities);
        }
        PatientDTO dto = new PatientDTO();
        dto.setIdentifier(DSetConverter.convertIiToDset(ii));
        dto.setPlayerIdentifier(IiConverter.convertToPoPersonIi("1"));
        dto.setScoperIdentifier(IiConverter.convertToPoOrganizationIi("1"));
        dto.setStatus(CdConverter.convertStringToCd("ACTIVE"));
        return dto;    
    }

    public List<PatientDTO> getCorrelations(Ii[] arg0)
            throws NullifiedRoleException {
        // TODO Auto-generated method stub
        return null;
    }

    public List<PatientDTO> getCorrelationsByPlayerIds(Ii[] arg0)
            throws NullifiedRoleException {
        // TODO Auto-generated method stub
        return null;
    }

    public List<PatientDTO> search(PatientDTO arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    public List<PatientDTO> search(PatientDTO arg0, LimitOffset arg1)
            throws TooManyResultsException {
        // TODO Auto-generated method stub
        return null;
    }

    public void updateCorrelation(PatientDTO arg0)
            throws EntityValidationException {
        // TODO Auto-generated method stub
        
    }

    public void updateCorrelationStatus(Ii arg0, Cd arg1)
            throws EntityValidationException {
        // TODO Auto-generated method stub
        
    }

    public Map<String, String[]> validate(PatientDTO arg0) {
        // TODO Auto-generated method stub
        return null;
    }

}
