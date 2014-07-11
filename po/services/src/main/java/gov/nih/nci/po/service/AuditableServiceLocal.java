package gov.nih.nci.po.service;


import com.fiveamsolutions.nci.commons.audit.AuditLogRecord;
import com.fiveamsolutions.nci.commons.search.SearchCriteria;

import javax.ejb.Local;

/**
 * Local interface for searching audit log records.
 */
@Local
public interface AuditableServiceLocal extends GenericSearchService<AuditLogRecord, SearchCriteria<AuditLogRecord>> {
    // defines no extra methods
}