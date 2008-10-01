/**
 * 
 */
package gov.nih.nci.pa.service;

import gov.nih.nci.coppa.iso.Ii;

import java.util.List;

/**
 * @author Hugh Reinhart
 * @since 10/01/2008
 * copyright NCI 2007.  All rights reserved.
 * This code may not be used without the express written permission of the
 * copyright holder, NCI.
 * @param <DTO> dto
 */
public interface StudyPaService<DTO> extends BasePaService<DTO> {
    /**
     * @param ii index of object
     * @return null
     * @throws PAException exception
     */
    List<DTO> getByStudyProtocol(Ii ii) throws PAException;
}
