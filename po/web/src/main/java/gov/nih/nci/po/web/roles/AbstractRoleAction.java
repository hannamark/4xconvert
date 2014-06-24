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

import gov.nih.nci.po.data.bo.AbstractRole;
import gov.nih.nci.po.data.bo.Address;
import gov.nih.nci.po.data.bo.Contactable;
import gov.nih.nci.po.data.bo.Correlation;
import gov.nih.nci.po.data.bo.CorrelationChangeRequest;
import gov.nih.nci.po.data.bo.Mailable;
import gov.nih.nci.po.data.bo.RoleStatus;
import gov.nih.nci.po.service.GenericStructrualRoleServiceLocal;
import gov.nih.nci.po.web.GenericSearchServiceUtil;
import gov.nih.nci.po.web.curation.AbstractPoAction;
import gov.nih.nci.po.web.util.PoHttpSessionUtil;
import gov.nih.nci.security.authorization.domainobjects.User;
import gov.nih.nci.security.exceptions.CSException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.jms.JMSException;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections.CollectionUtils;
import org.apache.struts2.ServletActionContext;

import com.fiveamsolutions.nci.commons.data.persistent.PersistentObject;
import com.fiveamsolutions.nci.commons.data.search.SortCriterion;
import com.fiveamsolutions.nci.commons.search.SearchCriteria;
import com.fiveamsolutions.nci.commons.web.displaytag.PaginatedList;
import com.fiveamsolutions.nci.commons.web.struts2.action.ActionHelper;
import com.opensymphony.xwork2.Preparable;

/**
 * @author smatyas
 * @author Rohit Gupta
 * 
 * @param <ROLE>
 * @param <ROLECR>
 * @param <ROLESERVICE>
 */
