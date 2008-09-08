package gov.nih.nci.pa.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import gov.nih.nci.pa.dao.PersonDAO;
import gov.nih.nci.pa.domain.Person;
import gov.nih.nci.pa.dto.PersonDTO;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.PAPersonService;

/**
 * 
 * @author Naveen Amiruddin
 * 
 * 
 */

public class PAPersonServiceImpl implements PAPersonService {
    
    private static final Logger LOG  = Logger.getLogger(PAPersonServiceImpl.class);
    
    /**
     * returns a list of PI name who have been associated with study protocol. 
     * @return list PersonDTO   
     * @throws PAException on error 
     */
   public List<PersonDTO> getAllPrincipalInvestigators() throws PAException {
       LOG.info("Entering getAllPrincipalInvestigators");
       List<PersonDTO> personDTOs = new ArrayList<PersonDTO>();
       List<Person> persons = null;
       PersonDAO personDAO = new PersonDAO();
       try {
           persons = personDAO.getAllPrincipalInvestigators();
           Person person = null;
           
           for (int i = 0; i < persons.size(); i++) {
               PersonDTO personDTO = new PersonDTO();
               person = (Person) persons.get(i);
               personDTO.setId(person.getId());
               personDTO.setFirstName(person.getFirstName());
               personDTO.setLastName(person.getLastName());
               personDTO.setMiddleName(person.getMiddleName());
               personDTOs.add(personDTO);
           }
       } finally {
           LOG.info("Leaving getAllPrincipalInvestigators"); 
       }
       return personDTOs;
   }

   
   

}
