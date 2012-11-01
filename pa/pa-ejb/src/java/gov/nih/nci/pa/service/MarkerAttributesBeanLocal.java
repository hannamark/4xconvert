package gov.nih.nci.pa.service;

import gov.nih.nci.pa.enums.BioMarkerAttributesCode;
import gov.nih.nci.pa.util.PaHibernateSessionInterceptor;
import gov.nih.nci.pa.util.PaHibernateUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

import org.hibernate.SQLQuery;
import org.hibernate.Session;

/**
 * 
 * @author Reshma Koganti
 * 
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
@Interceptors(PaHibernateSessionInterceptor.class)
@SuppressWarnings("PMD.ExcessiveParameterList")
public class MarkerAttributesBeanLocal implements MarkerAttributesServiceLocal {
    /**
     * Gets the map <Key,Value> pair of all Biomarkers Attributes.
     * 
     * @return the map with the type and corresponding attribute value
     * @throws PAException
     *             on error.
     */
    public Map<String, String> getAllMarkerAttributes() throws PAException {
        Map<String, String> returnValue = new HashMap<String, String>();
        returnValue.putAll(attributeValues(BioMarkerAttributesCode.ASSAY_TYPE));
        returnValue
                .putAll(attributeValues(BioMarkerAttributesCode.EVALUATION_TYPE));
        returnValue
                .putAll(attributeValues(BioMarkerAttributesCode.BIOMARKER_PURPOSE));
        returnValue
                .putAll(attributeValues(BioMarkerAttributesCode.BIOMARKER_USE));
        returnValue
                .putAll(attributeValues(BioMarkerAttributesCode.SPECIMEN_TYPE));
        returnValue
                .putAll(attributeValues(BioMarkerAttributesCode.SPECIMEN_COLLECTION));
        return returnValue;

    }

    private Map<String, String> attributeValues(
            BioMarkerAttributesCode valueType) {
        Map<String, String> returnValue = new HashMap<String, String>();
        Session session = PaHibernateUtil.getCurrentSession();
        List<Object[]> queryList = null;
        SQLQuery query = session
                .createSQLQuery("Select type_code, DESCRIPTION_TEXT from "
                        + valueType.getName());
        queryList = query.list();
        int i = 0;
        for (Object[] oArr : queryList) {
            if (oArr[0] != null) {
                returnValue.put(valueType.getName() + i, oArr[0].toString());
                i++;
            }
        }
        return returnValue;
    }

    /**
     * return the list of values for the BioMarker attributes.
     * 
     * @param valueType
     *            the valueType
     * @return the list with the attribute value
     * @throws PAException
     *             on error.
     */
    public static List<String> getTypeValues(BioMarkerAttributesCode valueType)
            throws PAException {
        Session session = PaHibernateUtil.getCurrentSession();
        List<Object[]> queryList = null;
        SQLQuery query = session
                .createSQLQuery("Select type_code, DESCRIPTION_TEXT from "
                        + valueType.getName());
        queryList = query.list();
        List<String> returnValue = new ArrayList<String>();
        for (Object[] oArr : queryList) {
            if (oArr[0] != null) {
                returnValue.add(oArr[0].toString());
            }
        }
        return returnValue;
    }

}
