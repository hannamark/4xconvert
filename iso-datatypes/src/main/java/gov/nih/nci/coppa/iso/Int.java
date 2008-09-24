package gov.nih.nci.coppa.iso;
/**
 * Integer numbers (-1,0,1,2, 100, 3398129, etc.) are precise numbers that are results of 
 * counting and enumerating. Integer numbers are discrete, the set of integers is 
 * infinite but countable. No arbitrary limit is imposed on the range of integer 
 * numbers..
 * 
 * @author Naveen Amiruddin
 *
 */
public class Int extends Qty {
    
    private static final long serialVersionUID = 1L;
    
    private Integer value;

    /**
     * 
     * @return value
     */
    public Integer getValue() {
        return value;
    }

    /**
     * 
     * @param value value of Integer
     */
    public void setValue(Integer value) {
        this.value = value;
    }
}
