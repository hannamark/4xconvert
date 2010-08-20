/**
 * 
 */
package gov.nih.nci.pa.service;

import gov.nih.nci.coppa.services.LimitOffset;
import gov.nih.nci.coppa.services.TooManyResultsException;
import gov.nih.nci.iso21090.Cd;
import gov.nih.nci.iso21090.IdentifierReliability;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.iso21090.NullFlavor;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.DSetConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.po.service.EntityValidationException;
import gov.nih.nci.services.correlation.HealthCareProviderCorrelationServiceRemote;
import gov.nih.nci.services.correlation.HealthCareProviderDTO;
import gov.nih.nci.services.correlation.NullifiedRoleException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Vrushali
 *
 */
public class MockPoHealthCareProviderCorrelationService implements
        HealthCareProviderCorrelationServiceRemote {

    /* (non-Javadoc)
     * @see gov.nih.nci.services.CorrelationService#createCorrelation(gov.nih.nci.services.PoDto)
     */
    public Ii createCorrelation(HealthCareProviderDTO arg0)
            throws EntityValidationException {
        return IiConverter.convertToPoHealtcareProviderIi("1");    
    }

    /* (non-Javadoc)
     * @see gov.nih.nci.services.CorrelationService#getCorrelation(gov.nih.nci.iso21090.Ii)
     */
    public HealthCareProviderDTO getCorrelation(Ii ii) throws NullifiedRoleException {
    if (NullFlavor.NA.equals(ii.getNullFlavor())) {
        Map<Ii, Ii> nullifiedEntities = new HashMap<Ii, Ii>();
        nullifiedEntities.put(ii, IiConverter.convertToPoHealtcareProviderIi("1"));
        throw new NullifiedRoleException(nullifiedEntities);
    }
    HealthCareProviderDTO hcp = new HealthCareProviderDTO();
    ii.setReliability(IdentifierReliability.ISS);
    ii.setRoot(IiConverter.HEALTH_CARE_PROVIDER_ROOT);
    hcp.setIdentifier(DSetConverter.convertIiToDset(ii));
    hcp.setPlayerIdentifier(IiConverter.convertToPoPersonIi("abc"));
    hcp.setScoperIdentifier(IiConverter.convertToPoOrganizationIi("abc"));
    hcp.setStatus(CdConverter.convertStringToCd("ACTIVE"));
    return hcp;
    }

    /* (non-Javadoc)
     * @see gov.nih.nci.services.CorrelationService#getCorrelations(gov.nih.nci.iso21090.Ii[])
     */
    public List<HealthCareProviderDTO> getCorrelations(Ii[] arg0)
            throws NullifiedRoleException {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see gov.nih.nci.services.CorrelationService#search(gov.nih.nci.services.PoDto)
     */
    public List<HealthCareProviderDTO> search(HealthCareProviderDTO arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see gov.nih.nci.services.CorrelationService#updateCorrelation(gov.nih.nci.services.PoDto)
     */
    public void updateCorrelation(HealthCareProviderDTO arg0)
            throws EntityValidationException {
        // TODO Auto-generated method stub

    }

    /* (non-Javadoc)
     * @see gov.nih.nci.services.CorrelationService#updateCorrelationStatus(gov.nih.nci.iso21090.Ii, gov.nih.nci.iso21090.Cd)
     */
    public void updateCorrelationStatus(Ii arg0, Cd arg1)
            throws EntityValidationException {
        // TODO Auto-generated method stub

    }

    /* (non-Javadoc)
     * @see gov.nih.nci.services.CorrelationService#validate(gov.nih.nci.services.PoDto)
     */
    public Map<String, String[]> validate(HealthCareProviderDTO arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    public List<HealthCareProviderDTO> search(HealthCareProviderDTO arg0,
            LimitOffset arg1) throws TooManyResultsException {
        // TODO Auto-generated method stub
        return null;
    }

    public List<HealthCareProviderDTO> getCorrelationsByPlayerIds(Ii[] arg0)
            throws NullifiedRoleException {
        // TODO Auto-generated method stub
        return null;
    }

}
