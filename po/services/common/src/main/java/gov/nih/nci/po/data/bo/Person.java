/**
 * The software subject to this notice and license includes both human readable
 * source code form and machine readable, binary, object code form. The caarray-app
 * Software was developed in conjunction with the National Cancer Institute
 * (NCI) by NCI employees and 5AM Solutions, Inc. (5AM). To the extent
 * government employees are authors, any rights in such works shall be subject
 * to Title 17 of the United States Code, section 105.
 *
 * This caarray-app Software License (the License) is between NCI and You. You (or
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
 * its rights in the caarray-app Software to (i) use, install, access, operate,
 * execute, copy, modify, translate, market, publicly display, publicly perform,
 * and prepare derivative works of the caarray-app Software; (ii) distribute and
 * have distributed to and by third parties the caarray-app Software and any
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

import gov.nih.nci.po.audit.Auditable;
import gov.nih.nci.po.data.bo.alternate.ProviderPerson;
import gov.nih.nci.po.data.common.CurationStatus;
import gov.nih.nci.po.data.common.PersonType;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.hibernate.annotations.ForeignKey;
import org.hibernate.annotations.Formula;
import org.hibernate.annotations.Index;
import org.hibernate.annotations.Sort;
import org.hibernate.annotations.SortType;
import org.hibernate.annotations.Where;
import org.hibernate.validator.NotEmpty;
import org.hibernate.validator.NotNull;
import org.hibernate.validator.Valid;


/**
 * Represents a single, natural person.
 */
@Entity
@SuppressWarnings({"PMD.AvoidDuplicateLiterals", "PMD.TooManyFields", "PMD.UselessOverridingMethod" })
public class Person implements Auditable, Curatable<Person> {
    private static final long serialVersionUID = 7515315163406642400L;
    private static final int SHORT_COL_LENGTH = 10;
    private static final int LONG_COL_LENGTH = 50;
    private Long id;
    private String firstName;
    private String lastName;
    private String middleName;
    private String suffix;
    private String prefix;
    private Date dateOfBirth;
    private ContactInfo preferredContactInfo;
    private List<ContactInfo> contactInfos = new ArrayList<ContactInfo>(1);
    private List<Degree> degrees = new ArrayList<Degree>();
    private List<Certificate> certificates = new ArrayList<Certificate>();
    private List<License> licenses = new ArrayList<License>();
    private List<Speciality> specialities = new ArrayList<Speciality>();
    private Set<ProviderPerson> altIds = new HashSet<ProviderPerson>();
    private CurationStatus curationStatus;
    private CurationStatus priorCurationStatus;
    private Person duplicateOf;
    private Set<PersonType> types = new HashSet<PersonType>();
    

    /**
     * @param preferredContactInfo primary contact information
     */
    public Person(ContactInfo preferredContactInfo) {
        this.preferredContactInfo = preferredContactInfo;
        this.contactInfos.add(this.preferredContactInfo);
    }

    /**
     * Create a new, empty person.
     */
    public Person() {
        this(new ContactInfo());
    }

   /**
     * @return database id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    /**
     * @param id database id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return date of birth
     */
    @Temporal(value = TemporalType.TIMESTAMP)
    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    /**
     * @return first (given) name
     */
    @Transient
    @Length(max = LONG_COL_LENGTH)
    public String getFirstName() {
        return firstName;
    }

    /**
     * @return last (family) name
     */
    @Transient
    @Length(max = LONG_COL_LENGTH)
    public String getLastName() {
        return lastName;
    }

    /**
     * @return middle initial
     */
    @Length(max = LONG_COL_LENGTH)
    public String getMiddleName() {
        return middleName;
    }

    /**
     * @return name prefix
     */
    @Length(max = SHORT_COL_LENGTH)
    public String getPrefix() {
        return prefix;
    }

    /**
     * @return name suffix
     */
    @Length(max = SHORT_COL_LENGTH)
    public String getSuffix() {
        return suffix;
    }

