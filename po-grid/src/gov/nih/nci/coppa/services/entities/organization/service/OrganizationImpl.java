package gov.nih.nci.coppa.services.entities.organization.service;

import gov.nih.nci.coppa.iso.Cd;
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.coppa.po.Organization;
import gov.nih.nci.coppa.po.StringMap;
import gov.nih.nci.coppa.po.grid.dto.transform.po.IdTransformer;
import gov.nih.nci.coppa.po.grid.dto.transform.po.LimitOffsetTransformer;
import gov.nih.nci.coppa.po.grid.dto.transform.po.OrganizationTransformer;
import gov.nih.nci.coppa.po.grid.dto.transform.po.StringMapTransformer;
import gov.nih.nci.coppa.po.grid.dto.transform.po.faults.FaultUtil;
import gov.nih.nci.coppa.po.grid.remote.InvokeOrganizationEjb;
import gov.nih.nci.coppa.po.grid.remote.Utils;
import gov.nih.nci.coppa.services.grid.dto.transform.iso.CDTransformer;
import gov.nih.nci.coppa.services.grid.dto.transform.iso.IITransformer;
import gov.nih.nci.services.LimitOffset;
import gov.nih.nci.services.organization.OrganizationDTO;

import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

import org.apache.log4j.LogManager;

/**
 * TODO:I am the service side implementation class. IMPLEMENT AND DOCUMENT ME
 * 
 * @created by Introduce Toolkit version 1.2
 * 
 */
public class OrganizationImpl extends OrganizationImplBase {
    private static org.apache.log4j.Logger logger = LogManager.getLogger(OrganizationImpl.class);
    private InvokeOrganizationEjb service = new InvokeOrganizationEjb();

    public OrganizationImpl() throws RemoteException {
        super();
    }

  public gov.nih.nci.coppa.po.Organization getById(gov.nih.nci.coppa.po.Id id) throws RemoteException, gov.nih.nci.coppa.po.faults.NullifiedEntityFault {
        try {
            Ii ii_iso = IITransformer.INSTANCE.toDto(id);
            OrganizationDTO org_dto = service.getOrganization(ii_iso);
            Organization org = OrganizationTransformer.INSTANCE.toXml(org_dto);
            return org;
        } catch (Exception e) {
            logger.error("GETBYID:ORGANIZATION", e);
            throw FaultUtil.reThrowRemote(e);
        }
    }

  public gov.nih.nci.coppa.po.Id create(gov.nih.nci.coppa.po.Organization organization) throws RemoteException, gov.nih.nci.coppa.po.faults.EntityValidationFault {
        try {
            OrganizationDTO organizationDTO = OrganizationTransformer.INSTANCE.toDto(organization);
            Ii ii = service.createOrganization(organizationDTO);
            return IdTransformer.INSTANCE.toXml(ii);
        } catch (Exception e) {
            logger.error("CREATE:ORGANIZATION", e);
            throw FaultUtil.reThrowRemote(e);
        }
    }

  public gov.nih.nci.coppa.po.Organization[] search(gov.nih.nci.coppa.po.Organization organization) throws RemoteException, gov.nih.nci.coppa.po.faults.TooManyResultsFault {
      try {
          return this.query(organization, LimitOffsetTransformer.INSTANCE.toXml(Utils.DEFAULT_PAGING));
      } catch (Exception e) {
          throw FaultUtil.reThrowRemote(e);
      }
    }

  public void update(gov.nih.nci.coppa.po.Organization organization) throws RemoteException, gov.nih.nci.coppa.po.faults.EntityValidationFault {
        try {
            OrganizationDTO organizationDTO = OrganizationTransformer.INSTANCE.toDto(organization);
            service.updateOrganization(organizationDTO);
        } catch (Exception e) {
            logger.error("UPDATE:ORGANIZATION", e);
            throw FaultUtil.reThrowRemote(e);
        }
    }

  public void updateStatus(gov.nih.nci.coppa.po.Id targetId,gov.nih.nci.coppa.po.Cd statusCode) throws RemoteException, gov.nih.nci.coppa.po.faults.EntityValidationFault {
        try {
            Ii iiDto = IdTransformer.INSTANCE.toDto(targetId);
            Cd cdDto = CDTransformer.INSTANCE.toDto(statusCode);
            service.updateOrganizationStatus(iiDto, cdDto);
        } catch (Exception e) {
            logger.error("UPDATESTATUS:ORGANIZATION", e);
            throw FaultUtil.reThrowRemote(e);
        }
    }

  public gov.nih.nci.coppa.po.StringMap validate(gov.nih.nci.coppa.po.Organization organization) throws RemoteException {
        try {
            OrganizationDTO organizationDTO = OrganizationTransformer.INSTANCE.toDto(organization);
            Map<String, String[]> map = service.validate(organizationDTO);
            StringMap stringMap = StringMapTransformer.INSTANCE.toXml(map);
            return stringMap;
        } catch (Exception e) {
            logger.error("VALIDATE:ORGANIZATION", e);
            throw FaultUtil.reThrowRemote(e);
        }
    }

  public gov.nih.nci.coppa.po.Organization[] query(gov.nih.nci.coppa.po.Organization organization,gov.nih.nci.coppa.po.LimitOffset limitOffset) throws RemoteException, gov.nih.nci.coppa.po.faults.TooManyResultsFault {
      try {
          LimitOffset limitOffsetDTO = LimitOffsetTransformer.INSTANCE.toDto(limitOffset);
          OrganizationDTO org = OrganizationTransformer.INSTANCE.toDto(organization);
          List<OrganizationDTO> results = service.search(org, limitOffsetDTO);
          if (results == null) {
              return null;
          }
          gov.nih.nci.coppa.po.Organization[] returnResults = new gov.nih.nci.coppa.po.Organization[results.size()];
          int i = 0;
          for (OrganizationDTO res : results) {
              gov.nih.nci.coppa.po.Organization o = OrganizationTransformer.INSTANCE.toXml(res);
              returnResults[i++] = o;
          }
          return returnResults;
      } catch (Exception e) {
          logger.error("SEARCH:ORGANIZATION", e);
          throw FaultUtil.reThrowRemote(e);
      }
  }

}
