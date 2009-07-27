package gov.nih.nci.po.util;

import gov.nih.nci.po.data.bo.AbstractPersonRole;

import java.io.Serializable;

import org.hibernate.validator.Validator;

/**
 * Used to validate that the scoper is unique for the given player, ignoring NULLIFIED records.  If there is no player,
 * the scoper is considered valid.
 *
 * @author slustbader
 */
@SuppressWarnings({ "PMD.UnusedPrivateField", "PMD.SingularField" })
public class UniquePlayerScoperPlayerOptionalValidator implements Validator<UniquePlayerScoperPlayerOptional>,
        Serializable {

    private static final long serialVersionUID = 1L;
    private String friendlyName;

    /**
     * @return friendly name for type being validated
     */
    public String getFriendlyName() {
        return friendlyName;
    }

    /**
     * {@inheritDoc}
     */
    public void initialize(UniquePlayerScoperPlayerOptional parameters) {
        this.friendlyName = parameters.friendlyName();
    }

    /**
     * {@inheritDoc}
     */
    public boolean isValid(Object value) {
        if (!(value instanceof AbstractPersonRole)) {
            return false;
        }
        AbstractPersonRole apr = (AbstractPersonRole) value;
        if (apr.getPlayer() == null) {
            return true;
        }

        return UniquePlayerScoperValidator.validate(apr);
    }

}
