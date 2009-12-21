package gov.nih.nci.coppa.services.outcomes.submission.service;

import gov.nih.nci.accrual.dto.SubmissionDto;
import gov.nih.nci.coppa.services.outcomes.Submission;
import gov.nih.nci.coppa.services.outcomes.grid.GenericAccrualStudyGridServiceImpl;
import gov.nih.nci.coppa.services.outcomes.grid.remote.InvokeSubmissionEjb;

import java.rmi.RemoteException;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * Dispatches to the remote EJBs and Transformers.
 *
 * @author ludetc
 *
 */
public class SubmissionImpl extends SubmissionImplBase {

    private static final Logger logger = LogManager.getLogger(SubmissionImpl.class);
    private final InvokeSubmissionEjb submissionService = new InvokeSubmissionEjb(SubmissionDto.class);
    private GenericAccrualStudyGridServiceImpl<SubmissionDto, Submission> impl
        = new GenericAccrualStudyGridServiceImpl<SubmissionDto, Submission>(Submission.class, SubmissionDto.class);

    public SubmissionImpl() throws RemoteException {
        super();
    }

  public gov.nih.nci.coppa.services.outcomes.Submission[] getByStudyProtocol(gov.nih.nci.coppa.services.outcomes.Id id) throws RemoteException {
        return impl.getByStudyProtocol(id);
    }

  public gov.nih.nci.coppa.services.outcomes.Submission get(gov.nih.nci.coppa.services.outcomes.Id id) throws RemoteException {
        return impl.get(id);
    }

  public gov.nih.nci.coppa.services.outcomes.Submission create(gov.nih.nci.coppa.services.outcomes.Submission submission) throws RemoteException {
        return impl.create(submission);
    }

  public gov.nih.nci.coppa.services.outcomes.Submission update(gov.nih.nci.coppa.services.outcomes.Submission submission) throws RemoteException {
        return impl.update(submission);
    }

  public void delete(gov.nih.nci.coppa.services.outcomes.Id id) throws RemoteException {
        impl.delete(id);
    }

}

