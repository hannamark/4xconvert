package gov.nih.nci.po.service;


import gov.nih.nci.po.audit.AuditLogRecord;

import javax.ejb.Local;

/**
 * Local interface for searching audit log records.
 */
@Local
public interface AuditableServiceLocal extends GenericSearchService<AuditLogRecord, SearchCriteria<AuditLogRecord>> {
    // defines no extra methods
}