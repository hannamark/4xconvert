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

import gov.nih.nci.po.data.bo.Person;
import gov.nih.nci.po.data.common.AbstractPerson;
import gov.nih.nci.po.data.common.PersonType;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.ForeignKey;
import org.hibernate.validator.NotNull;

/**
 * Person change request.
 */
@Entity
@SuppressWarnings({"PMD.UselessOverridingMethod", "PMD.AvoidDuplicateLiterals", "PMD.TooManyFields" })
public class PersonCR extends AbstractPerson {

    private static final long serialVersionUID = -677982281904821549L;
    private Person person;
    private ContactInfoCR preferredContactInfo;
    private Set<ContactInfoCR> contactInfoAddsAndMods = new HashSet<ContactInfoCR>();
    private Set<ContactInfoCR> contactInfoDrops = new HashSet<ContactInfoCR>();
    private Set<DegreeCR> degreeAddsAndMods = new HashSet<DegreeCR>();
    private Set<DegreeCR> degreeDrops = new HashSet<DegreeCR>();
    private Set<SpecialityCR> specialityAddsAndMods = new HashSet<SpecialityCR>();
    private Set<SpecialityCR> specialityDrops = new HashSet<SpecialityCR>();
    private Set<CertificateCR> certificateAddsAndMods = new HashSet<CertificateCR>();
    private Set<CertificateCR> certificateDrops = new HashSet<CertificateCR>();
    private Set<LicenseCR> licenseAddsAndMods = new HashSet<LicenseCR>();
    private Set<LicenseCR> licenseDrops = new HashSet<LicenseCR>();
    private Set<PersonType> typeAdds = new HashSet<PersonType>();
    private Set<PersonType> typeDrops = new HashSet<PersonType>();
    /*
     * Remote clients should not attempt to update the StatusInfo properties, all of this 
     * information is gathered by the server -- see POServiceBean modify*(CR, RemoteUserName)
     */
    private transient StatusInfo statusInfo;

    /**
     * @param personId id of person being changed
     */
    public PersonCR(long personId) {
        this.person = new Person();
        this.person.setId(personId);
    }

    /**
     * Default constructor.
     */
    public PersonCR() {
        // hibernate only
    }

    /**
     * @return the person
     */
    @ManyToOne
    @NotNull
    @ForeignKey(name = "PERSONCR_PERSON_FK")
    private Person getPerson() {
        return person;
    }

    /**
     * @param person The person we are changing.
     */
    private void setPerson(Person person) {
        this.person = person;
    }

