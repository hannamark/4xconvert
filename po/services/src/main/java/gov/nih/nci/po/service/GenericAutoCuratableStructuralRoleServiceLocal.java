/**
 * 
 */
package gov.nih.nci.po.service;

import gov.nih.nci.po.data.bo.Correlation;

import javax.jms.JMSException;

/**
 * @author Denis G. Krylov
 * @param <T>
 *            structural role
 * 
 */
public interface GenericAutoCuratableStructuralRoleServiceLocal<T extends Correlation>
        extends GenericStructrualRoleServiceLocal<T> {

    /**
     * Attempts to create a new correlation in ACTIVE state. If failed, falls
     * back to create the correlation in PENDING state. PO-5962.
     * 
     * @param structuralRole
     *            new role
     * @return id
     * @throws EntityValidationException
     *             if validation fails
     * @throws JMSException
     *             if JMS fails
     */
    long createActiveWithFallback(T structuralRole)
            throws EntityValidationException, JMSException;

}
