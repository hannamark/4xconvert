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
package gov.nih.nci.po.web.curation;

import gov.nih.nci.po.data.bo.Family;
import gov.nih.nci.po.data.bo.FamilyOrganizationRelationship;
import gov.nih.nci.po.data.bo.Organization;
import gov.nih.nci.po.data.bo.OrganizationRelationship;
import gov.nih.nci.po.data.dao.FamilyUtilDao;
import gov.nih.nci.po.service.OrganizationRelationshipServiceLocal;
import gov.nih.nci.po.util.OrganizationRelationshipComparator;
import gov.nih.nci.po.util.PoRegistry;
import gov.nih.nci.po.web.util.PoHttpSessionUtil;

import java.util.Date;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.jms.JMSException;

import com.fiveamsolutions.nci.commons.web.struts2.action.ActionHelper;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;
import com.opensymphony.xwork2.validator.annotations.CustomValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;

/**
 * Action class for handling family organization relationships.
 *
 * @author Abraham J. Evans-EL <aevansel@5amsolutions.com>
 */
public class CurateFamilyOrganizationRelationshipAction extends ActionSupport implements Preparable {
    /**
     * Constant for organizational perspective view of Family Organization Relationship.
     */
    public static final String ORGANIZATIONAL_PERSPECTIVE = "organization";
    /**
     * Constant for family perspective view of Family Organization Relationship.
     */
    public static final String FAMILY_PERSPECTIVE = "family";
    private static final long serialVersionUID = 1L;

    private FamilyOrganizationRelationship familyOrgRelationship = new FamilyOrganizationRelationship();
    private SortedSet<OrganizationRelationship> organizationRelationships =
        new TreeSet<OrganizationRelationship>(new OrganizationRelationshipComparator());

    private String rootKey;
    private Long selectedOrgId;
    private Long selectedFamilyId;
    private String perspective = FAMILY_PERSPECTIVE;
    private FamilyUtilDao familyUtilDao = new FamilyUtilDao(); 

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("PMD.SignatureDeclareThrowsException")
    public void prepare() throws Exception {
        if (getRootKey() != null) {
            setFamilyOrgRelationship(
                    (FamilyOrganizationRelationship) PoHttpSessionUtil.getSession().getAttribute(getRootKey()));
            if (getFamilyOrgRelationship() != null && getFamilyOrgRelationship().getId() != null) {
                initializeCollection();
            }
        }
    }

    /**
     * @return shows the initial page
     */
    public String start() {
        setFamilyOrgRelationship(
                PoRegistry.getFamilyOrganizationRelationshipService().getById(getFamilyOrgRelationship().getId()));
        setRootKey(PoHttpSessionUtil.addAttribute(getFamilyOrgRelationship()));
        initializeCollection();
        return INPUT;
    }

    /**
     * Updates the family organization relationship.
     * @return success
     * @throws JMSException exception
     */
    @Validations(customValidators = { @CustomValidator(type = "hibernate", fieldName = "familyOrgRelationship") })
    public String submit() throws JMSException {
        PoRegistry.getFamilyOrganizationRelationshipService().updateEntity(getFamilyOrgRelationship());
        initializeCollection();
        if (getFamilyOrgRelationship().getEndDate() == null) {
            ActionHelper.saveMessage(getText("familyOrgRelationship.update.success"));
        } else {
            ActionHelper.saveMessage(getText("familyOrgRelationship.remove.success"));
            return "parent";
        }
        return SUCCESS;
    }

    /**
     * Removes the family organization relationship by setting its end date.
     * @return success
     * @throws JMSException exception
     */
    public String remove() throws JMSException {
        setFamilyOrgRelationship(
                PoRegistry.getFamilyOrganizationRelationshipService().getById(getFamilyOrgRelationship().getId()));
        getFamilyOrgRelationship().setEndDate(new Date());
        PoRegistry.getFamilyOrganizationRelationshipService().updateEntity(getFamilyOrgRelationship());
        ActionHelper.saveMessage(getText("familyOrgRelationship.remove.success"));
        return "parent";
    }

    /**
     * Loads the organization info based on the selected org id.
     * @return orgInfo
     */
    public String loadOrganizationInfo() {
        Organization org = PoRegistry.getOrganizationService().getById(getSelectedOrgId());
        getFamilyOrgRelationship().setOrganization(org);
        return "orgInfo";
    }

    /**
     * Loads the family info based on the selected family id.
     * @return familyInfo
     */
    public String loadFamilyInfo() {
        Family family = PoRegistry.getFamilyService().getById(getSelectedFamilyId());
        getFamilyOrgRelationship().setFamily(family);
        return "familyInfo";
    }

