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
import gov.nih.nci.po.util.NotEmpty;
import gov.nih.nci.po.util.PoRegistry;
import org.hibernate.annotations.ForeignKey;
import org.hibernate.annotations.Formula;
import org.hibernate.annotations.Index;
import org.hibernate.validator.Length;
import org.hibernate.validator.NotNull;
import org.hibernate.validator.Valid;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * Persons.
 *
 * @xsnapshot.snapshot-class name="iso" tostring="none"
 *      class="gov.nih.nci.services.person.AbstractPersonDTO"
 *      extends="gov.nih.nci.services.person.BasePersonDTO"
 *      generate-helper-methods="false"
 *      serial-version-uid="1L"
 */
@MappedSuperclass
@SuppressWarnings("PMD.TooManyFields")
public abstract class AbstractPerson implements PersistentObject, UsOrCanEnforceable {
    private static final long serialVersionUID = 1L;
    private static final int SHORT_COL_LENGTH = 10;
    private static final int LONG_COL_LENGTH = 50;

    private Long id;
    private String firstName;
    private String middleName;
    private String lastName;
    private String suffix;
    private String prefix;
    private EntityStatus statusCode;
    private EntityStatus priorStatusCode;
    private Address postalAddress;
    private List<Email> email = new ArrayList<Email>();
    private List<PhoneNumber> fax = new ArrayList<PhoneNumber>(1);
    private List<PhoneNumber> phone = new ArrayList<PhoneNumber>(1);
    private List<URL> url = new ArrayList<URL>(1);
    private List<PhoneNumber> tty = new ArrayList<PhoneNumber>(1);
    private Date statusDate;
    private PersonSex sexCode;
    private Set<PersonRace> raceCode = new HashSet<PersonRace>();
    private Set<PersonEthnicGroup> ethnicGroupCode = new HashSet<PersonEthnicGroup>();
    private Date birthDate;



    /**
     * @return database id
     * @xsnapshot.property match="iso"
     *                     type="gov.nih.nci.iso21090.Ii" name="identifier"
     *                     snapshot-transformer="gov.nih.nci.po.data.convert.IdConverter$PersonIdConverter"
     *                     model-transformer="gov.nih.nci.po.data.convert.IiConverter"
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Searchable
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
     * @return first (given) name
     */
    @Length(max = LONG_COL_LENGTH)
    @NotEmpty
    @Searchable(matchMode = Searchable.MATCH_MODE_CONTAINS)
    @Index(name = PoRegistry.GENERATE_INDEX_NAME_PREFIX + "fname")
    public String getFirstName() {
        return firstName;
    }

    /**
     * @param firstName first name
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * @return the middleName
     */
    @Length(max = LONG_COL_LENGTH)
    @Searchable(matchMode = Searchable.MATCH_MODE_CONTAINS)
    @Index(name = PoRegistry.GENERATE_INDEX_NAME_PREFIX + "mname")
    public String getMiddleName() {
        return this.middleName;
    }

    /**
     * @param middleName the middleName to set
     */
    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    /**
     * @return last (family) name
     */
    @Length(max = LONG_COL_LENGTH)
    @NotEmpty
    @Searchable(matchMode = Searchable.MATCH_MODE_CONTAINS)
    @Index(name = PoRegistry.GENERATE_INDEX_NAME_PREFIX + "lname")
    public String getLastName() {
        return lastName;
    }

    /**
     * @param lastName last name
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * @return name prefix
     */
    @Length(max = SHORT_COL_LENGTH)
    @Searchable(matchMode = Searchable.MATCH_MODE_CONTAINS)
    @Index(name = PoRegistry.GENERATE_INDEX_NAME_PREFIX + "prefix")
    public String getPrefix() {
        return prefix;
    }

    /**
     * @param prefix prefix
     */
    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    /**
     * @return name suffix
     */
    @Length(max = SHORT_COL_LENGTH)
    @Searchable(matchMode = Searchable.MATCH_MODE_CONTAINS)
    @Index(name = PoRegistry.GENERATE_INDEX_NAME_PREFIX + "suffix")
    public String getSuffix() {
        return suffix;
    }

    /**
     * @param suffix suffix
     */
    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    /**
     * @return mail address
     * @xsnapshot.property match="iso" type="gov.nih.nci.iso21090.Ad"
     *                     snapshot-transformer="gov.nih.nci.po.data.convert.AddressConverter$SimpleConverter"
     *                     model-transformer="gov.nih.nci.po.data.convert.AdConverter$SimpleConverter"
     */
    @ManyToOne(cascade = CascadeType.ALL)
    @NotNull
    @JoinColumn(name = "postal_address_id")
    @ForeignKey(name = "PER_POSTAL_ADDRESS_FK")
    @Valid
    @Searchable(nested = true)
    @Index(name = PoRegistry.GENERATE_INDEX_NAME_PREFIX + "address")
    public Address getPostalAddress() {
        return postalAddress;
    }

    /**
     * @param postalAddress new mailing address
     */
    public void setPostalAddress(Address postalAddress) {
        this.postalAddress = postalAddress;
    }

    /**
     * Get the Person's email property.
     * @return email list
     */
    @Transient
    public List<Email> getEmail() {
        return email;
    }

    /**
     * @param email new email address list
     */
    protected void setEmail(List<Email> email) {
        this.email = email;
    }

    /**
     * Get the Person's fax property.
     * @return fax list
     */
    @Transient
    public List<PhoneNumber> getFax() {
        return fax;
    }

    /**
     * @param fax new fax
     */
    protected void setFax(List<PhoneNumber> fax) {
        this.fax = fax;
    }

