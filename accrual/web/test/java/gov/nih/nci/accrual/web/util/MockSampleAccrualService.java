package gov.nih.nci.accrual.web.util;
import gov.nih.nci.accrual.service.SampleAccrualRemote;
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.coppa.iso.Int;
import gov.nih.nci.coppa.iso.St;
import gov.nih.nci.pa.iso.util.IntConverter;
import gov.nih.nci.pa.iso.util.StConverter;

import java.rmi.RemoteException;


public class MockSampleAccrualService implements SampleAccrualRemote{

    public St getSquare(Int integer) throws RemoteException {
        Integer value = IntConverter.convertToInteger(integer);
        return StConverter.convertToSt(((Integer)(value * value)).toString());
    }

    public St getEpochNameByIi(Ii ii) throws RemoteException {
        return StConverter.convertToSt("chickenpox");
    }
}
