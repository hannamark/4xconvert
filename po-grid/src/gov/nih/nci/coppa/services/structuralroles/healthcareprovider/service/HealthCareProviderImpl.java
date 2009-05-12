package gov.nih.nci.coppa.services.structuralroles.healthcareprovider.service;

import gov.nih.nci.coppa.po.HealthCareProvider;
import gov.nih.nci.coppa.po.grid.services.GenericCorrelationGridServiceImpl;
import gov.nih.nci.coppa.services.structuralroles.healthcarefacility.service.HealthCareFacilityImpl;
import gov.nih.nci.services.correlation.HealthCareProviderDTO;

import java.rmi.RemoteException;

import org.apache.log4j.LogManager;

/**
 * TODO:I am the service side implementation class.  IMPLEMENT AND DOCUMENT ME
 *
 * @created by Introduce Toolkit version 1.2
 *
 */
public class HealthCareProviderImpl extends HealthCareProviderImplBase {

private static org.apache.log4j.Logger logger = LogManager.getLogger(HealthCareFacilityImpl.class);

    private GenericCorrelationGridServiceImpl<HealthCareProviderDTO,HealthCareProvider> impl 
        = new GenericCorrelationGridServiceImpl<HealthCareProviderDTO, HealthCareProvider>(HealthCareProvider.class, HealthCareProviderDTO.class);

    public HealthCareProviderImpl() throws RemoteException {
        super();
    }

  public gov.nih.nci.coppa.po.Id create(gov.nih.nci.coppa.po.HealthCareProvider healthCareProvider) throws RemoteException, gov.nih.nci.coppa.po.faults.EntityValidationFault {
      return impl.create(healthCareProvider);
  }

  public gov.nih.nci.coppa.po.HealthCareProvider getById(gov.nih.nci.coppa.po.Id id) throws RemoteException, gov.nih.nci.coppa.po.faults.NullifiedRoleFault {
      return impl.getById(id);
  }

  public gov.nih.nci.coppa.po.HealthCareProvider[] getByIds(gov.nih.nci.coppa.po.Id[] id) throws RemoteException, gov.nih.nci.coppa.po.faults.NullifiedRoleFault {
      return impl.getByIds(id);
  }

  public gov.nih.nci.coppa.po.HealthCareProvider[] search(gov.nih.nci.coppa.po.HealthCareProvider healthCareProvider) throws RemoteException, gov.nih.nci.coppa.common.faults.TooManyResultsFault {
      return impl.search(healthCareProvider);
  }

  public void update(gov.nih.nci.coppa.po.HealthCareProvider healthCareProvider) throws RemoteException, gov.nih.nci.coppa.po.faults.EntityValidationFault {
      impl.update(healthCareProvider);
  }

  public void updateStatus(gov.nih.nci.coppa.po.Id targetId,gov.nih.nci.coppa.po.Cd statusCode) throws RemoteException, gov.nih.nci.coppa.po.faults.EntityValidationFault {
      impl.updateStatus(targetId, statusCode);
  }

  public gov.nih.nci.coppa.po.StringMap validate(gov.nih.nci.coppa.po.HealthCareProvider healthCareProvider) throws RemoteException {
      return impl.validate(healthCareProvider);
  }

  public gov.nih.nci.coppa.po.HealthCareProvider[] query(gov.nih.nci.coppa.po.HealthCareProvider healthCareProvider,gov.nih.nci.coppa.common.LimitOffset limitOffset) throws RemoteException, gov.nih.nci.coppa.common.faults.TooManyResultsFault {
      return impl.query(healthCareProvider, limitOffset);
  }

}