    /**
     * Get the Person's phone property.
     * @return phone list
     */
    @Transient
    public List<PhoneNumber> getPhone() {
        return phone;
    }

    /**
     * @param phone new phone list
     */
    protected void setPhone(List<PhoneNumber> phone) {
        this.phone = phone;
    }

    /**
     * Get the Person's url property.
     * @return list of urls
     */
    @Transient
    public List<URL> getUrl() {
        return url;
    }

    /**
     * @param url new url
     */
    protected void setUrl(List<URL> url) {
        this.url = url;
    }
    /**
     * Get the Person's tty property.
     * @return list of tty phone numbers.
     */
    @Transient
    public List<PhoneNumber> getTty() {
        return tty;
    }

    /**
     * @param tty new text numbers
     */
    protected void setTty(List<PhoneNumber> tty) {
        this.tty = tty;
    }

    /**
     * @param newStatus the status of this person record
     */
    public void setStatusCode(EntityStatus newStatus) {
        this.statusCode = newStatus;
    }

    /**
     * @param newSexCode the sex of this person record
     */
    public void setSexCode(PersonSex newSexCode) {
        this.sexCode = newSexCode;
    }

    /**
     * @param newRaceCodes the races of this person record
     */
    public void setRaceCode(Set<PersonRace> newRaceCodes) {
        this.raceCode = newRaceCodes;
    }

    /**
     * @param newEthnicCodes the ethnicity of this person record
     */
    public void setEthnicGroupCode(Set<PersonEthnicGroup> newEthnicCodes) {
        this.ethnicGroupCode = newEthnicCodes;
    }

    /**
     * @param newBirthDate the birth date of this person record
     */
    public void setBirthDate(Date newBirthDate) {
        this.birthDate = newBirthDate;
    }

    /**
     * {@inheritDoc}
     * @xsnapshot.property match="iso" type="gov.nih.nci.iso21090.Cd"
     *                     snapshot-transformer="gov.nih.nci.po.data.convert.StatusCodeConverter$EnumConverter"
     *                     model-transformer="gov.nih.nci.po.data.convert.StatusCodeConverter$CdConverter"
     */
    @Enumerated(EnumType.STRING)
    @NotNull
    @Column(name = "STATUS")
    @Searchable
    @Index(name = PoRegistry.GENERATE_INDEX_NAME_PREFIX + "status")
    public EntityStatus getStatusCode() {
        return this.statusCode;
    }

    /**
     * @return the sex code
     * @xsnapshot.property match="iso" type="gov.nih.nci.iso21090.Cd"
     *                     snapshot-transformer="gov.nih.nci.po.data.convert.SexCodeConverter$EnumConverter"
     *                     model-transformer="gov.nih.nci.po.data.convert.SexCodeConverter$CdConverter"
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "SEX")
    @Searchable
    @Index(name = PoRegistry.GENERATE_INDEX_NAME_PREFIX + "sex")
    public PersonSex getSexCode() {
        return this.sexCode;
    }

    /**
     * @return the race code
     * @xsnapshot.property match="iso" type="gov.nih.nci.iso21090.DSet"
     *                     snapshot-transformer="gov.nih.nci.po.data.convert.RaceCodeConverter$EnumConverter"
     *                     model-transformer="gov.nih.nci.po.data.convert.RaceCodeConverter$DSetConverter"
     */
    @Transient
    public Set<PersonRace> getRaceCode() {
        return this.raceCode;
    }

    /**
     * @return the ethnic code
     * @xsnapshot.property match="iso" type="gov.nih.nci.iso21090.DSet"
     *                     snapshot-transformer="gov.nih.nci.po.data.convert.EthnicGroupCodeConverter$EnumConverter"
     *                     model-transformer="gov.nih.nci.po.data.convert.EthnicGroupCodeConverter$DSetConverter"
     */
    @Transient
    public Set<PersonEthnicGroup> getEthnicGroupCode() {
        return this.ethnicGroupCode;
    }

    /**
     * @return name birthDate
     * @xsnapshot.property match="iso" type="gov.nih.nci.iso21090.Ts"
     *                     snapshot-transformer="gov.nih.nci.po.data.convert.DateConverter"
     *                     model-transformer="gov.nih.nci.po.data.convert.TsConverter"
     */
    @Index(name = PoRegistry.GENERATE_INDEX_NAME_PREFIX + "birthDate")
    public Date getBirthDate() {
        return this.birthDate;
    }

    @Formula("status")
    @SuppressWarnings({"unused", "PMD.EmptyMethodInAbstractClassShouldBeAbstract" })
    private String getPriorAsString() {
        return null;
    }

    @SuppressWarnings("unused")
    private void setPriorAsString(String prior) {
        if (prior != null) {
            this.priorStatusCode = EntityStatus.valueOf(prior);
        } else {
            this.priorStatusCode = null;
        }
    }

    /**
     * @return the prior curation status
     */
    @Transient
    public EntityStatus getPriorEntityStatus() {
       return priorStatusCode;
    }

    /**
     * @return the statusDate
     * @xsnapshot.property match="iso" type="gov.nih.nci.iso21090.Ts"
     *                     snapshot-transformer="gov.nih.nci.po.data.convert.DateConverter"
     *                     model-transformer="gov.nih.nci.po.data.convert.TsConverter"
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
     * {@inheritDoc}
     */
    @Transient
    public boolean isUsOrCanadaAddress() {
        return UsOrCanadaAddressHelper.isUsOrCanadaAddress(this.getPostalAddress());
    }
}
