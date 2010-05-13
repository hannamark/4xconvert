package gov.nih.nci.accrual.outweb.util;

import gov.nih.nci.outcomes.svc.PHRConnectorSvc;
import gov.nih.nci.outcomes.svc.exception.OutcomesException;

public class MockPHRConnectorSvcBean implements PHRConnectorSvc {

    public String getCDAIdentifierMessage(String id, String user, String password) throws OutcomesException {
        return "01234";
    }
}
