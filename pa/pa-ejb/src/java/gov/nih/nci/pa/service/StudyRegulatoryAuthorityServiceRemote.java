package gov.nih.nci.pa.service;

import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.pa.iso.dto.StudyRegulatoryAuthorityDTO;

import javax.ejb.Remote;



/**
 * 
 * @author Harsha
 * @since 08/05/2008
 * copyright NCI 2007.  All rights reserved.
 * This code may not be used without the express written permission of the
 * copyright holder, NCI.
 */
@Remote
public interface StudyRegulatoryAuthorityServiceRemote {
    
    /**
     * 
     * @param ii to be retrieved
     * @return StudyRegulatoryAuthorityDTO as the dto
     * @throws PAException on error
     */
    StudyRegulatoryAuthorityDTO getStudyRegulatoryAuthority(Ii ii)  throws PAException;
    
    /**
     * 
     * @param sraDTO as parameter
     * @return StudyRegulatoryAuthorityDTO as the return object
     * @throws PAException on error
     */ 
    StudyRegulatoryAuthorityDTO createStudyRegulatoryAuthority(StudyRegulatoryAuthorityDTO sraDTO) 
                                                                                            throws PAException;
    /**
     * 
     * @param sraDTO as parameter
     * @return StudyRegulatoryAuthorityDTO as DTO
     * @throws PAException on exception
     */
    StudyRegulatoryAuthorityDTO updateStudyRegulatoryAuthority(StudyRegulatoryAuthorityDTO sraDTO) 
                                                    throws PAException;
}
