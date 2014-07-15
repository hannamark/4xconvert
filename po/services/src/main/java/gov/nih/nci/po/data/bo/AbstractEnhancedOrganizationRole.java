package gov.nih.nci.po.data.bo;

import com.fiveamsolutions.nci.commons.search.Searchable;
import gov.nih.nci.po.util.PoRegistry;
import org.hibernate.annotations.Index;
import org.hibernate.validator.Length;

import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Base class for all organization to health care facility types.
 *
 * @xsnapshot.snapshot-class name="iso" tostring="none" generate-helper-methods="false"
 *      class="gov.nih.nci.services.correlation.AbstractEnhancedOrganizationRoleDTO"
 *      model-extends="gov.nih.nci.po.data.bo.AbstractOrganizationRole"
 *      extends="gov.nih.nci.services.correlation.AbstractBaseEnhancedOrganizationRoleDTO"
 *      serial-version-uid="2L"
 */
@MappedSuperclass
public abstract class AbstractEnhancedOrganizationRole extends AbstractOrganizationRole
    implements UsOrCanEnforceable, Mailable, Aliasable {

    private static final long serialVersionUID = 1L;
    private static final int DEFAULT_TEXT_COL_LENGTH = 160;

    private String name;
    private List<Email> email = new ArrayList<Email>();
    private Set<Address> postalAddresses = new HashSet<Address>();
    private List<PhoneNumber> fax = new ArrayList<PhoneNumber>(1);
    private List<PhoneNumber> phone = new ArrayList<PhoneNumber>(1);
    private List<PhoneNumber> tty = new ArrayList<PhoneNumber>(1);
    private List<URL> url = new ArrayList<URL>(1);
    
    private List<Alias> alias = new ArrayList<Alias>();

    /**
     * @return the name
     * @xsnapshot.property match="iso" type="gov.nih.nci.iso21090.EnOn"
     *                     snapshot-transformer="gov.nih.nci.po.data.convert.StringConverter"
     *                     model-transformer="gov.nih.nci.po.data.convert.EnConverter"
     */
    @Length(max = DEFAULT_TEXT_COL_LENGTH)
    @Searchable(matchMode = Searchable.MATCH_MODE_CONTAINS)
    @Index(name = PoRegistry.GENERATE_INDEX_NAME_PREFIX + "name")
    public String getName() {
        return this.name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the email
     */
    @Transient
    public List<Email> getEmail() {
        return this.email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(List<Email> email) {
        this.email = email;
    }

    /**
     * @return the postalAddresses
     *
     @xsnapshot.property name="postalAddress"
     *                     match="iso" type="gov.nih.nci.iso21090.DSet"
     *                     snapshot-transformer="gov.nih.nci.po.data.convert.AddressConverter$SetConverter"
     *                     model-transformer="gov.nih.nci.po.data.convert.AdConverter$DSetConverter"
     */
    @Transient
    public Set<Address> getPostalAddresses() {
        return this.postalAddresses;
    }

    /**
     * @param postalAddresses the postalAddresses to set
     */
    public void setPostalAddresses(Set<Address> postalAddresses) {
        this.postalAddresses = postalAddresses;
    }

    /**
     * @return the fax
     */
    @Transient
    public List<PhoneNumber> getFax() {
        return this.fax;
    }

    /**
     * @param fax the fax to set
     */
    public void setFax(List<PhoneNumber> fax) {
        this.fax = fax;
    }

    /**
     * @return the phone
     */
    @Transient
    public List<PhoneNumber> getPhone() {
        return this.phone;
    }

    /**
     * @param phone the phone to set
     */
    public void setPhone(List<PhoneNumber> phone) {
        this.phone = phone;
    }

    /**
     * @return the tty
     */
    @Transient
    public List<PhoneNumber> getTty() {
        return this.tty;
    }

    /**
     * @param tty the tty to set
     */
    public void setTty(List<PhoneNumber> tty) {
        this.tty = tty;
    }

    /**
     * @return the url
     */
    @Transient
    public List<URL> getUrl() {
        return this.url;
    }

    /**
     * @param url the url to set
     */
    public void setUrl(List<URL> url) {
        this.url = url;
    }
    
    /**
     * @return the alias
     */
    @Transient
    public List<Alias> getAlias() {
        return this.alias;
    }

    /**
     * @param alias the alias to set
     */
    public void setAlias(List<Alias> alias) {
        this.alias = alias;
    }

    /**
     * Returns boolean if the address is US.
     * @return bool
     */
    @Transient
    public boolean isUsOrCanadaAddress() {
        return super.isUsOrCanadaAddresses(this.getPostalAddresses());
    }
}
