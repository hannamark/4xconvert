/**
 * 
 */
package gov.nih.nci.pa.iso.convert;

import gov.nih.nci.pa.service.PAException;

/**
 * @author Hugh Reinhart
 * @since 11/05/2008
 
 * copyright NCI 2008.  All rights reserved.
 * This code may not be used without the express written permission of the copyright holder, NCI.
 * @param <BO> domain object
 * @param <DTO> dto
 */
public abstract class AbstractConverter<DTO, BO> {
    /**
     * @param dto dto
     * @return domain object
     * @throws PAException exception
     */
    public abstract BO convertFromDtoToDomain(DTO dto) throws PAException;
    /**
     * @param bo domain object
     * @return dto
     * @throws PAException exception
     */
    public abstract DTO convertFromDomainToDto(BO bo) throws PAException;
}
