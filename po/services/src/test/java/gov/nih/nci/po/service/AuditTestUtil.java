package gov.nih.nci.po.service;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import gov.nih.nci.po.util.PoHibernateUtil;

import java.util.List;

import org.apache.commons.lang.ObjectUtils;
import org.apache.log4j.Logger;
import org.hibernate.Query;

import com.fiveamsolutions.nci.commons.audit.AuditLogDetail;
import com.fiveamsolutions.nci.commons.audit.AuditLogRecord;
import com.fiveamsolutions.nci.commons.audit.AuditType;

public class AuditTestUtil {
    private static final Logger LOG = Logger.getLogger(AuditTestUtil.class);
    private AuditTestUtil() {
        //noop
    }
    
    @SuppressWarnings("unchecked")
    public static List<AuditLogRecord> find(Class<?> type, Long entityId) {
        String str = "FROM " + AuditLogRecord.class.getName() + " alr "
                     + "WHERE alr.entityName = :entityName "
                     + "  AND alr.entityId = :entityId";
        Query q = PoHibernateUtil.getCurrentSession().createQuery(str);
        q.setLong("entityId", entityId);
        q.setString("entityName", type.getSimpleName());
        List<AuditLogRecord> result = q.list();

        assertTrue(!result.isEmpty());

        return result;
    }

    public static void assertDetail(List<AuditLogRecord> alr, AuditType auditType,
            String attribute, String oldVal, String newVal, boolean foreignKey) {
        LOG.debug(String.format("record scan: %s, %s, %s", attribute, oldVal, newVal));
        for (AuditLogRecord r : alr) {
            LOG.debug("examining record: " + r);
            if (auditType == null || r.getType().equals(auditType)) {
                LOG.debug("correct audit type found");
                for (AuditLogDetail ald : r.getDetails()) {
                    LOG.debug(ald.getAttribute() + " " + ald.getOldValue() + " " + ald.getNewValue());
                    if (ald.getAttribute().equals(attribute)
                            && ObjectUtils.equals(ald.getOldValue(), oldVal)
                            && ObjectUtils.equals(ald.getNewValue(), newVal)) {
                        LOG.debug("Correct details found");
                        return;
                    }
                }
            }
        }
        fail();
    }
}
