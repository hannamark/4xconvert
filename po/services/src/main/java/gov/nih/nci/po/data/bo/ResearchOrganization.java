/**
 * The software subject to this notice and license includes both human readable
 * source code form and machine readable, binary, object code form. The COPPA PO
 * Software was developed in conjunction with the National Cancer Institute
 * (NCI) by NCI employees and 5AM Solutions, Inc. (5AM). To the extent
 * government employees are authors, any rights in such works shall be subject
 * to Title 17 of the United States Code, section 105.
 *
 * This COPPA PO Software License (the License) is between NCI and You. You (or
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
 * its rights in the COPPA PO Software to (i) use, install, access, operate,
 * execute, copy, modify, translate, market, publicly display, publicly perform,
 * and prepare derivative works of the COPPA PO Software; (ii) distribute and
 * have distributed to and by third parties the COPPA PO Software and any
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

import com.fiveamsolutions.nci.commons.search.Searchable;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.po.util.NotEmptyIiExtension;
import gov.nih.nci.po.util.NotEmptyIiRoot;
import gov.nih.nci.po.util.PoServiceUtil;
import gov.nih.nci.po.util.ResearchOrganizationTypeCodeValidator;
import gov.nih.nci.po.util.RoleStatusChange;
import gov.nih.nci.po.util.UniqueResearchOrganization;
import gov.nih.nci.po.util.VaildResearchOrganizationTypeWithFundingMechanism;
import gov.nih.nci.po.util.ValidIi;
import gov.nih.nci.security.authorization.domainobjects.User;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CollectionOfElements;
import org.hibernate.annotations.Columns;
import org.hibernate.annotations.ForeignKey;
import org.hibernate.annotations.Index;
import org.hibernate.annotations.IndexColumn;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.Where;
import org.hibernate.validator.Valid;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * Oversight committee role class.
 *
 * @xsnapshot.snapshot-class name="iso" tostring="none" generate-helper-methods="false"
 *      class="gov.nih.nci.services.correlation.ResearchOrganizationDTO"
 *      model-extends="gov.nih.nci.po.data.bo.AbstractResearchOrganization"
 *      implements="gov.nih.nci.services.CorrelationDto"
 *      serial-version-uid="2L"
 */
@Entity
@RoleStatusChange
@VaildResearchOrganizationTypeWithFundingMechanism
@UniqueResearchOrganization
@ResearchOrganizationTypeCodeValidator.ResearchOrganizationTypeCode
public class ResearchOrganization extends AbstractResearchOrganization implements Correlation {

    private static final String RO_ID = "ro_id";
    private static final String IDX = "idx";

    private static final long serialVersionUID = 2L;

    private Set<ResearchOrganizationCR> changeRequests = new HashSet<ResearchOrganizationCR>();

    private ResearchOrganization duplicateOf;

    /**
     * {@inheritDoc}
     */
    @OneToMany(mappedBy = "target")
    @Where(clause = "processed = 'false'")
    public Set<ResearchOrganizationCR> getChangeRequests() {
        return changeRequests;
    }

    @SuppressWarnings("unused")
    private void setChangeRequests(Set<ResearchOrganizationCR> changeRequests) {
        this.changeRequests = changeRequests;
    }

    /**
     * {@inheritDoc}
     * @xsnapshot.property match="iso" type="gov.nih.nci.iso21090.Ii" name="duplicateOf"
     *            snapshot-transformer="gov.nih.nci.po.data.convert.PersistentObjectConverter$PersistentROConverter"
     *            model-transformer="gov.nih.nci.po.data.convert.IiConverter$CorrelationIiConverter"
     */
    @ManyToOne(optional = true)
    @JoinColumn(name = "duplicate_of", nullable = true)
    @Index(name = "ro_duplicateof_idx")
    @ForeignKey(name = "RO_DUPLICATE_RO_FK")
    public ResearchOrganization getDuplicateOf() {
        return duplicateOf;
    }

