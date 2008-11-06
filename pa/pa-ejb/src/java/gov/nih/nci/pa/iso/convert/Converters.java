/**
 * 
 */
package gov.nih.nci.pa.iso.convert;

import gov.nih.nci.pa.service.PAException;

/**
 * Class contains exclusively a static method used to return converters for iso dto's.
 * @author Hugh Reinhart
 * @since 11/06/2008
 * copyright NCI 2007.  All rights reserved.
 * This code may not be used without the express written permission of the
 * copyright holder, NCI.
 */
public class Converters {
    private static ArmConverter arm = new ArmConverter();
    /**
     * @param clazz class
     * @return converter
     * @throws PAException exception
     */
    @SuppressWarnings("unchecked")
    public static AbstractConverter get(Class clazz)  throws PAException {
        if (clazz.equals(ArmConverter.class)) {
            return arm;
        }
        throw new PAException("Converter needs to be added to gov.nih.nci.pa.iso.convert.Converters.  ");
    }
}
