package gov.nih.nci.accrual.web.util;
import gov.nih.nci.accrual.iso.util.IntConverter;
import gov.nih.nci.accrual.iso.util.StConverter;
import gov.nih.nci.accrual.service.SampleAccrualRemote;
import gov.nih.nci.coppa.iso.Int;
import gov.nih.nci.coppa.iso.St;

import java.rmi.RemoteException;


public class MockSampleAccrualService implements SampleAccrualRemote{

    public St getSquare(Int integer) throws RemoteException {
        Integer value = IntConverter.convertToInteger(integer);
        return StConverter.convertToSt(((Integer)(value * value)).toString());
    }

}
