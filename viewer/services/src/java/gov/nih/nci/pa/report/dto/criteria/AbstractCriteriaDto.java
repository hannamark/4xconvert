package gov.nih.nci.pa.report.dto.criteria;

import gov.nih.nci.pa.service.PAException;

/**
 * Base class for all criteria iso dto's.
 *
 * @author Hugh Reinhart
 * @since 06/09/2009
 */
public class AbstractCriteriaDto {

    /**
     * Validate that the criteria is good.
     * @param criteria criterie iso dto
     * @throws PAException exception
     */
    public static void validate(Object criteria) throws PAException {
        if (criteria == null) {
            throw new PAException("ERROR:  Criteria is null.");
        }
    }
}
