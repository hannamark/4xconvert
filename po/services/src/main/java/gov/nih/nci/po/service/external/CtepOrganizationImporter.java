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
package gov.nih.nci.po.service.external;

import gov.nih.nci.common.exceptions.CTEPEntException;
import gov.nih.nci.coppa.iso.Adxp;
import gov.nih.nci.coppa.iso.Enxp;
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.coppa.iso.Tel;
import gov.nih.nci.po.data.bo.AbstractEnhancedOrganizationRole;
import gov.nih.nci.po.data.bo.AbstractOrganization;
import gov.nih.nci.po.data.bo.Address;
import gov.nih.nci.po.data.bo.EntityStatus;
import gov.nih.nci.po.data.bo.HealthCareFacility;
import gov.nih.nci.po.data.bo.IdentifiedOrganization;
import gov.nih.nci.po.data.bo.Organization;
import gov.nih.nci.po.data.bo.OrganizationCR;
import gov.nih.nci.po.data.bo.ResearchOrganization;
import gov.nih.nci.po.data.bo.RoleStatus;
import gov.nih.nci.po.service.AnnotatedBeanSearchCriteria;
import gov.nih.nci.po.service.EntityValidationException;
import gov.nih.nci.po.service.HealthCareFacilityServiceLocal;
import gov.nih.nci.po.service.IdentifiedOrganizationServiceLocal;
import gov.nih.nci.po.service.OrganizationCRServiceLocal;
import gov.nih.nci.po.service.OrganizationServiceLocal;
import gov.nih.nci.po.service.ResearchOrganizationServiceLocal;
import gov.nih.nci.po.service.external.CtepMessageBean.OrganizationType;
import gov.nih.nci.po.util.PoRegistry;
import gov.nih.nci.po.util.PoXsnapshotHelper;
import gov.nih.nci.services.correlation.HealthCareFacilityDTO;
import gov.nih.nci.services.correlation.ResearchOrganizationDTO;
import gov.nih.nci.services.organization.OrganizationDTO;

import java.util.List;
import java.util.Set;

import javax.jms.JMSException;
import javax.naming.Context;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.fiveamsolutions.nci.commons.search.SearchCriteria;

/**
 * @author Scott Miller
 *
 */
@SuppressWarnings({"PMD.TooManyMethods", "PMD.ExcessiveClassLength", "PMD.CyclomaticComplexity" })
public class CtepOrganizationImporter extends CtepEntityImporter {

    private static final Logger LOG = Logger.getLogger(CtepOrganizationImporter.class);
    private static final String CTEP_EXTENSION = "CTEP";

    /**
     * The value of the 'root' element of a ctep ii for an org.
     */
    public static final String CTEP_ORG_ROOT = "Cancer Therapy Evaluation Program Organization Identifier";

    private final OrganizationServiceLocal orgService = PoRegistry.getOrganizationService();
    private final OrganizationCRServiceLocal orgCRService = PoRegistry.getInstance().getServiceLocator()
            .getOrganizationCRService();
    private final IdentifiedOrganizationServiceLocal identifiedOrgService = PoRegistry.getInstance()
            .getServiceLocator().getIdentifiedOrganizationService();
    private final HealthCareFacilityServiceLocal hcfService = PoRegistry.getInstance().getServiceLocator()
            .getHealthCareFacilityService();
    private final ResearchOrganizationServiceLocal roService = PoRegistry.getInstance().getServiceLocator()
            .getResearchOrganizationService();

    private Organization persistedCtepOrg;

    /**
     * Constructor.
     *
     * @param ctepContext the initial context providing access to ctep services.
     */
    public CtepOrganizationImporter(Context ctepContext) {
        super(ctepContext);
    }

    /**
     * Get the organization representing ctep.
     *
     * @return the org
     * @throws JMSException on error
     * @throws EntityValidationException if a validation error occurs anywhere throughout
     */
    public Organization getCtepOrganization() throws JMSException, EntityValidationException {
        if (persistedCtepOrg == null) {
            Ii ctepIi = new Ii();
            ctepIi.setExtension(CTEP_EXTENSION);
            ctepIi.setRoot(CTEP_ORG_ROOT);
            persistedCtepOrg = importOrgNoUpdate(ctepIi);
        }

        return persistedCtepOrg;
    }

