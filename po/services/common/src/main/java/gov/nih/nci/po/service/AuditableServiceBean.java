package gov.nih.nci.po.service;


import gov.nih.nci.po.audit.AuditLogRecord;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

/**
 * Implementation of the audit service.
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class AuditableServiceBean extends BaseServiceBean<AuditLogRecord> implements AuditableServiceLocal {

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<AuditLogRecord> search(SearchCriteria<AuditLogRecord> criteria) {
        return super.genericSearch(criteria, null);
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<AuditLogRecord> search(SearchCriteria<AuditLogRecord> criteria,
            PageSortParams<AuditLogRecord> pageSortParams) {
        return super.genericSearch(criteria, pageSortParams);
    }

    /**
     * {@inheritDoc}
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public int count(SearchCriteria<AuditLogRecord> criteria) {
        return super.genericCount(criteria);
    }
}