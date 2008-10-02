package gov.nih.nci.po.data.bo;



/**
 *
 * @param <C> the afected correlation type.
 * @author gax
 */
public interface CorrelationChangeRequest <C extends Correlation> extends ChangeRequest<C>, Correlation {

    /**
     * the id setter.
     * @param id the id
     */
    void setId(Long id);
}