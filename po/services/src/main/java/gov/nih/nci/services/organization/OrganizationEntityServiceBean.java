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
package gov.nih.nci.services.organization;

import com.fiveamsolutions.nci.commons.data.search.PageSortParams;
import com.fiveamsolutions.nci.commons.ejb.AuthorizationInterceptor;
import com.fiveamsolutions.nci.commons.util.UsernameHolder;

import gov.nih.nci.coppa.services.LimitOffset;
import gov.nih.nci.coppa.services.TooManyResultsException;
import gov.nih.nci.iso21090.Cd;
import gov.nih.nci.iso21090.EnOn;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.po.data.CurationException;
import gov.nih.nci.po.data.bo.AbstractOrganization;
import gov.nih.nci.po.data.bo.Family;
import gov.nih.nci.po.data.bo.FamilyOrganizationRelationship;
import gov.nih.nci.po.data.bo.Organization;
import gov.nih.nci.po.data.bo.OrganizationCR;
import gov.nih.nci.po.data.convert.EnConverter;
import gov.nih.nci.po.data.convert.IdConverter.OrgIdConverter;
import gov.nih.nci.po.data.convert.IiConverter;
import gov.nih.nci.po.data.convert.StatusCodeConverter;
import gov.nih.nci.po.service.AnnotatedBeanSearchCriteria;
import gov.nih.nci.po.service.EntityValidationException;
import gov.nih.nci.po.service.ExtendedOrganizationSearchCriteria;
import gov.nih.nci.po.service.OrganizationCRServiceLocal;
import gov.nih.nci.po.service.OrganizationServiceLocal;
import gov.nih.nci.po.service.OrganizationSortCriterion;
import gov.nih.nci.po.util.CsmUserUtil;
import gov.nih.nci.po.util.PoHibernateSessionInterceptor;
import gov.nih.nci.po.util.PoXsnapshotHelper;
import gov.nih.nci.security.authorization.domainobjects.User;
import gov.nih.nci.services.Utils;
import gov.nih.nci.services.entity.NullifiedEntityException;
import gov.nih.nci.services.entity.NullifiedEntityInterceptor;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.jboss.ejb3.annotation.SecurityDomain;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;
import javax.jms.JMSException;

import java.util.List;
import java.util.Map;

