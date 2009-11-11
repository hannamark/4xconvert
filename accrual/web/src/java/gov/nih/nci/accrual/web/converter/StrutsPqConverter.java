package gov.nih.nci.accrual.web.converter;

import gov.nih.nci.coppa.iso.Pq;
import gov.nih.nci.pa.iso.util.PqConverter;

import java.math.BigDecimal;
import java.util.Map;

import org.apache.struts2.util.StrutsTypeConverter;

import com.opensymphony.xwork2.conversion.TypeConversionException;

public class StrutsPqConverter extends StrutsTypeConverter {

	/**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    @Override
    public Object convertFromString(Map map, String[] strings, Class aClass) {
        if (strings.length != 1) {
            throw new TypeConversionException(
                    "Error in custom struts2 converter StrutsPqConverter.convertFromString().  "
                    + "Expecting 1 string; " + strings.length + "were passed in.");
        }
        return PqConverter.convertToPq(BigDecimal.ZERO, strings[0].trim());
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    @Override
    public String convertToString(Map arg0, Object arg1) {
        return PqConverter.convertToPqToString((Pq) arg1);
    }

}
