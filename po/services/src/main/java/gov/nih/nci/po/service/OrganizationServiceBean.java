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
package gov.nih.nci.po.service;

import gov.nih.nci.po.data.bo.ClinicalResearchStaff;
import gov.nih.nci.po.data.bo.Correlation;
import gov.nih.nci.po.data.bo.EntityStatus;
import gov.nih.nci.po.data.bo.HealthCareFacility;
import gov.nih.nci.po.data.bo.HealthCareProvider;
import gov.nih.nci.po.data.bo.IdentifiedOrganization;
import gov.nih.nci.po.data.bo.IdentifiedPerson;
import gov.nih.nci.po.data.bo.Organization;
import gov.nih.nci.po.data.bo.OrganizationalContact;
import gov.nih.nci.po.data.bo.OversightCommittee;
import gov.nih.nci.po.data.bo.Patient;
import gov.nih.nci.po.data.bo.PlayedRole;
import gov.nih.nci.po.data.bo.ResearchOrganization;
import gov.nih.nci.po.data.bo.RoleStatus;
import gov.nih.nci.po.data.bo.ScopedRole;
import gov.nih.nci.po.util.MergeOrganizationHelper;
import gov.nih.nci.po.util.MergeOrganizationHelperImpl;
import gov.nih.nci.po.util.PoHibernateUtil;
import gov.nih.nci.po.util.UsOrCanadaPhoneHelper;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.jms.JMSException;

import org.apache.commons.collections.MapUtils;
import org.hibernate.Session;