/**
 * 
 * @author gax1
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@Interceptors({ AuthorizationInterceptor.class, PoHibernateSessionInterceptor.class, NullifiedEntityInterceptor.class })
@SecurityDomain("po")
public class OrganizationEntityServiceBean implements OrganizationEntityServiceRemote {

    private static final String DEFAULT_ROLE_ALLOWED_CLIENT = "client";
    private static final String DEFAULT_ROLE_ALLOWED_GRID_CLIENT = "gridClient";
    private static final Logger LOG = Logger.getLogger(OrganizationEntityServiceBean.class);
    private static int maxResults = Utils.MAX_SEARCH_RESULTS;
    private OrganizationServiceLocal orgService;
    private OrganizationCRServiceLocal orgCRService;

    /**
     * @param max set the maximum 
     * @deprecated only for testing
     */
    @Deprecated
    public static void setMaxResultsReturnedLimit(int max) {
        maxResults = max;
    }
    /**
     * @param svc service, injected
     */
    @EJB
    public void setOrganizationServiceBean(OrganizationServiceLocal svc) {
        this.orgService = svc;
    }

    /**
     * @param svc service, injected
     */
    @EJB
    public void setOrganizationCRServiceBean(OrganizationCRServiceLocal svc) {
        this.orgCRService = svc;
    }

    /**
     * @return orgService that was injected by container.
     */
    public OrganizationServiceLocal getOrganizationServiceBean() {
        return this.orgService;
    }

    /**
     * {@inheritDoc}
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    @RolesAllowed({DEFAULT_ROLE_ALLOWED_CLIENT, DEFAULT_ROLE_ALLOWED_GRID_CLIENT })
    public OrganizationDTO getOrganization(Ii id) throws NullifiedEntityException {
        Organization org = orgService.getById(IiConverter.convertToLong(id));
        return (OrganizationDTO) PoXsnapshotHelper.createSnapshot(org);
    }

    /**
     * {@inheritDoc}
     * @throws CurationException 
     */
    @RolesAllowed({DEFAULT_ROLE_ALLOWED_CLIENT, DEFAULT_ROLE_ALLOWED_GRID_CLIENT })
    @SuppressWarnings("PMD.PreserveStackTrace")
    public Ii createOrganization(OrganizationDTO org) throws EntityValidationException, CurationException {
        Organization orgBO = (Organization) PoXsnapshotHelper.createModel(org);
        try {
            //set createdby
            User currentUser = CsmUserUtil.getUser(UsernameHolder.getUser());
            orgBO.setCreatedBy(currentUser);
            return new OrgIdConverter().convertToIi(orgService.create(orgBO));
        } catch (JMSException e) {
            LOG.error("Problem is JMS, unable to complete requst to create data.", e);
            throw new CurationException("Unable to publish the creation message.");
        }
    }

    /**
     * {@inheritDoc}
     */
    @RolesAllowed({DEFAULT_ROLE_ALLOWED_CLIENT, DEFAULT_ROLE_ALLOWED_GRID_CLIENT })
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public Map<String, String[]> validate(OrganizationDTO org) {
        Organization orgBO = (Organization) PoXsnapshotHelper.createModel(org);
        return orgService.validate(orgBO);
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    @RolesAllowed({DEFAULT_ROLE_ALLOWED_CLIENT, DEFAULT_ROLE_ALLOWED_GRID_CLIENT })
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    @Deprecated
    public List<OrganizationDTO> search(OrganizationDTO organization) {
        Organization orgBO = (Organization) PoXsnapshotHelper.createModel(organization);
        AnnotatedBeanSearchCriteria<Organization> sc = new AnnotatedBeanSearchCriteria<Organization>(orgBO);
        List<Organization> listBOs = getOrganizationServiceBean().search(sc, null);
        return PoXsnapshotHelper.createSnapshotList(listBOs);
    }

    /**
     * {@inheritDoc}
     */
    @RolesAllowed({DEFAULT_ROLE_ALLOWED_CLIENT, DEFAULT_ROLE_ALLOWED_GRID_CLIENT })
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<OrganizationDTO> search(OrganizationDTO organization, LimitOffset pagination)
            throws TooManyResultsException {
        return search(organization, null, pagination);
    }
    
    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    @RolesAllowed({DEFAULT_ROLE_ALLOWED_CLIENT, DEFAULT_ROLE_ALLOWED_GRID_CLIENT })
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<OrganizationDTO> search(OrganizationDTO organization, EnOn familyName, LimitOffset pagination)
            throws TooManyResultsException {
        Organization orgBO = (Organization) PoXsnapshotHelper.createModel(organization);
        String familyNameStr = new EnConverter<EnOn>().convertToString(familyName);
        if (StringUtils.isNotBlank(familyNameStr)) {
            Family fam = new Family();
            fam.setName(familyNameStr);
            FamilyOrganizationRelationship famOrgRel = new FamilyOrganizationRelationship();
            famOrgRel.setFamily(fam);
            orgBO.getFamilyOrganizationRelationships().add(famOrgRel);
        }
        AnnotatedBeanSearchCriteria<Organization> sc = new AnnotatedBeanSearchCriteria<Organization>(orgBO);
        int maxLimit = Math.min(pagination.getLimit(), maxResults + 1);
        PageSortParams<Organization> params = new PageSortParams<Organization>(maxLimit, pagination
                .getOffset(), OrganizationSortCriterion.ORGANIZATION_ID, false);
        List<Organization> listBOs = getOrganizationServiceBean().search(sc, params);
        if (listBOs.size() > maxResults) {
            throw new TooManyResultsException(maxResults);
        }
        return PoXsnapshotHelper.createSnapshotList(listBOs);
    }

    /**
     * {@inheritDoc}
     */
    @RolesAllowed({DEFAULT_ROLE_ALLOWED_CLIENT, DEFAULT_ROLE_ALLOWED_GRID_CLIENT })
    public void updateOrganization(OrganizationDTO proposedState) throws EntityValidationException {
        Long oId = IiConverter.convertToLong(proposedState.getIdentifier());
        if (oId != null) {
            Organization target = orgService.getById(oId);
            if (target != null) {
                OrganizationCR cr = new OrganizationCR(target);
                proposedState.setIdentifier(null);
                PoXsnapshotHelper.copyIntoAbstractModel(proposedState, cr, AbstractOrganization.class);
                cr.setId(null);
                if (cr.getStatusCode() != target.getStatusCode()) {
                    throw new IllegalArgumentException(
                            "use updateOrganizationStatus() to update the statusCode property");
                }
                cr.setStatusCode(target.getStatusCode());
                orgCRService.create(cr);
            } else {
                throw new IllegalArgumentException("Organization could not be found with provided identifier.");
            }
        } else {
            throw new IllegalArgumentException("Organization to be updated did not contain an identifier.");
        }
                
    }

    /**
     * {@inheritDoc}
     */
    @RolesAllowed({DEFAULT_ROLE_ALLOWED_CLIENT, DEFAULT_ROLE_ALLOWED_GRID_CLIENT })
    public void updateOrganizationStatus(Ii targetOrg, Cd statusCode) throws EntityValidationException {
        Long oId = IiConverter.convertToLong(targetOrg);
        if (oId != null) {
            Organization target = orgService.getById(oId);
            if (target != null) {
                // lazy way to clone with stripped hibernate IDs.
                OrganizationDTO tmp = (OrganizationDTO) PoXsnapshotHelper.createSnapshot(target);
                OrganizationCR cr = new OrganizationCR(target);
                PoXsnapshotHelper.copyIntoAbstractModel(tmp, cr, AbstractOrganization.class);
                cr.setId(null);
                cr.setStatusCode(StatusCodeConverter.convertToStatusEnum(statusCode));
                orgCRService.create(cr);
            } else {
                throw new IllegalArgumentException("Organization could not be found with provided identifier.");
            }
        } else {
            throw new IllegalArgumentException("Organization to be updated did not contain an identifier.");
        }    
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<OrganizationDTO> search(
            OrganizationSearchCriteriaDTO criteriaDTO, LimitOffset pagination)
            throws TooManyResultsException {

        ExtendedOrganizationSearchCriteria criteria = new ExtendedOrganizationSearchCriteria(criteriaDTO);
        PageSortParams<Organization> pageSortParams = new PageSortParams<Organization>(
                pagination.getLimit(), pagination.getOffset(),
                OrganizationSortCriterion.ORGANIZATION_ID, false);
        List<Organization> results = getOrganizationServiceBean().search(
                criteria, pageSortParams);
        return PoXsnapshotHelper.createSnapshotList(results);
    }
    
    /**
     * {@inheritDoc}
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    @RolesAllowed({ DEFAULT_ROLE_ALLOWED_CLIENT,
            DEFAULT_ROLE_ALLOWED_GRID_CLIENT })
    public Ii getDuplicateOfNullifiedOrg(String ctepID) {
        return new OrgIdConverter().convertToIi(orgService
                .getDuplicateOfNullifiedOrg(ctepID));
    }

}