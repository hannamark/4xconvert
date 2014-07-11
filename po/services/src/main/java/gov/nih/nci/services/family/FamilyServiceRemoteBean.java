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
package gov.nih.nci.services.family;

import com.fiveamsolutions.nci.commons.data.search.PageSortParams;
import com.fiveamsolutions.nci.commons.ejb.AuthorizationInterceptor;
import gov.nih.nci.coppa.services.LimitOffset;
import gov.nih.nci.coppa.services.TooManyResultsException;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.po.data.bo.Family;
import gov.nih.nci.po.data.bo.FamilyOrganizationRelationship;
import gov.nih.nci.po.data.convert.IiConverter;
import gov.nih.nci.po.service.AnnotatedBeanSearchCriteria;
import gov.nih.nci.po.service.FamilyOrganizationRelationshipServiceLocal;
import gov.nih.nci.po.service.FamilyServiceLocal;
import gov.nih.nci.po.service.FamilySortCriterion;
import gov.nih.nci.po.util.PoHibernateSessionInterceptor;
import gov.nih.nci.po.util.PoXsnapshotHelper;
import gov.nih.nci.services.Utils;
import gov.nih.nci.services.correlation.FamilyOrganizationRelationshipDTO;
import org.jboss.ejb3.annotation.SecurityDomain;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Remote service bean for getting family, searching for Families and family org relationships. 
 * @author mshestopalov
 *
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@Interceptors({ AuthorizationInterceptor.class, PoHibernateSessionInterceptor.class })
@SecurityDomain("po")
public class FamilyServiceRemoteBean implements FamilyServiceRemote {

    private static final String DEFAULT_ROLE_ALLOWED_CLIENT = "client";
    private static int maxResults = Utils.MAX_SEARCH_RESULTS;
    private FamilyServiceLocal famService;
    private FamilyOrganizationRelationshipServiceLocal famOrgRelService;
    
    /**
     * @param svc service, injected
     */
    @EJB
    public void setFamilyServiceBean(FamilyServiceLocal svc) {
        this.famService = svc;
    }

    /**
     * @return famService that was injected by container.
     */
    public FamilyServiceLocal getFamilyServiceBean() {
        return this.famService;
    }
    
    /**
     * @param svc service, injected
     */
    @EJB
    public void setFamilyOrganizationRelationshipServiceBean(FamilyOrganizationRelationshipServiceLocal svc) {
        this.famOrgRelService = svc;
    }

    /**
     * @return famOrgRelService that was injected by container.
     */
    public FamilyOrganizationRelationshipServiceLocal getFamilyOrganizationRelationshipServiceBean() {
        return this.famOrgRelService;
    }
    
    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    @RolesAllowed({DEFAULT_ROLE_ALLOWED_CLIENT })
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<FamilyDTO> search(FamilyDTO family, LimitOffset pagination) throws TooManyResultsException {
        Family famBO = (Family) PoXsnapshotHelper.createModel(family);
        AnnotatedBeanSearchCriteria<Family> sc = new AnnotatedBeanSearchCriteria<Family>(famBO);
        int maxLimit = Math.min(pagination.getLimit(), maxResults + 1);
        PageSortParams<Family> params = new PageSortParams<Family>(maxLimit, pagination
                .getOffset(), FamilySortCriterion.FAMILY_ID, false);
        List<Family> listBOs = getFamilyServiceBean().search(sc, params);
        if (listBOs.size() > maxResults) {
            throw new TooManyResultsException(maxResults);
        }
        return PoXsnapshotHelper.createSnapshotList(listBOs);
    }
    
    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    @RolesAllowed({DEFAULT_ROLE_ALLOWED_CLIENT })
    public List<FamilyOrganizationRelationshipDTO> getActiveRelationships(Long familyId) {
        List<FamilyOrganizationRelationship> listBOs 
            = getFamilyOrganizationRelationshipServiceBean().getActiveRelationships(familyId);
        return PoXsnapshotHelper.createSnapshotList(listBOs);
    }

    /**
     * {@inheritDoc}
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    @RolesAllowed({DEFAULT_ROLE_ALLOWED_CLIENT })
    public FamilyDTO getFamily(Ii id) {
        Family fam = famService.getById(IiConverter.convertToLong(id));
        return (FamilyDTO) PoXsnapshotHelper.createSnapshot(fam);
    }

    /**
     * {@inheritDoc}
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    @RolesAllowed({DEFAULT_ROLE_ALLOWED_CLIENT })
    public Map<Ii, FamilyDTO> getFamilies(Set<Ii> familyOrgRelationshipIis) {
        Map<Long, Family> localMap = famOrgRelService.getFamilies(getFamOrgRelIds(familyOrgRelationshipIis));
        Map<Ii, FamilyDTO> retMap = new LinkedHashMap<Ii, FamilyDTO>();
        for (Ii familyOrgRelationshipIi : familyOrgRelationshipIis) {
            Long extension = IiConverter.convertToLong(familyOrgRelationshipIi);
            retMap.put(familyOrgRelationshipIi, (FamilyDTO) PoXsnapshotHelper.createSnapshot(localMap.get(extension)));
        }
        return retMap;
    }
    
    private Set<Long> getFamOrgRelIds(Set<Ii> familyOrganizationRelationships) {
        Set<Long> famOrgRelIdList = new HashSet<Long>();
        for (Ii ii : familyOrganizationRelationships) {
            famOrgRelIdList.add(IiConverter.convertToLong(ii));
        }
        return famOrgRelIdList;
    }

    @Override
    public FamilyOrganizationRelationshipDTO getFamilyOrganizationRelationship(Ii ii) {
        FamilyOrganizationRelationship famOrgRel =  getFamilyOrganizationRelationshipServiceBean().
                getById(IiConverter.convertToLong(ii));
        return (FamilyOrganizationRelationshipDTO) PoXsnapshotHelper.createSnapshot(famOrgRel);
    }

    @Override
    public FamilyP30DTO getP30Grant(Long id) {
        Family family = getFamilyServiceBean().getById(id);
        return (FamilyP30DTO) PoXsnapshotHelper.createSnapshot(family.getFamilyP30());
    }
}
