/**
 * 
 */
package gov.nih.nci.pa.domain;


import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.validator.Length;
import org.hibernate.validator.NotNull;

/**
 * A formalized group of persons or other organizations collected together for a common purpose 
 * (such as administrative, legal, political) and the infrastructure to carry out that purpose..
 * @author Hugh Reinhart
 *
 */
@Entity
@Table(name =  "ORGANIZATION")
public class Organization extends AbstractEntity {
    private static final long serialVersionUID = 1234567890L;
    
    private String name;
    private String identifier;
    private List<HealthCareFacility> healthCareFacilities = new ArrayList<HealthCareFacility>();
    
    /**
     * @return the name
     */
    @NotNull
    @Length(max = AbstractEntity.LONG_TEXT_LENGTH)
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 
     * @return identifier 
     */
    @Column(name = "Identifier")
    public String getIdentifier() {
        return identifier;
    }

    /**
     * 
     * @param identifier identifier
     */
    public void setIdentifier(String identifier) {
       this.identifier = identifier;
    }

    /**
     * 
     * @return healthCareFacilities
     */
    @OneToMany(mappedBy = "organization")
    public List<HealthCareFacility> getHealthCareFacilities() {
        return healthCareFacilities;
    }

    /**
     * 
     * @param healthCareFacilities healthCareFacilities
     */
    public void setHealthCareFacilities(
            List<HealthCareFacility> healthCareFacilities) {
        this.healthCareFacilities = healthCareFacilities;
    }
    
    
    
}
