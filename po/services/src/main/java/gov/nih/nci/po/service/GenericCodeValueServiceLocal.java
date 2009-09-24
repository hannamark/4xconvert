package gov.nih.nci.po.service;

import gov.nih.nci.coppa.iso.Cd;
import gov.nih.nci.po.data.bo.CodeValue;

import java.util.List;

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
    
    /**
     * @param <T> the type
     * @param clz the type to lookup
     * @return the list CodeValue instances found otherwise, null
     */
    <T extends CodeValue> List<T> list(Class<T> clz);

    /**
     * @param <T> the type
     * @param clz the type to lookup
     * @param orderBy the field to orderBy, or null to leave default ordering.
     * @return the list CodeValue instances found otherwise, null
     */
    <T extends CodeValue> List<T> list(Class<T> clz, String orderBy);

}