    /**
     * Reloads the organization relationship info.
     * @return success
     */
    public String loadOrgRelationships() {
        initializeCollection();
        return "relationships";
    }

    /**
     * Retrieves all existing organization relationships and adds missing organization relationships as organization
     * relationships with not family hierarchical types set.
     */
    private void initializeCollection() {
        OrganizationRelationshipServiceLocal orgRelService = PoRegistry.getOrganizationRelationshipService();
        if (getFamilyOrgRelationship().getFamily().getId() != null
                && getFamilyOrgRelationship().getOrganization().getId() != null) {
            setOrganizationRelationships(
                    new TreeSet<OrganizationRelationship>(new OrganizationRelationshipComparator()));
            getOrganizationRelationships().addAll(
                    orgRelService.getActiveOrganizationRelationships(getFamilyOrgRelationship().getFamily().getId(),
                            getFamilyOrgRelationship().getOrganization().getId()));
        }
        Long familyId = getFamilyOrgRelationship().getFamily().getId();
        Long orgId = getFamilyOrgRelationship().getOrganization().getId();
        List<FamilyOrganizationRelationship> relationships =
            getFamilyUtilDao().getActiveRelationships(familyId);
        for (FamilyOrganizationRelationship famOrgRel : relationships) {
            if (!famOrgRel.getId().equals(getFamilyOrgRelationship().getId())
                    && orgRelService.getActiveOrganizationRelationship(familyId, orgId,
                            famOrgRel.getOrganization().getId()) == null) {
                OrganizationRelationship or = new OrganizationRelationship();
                or.setOrganization(getFamilyOrgRelationship().getOrganization());
                or.setRelatedOrganization(famOrgRel.getOrganization());
                getOrganizationRelationships().add(or);
            }
        }
    }

    /**
     * @return the familyOrgRelationship
     */
    public FamilyOrganizationRelationship getFamilyOrgRelationship() {
        return familyOrgRelationship;
    }

    /**
     * @param familyOrgRelationship the familyOrgRelationship to set
     */
    public void setFamilyOrgRelationship(FamilyOrganizationRelationship familyOrgRelationship) {
        this.familyOrgRelationship = familyOrgRelationship;
    }

    /**
     * Method for pulling this value in struts xml.
     * @return the family organization relationship id as a string.
     */
    public String getFamilyOrgRelationshipId() {
        if (getFamilyOrgRelationship() != null && getFamilyOrgRelationship().getId() != null) {
            return this.getFamilyOrgRelationship().getId().toString();
        }
        return "";
    }
    
    /**
     * @return the rootKey
     */
    public String getRootKey() {
        return rootKey;
    }

    /**
     * @param rootKey the rootKey to set
     */
    public void setRootKey(String rootKey) {
        this.rootKey = rootKey;
    }

    /**
     * @return the selectedOrgId
     */
    public Long getSelectedOrgId() {
        return selectedOrgId;
    }

    /**
     * @param selectedOrgId the selectedOrgId to set
     */
    public void setSelectedOrgId(Long selectedOrgId) {
        this.selectedOrgId = selectedOrgId;
    }

    /**
     * @return the selectedFamilyId
     */
    public Long getSelectedFamilyId() {
        return selectedFamilyId;
    }

    /**
     * @param selectedFamilyId the selectedFamilyId to set
     */
    public void setSelectedFamilyId(Long selectedFamilyId) {
        this.selectedFamilyId = selectedFamilyId;
    }

    /**
     * @return the organizationRelationships
     */
    public SortedSet<OrganizationRelationship> getOrganizationRelationships() {
        return organizationRelationships;
    }

    /**
     * @param organizationRelationships the organizationRelationships to set
     */
    public void setOrganizationRelationships(SortedSet<OrganizationRelationship> organizationRelationships) {
        this.organizationRelationships = organizationRelationships;
    }


    /**
     * @param perspective the perspective to set
     */
    public void setPerspective(String perspective) {
        this.perspective = perspective;
    }

    /**
     * @return the perspective
     */
    public String getPerspective() {
        return perspective;
    }

    /**
     * @return the familyUtilDao
     */
    public FamilyUtilDao getFamilyUtilDao() {
        return familyUtilDao;
    }

    /**
     * @param familyUtilDao the familyUtilDao to set
     */
    public void setFamilyUtilDao(FamilyUtilDao familyUtilDao) {
        this.familyUtilDao = familyUtilDao;
    }
}
