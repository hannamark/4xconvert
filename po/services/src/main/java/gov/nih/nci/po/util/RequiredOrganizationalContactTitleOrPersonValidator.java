package gov.nih.nci.po.util;

import gov.nih.nci.po.data.bo.AbstractOrganizationalContact;
import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.Validator;

import java.io.Serializable;

/**
 * Used to validate that at least one of player or title is set.
 *
 * @author slustbader
 */
public class RequiredOrganizationalContactTitleOrPersonValidator implements
        Validator<RequiredOrganizationalContactTitleOrPerson>, Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * {@inheritDoc}
     */
    public void initialize(RequiredOrganizationalContactTitleOrPerson parameters) {
        // nothing to do here
    }

    /**
     * {@inheritDoc}
     */
    public boolean isValid(Object value) {
        if (!(value instanceof AbstractOrganizationalContact)) {
            return false;
        }
        AbstractOrganizationalContact aoc = (AbstractOrganizationalContact) value;
        return StringUtils.isNotBlank(aoc.getTitle()) || (aoc.getPlayer() != null && aoc.getPlayer().getId() != null);
    }
}
