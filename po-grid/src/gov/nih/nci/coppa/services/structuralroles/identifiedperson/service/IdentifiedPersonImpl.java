package gov.nih.nci.coppa.services.structuralroles.identifiedperson.service;

import gov.nih.nci.coppa.po.IdentifiedPerson;
import gov.nih.nci.coppa.po.grid.services.GenericCorrelationGridServiceImpl;
import gov.nih.nci.services.correlation.IdentifiedPersonDTO;

import java.rmi.RemoteException;

import org.apache.log4j.LogManager;

/**
 * TODO:I am the service side implementation class.  IMPLEMENT AND DOCUMENT ME
 *
 * @created by Introduce Toolkit version 1.2
 *
 */
public class IdentifiedPersonImpl extends IdentifiedPersonImplBase {

private static org.apache.log4j.Logger logger = LogManager.getLogger(IdentifiedPersonImpl.class);

    private GenericCorrelationGridServiceImpl<IdentifiedPersonDTO,IdentifiedPerson> impl 
        = new GenericCorrelationGridServiceImpl<IdentifiedPersonDTO, IdentifiedPerson>(IdentifiedPerson.class, IdentifiedPersonDTO.class);

    public IdentifiedPersonImpl() throws RemoteException {
        super();
    }

  public gov.nih.nci.coppa.po.Id create(gov.nih.nci.coppa.po.IdentifiedPerson identifiedPerson) throws RemoteException, gov.nih.nci.coppa.po.faults.EntityValidationFault {
      return impl.create(identifiedPerson);
  }

  public gov.nih.nci.coppa.po.IdentifiedPerson getById(gov.nih.nci.coppa.po.Id id) throws RemoteException, gov.nih.nci.coppa.po.faults.NullifiedRoleFault {
      return getById(id);
  }

  public gov.nih.nci.coppa.po.IdentifiedPerson[] getByIds(gov.nih.nci.coppa.po.Id[] id) throws RemoteException, gov.nih.nci.coppa.po.faults.NullifiedRoleFault {
      return impl.getByIds(id);
  }

  public gov.nih.nci.coppa.po.IdentifiedPerson[] search(gov.nih.nci.coppa.po.IdentifiedPerson identifiedPerson) throws RemoteException {
      return impl.search(identifiedPerson);
  }

  public void update(gov.nih.nci.coppa.po.IdentifiedPerson identifiedPerson) throws RemoteException, gov.nih.nci.coppa.po.faults.EntityValidationFault {
      impl.update(identifiedPerson);
  }

  public void updateStatus(gov.nih.nci.coppa.po.Id targetId,gov.nih.nci.coppa.po.Cd statusCode) throws RemoteException, gov.nih.nci.coppa.po.faults.EntityValidationFault {
      impl.updateStatus(targetId, statusCode);
  }

  public gov.nih.nci.coppa.po.StringMap validate(gov.nih.nci.coppa.po.IdentifiedPerson identifiedPerson) throws RemoteException {
      return impl.validate(identifiedPerson);
  }

}