/**
 *
 * @author gax
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class OrganizationServiceBean extends AbstractCuratableEntityServiceBean<Organization> implements
        OrganizationServiceLocal {
    private static final String UNCHECKED = "unchecked";
    private MergeOrganizationHelper mergeOrganizationHelper;

    /**
     * Constructs an {@link OrganizationServiceBean} with the default MergeOrganizationHelper.
     */
    public OrganizationServiceBean() {
        this.mergeOrganizationHelper = new MergeOrganizationHelperImpl();
    }

    /**
     * Constructs an {@link OrganizationServiceBean} with the provided MergeOrganizationHelper instance.
     * @param mergeOrganizationHelper Inject the MergeOrganizationHelper to use
     */
    public OrganizationServiceBean(MergeOrganizationHelper mergeOrganizationHelper) {
        this.mergeOrganizationHelper = mergeOrganizationHelper;
    }

    /**
     * @return the mergeOrganizationHelper
     */
    public MergeOrganizationHelper getMergeOrganizationHelper() {
        return mergeOrganizationHelper;
    }

    /**
     * @param mergeOrganizationHelper the mergeOrganizationHelper to set
     */
    public void setMergeOrganizationHelper(MergeOrganizationHelper mergeOrganizationHelper) {
        this.mergeOrganizationHelper = mergeOrganizationHelper;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public long create(Organization org) throws EntityValidationException, JMSException {
        org.setStatusCode(EntityStatus.PENDING);
        return super.create(org);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Set<Correlation> getAssociatedRoles(Organization o, Session s) {
        Set<Correlation> l = new HashSet<Correlation>();
        // played roles
        l.addAll(getAssociatedPlayedRoles(o, s));
        // scoped roles
        l.addAll(getAssociatedScopedRoles(o, s));
        return l;
    }

    /**
     * {@inheritDoc}
     */
    public Set<Correlation> getAssociatedPlayedRoles(Organization o) {
        return getAssociatedPlayedRoles(o, PoHibernateUtil.getCurrentSession());
    }

    private Set<Correlation> getAssociatedPlayedRoles(Organization o, Session s) {
        Set<Correlation> l = new HashSet<Correlation>();
        if (o == null) {
            return l;
        }
        l.addAll(getAssociatedRoles(o.getId(), HealthCareFacility.class, PLAYER_ID, s));
        l.addAll(getAssociatedRoles(o.getId(), IdentifiedOrganization.class, PLAYER_ID, s));
        l.addAll(getAssociatedRoles(o.getId(), OversightCommittee.class, PLAYER_ID, s));
        l.addAll(getAssociatedRoles(o.getId(), ResearchOrganization.class, PLAYER_ID, s));
        return l;
    }

    /**
     * {@inheritDoc}
     */
    public Set<Correlation> getAssociatedScopedRoles(Organization o) {
        return getAssociatedScopedRoles(o, PoHibernateUtil.getCurrentSession());
    }

    private Set<Correlation> getAssociatedScopedRoles(Organization o, Session s) {
        Set<Correlation> l = new HashSet<Correlation>();
        if (o == null) {
            return l;
        }
        l.addAll(getAssociatedRoles(o.getId(), HealthCareProvider.class, SCOPER_ID, s));
        l.addAll(getAssociatedRoles(o.getId(), IdentifiedOrganization.class, SCOPER_ID, s));
        l.addAll(getAssociatedRoles(o.getId(), ClinicalResearchStaff.class, SCOPER_ID, s));
        l.addAll(getAssociatedRoles(o.getId(), IdentifiedPerson.class, SCOPER_ID, s));
        l.addAll(getAssociatedRoles(o.getId(), OrganizationalContact.class, SCOPER_ID, s));
        l.addAll(getAssociatedRoles(o.getId(), Patient.class, SCOPER_ID, s));
        return l;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void cascadeStatusChangeNullified(Organization org, Session s) throws JMSException {
        if (org.getDuplicateOf() == null) {
            super.cascadeStatusChangeNullified(org, s);
        } else {
            mergeCorrelations(org, s);
        }
    }

    private void mergeCorrelations(Organization org, Session s) throws JMSException {
        Organization dup = org.getDuplicateOf();
        Set<Correlation> associatedRoles = getAssociatedRoles(org, s);
        List<Correlation> changes = new ArrayList<Correlation>();
        for (Correlation correlation : associatedRoles) {
            changes.addAll(mergePlayedRoleCorrelation(org, dup, correlation));
            changes.addAll(mergeScopedRoleCorrelation(org, dup, correlation));
        }
        curateMergedCorrelations(changes);
    }

    private List<Correlation> mergeScopedRoleCorrelation(Organization org, Organization dup, Correlation correlation) {
        List<Correlation> changes = new ArrayList<Correlation>();
        if (correlation instanceof ScopedRole && ((ScopedRole) correlation).getScoper().getId().equals(org.getId())) {
            ScopedRole sr = (ScopedRole) correlation;
            sr.setScoper(dup);
            activateRoleStatusByDupStatus(dup, correlation);
            if (isChangeConflicting(correlation)) {
                changes.addAll(mergeOrganizationHelper.handleConflictingScopedRoleCorrelation(org,
                        correlation));
            } else {
                changes.add(correlation);
            }
        }
        return changes;
    }

    private List<Correlation> mergePlayedRoleCorrelation(Organization org, Organization dup, Correlation correlation) {
        List<Correlation> changes = new ArrayList<Correlation>();
        if (correlation instanceof PlayedRole && ((PlayedRole) correlation).getPlayer() instanceof Organization
                && ((PlayedRole) correlation).getPlayer().getId().equals(org.getId())) {
            PlayedRole<Organization> pr = (PlayedRole<Organization>) correlation;
            pr.setPlayer(dup);
            activateRoleStatusByDupStatus(dup, correlation);
            if (isChangeConflicting(correlation)) {
                changes.addAll(mergeOrganizationHelper.handleConflictingPlayedRoleCorrelation(org,
                        correlation));
            } else {
                changes.add(correlation);
            }
        }
        return changes;
    }

    /**
     * @param correlation
     * @return
     */
    private boolean isChangeConflicting(Correlation correlation) {
        GenericStructrualRoleServiceLocal serviceForRole = getServiceForRole(correlation.getClass());
        // validate()'s behavior ensures that all keys are unique
        Map<String, String[]> correlationErrorMsgs = serviceForRole.validate(correlation);

        //Checks if any of the validation errors are due to bad phone data. (PO-3509).
        if (MapUtils.isNotEmpty(correlationErrorMsgs)) {
            for (String key : correlationErrorMsgs.keySet()) {
                for (String error : correlationErrorMsgs.get(key)) {
                    if (UsOrCanadaPhoneHelper.getPhoneFormatErrorMessage().equals(error)) {
                        throw new CurateEntityValidationException(correlationErrorMsgs);
                    }
                }
            }
        }
        return MapUtils.isNotEmpty(correlationErrorMsgs);
    }

    private void curateMergedCorrelations(List<Correlation> changes) throws JMSException {
        for (Correlation correlation : changes) {
            GenericStructrualRoleServiceLocal serviceForRole = getServiceForRole(correlation.getClass());
            serviceForRole.curate(correlation);
        }
    }

    private void activateRoleStatusByDupStatus(Organization dup, Correlation correlation) {
        if (dup.getStatusCode() == EntityStatus.ACTIVE && correlation.getStatus() == RoleStatus.PENDING
                && isCtepRole(correlation)) {
            correlation.setStatus(RoleStatus.ACTIVE);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressWarnings(UNCHECKED)
    protected void activateCtepRoles(Organization e) throws JMSException {
        Session s = PoHibernateUtil.getCurrentSession();
        for (Correlation x : getAssociatedRoles(e, s)) {
            if (x.getStatus() == RoleStatus.PENDING && isCtepRole(x)) {
                x.setStatus(RoleStatus.ACTIVE);
                GenericStructrualRoleServiceLocal service = getServiceForRole(x.getClass());
                service.curate(x);
            }
        }
    }

}
