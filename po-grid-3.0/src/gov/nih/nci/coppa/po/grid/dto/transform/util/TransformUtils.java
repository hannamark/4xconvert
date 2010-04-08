package gov.nih.nci.coppa.po.grid.dto.transform.util;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;

import gov.nih.nci.iso21090.extensions.Id;


/**
 * Utility class for converting old types to new types.
 * @author aevansel
 */
public class TransformUtils {
    private static final Logger LOGGER = Logger.getLogger(TransformUtils.class);
    
    /**
     * Converts the given 1.2.6 Id to the 2.0 version.
     * @param oldId The Id to convert
     * @return The id from 2.0
     */
    public static Id convertToNewId(gov.nih.nci.coppa.po.Id oldId) {
        Id id = new Id();
        try {
            BeanUtils.copyProperties(id, oldId);
        } catch (Exception e) {
            LOGGER.error("Error converting gov.nih.nci.coppa.po.Id to gov.nih.nci.iso21090.extensions.Id");
        }
        return id;
    }
    
    /**
     * Converts the given 2.0 Id to the 1.2.6 version.
     * @param id The id to convert
     * @return The 1.2.6 version
     */
    public static gov.nih.nci.coppa.po.Id convertToOldId(Id id) {
        gov.nih.nci.coppa.po.Id oldId = new gov.nih.nci.coppa.po.Id();
        try {
            BeanUtils.copyProperties(oldId, id);
        } catch (Exception e) {
            LOGGER.error("Error converting gov.nih.nci.iso21090.extensions.Id to gov.nih.nci.coppa.po.Id");
        }
        return oldId;
    }
    
    /**
     * Converts the array of 1.2.6 ids to the 2.0 versions.
     * @param oldIds The old ids to convert
     * @return The 2.0 version of the array
     */
    public static Id[] convertToNewIds(gov.nih.nci.coppa.po.Id[] oldIds) {
        Id[] ids = new Id[oldIds.length];
        for (int i = 0; i < oldIds.length; i++) {
           gov.nih.nci.coppa.po.Id oldId = oldIds[i];
           Id newId = new Id();
           try {
               BeanUtils.copyProperties(newId, oldId);
           } catch (Exception e) {
               LOGGER.error("Error converting gov.nih.nci.coppa.po.Id  to gov.nih.nci.iso21090.extension.Id");
           }
           ids[i] = newId;
        }
        return ids;
    }
    
}
