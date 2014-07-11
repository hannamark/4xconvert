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
package gov.nih.nci.services.correlation;

import com.fiveamsolutions.nci.commons.data.search.PageSortParams;
import com.fiveamsolutions.nci.commons.search.SearchCriteria;
import com.fiveamsolutions.nci.commons.util.UsernameHolder;
import gov.nih.nci.coppa.services.LimitOffset;
import gov.nih.nci.coppa.services.TooManyResultsException;
import gov.nih.nci.iso21090.Cd;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.po.data.CurationException;
import gov.nih.nci.po.data.bo.AbstractRole;
import gov.nih.nci.po.data.bo.Correlation;
import gov.nih.nci.po.data.bo.CorrelationChangeRequest;
import gov.nih.nci.po.data.convert.CdConverter;
import gov.nih.nci.po.data.convert.IdConverter;
import gov.nih.nci.po.data.convert.IiConverter;
import gov.nih.nci.po.data.convert.IiDsetConverter;
import gov.nih.nci.po.service.AnnotatedBeanSearchCriteria;
import gov.nih.nci.po.service.CorrelationSortCriterion;
import gov.nih.nci.po.service.EntityValidationException;
import gov.nih.nci.po.service.GenericAutoCuratableStructuralRoleServiceLocal;
import gov.nih.nci.po.service.GenericStructrualRoleCRServiceLocal;
import gov.nih.nci.po.service.GenericStructrualRoleServiceLocal;
import gov.nih.nci.po.util.CsmUserUtil;
import gov.nih.nci.po.util.PoXsnapshotHelper;
import gov.nih.nci.security.authorization.domainobjects.User;
import gov.nih.nci.services.CorrelationDto;
import gov.nih.nci.services.Utils;
import org.apache.log4j.Logger;

import javax.annotation.security.RolesAllowed;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.jms.JMSException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Generic superclass for correlation services.
 *
 * @param <T> type
 * @param <CR> the CR type for T
 * @param <DTO> the dto type for T
 */
