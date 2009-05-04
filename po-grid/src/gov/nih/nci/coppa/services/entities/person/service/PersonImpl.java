package gov.nih.nci.coppa.services.entities.person.service;

import gov.nih.nci.coppa.iso.Cd;
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.coppa.po.grid.dto.transform.po.IdTransformer;
import gov.nih.nci.coppa.po.grid.dto.transform.po.LimitOffsetTransformer;
import gov.nih.nci.coppa.po.grid.dto.transform.po.PersonTransformer;
import gov.nih.nci.coppa.po.grid.dto.transform.po.StringMapTransformer;
import gov.nih.nci.coppa.po.grid.dto.transform.po.faults.FaultUtil;
import gov.nih.nci.coppa.po.grid.remote.InvokePersonEjb;
import gov.nih.nci.coppa.po.grid.remote.Utils;
import gov.nih.nci.coppa.services.grid.dto.transform.iso.CDTransformer;
import gov.nih.nci.coppa.services.grid.dto.transform.iso.IITransformer;
import gov.nih.nci.services.LimitOffset;
import gov.nih.nci.services.person.PersonDTO;

import java.rmi.RemoteException;
import java.util.List;

import org.apache.log4j.LogManager;

/**
 * 
 * @created by Introduce Toolkit version 1.2
 * 
 */
public class PersonImpl extends PersonImplBase {

    private static org.apache.log4j.Logger logger = LogManager.getLogger(PersonImpl.class);
    private InvokePersonEjb personService = new InvokePersonEjb();

    public PersonImpl() throws RemoteException {
        super();
    }

  public gov.nih.nci.coppa.po.Person getById(gov.nih.nci.coppa.po.Id id) throws RemoteException, gov.nih.nci.coppa.po.faults.NullifiedEntityFault {
        try {
            Ii ii_dto = IITransformer.INSTANCE.toDto(id);
            PersonDTO person_dto = personService.getPerson(ii_dto);
            gov.nih.nci.coppa.po.Person person = PersonTransformer.INSTANCE.toXml(person_dto);
            return person;
        } catch (Exception e) {
            logger.error("Error in getting Person.", e);
            throw FaultUtil.reThrowRemote(e);
        }
    }

  public gov.nih.nci.coppa.po.Id create(gov.nih.nci.coppa.po.Person person) throws RemoteException, gov.nih.nci.coppa.po.faults.EntityValidationFault {
        try {
            PersonDTO dto = PersonTransformer.INSTANCE.toDto(person);
            return IdTransformer.INSTANCE.toXml(personService.createPerson(dto));
        } catch (Exception e) {
            logger.error("Error in creating Person.", e);
            throw FaultUtil.reThrowRemote(e);
        }
    }

  public gov.nih.nci.coppa.po.StringMap validate(gov.nih.nci.coppa.po.Person person) throws RemoteException {
        try {
            PersonDTO dto = PersonTransformer.INSTANCE.toDto(person);
            return StringMapTransformer.INSTANCE.toXml(personService.validate(dto));
        } catch (Exception e) {
            logger.error("Error in creating Person.", e);
            throw FaultUtil.reThrowRemote(e);
        }
    }

  public gov.nih.nci.coppa.po.Person[] search(gov.nih.nci.coppa.po.Person person) throws RemoteException, gov.nih.nci.coppa.po.faults.TooManyResultsFault {
      try {
          return this.query(person, LimitOffsetTransformer.INSTANCE.toXml(Utils.DEFAULT_PAGING));
      } catch (Exception e) {
          throw FaultUtil.reThrowRemote(e);
      }
    }

  public void update(gov.nih.nci.coppa.po.Person person) throws RemoteException, gov.nih.nci.coppa.po.faults.EntityValidationFault {
        try {
            PersonDTO dto = PersonTransformer.INSTANCE.toDto(person);
            personService.updatePerson(dto);
        } catch (Exception e) {
            logger.error("Error in creating Person.", e);
            throw FaultUtil.reThrowRemote(e);
        }
    }

  public void updateStatus(gov.nih.nci.coppa.po.Id targetId,gov.nih.nci.coppa.po.Cd statusCode) throws RemoteException, gov.nih.nci.coppa.po.faults.EntityValidationFault {
        try {
            Ii targetId_dto = IITransformer.INSTANCE.toDto(targetId);
            Cd targetStatus_dto = CDTransformer.INSTANCE.toDto(statusCode);
            personService.updatePersonStatus(targetId_dto, targetStatus_dto);
        } catch (Exception e) {
            logger.error("Error in creating Person.", e);
            throw FaultUtil.reThrowRemote(e);
        }
    }

  public gov.nih.nci.coppa.po.Person[] query(gov.nih.nci.coppa.po.Person person,gov.nih.nci.coppa.po.LimitOffset limitOffset) throws RemoteException, gov.nih.nci.coppa.po.faults.TooManyResultsFault {
      try {
          LimitOffset limitOffsetDTO = LimitOffsetTransformer.INSTANCE.toDto(limitOffset);
          PersonDTO person_iso = PersonTransformer.INSTANCE.toDto(person);
          List<PersonDTO> results = personService.search(person_iso, limitOffsetDTO);
          if (results == null) {
              return null;
          }
          logger.debug("Person(s) found from COPPA:" + results.size());
          gov.nih.nci.coppa.po.Person[] returnResults = new gov.nih.nci.coppa.po.Person[results.size()];
          int i = 0;
          for (PersonDTO person_res : results) {
              gov.nih.nci.coppa.po.Person person_res_tr = PersonTransformer.INSTANCE.toXml(person_res);
              returnResults[i++] = person_res_tr;
          }
          return returnResults;
      } catch (Exception e) {
          logger.error("Error in searching Person(s).", e);
          throw FaultUtil.reThrowRemote(e);
      }
  }

}
