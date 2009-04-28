package gov.nih.nci.pa.viewer.util;

import gov.nih.nci.pa.util.PAUtil;

import java.util.Date;
import java.util.Map;

import org.apache.struts2.util.StrutsTypeConverter;

/**
 * Class used to convert dates.
 *.
 * @author Hugh Reinhart
 * @since 4/16/2009
*/
@SuppressWarnings("unchecked")
public class DateConverter extends StrutsTypeConverter {
    /**
     * @param context context
     * @param values  values
     * @param toClass class to convert to
     * @return Object date object
     */
    @Override
    public final Object convertFromString(final Map context
                         , final String[] values, final Class toClass) {
        if (values != null && values.length > 0
                           && values[0] != null && values[0].length() > 0) {
            return PAUtil.dateStringToTimestamp(values[0]);
        }
        return null;
    }

    /**
     * @param context context
     * @param o object
     * @return String date string
     */
    @Override
    public final String convertToString(final Map context, final Object o) {
        if (o instanceof Date) {
            return PAUtil.normalizeDateString(((Date) o).toString());
        }
        return "";
    }
}

