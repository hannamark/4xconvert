package gov.nih.nci.po.service;

import gov.nih.nci.coppa.iso.Cd;
import gov.nih.nci.po.data.bo.CodeValue;

import javax.ejb.Local;

/**
 * Generic ISO Cd Lookup service.
 * @author smatyas
 */
@Local
public interface GenericCodeValueServiceLocal {

    /**
     * @param <T> the type
     * @param clz the type to lookup
     * @param code to Cd to lookup
     * @return the CodeValue instance found otherwise, null
     */
    <T extends CodeValue> T getByCode(Class<T> clz, Cd code);
    
    /**
     * @param <T> the type
     * @param clz the type to lookup
     * @param code to Cd to lookup
     * @return the CodeValue instance found otherwise, null
     */
    <T extends CodeValue> T getByCode(Class<T> clz, String code);
}
