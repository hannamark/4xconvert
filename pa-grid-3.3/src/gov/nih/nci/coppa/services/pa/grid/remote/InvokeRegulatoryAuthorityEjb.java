package gov.nih.nci.coppa.services.pa.grid.remote;

import gov.nih.nci.coppa.services.LimitOffset;
import gov.nih.nci.coppa.services.TooManyResultsException;
import gov.nih.nci.coppa.services.grid.remote.InvokeCoppaServiceException;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.iso21090.St;
import gov.nih.nci.pa.iso.dto.RegulatoryAuthorityDTO;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.RegulatoryAuthorityServiceRemote;

import java.util.List;

/**
 * Wrapper class for invoking the StudyProtocol remote EJB.
 */
public class InvokeRegulatoryAuthorityEjb implements RegulatoryAuthorityServiceRemote {

    
    /**
     * {@inheritDoc}
     */
    public List<RegulatoryAuthorityDTO> search(RegulatoryAuthorityDTO dto, LimitOffset pagingParams) 
    throws PAException,
            TooManyResultsException {
        try {
            return GridSecurityJNDIServiceLocator.newInstance().getRegulatoryAuthorityService()
                .search(dto, pagingParams);
        } catch (PAException pae) {
            throw pae;
        } catch (TooManyResultsException tmre) {
            throw tmre;
        } catch (Exception e) {
            throw new InvokeCoppaServiceException(e.toString(), e);
        }
    }

    /**
     * {@inheritDoc}
     */
    public Ii getRegulatoryAuthorityId(St arg0, St arg1) throws PAException {
        throw new PAException("not implemented");
    }

    /**
     * {@inheritDoc}
     */
    public RegulatoryAuthorityDTO create(RegulatoryAuthorityDTO arg0) throws PAException {
        throw new PAException("not implemented");
    }

    /**
     * {@inheritDoc}
     */
    public void delete(Ii arg0) throws PAException {
        throw new PAException("not implemented");
    }

    /**
     * {@inheritDoc}
     */
    public RegulatoryAuthorityDTO get(Ii arg0) throws PAException {
        throw new PAException("not implemented");
    }

    /**
     * {@inheritDoc}
     */
    public RegulatoryAuthorityDTO update(RegulatoryAuthorityDTO arg0) throws PAException {
        throw new PAException("not implemented");
    }

    /**
     * {@inheritDoc}
     */
    public void validate(RegulatoryAuthorityDTO arg0) throws PAException {
        throw new PAException("not implemented");   
    }
    
    /**
     * {@inheritDoc}
     */
    public List<RegulatoryAuthorityDTO> getAll() throws PAException {
        throw new PAException("not implemented");
    }
   
}
