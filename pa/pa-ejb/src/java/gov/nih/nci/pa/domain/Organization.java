/**
 * 
 */
package gov.nih.nci.pa.domain;


import javax.persistence.Column;
import javax.persistence.Entity;
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
    private String nciInstituteCode;
    
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
