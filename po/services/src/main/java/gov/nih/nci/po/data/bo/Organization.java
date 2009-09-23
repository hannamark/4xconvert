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

import gov.nih.nci.po.util.NotEmpty;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.ForeignKey;
import org.hibernate.annotations.Index;
import org.hibernate.annotations.IndexColumn;
import org.hibernate.annotations.Where;
import org.hibernate.validator.Valid;

import com.fiveamsolutions.nci.commons.audit.Auditable;
import com.fiveamsolutions.nci.commons.search.Searchable;

/**
 * Organizations.
 *
 * @xsnapshot.snapshot-class name="iso" tostring="none"
 *      class="gov.nih.nci.services.organization.OrganizationDTO"
 *      model-extends="gov.nih.nci.po.data.bo.AbstractOrganization"
 *      generate-helper-methods="false"
 *      serial-version-uid="1L"
 */
@Entity
@SuppressWarnings({ "PMD.AvoidDuplicateLiterals", "PMD.UselessOverridingMethod" })
public class Organization extends AbstractOrganization
        implements Auditable, CuratableEntity<Organization, OrganizationCR> {
    private static final String VALUE = "value";
    private static final long serialVersionUID = 1L;
    private Organization duplicateOf;
    private Set<OrganizationCR> changeRequests = new HashSet<OrganizationCR>();
    private Date statusDate;

    private Set<ResearchOrganization> researchOrganizations = new HashSet<ResearchOrganization>();
    private Set<OversightCommittee> oversightCommittees = new HashSet<OversightCommittee>();
    private Set<HealthCareFacility> healthCareFacilities = new HashSet<HealthCareFacility>();
    private Set<IdentifiedOrganization> identifiedOrganizations = new HashSet<IdentifiedOrganization>();
    private Set<OrganizationalContact> organizationalContacts = new HashSet<OrganizationalContact>();

    private String comments;

    /**
     * Create a new, empty org.
     */
    public Organization() {
        super();
    }

    /**
     * {@inheritDoc}
     */
    @OneToMany
    @Cascade(value = {org.hibernate.annotations.CascadeType.ALL,
                      org.hibernate.annotations.CascadeType.DELETE_ORPHAN }
    )
    @JoinTable(
            name = "organization_email",
            joinColumns = @JoinColumn(name = "organization_id"),
            inverseJoinColumns = @JoinColumn(name = "email_id")
    )
    @IndexColumn(name = "idx")
    @ForeignKey(name = "ORG_EMAIL_FK", inverseName = "EMAIL_ORG_FK")
    @Valid
    @Override
    @Searchable(fields = { VALUE }, matchMode = Searchable.MATCH_MODE_CONTAINS)
    @NotEmpty(message = "{validator.notEmpty.collection}")
    public List<Email> getEmail() {
        return super.getEmail();
    }

    /**
     * {@inheritDoc}
     */
    @OneToMany
    @Cascade(value = {org.hibernate.annotations.CascadeType.ALL,
            org.hibernate.annotations.CascadeType.DELETE_ORPHAN }
    )
    @JoinTable(
            name = "organization_fax",
            joinColumns = @JoinColumn(name = "organization_id"),
            inverseJoinColumns = @JoinColumn(name = "fax_id")
    )
    @IndexColumn(name = "idx")
    @Column(name = "fax")
    @ForeignKey(name = "ORG_FAX_FK", inverseName = "FAX_ORG_FK")
    @Valid
    @Override
    @Searchable(fields = { VALUE }, matchMode = Searchable.MATCH_MODE_CONTAINS)
    public List<PhoneNumber> getFax() {
        return super.getFax();
    }

    /**
     * {@inheritDoc}
     */
    @OneToMany
    @Cascade(value = {org.hibernate.annotations.CascadeType.ALL,
            org.hibernate.annotations.CascadeType.DELETE_ORPHAN }
    )
    @JoinTable(
            name = "organization_phone",
            joinColumns = @JoinColumn(name = "organization_id"),
            inverseJoinColumns = @JoinColumn(name = "phone_id")
    )
    @IndexColumn(name = "idx")
    @Column(name = "phone")
    @ForeignKey(name = "ORG_PHONE_FK", inverseName = "PHONE_ORG_FK")
    @Override
    @Valid
    @Searchable(fields = { VALUE }, matchMode = Searchable.MATCH_MODE_CONTAINS)
    public List<PhoneNumber> getPhone() {
        return super.getPhone();
    }

    /**
     * {@inheritDoc}
     */
    @OneToMany
    @Cascade(value = {org.hibernate.annotations.CascadeType.ALL,
            org.hibernate.annotations.CascadeType.DELETE_ORPHAN }
    )
    @JoinTable(
            name = "organization_url",
            joinColumns = @JoinColumn(name = "organization_id"),
            inverseJoinColumns = @JoinColumn(name = "url_id")
    )
    @IndexColumn(name = "idx")
    @Column(name = "url")
    @ForeignKey(name = "ORG_URL_FK", inverseName = "URL_ORG_FK")
    @Override
    @Valid
    @Searchable(fields = { VALUE }, matchMode = Searchable.MATCH_MODE_CONTAINS)
    public List<URL> getUrl() {
        return super.getUrl();
    }

    /**
     * {@inheritDoc}
     */
    @OneToMany
    @Cascade(value = {org.hibernate.annotations.CascadeType.ALL,
            org.hibernate.annotations.CascadeType.DELETE_ORPHAN }
    )
    @JoinTable(
            name = "organization_tty",
            joinColumns = @JoinColumn(name = "organization_id"),
            inverseJoinColumns = @JoinColumn(name = "tty_id")
    )
    @IndexColumn(name = "idx")
    @Column(name = "tty")
    @ForeignKey(name = "ORG_TTY_FK", inverseName = "TTY_ORG_FK")
    @Valid
    @Override
    @Searchable(fields = { VALUE }, matchMode = Searchable.MATCH_MODE_CONTAINS)
    public List<PhoneNumber> getTty() {
        return super.getTty();
    }

    /**
     * @param org the organization for which this is a duplicate
     */
    public void setDuplicateOf(Organization org) {
        this.duplicateOf = org;
    }

    /**
     * @return organization
     */
    @ManyToOne(optional = true)
    @JoinColumn(name = "duplicate_of", nullable = true)
    @Index(name = "organization_duplicateof_idx")
    @ForeignKey(name = "ORG_DUPLICATE_ORG_FK")
    public Organization getDuplicateOf() {
        return this.duplicateOf;
    }

    /**
     * @return associated CRs
     */
    @OneToMany(mappedBy = "target")
    @Where(clause = "processed = 'false'")
    public Set<OrganizationCR> getChangeRequests() {
        return changeRequests;
    }

    @SuppressWarnings("unused")
    private void setChangeRequests(Set<OrganizationCR> changeRequests) {
        this.changeRequests = changeRequests;
    }

    /**
     * @return the statusDate
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


    /**
     * @return healthCareFacilities.
     */
    @OneToMany(mappedBy = "player")
    @Where(clause = "status <> 'NULLIFIED'")
    public Set<HealthCareFacility> getHealthCareFacilities() {
        return healthCareFacilities;
    }

    /**
     * @param healthCareFacility healthCareFacility.
     */
    @SuppressWarnings("unused")
    private void setHealthCareFacilities(Set<HealthCareFacility> healthCareFacilities) {
        this.healthCareFacilities = healthCareFacilities;
    }

    /**
     * @return identifiedOrganizations.
     */
    @OneToMany(mappedBy = "player")
    @Where(clause = "status <> 'NULLIFIED'")
    public Set<IdentifiedOrganization> getIdentifiedOrganizations() {
        return identifiedOrganizations;
    }

    /**
     * @param identifiedOrganizations.
     */
    @SuppressWarnings("unused")
    private void setIdentifiedOrganizations(Set<IdentifiedOrganization> identifiedOrganizations) {
        this.identifiedOrganizations = identifiedOrganizations;
    }

    /**
     * @return oversightCommittee
     */
    @OneToMany(mappedBy = "player")
    @Where(clause = "status <> 'NULLIFIED'")
    public Set<OversightCommittee> getOversightCommittees() {
        return oversightCommittees;
    }

    /**
     * @param oversightCommittee
     */
    @SuppressWarnings("unused")
    private void setOversightCommittees(Set<OversightCommittee> oversightCommittees) {
        this.oversightCommittees = oversightCommittees;
    }

    /**
     * @return researchOrganization.
     */
    @OneToMany(mappedBy = "player")
    @Where(clause = "status <> 'NULLIFIED'")
    public Set<ResearchOrganization> getResearchOrganizations() {
        return researchOrganizations;
    }

    /**
     * @return researchOrganization.
     */
    @SuppressWarnings("unused")
    private void setResearchOrganizations(Set<ResearchOrganization> researchOrganizations) {
        this.researchOrganizations = researchOrganizations;
    }

    /**
     * @return organizationalContacts.
     */
    @OneToMany(mappedBy = "scoper")
    @Where(clause = "status <> 'NULLIFIED' and person_id IS NULL")
    public Set<OrganizationalContact> getOrganizationalContacts() {
        return organizationalContacts;
    }

    /**
     * @param organizationalContacts organizationalContacts.
     */
    @SuppressWarnings("unused")
    private void setOrganizationalContacts(Set<OrganizationalContact> organizationalContacts) {
        this.organizationalContacts = organizationalContacts;
    }

    /**
     * @return comments
     */
    @Lob
    public String getComments() {
        return comments;
    }

    /**
     * @param comments comments
     */
    public void setComments(String comments) {
        this.comments = comments;
    }
    
    /**
     * Checks if HCF or RO associated w/ this org are from ctep.
     * @return boolean
     */
    @Transient
    public boolean isAssociatedWithCtepRoles() {
        for (ResearchOrganization ro : getResearchOrganizations()) {
            if (ro.isCtepOwned()) {
                return true;
            }
        }
         
        for (HealthCareFacility hcf : getHealthCareFacilities()) {
            if (hcf.isCtepOwned()) {
                return true;
            }
        }    
        return false;
    }
}
