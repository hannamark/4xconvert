/**
 * The software subject to this notice and license includes both human readable
 * source code form and machine readable, binary, object code form. The caarray-app
 * Software was developed in conjunction with the National Cancer Institute
 * (NCI) by NCI employees and 5AM Solutions, Inc. (5AM). To the extent
 * government employees are authors, any rights in such works shall be subject
 * to Title 17 of the United States Code, section 105.
 *
 * This caarray-app Software License (the License) is between NCI and You. You (or
 * Your) shall mean a person or an entity, and all other entities that control,
 * are controlled by, or are under common control with the entity. Control for
 * purposes of this definition means (i) the direct or indirect power to cause
 * the direction or management of such entity, whether by contract or otherwise,
 * or (ii) ownership of fifty percent (50%) or more of the outstanding shares,
 * or (iii) beneficial ownership of such entity.
 *
 * This License is granted provided that You agree to the conditions described
 * below. NCI grants You a non-exclusive, worldwide, perpetual, fully-paid-up,
 * no-charge, irrevocable, transferable and royalty-free right and license in
 * its rights in the caarray-app Software to (i) use, install, access, operate,
 * execute, copy, modify, translate, market, publicly display, publicly perform,
 * and prepare derivative works of the caarray-app Software; (ii) distribute and
 * have distributed to and by third parties the caarray-app Software and any
 * modifications and derivative works thereof; and (iii) sublicense the
 * foregoing rights set out in (i) and (ii) to third parties, including the
 * right to license such rights to further third parties. For sake of clarity,
 * and not by way of limitation, NCI shall have no right of accounting or right
 * of payment from You or Your sub-licensees for the rights granted under this
 * License. This License is granted at no charge to You.
 *
 * Your redistributions of the source code for the Software must retain the
 * above copyright notice, this list of conditions and the disclaimer and
 * limitation of liability of Article 6, below. Your redistributions in object
 * code form must reproduce the above copyright notice, this list of conditions
 * and the disclaimer of Article 6 in the documentation and/or other materials
 * provided with the distribution, if any.
 *
 * Your end-user documentation included with the redistribution, if any, must
 * include the following acknowledgment: This product includes software
 * developed by 5AM and the National Cancer Institute. If You do not include
 * such end-user documentation, You shall include this acknowledgment in the
 * Software itself, wherever such third-party acknowledgments normally appear.
 *
 * You may not use the names "The National Cancer Institute", "NCI", or "5AM"
 * to endorse or promote products derived from this Software. This License does
 * not authorize You to use any trademarks, service marks, trade names, logos or
 * product names of either NCI or 5AM, except as required to comply with the
 * terms of this License.
 *
 * For sake of clarity, and not by way of limitation, You may incorporate this
 * Software into Your proprietary programs and into any third party proprietary
 * programs. However, if You incorporate the Software into third party
 * proprietary programs, You agree that You are solely responsible for obtaining
 * any permission from such third parties required to incorporate the Software
 * into such third party proprietary programs and for informing Your
 * sub-licensees, including without limitation Your end-users, of their
 * obligation to secure any required permissions from such third parties before
 * incorporating the Software into such third party proprietary software
 * programs. In the event that You fail to obtain such permissions, You agree
 * to indemnify NCI for any claims against NCI by such third parties, except to
 * the extent prohibited by law, resulting from Your failure to obtain such
 * permissions.
 *
 * For sake of clarity, and not by way of limitation, You may add Your own
 * copyright statement to Your modifications and to the derivative works, and
 * You may provide additional or different license terms and conditions in Your
 * sublicenses of modifications of the Software, or any derivative works of the
 * Software as a whole, provided Your use, reproduction, and distribution of the
 * Work otherwise complies with the conditions stated in this License.
 *
 * THIS SOFTWARE IS PROVIDED "AS IS," AND ANY EXPRESSED OR IMPLIED WARRANTIES,
 * (INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY,
 * NON-INFRINGEMENT AND FITNESS FOR A PARTICULAR PURPOSE) ARE DISCLAIMED. IN NO
 * EVENT SHALL THE NATIONAL CANCER INSTITUTE, 5AM SOLUTIONS, INC. OR THEIR
 * AFFILIATES BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS;
 * OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR
 * OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package gov.nih.nci.po.audit;

import com.fiveamsolutions.nci.commons.data.persistent.PersistentObject;
import gov.nih.nci.po.util.PoHibernateUtil;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.persistence.JoinTable;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.log4j.Logger;
import org.apache.log4j.Priority;
import org.hibernate.CallbackException;
import org.hibernate.EmptyInterceptor;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.collection.PersistentCollection;
import org.hibernate.dialect.Dialect;
import org.hibernate.mapping.Column;
import org.hibernate.mapping.PersistentClass;
import org.hibernate.mapping.Property;
import org.hibernate.mapping.Table;
import org.hibernate.type.BagType;
import org.hibernate.type.CollectionType;
import org.hibernate.type.EntityType;
import org.hibernate.type.Type;

import com.fiveamsolutions.nci.commons.util.UsernameHolder;

/**
 * Interceptor that adds audit log records for audits.
 */
