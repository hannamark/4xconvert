package gov.nih.nci.po.data.bo;



/**
 *
 * @param <C> the afected correlation type.
 * @author gax
 */
public interface CorrelationChangeRequest <C extends Correlation> extends ChangeRequest<C> {

    /**
     * the id setter.
     * @param id the id
     */
    void setId(Long id);

    /**
     * @return the curration status
     */
    RoleStatus getStatus();

    /**
     * @param status the Correlation's status code.
     */
    void setStatus(RoleStatus status);
}