package gov.nih.nci.po.data.audit;

import gov.nih.nci.po.audit.AuditLogDetail;
import gov.nih.nci.po.audit.AuditLogInterceptor;
import gov.nih.nci.po.audit.AuditLogRecord;
import gov.nih.nci.po.audit.AuditType;
import gov.nih.nci.po.audit.Auditable;
import gov.nih.nci.po.data.bo.Address;
import gov.nih.nci.po.data.bo.ContactInfo;
import gov.nih.nci.po.data.bo.Email;
import gov.nih.nci.po.data.common.Country;
import gov.nih.nci.po.service.AbstractHibernateTestCase;
import gov.nih.nci.po.util.PoHibernateUtil;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import java.util.Map;
import org.apache.commons.lang.ObjectUtils;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.collection.PersistentList;
import org.junit.Test;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

/**
 *
 * @author gax
 */
public class AuditLogInterceptorTest extends AbstractHibernateTestCase {

    private static final Logger LOG = Logger.getLogger(AuditLogInterceptorTest.class);
    
    @Test
    public void collectionUpdate() {
        Country country = new Country("USA", "840", "US", "USA");
        PoHibernateUtil.getCurrentSession().save(country);
        Address address1 = new Address("a", "b", "c", "d", country);
        ContactInfo ci = new ContactInfo(address1);
        ci.getEmail().add(new Email("foo@zombo.com"));
        PoHibernateUtil.getCurrentSession().save(ci);
        PoHibernateUtil.getCurrentSession().flush();

        // make a chage to a collection
        ci.getEmail().add(new Email("zzz@example.com"));
//        ci.setTitle("foo");
        
        PoHibernateUtil.getCurrentSession().update(ci);
        
        PoHibernateUtil.getCurrentSession().flush();
        PoHibernateUtil.getCurrentSession().clear();
        
       
        List<AuditLogRecord> alr = find(ContactInfo.class, ci.getId());
        //System.out.println(""+alr);
//        for (AuditLogRecord a :alr) {
//            System.out.println(a.toString());
//        }
        assertDetail(alr, AuditType.UPDATE, "email", ci.getEmail().get(0).getId().toString(), 
            ci.getEmail().get(0).getId() + ", " + ci.getEmail().get(1).getId(), true);
  
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
        assertTrue(false);
    }
    
    @Test
    public void onCollectionUpdate() {
        AuditLogInterceptor interceptor = new AuditLogInterceptor();
        interceptor.onCollectionUpdate(null, null);
        Map m = callGetRecords(interceptor).get();
        assertTrue(m==null || m.isEmpty());
        
        PersistentList dummy = new PersistentList();
        dummy.setOwner(null);
        interceptor.onCollectionUpdate(dummy, null);
        m = callGetRecords(interceptor).get();
        assertTrue(m==null || m.isEmpty());
    }
    
    private static AuditLogRecord callGetOrCreateRecord(AuditLogInterceptor interceptor, Auditable entity, String entityName, Long id,  AuditType eventToLog) {
        try {
            Method m = AuditLogInterceptor.class.getDeclaredMethod("getOrCreateRecord", Auditable.class, String.class, Long.class, AuditType.class);
            m.setAccessible(true);
            return (AuditLogRecord) m.invoke(interceptor, entity, entityName, id, eventToLog);
        } catch (NoSuchMethodException ex) {
            throw new RuntimeException(ex);
        } catch (SecurityException ex) {
            throw new RuntimeException(ex);
        } catch (IllegalAccessException ex) {
            throw new RuntimeException(ex);
        } catch (IllegalArgumentException ex) {
            throw new RuntimeException(ex);
        } catch (InvocationTargetException ex) {
            Throwable t = ex.getTargetException();
            if(t instanceof RuntimeException)
                throw (RuntimeException) t;
            throw new RuntimeException(t);
        }
    }
    
    private static ThreadLocal<Map<Object, AuditLogRecord>> callGetRecords(AuditLogInterceptor interceptor) {
        try {
            Field f = AuditLogInterceptor.class.getDeclaredField("records");
            f.setAccessible(true);
            return (ThreadLocal<Map<Object, AuditLogRecord>>) f.get(interceptor);
        } catch (NoSuchFieldException ex) {
            throw new RuntimeException(ex);
        } catch (SecurityException ex) {
            throw new RuntimeException(ex);
        } catch (IllegalAccessException ex) {
            throw new RuntimeException(ex);
        } catch (IllegalArgumentException ex) {
            throw new RuntimeException(ex);
        }
    }
    
    @Test
    public void getOrCreateRecord() {
        AuditLogInterceptor interceptor = new AuditLogInterceptor();
        Auditable a = new ContactInfo();
        String entityName = "ContactInfo";
        Long id = null;
        AuditType eventToLog = AuditType.UPDATE;
        AuditLogRecord r = callGetOrCreateRecord(interceptor, a, entityName, id, eventToLog);
        AuditLogRecord r2 = callGetOrCreateRecord(interceptor, a, entityName, id, eventToLog);
        assertSame(r, r2);
    }

}