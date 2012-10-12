/**
 * 
 */
package gov.nih.nci.coppa.services.pa.grid;

/**
 * @author Denis G. Krylov
 * 
 */
public final class PAGridUtils {

    private PAGridUtils() {
    }

    /**
     * The only purpose of this method is to fix an issue described in
     * https://tracker.nci.nih.gov/browse/PO-5558 (because we are unable to
     * apply the fix directory to NCI's ISO 21090 project at the time).
     */
    public static void initIso21090Transformers() {
        try {
            // This is to force the "proper" class initialization order.
            // It is critical that this class gets initialization start first,
            // not any of the two of its sub-classes.
            Class.forName("gov.nih.nci.iso21090.grid.dto.transform.iso.ENTransformer");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

}
