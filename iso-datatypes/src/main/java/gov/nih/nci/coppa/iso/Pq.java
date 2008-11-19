package gov.nih.nci.coppa.iso;

/**
 * Physical Quantity (PQ)/
 * A dimensioned quantity expressing the result of measuring.
 * @author Naveen Amiruddin
 *
 */
public class Pq extends Pqv {
    private static final long serialVersionUID = 1L;
    
    private String unit;

    /**
     * 
     * @return unit 
     */
    public String getUnit() {
        return unit;
    }

    /**
     * 
     * @param unit unit
     */
    public void setUnit(String unit) {
        this.unit = unit;
    }
}
