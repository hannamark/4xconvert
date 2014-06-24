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
package gov.nih.nci.po.web.roles;

import gov.nih.nci.po.data.bo.Organization;
import gov.nih.nci.po.data.bo.OversightCommittee;
import gov.nih.nci.po.data.bo.OversightCommitteeCR;
import gov.nih.nci.po.service.AnnotatedBeanSearchCriteria;
import gov.nih.nci.po.service.OversightCommitteeServiceLocal;
import gov.nih.nci.po.service.OversightCommitteeSortCriterion;
import gov.nih.nci.po.util.PoRegistry;
import gov.nih.nci.po.web.util.PoHttpSessionUtil;
import gov.nih.nci.po.web.util.validator.Addressable;
import gov.nih.nci.security.authorization.domainobjects.User;
import gov.nih.nci.security.exceptions.CSException;

import java.util.ArrayList;

import javax.jms.JMSException;

import org.displaytag.properties.SortOrderEnum;

import com.fiveamsolutions.nci.commons.search.SearchCriteria;
import com.fiveamsolutions.nci.commons.web.displaytag.PaginatedList;
import com.opensymphony.xwork2.Preparable;
import com.opensymphony.xwork2.validator.annotations.CustomValidator;
import com.opensymphony.xwork2.validator.annotations.ValidationParameter;
import com.opensymphony.xwork2.validator.annotations.Validations;

/**
 * Action to manage OversightCommittee(s).
 *
 * @author smatyas
 * @author Rohit Gupta
 */
