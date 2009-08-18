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
import gov.nih.nci.coppa.iso.Cd;
import gov.nih.nci.coppa.iso.Enxp;
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.coppa.iso.St;
import gov.nih.nci.coppa.iso.Tel;
import gov.nih.nci.po.data.bo.EntityStatus;
import gov.nih.nci.po.data.bo.HealthCareFacility;
import gov.nih.nci.po.data.bo.IdentifiedOrganization;
import gov.nih.nci.po.data.bo.Organization;
import gov.nih.nci.po.data.bo.ResearchOrganization;
import gov.nih.nci.po.data.bo.RoleStatus;
import gov.nih.nci.po.service.AnnotatedBeanSearchCriteria;
import gov.nih.nci.po.service.HealthCareFacilityServiceLocal;
import gov.nih.nci.po.service.IdentifiedOrganizationServiceLocal;
import gov.nih.nci.po.service.OrganizationServiceLocal;
import gov.nih.nci.po.service.ResearchOrganizationServiceLocal;
import gov.nih.nci.po.util.PoRegistry;
import gov.nih.nci.po.util.PoXsnapshotHelper;
import gov.nih.nci.services.CorrelationDto;
import gov.nih.nci.services.correlation.HealthCareFacilityDTO;
import gov.nih.nci.services.correlation.ResearchOrganizationDTO;
import gov.nih.nci.services.organization.OrganizationDTO;

import java.util.Iterator;
import java.util.List;

import javax.jms.JMSException;
import javax.naming.Context;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.fiveamsolutions.nci.commons.search.SearchCriteria;

/**
 * @author Scott Miller
 *
 */
@SuppressWarnings("PMD.TooManyMethods")
public class CtepOrganizationImporter extends CtepEntityImporter {
    private static final String NULL_STRING = "null";
    private static final Logger LOG = Logger.getLogger(CtepOrganizationImporter.class);
    private static final String CTEP_EXTENSION = "CTEP";

    /**
     * The value of the 'root' element of a ctep ii for an org.
     */
    public static final String CTEP_ORG_ROOT = "Cancer Therapy Evaluation Program Organization Identifier";

    private final OrganizationServiceLocal orgService = PoRegistry.getOrganizationService();
    private final IdentifiedOrganizationServiceLocal identifiedOrgService = PoRegistry.getInstance().
        getServiceLocator().getIdentifiedOrganizationService();
    private final HealthCareFacilityServiceLocal hcfService = PoRegistry.getInstance().
        getServiceLocator().getHealthCareFacilityService();
    private final ResearchOrganizationServiceLocal roService = PoRegistry.getInstance().
        getServiceLocator().getResearchOrganizationService();

    private Organization persistedCtepOrg;

    /**
     * Constructor.
     * @param ctepContext the initial context providing access to ctep services.
     */
    public CtepOrganizationImporter(Context ctepContext) {
        super(ctepContext);
    }

