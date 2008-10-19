package gov.nih.nci.pa.util;

import gov.nih.nci.coppa.iso.EnPn;
import gov.nih.nci.coppa.iso.EntityNamePartType;
import gov.nih.nci.coppa.iso.Enxp;

import org.apache.commons.lang.StringUtils;

/**
 * Adapted from PO modified for PA to call the PO API's.
 * @author Harsha
 *
 */
public class RemoteApiUtil {
    /**
     * @param firstName given name
     * @param middleName middle name
     * @param lastName family name
     * @param prefix prefix
     * @param suffix suffix
     * @return ISO EN Person Name
     */
    public static final EnPn convertToEnPn(String firstName, String middleName,
            String lastName, String prefix, String suffix) {
        EnPn enpn = new EnPn();
        addEnxp(enpn, lastName, EntityNamePartType.FAM);
        addEnxp(enpn, firstName, EntityNamePartType.GIV);
        addEnxp(enpn, middleName, EntityNamePartType.GIV);
        addEnxp(enpn, prefix, EntityNamePartType.PFX);
        addEnxp(enpn, suffix, EntityNamePartType.SFX);
        return enpn;
    }
    
    private static void addEnxp(EnPn enpn, String value, EntityNamePartType type) {
        if (StringUtils.isNotEmpty(value)) {
            Enxp part = new Enxp(type);
            part.setValue(value);
            enpn.getPart().add(part);
        }
    }

}
