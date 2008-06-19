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
package gov.nih.nci.po.data.cr;

import gov.nih.nci.po.data.bo.ContactInfo;
import gov.nih.nci.po.data.bo.Organization;
import gov.nih.nci.po.data.bo.Person;
import gov.nih.nci.po.data.common.AbstractContactInfo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.ForeignKey;
import org.hibernate.annotations.IndexColumn;

/**
 * ContactInfo change request.  These change requests are <em>always</em> tied to
 * either a Person or Organization Change request.
 */
@Entity
@SuppressWarnings({"PMD.UselessOverridingMethod", "PMD.AvoidDuplicateLiterals" })
public class ContactInfoCR extends AbstractContactInfo {

    private static final long serialVersionUID = -2612520619734068917L;
    private Person person;
    private Organization organization;
    private ContactInfo contactInfo;
    private AddressCR addressCR;
    private List<EmailCR> emailAddsAndMods = new ArrayList<EmailCR>();
    private List<EmailCR> emailDrops = new ArrayList<EmailCR>();
    private List<PhoneNumberCR> faxAddsAndMods = new ArrayList<PhoneNumberCR>();
    private List<PhoneNumberCR> faxDrops = new ArrayList<PhoneNumberCR>();
    private List<PhoneNumberCR> phoneAddsAndMods = new ArrayList<PhoneNumberCR>();
    private List<PhoneNumberCR> phoneDrops = new ArrayList<PhoneNumberCR>();
    private List<URLCR> urlAddsAndMods = new ArrayList<URLCR>();
    private List<URLCR> urlDrops = new ArrayList<URLCR>();

    /**
     * used by hibernate and jaxb.
     */
    public ContactInfoCR() {
        // for hibernate and jaxb
    }

    /**
     * @param contactInfoId id of the contact info to change
     */
    public ContactInfoCR(long contactInfoId) {
        this.contactInfo = new ContactInfo();
        this.contactInfo.setId(contactInfoId);
    }

    /**
     * @return the contactInfo
     */
    @ManyToOne
    @ForeignKey(name = "CICR_CI_FK")
    private ContactInfo getContactInfo() {
        return contactInfo;
    }

    /**
     * @param contactInfo the contactInfo to set
     */
    private void setContactInfo(ContactInfo contactInfo) {
        this.contactInfo = contactInfo;
    }

    /**
     * Get the contact info id this cr is associated with.
     * @return the id
     */
    @Transient
    public Long getContactInfoId() {
        return (getContactInfo() == null) ? null : getContactInfo().getId();
    }

    /**
     * Set the id of the ci this cr is associated to.
     * @param id the id.
     */
    @SuppressWarnings("deprecation")
    public void setContactInfoId(Long id) {
        if (getContactInfo() == null) {
            setContactInfo(new ContactInfo());
        }
        getContactInfo().setId(id);
    }

    /**
     * @return the person
     */
    @ManyToOne
    @ForeignKey(name = "CICR_PERSON_FK")
    private Person getPerson() {
        return this.person;
    }

    /**
     * @param person the person to set
     */
    private void setPerson(Person person) {
        this.person = person;
    }

    /**
     * Get the id of the person this change request is associated with.
     * @return the id.
     */
    @Transient
    public Long getPersonId() {
        return (getPerson() != null) ? getPerson().getId() : null;
    }

    /**
     * Set the id of the person this change request is associated with.
     * @param id the id.
     */
    public void setPersonId(Long id) {
        if (getPerson() == null) {
            setPerson(new Person());
        }
        getPerson().setId(id);
    }

    /**
     * @return the organization
     */
    @ManyToOne
    @ForeignKey(name = "CICR_ORG_FK")
    private Organization getOrganization() {
        return this.organization;
    }

    /**
     * @param organization the organization to set
     */
    private void setOrganization(Organization organization) {
        this.organization = organization;
    }

    /**
     * Get the id of the Organization this change request is associated with.
     * @return the id.
     */
    @Transient
    public Long getOrganizationId() {
        return (getOrganization() != null) ? getOrganization().getId() : null;
    }