    /**
     * Set the object of which this is as a duplicate.
     * @param duplicateOf object of which this is a duplicate
     */
    public void setDuplicateOf(ResearchOrganization duplicateOf) {
        this.duplicateOf = duplicateOf;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Searchable
    public Long getId() {
        return super.getId();
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
            name = "ro_address",
            joinColumns = @JoinColumn(name = RO_ID),
            inverseJoinColumns = @JoinColumn(name = "address_id")
    )
    @IndexColumn(name = IDX)
    @ForeignKey(name = "RO_ADDRESS_FK", inverseName = "ADDRESS_RO_FK")
    @Valid
    @Searchable(nested = true)
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
            name = "ro_email",
            joinColumns = @JoinColumn(name = RO_ID),
            inverseJoinColumns = @JoinColumn(name = "email_id")
    )
    @IndexColumn(name = IDX)
    @ForeignKey(name = "RO_EMAIL_FK", inverseName = "EMAIL_RO_FK")
    @Valid
    @Searchable(nested = true)
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
            name = "ro_fax",
            joinColumns = @JoinColumn(name = RO_ID),
            inverseJoinColumns = @JoinColumn(name = "fax_id")
    )
    @IndexColumn(name = IDX)
    @ForeignKey(name = "RO_FAX_FK", inverseName = "FAX_RO_FK")
    @Valid
    @Searchable(nested = true)
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
            name = "ro_phone",
            joinColumns = @JoinColumn(name = RO_ID),
            inverseJoinColumns = @JoinColumn(name = "phone_id")
    )
    @IndexColumn(name = IDX)
    @ForeignKey(name = "RO_PHONE_FK", inverseName = "PHONE_RO_FK")
    @Valid
    @Searchable(nested = true)
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
            name = "ro_tty",
            joinColumns = @JoinColumn(name = RO_ID),
            inverseJoinColumns = @JoinColumn(name = "tty_id")
    )
    @IndexColumn(name = IDX)
    @ForeignKey(name = "RO_TTY_FK", inverseName = "TTY_RO_FK")
    @Valid
    @Searchable(nested = true)
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
            name = "ro_url",
            joinColumns = @JoinColumn(name = RO_ID),
            inverseJoinColumns = @JoinColumn(name = "url_id")
    )
    @IndexColumn(name = IDX)
    @ForeignKey(name = "RO_URL_FK", inverseName = "URL_RO_FK")
    @Valid
    @Searchable(nested = true)
    public List<URL> getUrl() {
        return super.getUrl();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @OneToMany(fetch = FetchType.EAGER)
    @Cascade(value = {org.hibernate.annotations.CascadeType.ALL,
                      org.hibernate.annotations.CascadeType.DELETE_ORPHAN }
    )
    @JoinTable(
            name = "ro_alias",
            joinColumns = @JoinColumn(name = RO_ID),
            inverseJoinColumns = @JoinColumn(name = "alias_id")
    )
    @IndexColumn(name = IDX)
    @ForeignKey(name = "RO_Alias_FK", inverseName = "Alias_RO_FK")
    @Valid
    @Searchable(nested = true)
    public List<Alias> getAlias() {
        return super.getAlias();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    @CollectionOfElements
    @JoinTable(
            name = "ro_otheridentifier",
            joinColumns = @JoinColumn(name = "ro_id")
    )
    @ForeignKey(name = "RO_OI_FK")
    @Type(type = "gov.nih.nci.po.util.IiCompositeUserType")
    @Columns(columns = {
            @Column(name = "null_flavor"),
            @Column(name = "displayable"),
            @Column(name = "extension"),
            @Column(name = "identifier_name"),
            @Column(name = "reliability"),
            @Column(name = "root"),
            @Column(name = "scope")
    })
    @ValidIi
    @NotEmptyIiExtension
    @NotEmptyIiRoot
    @Searchable(fields = { "extension", "root" }, matchMode = Searchable.MATCH_MODE_EXACT)
    public Set<Ii> getOtherIdentifiers() {
        return super.getOtherIdentifiers();
    }
    
    /**
     * @return the user
     */
    @ManyToOne(optional = true)
    @JoinColumn(name = "created_by_id", nullable = true)
    @ForeignKey(name = "orgrole_createdby_user_fk")
    public User getCreatedBy() {
        return super.getCreatedBy();
    }
    
    /**
     * @return user name
     */
    @Transient
    public String getCreatedByUserName() {
        return PoServiceUtil.getUserName(super.getCreatedBy());
    }
    
    /**
     * @return the user
     */
    @ManyToOne(optional = true)
    @JoinColumn(name = "overridden_by_id", nullable = true)
    @ForeignKey(name = "orgrole_overriddenby_user_fk")
    public User getOverriddenBy() {
        return super.getOverriddenBy();
    }

    
    /**
     * @return user name
     */
    @Transient
    public String getOverriddenByUserName() {
        return PoServiceUtil.getUserName(super.getOverriddenBy());
    }
}