public class OversightCommitteeAction extends
        AbstractOrganizationRoleAction<OversightCommittee, OversightCommitteeCR, OversightCommitteeServiceLocal>
        implements Addressable, Preparable {

    private static final long serialVersionUID = 1L;
    private OversightCommittee role = new OversightCommittee();
    private OversightCommittee duplicateOf = new OversightCommittee();
    private OversightCommitteeCR cr = new OversightCommitteeCR();
    private String rootKey;

    /**
     * default constructor.
     */
    public OversightCommitteeAction() {
        super();
    }

    /**
     * {@inheritDoc}
     */
    public OversightCommittee getRole() {
        return role;
    }

    /**
     * {@inheritDoc}
     */
    public void setRole(OversightCommittee role) {
        this.role = role;
    }

    /**
     * {@inheritDoc}
     */
    public OversightCommitteeCR getCr() {
        return cr;
    }

    /**
     * {@inheritDoc}
     */
    public void setCr(OversightCommitteeCR cr) {
        this.cr = cr;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OversightCommitteeCR getBaseCr() {
        return getCr();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OversightCommittee getBaseRole() {
        return getRole();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setBaseCr(OversightCommitteeCR baseCr) {
        setCr(baseCr);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setBaseRole(OversightCommittee baseRole) {
        setRole(baseRole);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void prepare() {
        super.prepare();
        if (getRootKey() != null) {
            role = (OversightCommittee) getSession().getAttribute(getRootKey());
        }        
        if (getRole() == null) {
            setRole(new OversightCommittee());
        }
        if (getRole().getPlayer() == null) { // if not set, then set to default
            getRole().setPlayer(getOrganization());
        }
    }

    /**
     *
     * {@inheritDoc}
     * @throws CSException CSException
     */
    @Validations(
        customValidators = { @CustomValidator(type = "hibernate", fieldName = "role" ,
                parameters = { @ValidationParameter(name = "resourceKeyBase", value = "oversightCommittee") }),
                @CustomValidator(type = USORCANADAVALIDATOR, fieldName = "role.phone",
                        message = "US and Canadian telephone numbers must match ###-###-####(x#*).") ,
                @CustomValidator(type = USORCANADAVALIDATOR, fieldName = "role.fax",
                        message = "US and Canadian fax numbers must match ###-###-####(x#*)."),
                @CustomValidator(type = USORCANADAVALIDATOR, fieldName = "role.tty",
                        message = "US and Canadian tty numbers must match ###-###-####(x#*).")
            })
    @Override
    public String add() throws JMSException, CSException {
        return super.add();
    }

    /**
     * {@inheritDoc}
     */
    @Validations(
        customValidators = { @CustomValidator(type = "hibernate", fieldName = "role" ,
                parameters = { @ValidationParameter(name = "resourceKeyBase", value = "oversightCommittee") }),
                @CustomValidator(type = USORCANADAVALIDATOR, fieldName = "role.phone",
                        message = "US and Canadian telephone numbers must match ###-###-####(x#*).") ,
                @CustomValidator(type = USORCANADAVALIDATOR, fieldName = "role.fax",
                        message = "US and Canadian fax numbers must match ###-###-####(x#*)."),
                @CustomValidator(type = USORCANADAVALIDATOR, fieldName = "role.tty",
                        message = "US and Canadian tty numbers must match ###-###-####(x#*).")
            })
    @Override
    public String edit() throws JMSException {
        // PO-1098 - for some reason, the duplicate of wasn't getting set properly by struts when we tried to
        // set person.duplicateOf.id directly, so we're setting it manually
        if (duplicateOf != null && duplicateOf.getId() != null) {
            role.setDuplicateOf(duplicateOf);
        }
        return super.edit();
    }
    
    /**
     * Override method.
     * @return input
     * @throws CSException JMSException
     */
    public String override() throws CSException {        
        User overriddenBy = getLoggedInUser();                
        getRoleService().override(role, overriddenBy);            
        return input();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected SearchCriteria<OversightCommittee> getDuplicateCriteria() {
        OversightCommittee dupOfBOCrit = new OversightCommittee();
        AnnotatedBeanSearchCriteria<OversightCommittee> duplicateOfCriteria
            = new AnnotatedBeanSearchCriteria<OversightCommittee>(dupOfBOCrit);
        dupOfBOCrit.setPlayer(getOrganization());
        return duplicateOfCriteria;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void defaultConstructorInit() {
        setResults(new PaginatedList<OversightCommittee>(0, new ArrayList<OversightCommittee>(),
                PoRegistry.DEFAULT_RECORDS_PER_PAGE, 1, null, OversightCommitteeSortCriterion.ID.name(),
                SortOrderEnum.ASCENDING));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected OversightCommitteeServiceLocal getRoleService() {
        return PoRegistry.getInstance().getServiceLocator().getOversightCommitteeService();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected SearchCriteria<OversightCommittee> getSearchCriteria() {
        OversightCommittee boCrit = new OversightCommittee();
        AnnotatedBeanSearchCriteria<OversightCommittee> criteria = new AnnotatedBeanSearchCriteria<OversightCommittee>(
                boCrit);
        Organization player = new Organization();
        player.setId(getOrganization().getId());
        boCrit.setPlayer(player);
        return criteria;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Class<OversightCommitteeSortCriterion> getSortCriterion() {
        return OversightCommitteeSortCriterion.class;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String getAddSuccessMessageKey() {
        return "oversightcommittee.create.success";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String getEditSuccessMessageKey() {
        return "oversightcommittee.update.success";
    }

    /**
     * @return the duplicateOf
     */
    public OversightCommittee getDuplicateOf() {
        return duplicateOf;
    }

    /**
     * @param duplicateOf the duplicateOf to set
     */
    public void setDuplicateOf(OversightCommittee duplicateOf) {
        this.duplicateOf = duplicateOf;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isUsOrCanadaFormat() {
        return role.isUsOrCanadaAddress();    
    }
    
    
    /**
    *
    * @return the session key of the root object (org or person)
    */
   public String getRootKey() {
       return rootKey;
   }

   /**
    *
    * @param rootKey the session key of the root object.
    */
   public void setRootKey(String rootKey) {
       PoHttpSessionUtil.validateSessionKey(rootKey);
       this.rootKey = rootKey;
   }
   
   /**
    * {@inheritDoc}
    */
   @Override
   public String input() {
       String result = super.input();
       initializeCollections(getRole());
       initialize(getRole());
       setRootKey(PoHttpSessionUtil.addAttribute(getRole()));
       return result;
   }
   
}
