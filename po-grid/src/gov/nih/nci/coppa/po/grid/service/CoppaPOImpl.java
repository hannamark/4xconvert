package gov.nih.nci.coppa.po.grid.service;

import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.coppa.po.ClinicalResearchStaff;
import gov.nih.nci.coppa.po.HealthCareFacility;
import gov.nih.nci.coppa.po.Organization;
import gov.nih.nci.coppa.po.grid.dto.transform.po.ClinicalResearchStaffTransformer;
import gov.nih.nci.coppa.po.grid.dto.transform.po.HealthCareFacilityTransformer;
import gov.nih.nci.coppa.po.grid.dto.transform.IITransformer;
import gov.nih.nci.coppa.po.grid.dto.transform.po.OrganizationTransformer;
import gov.nih.nci.coppa.po.grid.dto.transform.po.PersonTransformer;
import gov.nih.nci.coppa.po.grid.remote.InvokeClinicalResearchStaffEjb;
import gov.nih.nci.coppa.po.grid.remote.InvokeHealthCareFacilityEjb;
import gov.nih.nci.coppa.po.grid.remote.InvokeOrganizationEjb;
import gov.nih.nci.coppa.po.grid.remote.InvokePersonEjb;
import gov.nih.nci.services.correlation.ClinicalResearchStaffDTO;
import gov.nih.nci.services.correlation.HealthCareFacilityDTO;
import gov.nih.nci.services.organization.OrganizationDTO;
import gov.nih.nci.services.person.PersonDTO;

import java.rmi.RemoteException;
import java.util.List;

import org.apache.log4j.LogManager;

/**
 * TODO:I am the service side implementation class. IMPLEMENT AND DOCUMENT ME
 * 
 * @created by Introduce Toolkit version 1.2
 * 
 */
public class CoppaPOImpl extends CoppaPOImplBase {

    private static org.apache.log4j.Logger logger = LogManager.getLogger(CoppaPOImpl.class);
    private InvokePersonEjb personService = new InvokePersonEjb();
    private InvokeOrganizationEjb organizationService = new InvokeOrganizationEjb();
    private InvokeHealthCareFacilityEjb healthCareFacilityService = new InvokeHealthCareFacilityEjb();
    private InvokeClinicalResearchStaffEjb clinicalResearchStaffService = new InvokeClinicalResearchStaffEjb();

    public CoppaPOImpl() throws RemoteException {
        super();
    }

  public gov.nih.nci.coppa.po.Person getPerson(gov.nih.nci.coppa.po.Id identifier) throws RemoteException {
        try {
            Ii ii_iso = IITransformer.INSTANCE.toDto(identifier);
            PersonDTO person_iso = personService.getPerson(ii_iso);
            gov.nih.nci.coppa.po.Person person = PersonTransformer.INSTANCE.toXml(person_iso);
            return person;
        } catch (Exception e) {
            logger.error("Error in getting persons.", e);
            throw reThrowRemote(e);
        }
    }

  public gov.nih.nci.coppa.po.Organization getOrganization(gov.nih.nci.coppa.po.Id identifier) throws RemoteException {
        try {
            Ii ii_iso = IITransformer.INSTANCE.toDto(identifier);
            OrganizationDTO org_dto = organizationService.getOrganization(ii_iso);
            Organization org = OrganizationTransformer.INSTANCE.toXml(org_dto);
            return org;
        } catch (Exception e) {
            logger.error("Error in getting persons.", e);
            throw reThrowRemote(e);
        }
    }

  public gov.nih.nci.coppa.po.Organization[] searchOrganizations(gov.nih.nci.coppa.po.Organization organization) throws RemoteException {
        try{
            OrganizationDTO org = OrganizationTransformer.INSTANCE.toDto(organization);
            List<OrganizationDTO> results = organizationService.search(org);
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
            logger.error("Error in searching persons.", e);
            throw reThrowRemote(e);
        }
    }

  public gov.nih.nci.coppa.po.Person[] searchPersons(gov.nih.nci.coppa.po.Person person) throws RemoteException {
        try {
            PersonDTO person_iso = PersonTransformer.INSTANCE.toDto(person);
            List<PersonDTO> results = personService.search(person_iso);
            if (results == null) {
                return null;
            }
            logger.debug("Persons searched from COPPA:" + results.size());
            gov.nih.nci.coppa.po.Person[] returnResults = new gov.nih.nci.coppa.po.Person[results.size()];
            int i = 0;
            for (PersonDTO person_res : results) {
                gov.nih.nci.coppa.po.Person person_res_tr = PersonTransformer.INSTANCE.toXml(person_res);
                returnResults[i++] = person_res_tr;
            }
            return returnResults;
        } catch (Exception e) {
            logger.error("Error in searching persons.", e);
            throw reThrowRemote(e);
        }
    }

