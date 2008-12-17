//$Id: $
package gov.nih.nci.po.util;

import gov.nih.nci.coppa.iso.Ii;

import java.io.Serializable;
import java.util.Iterator;

import org.hibernate.mapping.Column;
import org.hibernate.mapping.Property;
import org.hibernate.validator.PropertyConstraint;
import org.hibernate.validator.Validator;

/**
 * Check the non emptyness of the element.
 * 
 */
public class NotEmptyIiExtensionValidator implements Validator<NotEmptyIiExtension>, PropertyConstraint, Serializable {
    private static final long serialVersionUID = 1L;
    private String columnName;

    /**
     * {@inheritDoc}
     */
    public void initialize(NotEmptyIiExtension parameters) {
        columnName = parameters.columnName();
    }

    /**
     * {@inheritDoc}
     */
    public boolean isValid(Object value) {
        if (value == null) {
            return false;
        }
        if (!(value instanceof Ii)) {
            return false;
        }

        Ii ii = (Ii) value;

        return new NotEmptyValidator().isValid(ii.getExtension());
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public void apply(Property property) {
        Iterator<Column> iter = property.getColumnIterator();
        while (iter.hasNext()) {
            Column next = iter.next();
            if (columnName.equals(next.getName())) {
                next.setNullable(false);
                break;
            }
        }
    }

}
