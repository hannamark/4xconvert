package gov.nih.nci.coppa.services.outcomes.grid.remote;

import gov.nih.nci.accrual.dto.SubmissionDto;
import gov.nih.nci.accrual.service.SubmissionService;

/**
 * Invokes the Submission Service.
 * 
 * @author ludetc
 *
 */
public class InvokeSubmissionEjb extends InvokeAccrualStudyServiceEjb<SubmissionDto> implements SubmissionService {

    /**
     * Constructor. 
     * 
     * @param type SubmissionDto
     */
    public InvokeSubmissionEjb(Class<SubmissionDto> type) {
        super(type);
    }
}
