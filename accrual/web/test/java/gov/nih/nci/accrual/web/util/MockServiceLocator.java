package gov.nih.nci.accrual.web.util;

import gov.nih.nci.accrual.service.SampleAccrualBean;
import gov.nih.nci.accrual.service.SampleAccrualRemote;

public class MockServiceLocator implements ServiceLocator{
    private final SampleAccrualRemote sampleAccrual = new SampleAccrualBean();

    public SampleAccrualRemote getSampleAccrualService() {
        return sampleAccrual;
    }

}
