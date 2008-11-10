package gov.nih.nci.pa.service;

import gov.nih.nci.pa.domain.DocumentWorkflowStatus;
import gov.nih.nci.pa.iso.convert.DocumentWorkflowStatusConverter;
import gov.nih.nci.pa.iso.dto.DocumentWorkflowStatusDTO;

import javax.ejb.Stateless;

/**
 * @author Kalpana Guthikonda
 * @since 11/07/2008
 * copyright NCI 2008.  All rights reserved.
 * This code may not be used without the express written permission of the
 * copyright holder, NCI.
 */
@Stateless
public class DocumentWorkflowStatusServiceBean extends 
AbstractStudyIsoService<DocumentWorkflowStatusDTO, DocumentWorkflowStatus, DocumentWorkflowStatusConverter> 
implements DocumentWorkflowStatusServiceRemote {

}
