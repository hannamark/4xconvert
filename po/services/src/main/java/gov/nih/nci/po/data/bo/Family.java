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
import gov.nih.nci.po.util.FamilyDateValidator.FamilyValidDate;
import gov.nih.nci.po.util.FamilyOrganizationRelationshipOrgComparator;
import gov.nih.nci.po.util.NotEmpty;
import gov.nih.nci.po.util.OrderedDateValidator.OrderedDate;
import gov.nih.nci.po.util.PastOrCurrentDateValidator.PastOrCurrentDate;
import gov.nih.nci.po.util.PoRegistry;
import org.hibernate.annotations.Index;
import org.hibernate.annotations.Sort;
import org.hibernate.annotations.SortType;
import org.hibernate.annotations.Where;
import org.hibernate.validator.Length;
import org.hibernate.validator.NotNull;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Family represents a set of related organizations.
 * 
 * @author moweis
 * 
 * @xsnapshot.snapshot-class name="iso" tostring="none"
 *      class="gov.nih.nci.services.family.FamilyDTO"
 *      implements="gov.nih.nci.services.PoDto"
 *      generate-helper-methods="false"
 *      serial-version-uid="1L"
 */
@javax.persistence.Entity
@OrderedDate
@FamilyValidDate
public class Family implements Auditable {
    private static final long serialVersionUID = 9142333411678327002L;
    private static final int DEFAULT_TEXT_COL_LENGTH = 160;

    private Long id;
    private String name;
    private FamilyStatus statusCode;
    private Date startDate;
    private Date endDate;
    private SortedSet<FamilyOrganizationRelationship> familyOrganizationRelationships = 
        new TreeSet<FamilyOrganizationRelationship>(new FamilyOrganizationRelationshipOrgComparator());
    private Set<OrganizationRelationship> organizationRelationships = 
        new HashSet<OrganizationRelationship>();
    private FamilyP30 familyP30;

    /**
     * @return database id
     * @xsnapshot.property match="iso"
     *                     type="gov.nih.nci.iso21090.Ii" name="identifier"
     *                     snapshot-transformer="gov.nih.nci.po.data.convert.IdConverter$FamilyIdConverter"
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
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the statusCode
     * @xsnapshot.property match="iso" type="gov.nih.nci.iso21090.Cd"
     *                     snapshot-transformer="gov.nih.nci.po.data.convert.FamilyStatusCodeConverter$EnumConverter"
     *                     model-transformer="gov.nih.nci.po.data.convert.FamilyStatusCodeConverter$CdConverter"
     */
    @Enumerated(EnumType.STRING)
    @Searchable(matchMode = Searchable.MATCH_MODE_EXACT)
    @NotNull
    public FamilyStatus getStatusCode() {
        return statusCode;
    }

    /**
     * @param statusCode the statusCode to set
     */
    public void setStatusCode(FamilyStatus statusCode) {
        this.statusCode = statusCode;
    }

    /**
     * @return the startDate
     */
    @Temporal(TemporalType.DATE)
    @NotNull
    @PastOrCurrentDate
    public Date getStartDate() {
        return startDate;
    }

    /**
     * @param startDate the startDate to set
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * @return the endDate
     */
    @Temporal(TemporalType.DATE)
    @PastOrCurrentDate
    public Date getEndDate() {
        return endDate;
    }

    /**
     * @param endDate the endDate to set
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    /**
     * @return the family organization relationships within this family.
     */
    @OneToMany(mappedBy = "family")
    @Where(clause = "endDate is null")
    @Sort(type = SortType.COMPARATOR, 
            comparator = FamilyOrganizationRelationshipOrgComparator.class)
    @Searchable(nested = true)
    public SortedSet<FamilyOrganizationRelationship> getFamilyOrganizationRelationships() {
        return familyOrganizationRelationships;
    }

    /**
     * @return the family org relationships for organizations which have not been nullified
     */
    @Transient
    public List<FamilyOrganizationRelationship> getCurrentFamilyOrganizationRelationships() {
        List<FamilyOrganizationRelationship> filteredList = new ArrayList<FamilyOrganizationRelationship>();
        for (FamilyOrganizationRelationship famOrg : getFamilyOrganizationRelationships()) {
            if (famOrg.getOrganization().getStatusCode() != EntityStatus.NULLIFIED) {
                filteredList.add(famOrg);
            }
        }
        return filteredList;
    }

    @SuppressWarnings("unused")
    private void setFamilyOrganizationRelationships(
            SortedSet<FamilyOrganizationRelationship> familyOrganizationRelationships) {
        this.familyOrganizationRelationships = familyOrganizationRelationships;
    }

    /**
     * @return the organizationRelationships
     */
    @OneToMany(mappedBy = "family")
    @Where(clause = "endDate is null")
    public Set<OrganizationRelationship> getOrganizationRelationships() {
        return organizationRelationships;
    }

    /**
     * @param organizationRelationships the organizationRelationships to set
     */
    @SuppressWarnings("unused")
    private void setOrganizationRelationships(Set<OrganizationRelationship> organizationRelationships) {
        this.organizationRelationships = organizationRelationships;
    }

    /**
     * @return the familyP30
     */
    @OneToOne(mappedBy = "family")
    public FamilyP30 getFamilyP30() {
        return familyP30;
    }

    /**
     * @param familyP30 the familyP30 to set
     */
    public void setFamilyP30(FamilyP30 familyP30) {
        this.familyP30 = familyP30;
    }
}
