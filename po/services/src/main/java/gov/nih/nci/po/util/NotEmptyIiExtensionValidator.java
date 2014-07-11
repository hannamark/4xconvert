//$Id: $
package gov.nih.nci.po.util;

import gov.nih.nci.iso21090.Ii;
import org.hibernate.mapping.Column;
import org.hibernate.mapping.Property;

import java.util.Iterator;

/**
 * Checks that the Ii extension has been set.
 *
 */
public class NotEmptyIiExtensionValidator extends AbstractIiValidator<NotEmptyIiExtension> {
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
    @Override
    boolean validate(Ii ii) {
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
