package gov.nih.nci.pa.service;

import gov.nih.nci.pa.domain.StratumGroup;
import gov.nih.nci.pa.iso.convert.StratumGroupConverter;
import gov.nih.nci.pa.iso.dto.StratumGroupDTO;

import javax.ejb.Stateless;

/**
 * @author Kalpana Guthikonda
 * @since 10/13/2008
 * copyright NCI 2008.  All rights reserved.
 * This code may not be used without the express written permission of the
 * copyright holder, NCI.
 */
@Stateless
@SuppressWarnings({"PMD.CyclomaticComplexity" })
public class SubGroupsServiceBean extends AbstractStudyIsoService<StratumGroupDTO, StratumGroup, StratumGroupConverter>
implements SubGroupsServiceRemote {
    
}
