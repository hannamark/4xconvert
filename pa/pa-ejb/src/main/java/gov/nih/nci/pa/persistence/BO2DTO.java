package gov.nih.nci.pa.persistence;

import gov.nih.nci.pa.domain.Protocol;
import gov.nih.nci.pa.dto.ProtocolDTO;

/**
 * Class for converting domain objects to transfer objects.
 * @author Hugh Reinhart
 * @since 6/10/2008
 
 * copyright NCI 2007.  All rights reserved.
 * This code may not be used without the express written permission of the copyright holder, NCI.
 */
public final class BO2DTO {

    /**
     * @param bo domain object
     * @return transfer object
     */
    public static ProtocolDTO convert(Protocol bo) {
        if (bo == null) {
            return null;
        }
        return new ProtocolDTO(bo);
    }

}
