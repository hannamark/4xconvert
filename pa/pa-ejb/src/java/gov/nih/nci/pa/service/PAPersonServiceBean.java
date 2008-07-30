package gov.nih.nci.pa.service;

import gov.nih.nci.pa.dto.PersonDTO;
import gov.nih.nci.pa.service.impl.PAPersonServiceImpl;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.apache.log4j.Logger;

/**
 * 
 * @author Naveen Amiruddin
 * 
 *
 */
@Stateless
public class PAPersonServiceBean implements PAPersonServiceRemote {

    private static final Logger LOG  = Logger.getLogger(PAPersonServiceBean.class);

    /**
     * returns a list of PI name who have been associated with study protocol. 
     * @return list PersonDTO   
     * @throws PAException on error 
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<PersonDTO> getAllPrincipalInvestigators() throws PAException {
       LOG.info("Entering getAllPrincipalInvestigators");
       List<PersonDTO> personDTOs = null;
       PAPersonServiceImpl paPersonServiceImpl = new PAPersonServiceImpl();
       try {
           personDTOs = paPersonServiceImpl.getAllPrincipalInvestigators();
       } finally {
           LOG.info("Leaving getAllPrincipalInvestigators"); 
       }
       return personDTOs;
   }
    
}
