package gov.nih.nci.pa.iso.util;

import gov.nih.nci.iso21090.NullFlavor;
import gov.nih.nci.iso21090.Real;

/**
 * @author Hugh Reinhart
 * @since Jun 28, 2013
 */
public class RealConverter {

    /**
     * Convert a double to an Iso Real.
     * @param dbl the double
     * @return Iso object
     */
    public static Real convertToReal(Double dbl) {
        Real real = new Real();
        if (dbl == null) {
            real.setNullFlavor(NullFlavor.NI);
        } else {
            real.setValue(dbl);
        }
        return real;
    }

    /**
     * Convert an Iso Real to a double.
     * @param real the iso object
     * @return the double
     */
    public static Double convertToDouble(Real real) {
        if (real == null || real.getNullFlavor() != null) {
            return null;
        }
        return real.getValue();
    }
}
