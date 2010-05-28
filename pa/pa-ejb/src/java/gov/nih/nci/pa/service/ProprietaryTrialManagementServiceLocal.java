package gov.nih.nci.pa.service;

import gov.nih.nci.iso21090.Cd;

import gov.nih.nci.iso21090.St;
import gov.nih.nci.pa.iso.dto.DocumentDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.StudySiteAccrualStatusDTO;
import gov.nih.nci.pa.iso.dto.StudySiteDTO;
import gov.nih.nci.services.organization.OrganizationDTO;
import java.util.List;
import javax.ejb.Local;

/**
 * Prop trial Management Bean for registering and updating the protocol. 
 * @author Naveen Amiruddin
 * @since 05/24/2010
 *
 */
@Local
public interface ProprietaryTrialManagementServiceLocal {
    
    /**
     * update a proprietary trial.
     * @param studyProtocolDTO study protocol dto 
     * @param leadOrganizationDTO lead organization dto
     * @param summary4OrganizationDTO summary 4 organization dto
     * @param leadOrganizationIdentifier lead organization identifier
     * @param nctIdentifier nct Identifier
     * @param summary4TypeCode summary 4 type code
     * @param documentDTOs list of dtos
     * @param studySiteDTOs list of study site dtos
     * @param studySiteAccrualDTOs list of study site Accrual status
     * @throws PAException on error
     */
    @SuppressWarnings("PMD.ExcessiveParameterList")    
    void update(
            StudyProtocolDTO studyProtocolDTO,
            OrganizationDTO leadOrganizationDTO ,
            OrganizationDTO summary4OrganizationDTO ,
            St leadOrganizationIdentifier ,
            St nctIdentifier,
            Cd summary4TypeCode ,
            List<DocumentDTO> documentDTOs ,
            List<StudySiteDTO> studySiteDTOs ,
            List<StudySiteAccrualStatusDTO> studySiteAccrualDTOs)
    throws PAException;

}
