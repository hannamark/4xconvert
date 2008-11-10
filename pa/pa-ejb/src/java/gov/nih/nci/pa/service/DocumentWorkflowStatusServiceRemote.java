package gov.nih.nci.pa.service;

import gov.nih.nci.pa.iso.dto.DocumentWorkflowStatusDTO;

import javax.ejb.Remote;

/**
 * @author Kalpana Guthikonda
 * @since 11/07/2008
 * copyright NCI 2008.  All rights reserved.
 * This code may not be used without the express written permission of the
 * copyright holder, NCI.
 */
@Remote
public interface DocumentWorkflowStatusServiceRemote extends StudyPaService<DocumentWorkflowStatusDTO> {

}
