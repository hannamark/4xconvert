/**
 * 
 */
package gov.nih.nci.pa.service;

import gov.nih.nci.coppa.iso.Ii;

/**
 * @author Hugh Reinhart
 * @since 10/01/2008
 * copyright NCI 2007.  All rights reserved.
 * This code may not be used without the express written permission of the
 * copyright holder, NCI.
 * @param <DTO> dto
 */
public interface BasePaService<DTO> {
    /**
     * @param ii index of object
     * @return object
     * @throws PAException exception
     */
    DTO get(Ii ii) throws PAException;
    /**
     * @param dto dto
     * @return created object
     * @throws PAException exception
     */
    DTO create(DTO dto) throws PAException;
    /**
     * @param dto dto
     * @return updated object
     * @throws PAException exception
     */
    DTO update(DTO dto) throws PAException;
}
