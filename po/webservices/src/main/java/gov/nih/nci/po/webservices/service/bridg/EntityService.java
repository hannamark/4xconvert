package gov.nih.nci.po.webservices.service.bridg;

import gov.nih.nci.coppa.common.LimitOffset;
import gov.nih.nci.coppa.po.Entity;
import gov.nih.nci.coppa.po.StringMap;
import gov.nih.nci.coppa.services.TooManyResultsException;
import gov.nih.nci.iso21090.extensions.Cd;
import gov.nih.nci.iso21090.extensions.Id;
import gov.nih.nci.po.service.EntityValidationException;
import gov.nih.nci.services.entity.NullifiedEntityException;

import java.util.List;

/**
 *     @author Jason Aliyetti <jason.aliyetti@semanticbits.com>
 *     @param <ENTITY_TYPE> The entity type that this service is concerned with.
 */
public interface EntityService<ENTITY_TYPE extends Entity> {
    /**
     *
     * @param definition The instance to create.
     * @return the id of the newly created instance
     * @throws gov.nih.nci.po.service.EntityValidationException If the specified instance is invalid.
     */
    Id create(ENTITY_TYPE definition) throws EntityValidationException;

    /**
     *
     * @param spec The instance to match.
     * @param limitOffset The page size and offset
     * @return A list of matches
     * @throws gov.nih.nci.coppa.services.TooManyResultsException If too many results are found (as per the EJB).
     */
    List<ENTITY_TYPE> query(ENTITY_TYPE spec, LimitOffset limitOffset)
            throws TooManyResultsException;

    /**
     *
     * @param definition The updated instance
     * @throws EntityValidationException If the update fails validation.
     */
    void update(ENTITY_TYPE definition) throws EntityValidationException;

    /**
     *
     * @param targetId The id of the instance to update.
     * @param statusCode The new status code.
     * @throws EntityValidationException If the update fails validation.
     */
    void updateStatus(Id targetId, Cd statusCode) throws EntityValidationException;

    /**
     *
     * @param definition The instance to validate.
     * @return A string map of validation errors
     */
    StringMap validate(ENTITY_TYPE definition);


    /**
     *
     * @param id The id of the instance to retrieve.
     * @return the instance with the given id
     * @throws NullifiedEntityException tbd
     */
    ENTITY_TYPE getById(Id id) throws NullifiedEntityException;

}
