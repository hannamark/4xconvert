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

import gov.nih.nci.services.correlation.PersonRoleDTO;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.ForeignKey;
import org.hibernate.annotations.IndexColumn;
import org.hibernate.validator.NotNull;
import org.hibernate.validator.Valid;

import com.fiveamsolutions.nci.commons.data.persistent.PersistentObject;

/**
 * Base class for all person to org roles.
 * @author Scott Miller
 * @xsnapshot.snapshot-class name="iso" tostring="none" generate-helper-methods="false"
 *      class="gov.nih.nci.services.correlation.PersonRoleDTO"
 *      extends="gov.nih.nci.services.correlation.BasePersonRoleDTO"
 */
@MappedSuperclass
@SuppressWarnings({"PMD.AvoidDuplicateLiterals", "PMD.AbstractNaming" })
public abstract class PersonRole implements PersistentObject, Contactable, Root<PersonRoleCR, PersonRoleDTO> {

    private static final long serialVersionUID = 1L;

    private Long id;

    private Person person;
    private Organization organization;

    private Set<Address> postalAddresses;
    private List<Email> email = new ArrayList<Email>();
    private List<PhoneNumber> fax = new ArrayList<PhoneNumber>(1);
    private List<PhoneNumber> phone = new ArrayList<PhoneNumber>(1);
    private List<URL> url = new ArrayList<URL>(1);
    private List<PhoneNumber> tty = new ArrayList<PhoneNumber>(1);
    private RoleStatus status;
    private Date statusDate;

    /**
     * @return the id
     * @xsnapshot.property match="iso"
     *                     type="gov.nih.nci.coppa.iso.Ii" name="identifier"
     *                     snapshot-transformer="gov.nih.nci.po.data.convert.IdConverter"
     *                     model-transformer="gov.nih.nci.po.data.convert.IiConverter"
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return this.id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the person
     * @xsnapshot.property match="iso" type="gov.nih.nci.coppa.iso.Ii" name="personIdentifier"
     *            snapshot-transformer="gov.nih.nci.po.data.convert.PersistentObjectConverter$PersistentPersonConverter"
     *            model-transformer="gov.nih.nci.po.data.convert.IiConverter"
     */
    @ManyToOne
    @NotNull
    @JoinColumn(name = "person_id")
    @ForeignKey(name = "personrole_per_fkey")
    public Person getPerson() {
        return this.person;
    }

    /**
     * @param person the person to set
     */
    public void setPerson(Person person) {
        this.person = person;
    }

    /**
     * @return the organization
     * @xsnapshot.property match="iso" type="gov.nih.nci.coppa.iso.Ii" name="organizationIdentifier"
     *            snapshot-transformer="gov.nih.nci.po.data.convert.PersistentObjectConverter$PersistentOrgConverter"
     *            model-transformer="gov.nih.nci.po.data.convert.IiConverter"
     */
    @ManyToOne
    @NotNull
    @JoinColumn(name = "organization_id")
    @ForeignKey(name = "personrole_org_fkey")
    public Organization getOrganization() {
        return this.organization;
    }

