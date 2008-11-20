package gov.nih.nci.pa.service.util;



import gov.nih.nci.pa.dto.PAHealthCareProviderDTO;
import gov.nih.nci.pa.iso.dto.PersonWebDTO;
import gov.nih.nci.pa.service.PAException;

import java.util.List;

import javax.ejb.Remote;

/**
 * @author Harsha
 * @since 10/09/2008 copyright NCI 2007. All rights reserved. This code may not
 *        be used without the express written permission of the copyright
 *        holder, NCI.
 */
@Remote 
public interface PAHealthCareProviderRemote {
    
    /**
     * Gets the person primary key (PA World) using the PO id (PO World).
     * 
     * @param poIdentifier for search
     * @param roleCode for the role code   
     * @param spId as Study Participation Id
     * @return Long the primary key identifier
     * @throws PAException on error
     */
    Long getStudyParticationContactByPersonAndSPId(Long poIdentifier, Long spId, String roleCode) 
        throws PAException;
    
    /**
     * 
     * @param dto to be saved
     * @return Long id of the Health care provider
     * @throws PAException on error.
     */    
    Long createPAHealthCareProvider(PAHealthCareProviderDTO dto) throws PAException;
    
    /**
     * 
     * @param id to search
     * @param roleCd to search
     * @return List of personWebDTO
     * @throws PAException on error
     */
    List<PersonWebDTO> getPersonsByStudyParticpationId(Long id, String roleCd) throws PAException;
    
    /**
     * 
     * @param identifier to search
     * @return Long id
     * @throws PAException on error.
     */
    Long findHcpByIdentifier(Long identifier) throws PAException;
    
    /**
     *  
     * @param id the study participation id
     * @return PersonWebDTO
     * @throws PAException on error
     */
    PersonWebDTO getIdentifierBySPCId(Long id) throws PAException; 
    
}