@SuppressWarnings({ "PMD.TooManyMethods", "PMD.CyclomaticComplexity" })
public abstract class AbstractRoleAction<ROLE extends Correlation, ROLECR extends CorrelationChangeRequest<ROLE>, 
        ROLESERVICE extends GenericStructrualRoleServiceLocal<ROLE>>
        extends AbstractPoAction implements Preparable {

    private static final long serialVersionUID = 1L;
    private static final String UNCHECKED = "unchecked";
    /**
     * The action execution was successful. Show result view to the end user.
     */
    public static final String CHANGE_CURRENT_CHANGE_REQUEST_RESULT = "changeCurrentChangeRequest";
    private PaginatedList<ROLE> results;   

    /**
     * Default Constructor (force subclasses to initialize.
     */
    public AbstractRoleAction() {
        super();
        defaultConstructorInit();
    }

    /**
     * Implement to provide default constructor initialization.
     */
    protected abstract void defaultConstructorInit();

    /**
     * {@inheritDoc}
     */
    public void prepare() {
        if (getBaseRole() != null && getBaseRole().getId() != null) {
            setBaseRole(getRoleService().getById(getBaseRole().getId()));
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressWarnings(UNCHECKED)
    public String input() {
        ServletActionContext.getRequest().setAttribute(INPUT, true);
        if (getBaseRole().getId() == null) {
            getBaseRole().setStatus(RoleStatus.PENDING);
        }
        if (!getBaseRole().getChangeRequests().isEmpty()) {
            setBaseCr((ROLECR) getBaseRole().getChangeRequests().iterator()
                    .next());
        }
        return INPUT;
    }

    /**
     * @return success
     */
    public String start() {
        search();
        return SUCCESS;
    }

    /**
     * @return success
     */
    public String list() {
        search();
        return SUCCESS;
    }

    /**
     * Generic method to search and fill the getResults() property using the
     * getSearchCriteria() and getSortCriterion().
     */
    protected void search() {
        GenericSearchServiceUtil.search(getRoleService(), getSearchCriteria(),
                getResults(), getSortCriterion());
    }

    /**
     * @return sort criterion type to be used by search method
     */
    protected abstract Class<? extends SortCriterion<ROLE>> getSortCriterion();

    /**
     * @return criteria to be used by search method
     */
    protected abstract SearchCriteria<ROLE> getSearchCriteria();

    /**
     * @return success
     * @throws JMSException
     *             if an error occurred while publishing announcement
     * @throws CSException
     *             CSException
     * 
     */
    public String add() throws JMSException, CSException {
        setCreatedByUserInRole();
        getRoleService().curate(getBaseRole());
        list();
        ActionHelper.saveMessage(getText(getAddSuccessMessageKey()));
        return SUCCESS;
    }

    private void setCreatedByUserInRole() throws CSException {
        User createdBy = getLoggedInUser();
        Object role = getBaseRole();
       
        if (role instanceof AbstractRole) {
            ((AbstractRole) role).setCreatedBy(createdBy);
        } 
    }

    /**
     * @return message key
     */
    protected abstract String getAddSuccessMessageKey();

    /**
     * @return success
     * @throws JMSException
     *             if an error occurred while publishing announcement
     */
    public String edit() throws JMSException {
        getRoleService().curate(getBaseRole());
        list();
        ActionHelper.saveMessage(getText(getEditSuccessMessageKey()));
        return SUCCESS;
    }

    /**
     * @return message key
     */
    protected abstract String getEditSuccessMessageKey();

    /**
     * Force sub-classes to override so that the PersistentObjectTypeConverter
     * works properly.
     * 
     * @return to add/edit/remove
     */
    public abstract ROLE getBaseRole();

    /**
     * Force sub-classes to override so that the PersistentObjectTypeConverter
     * works properly.
     * 
     * @param role
     *            to add/edit/remove
     */
    public abstract void setBaseRole(ROLE role);

    /**
     * Force sub-classes to override so that the PersistentObjectTypeConverter
     * works properly.
     * 
     * @return active change request
     */
    public abstract ROLECR getBaseCr();

    /**
     * Force sub-classes to override so that the PersistentObjectTypeConverter
     * works properly.
     * 
     * @param cr
     *            active change request
     */
    public abstract void setBaseCr(ROLECR cr);

    /**
     * @return the service for the ROLE
     */
    protected abstract ROLESERVICE getRoleService();

    /**
     * @return success
     */
    public String changeCurrentChangeRequest() {
        return CHANGE_CURRENT_CHANGE_REQUEST_RESULT;
    }

    /**
     * @return the list of entries for the select CR pull-down
     */
    @SuppressWarnings(UNCHECKED)
    public Map<String, String> getSelectChangeRequests() {
        TreeMap<String, String> treeMap = new TreeMap<String, String>();
        Set<ROLECR> unprocessedChangeRequests = getBaseRole()
                .getChangeRequests();
        for (ROLECR changeRequest : unprocessedChangeRequests) {
            treeMap.put(changeRequest.getId().toString(), "CR-ID-"
                    + changeRequest.getId().toString());
        }
        return treeMap;
    }

    /**
     * @return the allowable RoleStatus values
     */
    public Collection<RoleStatus> getAvailableStatus() {
        if (getBaseRole().getId() != null) {
            return getBaseRole().getPriorStatus().getAllowedTransitions();
        }
        List<RoleStatus> set = new ArrayList<RoleStatus>();
        set.add(RoleStatus.PENDING);
        set.add(RoleStatus.ACTIVE);
        return set;
    }

    /**
     * @return search results
     */
    public PaginatedList<ROLE> getResults() {
        return results;
    }

    /**
     * @param results
     *            search results
     */
    protected void setResults(PaginatedList<ROLE> results) {
        this.results = results;
    }

    /**
     * @return list of available duplicate of entries for the current
     *         ResearchOrganization
     */
    public List<ROLE> getAvailableDuplicateOfs() {
        List<ROLE> duplicateOfList = getRoleService().search(
                getDuplicateCriteria());
        remove(duplicateOfList, getBaseRole().getId());
        return duplicateOfList;
    }

    /**
     * @return the SearchCriteria used to search for duplicateOf for the role.
     */
    protected abstract SearchCriteria<ROLE> getDuplicateCriteria();

    /**
     * Remove matching PersistentObject(s) from the list.
     * 
     * @param duplicateOfList
     *            list to search
     * @param id
     *            to find
     */
    protected void remove(List<? extends PersistentObject> duplicateOfList,
            Long id) {
        if (id != null && CollectionUtils.isNotEmpty(duplicateOfList)) {
            for (Iterator<? extends PersistentObject> itr = duplicateOfList
                    .iterator(); itr.hasNext();) {
                PersistentObject ro = itr.next();
                if (ro.getId().equals(id)) {
                    itr.remove();
                }
            }
        }
    }

    /**
     * @param contactable
     *            initialize Contactable
     */
    protected void initializeCollections(Contactable contactable) {
        if (contactable != null) {
            contactable.getEmail().size();
            contactable.getFax().size();
            contactable.getPhone().size();
            contactable.getTty().size();
            contactable.getUrl().size();
        }
    }

    /**
     * @param mailable
     *            initialize Mailable
     */
    protected void initialize(Mailable mailable) {
        mailable.getPostalAddresses().size();
        for (Address address : mailable.getPostalAddresses()) {
            if (address.getCountry() != null) {
                address.getCountry().getStates().size();
            }
        }
    }

    /**
     * Convenience method.
     * 
     * @return HttpSession
     */
    protected HttpSession getSession() {
        return PoHttpSessionUtil.getSession();
    }
}
