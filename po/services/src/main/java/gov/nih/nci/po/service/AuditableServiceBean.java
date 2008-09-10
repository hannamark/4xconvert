package gov.nih.nci.po.service;


import gov.nih.nci.po.audit.AuditLogRecord;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

/**
 * Implementation of the audit service.
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class AuditableServiceBean extends AbstractBaseServiceBean<AuditLogRecord> implements AuditableServiceLocal {
}