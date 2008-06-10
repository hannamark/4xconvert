package gov.nih.nci.pa.persistence;

import gov.nih.nci.pa.domain.Protocol;
import gov.nih.nci.pa.dto.ProtocolDTO;

/**
 * Class for converting transfer objects to domain objects.
 * @author Hugh Reinhart
 * @since 6/10/2008
 
 * copyright NCI 2007.  All rights reserved.
 * This code may not be used without the express written permission of the copyright holder, NCI.
 */
public final class DTO2BO {

    /**
     * @param dto transfer object
     * @return domain object
     */
    public static Protocol convert(ProtocolDTO dto) {
        if (dto == null) {
            return null;
        }
        return new Protocol(dto);
    }

}