@SuppressWarnings({"PMD.CyclomaticComplexity", "unchecked", "PMD.ExcessiveClassLength", "PMD.TooManyMethods" })
public class AuditLogInterceptor extends EmptyInterceptor {

    private static final int MAX_VALUE_LENGTH = 1024;
    private static final BagType BOGUS_TYPE = new BagType("", "", true);
    private static final String COLUMN_NAME = "columnName";
    private static final String TABLE_NAME = "tableName";
    private static final long serialVersionUID = 1L;
    private static final Logger LOG = Logger.getLogger(AuditLogInterceptor.class);

    /**
     * For getting sequence numbers.
     */
    private static Dialect dialect;

    /**
     * Cache for getColumnTableName.
     */
    private static final Map<String, Map<String, String>> COLUMN_CACHE = new HashMap<String, Map<String, String>>();

    /**
     * Set to contain insert update log records.
     */
    private final transient ThreadLocal<Set<AuditLogHelper>> audits =
        new ThreadLocal<Set<AuditLogHelper>>();

    private final transient ThreadLocal<Set<DetailHelper>> details =
        new ThreadLocal<Set<DetailHelper>>();

    private final transient ThreadLocal<Map<RecordKey, AuditLogRecord>> records =
        new ThreadLocal<Map<RecordKey, AuditLogRecord>>();

    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressWarnings("PMD.ExcessiveParameterList")
    public boolean onFlushDirty(Object obj, Serializable id, Object[] newValues, Object[] oldValues,
                                String[] properties, Type[] types) {
        if (obj instanceof Auditable) {
            this.auditChangesIfNeeded((Auditable) obj, newValues, oldValues, properties,
                                      types, AuditType.UPDATE);
        }

        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean onSave(Object obj, Serializable id, Object[] newValues, String[] properties, Type[] types) {
        if (obj instanceof Auditable) {
            this.auditChangesIfNeeded((Auditable) obj, newValues, new Object[properties.length],
                                      properties, types, AuditType.INSERT);
        }

        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onCollectionUpdate(Object collection, Serializable key) {
        if (!(collection instanceof PersistentCollection)) {
            return;
        }

        PersistentCollection pc = (PersistentCollection) collection;
        Object owner = pc.getOwner();
        if (!(owner instanceof Auditable)) {
            return;
        }
        if (audits.get() == null) {
            audits.set(new HashSet<AuditLogHelper>());
            details.set(new HashSet<DetailHelper>());
        }

        Auditable auditableOwner = (Auditable) owner;
        String role = pc.getRole();
        Object oldV = pc.getStoredSnapshot();
        Object newV = pc.getValue();
        String oldValStr = getValueString((Collection<?>) oldV);
        String newValStr = getValueString((Collection<?>) newV);

        int idx = role.lastIndexOf('.');
        String className = role.substring(0, idx);
        String property = role.substring(idx + 1);
        Map<String, String> tabColMA = getColumnTableName(className, property);

        String entityName = tabColMA.get(TABLE_NAME);
        String attribute = tabColMA.get(COLUMN_NAME);

        AuditLogRecord record = getOrCreateRecord(auditableOwner, entityName, (Long) key, AuditType.UPDATE);
        audits.get().add(new AuditLogHelper(record, auditableOwner));
        AuditLogDetail detail = new AuditLogDetail(record, attribute, oldValStr, newValStr, true);
        record.getDetails().add(detail);
        details.get().add(new DetailHelper(detail, (Collection<Auditable>) newV));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void postFlush(Iterator arg0) {

        if (audits.get() != null && !audits.get().isEmpty()) {
            SessionFactory sf = PoHibernateUtil.getHibernateHelper().getSessionFactory();
            Session session = sf.openSession(PoHibernateUtil.getCurrentSession().connection());
            Long transactionId = null;
            try {
                String nextValStatement = getDialect().getSequenceNextValString("AUDIT_ID_SEQ");
                Number uniqueResult = (Number) session.createSQLQuery(nextValStatement).uniqueResult();
                if (uniqueResult == null) {
                    // For some reason, HSQL isn't returning a result here, even though the query doesn't
                    // throw an exception.  This works correctly in postgres.
                    LOG.warn("should only happen in unit tests!!");
                    uniqueResult = 1L;
                }
                transactionId = uniqueResult.longValue();

                // Resolve the new collection values into the proper string value
                for (DetailHelper detail : details.get()) {
                    detail.getDetail().setNewValue(getValueString(detail.getNewVals(), BOGUS_TYPE));
                }

                // Resolve the new entity values on the audit log record itself, then save
                for (AuditLogHelper audit : audits.get()) {
                    audit.getAuditLogRecord().setTransactionId(transactionId);
                    if (AuditType.INSERT.equals(audit.getAuditLogRecord().getType())) {
                        audit.getAuditLogRecord().setEntityId(audit.getEntity().getId());
                    }
                    session.save(audit.getAuditLogRecord());
                }
            } catch (HibernateException e) {
                throw new CallbackException(e);
            } finally {
                audits.get().clear();
                details.get().clear();
                records.get().clear();
                session.flush();
                session.close();
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void afterTransactionCompletion(Transaction arg0) {
        if (audits.get() != null) {
            audits.get().clear();
            details.get().clear();
            records.get().clear();
        }
    }

    /** a composite key for entity audit records. */
    private static final class RecordKey {
        private final Auditable entity;
        private final AuditType type;

        RecordKey(Auditable entity, AuditType type) {
            this.entity = entity; this.type = type;
        }
        /**
         * {@inheritDoc}
         */
        @Override
        public boolean equals(Object obj) {
            RecordKey r = (RecordKey) obj;
            return entity == r.entity && type == r.type;
        }
        /**
         * {@inheritDoc}
         */
        @Override
        public int hashCode() {
            return new HashCodeBuilder().append(entity).appendSuper(type.hashCode()).toHashCode();
        }

    }

    /**
     * @param id
     */
    private AuditLogRecord getOrCreateRecord(Auditable entity, String entityName, Long id,  AuditType eventToLog) {
        Map<RecordKey, AuditLogRecord> map = records.get();
        if (map == null) {
            map = new HashMap<RecordKey, AuditLogRecord>();
            records.set(map);
        }
        RecordKey key = new RecordKey(entity, eventToLog);
        AuditLogRecord record = map.get(key);
        if (record == null) {
            String user = UsernameHolder.getUser();
            record = new AuditLogRecord(eventToLog, entityName, id, user, new Date());
            map.put(key, record);
        }
        return record;
    }

    @SuppressWarnings("PMD.ExcessiveParameterList")
    private void auditChangesIfNeeded(Auditable auditableObj, Object[] newValues, Object[] oldValues,
                                      String[] properties, Type[] types, AuditType eventToLog) {

        // first make sure we have old values around. The passed in set will be null when an object is being updated
        // that was detached from the session
        Object[] myOldValues = getOldValues(oldValues, properties, auditableObj);

        if (audits.get() == null) {
            audits.set(new HashSet<AuditLogHelper>());
            details.set(new HashSet<DetailHelper>());
        }

        AuditLogRecord record = null;

        for (int i = 0; i < properties.length; i++) {
            String property = properties[i];
            Object oldV = myOldValues[i];
            Object newV = newValues[i];
            if (needsAuditing(auditableObj, types[i], newV, oldV, property)) {
                Map<String, String> tabColMA = getColumnTableName(getPersistentClass(auditableObj).getName(),
                                                                                     property);
                if (record == null) {
                    String entityName = tabColMA.get(TABLE_NAME);
                    record = getOrCreateRecord(auditableObj, entityName, auditableObj.getId(), eventToLog);
                    audits.get().add(new AuditLogHelper(record, auditableObj));
                }
                String attribute = tabColMA.get(COLUMN_NAME);
                String oldValStr = getValueString(oldV, types[i]);
                String newValStr = getValueString(newV, types[i]);
                AuditLogDetail detail = new AuditLogDetail(record, attribute, oldValStr, newValStr,
                                                           types[i] instanceof EntityType
                                                               || types[i] instanceof CollectionType);
                record.getDetails().add(detail);
                if (types[i] instanceof CollectionType) {
                    // It's possible that the items in newValues don't yet have their Id assigned yet.
                    // Thus, the call above to getValueString could have resulted in a bogus value.
                    // We store off the information we need to a threadlocal, and will refresh
                    // the value post-flush.
                    details.get().add(new DetailHelper(detail, (Collection<Auditable>) newV));
                }
            }
        }
    }

    private Object[] getOldValues(Object[] oldValues, String[] properties, Auditable auditableObj) {
        Object[] myOldValues = oldValues;
        Session session = null;
        if (myOldValues == null) {
            try {
                SessionFactory sf = PoHibernateUtil.getHibernateHelper().getSessionFactory();
                session = sf.openSession(PoHibernateUtil.getCurrentSession().connection());
                myOldValues = retrieveOldValues(session, auditableObj.getId(), properties,
                                                getPersistentClass(auditableObj));
            } finally {
                session.close();
            }
        }
        return myOldValues;
    }

    //
    // Static methods & classes below here
    //

    static Dialect getDialect() {
        if (dialect != null) {
            return dialect;
        }
        Dialect d = null;
        try {
            d = (Dialect) Class.forName(PoHibernateUtil.getHibernateHelper()
                                                        .getConfiguration()
                                                        .getProperty(Environment.DIALECT)).newInstance();
        } catch (Exception e) {
            LOG.error("Unable to determine dialect.", e);
        }
        dialect = d;
        return d;
    }

    private static boolean needsAuditing(Auditable auditableObj, Type type, Object newValue,
                                         Object oldValue, String property) {
        if (type instanceof CollectionType) {
            return collectionNeedsAuditing(auditableObj, newValue, oldValue, property);
        }
        if (type instanceof EntityType) {
            if (newValue != null && !(newValue instanceof Auditable)) {
                return false;
            }
            if (oldValue != null && !(oldValue instanceof Auditable)) {
                return false;
            }
        }

        return !ObjectUtils.equals(newValue, oldValue);
    }

    private static boolean collectionNeedsAuditing(Auditable auditableObj, Object newValue,
                                                   Object oldValue, String property) {
        Collection<?> oldSet = (Collection<?>) oldValue;
        Collection<?> newSet = (Collection<?>) newValue;

        try {
            Method getter = auditableObj.getClass().getMethod("get" + StringUtils.capitalize(property));
            if (getter.getAnnotation(JoinTable.class) != null) {
                if (LOG.isEnabledFor(Priority.DEBUG)) {
                    LOG.debug("Found annotated getter: " + oldSet + ", " + newSet);
                }
                return !CollectionUtils.isEqualCollection((oldSet == null) ? Collections.emptySet() : oldSet,
                                                          (newSet == null) ? Collections.emptySet() : newSet);
            }
        } catch (SecurityException e) {
            LOG.error(e.getMessage(), e);
        } catch (NoSuchMethodException e) {
            LOG.error(e.getMessage(), e);
        }

        return false;
    }

    private static Object[] retrieveOldValues(Session session, Serializable id, String[] properties,
                                              Class<?> theClass) {
        Object[] myOldValues = new Object[properties.length];

        Object oldObject = session.get(theClass, id);
        if (oldObject != null) {
            for (int i = 0; i < properties.length; i++) {
                try {
                    myOldValues[i] = PropertyUtils.getProperty(oldObject, properties[i]);
                } catch (Exception e) {
                    LOG.error("Unable to read the old value of a property while logging.", e);
                    myOldValues[i] = null;
                }
            }
        }

        return myOldValues;
    }

    private static String getValueString(Object value, Type type) {
        if (value == null) {
            return null;
        }
        if (type instanceof EntityType) {
            return getValueString((Auditable) value);
        }
        if (type instanceof CollectionType) {
            return getValueString((Collection<Auditable>) value);
        }
        return value.toString();
    }

    private static <A extends Auditable> String getValueString(A value) {
        return (value.getId() == null) ? null : value.getId().toString();
    }

    private static String getValueString(Collection<?> value) {
        String sep = "";
        StringBuffer sb = new StringBuffer();
        for (Object a : value) {
            if (a instanceof PersistentObject) {
                a = ((PersistentObject) a).getId();
            } 
            sb.append(sep).append(String.valueOf(a));
            sep = ", ";
            if (sb.length() > MAX_VALUE_LENGTH) {
                String suffix = "...";
                sb.setLength(MAX_VALUE_LENGTH);
                sb.replace(MAX_VALUE_LENGTH - suffix.length(), MAX_VALUE_LENGTH, suffix);
                break;
            }
        }
        return sb.toString();
    }

    /**
     * Retrieves the table name and the column name for the given class and property.
     *
     * @param className
     * @param fieldName
     * @return Map
     */
    private static synchronized Map<String, String> getColumnTableName(String className, String fieldName) {
        String hashkey = className + ";" + fieldName;
        Map<String, String> retMap = COLUMN_CACHE.get(hashkey);
        if (retMap != null) {
            return retMap;
        }

        retMap = new HashMap<String, String>();
        COLUMN_CACHE.put(hashkey, retMap);

        PersistentClass pc = PoHibernateUtil.getHibernateHelper().getConfiguration().getClassMapping(className);
        // get the table and column information
        Table table = pc.getTable();
        String tableName = table.getName();
        String columnName = getColumnName(pc, fieldName);
        if (columnName == null) {
            columnName = fieldName;
        }
        retMap.put(TABLE_NAME, tableName);
        retMap.put(COLUMN_NAME, columnName);
        return retMap;
    }

    /**
     * Retrieves the column name for the given PersistentClass and fieldName.
     *
     * @param pc
     * @param fieldName
     * @return columnName
     */
    private static String getColumnName(PersistentClass pc, String fieldName) {
        if (pc == null) {
            return null;
        }

        String columnName = null;
        Property property = pc.getProperty(fieldName);
        for (Iterator<?> it3 = property.getColumnIterator(); it3.hasNext();) {
            Object o = it3.next();
            if (!(o instanceof Column)) {
                LOG.debug("Skipping non-column (probably a formula");
                continue;
            }
            Column column = (Column) o;
            columnName = column.getName();
            break;
        }
        if (columnName == null) {
            columnName = getColumnName(pc.getSuperclass(), fieldName);
        }

        return columnName;
    }

    /**.
     * Returns the Actual class name from the CGILIB Proxy classname of the entity
     *
     * @param obj
     * @return
     */
    private static Class<?> getPersistentClass(Object obj) {
        Class<?> clazz = obj.getClass();
        Configuration cfg = PoHibernateUtil.getHibernateHelper().getConfiguration();
        while (cfg.getClassMapping(clazz.getName()) == null) {
            if (clazz.getSuperclass() == null) {
                return null;
            }
            clazz = clazz.getSuperclass();
        }
        return clazz;
    }

    /**
     * Class to hold the audit log pair.
     */
    static class AuditLogHelper {
        private final AuditLogRecord auditLogRecord;
        private final Auditable entity;

        /**
         * @return the auditLogRecord
         */
        public AuditLogRecord getAuditLogRecord() {
            return auditLogRecord;
        }

        /**
         * @return the entity
         */
        public Auditable getEntity() {
            return entity;
        }

        /**
         * @param record audit record
         * @param entity entity
         */
        public AuditLogHelper(AuditLogRecord record, Auditable entity) {
            this.auditLogRecord = record;
            this.entity = entity;
        }
    }

    /**
     * Class to hold information about detail records that we can't immediately resolve the 'new' value of.
     */
    static class DetailHelper {
        private final AuditLogDetail detail;
        private final Collection<Auditable> newVals;

        /**
         * @param detail detail record
         * @param newVals (potentially non-persisted [yet]) list of new values
         */
        public DetailHelper(AuditLogDetail detail, Collection<Auditable> newVals) {
            this.detail = detail;
            this.newVals = newVals;
        }

        /**
         * @return the detail
         */
        public AuditLogDetail getDetail() {
            return detail;
        }

        /**
         * @return the newVals
         */
        public Collection<Auditable> getNewVals() {
            return newVals;
        }
    }
}