    /**
     * Set the id of the Organization this change request is associated with.
     * @param id the id.
     */
    public void setOrganizationId(Long id) {
        if (getOrganization() == null) {
            setOrganization(new Organization());
        }
        getOrganization().setId(id);
    }

    /**
     * @return the addressCR
     */
    @ManyToOne (cascade = CascadeType.ALL)
    @ForeignKey(name = "CICR_ADDRESSCR_FK")
    public AddressCR getAddressCR() {
        return addressCR;
    }

    /**
     * @param addressCR the addressCR to set
     */
    public void setAddressCR(AddressCR addressCR) {
        this.addressCR = addressCR;
    }

    /**
     * @param email new emailAddsAndMods address list
     */
    protected void setEmailAddsAndMods(List<EmailCR> email) {
        this.emailAddsAndMods = email;
    }

    /**
     * @param email new emailDrops address list
     */
    protected void setEmailDrops(List<EmailCR> email) {
        this.emailDrops = email;
    }

    /**
     * @param fax new fax
     */
    protected void setFaxAddsAndMods(List<PhoneNumberCR> fax) {
        this.faxAddsAndMods = fax;
    }

    /**
     * @param fax new fax
     */
    protected void setFaxDrops(List<PhoneNumberCR> fax) {
        this.faxDrops = fax;
    }

    /**
     * @param phone new phone list
     */
    protected void setPhoneAddsAndMods(List<PhoneNumberCR> phone) {
        this.phoneAddsAndMods = phone;
    }

    /**
     * @param phone new phone list
     */
    protected void setPhoneDrops(List<PhoneNumberCR> phone) {
        this.phoneDrops = phone;
    }

    /**
     * @param url new url
     */
    protected void setUrlAddsAndMods(List<URLCR> url) {
        this.urlAddsAndMods = url;
    }

    /**
     * @param url new url
     */
    protected void setUrlDrops(List<URLCR> url) {
        this.urlDrops = url;
    }

    /**
     * @return emailAddsAndMods list of add / modify requests.
     */
    @OneToMany
    @Cascade(value = {org.hibernate.annotations.CascadeType.ALL,
                      org.hibernate.annotations.CascadeType.DELETE_ORPHAN }
    )
    @JoinTable(
            name = "cicr_email_adds",
            joinColumns = @JoinColumn(name = "cicr_id"),
            inverseJoinColumns = @JoinColumn(name = "email_id")
    )
    @IndexColumn(name = "idx")
    @ForeignKey(name = "CICR_EMAIL_ADD_CI_FK", inverseName = "CICR_EMAIL_ADD_EMAIL_FK")
    public List<EmailCR> getEmailAddsAndMods() {
        return emailAddsAndMods;
    }

    /**
     * @return emailAddsAndMods list of delete requests.
     */
    @OneToMany
    @Cascade(value = {org.hibernate.annotations.CascadeType.ALL,
                      org.hibernate.annotations.CascadeType.DELETE_ORPHAN }
    )
    @JoinTable(
            name = "cicr_email_drops",
            joinColumns = @JoinColumn(name = "cicr_id"),
            inverseJoinColumns = @JoinColumn(name = "email_id")
    )
    @IndexColumn(name = "idx")
    @ForeignKey(name = "CICR_EMAIL_DROP_CI_FK", inverseName = "CICR_EMAIL_DROP_EMAIL_FK")
    public List<EmailCR> getEmailDrops() {
        return emailDrops;
    }

    /**
     * @return fax list
     */
    @OneToMany
    @Cascade(value = {org.hibernate.annotations.CascadeType.ALL,
            org.hibernate.annotations.CascadeType.DELETE_ORPHAN }
    )
    @JoinTable(
            name = "cicr_fax_adds",
            joinColumns = @JoinColumn(name = "cicr_id"),
            inverseJoinColumns = @JoinColumn(name = "phone_id")
    )
    @IndexColumn(name = "idx")
    @Column(name = "fax")
    @ForeignKey(name = "CICR_FAX_ADD_CI_FK", inverseName = "CICR_FAX_ADD_PHONE_FK")
    public List<PhoneNumberCR> getFaxAddsAndMods() {
        return faxAddsAndMods;
    }