    /**
     * @param dateOfBirth date of birth
     */
    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    /**
     * @param firstName first name
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * @param lastName last name
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * @param middleName middle initial
     */
    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    /**
     * @param prefix prefix
     */
    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    /**
     * @param suffix suffix
     */
    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }
    
    /**
     * @return certificates
     */
    @OneToMany(mappedBy = "person")
    @Sort(type = SortType.NATURAL)
    public List<Certificate> getCertificates() {
        return certificates;
    }

    /**
     * @return list of contact info
     */
    @OneToMany(mappedBy = "person")
    @Sort(type = SortType.NATURAL)
    public List<ContactInfo> getContactInfos() {
        return contactInfos;
    }

    /**
     * @return degrees
     */
    @OneToMany(mappedBy = "person")
    @Sort(type = SortType.NATURAL)
    public List<Degree> getDegrees() {
        return degrees;
    }

    /**
     * @return first (given) name
     */
    @Override
    @NotEmpty
    public String getFirstName() {
        return super.getFirstName();
    }

    /**
     * @return last (family) name
     */
    @Override
    @NotEmpty
    public String getLastName() {
        return super.getLastName();
    }

    /**
     * @return licenses
     */
    @OneToMany(mappedBy = "person")
    @Sort(type = SortType.NATURAL)
    public List<License> getLicenses() {
        return licenses;
    }

    /**
     * @return preferred contact info
     */
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "preferred_contact_info_id")
    @NotNull
    @ForeignKey(name = "PERSON_CONTACT_INFO_FK")
    @Valid
    public ContactInfo getPreferredContactInfo() {
        return preferredContactInfo;
    }

    /**
     * @return specialities
     */
    @OneToMany(mappedBy = "person")
    @Sort(type = SortType.NATURAL)
    public List<Speciality> getSpecialities() {
        return specialities;
    }

    /**
     * @param preferredContactInfo preferred contact info
     */
    public void setPreferredContactInfo(ContactInfo preferredContactInfo) {
        this.preferredContactInfo = preferredContactInfo;
    }

    @SuppressWarnings("unused")
    private void setCertificates(List<Certificate> certificates) {
        this.certificates = certificates;
    }

    @SuppressWarnings("unused")
    private void setContactInfos(List<ContactInfo> contactInfos) {
        this.contactInfos = contactInfos;
    }

    @SuppressWarnings("unused")
    private void setDegrees(List<Degree> degrees) {
        this.degrees = degrees;
    }

    @SuppressWarnings("unused")
    private void setLicenses(List<License> licenses) {
        this.licenses = licenses;
    }

    @SuppressWarnings("unused")
    private void setSpecialities(List<Speciality> specialities) {
        this.specialities = specialities;
    }

    /**
     * @return the altIds
     */
    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL)
    public Set<ProviderPerson> getAltIds() {
        return altIds;
    }

    /**
     * @param altIds the altIds to set
     */
    public void setAltIds(Set<ProviderPerson> altIds) {
        this.altIds = altIds;
    }

    /**
     * @param newStatus the status of this person record
     */
    public void setCurationStatus(CurationStatus newStatus) {
        this.curationStatus = newStatus;
    }

    /**
     * {@inheritDoc}
     */
    @Enumerated(EnumType.STRING)
    @NotNull
    @Column(name = "STATUS")
    public CurationStatus getCurationStatus() {
        return this.curationStatus;
    }

    /**
     * {@inheritDoc}
     */
    @Formula("status")
    @SuppressWarnings("unused")
    private String getPriorAsString() {
        return null;
    }

    @SuppressWarnings("unused")
    private void setPriorAsString(String prior) {
        if (prior != null) {
            this.priorCurationStatus = CurationStatus.valueOf(prior);
        } else {
            this.priorCurationStatus = null;
        }
    }

    /**
     * @return the prior curation status
     */
    @Transient
    public CurationStatus getPriorCurationStatus() {
       return priorCurationStatus;
    }

    /**
     * @param per the Person for which this is a duplicate
     */
    public void setDuplicateOf(Person per) {
        if (this.getCurationStatus().equals(CurationStatus.REJECTED)
                || this.getCurationStatus().equals(CurationStatus.DEPRECATED)) {
            this.duplicateOf = per;
        }
    }

    /**
     * @return person
     */
    @ManyToOne(cascade = CascadeType.PERSIST, optional = true)
    @JoinColumn(name = "duplicate_of", nullable = true)
    @Index(name = "person_duplicateof_idx")
    @ForeignKey(name = "PERSON_DUPLICATE_PERSON_FK")
    public Person getDuplicateOf() {
        return this.duplicateOf;
    }


    /**
     * @return the types
     */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "PersonTypes",
            joinColumns = {@JoinColumn(name = "person_id") },
            inverseJoinColumns = @JoinColumn(name = "type_id")
    )
    @ForeignKey(name = "PER_TYPE_PER_FK", inverseName = "PER_TYPE_TYPE_FK")
    public Set<PersonType> getTypes() {
        return this.types;
    }

    /**
     * @param types person types
     */
    public void setTypes(Set<PersonType> types) {
        this.types = types;
    }

    /**
     * @return true if the types collection contains a PersonType.name equal to "Investigator"
     */
    @Transient
    public Boolean isInvestigator() {
        return CollectionUtils.exists(getTypes(), new Predicate() {
            public boolean evaluate(Object object) {
                return (object != null && PersonType.INVESTIGATOR.equals(((PersonType) object).getName()));
            }
        });
    }

    /**
     * @return true if the types collection contains a PersonType.name equal to "Investigator"
     */
    @Transient
    public Boolean getInvestigator() {
        return isInvestigator();
    }

    /**
     * @return associated CRs
     */
    @OneToMany(mappedBy = "person")
    public Set<PersonCR> getAllChangeRequests() {
        return allChangeRequests;
    }

    /**
     * @return associated unprocessed CRs
     */
    @OneToMany(mappedBy = "person")
    @Where(clause = "processedById is NULL")
    public Set<PersonCR> getUnprocessedChangeRequests() {
        return unprocessedChangeRequests;
    }

    @SuppressWarnings("unused")
    private void setAllChangeRequests(Set<PersonCR> changeRequests) {
        this.allChangeRequests = changeRequests;
    }

    @SuppressWarnings("unused")
    private void setUnprocessedChangeRequests(Set<PersonCR> unprocessedChangeRequests) {
        this.unprocessedChangeRequests = unprocessedChangeRequests;
    }

}
