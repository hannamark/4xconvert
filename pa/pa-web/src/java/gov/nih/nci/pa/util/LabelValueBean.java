package gov.nih.nci.pa.util;

/**
 * The Class LabelValueBean.
 *  @author Kalpana Guthikonda
 */
public class LabelValueBean implements Comparable<LabelValueBean> {
    
    /** The id. */
    private Long id;
    
    /** The name. */
    private String name;
    
    /**
     * Gets the id.
     * @return the id
     */
    public Long getId() {
        return id;
    }
    
    /**
     * Sets the id.
     * @param id the new id
     */
    public void setId(Long id) {
        this.id = id;
    }
    
    /**
     * Gets the name.
     * @return the name
     */
    public String getName() {
        return name;
    }
    
    /**
     * Sets the name.
     * @param name the new name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * {@inheritDoc}
     */
    public int compareTo(LabelValueBean obj1) {
        String userName1 = this.getName().toUpperCase();
        String userName2 = obj1.getName().toUpperCase();

        return userName1.compareTo(userName2);

    }
}
