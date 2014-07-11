/**
 * The software subject to this notice and license includes both human readable
 * source code form and machine readable, binary, object code form. The po
 * Software was developed in conjunction with the National Cancer Institute
 * (NCI) by NCI employees and 5AM Solutions, Inc. (5AM). To the extent
 * government employees are authors, any rights in such works shall be subject
 * to Title 17 of the United States Code, section 105.
 *
 * This po Software License (the License) is between NCI and You. You (or
 * Your) shall mean a person or an entity, and all other entities that control,
 * are controlled by, or are under common control with the entity. Control for
 * purposes of this definition means (i) the direct or indirect power to cause
 * the direction or management of such entity, whether by contract or otherwise,
 * or (ii) ownership of fifty percent (50%) or more of the outstanding shares,
 * or (iii) beneficial ownership of such entity.
 *
 * This License is granted provided that You agree to the conditions described
 * below. NCI grants You a non-exclusive, worldwide, perpetual, fully-paid-up,
 * no-charge, irrevocable, transferable and royalty-free right and license in
 * its rights in the po Software to (i) use, install, access, operate,
 * execute, copy, modify, translate, market, publicly display, publicly perform,
 * and prepare derivative works of the po Software; (ii) distribute and
 * have distributed to and by third parties the po Software and any
 * modifications and derivative works thereof; and (iii) sublicense the
 * foregoing rights set out in (i) and (ii) to third parties, including the
 * right to license such rights to further third parties. For sake of clarity,
 * and not by way of limitation, NCI shall have no right of accounting or right
 * of payment from You or Your sub-licensees for the rights granted under this
 * License. This License is granted at no charge to You.
 *
 * Your redistributions of the source code for the Software must retain the
 * above copyright notice, this list of conditions and the disclaimer and
 * limitation of liability of Article 6, below. Your redistributions in object
 * code form must reproduce the above copyright notice, this list of conditions
 * and the disclaimer of Article 6 in the documentation and/or other materials
 * provided with the distribution, if any.
 *
 * Your end-user documentation included with the redistribution, if any, must
 * include the following acknowledgment: This product includes software
 * developed by 5AM and the National Cancer Institute. If You do not include
 * such end-user documentation, You shall include this acknowledgment in the
 * Software itself, wherever such third-party acknowledgments normally appear.
 *
 * You may not use the names "The National Cancer Institute", "NCI", or "5AM"
 * to endorse or promote products derived from this Software. This License does
 * not authorize You to use any trademarks, service marks, trade names, logos or
 * product names of either NCI or 5AM, except as required to comply with the
 * terms of this License.
 *
 * For sake of clarity, and not by way of limitation, You may incorporate this
 * Software into Your proprietary programs and into any third party proprietary
 * programs. However, if You incorporate the Software into third party
 * proprietary programs, You agree that You are solely responsible for obtaining
 * any permission from such third parties required to incorporate the Software
 * into such third party proprietary programs and for informing Your
 * sub-licensees, including without limitation Your end-users, of their
 * obligation to secure any required permissions from such third parties before
 * incorporating the Software into such third party proprietary software
 * programs. In the event that You fail to obtain such permissions, You agree
 * to indemnify NCI for any claims against NCI by such third parties, except to
 * the extent prohibited by law, resulting from Your failure to obtain such
 * permissions.
 *
 * For sake of clarity, and not by way of limitation, You may add Your own
 * copyright statement to Your modifications and to the derivative works, and
 * You may provide additional or different license terms and conditions in Your
 * sublicenses of modifications of the Software, or any derivative works of the
 * Software as a whole, provided Your use, reproduction, and distribution of the
 * Work otherwise complies with the conditions stated in this License.
 *
 * THIS SOFTWARE IS PROVIDED "AS IS," AND ANY EXPRESSED OR IMPLIED WARRANTIES,
 * (INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY,
 * NON-INFRINGEMENT AND FITNESS FOR A PARTICULAR PURPOSE) ARE DISCLAIMED. IN NO
 * EVENT SHALL THE NATIONAL CANCER INSTITUTE, 5AM SOLUTIONS, INC. OR THEIR
 * AFFILIATES BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS;
 * OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR
 * OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package gov.nih.nci.po.data.bo;

import com.fiveamsolutions.nci.commons.data.persistent.PersistentObject;
import com.fiveamsolutions.nci.commons.search.Searchable;
import gov.nih.nci.po.util.PoRegistry;
import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.ForeignKey;
import org.hibernate.annotations.Index;
import org.hibernate.annotations.IndexColumn;
import org.hibernate.validator.Length;
import org.hibernate.validator.Valid;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author smatyas
 */