    /**
     * Imports the given org but will not update an existing entry.
     *
     * @param ctepOrgId the org id.
     * @return the org
     * @throws JMSException on error
     * @throws EntityValidationException if validation errors occur anywhere throughout
     */
    public Organization importOrgNoUpdate(Ii ctepOrgId) throws JMSException, EntityValidationException {
        IdentifiedOrganization identifiedOrg = searchForPreviousRecord(ctepOrgId);
        if (identifiedOrg == null) {
            return importOrganization(ctepOrgId);
        }
        return identifiedOrg.getPlayer();
    }

    /**
     * Method to import an organization based on its ctep id.
     *
     * @param ctepOrgId the ctep id.
     * @return the organization record.
     * @throws JMSException on error
     * @throws EntityValidationException if validation errors occur anywhere throughout
     */
    @SuppressWarnings("PMD.AvoidThrowingRawExceptionTypes")
    public Organization importOrganization(Ii ctepOrgId) throws JMSException, EntityValidationException {
        try {
            // get org from ctep and convert to local data model
            OrganizationDTO ctepOrgDto = getCtepOrgService().getOrganizationById(ctepOrgId);
            printOrgDataToDebugLog(ctepOrgDto);
            Ii assignedId = ctepOrgDto.getIdentifier();
            Organization ctepOrg = convertToLocalOrg(ctepOrgDto);
            ctepOrg.setStatusCode(EntityStatus.PENDING);

            // search for org based on the ctep provided ii
            IdentifiedOrganization identifiedOrg = searchForPreviousRecord(assignedId);
            HealthCareFacility hcf = getCtepHealthCareFacility(assignedId);
            ResearchOrganization ro = getCtepResearchOrganization(assignedId);

            if (isNewCtepOrg(identifiedOrg, hcf, ro)) {
                return createCtepOrg(ctepOrg, assignedId, RoleStatus.PENDING);
            }
            // if identified org is null we can generate one
            if (identifiedOrg == null) {
                identifiedOrg = genIdentifiedOrg(hcf, ro, assignedId, ctepOrg, RoleStatus.PENDING);
            }
            return updateCtepOrg(ctepOrg, identifiedOrg, assignedId, hcf, ro);

        } catch (CTEPEntException e) {
            // ID not found in ctep, therefore we can safely inactivate the entity if it exists locally.
            IdentifiedOrganization identifiedOrg = searchForPreviousRecord(ctepOrgId);
            if (identifiedOrg != null) {
                Organization org = identifiedOrg.getPlayer();
                org.setStatusCode(EntityStatus.INACTIVE);
                this.orgService.curate(org);
            }
            return null;
        }
    }

    private boolean isNewCtepOrg(IdentifiedOrganization identifiedOrg, HealthCareFacility hcf,
            ResearchOrganization ro) {
        return (identifiedOrg == null && (hcf == null || hcf.getPlayer() == null)
                && (ro == null || ro.getPlayer() == null));
    }

