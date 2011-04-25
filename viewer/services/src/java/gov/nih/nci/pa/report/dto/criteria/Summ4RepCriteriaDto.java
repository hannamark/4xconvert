package gov.nih.nci.pa.report.dto.criteria;

import gov.nih.nci.iso21090.St;
import gov.nih.nci.pa.service.PAException;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

/**
 * @author Max Shestopalov
 */
public class Summ4RepCriteriaDto extends AbstractStandardCriteriaDto {

    private final List<St> orgNames = new ArrayList<St>();

    /**
     * @return list of orgNames
     */
    public List<St> getOrgNames() {
        return orgNames;
    }

    /**
     * Validate that the criteria is good.
     * @param criteria criteria iso dto
     * @throws PAException exception
     */
    public static void validate(Object criteria) throws PAException {
        AbstractCriteriaDto.validate(criteria);
        if (CollectionUtils.isEmpty(((Summ4RepCriteriaDto) criteria).getOrgNames())) {
            throw new PAException("At least one Organization is required to run the Summary 4 Report.");
        }
    }
}
