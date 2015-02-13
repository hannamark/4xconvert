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
package gov.nih.nci.po.util;

import com.fiveamsolutions.nci.commons.util.HibernateHelper;
import gov.nih.nci.po.data.bo.AbstractIdentifiedEntity;
import gov.nih.nci.po.data.bo.AbstractOrganizationalContact;
import gov.nih.nci.po.data.bo.AbstractPersonRole;
import gov.nih.nci.po.data.bo.Address;
import gov.nih.nci.po.data.bo.ClinicalResearchStaff;
import gov.nih.nci.po.data.bo.Contact;
import gov.nih.nci.po.data.bo.Correlation;
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
import gov.nih.nci.po.service.CurateEntityValidationException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Provides support for merging Structural Roles of a merging Organization. Given the Structural Role (correlation) and
 * its parent Organization, will find a conflicting Structural Role (if appropriate), and make necessary changes (e.g.
 * move data from the Structural Role to the conflicting Structural Role) if any, and nullify the Structural Role with
 * the conflicting role (if appropriate).
 *
 * Terminology:
 * <ul>
 * <li>Going-away - used to indicate an entity (Organization or Structural Role) whose status is being set to Nullified,
 * and is designated a duplicate of another entity of the same type. (Either due to user-initiated organizational
 * merging or automated structural role merging.</li>
 * <li>Surviving - used to indicate an entity (Organization or Structural Role) that is the target duplicate of a
 * Going-away firm. The Surviving entity may also require curation if data from the Going-away entity is copied over to
 * the surviving entity.</li>
 * <li>Conflicting - used to indicate the Structural role for which a Structural Role of the Going-away Organization
 * conflicts with. This will result in the Conflicting Structural Role becoming a Surviving Structural Role.
 * </ul>
 *
 * @author moweis
 *
 */
public final class MergeOrganizationHelperImpl implements MergeOrganizationHelper { // NOPMD

    private UniquePlayerScoperIdentifierValidator uniquePlayerScoperIdentifierValidator
        = new UniquePlayerScoperIdentifierValidator();

    private UniquePlayerScoperValidator uniquePlayerScoperValidator
    = new UniquePlayerScoperValidator();

    private UniqueOversightCommitteeValidator uniqueOversightCommitteeValidator
        = new UniqueOversightCommitteeValidator();

    private UniqueResearchOrganizationValidator uniqueResearchOrganizationValidator
        = new UniqueResearchOrganizationValidator();

    private UniqueOrganizationalContactTitleScoperTypeValidator
       uniqueOrganizationalContactTitleScoperTypeValidator
           = new UniqueOrganizationalContactTitleScoperTypeValidator();
    
    private final UniqueOrganizationalContactPlayerScoperTypeValidator 
        uniqueOrganizationalContactPlayerScoperTypeValidator 
            = new UniqueOrganizationalContactPlayerScoperTypeValidator();

    private HibernateHelper hibernateHelper = PoHibernateUtil.getHibernateHelper();

    /**
     * Set a UniquePlayerScoperIdentifierValidator.
     * Optional, an instance is created by default.
     * @param validator the validator to set.
     */
    public void setUniquePlayerScoperIdentifierValidator(UniquePlayerScoperIdentifierValidator validator) {
        uniquePlayerScoperIdentifierValidator = validator;
    }

    /**
     * Set a UniquePlayerValidator.
     * Optional, an instance is created by default.
     * @param validator the validator to set.
     */
    public void setUniquePlayerScoperValidator(UniquePlayerScoperValidator validator) {
        uniquePlayerScoperValidator = validator;
    }

    /**
     * Set a UniqueOversightCommitteeValidator.
     * Optional, an instance is created by default.
     * @param validator the validator to set.
     */
    public void setUniqueOversightCommitteeValidator(UniqueOversightCommitteeValidator validator) {
        uniqueOversightCommitteeValidator = validator;
    }

    /**
     * Set a UniqueResearchOrganizationValidator.
     * Optional, an instance is created by default.
     * @param validator the validator to set.
     */
    public void setUniqueResearchOrganizationValidator(UniqueResearchOrganizationValidator validator) {
        uniqueResearchOrganizationValidator = validator;
    }

    /**
     * Set a UniqueOrganizationalContactTitleScopeValidator.
     * Optional, an instance is created by default.
     * @param validator the validator to set.
     */
    public void setUniqueOrganizationalContactScoperTypeValidator(
            UniqueOrganizationalContactTitleScoperTypeValidator validator) {
        uniqueOrganizationalContactTitleScoperTypeValidator = validator;
    }

    /**
     * Set a HibernateHelper.
     * Optional, one is provided by default.
     * @param helper the helper to set.
     */
    public void setHibernateHelper(HibernateHelper helper) {
        hibernateHelper = helper;
    }

    /**
     * Resolves conflicting Roles played by an organization.
     *
     * The following rules apply if correlation is of type:
     * <ul>
     * <li>{@link HealthCareFacility}: {@link IllegalArgumentException} is thrown since HealthCareFacilities should
     * never be in conflict</li>
     * <li>{@link OversightCommittee} or {@link IdentifiedOrganization}:
     * <ol>
     * <li>Correlation player is set to the Going-away organization</li>
     * <li>Correlation status is set to NULLIFIED</li>
     * <li>Correlation is set as a duplicate of the conflicting Structural Role</li>
     * </ol>
     * </li>
     * <li>{@link ResearchOrganization}:
     * <ol>
     * <li>Correlation player is set to the Going-away organization</li>
     * <li>Correlation status is set to NULLIFIED</li>
     * <li>Correlation is set as a duplicate of the conflicting Structural Role</li>
     * <li>Correlation contact data is copied over to the conflicting Structural Role</li>
     * </ol>
     * </li>
     * </ul>
     *
     * @param org The Going-away organization.
     * @param correlation The potentially Going-away Structural Role.
     * @return returns a list of {@link Correlation} (Structural Roles) that will require curation.
     */
    @SuppressWarnings("serial")
    public List<Correlation> handleConflictingPlayedRoleCorrelation(Organization org, Correlation correlation) { //NOPMD
        List<Correlation> changes = new ArrayList<Correlation>();
        if (correlation instanceof HealthCareFacility) {
            final String errMsg = "Conflict found for Health Care Facility "
                    + correlation.getId();
            Map<String, String[]> errors = new HashMap<String, String[]>();
            errors.put(
                    "",
                    new String[] {errMsg });
            throw new CurateEntityValidationException(errors);
        } else if (correlation instanceof IdentifiedOrganization) {
            IdentifiedOrganization survivingRole = (IdentifiedOrganization) uniquePlayerScoperIdentifierValidator
                    .getConflictingRole((AbstractIdentifiedEntity<?>) correlation);
            if (survivingRole != null) {
                ((IdentifiedOrganization) correlation).setDuplicateOf(survivingRole);
                nullifyAndSetPlayer(org, correlation);
            }
        } else if (correlation instanceof OversightCommittee) {
            OversightCommittee oc = uniqueOversightCommitteeValidator
                    .getConflictingRole((OversightCommittee) correlation);
            if (oc != null) {
                ((OversightCommittee) correlation).setDuplicateOf(oc);
                nullifyAndSetPlayer(org, correlation);
            }
        } else if (correlation instanceof ResearchOrganization) {
            ResearchOrganization survivingRole = uniqueResearchOrganizationValidator
                    .getConflictingRole((ResearchOrganization) correlation);
            if (survivingRole != null) {
                copyOverResearchOrgRoleData(survivingRole,
                        (ResearchOrganization) correlation);
                ((ResearchOrganization) correlation)
                        .setDuplicateOf(survivingRole);
                changes.add(survivingRole);
                nullifyAndSetPlayer(org, correlation);
            }
        } else {
            throw new IllegalArgumentException("Invalid correlation: " + correlation);
        }
        changes.add(correlation);
        return changes;
    }

    /**
     * Resolves conflicting Roles scoped by an organization.
     *
     * The following rules apply if correlation is of type:
     * <ul>
     * <li>{@link Patient}: {@link IllegalArgumentException} is thrown since Patient should never be in conflict</li>
     * <li>{@link IdentifiedPerson}:
     * <ol>
     * <li>Correlation scoper is set to the Going-away organization</li>
     * <li>Correlation status is set to NULLIFIED</li>
     * <li>Correlation is set as a duplicate of the conflicting Structural Role</li>
     * </ol>
     * </li>
     * <li>{@link HealthCareProvider} or {@link OrganizationalContact} or {@link ClinicalResearchStaff}:
     * <ol>
     * <li>Correlation scoper is set to the Going-away organization</li>
     * <li>Correlation status is set to NULLIFIED</li>
     * <li>Correlation is set as a duplicate of the conflicting Structural Role</li>
     * <li>Correlation contact data is copied over to the conflicting Structural Role</li>
     * </ol>
     * </li>
     * </ul>
     *
     * @param org The Going-away organization.
     * @param correlation The potentially Going-away Structural Role.
     * @return returns a list of {@link Correlation} (Structural Roles) that will require curation.
     */
    public List<Correlation> handleConflictingScopedRoleCorrelation(Organization org, Correlation correlation) { //NOPMD
        List<Correlation> changes = new ArrayList<Correlation>();
        if (correlation instanceof Patient) {
            throw new IllegalArgumentException("Conflict found for Patient " + correlation.getId());
        } else if (correlation instanceof IdentifiedPerson) {
            IdentifiedPerson survivingRole = (IdentifiedPerson) uniquePlayerScoperIdentifierValidator
                    .getConflictingRole((AbstractIdentifiedEntity<?>) correlation);
            ((IdentifiedPerson) correlation).setDuplicateOf(survivingRole);
            nullifyAndSetScoper(org, correlation);
        } else if (correlation instanceof HealthCareProvider) {
            HealthCareProvider survivingRole = (HealthCareProvider) uniquePlayerScoperValidator
                    .getConflictingRole((AbstractPersonRole) correlation);
            if (survivingRole != null) {
                copyOverSurvivingPersonRoleData(survivingRole,
                        (AbstractPersonRole) correlation);
                ((HealthCareProvider) correlation)
                        .setDuplicateOf(survivingRole);
                changes.add(survivingRole);
                nullifyAndSetScoper(org, correlation);
            }
        } else if (correlation instanceof OrganizationalContact) {
            OrganizationalContact survivingRole = (OrganizationalContact) 
                    uniqueOrganizationalContactTitleScoperTypeValidator 
                    .getConflictingRole((AbstractOrganizationalContact) correlation);
            if (survivingRole == null) {
                survivingRole = (OrganizationalContact) uniqueOrganizationalContactPlayerScoperTypeValidator
                        .getConflictingRole((AbstractOrganizationalContact) correlation);
            }
            if (survivingRole != null) {
                copyOverSurvivingPersonRoleData(survivingRole,
                        (AbstractPersonRole) correlation);
                ((OrganizationalContact) correlation)
                        .setDuplicateOf(survivingRole);
                changes.add(survivingRole);
                nullifyAndSetScoper(org, correlation);
            }
        } else if (correlation instanceof ClinicalResearchStaff) {
            ClinicalResearchStaff survivingRole = (ClinicalResearchStaff) uniquePlayerScoperValidator
                    .getConflictingRole((AbstractPersonRole) correlation);
            if (survivingRole != null) {
                copyOverSurvivingPersonRoleData(survivingRole,
                        (AbstractPersonRole) correlation);
                ((ClinicalResearchStaff) correlation)
                        .setDuplicateOf(survivingRole);
                changes.add(survivingRole);
                nullifyAndSetScoper(org, correlation);
            }
        } else {
            throw new IllegalArgumentException("Invalid correlation: " + correlation);
        }
        changes.add(correlation);
        return changes;
    }

    private void nullifyAndSetPlayer(Organization org, Correlation correlation) {
        PlayedRole<Organization> survivingRole = (PlayedRole<Organization>) correlation;
        survivingRole.setPlayer(org);
        correlation.setStatus(RoleStatus.NULLIFIED);
    }

    private void nullifyAndSetScoper(Organization org, Correlation correlation) {
        ScopedRole sr = (ScopedRole) correlation;
        sr.setScoper(org);
        correlation.setStatus(RoleStatus.NULLIFIED);
    }

    /**
     * @param survivingRole
     * @param goingAwayRole
     */
    @SuppressWarnings("PMD.AvoidReassigningParameters")
    private void copyOverSurvivingPersonRoleData(AbstractPersonRole survivingRole, AbstractPersonRole goingAwayRole) {
        //Both survivingRole and goingAwayRole are not currently managed by Hibernate, so a merge is needed
        //to allow to load the contact data which is configured for lazy loading.  This requires a reassigning of the
        //parameters - hence the PMD SuppressWarnings.
        survivingRole = (AbstractPersonRole) hibernateHelper.getCurrentSession().merge(
                survivingRole);
        goingAwayRole = (AbstractPersonRole) hibernateHelper.getCurrentSession().merge(
                goingAwayRole);
        appendContactData(survivingRole.getEmail(), goingAwayRole.getEmail());
        appendContactData(survivingRole.getFax(), goingAwayRole.getFax());
        appendContactData(survivingRole.getPhone(), goingAwayRole.getPhone());
        appendAddressData(survivingRole.getPostalAddresses(), goingAwayRole.getPostalAddresses());
        appendContactData(survivingRole.getTty(), goingAwayRole.getTty());
        appendContactData(survivingRole.getUrl(), goingAwayRole.getUrl());
    }

    /**
     * @param survivingRole
     * @param goingAwayRole
     */
    @SuppressWarnings("PMD.AvoidReassigningParameters")
    private void copyOverResearchOrgRoleData(ResearchOrganization survivingRole, ResearchOrganization goingAwayRole) {
        //Both survivingRole and goingAwayRole are not currently managed by Hibernate, so a merge is needed
        //to allow to load the contact data which is configured for lazy loading.  This requires a reassigning of the
        //parameters - hence the PMD SuppressWarnings.
        survivingRole = (ResearchOrganization) hibernateHelper.getCurrentSession().merge(
                survivingRole);
        goingAwayRole = (ResearchOrganization) hibernateHelper.getCurrentSession().merge(
                goingAwayRole);
        appendContactData(survivingRole.getEmail(), goingAwayRole.getEmail());
        appendContactData(survivingRole.getFax(), goingAwayRole.getFax());
        appendContactData(survivingRole.getPhone(), goingAwayRole.getPhone());
        appendAddressData(survivingRole.getPostalAddresses(), goingAwayRole.getPostalAddresses());
        appendContactData(survivingRole.getTty(), goingAwayRole.getTty());
        appendContactData(survivingRole.getUrl(), goingAwayRole.getUrl());
    }

    private <C extends Contact> List<C> appendContactData(List<C> appendTo, List<C> appendFrom) {
        List<C> resultList = new ArrayList<C>();
        resultList.addAll(appendTo);
        for (C contact : appendFrom) {
            try {
                if (!containsContact(resultList, contact)) {
                    C newContact = (C) contact.getClass().newInstance();
                    newContact.setValue(contact.getValue());
                    resultList.add(newContact);
                }
            } catch (Exception e) {
                throw new IllegalStateException("Unable to merge contact data exception caught: " + e.getMessage(), e);
            }
        }
        return resultList;
    }

    private Set<Address> appendAddressData(Set<Address> appendTo, Set<Address> appendFrom) {
        Set<Address> resultSet = new HashSet<Address>();
        resultSet.addAll(appendTo);
        for (Address address : appendFrom) {
            if (!containsAddress(resultSet, address)) {
                Address newAddress = new Address();
                newAddress.setCityOrMunicipality(address.getCityOrMunicipality());
                newAddress.setCountry(address.getCountry());
                newAddress.setDeliveryAddressLine(address.getDeliveryAddressLine());
                newAddress.setPostalCode(address.getPostalCode());
                newAddress.setStateOrProvince(address.getStateOrProvince());
                newAddress.setStreetAddressLine(address.getStreetAddressLine());
                resultSet.add(newAddress);
            }
        }
        return resultSet;
    }

    private <C extends Contact> boolean containsContact(List<C> resultList, Contact input) {
        for (C contact : resultList) {
            if (contact.getValue().equals(input)) {
                return true;
            }
        }
        return false;
    }

    private boolean containsAddress(Set<Address> resultSet, Address input) {
        for (Address address : resultSet) {
            if (address.contentEquals(input)) {
                return true;
            }
        }
        return false;
    }

}
