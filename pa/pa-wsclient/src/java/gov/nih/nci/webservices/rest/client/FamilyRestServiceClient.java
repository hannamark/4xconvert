package gov.nih.nci.webservices.rest.client;

import gov.nih.nci.po.ws.common.types.Family;

import java.util.List;

/**
 * Rest client interface for FamilyService
 * 
 * @author vinodh.rc
 * 
 */
public interface FamilyRestServiceClient extends RestClientInterface {

    /**
     * Performs search by example for Family
     * 
     * @param family
     *            Family instance filled with filter criteria
     * @return list of Family instances
     */
    List<Family> search(Family family);

    /**
     * get Family by id
     * 
     * @param id
     *            - db id
     * @return Family instance
     */
    Family getFamily(String id);

}