    /**
     * @return fax list
     */
    @OneToMany
    @Cascade(value = {org.hibernate.annotations.CascadeType.ALL,
            org.hibernate.annotations.CascadeType.DELETE_ORPHAN }
    )
    @JoinTable(
            name = "cicr_fax_drops",
            joinColumns = @JoinColumn(name = "cicr_id"),
            inverseJoinColumns = @JoinColumn(name = "phone_id")
    )
    @IndexColumn(name = "idx")
    @Column(name = "fax")
    @ForeignKey(name = "CICR_FAX_DROP_CI_FK", inverseName = "CICR_FAX_DROP_PHONE_FK")
    public List<PhoneNumberCR> getFaxDrops() {
        return faxDrops;
    }

    /**
     * @return phone list
     */
    @OneToMany
    @Cascade(value = {org.hibernate.annotations.CascadeType.ALL,
            org.hibernate.annotations.CascadeType.DELETE_ORPHAN }
    )
    @JoinTable(
            name = "cicr_phone_adds",
            joinColumns = @JoinColumn(name = "cicr_id"),
            inverseJoinColumns = @JoinColumn(name = "phone_id")
    )
    @IndexColumn(name = "idx")
    @Column(name = "phone")
    @ForeignKey(name = "CICR_PHONE_ADD_CI_FK", inverseName = "CICR_PHONE_ADD_PHONE_FK")
    public List<PhoneNumberCR> getPhoneAddsAndMods() {
        return phoneAddsAndMods;
    }

    /**
     * @return phone list
     */
    @OneToMany
    @Cascade(value = {org.hibernate.annotations.CascadeType.ALL,
            org.hibernate.annotations.CascadeType.DELETE_ORPHAN }
    )
    @JoinTable(
            name = "cicr_phone_drops",
            joinColumns = @JoinColumn(name = "cicr_id"),
            inverseJoinColumns = @JoinColumn(name = "phone_id")
    )
    @IndexColumn(name = "idx")
    @Column(name = "phone")
    @ForeignKey(name = "CICR_PHONE_DROPS_CI_FK", inverseName = "CICR_PHONE_DROPS_PHONE_FK")
    public List<PhoneNumberCR> getPhoneDrops() {
        return phoneDrops;
    }

    /**
     * @return list of urls
     */
    @OneToMany
    @Cascade(value = {org.hibernate.annotations.CascadeType.ALL,
            org.hibernate.annotations.CascadeType.DELETE_ORPHAN }
    )
    @JoinTable(
            name = "cicr_url_adds",
            joinColumns = @JoinColumn(name = "cicr_id"),
            inverseJoinColumns = @JoinColumn(name = "url_id")
    )
    @IndexColumn(name = "idx")
    @Column(name = "url")
    @ForeignKey(name = "CICR_URL_ADDS_CI_FK", inverseName = "CICR_URL_ADDS_URL_FK")
    public List<URLCR> getUrlAddsAndMods() {
        return urlAddsAndMods;
    }

    /**
     * @return list of urls
     */
    @OneToMany
    @Cascade(value = {org.hibernate.annotations.CascadeType.ALL,
            org.hibernate.annotations.CascadeType.DELETE_ORPHAN }
    )
    @JoinTable(
            name = "cicr_url_drops",
            joinColumns = @JoinColumn(name = "cicr_id"),
            inverseJoinColumns = @JoinColumn(name = "url_id")
    )
    @IndexColumn(name = "idx")
    @Column(name = "url")
    @ForeignKey(name = "CICR_URL_DROPS_CI_FK", inverseName = "CICR_URL_DROPS_URL_FK")
    public List<URLCR> getUrlDrops() {
        return urlDrops;
    }
    
    private boolean clearTitle;

    /**
     * @return true when title is meant to become null
     */
    public boolean isClearTitle() {
        return clearTitle;
    }

    private void setClearTitle(boolean clearTitle) {
        this.clearTitle = clearTitle;
    }

    /**
     * Use to set the value of title to null since, setTitle(null) implies there is no
     * change to title.
     */
    @Transient
    public void clearPropertyTitle() {
        this.setClearTitle(true);
        setTitle(null);
    }
}