    /**
     * @param organization the organization to set
     */
    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    /**
     * @return the postalAddresses
     * todo add xsnapshot tags
     */
    @OneToMany
    @Cascade(value = {org.hibernate.annotations.CascadeType.ALL,
                      org.hibernate.annotations.CascadeType.DELETE_ORPHAN }
    )
    @JoinTable(
            name = "person_role_address",
            joinColumns = @JoinColumn(name = "person_role_id"),
            inverseJoinColumns = @JoinColumn(name = "address_id")
    )
    @IndexColumn(name = "idx")
    @ForeignKey(name = "PER_ROLE_ADDRESS_FK", inverseName = "ADDRESS_PER_ROLE_FK")
    @Valid
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
     * @return the email
     */
    @OneToMany
    @Cascade(value = {org.hibernate.annotations.CascadeType.ALL,
                      org.hibernate.annotations.CascadeType.DELETE_ORPHAN }
    )
    @JoinTable(
            name = "person_role_email",
            joinColumns = @JoinColumn(name = "person_role_id"),
            inverseJoinColumns = @JoinColumn(name = "email_id")
    )
    @IndexColumn(name = "idx")
    @ForeignKey(name = "PER_ROLE_EMAIL_FK", inverseName = "EMAIL_PER_ROLE_FK")
    @Valid
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
     * @return the fax
     */
    @OneToMany
    @Cascade(value = {org.hibernate.annotations.CascadeType.ALL,
                      org.hibernate.annotations.CascadeType.DELETE_ORPHAN }
    )
    @JoinTable(
            name = "person_role_fax",
            joinColumns = @JoinColumn(name = "person_role_id"),
            inverseJoinColumns = @JoinColumn(name = "fax_id")
    )
    @IndexColumn(name = "idx")
    @ForeignKey(name = "PER_ROLE_FAX_FK", inverseName = "FAX_PER_ROLE_FK")
    @Valid
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
    @OneToMany
    @Cascade(value = {org.hibernate.annotations.CascadeType.ALL,
                      org.hibernate.annotations.CascadeType.DELETE_ORPHAN }
    )
    @JoinTable(
            name = "person_role_phone",
            joinColumns = @JoinColumn(name = "person_role_id"),
            inverseJoinColumns = @JoinColumn(name = "phone_id")
    )
    @IndexColumn(name = "idx")
    @ForeignKey(name = "PER_ROLE_PHONE_FK", inverseName = "PHONE_PER_ROLE_FK")
    @Valid
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
     * @return the url
     */
    @OneToMany
    @Cascade(value = {org.hibernate.annotations.CascadeType.ALL,
                      org.hibernate.annotations.CascadeType.DELETE_ORPHAN }
    )
    @JoinTable(
            name = "person_role_url",
            joinColumns = @JoinColumn(name = "person_role_id"),
            inverseJoinColumns = @JoinColumn(name = "url_id")
    )
    @IndexColumn(name = "idx")
    @ForeignKey(name = "PER_ROLE_URL_FK", inverseName = "URL_PER_ROLE_FK")
    @Valid
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
     * @return the tty
     */
    @OneToMany
    @Cascade(value = {org.hibernate.annotations.CascadeType.ALL,
                      org.hibernate.annotations.CascadeType.DELETE_ORPHAN }
    )
    @JoinTable(
            name = "person_role_tty",
            joinColumns = @JoinColumn(name = "person_role_id"),
            inverseJoinColumns = @JoinColumn(name = "tty_id")
    )
    @IndexColumn(name = "idx")
    @ForeignKey(name = "PER_ROLE_TTY_FK", inverseName = "TTY_PER_ROLE_FK")
    @Valid
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
     * @return the status
     * @xsnapshot.property match="iso" type="gov.nih.nci.coppa.iso.Cd"
     *                     snapshot-transformer="gov.nih.nci.po.data.convert.RoleStatusConverter"
     *                     model-transformer="gov.nih.nci.po.data.convert.CdConverter"
     */
    @Enumerated(EnumType.STRING)
    @NotNull
    public RoleStatus getStatus() {
        return this.status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(RoleStatus status) {
        this.status = status;
    }

    /**
     * @return the statusDate
     * @xsnapshot.property match="iso" type="gov.nih.nci.coppa.iso.Ivl"
     *                     name="statusDateRange"
     *                     snapshot-transformer="gov.nih.nci.po.data.convert.StatusDateConverter"
     *                     model-transformer="gov.nih.nci.po.data.convert.IvlTsConverter"
     */
    @Temporal(TemporalType.TIMESTAMP)
    public Date getStatusDate() {
        return this.statusDate;
    }

    /**
     * @param statusDate the statusDate to set
     */
    public void setStatusDate(Date statusDate) {
        this.statusDate = statusDate;
    }
}