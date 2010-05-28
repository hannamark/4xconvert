/**
 * 
 */
package gov.nih.nci.registry.service;

import gov.nih.nci.iso21090.Cd;
import gov.nih.nci.iso21090.St;
import gov.nih.nci.pa.iso.dto.DocumentDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.StudySiteAccrualStatusDTO;
import gov.nih.nci.pa.iso.dto.StudySiteDTO;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.ProprietaryTrialManagementServiceLocal;
import gov.nih.nci.services.organization.OrganizationDTO;

import java.util.List;

/**
 * @author Kalpana Guthikonda
 *
 */
public class MockProprietaryTrialManagementService implements ProprietaryTrialManagementServiceLocal{

    public void update(StudyProtocolDTO studyProtocolDTO,
            OrganizationDTO leadOrganizationDTO,
            OrganizationDTO summary4OrganizationDTO,
            St leadOrganizationIdentifier, St nctIdentifierDTO,
            Cd summary4TypeCode, List<DocumentDTO> documentDTOs,
            List<StudySiteDTO> studySiteDTOs,
            List<StudySiteAccrualStatusDTO> studySiteAccrualDTOs)
            throws PAException {
        // TODO Auto-generated method stub
        
    }
    

}
