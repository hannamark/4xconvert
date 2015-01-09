package gov.nih.nci.pa.service.status;

import java.util.Date;
import java.util.List;

import org.junit.Test;

import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.status.json.AppName;
import gov.nih.nci.pa.service.status.json.TransitionFor;
import gov.nih.nci.pa.service.status.json.TrialType;
import gov.nih.nci.pa.util.AbstractEjbTestCase;
import static org.junit.Assert.*;

public class StatusTransitionServiceTest extends AbstractEjbTestCase {

    private StatusTransitionServiceBean statusTransitionServiceBean = new StatusTransitionServiceBean();
    
    @Test
    public void testBeginTransition() throws PAException {
       List<StatusDto> statusDtos = statusTransitionServiceBean.validateStatusTransition(
                AppName.PA,TrialType.ABBREVIATED, TransitionFor.TRIAL_STATUS, "IN_REVIEW",
                new Date(), "APPROVED");
       
       assertNotNull(statusDtos);
       assertFalse(statusDtos.isEmpty());
       
       StatusDto sd = statusDtos.get(0);
       assertFalse(sd.hasErrors());
    }
}
