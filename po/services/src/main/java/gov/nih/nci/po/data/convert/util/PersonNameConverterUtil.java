package gov.nih.nci.po.data.convert.util;

import gov.nih.nci.coppa.iso.EnPn;
import gov.nih.nci.coppa.iso.EntityNamePartType;
import gov.nih.nci.coppa.iso.Enxp;

import org.apache.commons.lang.StringUtils;

/**
 * Utility class to generate ISO EN.PN type.
 * <strong>Should not depend on BO classes</strong> 
 */
public class PersonNameConverterUtil {
    
    private static void addEnxp(EnPn enpn, String value, EntityNamePartType type) {
        if (StringUtils.isNotEmpty(value)) {
            Enxp part = new Enxp(type);
            part.setValue(value);
            enpn.getPart().add(part);
        }
    }

    /**
     * @param firstName given name
     * @param lastName family name
     * @param prefix prefix
     * @param suffix suffix
     * @return ISO EN Person Name
     */
    public static final EnPn convertToEnPn(String firstName, String lastName, String prefix, String suffix) {
        EnPn enpn = new EnPn();
        addEnxp(enpn, lastName, EntityNamePartType.FAM);
        addEnxp(enpn, firstName, EntityNamePartType.GIV);
        addEnxp(enpn, prefix, EntityNamePartType.PFX);
        addEnxp(enpn, suffix, EntityNamePartType.SFX);
        return enpn;
    }

}