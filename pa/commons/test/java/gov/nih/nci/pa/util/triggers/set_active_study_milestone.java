/**
 * 
 */
package gov.nih.nci.pa.util.triggers;

import org.hsqldb.Trigger;

/**
 * @author dkrylov
 * 
 */
public class set_active_study_milestone implements Trigger {

    /*
     * (non-Javadoc)
     * 
     * @see org.hsqldb.Trigger#fire(int, java.lang.String, java.lang.String,
     * java.lang.Object[], java.lang.Object[])
     */
    @Override
    public void fire(int type, String trigName, String tabName,
            Object[] oldRow, Object[] newRow) {

        Long spID = (Long) (newRow != null ? newRow[11] : oldRow[11]);
        if (spID != null) {
            // TODO!!!!!!!!!!!
        }

    }

}
