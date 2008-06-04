package gov.nih.nci.pa.util;

/**
 * 
 * @author Harsha
 *
 */
public class PaRegistry {
    
    /**
     * Number of records to display by default in displaytag controlled tables.
     */
    public static final int DEFAULT_RECORDS_PER_PAGE = 20;
    private static final PaRegistry PO_REGISTRY = new PaRegistry();
    private ServiceLocator serviceLocator;


}