@Entity
@SuppressWarnings("PMD")
public class OrganizationalContactCR extends AbstractOrganizationalContact
    implements CorrelationChangeRequest<OrganizationalContact> {
    private static final String INDEX_NAME = "idx";
    private static final String JOIN_COLUMN = "orgcontactcr_id";
    private static final long serialVersionUID = 1L;
    private OrganizationalContact target;

    private boolean processed;

    /**
     * {@inheritDoc}
     */
    public boolean isProcessed() {
        return this.processed;
    }

    /**
     * {@inheritDoc}
     */
    public void setProcessed(boolean processed) {
        this.processed = processed;
    }

    /**
     * default ctor.
     */
    public OrganizationalContactCR() {
        super();
    }

    /**
     * useful ctor.
     * @param target the OrganizationalContact that should changed.
     */
    public OrganizationalContactCR(OrganizationalContact target) {
        this();
        this.target = target;
    }


    /** {@inheritDoc} */
    @Override
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return super.getId();
    }

    /**
     * {@inheritDoc}
     */
    @ManyToOne(optional = true)
    @JoinColumn(name = "orgcontacttype_id", nullable = false)
    @Index(name = "oc_orgcontacttypeid_idx")
    @ForeignKey(name = "ORGCNCTCR_TYPE_ORGCNCTCR_FK")
    @Searchable
    @Override
    public OrganizationalContactType getType() {
        return super.getType();
    }

    /**
     * @return the person that should have this proposed state
     */
    @ManyToOne(optional = false)
    @JoinColumn(name = "target", nullable = false)
    @Index(name = "orgcontactcr_target_idx")
    @ForeignKey(name = "ORGCNCTCR_TARGET_ORGCNCT_FK")
    public OrganizationalContact getTarget() {
        return target;
    }

    /**
     * @param target affected role.
     */
    public void setTarget(OrganizationalContact target) {
        this.target = target;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @OneToMany
    @Cascade(value = {org.hibernate.annotations.CascadeType.ALL,
                      org.hibernate.annotations.CascadeType.DELETE_ORPHAN }
    )
    @JoinTable(
            name = "orgcontactcr_address",
            joinColumns = @JoinColumn(name = JOIN_COLUMN),
            inverseJoinColumns = @JoinColumn(name = "address_id")
    )
    @IndexColumn(name = INDEX_NAME)
    @ForeignKey(name = "ORGCNCTCR_ADDRESS_FK", inverseName = "ADDRESS_ORGCNCTCR_FK")
    @Valid
    public Set<Address> getPostalAddresses() {
        return super.getPostalAddresses();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @OneToMany
    @Cascade(value = {org.hibernate.annotations.CascadeType.ALL,
                      org.hibernate.annotations.CascadeType.DELETE_ORPHAN }
    )
    @JoinTable(
            name = "orgcontactcr_email",
            joinColumns = @JoinColumn(name = JOIN_COLUMN),
            inverseJoinColumns = @JoinColumn(name = "email_id")
    )
    @IndexColumn(name = INDEX_NAME)
    @ForeignKey(name = "ORGCNCTCR_EMAIL_FK", inverseName = "EMAIL_ORGCNCTCR_FK")
    @Valid
    public List<Email> getEmail() {
        return super.getEmail();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @OneToMany
    @Cascade(value = {org.hibernate.annotations.CascadeType.ALL,
                      org.hibernate.annotations.CascadeType.DELETE_ORPHAN }
    )
    @JoinTable(
            name = "orgcontactcr_fax",
            joinColumns = @JoinColumn(name = JOIN_COLUMN),
            inverseJoinColumns = @JoinColumn(name = "fax_id")
    )
    @IndexColumn(name = INDEX_NAME)
    @ForeignKey(name = "ORGCNCTCR_FAX_FK", inverseName = "FAX_ORGCNCTCR_FK")
    @Valid
    public List<PhoneNumber> getFax() {
        return super.getFax();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @OneToMany
    @Cascade(value = {org.hibernate.annotations.CascadeType.ALL,
                      org.hibernate.annotations.CascadeType.DELETE_ORPHAN }
    )
    @JoinTable(
            name = "orgcontactcr_phone",
            joinColumns = @JoinColumn(name = JOIN_COLUMN),
            inverseJoinColumns = @JoinColumn(name = "phone_id")
    )
    @IndexColumn(name = INDEX_NAME)
    @ForeignKey(name = "ORGCNCTCR_PHONE_FK", inverseName = "PHONE_ORGCNCTCR_FK")
    @Valid
    public List<PhoneNumber> getPhone() {
        return super.getPhone();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @OneToMany
    @Cascade(value = {org.hibernate.annotations.CascadeType.ALL,
                      org.hibernate.annotations.CascadeType.DELETE_ORPHAN }
    )
    @JoinTable(
            name = "orgcontactcr_tty",
            joinColumns = @JoinColumn(name = JOIN_COLUMN),
            inverseJoinColumns = @JoinColumn(name = "tty_id")
    )
    @IndexColumn(name = INDEX_NAME)
    @ForeignKey(name = "ORGCNCTCR_TTY_FK", inverseName = "TTY_ORGCNCTCR_FK")
    @Valid
    public List<PhoneNumber> getTty() {
        return super.getTty();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @OneToMany
    @Cascade(value = {org.hibernate.annotations.CascadeType.ALL,
                      org.hibernate.annotations.CascadeType.DELETE_ORPHAN }
    )
    @JoinTable(
            name = "orgcontactcr_url",
            joinColumns = @JoinColumn(name = JOIN_COLUMN),
            inverseJoinColumns = @JoinColumn(name = "url_id")
    )
    @IndexColumn(name = INDEX_NAME)
    @ForeignKey(name = "ORGCNCTCR_URL_FK", inverseName = "URL_ORGCNCTCR_FK")
    @Valid
    public List<URL> getUrl() {
        return super.getUrl();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @ManyToOne
    @JoinColumn(name = "person_id")
    @ForeignKey(name = "personrole_per_fkey")
    @Index(name = PoRegistry.GENERATE_INDEX_NAME_PREFIX + "player")
    public Person getPlayer() {
        return super.getPlayer();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Length(max = LONG_COL_LENGTH)
    public String getTitle() {
        return super.getTitle();
    }
    
    
    /**
     * @return boolean
     */
    @Transient
    public boolean isStatusCodeChanged() {
        return getStatus() != target.getStatus();
    }
    
    /**
     * @return boolean
     */
    @Transient
    public boolean isTitleChanged() {
        return !StringUtils.equalsIgnoreCase(getTitle(), target.getTitle());
    }
    
    /**
     * @return boolean
     */
    @Transient
    public boolean isContactTypeChanged() {
        if (getType() == null && target.getType() == null) {
            return false;
        }
        
        return !StringUtils.equals(getType().getCode(), target.getType().getCode());
    }
    
   

    /**
     * @return boolean
     */
    @Transient
    public boolean isCountryChanged() {
        return (getPostalAddresses().size() != target.getPostalAddresses()
                .size())
                || (!getPostalAddresses().isEmpty()
                        && !target.getPostalAddresses().isEmpty() && !StringUtils
                            .equals(getPostalAddresses().iterator().next()
                                    .getCountry().getName(), target
                                    .getPostalAddresses().iterator().next()
                                    .getCountry().getName()));
    }
    
    /**
     * @return boolean
     */
    @Transient
    public boolean isStreetAddressLineChanged() {
        return (getPostalAddresses().size() != target.getPostalAddresses()
                .size())
                || (!StringUtils.equals(getPostalAddresses().iterator().next()
                        .getStreetAddressLine(), target.getPostalAddresses()
                        .iterator().next().getStreetAddressLine()));
    }

    /**
     * @return boolean
     */
    @Transient
    public boolean isDeliveryAddressLineChanged() {
        return (getPostalAddresses().size() != target.getPostalAddresses()
                .size())
                || (!StringUtils.equals(getPostalAddresses().iterator().next()
                        .getDeliveryAddressLine(), target.getPostalAddresses()
                        .iterator().next().getDeliveryAddressLine()));
    }

    /**
     * @return boolean
     */
    @Transient
    public boolean isCityOrMunicipalityChanged() {
        return (getPostalAddresses().size() != target.getPostalAddresses()
                .size())
                || (!StringUtils.equals(getPostalAddresses().iterator().next()
                        .getCityOrMunicipality(), target.getPostalAddresses()
                        .iterator().next().getCityOrMunicipality()));
    }

    /**
     * @return boolean
     */
    @Transient
    public boolean isStateOrProvinceChanged() {
        return (getPostalAddresses().size() != target.getPostalAddresses()
                .size())
                || (!StringUtils.equals(getPostalAddresses().iterator().next()
                        .getStateOrProvince(), target.getPostalAddresses()
                        .iterator().next().getStateOrProvince()));
    }
    
    /**
     * @return boolean
     */
    @Transient
    public boolean isPostalCodeChanged() {
        return (getPostalAddresses().size() != target.getPostalAddresses()
                .size())
                || (!StringUtils.equals(getPostalAddresses().iterator().next()
                        .getPostalCode(), target.getPostalAddresses()
                        .iterator().next().getPostalCode()));
    }
    
    /**
     * @return boolean
     */
    @Transient
    public boolean isEmailChanged() {
        return isContactChanged(target.getEmail(), getEmail());
    }
    
    /**
     * @return boolean
     */
    @Transient
    public boolean isUrlChanged() {
        return isContactChanged(target.getUrl(), getUrl());

    }
    
    /**
     * @return boolean
     */
    @Transient
    public boolean isPhoneChanged() {
        return isContactChanged(target.getPhone(), getPhone());
    }
    
    /**
     * @return boolean
     */
    @Transient
    public boolean isFaxChanged() {
        return isContactChanged(target.getFax(), getFax());
    }
    
    /**
     * @return boolean
     */
    @Transient
    public boolean isTtyChanged() {
        return isContactChanged(target.getTty(), getTty());
    }


    private boolean isContactChanged(List<? extends Contact> oldContacts,
            List<? extends Contact> newContacts) {
        TreeSet<Contact> set = new TreeSet<Contact>(new Comparator<Contact>() {
            public int compare(Contact o1, Contact o2) {
                return StringUtils.equalsIgnoreCase(o1.getValue(),
                        o2.getValue()) ? 0 : -1;
            }
        });
        set.addAll(oldContacts);
        set.addAll(newContacts);
        if (set.size() != oldContacts.size()) {
            return true;
        }
        return false;
    }
    
    /**
     * @return boolean
     */
    @Transient
    public boolean isNoChange() { 
        return !isCityOrMunicipalityChanged() && !isCountryChanged()
                && !isDeliveryAddressLineChanged() && !isEmailChanged()
                && !isFaxChanged() && !isPhoneChanged()
                && !isPostalCodeChanged() && !isStateOrProvinceChanged()
                && !isStatusCodeChanged() && !isStreetAddressLineChanged()
                && !isTtyChanged() && !isUrlChanged();
    }

    /**
     * blank implementation of method from Curatable interface.
     * @return null.
     */
    @Transient
    public PersistentObject getDuplicateOf() {        
        return null;
    }

    /**
     * blank implementation of method from Curatable interface.
     * @return null.
     */
    @SuppressWarnings("rawtypes")
    @Transient
    public Set getChangeRequests() {        
        return null;
    }

}
