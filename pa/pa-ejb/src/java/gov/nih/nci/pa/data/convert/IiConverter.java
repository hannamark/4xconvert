/**
 * 
 */
package gov.nih.nci.pa.data.convert;

/**
 * @author hreinhart
 *
 */

import gov.nih.nci.coppa.iso.Ii;

/**
 * Converter iso/java types.
 * Based on PO version, removed XSnapshot.
 * @author Hugh Reinhart
 * @since 05/22/2008
 * copyright NCI 2008.  All rights reserved.
 * This code may not be used without the express written permission of the copyright holder, NCI.
 */
public class IiConverter {

    /**
     * @param value a Long suitable for a hibernate entity Id
     * @return an Ii used to identify PA entities.
     */
    public static Ii convertToIi(Long value) {
        Ii result = new Ii();
        result.setRoot("0");
        result.setExtension(value.toString());
        return result;
    }

    /**
     * @param value an Ii used to identify PA entities.
     * @return a Long suitable for a hibernate entity Id
     */
    public static Long convertToLong(Ii value) {
        if (value == null || value.getNullFlavor() != null) {
            return null;
        }

        if (value.getFlavorId() != null) {
            throw new PaIsoConstraintException("PA expects a null flavorId");
        }

        String root = value.getRoot();
        if (root == null) {
            throw new IllegalArgumentException("root is required");
        }
        return Long.valueOf(value.getExtension());
    }
}
