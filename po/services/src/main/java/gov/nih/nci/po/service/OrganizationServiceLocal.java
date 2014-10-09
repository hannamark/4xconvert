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

package gov.nih.nci.po.service;

import com.fiveamsolutions.nci.commons.data.search.PageSortParams;
import com.fiveamsolutions.nci.commons.search.SearchCriteria;

import gov.nih.nci.po.data.bo.Correlation;
import gov.nih.nci.po.data.bo.Organization;
import gov.nih.nci.po.data.bo.OrganizationCR;
import gov.nih.nci.po.data.bo.Overridable;
import gov.nih.nci.security.authorization.domainobjects.User;

import javax.ejb.Local;
import javax.jms.JMSException;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author gax
 * @author Rohit Gupta
 */
@SuppressWarnings({ "PMD.TooManyMethods" })
@Local
public interface OrganizationServiceLocal extends GenericSearchService<Organization, SearchCriteria<Organization>> {

    /**
     * @param org new organization
     * @return id
     * @throws EntityValidationException if validation fails
     * @throws JMSException if JMS fails
     */
    long create(Organization org) throws EntityValidationException, JMSException;
    
    /**
     * @param org new organization
     * @param ctepId ctepId to be set
     * @return id
     * @throws EntityValidationException if validation fails
     * @throws JMSException if JMS fails
     */
    long create(Organization org, String ctepId) throws EntityValidationException, JMSException;

    /**
     * @param id db id to get
     * @return organization with matching id
     */
    Organization getById(long id);

    /**
     * @param entity the entity to validate
     * @return return validation error messages per invalid field path.
     */
    Map<String, String[]> validate(Organization entity);

    /**
     * @param curatedOrg method to curate/accept Organization's w/ EntityStatus.NEW and transition to
     *            EntityStatus.ACTIVE
     * @throws JMSException if problem occurred publishing the announcement message for updates.
     */
    void curate(Organization curatedOrg) throws JMSException;
    
    /**
     * @param curatedOrg method to curate/accept Organization's w/ EntityStatus.NEW and transition to
     *            EntityStatus.ACTIVE
     * @param ctepId ctepId to be set            
     * @throws EntityValidationException if validation fails 
     * @throws JMSException if problem occurred publishing the announcement message for updates.
     */
    void curate(Organization curatedOrg, String ctepId) throws EntityValidationException, JMSException;
    
    /**
     * @param curatedOrg method to curate the Organization without processing its CR.
     * @throws JMSException if problem occurred publishing the announcement message for updates.
     */
    void curateWithoutCRProcessing(Organization curatedOrg) throws JMSException;
    
    /**
     * 
     * @param overridable org to be updated with 'OverriddenBy' value set.
     * @param overriddenBy user who overrode the org.
     */
    @SuppressWarnings("rawtypes")
    void override(Overridable overridable, User overriddenBy);
    
    /**
     * @param o Organization to find associated played roles
     * @return associated played roles
     */
    Set<Correlation> getAssociatedPlayedRoles(Organization o);
    
    /**
     * @param o Organization to find associated scoped roles
     * @return associated scoped roles
     */
    Set<Correlation> getAssociatedScopedRoles(Organization o); 
    
    /**
     * Searches for organizations by the given criteria. High performance search
     * that avoids Hibernate.
     * 
     * @param criteria
     *            OrganizationSearchCriteria
     * @param pageSortParams pageSortParams 
     * @return List<OrganizationSearchDTO>
     */
    List<OrganizationSearchDTO> search(OrganizationSearchCriteria criteria,
            PageSortParams<OrganizationSearchDTO> pageSortParams);

    /**
     * Counts organizations by the given criteria. High performance search
     * that avoids Hibernate.
     * @param criteria OrganizationSearchCriteria
     * @return int count
     */
    long count(OrganizationSearchCriteria criteria);
    
    /**
     * Searches for inbox organizations. High performance search
     * that avoids Hibernate.
     * 
     * 
     * @param pageSortParams pageSortParams 
     * @return List<OrganizationSearchDTO>
     */
    List<OrganizationSearchDTO> getInboxOrgs(PageSortParams<OrganizationSearchDTO> pageSortParams);

    /**
     * Counts inbox organizations. High performance search
     * that avoids Hibernate.
     * 
     * @return int count
     */
    long countInboxOrgs();  
    
    /**
     * Removes the given {@link OrganizationCR} from the change requests list.
     * @param cr OrganizationCR
     */
    void removeChangeRequest(OrganizationCR cr);

    /**
     * Finds a nullified organization that used to have the given CTEP ID. If found, returns the organization's
     * Duplicate Of attribute.
     * @param ctepID given CTEP ID
     * @return Long Duplicate Of attribute
     */
    Long getDuplicateOfNullifiedOrg(String ctepID);

}