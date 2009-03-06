package gov.nih.nci.coppa.po.grid.remote;

import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.services.entity.NullifiedEntityException;
import gov.nih.nci.services.person.PersonDTO;

import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class InvokePersonEjb {
	
	static Logger logger = LogManager.getLogger(InvokePersonEjb.class); 
    JNDIUtil jndiUtil = JNDIUtil.getInstance();
	
	public List<PersonDTO> search(PersonDTO person) throws InvokeCoppaServiceException {
		try {
			List<PersonDTO> persons = jndiUtil.getPersonService().search(person);
			return persons;
		}catch(Exception exception) {
			logger.error("Error searching persons.",exception);
			throw new InvokeCoppaServiceException(exception.toString(), exception);
		}
	}
	
	public PersonDTO getPerson(Ii ii) throws NullifiedEntityException,InvokeCoppaServiceException {
		try {
			PersonDTO person = jndiUtil.getPersonService().getPerson(ii);
			return person;
		}catch(NullifiedEntityException nee) {
			logger.error("Nullified entity exception getting persons.",nee);
			throw nee;
		}catch(Exception exception) {
			logger.error("Error getting persons.",exception);
			throw new InvokeCoppaServiceException(exception.toString(), exception);
		}
	}	
	
}
