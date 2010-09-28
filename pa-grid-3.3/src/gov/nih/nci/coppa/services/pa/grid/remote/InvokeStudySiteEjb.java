package gov.nih.nci.coppa.services.pa.grid.remote;

import gov.nih.nci.coppa.services.LimitOffset;
import gov.nih.nci.coppa.services.TooManyResultsException;
import gov.nih.nci.coppa.services.grid.remote.InvokeCoppaServiceException;
import gov.nih.nci.iso21090.Cd;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.iso.dto.StudySiteDTO;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.StudySiteServiceRemote;
import gov.nih.nci.po.data.CurationException;
import gov.nih.nci.po.service.EntityValidationException;

import java.util.List;

import org.apache.commons.lang.NotImplementedException;

/**
 * Wrapper class for invoking the StudyParticipant remote EJB.
 */
public class InvokeStudySiteEjb extends InvokeStudyPaServiceEjb<StudySiteDTO> implements StudySiteServiceRemote {

    /**
     * Const.
     */
    public InvokeStudySiteEjb() {
        super(StudySiteDTO.class);
    }

    /**
     * {@inheritDoc}
     */
    public List<StudySiteDTO> getByStudyProtocol(Ii studyProtocolIi, StudySiteDTO dto) throws PAException {
        try {
            List<StudySiteDTO> result = GridSecurityJNDIServiceLocator.newInstance().getStudySiteService()
                    .getByStudyProtocol(studyProtocolIi, dto);
            return result;
        } catch (PAException pae) {
            throw pae;
        } catch (Exception e) {
            throw new InvokeCoppaServiceException(e.toString(), e);
        }
    }

    /**
     * {@inheritDoc}
     */
    public List<StudySiteDTO> getByStudyProtocol(Ii ii, List<StudySiteDTO> dto) throws PAException {
        try {
            List<StudySiteDTO> result = GridSecurityJNDIServiceLocator.newInstance().getStudySiteService()
                    .getByStudyProtocol(ii, dto);
            return result;
        } catch (PAException pae) {
            throw pae;
        } catch (Exception e) {
            throw new InvokeCoppaServiceException(e.toString(), e);
        }
    }

    /**
     * {@inheritDoc}
     */
    public void cascadeRoleStatus(Ii ii, Cd roleStatusCode) throws PAException {
        try {
            GridSecurityJNDIServiceLocator.newInstance().getStudySiteService().cascadeRoleStatus(ii, roleStatusCode);
        } catch (PAException pae) {
            throw pae;
        } catch (Exception e) {
            throw new InvokeCoppaServiceException(e.toString(), e);
        }
    }
    /**
     * {@inheritDoc}
     */
    public List<StudySiteDTO> search(StudySiteDTO dto, LimitOffset pagingParams)
            throws PAException, TooManyResultsException {
        try {
            return GridSecurityJNDIServiceLocator.newInstance().getStudySiteService().search(dto, pagingParams);
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
    public Ii getStudySiteIiByTrialAndPoHcfIi(Ii studyProtocolIi, Ii poHcfIi) throws EntityValidationException,
            CurationException, PAException, TooManyResultsException {
        throw new NotImplementedException();
    }
}
