package gov.nih.nci.po.webservices.service.bridg;

import gov.nih.nci.coppa.common.LimitOffset;
import gov.nih.nci.coppa.po.Correlation;
import gov.nih.nci.coppa.po.StringMap;
import gov.nih.nci.coppa.services.TooManyResultsException;
import gov.nih.nci.iso21090.extensions.Cd;
import gov.nih.nci.iso21090.extensions.Id;
import gov.nih.nci.po.service.EntityValidationException;
import gov.nih.nci.services.correlation.NullifiedRoleException;

import java.util.List;

/**
 * @author Jason Aliyetti <jason.aliyetti@semanticbits.com>
 *     @param <ROLE_TYPE> The role type that this service is concerned with.
 */
public interface RoleService <ROLE_TYPE extends Correlation> {
    /**
     *
     * @param definition The instance to create.
     * @return the id of the newly created instance
     * @throws EntityValidationException If the specified instance is invalid.
     */
    Id create(ROLE_TYPE definition) throws EntityValidationException;

    /**
     *
     * @param spec The instance to match.
     * @param limitOffset The page size and offset
     * @return A list of matches
     * @throws TooManyResultsException If too many results are found (as per the EJB).
     */
    List<ROLE_TYPE> query(ROLE_TYPE spec, LimitOffset limitOffset)
            throws TooManyResultsException;

    /**
     *
     * @param definition The updated instance
     * @throws EntityValidationException If the update fails validation.
     */
    void update(ROLE_TYPE definition) throws EntityValidationException;

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
    StringMap validate(ROLE_TYPE definition);

    /**
     *
     * @param ids The ids to match.
     * @return A list of matches
     */
    List<ROLE_TYPE> getByPlayerIds(List<Id> ids);

    /**
     *
     * @param id The id of the instance to retrieve.
     * @return the instance with the given id
     * @throws NullifiedRoleException tbd
     */
    ROLE_TYPE getById(Id id) throws NullifiedRoleException;

    /**
     *
     * @param ids The ids to match.
     * @return A list of instances for the given ids
     * @throws NullifiedRoleException tbd
     */
    List<ROLE_TYPE> getByIds(List<Id> ids) throws NullifiedRoleException;
}