    private void printOrgDataToDebugLog(OrganizationDTO dto) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("*** Importing ctep org ***");
            LOG.debug("org.ii.root: " + dto.getIdentifier().getRoot());
            LOG.debug("org.ii.extension: " + dto.getIdentifier().getExtension());
            LOG.debug("org.status: " + dto.getStatusCode().getCode());
            for (Enxp xp : dto.getName().getPart()) {
                LOG.debug("org.name.value: " + xp.getValue());
            }
            for (Adxp adxp : dto.getPostalAddress().getPart()) {
                LOG.debug("org.postalAddress.part.type: " + adxp.getType());
                LOG.debug("org.postalAddress.part.value: " + adxp.getValue());
                LOG.debug("org.postalAddress.part.code: " + adxp.getCode());
            }
            if (dto.getTelecomAddress() == null) {
                LOG.debug("org.telecomAddress: null");
            } else {
                for (Tel tel : dto.getTelecomAddress().getItem()) {
                    LOG.debug("org.telecomAddress.item.value: " + tel.getValue());
                }
            }
        }
    }

    private Organization convertToLocalOrg(OrganizationDTO dto) {
        // set the id to null, because when we convert to a local org, the identifier value should not be the value
        // provided by ctep, that will get suck in to the assignedId field of the identified org role.
        dto.setIdentifier(null);
        return (Organization) PoXsnapshotHelper.createModel(dto);
    }

    private IdentifiedOrganization searchForPreviousRecord(Ii ctepOrgId) {
        IdentifiedOrganization identifiedOrg = new IdentifiedOrganization();
        identifiedOrg.setAssignedIdentifier(ctepOrgId);
        SearchCriteria<IdentifiedOrganization> sc = new AnnotatedBeanSearchCriteria<IdentifiedOrganization>(
                identifiedOrg);
        List<IdentifiedOrganization> identifiedOrgs = this.identifiedOrgService.search(sc);
        if (identifiedOrgs.isEmpty()) {
            return null;
        }
        return identifiedOrgs.get(0);
    }

    private Organization createCtepOrg(Organization ctepOrg, Ii ctepOrgId, RoleStatus roleStatus) throws JMSException,
            EntityValidationException {
        // create the local record
        this.orgService.curate(ctepOrg);

        HealthCareFacility hcf = getCtepHealthCareFacility(ctepOrgId);
        if (hcf != null) {
            hcf.setPlayer(ctepOrg);
            hcf.setStatus(roleStatus);
            hcf.getOtherIdentifiers().add(ctepOrgId);
            this.hcfService.curate(hcf);
        }

        ResearchOrganization ro = getCtepResearchOrganization(ctepOrgId);
        if (ro != null) {
            ro.setPlayer(ctepOrg);
            ro.setStatus(roleStatus);
            ro.getOtherIdentifiers().add(ctepOrgId);
            this.roService.curate(ro);
        }

        // create an identified org record
        IdentifiedOrganization identifiedOrg = genIdentifiedOrg(hcf, ro,
                ctepOrgId, ctepOrg, roleStatus);
        this.identifiedOrgService.curate(identifiedOrg);

        return ctepOrg;
    }

    private IdentifiedOrganization genIdentifiedOrg(HealthCareFacility hcf, ResearchOrganization ro,
            Ii assignedId, Organization ctepOrg, RoleStatus roleStatus)
        throws JMSException, EntityValidationException {

            IdentifiedOrganization identifiedOrg = new IdentifiedOrganization();
            identifiedOrg.setStatus(roleStatus);
            identifiedOrg.setAssignedIdentifier(assignedId);
            identifiedOrg.setScoper(getScoper(ctepOrg, assignedId));

            // When we are getting a new org from ctep we can use the ctepOrg provided to
            // be the player for the identified org. When we already have a user input org in the db
            // that has not yet been linked to ctep and we are receiving an update we need to use
            // the HCF or the PO player as the identified org player to make sure we create an identified org
            // for the appropriate existing Organization.

            if (ctepOrg != null && ctepOrg.getId() != null) {
                identifiedOrg.setPlayer(ctepOrg);
            } else if (hcf != null && hcf.getId() != null) {
                identifiedOrg.setPlayer(hcf.getPlayer());
            } else if (ro != null && ro.getId() != null) {
                identifiedOrg.setPlayer(ro.getPlayer());
            } else {
                throw new
                IllegalArgumentException("Either the HCF or the RO must be not null so we may get player id.");
            }
        return identifiedOrg;
    }

    private Organization updateCtepOrg(Organization src, IdentifiedOrganization identifiedOrg, Ii assignedId,
            HealthCareFacility hcf, ResearchOrganization ro)
            throws JMSException, EntityValidationException {

        Organization org = identifiedOrg.getPlayer();

        update(src, org);

        // Update the status of identified entity if needed
        if (!RoleStatus.ACTIVE.equals(identifiedOrg.getStatus())) {
            identifiedOrg
                    .setStatus(identifiedOrg.getPlayer().getStatusCode() == EntityStatus.ACTIVE ? RoleStatus.ACTIVE
                            : RoleStatus.PENDING);
            identifiedOrg.getAssignedIdentifier().setDisplayable(assignedId.getDisplayable());
            identifiedOrg.getAssignedIdentifier().setIdentifierName(assignedId.getIdentifierName());
            identifiedOrg.getAssignedIdentifier().setNullFlavor(assignedId.getNullFlavor());
            identifiedOrg.getAssignedIdentifier().setReliability(assignedId.getReliability());
            identifiedOrg.getAssignedIdentifier().setScope(assignedId.getScope());
            this.identifiedOrgService.curate(identifiedOrg);
        }

        // update health care facility role
        if (hcf != null) {
            updateHcfRole(org, hcf, assignedId);
        }

        // update research org role
        if (ro != null) {
            updateRoRoles(org, ro, assignedId);
        }

        return org;
    }

    private void update(Organization src, Organization dest) throws JMSException, EntityValidationException {
        if (EntityStatus.PENDING.equals(dest.getStatusCode())) {
            // copy in new data in to the local org
            if (copy(src, dest)) {
                this.orgService.curate(dest);
            }
        } else {
            OrganizationCR orgCR = new OrganizationCR(dest);
            // we are using the copy method twice. the first time to check if there are
            // any actual changes between ctep and our db and the second time to load data from
            // ctep into a cr object.
            if (copy(src, dest) && copy(src, orgCR)) {
                orgCR.setStatusCode(dest.getStatusCode());
                this.orgCRService.create(orgCR);
            }
        }
    }

    private void updateRoRoles(Organization org, ResearchOrganization ro, Ii assignedId) throws JMSException {
        ResearchOrganization toSave = null;
        if (ro.getId() != null) {
            ResearchOrganization persistedRo = roService.getById(ro.getId());

            toSave = updateFundingMechanism(ro, persistedRo);

            toSave = updateTypeCode(ro, persistedRo);

            if (copyCtepRoleToExistingRole(ro, persistedRo, assignedId)) {
                toSave = persistedRo;
            }
        } else {
            ro.setPlayer(org);
            ro.setStatus(RoleStatus.ACTIVE);
            if (!ro.isCtepOwned()) {
                ro.getOtherIdentifiers().add(assignedId);
            }
            toSave = ro;
        }
        // only save if something has actually changed, to avoid sending out unneeded JMS messages
        if (toSave != null) {
            this.roService.curate(toSave);
        }

    }

    private ResearchOrganization updateTypeCode(ResearchOrganization ro, ResearchOrganization persistedRo) {
        ResearchOrganization toSave = null;
        Long persistedTypeCodeId = persistedRo.getTypeCode() != null ? persistedRo.getTypeCode().getId() : null;
        Long typeCodeId = ro.getTypeCode() != null ? ro.getTypeCode().getId() : null;
        if (!ObjectUtils.equals(persistedTypeCodeId, typeCodeId)) {
            persistedRo.setTypeCode(ro.getTypeCode());
            toSave = persistedRo;
        }
        return toSave;
    }

    private ResearchOrganization updateFundingMechanism(ResearchOrganization ro, ResearchOrganization persistedRo) {
        ResearchOrganization toSave = null;
        Long persistedFundingMechanismId = persistedRo.getFundingMechanism() != null ? persistedRo
                .getFundingMechanism().getId() : null;
        Long fundingMechanismId = ro.getFundingMechanism() != null ? ro.getFundingMechanism().getId() : null;
        if (!ObjectUtils.equals(persistedFundingMechanismId, fundingMechanismId)) {
            persistedRo.setFundingMechanism(ro.getFundingMechanism());
            toSave = persistedRo;
        }
        return toSave;
    }

    private void updateHcfRole(Organization org, HealthCareFacility hcf, Ii assignedId) throws JMSException {
        HealthCareFacility toSave = null;
        if (hcf.getId() != null) {
            HealthCareFacility persistedHcf = hcfService.getById(hcf.getId());
            if (copyCtepRoleToExistingRole(hcf, persistedHcf, assignedId)) {
                toSave = persistedHcf;
            }
        } else {
            hcf.setPlayer(org);
            hcf.setStatus(org.getStatusCode() == EntityStatus.ACTIVE ? RoleStatus.ACTIVE : RoleStatus.PENDING);
            if (!hcf.isCtepOwned()) {
                hcf.getOtherIdentifiers().add(assignedId);
            }
            toSave = hcf;
        }



        // only save if something has actually changed, to avoid sending out unneeded JMS messages
        if (toSave != null) {
            this.hcfService.curate(toSave);
        }
    }

    private Organization getScoper(Organization defaultScoper, Ii ctepOrgId) throws JMSException,
            EntityValidationException {
        Organization scoper;
        if (ctepOrgId.getExtension().equals(CTEP_EXTENSION) && ctepOrgId.getRoot().equals(CTEP_ORG_ROOT)) {
            // we are currently importing ctep, therefore this org is its own scoper.
            scoper = defaultScoper;
        } else {
            // we are not currently importing ctep, so we need to get the org object that represents ctep.
            scoper = getCtepOrganization();
        }
        return scoper;
    }

    /**
     * @return true if data was different and therefore copied over, false if all data was already the same
     */
    private boolean copy(Organization src, AbstractOrganization dest) {
        boolean changed = false;
        // doing this here instead of a 'copy' method in the org itself because all
        // of the relationships are not copied (change requests, roles, etc) The org
        // we are copying from is just the base fields. Also, the org from ctep
        // does not provide fax, phone, tty, url, so those fields are skipped.
        if (!StringUtils.equals(dest.getName(), src.getName())) {
            dest.setName(src.getName());
            changed = true;
        }

        if (copyAddress(src, dest)) {
            changed = true;
        }

        if (!areEmailListsEqual(dest.getEmail(), src.getEmail())) {
            dest.getEmail().clear();
            dest.getEmail().addAll(src.getEmail());
            changed = true;
        }
        return changed;
    }

    private boolean copyAddress(Organization src, AbstractOrganization dest) {
        boolean changed = false;

        if (src.getPostalAddress() == null && dest.getPostalAddress() != null) {
            dest.setPostalAddress(null);
            changed = true;
        } else if (src.getPostalAddress() != null && dest.getPostalAddress() == null) {
            Address address = new Address();
            address.copy(src.getPostalAddress());
            dest.setPostalAddress(address);
            changed = true;
        } else if (!dest.getPostalAddress().contentEquals(src.getPostalAddress())) {
            dest.getPostalAddress().copy(src.getPostalAddress());
            changed = true;
        }

        return changed;
    }

    @SuppressWarnings("PMD.CyclomaticComplexity")
    private boolean copyCtepRoleToExistingRole(AbstractEnhancedOrganizationRole ctepRole,
            AbstractEnhancedOrganizationRole role, Ii assignedId) {
        boolean changed = false;
        if (!StringUtils.equals(role.getName(), ctepRole.getName())) {
            role.setName(ctepRole.getName());
            changed = true;
        }

        if (!checkAddressSetsEqual(role.getPostalAddresses(), ctepRole.getPostalAddresses())) {
            role.getPostalAddresses().clear();
            role.getPostalAddresses().addAll(ctepRole.getPostalAddresses());
            changed = true;
        }

        if (!areEmailListsEqual(role.getEmail(), ctepRole.getEmail())) {
            role.getEmail().clear();
            role.getEmail().addAll(ctepRole.getEmail());
            changed = true;
        }

        if (role.getPlayer().getStatusCode() == EntityStatus.ACTIVE && role.getStatus() != RoleStatus.ACTIVE) {
            role.setStatus(RoleStatus.ACTIVE);
            changed = true;
        }

        if (!role.isCtepOwned()) {
            role.getOtherIdentifiers().add(assignedId);
            changed = true;
        }

        return changed;
    }

    private boolean checkAddressSetsEqual(Set<Address> dbAddresses, Set<Address> ctepAddresses) {
        // case #1 - both are either null or empty
        if (CollectionUtils.isEmpty(dbAddresses) && CollectionUtils.isEmpty(ctepAddresses)) {
            return true;
        }

        // case #2 - either db or ctep is null, but not both
        if (checkForNullAddressSets(dbAddresses, ctepAddresses)) {
            return false;
        }

        // case #3 - neither is null or empty
        return compareNotEmptyAddressSets(dbAddresses, ctepAddresses);
    }

    private boolean checkForNullAddressSets(Set<Address> dbAddresses, Set<Address> ctepAddresses) {
        return (dbAddresses == null && ctepAddresses != null) || (dbAddresses != null && ctepAddresses == null);
    }

    private boolean compareNotEmptyAddressSets(Set<Address> dbAddresses, Set<Address> ctepAddresses) {
        if (!CollectionUtils.isEmpty(dbAddresses) && !CollectionUtils.isEmpty(ctepAddresses)) {

            if (dbAddresses.size() != ctepAddresses.size()) {
                return false;
            }

            for (Address ctepAd : ctepAddresses) {
                if (!isAddressPresent(ctepAd, dbAddresses)) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean isAddressPresent(Address ctepAd, Set<Address> dbAddresses) {
        for (Address dbAd : dbAddresses) {
            if (ctepAd.contentEquals(dbAd)) {
                return true;
            }
        }

        return false;
    }

    private HealthCareFacility getCtepHealthCareFacility(Ii ctepOrgId) {
        try {
            HealthCareFacilityDTO hcfDto = getCtepOrgService().getHealthCareFacility(ctepOrgId);
            printHcf(hcfDto);

            return (HealthCareFacility) PoXsnapshotHelper.createModel(hcfDto);
        } catch (CTEPEntException e) {
            return null;
        }
    }

    private void printHcf(HealthCareFacilityDTO hcfDto) {
        if (LOG.isDebugEnabled()) {
            LOG.debug(CtepUtils.toString(hcfDto));
        }
    }

    private ResearchOrganization getCtepResearchOrganization(Ii ctepOrgId) {
        try {
            ResearchOrganizationDTO roDto = getCtepOrgService().getResearchOrganization(ctepOrgId);
            print(roDto);

            return (ResearchOrganization) PoXsnapshotHelper.createModel(roDto);
        } catch (CTEPEntException e) {
            return null;
        }
    }

    private void print(ResearchOrganizationDTO roDto) {
        if (LOG.isDebugEnabled()) {
            LOG.debug(CtepUtils.toString(roDto));
        }
    }

    /**
     * Handle the nullification with duplicate of an organization from the ctep system.
     * @param ctepOrgId the ctep org id
     * @param duplicateOfId the ID of the organization of which this is a duplicate
     * @param orgType organization type
     * @throws JMSException on error
     */
    public void nullifyCtepOrganization(Ii ctepOrgId, Ii duplicateOfId, OrganizationType orgType) throws JMSException {
        if (orgType == OrganizationType.HEALTHCAREFACILITY) {
            LOG.error(String.format("Attempting to nullify HCF %s with duplicate %s", ctepOrgId.getExtension(),
                    duplicateOfId.getExtension()));
        } else if (orgType == OrganizationType.RESEARCHORGANIZATION) {
            LOG.error(String.format("Attempting to nullify RO %s with duplicate %s", ctepOrgId.getExtension(),
                    duplicateOfId.getExtension()));
        } else {
            throw new IllegalArgumentException(orgType.name() + " is an invalid organization type");
        }

    }

    /**
     * @return {@link OrganizationServiceLocal} bean
     */
    protected OrganizationServiceLocal getOrgService() {
        return orgService;
    }

    /**
     * @return {@link OrganizationCRServiceLocal} bean
     */
    protected OrganizationCRServiceLocal getOrgCRService() {
        return orgCRService;
    }

    /**
     * @return {@link IdentifiedOrganizationServiceLocal} bean
     */
    protected IdentifiedOrganizationServiceLocal getIdentifiedOrgService() {
        return identifiedOrgService;
    }

    /**
     * @return {@link HealthCareFacilityServiceLocal} bean
     */
    protected HealthCareFacilityServiceLocal getHCFService() {
        return hcfService;
    }

    /**
     * @return {@link ResearchOrganizationServiceLocal} bean
     */
    protected ResearchOrganizationServiceLocal getROService() {
        return roService;
    }

}
