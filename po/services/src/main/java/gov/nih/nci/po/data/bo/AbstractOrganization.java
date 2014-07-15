/**
 * The software subject to this notice and license includes both human readable
 * source code form and machine readable, binary, object code form. The po-app
 * Software was developed in conjunction with the National Cancer Institute
 * (NCI) by NCI employees and 5AM Solutions, Inc. (5AM). To the extent
 * government employees are authors, any rights in such works shall be subject
 * to Title 17 of the United States Code, section 105.
 *
 * This po-app Software License (the License) is between NCI and You. You (or
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
 * its rights in the po-app Software to (i) use, install, access, operate,
 * execute, copy, modify, translate, market, publicly display, publicly perform,
 * and prepare derivative works of the po-app Software; (ii) distribute and
 * have distributed to and by third parties the po-app Software and any
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
import javax.persistence.Transient;
import java.util.ArrayList;
import java.util.List;


/**
 * Organizations.
 *
 * @xsnapshot.snapshot-class name="iso" tostring="none"
 *      class="gov.nih.nci.services.organization.AbstractOrganizationDTO"
 *      extends="gov.nih.nci.services.organization.BaseOrganizationDTO"
 *      generate-helper-methods="false"
 *      serial-version-uid="1L"
 */
@MappedSuperclass
@SuppressWarnings("PMD.UnusedPrivateMethod")
public abstract class AbstractOrganization implements PersistentObject, UsOrCanEnforceable, Aliasable {
    private static final long serialVersionUID = 1L;
    private static final int DEFAULT_TEXT_COL_LENGTH = 160;
    private Long id;
    private String name;
    private Address postalAddress;

    private List<Email> email = new ArrayList<Email>();
    private List<PhoneNumber> fax = new ArrayList<PhoneNumber>(1);
    private List<PhoneNumber> phone = new ArrayList<PhoneNumber>(1);
    private List<URL> url = new ArrayList<URL>(1);
    private List<PhoneNumber> tty = new ArrayList<PhoneNumber>(1);
    
    private List<Alias> alias = new ArrayList<Alias>();
    
    private EntityStatus statusCode;
    private EntityStatus priorEntityStatus;


    /**
     * @return database identity
     * @xsnapshot.property match="iso"
     *                     type="gov.nih.nci.iso21090.Ii" name="identifier"
     *                     snapshot-transformer="gov.nih.nci.po.data.convert.IdConverter$OrgIdConverter"
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
     * @return name
     * @xsnapshot.property match="iso" type="gov.nih.nci.iso21090.EnOn"
     *                     snapshot-transformer="gov.nih.nci.po.data.convert.StringConverter"
     *                     model-transformer="gov.nih.nci.po.data.convert.EnConverter"
     */
    @NotEmpty
    @Length(max = DEFAULT_TEXT_COL_LENGTH)
    @Searchable(matchMode = Searchable.MATCH_MODE_CONTAINS)
    @Index(name = PoRegistry.GENERATE_INDEX_NAME_PREFIX + "name")
    public String getName() {
        return name;
    }

    /**
     * @param name name
     */
    public void setName(String name) {
        this.name = name;
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
    @ForeignKey(name = "ORG_POSTAL_ADDRESS_FK")
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
     * @param email new email address list
     */
    protected void setEmail(List<Email> email) {
        this.email = email;
    }

   /**
     * Get the Organization's email property.
     * @return email list
     */
    @Transient
    public List<Email> getEmail() {
        return email;
    }

    /**
     * @param fax new fax
     */
    protected void setFax(List<PhoneNumber> fax) {
        this.fax = fax;
    }

    /**
     * Get the Organization's fax property.
     * @return fax list
     */
    @Transient
    public List<PhoneNumber> getFax() {
        return fax;
    }


    /**
     * @param phone new phone list
     */
    protected void setPhone(List<PhoneNumber> phone) {
        this.phone = phone;
    }

    /**
     * Get the Organization's phone property.
     * @return phone list
     */
    @Transient
    public List<PhoneNumber> getPhone() {
        return phone;
    }

    /**
     * @param url new url
     */
    protected void setUrl(List<URL> url) {
        this.url = url;
    }

    /**
     * Get the Organization's url property.
     * @return list of urls
     */
    @Transient
    public List<URL> getUrl() {
        return url;
    }

    /**
     * @param tty new text numbers
     */
    protected void setTty(List<PhoneNumber> tty) {
        this.tty = tty;
    }

    /**
     * Get the Organization's tty property.
     * @return list of tty phone numbers.
     */
    @Transient
    public List<PhoneNumber> getTty() {
        return tty;
    }
    
    /**
     * @param alias new alias list
     */
    protected void setAlias(List<Alias> alias) {
        this.alias = alias;
    }

   /**
     * Get the Organization's alias property.
     * @return alias list
     */
    @Transient
    public List<Alias> getAlias() {
        return alias;
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
     * @param status Curation Status
     */
    public void setStatusCode(EntityStatus status) {
        this.statusCode = status;
    }

    @Formula("status")
    @SuppressWarnings("unused")
    private String getPriorAsString() {
        if (this.priorEntityStatus != null) {
            return this.priorEntityStatus.name();
        }
        return null;
    }

    @SuppressWarnings("unused")
    private void setPriorAsString(String prior) {
        if (prior != null) {
            this.priorEntityStatus = EntityStatus.valueOf(prior);
        } else {
            this.priorEntityStatus = null;
        }
    }

    /**
     * @return the prior curation status
     */
    @Transient
    public EntityStatus getPriorEntityStatus() {
        return priorEntityStatus;
    }

    /**
     * Returns boolean if the address is US.
     * @return bool
     */
    @Transient
    public boolean isUsOrCanadaAddress() {
        return UsOrCanadaAddressHelper.isUsOrCanadaAddress(this.getPostalAddress());
    }

}