@SuppressWarnings("PMD.TooManyMethods")
public abstract class AbstractCorrelationServiceBean
        <T extends Correlation, CR extends CorrelationChangeRequest<T>, DTO extends CorrelationDto> {

    /**
     * grid client role.
     */
    protected static final String DEFAULT_ROLE_ALLOWED_GRID_CLIENT = "gridClient";
    /**
     * client role.
     */
    protected static final String DEFAULT_ROLE_ALLOWED_CLIENT = "client";
    private static final Logger LOG = Logger.getLogger(AbstractCorrelationServiceBean.class);

    private static final String UNCHECKED = "unchecked";

    private static int maxResults = Utils.MAX_SEARCH_RESULTS;

    abstract GenericStructrualRoleServiceLocal<T> getLocalService();

    abstract GenericStructrualRoleCRServiceLocal<CR> getLocalCRService();

    abstract IdConverter getIdConverter();

    /**
     * @param max set the maximum
     * @deprecated only for testing
     */
    @Deprecated
    public static void setMaxResultsReturnedLimit(int max) {
        maxResults = max;
    }

    /**
     * Get the search criteria.
     *
     * @param example the example to search based off of.
     * @return the criteria
     */
    protected SearchCriteria<T> getSearchCriteria(T example) {
        return new AnnotatedBeanSearchCriteria<T>(example);
    }

    /**
     * Create a new correlation.
     *
     * @param dto dto
     * @return identifier
     * @throws EntityValidationException on error
     * @throws CurationException if any unrecoverable error occurred
     */
    @RolesAllowed({DEFAULT_ROLE_ALLOWED_CLIENT, DEFAULT_ROLE_ALLOWED_GRID_CLIENT })
    @SuppressWarnings({UNCHECKED, "PMD.PreserveStackTrace" })
    public Ii createCorrelation(DTO dto) throws EntityValidationException, CurationException {
        T po = (T) PoXsnapshotHelper.createModel(dto);
        try {
            User currentUser = CsmUserUtil.getUser(UsernameHolder.getUser());
            ((AbstractRole) po).setCreatedBy(currentUser);
            return getIdConverter().convertToIi(getLocalService().create(po));
        } catch (JMSException e) {
            LOG.error("Problem is JMS, unable to complete requst to create data.", e);
            throw new CurationException("Unable to publish the creation message.");
        }
    }
    
    /**
     * Attempts to create a new correlation in ACTIVE state. If failed, falls back to create the correlation
     * in PENDING state. PO-5962.
     *
     * @param dto dto
     * @return identifier
     * @throws EntityValidationException on error
     * @throws CurationException if any unrecoverable error occurred
     */
    @RolesAllowed({DEFAULT_ROLE_ALLOWED_CLIENT, DEFAULT_ROLE_ALLOWED_GRID_CLIENT })
    @SuppressWarnings({UNCHECKED, "PMD.PreserveStackTrace" })
    public Ii createActiveCorrelation(DTO dto) throws EntityValidationException, CurationException {
        T po = (T) PoXsnapshotHelper.createModel(dto);
        try {
            final GenericStructrualRoleServiceLocal<T> localService = getLocalService();
            if (localService instanceof GenericAutoCuratableStructuralRoleServiceLocal) {
                User currentUser = CsmUserUtil.getUser(UsernameHolder.getUser());
                ((AbstractRole) po).setCreatedBy(currentUser);
                return getIdConverter()
                        .convertToIi(
                                ((GenericAutoCuratableStructuralRoleServiceLocal<T>) localService)
                                        .createActiveWithFallback(po));
            }
            throw new CurationException("The operation is not supported.");
        } catch (JMSException e) {
            LOG.error("Problem is JMS, unable to complete request to create data.", e);
            throw new CurationException("Unable to publish the creation message.");
        }
    }
    

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings(UNCHECKED)
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    @RolesAllowed({DEFAULT_ROLE_ALLOWED_CLIENT, DEFAULT_ROLE_ALLOWED_GRID_CLIENT })
    public DTO getCorrelation(Ii id) {
        T bo = getLocalService().getById(IiConverter.convertToLong(id));
        return (DTO) PoXsnapshotHelper.createSnapshot(bo);
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings(UNCHECKED)
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    @RolesAllowed({DEFAULT_ROLE_ALLOWED_CLIENT, DEFAULT_ROLE_ALLOWED_GRID_CLIENT })
    public List<DTO> getCorrelations(Ii[] ids) {
        Set<Long> longIds = new HashSet<Long>();
        for (Ii id : ids) {
            longIds.add(IiConverter.convertToLong(id));
        }
        List<T> hcps = getLocalService().getByIds(longIds.toArray(new Long[longIds.size()]));
        return PoXsnapshotHelper.createSnapshotList(hcps);
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings(UNCHECKED)
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    @RolesAllowed({DEFAULT_ROLE_ALLOWED_CLIENT, DEFAULT_ROLE_ALLOWED_GRID_CLIENT })
    public List<DTO> getCorrelationsByPlayerIds(Ii[] pids) {
        Set<Long> longIds = new HashSet<Long>();
        for (Ii id : pids) {
            longIds.add(IiConverter.convertToLong(id));
        }
        List<T> correlations = getLocalService().getByPlayerIds(longIds.toArray(new Long[longIds.size()]));
        return PoXsnapshotHelper.createSnapshotList(correlations);
    }


    /**
     * @param dto dto to convert
     * @return validation errors
     */
    @SuppressWarnings(UNCHECKED)
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    @RolesAllowed({DEFAULT_ROLE_ALLOWED_CLIENT, DEFAULT_ROLE_ALLOWED_GRID_CLIENT })
    public Map<String, String[]> validate(DTO dto) {
        T hcpBo = (T) PoXsnapshotHelper.createModel(dto);
        return getLocalService().validate(hcpBo);
    }

    /**
     * @param dto query by example dto
     * @return list of matching dtos
     */
    @SuppressWarnings(UNCHECKED)
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    @RolesAllowed({DEFAULT_ROLE_ALLOWED_CLIENT, DEFAULT_ROLE_ALLOWED_GRID_CLIENT })
    public List<DTO> search(DTO dto) {
        T model = (T) PoXsnapshotHelper.createModel(dto);
        List<T> search = getLocalService().search(getSearchCriteria(model));
        return PoXsnapshotHelper.createSnapshotList(search);
    }

    /**
     * @param dto query by example dto
     * @param pagination the settings for control pagination of results
     * @return list of matching dtos
     * @throws TooManyResultsException when the system's limit is exceeded
     */
    @SuppressWarnings(UNCHECKED)
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    @RolesAllowed({DEFAULT_ROLE_ALLOWED_CLIENT, DEFAULT_ROLE_ALLOWED_GRID_CLIENT })
    public List<DTO> search(DTO dto, LimitOffset pagination) throws TooManyResultsException {
        T model = (T) PoXsnapshotHelper.createModel(dto);
        int maxLimit = Math.min(pagination.getLimit(), maxResults + 1);
        PageSortParams params = new PageSortParams(maxLimit, pagination.getOffset(),
                CorrelationSortCriterion.ID, false);
        List<T> search = getLocalService().search(getSearchCriteria(model), params);
        if (search.size() > maxResults) {
            throw new TooManyResultsException(maxResults);
        }
        return PoXsnapshotHelper.createSnapshotList(search);
    }

    /**
     * {@inheritDoc}
     */
    @RolesAllowed({DEFAULT_ROLE_ALLOWED_CLIENT, DEFAULT_ROLE_ALLOWED_GRID_CLIENT })
    public void updateCorrelation(DTO proposedState) throws EntityValidationException {
        Long pId = IiDsetConverter.convertToId(proposedState.getIdentifier());
        if (pId != null) {
            T target = getLocalService().getById(pId);
            if (target != null) {
                preUpdateValidation(target);
                CR cr = newCR(target);
                copyIntoAbstractModel(proposedState, cr);
                cr.setId(null);
                if (cr.getStatus() != target.getStatus()) {
                    throw new IllegalArgumentException("use updateCorrelationStatus() to update the status property");
                }
                getLocalCRService().create(cr);
            } else {
                throw new IllegalArgumentException("Correlation could not be found with provided identifier.");
            }
        } else {
            throw new IllegalArgumentException("Correlation to be updated did not contain an identifier.");
        }
    }

    /**
     * Override this method to perform custom pre-update validation. 
     * @param target correlation to be update
     */
    @SuppressWarnings("PMD.EmptyMethodInAbstractClassShouldBeAbstract")
    protected void preUpdateValidation(T target) {
        //no-op
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings(UNCHECKED)
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    @RolesAllowed({DEFAULT_ROLE_ALLOWED_CLIENT, DEFAULT_ROLE_ALLOWED_GRID_CLIENT })
    public void updateCorrelationStatus(Ii targetHCP, Cd statusCode) throws EntityValidationException {
        Long pId = IiConverter.convertToLong(targetHCP);
        if (pId != null) {
            T target = getLocalService().getById(pId);
            if (target != null) {
                preUpdateStatusValidation(target);
                // lazy way to clone with stripped hibernate IDs.
                DTO tmp = (DTO) PoXsnapshotHelper.createSnapshot(target);
                CR cr = newCR(target);
                copyIntoAbstractModel(tmp, cr);
                cr.setId(null);
                cr.setStatus(CdConverter.convertToRoleStatus(statusCode));
                getLocalCRService().create(cr);
            } else {
                throw new IllegalArgumentException("Correlation could not be found with provided identifier.");
            }
        } else {
            throw new IllegalArgumentException("Correlation to be updated did not contain an identifier.");
        }    
    }
    
    /**
     * Override this method to perform custom pre-updateStatus validation. 
     * @param target correlation to be update
     */
    @SuppressWarnings("PMD.EmptyMethodInAbstractClassShouldBeAbstract")
    protected void preUpdateStatusValidation(T target) {
        //no-op
    }

    abstract CR newCR(T t);

    abstract void copyIntoAbstractModel(DTO proposedState, CR cr);

}
