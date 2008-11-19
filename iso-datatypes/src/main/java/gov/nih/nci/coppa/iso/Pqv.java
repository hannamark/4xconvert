package gov.nih.nci.coppa.iso;

import java.math.BigDecimal;

/**
 * Physical Quantity Value (PQV).
 * @author Naveen Amiruddin
 *
 */
public class Pqv extends Qty {
    private static final long serialVersionUID = 1L;
    
    private BigDecimal value;
    private Integer precision; 

    /**
     * 
     * @return value 
     */
    public BigDecimal getValue() {
        return value;
    }

    /**
     * 
     * @param value the value
     */
    public void setValue(BigDecimal value) {
        this.value = value;
    }

    /**
     * 
     * @return precision 
     */
    public Integer getPrecision() {
        return precision;
    }

    /**
     * 
     * @param precision precision digits
     */
    public void setPrecision(Integer precision) {
        this.precision = precision;
    }
    
    
}
