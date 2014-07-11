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

import com.fiveamsolutions.nci.commons.audit.Auditable;
import com.fiveamsolutions.nci.commons.search.Searchable;
import gov.nih.nci.po.util.PhoneOrEmailRequiredValidator;
import gov.nih.nci.po.util.PoServiceUtil;
import gov.nih.nci.security.authorization.domainobjects.User;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CollectionOfElements;
import org.hibernate.annotations.Columns;
import org.hibernate.annotations.ForeignKey;
import org.hibernate.annotations.Index;
import org.hibernate.annotations.IndexColumn;
import org.hibernate.annotations.Where;
import org.hibernate.validator.Valid;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Persons.
 *
 * @xsnapshot.snapshot-class name="iso" tostring="none"
 *      class="gov.nih.nci.services.person.PersonDTO"
 *      model-extends="gov.nih.nci.po.data.bo.AbstractPerson"
 *      generate-helper-methods="false"
 *      serial-version-uid="1L"
 */
@javax.persistence.Entity
@PhoneOrEmailRequiredValidator.PhoneOrEmailRequired
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public class Person extends AbstractPerson implements Auditable, CuratableEntity<Person, PersonCR>, Entity {
    private static final long serialVersionUID = 1L;
    private static final String NOT_NULLIFIED_CLAUSE = "status <> 'NULLIFIED'";
    private static final String PLAYER_MAPPING = "player";
    private static final String INDEX_NAME = "idx";
    private static final String JOIN_COLUMN = "person_id";
    private Person duplicateOf;
    private Set<PersonCR> changeRequests = new HashSet<PersonCR>();

    private Set<OrganizationalContact> organizationalContacts = new HashSet<OrganizationalContact>();
    private Set<ClinicalResearchStaff> clinicalResearchStaff = new HashSet<ClinicalResearchStaff>();
    private Set<HealthCareProvider> healthCareProviders = new HashSet<HealthCareProvider>();
    private Set<IdentifiedPerson> identifiedPersons = new HashSet<IdentifiedPerson>();

    private List<Comment> comments = new ArrayList<Comment>();
    private User createdBy;

     /**
     * Create a new, empty person.
     */
    public Person() {
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
            name = "person_email",
            joinColumns = @JoinColumn(name = JOIN_COLUMN),
            inverseJoinColumns = @JoinColumn(name = "email_id")
    )
    @IndexColumn(name = INDEX_NAME)
    @ForeignKey(name = "PER_EMAIL_FK", inverseName = "EMAIL_PER_FK")
    @Valid
    @Override
    @Searchable(nested = true)    
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
            name = "person_fax",
            joinColumns = @JoinColumn(name = JOIN_COLUMN),
            inverseJoinColumns = @JoinColumn(name = "fax_id")
    )
    @IndexColumn(name = INDEX_NAME)
    @Column(name = "fax")
    @ForeignKey(name = "PER_FAX_FK", inverseName = "FAX_PER_FK")
    @Valid
    @Override
    @Searchable(nested = true)
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
            name = "person_phone",
            joinColumns = @JoinColumn(name = JOIN_COLUMN),
            inverseJoinColumns = @JoinColumn(name = "phone_id")
    )
    @IndexColumn(name = INDEX_NAME)
    @Column(name = "phone")
    @ForeignKey(name = "PER_PHONE_FK", inverseName = "PHONE_PER_FK")
    @Valid
    @Override
    @Searchable(nested = true)
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
            name = "person_url",
            joinColumns = @JoinColumn(name = JOIN_COLUMN),
            inverseJoinColumns = @JoinColumn(name = "url_id")
    )
    @IndexColumn(name = INDEX_NAME)
    @Column(name = "url")
    @ForeignKey(name = "PER_URL_FK", inverseName = "URL_PER_FK")
    @Valid
    @Override
    @Searchable(nested = true)
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
            name = "person_tty",
            joinColumns = @JoinColumn(name = JOIN_COLUMN),
            inverseJoinColumns = @JoinColumn(name = "tty_id")
    )
    @IndexColumn(name = INDEX_NAME)
    @Column(name = "tty")
    @ForeignKey(name = "PER_TTY_FK", inverseName = "TTY_PER_FK")
    @Valid
    @Override
    @Searchable(nested = true)
    public List<PhoneNumber> getTty() {
        return super.getTty();
    }

    /**
     * {@inheritDoc}
     */
    @Enumerated(EnumType.STRING)
    @CollectionOfElements
    @JoinTable(
            name = "person_ethnicgroup",
            joinColumns = @JoinColumn(name = JOIN_COLUMN)
    )
    @ForeignKey(name = "PER_EG_FK")
    @Columns(columns = {
            @Column(name = "ETHNIC_GROUP")
    })
    @Override
    public Set<PersonEthnicGroup> getEthnicGroupCode() {
        return super.getEthnicGroupCode();
    }

    /**
     * {@inheritDoc}
     */
    @Enumerated(EnumType.STRING)
    @CollectionOfElements
    @JoinTable(
            name = "person_race",
            joinColumns = @JoinColumn(name = JOIN_COLUMN)
    )
    @ForeignKey(name = "PER_RACE_FK")
    @Columns(columns = {
            @Column(name = "RACE")
    })
    @Override
    public Set<PersonRace> getRaceCode() {
        return super.getRaceCode();
    }

    /**
     * @param per the Person for which this is a duplicate
     */
    public void setDuplicateOf(Person per) {
        this.duplicateOf = per;
    }

    /**
     * {@inheritDoc}
     */
    @ManyToOne(optional = true)
    @JoinColumn(name = "duplicate_of", nullable = true)
    @Index(name = "person_duplicateof_idx")
    @ForeignKey(name = "PERSON_DUPLICATE_PERSON_FK")
    public Person getDuplicateOf() {
        return this.duplicateOf;
    }

    /**
     * @return associated CRs
     */
    @OneToMany(mappedBy = "target")
    @Where(clause = "processed = 'false'")
    public Set<PersonCR> getChangeRequests() {
        return changeRequests;
    }

    @SuppressWarnings("unused")
    private void setChangeRequests(Set<PersonCR> changeRequests) {
        this.changeRequests = changeRequests;
    }

    /**
     * @return clinicalResearchStaff.
     */
    @OneToMany(mappedBy = PLAYER_MAPPING)
    @Where(clause = NOT_NULLIFIED_CLAUSE)
    @Searchable(nested = true)
    public Set<ClinicalResearchStaff> getClinicalResearchStaff() {
        return clinicalResearchStaff;
    }

    /**
     * @param clinicalResearchStaff clinicalResearchStaff.
     */
    @SuppressWarnings("unused")
    private void setClinicalResearchStaff(Set<ClinicalResearchStaff> clinicalResearchStaff) {
        this.clinicalResearchStaff = clinicalResearchStaff;
    }

    /**
     * @return healthCareProviders.
     */
    @OneToMany(mappedBy = PLAYER_MAPPING)
    @Where(clause = NOT_NULLIFIED_CLAUSE)
    @Searchable(nested = true)
    public Set<HealthCareProvider> getHealthCareProviders() {
        return healthCareProviders;
    }

    /**
     * @param healthCareProviders healthCareProviders.
     */
    @SuppressWarnings("unused")
    private void setHealthCareProviders(Set<HealthCareProvider> healthCareProviders) {
        this.healthCareProviders = healthCareProviders;
    }

    /**
     * @return organizationalContacts.
     */
    @OneToMany(mappedBy = PLAYER_MAPPING)
    @Where(clause = NOT_NULLIFIED_CLAUSE)
    @Searchable(nested = true)
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
     * @return identifiedPersons.
     */
    @OneToMany(mappedBy = PLAYER_MAPPING)
    @Where(clause = NOT_NULLIFIED_CLAUSE)
    @Searchable(nested = true)
    public Set<IdentifiedPerson> getIdentifiedPersons() {
        return identifiedPersons;
    }

    /**
     * @param identifiedPersons identifiedPersons.
     */
    @SuppressWarnings("unused")
    private void setIdentifiedPersons(Set<IdentifiedPerson> identifiedPersons) {
        this.identifiedPersons = identifiedPersons;
    }

    /**
     * {@inheritDoc}
     */
    @OneToMany
    @Cascade(value = {org.hibernate.annotations.CascadeType.ALL,
                      org.hibernate.annotations.CascadeType.DELETE_ORPHAN }
    )
    @JoinTable(
            name = "person_comment",
            joinColumns = @JoinColumn(name = JOIN_COLUMN),
            inverseJoinColumns = @JoinColumn(name = "comment_id")
    )    
    @IndexColumn(name = INDEX_NAME)    
    public List<Comment> getComments() {
        return comments;
    }

    /**
     * @param comments
     *            the comments to set
     */
    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
    
    /**
     * @return the user
     */
    @ManyToOne(optional = true)
    @JoinColumn(name = "created_by_id", nullable = true)
    @ForeignKey(name = "person_createdby_user_fk")
    public User getCreatedBy() {
        return createdBy;
    }

    /**
     * @param user
     *            the user to set
     */
    public void setCreatedBy(User user) {
        this.createdBy = user;
    }
    
    /**
     * @return user name
     */
    @Transient
    public String getCreatedByUserName() {
        return PoServiceUtil.getUserName(createdBy);
    }

}
