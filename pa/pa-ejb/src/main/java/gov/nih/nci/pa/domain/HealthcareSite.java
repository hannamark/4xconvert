/**
 * 
 */
package gov.nih.nci.pa.domain;


import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.validator.Length;
import org.hibernate.validator.NotNull;

/**
 * todo: Interface with PO as source of truth.
 * Healthcare site bo.  
 * @author Hugh Reinhart
 *
 */
@Entity
@Table(name =  "HEALTHCARE_SITE")
public class HealthcareSite implements Serializable {
    private static final long serialVersionUID = 1234567890L;
    
    private Long id;
    private String name;
    private String nciInstituteCode;
    
    @SuppressWarnings("unused")    
    private void setId(Long id) {
        this.id = id;
    }

    /**
     * Get the id of the object.
     *
     * @return the id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return this.id;
    }

    
    /**
     * @return the name
     */
    @NotNull
    @Length(max = AbstractEntity.LONG_TEXT_LENGTH)
    @Column(name = "NAME")
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
     * @return nciInstituteCode Nci Institute Code
     */
    @Column(name = "NCI_INSTITUTE_CODE")
    public String getNciInstituteCode() {
        return nciInstituteCode;
    }

    /**
     * 
     * @param nciInstituteCode nciInstituteCode
     */
    public void setNciInstituteCode(String nciInstituteCode) {
       this.nciInstituteCode = nciInstituteCode;
    }
    
}
