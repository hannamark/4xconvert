/**
 * 
 */
package gov.nih.nci.pa.service;

import gov.nih.nci.coppa.iso.Ii;

import java.util.List;

/**
 * @author Hugh Reinhart
 * @since 09/30/2008
 * copyright NCI 2007.  All rights reserved.
 * This code may not be used without the express written permission of the
 * copyright holder, NCI.
 * @param <DTO> data transfer object
 */
public abstract class AbstractStudyPaService<DTO> extends AbstractBasePaService<DTO> {
    abstract List<DTO> getByStudyProtocol(Ii ii) throws PAException;
}
