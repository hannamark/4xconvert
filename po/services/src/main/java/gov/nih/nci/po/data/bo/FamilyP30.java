package gov.nih.nci.po.data.bo;

import com.fiveamsolutions.nci.commons.audit.Auditable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * @author Hugh Reinhart
 * @since Jun 21, 2013
 * 
 * @xsnapshot.snapshot-class name="iso" tostring="none"
 *      class="gov.nih.nci.services.family.FamilyP30DTO"
 *      implements="gov.nih.nci.services.PoDto"
 *      generate-helper-methods="false"
 *      serial-version-uid="1L" */
@Entity
@Table(name = "family_p30_grant")
public class FamilyP30 implements Auditable {

    private static final long serialVersionUID = 9142333748378327002L;

    private Long id;
    private String serialNumber;
    private Family family;


    /**
     * @return database id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }
    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }
    /**
     * @return the family
     */
    @OneToOne
    public Family getFamily() {
        return family;
    }
    /**
     * @param family the family to set
     */
    public void setFamily(Family family) {
        this.family = family;
    }
    /**
     * @return the serialNumber
     * @xsnapshot.property match="iso" type="gov.nih.nci.iso21090.EnOn"
     *                     snapshot-transformer="gov.nih.nci.po.data.convert.StringConverter"
     *                     model-transformer="gov.nih.nci.po.data.convert.EnConverter"
     */
    public String getSerialNumber() {
        return serialNumber;
    }
    /**
     * @param serialNumber the serialNumber to set
     */
    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }
}