  public gov.nih.nci.coppa.po.Person echoPerson(gov.nih.nci.coppa.po.Person person) throws RemoteException {
        return person;
    }

  public gov.nih.nci.coppa.po.Organization echoOrganization(gov.nih.nci.coppa.po.Organization organization) throws RemoteException {
        return organization;
    }

    private static RemoteException reThrowRemote(Throwable t) throws RemoteException {
        if (t instanceof RemoteException) {
            throw (RemoteException)t;
        }
        RemoteException re = new RemoteException(t.toString(), t);
        throw re;
    }
  public gov.nih.nci.coppa.po.HealthCareFacility echoHealthCareFacility(gov.nih.nci.coppa.po.HealthCareFacility healthCareFacility) throws RemoteException {
	  return healthCareFacility;
  }

  public gov.nih.nci.coppa.po.HealthCareFacility getHealthCareFacility(gov.nih.nci.coppa.po.Id identifier) throws RemoteException {
		try {
			Ii ii_iso = IITransformer.INSTANCE.toDto(identifier);
			HealthCareFacilityDTO hcf_dto = healthCareFacilityService
					.getHealthCareFacility(ii_iso);
			HealthCareFacility healthCareFacility = HealthCareFacilityTransformer.INSTANCE
					.toXml(hcf_dto);
			return healthCareFacility;
		} catch (Exception e) {
			logger.error("Error in getting healthcareFacility.", e);
			throw reThrowRemote(e);
		}

  }

  public gov.nih.nci.coppa.po.HealthCareFacility[] searchHealthCareFacilities(gov.nih.nci.coppa.po.HealthCareFacility healthCareFacility) throws RemoteException {
      try {
    	  HealthCareFacilityDTO hcf_iso = HealthCareFacilityTransformer.INSTANCE.toDto(healthCareFacility);
          List<HealthCareFacilityDTO> results = healthCareFacilityService.search(hcf_iso);
          if (results == null) {
              return null;
          }
          logger.debug("healthcarefacility searched from COPPA:" + results.size());
          gov.nih.nci.coppa.po.HealthCareFacility [] returnResults = new gov.nih.nci.coppa.po.HealthCareFacility[results.size()];
          int i = 0;
          for (HealthCareFacilityDTO hcf_res : results) {
        	  gov.nih.nci.coppa.po.HealthCareFacility hcf_res_tr = HealthCareFacilityTransformer.INSTANCE.toXml(hcf_res);
              returnResults[i++] = hcf_res_tr;
          }
          return returnResults;
      } catch (Exception e) {
          logger.error("Error in searching healthcare facility.", e);
          throw reThrowRemote(e);
      }

  }

  public gov.nih.nci.coppa.po.ClinicalResearchStaff echoClinicalResearchStaff(gov.nih.nci.coppa.po.ClinicalResearchStaff clinicalResearchStaff) throws RemoteException {
     return clinicalResearchStaff;
  }

  public gov.nih.nci.coppa.po.ClinicalResearchStaff getClinicalResearchStaff(gov.nih.nci.coppa.po.Id identifier) throws RemoteException {
		try {
			Ii ii_iso = IITransformer.INSTANCE.toDto(identifier);
			ClinicalResearchStaffDTO crs_dto = clinicalResearchStaffService.getClinicalResearchStaff(ii_iso);
			ClinicalResearchStaff clinicalResearchStaff = ClinicalResearchStaffTransformer.INSTANCE.toXml(crs_dto);
			return clinicalResearchStaff;
		} catch (Exception e) {
			logger.error("Error in getting ClinicalResearchStaff.", e);
			throw reThrowRemote(e);
		}
  }

  public gov.nih.nci.coppa.po.ClinicalResearchStaff[] searchClinicalResearchStaffs(gov.nih.nci.coppa.po.ClinicalResearchStaff clinicalResearchStaff) throws RemoteException {
      try {
    	  ClinicalResearchStaffDTO crs_iso = ClinicalResearchStaffTransformer.INSTANCE.toDto(clinicalResearchStaff);
          List<ClinicalResearchStaffDTO> results = clinicalResearchStaffService.search(crs_iso);
          if (results == null) {
              return null;
          }
          logger.debug("ClinicalResearchStaff searched from COPPA:" + results.size());
          gov.nih.nci.coppa.po.ClinicalResearchStaff [] returnResults = new gov.nih.nci.coppa.po.ClinicalResearchStaff[results.size()];
          int i = 0;
          for (ClinicalResearchStaffDTO crs_res : results) {
        	  gov.nih.nci.coppa.po.ClinicalResearchStaff crs_res_tr = ClinicalResearchStaffTransformer.INSTANCE.toXml(crs_res);
              returnResults[i++] = crs_res_tr;
          }
          return returnResults;
      } catch (Exception e) {
          logger.error("Error in searching ClinicalResearchStaff.", e);
          throw reThrowRemote(e);
      }
  }

}
