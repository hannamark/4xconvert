/**
 * 
 */
package gov.nih.nci.po.util;

import org.apache.commons.lang.StringEscapeUtils;
import org.hibernate.Session;

/**
 * Class that delivers content of misc_documents table.
 * 
 * @author Denis G. Krylov
 * 
 */
public final class MiscDocumentUtils {

    /**
     * Returns content of the first found document with the given name, ignoring
     * the version and application name.
     * 
     * @param docName
     *            String
     * @return String
     */
    public static String getDocumentContent(String docName) {
        Session s = PoHibernateUtil.getCurrentSession();
        return (String) s.createSQLQuery(
                "select content from misc_documents where name='"
                        + StringEscapeUtils.escapeSql(docName) + "' limit 1")
                .uniqueResult();
    }
}
