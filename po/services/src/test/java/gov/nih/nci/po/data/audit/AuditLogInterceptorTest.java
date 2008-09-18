package gov.nih.nci.po.data.audit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import gov.nih.nci.po.audit.AuditLogDetail;
import gov.nih.nci.po.audit.AuditLogInterceptor;
import gov.nih.nci.po.audit.AuditLogRecord;
import gov.nih.nci.po.audit.AuditType;
import gov.nih.nci.po.audit.Auditable;
import gov.nih.nci.po.data.bo.Address;
import gov.nih.nci.po.data.bo.Country;
import gov.nih.nci.po.data.bo.Email;
import gov.nih.nci.po.data.bo.EntityStatus;
import gov.nih.nci.po.data.bo.Organization;
import gov.nih.nci.po.service.AbstractHibernateTestCase;
import gov.nih.nci.po.util.PoHibernateUtil;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.ObjectUtils;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.collection.PersistentList;
import org.junit.Test;

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
        Organization org = new Organization();
        org.setStatusCode(EntityStatus.NEW);
        org.setPostalAddress(address1);
        org.setName("tstName");
        org.getEmail().add(new Email("foo@zombo.com"));
        PoHibernateUtil.getCurrentSession().save(org);
        PoHibernateUtil.getCurrentSession().flush();

        // make a chage to a collection
        org.getEmail().add(new Email("zzz@example.com"));

        PoHibernateUtil.getCurrentSession().update(org);

        PoHibernateUtil.getCurrentSession().flush();
        PoHibernateUtil.getCurrentSession().clear();


        List<AuditLogRecord> alr = find(Organization.class, org.getId());
        assertDetail(alr, AuditType.UPDATE, "email", org.getEmail().get(0).getId().toString(),
                org.getEmail().get(0).getId() + ", " + org.getEmail().get(1).getId(), true);

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
        Map<?, ?> m = callGetRecords(interceptor).get();
        assertTrue(m==null || m.isEmpty());

        PersistentList dummy = new PersistentList();
        dummy.setOwner(null);
        interceptor.onCollectionUpdate(dummy, null);
        m = callGetRecords(interceptor).get();
        assertTrue(m==null || m.isEmpty());
    }

    @SuppressWarnings("unchecked")
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

    private static String callGetValueString(Collection<? extends Auditable> value) {
        try {
            Method m = AuditLogInterceptor.class.getDeclaredMethod("getValueString", Collection.class);
            m.setAccessible(true);
            return (String) m.invoke(null, value);
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
            if(t instanceof RuntimeException) {
                throw (RuntimeException) t;
            }
            throw new RuntimeException(t);
        }
    }
    private static String callGetValueString(Map<? extends Auditable, ? extends Auditable> value) {
        try {
            Method m = AuditLogInterceptor.class.getDeclaredMethod("getValueString", Map.class);
            m.setAccessible(true);
            return (String) m.invoke(null, value);
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
            if(t instanceof RuntimeException) {
                throw (RuntimeException) t;
            }
            throw new RuntimeException(t);
        }
    }
    private static String callGetValueStringHelper(Object value) {
        try {
            Method m = AuditLogInterceptor.class.getDeclaredMethod("getValueStringHelper", Object.class);
            m.setAccessible(true);
            return (String) m.invoke(null, value);
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
            if(t instanceof RuntimeException) {
                throw (RuntimeException) t;
            }
            throw new RuntimeException(t);
        }
    }
    @Test
    public void getValueStringFromCollectionWithNull() {
        assertNull(callGetValueString((Collection<Auditable>) null));
    }
    @Test
    public void getValueStringFromCollection() {
        class Foo implements Auditable {
            private static final long serialVersionUID = 1L;
            public Long getId() {
                return Long.MAX_VALUE;
            }
        }
        final Foo dummy = new Foo();
        ArrayList<Foo> list = new ArrayList<Foo>() {
            private static final long serialVersionUID = 1L;
            @Override
            public Iterator<Foo> iterator() {
                return new Iterator<Foo>() {
                    int iteration = 0;
                    public boolean hasNext() { return true; }
                    public Foo next() { iteration++; return dummy; }
                    public void remove() { };
                };
            }

        };

        String s = callGetValueString(list);
        assertTrue(s.length() == 4000);
        assertTrue(s.endsWith("..."));
    }
    
    @Test
    public void getValueStringHelper() {
        Object value = new Object();
        String s = callGetValueStringHelper(value);
        assertEquals(value.toString(), s);
    }
    
    @Test
    public void getValueStringFromMapWithNull() {
        assertNull(callGetValueString((Map<Auditable, Auditable>) null));
    }
    
    @Test
    public void getValueStringFromMap() {
        class Key implements Auditable {
            private static final long serialVersionUID = 1L;
            public Long getId() {
                return Long.MAX_VALUE;
            }
        }
        
        class Value implements Auditable {
            private static final long serialVersionUID = 1L;
            public Long getId() {
                return Long.MIN_VALUE;
            }
        }
        final Key dummyKey = new Key();
        final Value dummyValue = new Value();
        class KeySet implements Set {

            public boolean add(Object o) {
                // TODO Auto-generated method stub
                return false;
            }

            public boolean addAll(Collection c) {
                // TODO Auto-generated method stub
                return false;
            }

            public void clear() {
                // TODO Auto-generated method stub
                
            }

            public boolean contains(Object o) {
                // TODO Auto-generated method stub
                return false;
            }

            public boolean containsAll(Collection c) {
                // TODO Auto-generated method stub
                return false;
            }

            public boolean isEmpty() {
                // TODO Auto-generated method stub
                return false;
            }

            public Iterator<Key> iterator() {
                return new Iterator<Key>() {
                    int iteration = 0;
                    public boolean hasNext() { return true; }
                    public Key next() { iteration++; return dummyKey; }
                    public void remove() { };
                };
            }

            public boolean remove(Object o) {
                // TODO Auto-generated method stub
                return false;
            }

            public boolean removeAll(Collection c) {
                // TODO Auto-generated method stub
                return false;
            }

            public boolean retainAll(Collection c) {
                // TODO Auto-generated method stub
                return false;
            }

            public int size() {
                // TODO Auto-generated method stub
                return 0;
            }

            public Object[] toArray() {
                // TODO Auto-generated method stub
                return null;
            }

            public Object[] toArray(Object[] a) {
                // TODO Auto-generated method stub
                return null;
            }
            
        }
        final KeySet dummyKeySet = new KeySet();
        Map<Key,Value> map = new HashMap<Key,Value>() {
            private static final long serialVersionUID = 1L;
            @Override
            public Set<Key> keySet() {
                return dummyKeySet;
            }
            @Override
            public Value get(Object key) {
                return dummyValue;
            }
        };
        
        String s = callGetValueString(map);
        assertTrue(s.startsWith("(k1=9223372036854775807, v1=-9223372036854775808), (k2=9223372036854775807, v2=-9223372036854775808),"));
        assertTrue(s.length() == 4000);
        assertTrue(s.endsWith("..."));
    }
}