    /**
     * Get the organization representing ctep.
     * @return the org
     * @throws JMSException on error
     */
    public Organization getCtepOrganization() throws JMSException {
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
     * @param ctepOrgId the org id.
     * @return the org
     * @throws JMSException on error
     */
    public Organization importOrgNoUpdate(Ii ctepOrgId) throws JMSException {
        IdentifiedOrganization identifiedOrg = searchForPreviousRecord(ctepOrgId);
        if (identifiedOrg == null) {
            return importOrganization(ctepOrgId);
        }
        return identifiedOrg.getPlayer();
    }

    /**
     * Method to import an organization based on its ctep id.
     * @param ctepOrgId the ctep id.
     * @throws JMSException on error
     * @return the organization record.
     */
    @SuppressWarnings("PMD.AvoidThrowingRawExceptionTypes")
    public Organization importOrganization(Ii ctepOrgId) throws JMSException {
        try {
            // get org from ctep and convert to local data model
            OrganizationDTO ctepOrgDto = getCtepOrgService().getOrganizationById(ctepOrgId);
            printOrgDataToDebugLog(ctepOrgDto);
            fixCountryCodeSystem(ctepOrgDto.getPostalAddress(), "organization");
            Ii assignedId = ctepOrgDto.getIdentifier();
            Organization ctepOrg = convertToLocalOrg(ctepOrgDto);

            // search for org based on the ctep provided ii
            IdentifiedOrganization identifiedOrg = searchForPreviousRecord(assignedId);
            if (identifiedOrg == null) {
                return createCtepOrg(ctepOrg, assignedId);
            }
            return updateCtepOrg(ctepOrg, identifiedOrg, assignedId);
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
        SearchCriteria<IdentifiedOrganization> sc =
            new AnnotatedBeanSearchCriteria<IdentifiedOrganization>(identifiedOrg);
        List<IdentifiedOrganization> identifiedOrgs = this.identifiedOrgService.search(sc);
        if (identifiedOrgs.isEmpty()) {
            return null;
        }
        return identifiedOrgs.get(0);
    }

    private Organization createCtepOrg(Organization ctepOrg, Ii ctepOrgId) throws JMSException {
        // create the local record
        this.orgService.curate(ctepOrg);

        // create an identified org record
        IdentifiedOrganization identifiedOrg = new IdentifiedOrganization();
        identifiedOrg.setPlayer(ctepOrg);
        identifiedOrg.setScoper(getScoper(ctepOrg, ctepOrgId));
        identifiedOrg.setAssignedIdentifier(ctepOrgId);
        identifiedOrg.setStatus(RoleStatus.ACTIVE);
        this.identifiedOrgService.curate(identifiedOrg);

        // save health care facility record, if it exists
        HealthCareFacility hcf = getCtepHealthCareFacility(ctepOrgId);
        if (hcf != null) {
            hcf.setPlayer(ctepOrg);
            hcf.setStatus(RoleStatus.ACTIVE);
            hcf.getOtherIdentifiers().add(ctepOrgId);
            this.hcfService.curate(hcf);
        }

        ResearchOrganization ro = getCtepResearchOrganization(ctepOrgId);
        if (ro != null) {
            ro.setPlayer(ctepOrg);
            ro.setStatus(RoleStatus.ACTIVE);
            ro.getOtherIdentifiers().add(ctepOrgId);
            this.roService.curate(ro);
        }

        return ctepOrg;
    }

    private Organization updateCtepOrg(Organization ctepOrg, IdentifiedOrganization identifiedOrg, Ii assignedId)
            throws JMSException {
        Organization org = identifiedOrg.getPlayer();

        // copy in new data in to the local org
        if (copyCtepOrgToExistingOrg(ctepOrg, org)) {
            this.orgService.curate(org);
        }

        // Update the status of identified entity if needed
        if (!RoleStatus.ACTIVE.equals(identifiedOrg.getStatus())) {
            identifiedOrg.setStatus(RoleStatus.ACTIVE);
            identifiedOrg.getAssignedIdentifier().setDisplayable(assignedId.getDisplayable());
            identifiedOrg.getAssignedIdentifier().setIdentifierName(assignedId.getIdentifierName());
            identifiedOrg.getAssignedIdentifier().setNullFlavor(assignedId.getNullFlavor());
            identifiedOrg.getAssignedIdentifier().setReliability(assignedId.getReliability());
            identifiedOrg.getAssignedIdentifier().setScope(assignedId.getScope());
            this.identifiedOrgService.curate(identifiedOrg);
        }

        // update health care facility role
        HealthCareFacility hcf = getCtepHealthCareFacility(identifiedOrg.getAssignedIdentifier());
        if (hcf != null) {
            updateHcfRole(org, hcf);
        }

        // update research org role
        ResearchOrganization ro = getCtepResearchOrganization(identifiedOrg.getAssignedIdentifier());
        if (ro != null) {
            updateRoRoles(org, ro);
        }

        return org;
    }

    private void updateRoRoles(Organization org, ResearchOrganization ro) throws JMSException {
        // we don't handle merge here, iterate over all of the ro's nullifying them out, except the last one,
        // which we will update with ctep's data.
        Iterator<ResearchOrganization> i = org.getResearchOrganizations().iterator();
        boolean ctepDataSaved = false;
        while (i.hasNext()) {
            ResearchOrganization persistedRo = i.next();
            if (i.hasNext()) {
                persistedRo.setStatus(RoleStatus.NULLIFIED);
                LOG.warn("Nullifying research organization role during import, curator must have added new data.");
            } else {
                // really only type code can change from ctep, but since funding mech is related, we will
                // clear it out as well
                persistedRo.setFundingMechanism(ro.getFundingMechanism());
                persistedRo.setTypeCode(ro.getTypeCode());
                ctepDataSaved = true;
            }
            this.roService.curate(persistedRo);
        }
        if (!ctepDataSaved) {
            ro.setPlayer(org);
            ro.setStatus(RoleStatus.ACTIVE);
            this.roService.curate(ro);
        }
    }

    private void updateHcfRole(Organization org, HealthCareFacility hcf) throws JMSException {
        // handle HCF - ensure exactly 1 non-null HCF exists if ctep has this as a hcf
        if (org.getHealthCareFacilities().isEmpty()) {
            hcf.setPlayer(org);
            hcf.setStatus(RoleStatus.ACTIVE);
            this.hcfService.curate(hcf);
        } else {
            // can assume exactly one hcf here because our validator only allows 1 non null hcf to exist
            // also, no need to reset the player, because as it is already linked to the persistent object
            // it is set correctly
            HealthCareFacility persistedHcf = org.getHealthCareFacilities().iterator().next();
            persistedHcf.setStatus(RoleStatus.ACTIVE);
            this.hcfService.curate(persistedHcf);
        }
    }

    private Organization getScoper(Organization defaultScoper, Ii ctepOrgId) throws JMSException {
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
    private boolean copyCtepOrgToExistingOrg(Organization ctepOrg, Organization org) {
        boolean change = false;
        // doing this here instead of a 'copy' method in the org itself because all
        // of the relationships are not copied (change requests, roles, etc) The org
        // we are copying from is just the base fields. Also, the org from ctep
        // does not provide fax, phone, tty, url, so those fields are skipped.
        if (!StringUtils.equals(org.getName(), ctepOrg.getName())) {
            org.setName(ctepOrg.getName());
            change = true;
        }
        if (!org.getPostalAddress().contentEquals(ctepOrg.getPostalAddress())) {
            org.getPostalAddress().copy(ctepOrg.getPostalAddress());
            change = true;
        }
        if (!areEmailListsEqual(org.getEmail(), ctepOrg.getEmail())) {
            org.getEmail().clear();
            org.getEmail().addAll(ctepOrg.getEmail());
            change = true;
        }
        org.setStatusCode(EntityStatus.ACTIVE);
        return change;
    }

    private HealthCareFacility getCtepHealthCareFacility(Ii ctepOrgId) {
        try {
            HealthCareFacilityDTO hcfDto = getCtepOrgService().getHealthCareFacility(ctepOrgId);
            printHcf(hcfDto);

            // null out the id and player id, as these are CTEP's references to primary id - we will
            // use our local objects instead of CTEP's.
            hcfDto.setIdentifier(null);
            hcfDto.setPlayerIdentifier(null);
            return (HealthCareFacility) PoXsnapshotHelper.createModel(hcfDto);
        } catch (CTEPEntException e) {
            return null;
        }
    }

    private void printHcf(HealthCareFacilityDTO hcfDto) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("hcf identifiers: ");
            printIdentifierSet(hcfDto, "hcf");
            LOG.debug("hcf.playerii.root: " + hcfDto.getPlayerIdentifier().getRoot());
            LOG.debug("hcf.playerii.extension: " + hcfDto.getPlayerIdentifier().getExtension());
            LOG.debug("hcf.status: " + (hcfDto.getStatus() != null ? hcfDto.getStatus().getCode() : NULL_STRING));
        }
    }

    private ResearchOrganization getCtepResearchOrganization(Ii ctepOrgId) {
        try {
            ResearchOrganizationDTO roDto = getCtepOrgService().getResearchOrganization(ctepOrgId);
            printRo(roDto);

            // null out the id and player id, as these are CTEP's references to primary id - we will
            // use our local objects instead of CTEPs.
            roDto.setIdentifier(null);
            roDto.setPlayerIdentifier(null);
            return (ResearchOrganization) PoXsnapshotHelper.createModel(roDto);
        } catch (CTEPEntException e) {
            return null;
        }
    }

    private void printRo(ResearchOrganizationDTO dto) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("ro identifiers: ");
            printIdentifierSet(dto, "ro");
            LOG.debug("ro.playerii.root: " + dto.getPlayerIdentifier().getRoot());
            LOG.debug("ro.playerii.extension: " + dto.getPlayerIdentifier().getExtension());
            LOG.debug("ro.status: " + (dto.getStatus() != null ? dto.getStatus().getCode() : NULL_STRING));
            LOG.debug("ro.typeCode.code: " + dto.getTypeCode().getCode());
            St displayName = dto.getTypeCode().getDisplayName();
            LOG.debug("ro.typeCode.displayName: " + ((displayName == null) ?  NULL_STRING : displayName.getValue()));
            Cd funding = dto.getFundingMechanism();
            LOG.debug("ro.fundingMech: " + ((funding == null) ?  NULL_STRING : funding.getCode()));
        }
    }

    private void printIdentifierSet(CorrelationDto dto, String prefix) {
        if (dto.getIdentifier() != null) {
            for (Ii ii : dto.getIdentifier().getItem()) {
                LOG.debug(prefix + ".ii.identifierName: " + ii.getIdentifierName());
                LOG.debug(prefix + ".ii.extension: " + ii.getExtension());
                LOG.debug(prefix + ".ii.root: " + ii.getRoot());
            }
        }
    }
}
