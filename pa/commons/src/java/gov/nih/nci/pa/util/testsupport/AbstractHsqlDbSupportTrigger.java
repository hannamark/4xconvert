/**
 * 
 */
package gov.nih.nci.pa.util.testsupport;

import org.hsqldb.Trigger;

/**
 * @author dkrylov
 * 
 */
// CHECKSTYLE:OFF
public abstract class AbstractHsqlDbSupportTrigger implements Trigger {

    public static volatile boolean ENABLED = true; // NOPMD

    /*
     * (non-Javadoc)
     * 
     * @see org.hsqldb.Trigger#fire(int, java.lang.String, java.lang.String,
     * java.lang.Object[], java.lang.Object[])
     */
    @Override
    public final void fire(int type, String trigName, String tabName,
            Object[] oldRow, Object[] newRow) { // NOPMD
        if (ENABLED) {
            fireInternal(type, trigName, tabName, oldRow, newRow);
        }

    }

    /**
     * @param type
     *            type
     * @param trigName
     *            trigName
     * @param tabName
     *            tabName
     * @param oldRow
     *            oldRow
     * @param newRow
     *            newRow
     */
    protected abstract void fireInternal(int type, String trigName,
            String tabName, Object[] oldRow, Object[] newRow);// NOPMD

}
