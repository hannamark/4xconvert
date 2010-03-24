package gov.nih.nci.coppa.services.outcomes.business.service;

import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.coppa.services.grid.dto.transform.iso.IITransformer;
import gov.nih.nci.coppa.services.outcomes.grid.dto.transform.business.PatientTransformer;
import gov.nih.nci.coppa.services.outcomes.grid.dto.transform.faults.FaultUtil;
import gov.nih.nci.coppa.services.outcomes.grid.remote.GridSecurityJNDIServiceLocator;
import gov.nih.nci.outcomes.svc.dto.PatientSvcDto;

import java.rmi.RemoteException;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/** 
 * TODO:I am the service side implementation class.  IMPLEMENT AND DOCUMENT ME
 * 
 * @created by Introduce Toolkit version 1.3
 * 
 */
public class BusinessImpl extends BusinessImplBase {
    private static final Logger LOG = LogManager.getLogger(BusinessImpl.class);
   
   public BusinessImpl() throws RemoteException {
      super();
   }
   
  public gov.nih.nci.coppa.services.outcomes.business.Patient[] get(gov.nih.nci.coppa.services.outcomes.business.Patient patient) throws RemoteException {
      try {
          PatientSvcDto dto = PatientTransformer.INSTANCE.toDto(patient);
          List<PatientSvcDto> result = GridSecurityJNDIServiceLocator.newInstance().getOutcomesService().get(dto);
          return PatientTransformer.INSTANCE.convert(result);
      } catch (Exception e) {
          LOG.error(e.getMessage(), e);
          throw FaultUtil.reThrowRemote(e);
      }
  }

  public gov.nih.nci.coppa.services.outcomes.business.Patient getById(gov.nih.nci.coppa.services.outcomes.Id id) throws RemoteException {
      try {
          Ii dto = IITransformer.INSTANCE.toDto(id);
          PatientSvcDto result = GridSecurityJNDIServiceLocator.newInstance().getOutcomesService().getById(dto);
          return PatientTransformer.INSTANCE.toXml(result);
      } catch (Exception e) {
          LOG.error(e.getMessage(), e);
          throw FaultUtil.reThrowRemote(e);
      }
  }

  public gov.nih.nci.coppa.services.outcomes.business.Patient write(gov.nih.nci.coppa.services.outcomes.business.Patient patient) throws RemoteException {
      try {
          PatientSvcDto dto = PatientTransformer.INSTANCE.toDto(patient);
          PatientSvcDto result = GridSecurityJNDIServiceLocator.newInstance().getOutcomesService().write(dto);
          return PatientTransformer.INSTANCE.toXml(result);
      } catch (Exception e) {
          LOG.error(e.getMessage(), e);
          throw FaultUtil.reThrowRemote(e);
      }
  }

}