    /**
     * Get the id of the person this change request is for.
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
     * {@inheritDoc}
     */
    @Override
    public String getFirstName() {
        return super.getFirstName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getLastName() {
        return super.getLastName();
    }

    /**
     * @return the degreeCRs
     */
    @OneToMany
    @Cascade(value = {org.hibernate.annotations.CascadeType.ALL,
            org.hibernate.annotations.CascadeType.DELETE_ORPHAN }
    )
    @JoinTable(
            name = "personcr_degree_adds",
            joinColumns = {@JoinColumn(name = "personcr_id") },
            inverseJoinColumns = {@JoinColumn(name = "degree_id") }
    )
    @ForeignKey(name = "PERSONCR_DEGREE_ADD_P_FK", inverseName = "PERSONCR_DEGREE_ADD_D_FK")
    public Set<DegreeCR> getDegreeAddsAndMods() {
        return degreeAddsAndMods;
    }

    @SuppressWarnings("unused")
    private void setDegreeAddsAndMods(Set<DegreeCR> degreeCRs) {
        this.degreeAddsAndMods = degreeCRs;
    }

    /**
     * @return the degreeCRs
     */
    @OneToMany
    @Cascade(value = {org.hibernate.annotations.CascadeType.ALL,
            org.hibernate.annotations.CascadeType.DELETE_ORPHAN }
    )
    @JoinTable(
            name = "personcr_degree_drops",
            joinColumns = {@JoinColumn(name = "personcr_id") },
            inverseJoinColumns = {@JoinColumn(name = "degree_id") }
    )
    @ForeignKey(name = "PERSONCR_DEGREE_DROP_P_FK", inverseName = "PERSONCR_DEGREE_DROP_D_FK")
    public Set<DegreeCR> getDegreeDrops() {
        return degreeDrops;
    }

    @SuppressWarnings("unused")
    private void setDegreeDrops(Set<DegreeCR> degreeCRs) {
        this.degreeDrops = degreeCRs;
    }

    /**
     * @return the specialityCRs
     */
    @OneToMany
    @Cascade(value = {org.hibernate.annotations.CascadeType.ALL,
            org.hibernate.annotations.CascadeType.DELETE_ORPHAN }
    )
    @JoinTable(
            name = "personcr_speciality_adds",
            joinColumns = {@JoinColumn(name = "personcr_id") },
            inverseJoinColumns = {@JoinColumn(name = "speciality_id") }
    )
    @ForeignKey(name = "PERSONCR_SPECIALITY_ADD_P_FK", inverseName = "PERSONCR_SPECIALITY_ADD_S_FK")
    public Set<SpecialityCR> getSpecialityAddsAndMods() {
        return specialityAddsAndMods;
    }

    @SuppressWarnings("unused")
    private void setSpecialityAddsAndMods(Set<SpecialityCR> specialityCRs) {
        this.specialityAddsAndMods = specialityCRs;
    }

    /**
     * @return the specialityCRs
     */
    @OneToMany
    @Cascade(value = {org.hibernate.annotations.CascadeType.ALL,
            org.hibernate.annotations.CascadeType.DELETE_ORPHAN }
    )
    @JoinTable(
            name = "personcr_speciality_drops",
            joinColumns = {@JoinColumn(name = "personcr_id") },
            inverseJoinColumns = {@JoinColumn(name = "speciality_id") }
    )
    @ForeignKey(name = "PERSONCR_SPECIALITY_DROP_P_FK", inverseName = "PERSONCR_SPECIALITY_DROP_S_FK")
    public Set<SpecialityCR> getSpecialityDrops() {
        return specialityDrops;
    }

    @SuppressWarnings("unused")
    private void setSpecialityDrops(Set<SpecialityCR> specialityCRs) {
        this.specialityDrops = specialityCRs;
    }

    /**
     * @return the certificateCRs
     */
    @OneToMany
    @Cascade(value = {org.hibernate.annotations.CascadeType.ALL,
            org.hibernate.annotations.CascadeType.DELETE_ORPHAN }
    )
    @JoinTable(
            name = "personcr_certificate_adds",
            joinColumns = {@JoinColumn(name = "personcr_id") },
            inverseJoinColumns = {@JoinColumn(name = "certificate_id") }
    )
    @ForeignKey(name = "PERSONCR_CERTIFICATE_ADD_P_FK", inverseName = "PERSONCR_CERTIFICATE_ADD_C_FK")
    public Set<CertificateCR> getCertificateAddsAndMods() {
        return certificateAddsAndMods;
    }

    @SuppressWarnings("unused")
    private void setCertificateAddsAndMods(Set<CertificateCR> certificateCRs) {
        this.certificateAddsAndMods = certificateCRs;
    }

    /**
     * @return the certificateCRs
     */
    @OneToMany
    @Cascade(value = {org.hibernate.annotations.CascadeType.ALL,
            org.hibernate.annotations.CascadeType.DELETE_ORPHAN }
    )
    @JoinTable(
            name = "personcr_certificate_drops",
            joinColumns = {@JoinColumn(name = "personcr_id") },
            inverseJoinColumns = {@JoinColumn(name = "certificate_id") }
    )
    @ForeignKey(name = "PERSONCR_CERTIFICATE_DROP_P_FK", inverseName = "PERSONCR_CERTIFICATE_DROP_C_FK")
    public Set<CertificateCR> getCertificateDrops() {
        return certificateDrops;
    }

    @SuppressWarnings("unused")
    private void setCertificateDrops(Set<CertificateCR> certificateCRs) {
        this.certificateDrops = certificateCRs;
    }

    /**
     * @return the licenseCRs
     */
    @OneToMany
    @Cascade(value = {org.hibernate.annotations.CascadeType.ALL,
            org.hibernate.annotations.CascadeType.DELETE_ORPHAN }
    )
    @JoinTable(
            name = "personcr_license_adds",
            joinColumns = {@JoinColumn(name = "personcr_id") },
            inverseJoinColumns = {@JoinColumn(name = "license_id") }
    )
    @ForeignKey(name = "PERSONCR_LICENSE_ADD_P_FK", inverseName = "PERSONCR_LICENSE_ADD_L_FK")
    public Set<LicenseCR> getLicenseAddsAndMods() {
        return licenseAddsAndMods;
    }

    @SuppressWarnings("unused")
    private void setLicenseAddsAndMods(Set<LicenseCR> licenseCRs) {
        this.licenseAddsAndMods = licenseCRs;
    }

    /**
     * @return the licenseCRs
     */
    @OneToMany
    @Cascade(value = {org.hibernate.annotations.CascadeType.ALL,
            org.hibernate.annotations.CascadeType.DELETE_ORPHAN }
    )
    @JoinTable(
            name = "personcr_license_drops",
            joinColumns = {@JoinColumn(name = "personcr_id") },
            inverseJoinColumns = {@JoinColumn(name = "license_id") }
    )
    @ForeignKey(name = "PERSONCR_LICENSE_DROP_P_FK", inverseName = "PERSONCR_LICENSE_DROP_L_FK")
    public Set<LicenseCR> getLicenseDrops() {
        return licenseDrops;
    }

    @SuppressWarnings("unused")
    private void setLicenseDrops(Set<LicenseCR> licenseCRs) {
        this.licenseDrops = licenseCRs;
    }

    /**
     * @return the preferredContactInfo
     */
    @ManyToOne
    @ForeignKey(name = "PERSONCR_CICR_FK")
    @Cascade(CascadeType.ALL)
    public ContactInfoCR getPreferredContactInfo() {
        return preferredContactInfo;
    }

    /**
     * @param preferredContactInfo the preferredContactInfo to set
     */
    public void setPreferredContactInfo(ContactInfoCR preferredContactInfo) {
        this.preferredContactInfo = preferredContactInfo;
    }

    /**
     * @return the contactInfos
     */
    @OneToMany
    @Cascade(value = {org.hibernate.annotations.CascadeType.ALL,
            org.hibernate.annotations.CascadeType.DELETE_ORPHAN }
    )
    @JoinTable(
            name = "personcr_ci_adds",
            joinColumns = {@JoinColumn(name = "personcr_id") },
            inverseJoinColumns = {@JoinColumn(name = "ci_id") }
    )
    @ForeignKey(name = "PERSONCR_CI_ADD_P_FK", inverseName = "PERSONCR_CI_ADD_C_FK")
    public Set<ContactInfoCR> getContactInfoAddsAndMods() {
        return contactInfoAddsAndMods;
    }

    /**
     * @param contactInfos the contactInfos to set
     */
    public void setContactInfoAddsAndMods(Set<ContactInfoCR> contactInfos) {
        this.contactInfoAddsAndMods = contactInfos;
    }

    /**
     * @return the contactInfos
     */
    @OneToMany
    @Cascade(value = {org.hibernate.annotations.CascadeType.ALL,
            org.hibernate.annotations.CascadeType.DELETE_ORPHAN }
    )
    @JoinTable(
            name = "personcr_ci_drops",
            joinColumns = {@JoinColumn(name = "personcr_id") },
            inverseJoinColumns = {@JoinColumn(name = "ci_id") }
    )
    @ForeignKey(name = "PERSONCR_CI_DROP_P_FK", inverseName = "PERSONCR_CI_DROP_C_FK")
    public Set<ContactInfoCR> getContactInfoDrops() {
        return contactInfoDrops;
    }

    /**
     * @param contactInfos the contactInfos to set
     */
    public void setContactInfoDrops(Set<ContactInfoCR> contactInfos) {
        this.contactInfoDrops = contactInfos;
    }

    /**
     * @return the statusInfo
     */
    public StatusInfo getStatusInfo() {
        return statusInfo;
    }

    /**
     * @param statusInfo the statusInfo to set
     */
    public void setStatusInfo(StatusInfo statusInfo) {
        this.statusInfo = statusInfo;
    }

    /**
     * @return the typeAdds
     */
    @ManyToMany
    @JoinTable(
            name = "personcr_pertype_adds",
            joinColumns = {@JoinColumn(name = "personcr_id") },
            inverseJoinColumns = @JoinColumn(name = "type_id")
    )
    @ForeignKey(name = "PERCR_PERTYPE_ADD_FK")
    public Set<PersonType> getTypeAdds() {
        return this.typeAdds;
    }

    /**
     * @param typeAdds the typeAdds to set
     */
    @SuppressWarnings("unused")
    private void setTypeAdds(Set<PersonType> typeAdds) {
        this.typeAdds = typeAdds;
    }

    /**
     * @return the typeDrops
     */
    @ManyToMany
    @JoinTable(
            name = "personcr_pertype_drops",
            joinColumns = {@JoinColumn(name = "personcr_id") },
            inverseJoinColumns = @JoinColumn(name = "type_id")
    )
    @ForeignKey(name = "PERCR_PERTYPE_DROP_FK")
    public Set<PersonType> getTypeDrops() {
        return this.typeDrops;
    }

    /**
     * @param typeDrops the typeDrops to set
     */
    @SuppressWarnings("unused")
    private void setTypeDrops(Set<PersonType> typeDrops) {
        this.typeDrops = typeDrops;
    }

    private boolean clearPrefix;
    /**
     * @return true when prefix is meant to become null
     */
    public boolean isClearPrefix() {
        return clearPrefix;
    }

    private void setClearPrefix(boolean clearPrefix) {
        this.clearPrefix = clearPrefix;
        setPrefix(null);
    }

    /**
     * Use to set the value of prefix to null since, setPrefix(null) implies there is no
     * change to prefix.
     */
    @Transient
    public void clearPropertyPrefix() {
        this.setClearPrefix(true);
        setPrefix(null);
    }
    
    private boolean clearSuffix;
    
    /**
     * @return true when suffix is meant to become null
     */
    public boolean isClearSuffix() {
        return clearSuffix;
    }

    private void setClearSuffix(boolean clearSuffix) {
        this.clearSuffix = clearSuffix;
    }

    /**
     * Use to set the value of suffix to null since, setSuffix(null) implies there is no
     * change to suffix.
     */
    @Transient
    public void clearPropertySuffix() {
        this.setClearSuffix(true);
        setSuffix(null);
    }
    
    private boolean clearMiddleName;
    
    /**
     * @return true when middleName is meant to become null
     */
    public boolean isClearMiddleName() {
        return clearMiddleName;
    }

    private void setClearMiddleName(boolean clearMiddleName) {
        this.clearMiddleName = clearMiddleName;
    }

    /**
     * Use to set the value of middleName to null since, setMiddleName(null) implies there is no
     * change to middleName.
     */
    @Transient
    public void clearPropertyMiddleName() {
        this.setClearMiddleName(true);
        setMiddleName(null);
    }    
    
    private boolean clearDateOfBirth;
    
    /**
     * @return true when dateOfBirth is meant to become null
     */
    public boolean isClearDateOfBirth() {
        return clearDateOfBirth;
    }

    private void setClearDateOfBirth(boolean clearDateOfBirth) {
        this.clearDateOfBirth = clearDateOfBirth;
    }

    /**
     * Use to set the value of dateOfBirth to null since, setDateOfBirth(null) implies there is no
     * change to dateOfBirth.
     */
    @Transient
    public void clearPropertyDateOfBirth() {
        this.setClearDateOfBirth(true);
        setDateOfBirth(null);
    }   
}
