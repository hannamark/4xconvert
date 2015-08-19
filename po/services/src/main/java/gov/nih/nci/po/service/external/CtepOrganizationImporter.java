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

import com.fiveamsolutions.nci.commons.search.SearchCriteria;

import gov.nih.nci.common.exceptions.CTEPEntException;
import gov.nih.nci.iso21090.Adxp;
import gov.nih.nci.iso21090.Enxp;
import gov.nih.nci.iso21090.IdentifierReliability;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.iso21090.Tel;
import gov.nih.nci.po.data.bo.AbstractEnhancedOrganizationRole;
import gov.nih.nci.po.data.bo.AbstractOrganization;
import gov.nih.nci.po.data.bo.AbstractResearchOrganization;
import gov.nih.nci.po.data.bo.Address;
import gov.nih.nci.po.data.bo.Alias;
import gov.nih.nci.po.data.bo.Correlation;
import gov.nih.nci.po.data.bo.EntityStatus;
import gov.nih.nci.po.data.bo.HealthCareFacility;
import gov.nih.nci.po.data.bo.HealthCareFacilityCR;
import gov.nih.nci.po.data.bo.IdentifiedOrganization;
import gov.nih.nci.po.data.bo.Organization;
import gov.nih.nci.po.data.bo.OrganizationCR;
import gov.nih.nci.po.data.bo.PhoneNumber;
import gov.nih.nci.po.data.bo.ResearchOrganization;
import gov.nih.nci.po.data.bo.ResearchOrganizationCR;
import gov.nih.nci.po.data.bo.RoleStatus;
import gov.nih.nci.po.service.AnnotatedBeanSearchCriteria;
import gov.nih.nci.po.service.EntityValidationException;
import gov.nih.nci.po.service.HealthCareFacilityServiceLocal;
import gov.nih.nci.po.service.IdentifiedOrganizationServiceLocal;
import gov.nih.nci.po.service.OrganizationCRServiceLocal;
import gov.nih.nci.po.service.OrganizationServiceLocal;
import gov.nih.nci.po.service.ResearchOrganizationServiceLocal;
import gov.nih.nci.po.service.external.CtepMessageBean.OrganizationType;
import gov.nih.nci.po.util.PoHibernateUtil;
import gov.nih.nci.po.util.PoRegistry;
import gov.nih.nci.po.util.PoServiceUtil;
import gov.nih.nci.po.util.PoXsnapshotHelper;
import gov.nih.nci.security.SecurityServiceProvider;
import gov.nih.nci.security.authorization.domainobjects.User;
import gov.nih.nci.security.exceptions.CSException;
import gov.nih.nci.services.correlation.HealthCareFacilityDTO;
import gov.nih.nci.services.correlation.ResearchOrganizationDTO;
import gov.nih.nci.services.organization.OrganizationDTO;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import javax.jms.JMSException;
import javax.naming.Context;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * @author Scott Miller
 * @author Rohit Gupta
 */
@SuppressWarnings({"PMD.TooManyMethods", "PMD.ExcessiveClassLength", "PMD.CyclomaticComplexity" })
public class CtepOrganizationImporter extends CtepEntityImporter {

    private static final Logger LOG = Logger.getLogger(CtepOrganizationImporter.class);
    private static final String CTEP_EXTENSION = "CTEP";

    /**
     * The value of the 'root' element of a ctep ii for an org.
     */
    public static final String CTEP_ORG_ROOT = "2.16.840.1.113883.3.26.6.2";
    private static final String CTEP_USER_LOGIN
            = "ctepecm";

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
     * @throws JMSException              on error
     * @throws EntityValidationException if a validation error occurs anywhere throughout
     * @throws CtepImportException       ctep import exception
     */
    public Organization getCtepOrganization() throws JMSException, EntityValidationException, CtepImportException {
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
     * @throws JMSException              on error
     * @throws EntityValidationException if validation errors occur anywhere throughout
     * @throws CtepImportException       ctep import exception
     */
    public Organization importOrgNoUpdate(Ii ctepOrgId) throws JMSException, EntityValidationException,
            CtepImportException {
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
     * @throws JMSException              on error
     * @throws EntityValidationException if validation errors occur anywhere throughout
     * @throws CtepImportException       ctep import exception
     */
    @SuppressWarnings("PMD.AvoidThrowingRawExceptionTypes")
    public Organization importOrganization(Ii ctepOrgId) throws JMSException, EntityValidationException,
            CtepImportException {
        try {
            // get org from ctep and convert to local data model
            OrganizationDTO ctepOrgDto = getCtepOrgService().getOrganizationById(ctepOrgId);
            printOrgDataToDebugLog(ctepOrgDto);
            Ii assignedId = ctepOrgDto.getIdentifier();
            assignedId.setReliability(IdentifierReliability.VRF);
            Organization ctepOrg = convertToLocalOrg(ctepOrgDto);
            CtepUtils.validateAddress(ctepOrg.getPostalAddress());
            ctepOrg.setStatusCode(EntityStatus.ACTIVE);

            // search for org based on the ctep provided ii
            IdentifiedOrganization identifiedOrg = searchForPreviousRecord(assignedId);
            HealthCareFacility ctepHcf = getCtepHealthCareFacility(assignedId);
            CtepUtils.validateAddresses(ctepHcf);
            ResearchOrganization ctepRo = getCtepResearchOrganization(assignedId);
            CtepUtils.validateAddresses(ctepRo);
            if (isNewCtepOrg(identifiedOrg, ctepHcf, ctepRo)) {
                return createCtepOrg(ctepOrg, assignedId, RoleStatus.ACTIVE);
            }
            // if identified org is null we can generate one
            if (identifiedOrg == null) {
                identifiedOrg = genIdentifiedOrg(ctepHcf, ctepRo, assignedId, ctepOrg, RoleStatus.ACTIVE);
            }
            return updateCtepOrgAndRole(ctepOrg, identifiedOrg, assignedId, ctepHcf, ctepRo);

        } catch (CTEPEntException e) {
            LOG.error(e);
            // ID not found in ctep, therefore we can safely inactivate the entity if it exists locally.
            IdentifiedOrganization identifiedOrg = searchForPreviousRecord(ctepOrgId);
            if (identifiedOrg != null) {
                Organization org = identifiedOrg.getPlayer();
                if (org.getStatusCode().canTransitionTo(EntityStatus.INACTIVE)) {
                    org.setStatusCode(EntityStatus.INACTIVE);
                    this.orgService.curate(org);
                } else {
                    String shortMessage = "not found in ctep, " + org.getStatusCode() + " in po";
                    String message = "Organization " + ctepOrgId + " not found in CTEP ECM. Could not set INACTIVE "
                            + "in PO because transition from " + org.getStatusCode() + " to INACTIVE not allowed.";
                    throw new CtepImportException(shortMessage, message, e);
                }
            }
            return null;
        }
    }

    private boolean isNewCtepOrg(IdentifiedOrganization identifiedOrg,
                                 HealthCareFacility hcf, ResearchOrganization ro) {
        return (identifiedOrg == null && (hcf == null || hcf.getPlayer() == null)
                && (ro == null || ro.getPlayer() == null));
    }

    private void printOrgDataToDebugLog(OrganizationDTO dto) {
        LOG.info("*** Importing ctep org ***");
        LOG.info("org.ii.root: " + dto.getIdentifier().getRoot());
        LOG.info("org.ii.extension: " + dto.getIdentifier().getExtension());
        LOG.info("org.status: " + dto.getStatusCode().getCode());
        for (Enxp xp : dto.getName().getPart()) {
            LOG.info("org.name.value: " + xp.getValue());
        }
        for (Adxp adxp : dto.getPostalAddress().getPart()) {
            LOG.info("org.postalAddress.part.type: " + adxp.getType());
            LOG.info("org.postalAddress.part.value: " + adxp.getValue());
            LOG.info("org.postalAddress.part.code: " + adxp.getCode());
        }
        if (dto.getTelecomAddress() == null) {
            LOG.info("org.telecomAddress: null");
        } else {
            for (Tel tel : dto.getTelecomAddress().getItem()) {
                LOG.info("org.telecomAddress.item.value: " + tel.getValue());
            }
        }
    }

    private Organization convertToLocalOrg(OrganizationDTO dto) {
        // set the id to null, because when we convert to a local org, the identifier value should not be the value
        // provided by ctep, that will get suck in to the assignedId field of the identified org role.
        dto.setIdentifier(null);
        return (Organization) PoXsnapshotHelper.createModel(dto);
    }

    /**
     * Find the persisted instance of a given organization using ctep id.
     *
     * @param ctepOrgId the ctep id
     * @return existing organization
     */
    public IdentifiedOrganization searchForPreviousRecord(Ii ctepOrgId) {
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
            EntityValidationException, CtepImportException {

        User ctepUser = getCtepUser();

        // create the local record
        ctepOrg.setCreatedBy(ctepUser);
        this.orgService.curate(ctepOrg);

        HealthCareFacility hcf = handleHcfUpdate(ctepOrg, ctepOrgId, roleStatus, ctepUser);

        ResearchOrganization ro = handleRoUpdate(ctepOrg, ctepOrgId, roleStatus, ctepUser);

        // create an identified org record
        IdentifiedOrganization identifiedOrg = genIdentifiedOrg(hcf, ro, ctepOrgId, ctepOrg, roleStatus);
        identifiedOrg.setCreatedBy(ctepUser);
        this.identifiedOrgService.curate(identifiedOrg);

        return ctepOrg;
    }

    private HealthCareFacility handleHcfUpdate(Organization ctepOrg, Ii ctepOrgId, RoleStatus roleStatus, User ctepUser)
            throws CtepImportException, JMSException {
        HealthCareFacility hcf = getCtepHealthCareFacility(ctepOrgId);
        if (hcf != null) {
            if (hcf.getId() != null) {
                throw new CtepImportException("org " + ctepOrgId.getExtension() + " not in po but ctep has po hcf id",
                        "CTEP ECM provided a po hcf id " + hcf.getId() + " for the org " + ctepOrgId
                                + " but org not found in database. ECM and PO are out of sync.");
            }
            hcf.setPlayer(ctepOrg);
            hcf.setStatus(roleStatus);
            // CTEP already sets their ID in the identifiers set, but they set the reliability as ISS
            // (since the ID is internally assigned there), but we want it be VRF, so we replace their identifier
            // with our copy of it with the reliability set to VRF
            hcf.getOtherIdentifiers().clear();
            hcf.getOtherIdentifiers().add(ctepOrgId);
            hcf.setCreatedBy(ctepUser);
            this.hcfService.curate(hcf);
        }

        return hcf;
    }

    private ResearchOrganization handleRoUpdate(Organization ctepOrg,
                                                Ii ctepOrgId,
                                                RoleStatus roleStatus,
                                                User ctepUser)
            throws CtepImportException, JMSException {
        ResearchOrganization ro = getCtepResearchOrganization(ctepOrgId);
        if (ro != null) {
            if (ro.getId() != null) {
                throw new CtepImportException("org " + ctepOrgId.getExtension() + " not in po but ctep has po ro id",
                        "CTEP provided a po ro id " + ro.getId() + " for the org " + ctepOrgId
                                + " but org not found in database. ECM and PO are out of sync.");
            }
            ro.setPlayer(ctepOrg);
            ro.setStatus(roleStatus);
            // CTEP already sets their ID in the identifiers set, but they set the reliability as ISS
            // (since the ID is internally assigned there), but we want it be VRF, so we replace their identifier
            // with our copy of it with the reliability set to VRF
            ro.getOtherIdentifiers().clear();
            ro.getOtherIdentifiers().add(ctepOrgId);
            ro.setCreatedBy(ctepUser);
            this.roService.curate(ro);
        }

        return ro;
    }

    private User getCtepUser() {
        User ctepUser = null;

        try {
            ctepUser = SecurityServiceProvider.getUserProvisioningManager("po").getUser(CTEP_USER_LOGIN);
        } catch (CSException e) {
            LOG.warn(String.format("CTEP user with login \"%s\" was not found!", CTEP_USER_LOGIN), e);
        }

        return ctepUser;
    }

    private IdentifiedOrganization genIdentifiedOrg(HealthCareFacility hcf, ResearchOrganization ro, Ii assignedId,
                                                    Organization ctepOrg, RoleStatus roleStatus)
            throws JMSException, EntityValidationException, CtepImportException {

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
            throw new CtepImportException("no role id",
                    "Either the HCF or the RO must be not null so we may get player id.");
        }
        return identifiedOrg;
    }

    private Organization updateCtepOrgAndRole(Organization ctepOrg, IdentifiedOrganization identifiedOrg, Ii assignedId,
                                       HealthCareFacility hcf, ResearchOrganization ro)
            throws JMSException, EntityValidationException, CtepImportException {

        Organization poOrg = identifiedOrg.getPlayer();        

        // update health care facility role        
        if (hcf != null) {
            updateHcfRole(poOrg, hcf, assignedId);
        }

        // update research org role
        if (ro != null) {
            updateRoRole(poOrg, ro, assignedId);
        }

        // update the Organization
        updateOrg(ctepOrg, poOrg);     
        
        // Update IO Role.
        identifiedOrg.setStatus(poOrg.getStatusCode() == EntityStatus.ACTIVE ? RoleStatus.ACTIVE
                : RoleStatus.PENDING);
        identifiedOrg.getAssignedIdentifier().setDisplayable(assignedId.getDisplayable());
        identifiedOrg.getAssignedIdentifier().setIdentifierName(assignedId.getIdentifierName());
        identifiedOrg.getAssignedIdentifier().setNullFlavor(assignedId.getNullFlavor());
        identifiedOrg.getAssignedIdentifier().setReliability(assignedId.getReliability());
        identifiedOrg.getAssignedIdentifier().setScope(assignedId.getScope());
        this.identifiedOrgService.curate(identifiedOrg);
        
        return poOrg;
    }

    /**
     * Either updates po org directly, or creates a CR for a po org based upon incoming ctep data.
     *
     * @param ctepOrg  incoming ctep data
     * @param poOrg current local state
     * @throws JMSException  unable to publish
     * @throws EntityValidationException invalid new state
     */
    private void updateOrg(Organization ctepOrg, Organization poOrg) throws JMSException, EntityValidationException {
        
        // check if only Org name is different
        if (CtepUtils.isOnlyOrgNameDifferent(ctepOrg, poOrg)) { 
            // check if Org can be directly updated
            if (isUpdateOrganizationDirectly(poOrg)) {                
                handleOrgNameChange(ctepOrg, poOrg); // perform Org name change logic
                this.orgService.curate(poOrg);
            } else {
                // otherwise create a CR
                createOrgCR(ctepOrg, poOrg);
            }
        } else if (CtepUtils.isOrganizationDifferent(ctepOrg, poOrg)) {    
            // If address/contact etc changed then check if Org can be directly update or need to create a CR
            CtepUtils.copyOrganization(ctepOrg, poOrg); 
            
            if (isUpdateOrganizationDirectly(poOrg)) {
                this.orgService.curate(poOrg);
            } else {
                // otherwise create a CR
                createOrgCR(ctepOrg, poOrg);
            }            
        }        
    }

    
    private void createOrgCR(Organization ctepOrg, Organization poOrg) throws EntityValidationException {
        OrganizationCR orgCR = new OrganizationCR(poOrg);
        OrganizationDTO oDto = (OrganizationDTO) PoXsnapshotHelper.createSnapshot(ctepOrg);
        oDto.setIdentifier(null);
        PoXsnapshotHelper.copyIntoAbstractModel(oDto, orgCR, AbstractOrganization.class);
        orgCR.setId(null);        
        handleOrgCrPhoneSync(orgCR, poOrg);
        // evict 'poOrg' from Hibernate-Session, so that 'poOrg' changes are not saved(We have CR instead)
        PoHibernateUtil.getCurrentSession().evict(poOrg); 
        PoRegistry.getInstance().getServiceLocator().getOrganizationCRService().create(orgCR);
    }
    
    /**
     * This method is used to handle Phone number synch for OrgCR- we are NOT synching Phone number
     */
    
    private void handleOrgCrPhoneSync(OrganizationCR orgCR, Organization poOrg) {
        orgCR.getPhone().clear(); // clear the list as it contains phone number from oDto (from ctepOrg)
        if (CollectionUtils.isNotEmpty(poOrg.getPhone())) {
            for (PhoneNumber phone : poOrg.getPhone()) {
                orgCR.getPhone().add(new PhoneNumber(phone.getValue()));                
            }
        }        
    }

    /**
     * This method is used to update the PO-HCF directly or create a CR as applicable.
     * It is calling some other private method to get this done.
     */
    private void updateHcfRole(Organization poOrg, HealthCareFacility ctepHcf, Ii assignedId)
            throws JMSException, CtepImportException, EntityValidationException {
        
        HealthCareFacility toSave = null;
        HealthCareFacility poHcf = null;
        
        if (ctepHcf.getId() != null) { 
            // if po-hcfId is present in CTEP-HCF then get the PO-HCF using po-hcfId
            poHcf = hcfService.getById(ctepHcf.getId());
            
            if (poHcf == null) {
                // It means CTEP-HCF has such po-hcfId for which PO-HCF doesn't exist
                throw new CtepImportException("po hcf id " + ctepHcf.getId() + " from ctep not valid", "The po hcf id "
                        + ctepHcf.getId() + " pulled from ctep not found in database. ECM and PO are out of sync.");
            }
            
            // update the HCF in PO
            updateExistingHcfInPO(poHcf, ctepHcf, assignedId);
            
        } else {
            // if po-hcfId is NOT present in CTEP-HCF then get the PO-HCF using CTEP ID            
            poHcf = getHcfFromDbByCtepId(assignedId, "CTEP ID");
            
            if (poHcf != null) {
                // update the HCF in PO 
                updateExistingHcfInPO(poHcf, ctepHcf, assignedId);
            } else {
                // if not found using CTEP ID also, it means its a new HCF
                ctepHcf.setPlayer(poOrg);
                ctepHcf.setStatus(
                            poOrg.getStatusCode() == EntityStatus.ACTIVE ? RoleStatus.ACTIVE : RoleStatus.PENDING);
                if (!ctepHcf.isCtepOwned()) {
                    ctepHcf.getOtherIdentifiers().add(assignedId);
                }
                toSave = ctepHcf;
            }
        }
        
        if (toSave != null) {
            this.hcfService.curate(toSave);
        }        
    }
    
    
    /**
     * This method is used to update the PO-RO directly or create a CR as applicable.
     * It is calling some other private method to get this done.
     */
    private void updateRoRole(Organization poOrg, ResearchOrganization ctepRo, Ii assignedId)
            throws JMSException, CtepImportException, EntityValidationException {
        ResearchOrganization toSave = null;
        ResearchOrganization poRo = null;

        if (ctepRo.getId() != null) {
               // if po-roId is present in CTEP-RO then get the PO-RO using po-roId
            poRo = roService.getById(ctepRo.getId());
            if (poRo == null) {
                    // It means CTEP-RO has such po-roId for which PO-RO doesn't exist
                throw new CtepImportException("po ro id " + ctepRo.getId() + " from ctep not valid", "The po ro id "
                        + ctepRo.getId() + " pulled from ctep not found in database. ECM and PO are out of sync.");
            }
            // update the RO in PO
            updateExistingRoInPO(poRo, ctepRo, assignedId);
            
        } else {
                // if po-roId is NOT present in CTEP-RO then get the PO-RO using CTEP ID   
            poRo = getRoFromDbByCtepId(assignedId, "CTEP ID");
            
            if (poRo != null) {
                updateExistingRoInPO(poRo, ctepRo, assignedId);
            } else {
                    // if not found using CTEP ID also, it means its a new RO
                ctepRo.setPlayer(poOrg);
                ctepRo.setStatus(RoleStatus.ACTIVE);
                if (!ctepRo.isCtepOwned()) {
                    ctepRo.getOtherIdentifiers().add(assignedId);
                }
                toSave = ctepRo;
            }
        }
        
        // only save if something has actually changed, to avoid sending out unneeded JMS messages
        if (toSave != null) {
            this.roService.curate(toSave);
        }
        
    }
    
    
    /**
     * This method has code to actually update the HCF or create a CR.
     */
    private void updateExistingHcfInPO(HealthCareFacility poHcf, HealthCareFacility ctepHcf,
            Ii assignedId) throws JMSException, EntityValidationException {  
       
         if (isOnlyHCFNameChanged(ctepHcf, poHcf)) {     
             handleRoleNameChange(ctepHcf, poHcf);             
             this.hcfService.curate(poHcf); // save the updated HCF in PO            
         } else if (isHCFChanged(ctepHcf, poHcf)) {
             // If address/contact etc changed then check if HCF can be directly update or need to create a CR
             
             copyCtepRoleToExistingRole(ctepHcf, poHcf, assignedId); // copy CTEP-HCF into PO-HCF 
             
             if (isUpdateRoleDirectly(poHcf)) { 
                 if (isNameDifferent(poHcf.getName(), ctepHcf.getName())) {
                     handleRoleNameChange(ctepHcf, poHcf); 
                 }                                 
                 this.hcfService.curate(poHcf); // save the updated HCF in PO
             } else {  
                 // create a CR
                 HealthCareFacilityCR hcfCR = new HealthCareFacilityCR(poHcf);
                 HealthCareFacilityDTO curatedInstanceDto
                         = (HealthCareFacilityDTO) PoXsnapshotHelper.createSnapshot(poHcf);
                 curatedInstanceDto.setIdentifier(null);
                 PoXsnapshotHelper.copyIntoAbstractModel(
                         curatedInstanceDto, hcfCR, AbstractEnhancedOrganizationRole.class);
                 hcfCR.setId(null);
                 hcfCR.setName(ctepHcf.getName());
                 
                 // evict 'poHcf' from Hibernate-Session, so that 'poHcf' changes are not saved(We have CR instead)
                 PoHibernateUtil.getCurrentSession().evict(poHcf); 
                 PoRegistry.getInstance().getServiceLocator().getHealthCareFacilityCRService().create(hcfCR);
             }
         }       
    }
    
    
    /**
     * This method has code to actually update the RO or create a CR.
     */
    private void updateExistingRoInPO(ResearchOrganization poRo, ResearchOrganization ctepRo,
                               Ii assignedId) throws JMSException, EntityValidationException {
            
         if (isOnlyRONameChanged(ctepRo, poRo)) {
             handleRoleNameChange(ctepRo, poRo);             
             this.roService.curate(poRo); // save the updated RO in PO             
         } else if (isROChanged(ctepRo, poRo)) {
            // If anything changed then check if RO should be directly update or need to create a CR
            copyCtepRoleToExistingRole(ctepRo, poRo, assignedId); // copy from CTEP-RO into PO-RO             
            copyROFundingMechanism(ctepRo, poRo); // copy FundingMechanism from CTRP-RO into PO-RO 
            copyROTypeCode(ctepRo, poRo); // copy TypeCode from CTRP-RO into PO-RO 
            
            if (isUpdateRoleDirectly(poRo)) {
                if (isNameDifferent(poRo.getName(), ctepRo.getName())) {
                    handleRoleNameChange(ctepRo, poRo); 
                }  
                this.roService.curate(poRo); // save the updated RO in PO 
             } else {
                 // create a CR
                 ResearchOrganizationCR roCR = new ResearchOrganizationCR(poRo);
                 ResearchOrganizationDTO toSaveDto
                         = (ResearchOrganizationDTO) PoXsnapshotHelper.createSnapshot(poRo);
                 toSaveDto.setIdentifier(null);
                 PoXsnapshotHelper.copyIntoAbstractModel(toSaveDto, roCR, AbstractEnhancedOrganizationRole.class);
                 PoXsnapshotHelper.copyIntoAbstractModel(toSaveDto, roCR, AbstractResearchOrganization.class);
                 roCR.setId(null);
                 roCR.setName(ctepRo.getName());
                 // evict 'poRo' from Hibernate-Session, so that 'poRo' changes are not saved(We have CR instead)
                 PoHibernateUtil.getCurrentSession().evict(poRo); 
                 PoRegistry.getInstance().getServiceLocator().getResearchOrganizationCRService().create(roCR);
             }
        }
    }

    /**
     *  This method is used to check if only the name of CTEP-HCF & PO-HCF is changed. 
     *  @return true if only name changed, otherwise false.
     */
    private boolean isOnlyHCFNameChanged(HealthCareFacility ctepRole, HealthCareFacility role) {
        
        if (isNameDifferent(role.getName(), ctepRole.getName()) 
              && checkAddressSetsEqual(role.getPostalAddresses(), ctepRole.getPostalAddresses())
              && CtepUtils.areEmailListsEqual(role.getEmail(), ctepRole.getEmail())
              && CtepUtils.arePhoneNumberListsEqual(role.getPhone(), ctepRole.getPhone())
              && ObjectUtils.equals(role.getStatus(), ctepRole.getStatus())) {
            return true;
        }
        return false;
    }
    
    /**
     *  This method is used to check if any of the property of CTEP-HCF & PO-HCF is changed. 
     *  @return true if changed, otherwise false.
     */
    private boolean isHCFChanged(HealthCareFacility ctepRole, HealthCareFacility role) {        
        if (isNameDifferent(role.getName(), ctepRole.getName()) 
              || !checkAddressSetsEqual(role.getPostalAddresses(), ctepRole.getPostalAddresses())
              || !CtepUtils.areEmailListsEqual(role.getEmail(), ctepRole.getEmail())
              || !CtepUtils.arePhoneNumberListsEqual(role.getPhone(), ctepRole.getPhone())
              || !ObjectUtils.equals(role.getStatus(), ctepRole.getStatus())) {
            return true;
        }
        return false;
    }
    
    
    /**
     *  This method is used to check if only the name of CTEP-RO & PO-RO is changed. 
     *  @return true if only name changed, otherwise false.
     */
    private boolean isOnlyRONameChanged(ResearchOrganization ctepRo, ResearchOrganization poRo) {
            
        if (isNameDifferent(poRo.getName(), ctepRo.getName()) 
              && checkAddressSetsEqual(poRo.getPostalAddresses(), ctepRo.getPostalAddresses())
              && CtepUtils.areEmailListsEqual(poRo.getEmail(), ctepRo.getEmail())
              && CtepUtils.arePhoneNumberListsEqual(poRo.getPhone(), ctepRo.getPhone())
              && ObjectUtils.equals(poRo.getStatus(), ctepRo.getStatus())
              && isROFundingMechanismOrTypeSame(ctepRo, poRo)) {
            return true;
        }
        return false;
    }
    
    /**
     *  This method is used to check if any of the property of CTEP-RO & PO-RO is changed. 
     *  @return true if changed, otherwise false.
     */
    private boolean isROChanged(ResearchOrganization ctepRo, ResearchOrganization poRo) {        
        if (isNameDifferent(poRo.getName(), ctepRo.getName()) 
              || !checkAddressSetsEqual(poRo.getPostalAddresses(), ctepRo.getPostalAddresses())
              || !CtepUtils.areEmailListsEqual(poRo.getEmail(), ctepRo.getEmail())
              || !CtepUtils.arePhoneNumberListsEqual(poRo.getPhone(), ctepRo.getPhone())
              || !ObjectUtils.equals(poRo.getStatus(), ctepRo.getStatus())
              || !isROFundingMechanismOrTypeSame(ctepRo, poRo)) {
            return true;
        }
        return false;
    }
    
    /**
     *  This method is used to check if only the FundingMechanism & TypeCode
     *  of CTEP-RO & PO-RO are same or not. 
     *  @return true if they are same, otherwise false.
     */
    @SuppressWarnings({"PMD.NPathComplexity" })
    private boolean isROFundingMechanismOrTypeSame(ResearchOrganization ctepRo, ResearchOrganization poRo) {
        Long roFundMechId = poRo.getFundingMechanism() != null ? poRo.getFundingMechanism().getId() : null;
        Long ctepFundMechId = ctepRo.getFundingMechanism() != null ? ctepRo.getFundingMechanism().getId() : null;
        
        Long poTypeCodeId = poRo.getTypeCode() != null ? poRo.getTypeCode().getId() : null;
        Long ctepTypeCodeId = ctepRo.getTypeCode() != null ? ctepRo.getTypeCode().getId() : null;
        
        if (ObjectUtils.equals(roFundMechId, ctepFundMechId)
                && ObjectUtils.equals(poTypeCodeId, ctepTypeCodeId)) {
              return true;
          }
        
        return false;
    }
    
    /**
     *  This method is used to check if OrgRole should be directly updated or not.
     *  @return true if directly updated.
     */
    private boolean isUpdateRoleDirectly(AbstractEnhancedOrganizationRole role) {
        if (getCtepUser() != null) {
            return PoServiceUtil.isEntityEditableByUser(getCtepUser().getLoginName(), 
                    role.getCreatedBy(), role.getOverriddenBy());
        }
        
        return false;        
    }
    
    
    /**
     *  This method is used to check if Organization should be directly updated or not.
     *  @return true if directly updated.
     */
    private boolean isUpdateOrganizationDirectly(Organization org) {
        if (getCtepUser() != null) {
            return PoServiceUtil.isEntityEditableByUser(getCtepUser().getLoginName(), 
                    org.getCreatedBy(), org.getOverriddenBy());
        }
        
        return false;        
    }
    
    private void removeNameFromAlias(List<Alias> aliasList, String name) {
        if (CollectionUtils.isNotEmpty(aliasList)) {
            Iterator<Alias> it = aliasList.iterator();
            while (it.hasNext()) {
                Alias alias = it.next();
                if (alias.getValue().equalsIgnoreCase(name)) { 
                    it.remove();
                }
            }            
        }
    }
    
    /**
     * This method is used to perform logic incase of 'name' change at OrgROLE level.
     */
    private void handleRoleNameChange(AbstractEnhancedOrganizationRole ctepRole,
            AbstractEnhancedOrganizationRole poRole) {
        // step1- The new name should be removed from alias (if present in the list of aliases)
        removeNameFromAlias(poRole.getAlias(), ctepRole.getName()); 
        // step2- The current name should become an alias
        if (PoServiceUtil.aliasIsNotPresent(poRole.getAlias(), poRole.getName())) {
            poRole.getAlias().add(new Alias(poRole.getName()));                 
        }
        // step3- The new name should replace the current name
        poRole.setName(ctepRole.getName());
    }
    
    
    /**
     * This method is used to perform logic incase of 'name' change at Org level.
     */
    private void handleOrgNameChange(Organization ctepOrg, Organization poOrg) {
        // step1- The new name should be removed from alias (if present in the list of aliases)
        removeNameFromAlias(poOrg.getAlias(), ctepOrg.getName()); 
        // step2- The current name should become an alias
        if (PoServiceUtil.aliasIsNotPresent(poOrg.getAlias(), poOrg.getName())) {
            poOrg.getAlias().add(new Alias(poOrg.getName()));                 
        }
        // step3- The new name should replace the current name
        poOrg.setName(ctepOrg.getName());
    }
    
    /**
     * This method is used to copy the CTEP-RO 'Type' into PO-RO
     */
    private void copyROTypeCode(ResearchOrganization ctepRo, ResearchOrganization poRo) {
        Long persistedTypeCodeId = poRo.getTypeCode() != null ? poRo.getTypeCode().getId() : null;
        Long typeCodeId = ctepRo.getTypeCode() != null ? ctepRo.getTypeCode().getId() : null;
        if (!ObjectUtils.equals(persistedTypeCodeId, typeCodeId)) {
            poRo.setTypeCode(ctepRo.getTypeCode());
        }
    }

    /**
     * This method is used to copy the CTEP-RO 'FundingMechanism' into PO-RO
     */
    private void copyROFundingMechanism(ResearchOrganization ctepRo, ResearchOrganization poRo) {
        Long persistedFundingMechanismId = poRo.getFundingMechanism() != null ? poRo
                .getFundingMechanism().getId() : null;
        Long fundingMechanismId = ctepRo.getFundingMechanism() != null ? ctepRo.getFundingMechanism().getId() : null;
        if (!ObjectUtils.equals(persistedFundingMechanismId, fundingMechanismId)) {
            poRo.setFundingMechanism(ctepRo.getFundingMechanism());            
        }
    }


    private Organization getScoper(Organization defaultScoper, Ii ctepOrgId) throws JMSException,
            EntityValidationException, CtepImportException {
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

    @SuppressWarnings({"PMD.CyclomaticComplexity", "PMD.NPathComplexity" })
    private boolean copyCtepRoleToExistingRole(AbstractEnhancedOrganizationRole ctepRole,
                                               AbstractEnhancedOrganizationRole role, Ii assignedId) {
        boolean changed = false;

        // doesn't copy name here as name is handled separately
        
        if (!checkAddressSetsEqual(role.getPostalAddresses(), ctepRole.getPostalAddresses())) {
            role.getPostalAddresses().clear();
            if (ctepRole.getPostalAddresses() != null) {
                role.getPostalAddresses().addAll(ctepRole.getPostalAddresses());
            }
            changed = true;
        }

        if (!CtepUtils.areEmailListsEqual(role.getEmail(), ctepRole.getEmail())) {
            role.getEmail().clear();
            if (ctepRole.getEmail() != null) {
                role.getEmail().addAll(ctepRole.getEmail());
            }
            changed = true;
        }

        if (!CtepUtils.arePhoneNumberListsEqual(role.getPhone(), ctepRole.getPhone())) {
            role.getPhone().clear();
            if (ctepRole.getPhone() != null) {
                role.getPhone().addAll(ctepRole.getPhone());
            }
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
    
    private boolean isNameDifferent(String poName, String ctepName) {
        return !StringUtils.equalsIgnoreCase(poName, ctepName);
    }
    
    private boolean checkAddressSetsEqual(Set<Address> dbAddresses, Set<Address> ctepAddresses) {
        boolean dbAddrEmpty = CollectionUtils.isEmpty(dbAddresses);
        boolean ctepAddrEmpty = CollectionUtils.isEmpty(ctepAddresses);

        // case #1 - both are either null or empty
        if (dbAddrEmpty && ctepAddrEmpty) {
            return true;
        }

        // case #2 - either db or ctep is null or empty, but not both
        if ((dbAddrEmpty && !ctepAddrEmpty) || (!dbAddrEmpty && ctepAddrEmpty)) {
            return false;
        }

        // case #3 - neither is null or empty
        return compareNotEmptyAddressSets(dbAddresses, ctepAddresses);
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

    private HealthCareFacility getCtepHealthCareFacility(Ii ctepOrgId) throws CtepImportException {
        // In case ctep does not have the latest version of iso datatypes which
        // has the VRF reliability, we set it back to ISS before querying and
        // then change it to VRF for the rest of the import process.
        try {
            ctepOrgId.setReliability(IdentifierReliability.ISS);
            HealthCareFacilityDTO hcfDto = getCtepOrgService().getHealthCareFacility(ctepOrgId);
            CtepUtils.converPhoneNumberFormats(hcfDto);
            printHcf(hcfDto);
            return (HealthCareFacility) PoXsnapshotHelper.createModel(hcfDto);
        } catch (CTEPEntException e) {
            LOG.error(e);
            return null;
        } finally {
            ctepOrgId.setReliability(IdentifierReliability.VRF);
        }
    }

    private void printHcf(HealthCareFacilityDTO hcfDto) {
        LOG.info(CtepUtils.toString(hcfDto));
    }

    private ResearchOrganization getCtepResearchOrganization(Ii ctepOrgId) throws CtepImportException {
        // In case ctep does not have the latest version of iso datatypes which
        // has the VRF reliability, we set it back to ISS before querying and
        // then change it to VRF for the rest of the import process.
        try {
            ctepOrgId.setReliability(IdentifierReliability.ISS);
            ResearchOrganizationDTO roDto = getCtepOrgService().getResearchOrganization(ctepOrgId);
            CtepUtils.converPhoneNumberFormats(roDto);
            print(roDto);
            return (ResearchOrganization) PoXsnapshotHelper.createModel(roDto);
        } catch (CTEPEntException e) {
            LOG.error(e);
            return null;
        } finally {
            ctepOrgId.setReliability(IdentifierReliability.VRF);
        }
    }

    private void print(ResearchOrganizationDTO roDto) {
        LOG.info(CtepUtils.toString(roDto));
    }

    /**
     * Handle the nullification with duplicate of an organization from the ctep system.
     *
     * @param ctepOrgId     the ctep org id
     * @param duplicateOfId the ID of the organization of which this is a duplicate
     * @param orgType       organization type
     * @throws JMSException on error
     */
    public void nullifyCtepOrganization(Ii ctepOrgId, Ii duplicateOfId, OrganizationType orgType) throws JMSException {
        if (orgType == OrganizationType.HEALTHCAREFACILITY) {
            HealthCareFacility roleToNullify = getRequiredHcfInDbByCtepId(ctepOrgId, "ctep id");
            HealthCareFacility duplicateOfHcfId = getRequiredHcfInDbByCtepId(duplicateOfId, "duplicate Of ctep id");
            roleToNullify.setStatus(RoleStatus.NULLIFIED);
            roleToNullify.setDuplicateOf(duplicateOfHcfId);
            hcfService.curate(roleToNullify);
        } else if (orgType == OrganizationType.RESEARCHORGANIZATION) {
            ResearchOrganization roleToNullify = getRequiredRoInDbByCtepId(ctepOrgId, "ctep id");
            ResearchOrganization duplicateOfHcfId = getRequiredRoInDbByCtepId(duplicateOfId, "duplicate Of ctep id");
            roleToNullify.setStatus(RoleStatus.NULLIFIED);
            roleToNullify.setDuplicateOf(duplicateOfHcfId);
            roService.curate(roleToNullify);
        } else {
            throw new IllegalArgumentException(orgType.name() + " is an invalid organization type");
        }

    }

    private HealthCareFacility getRequiredHcfInDbByCtepId(Ii ctepOrgId, String logStr) {
        return searchForHcfInDbByCtepId(ctepOrgId, logStr, true);
    }

    private HealthCareFacility getHcfFromDbByCtepId(Ii ctepOrgId, String logStr) {
        return searchForHcfInDbByCtepId(ctepOrgId, logStr, false);
    }

    private HealthCareFacility searchForHcfInDbByCtepId(Ii ctepOrgId, String logStr, boolean required) {
        HealthCareFacility example = new HealthCareFacility();
        example.getOtherIdentifiers().add(ctepOrgId);

        SearchCriteria<HealthCareFacility> sc = new AnnotatedBeanSearchCriteria<HealthCareFacility>(example);
        List<HealthCareFacility> listOfHcfs = hcfService.search(sc);
        checkRoleResults(listOfHcfs, "HealthCareFacility", logStr, ctepOrgId.getExtension(), required);
        if (CollectionUtils.isEmpty(listOfHcfs)) {
            return null;
        }
        return listOfHcfs.get(0);
    }

    private ResearchOrganization getRequiredRoInDbByCtepId(Ii ctepOrgId, String logStr) {
        return searchForRoInDbByCtepId(ctepOrgId, logStr, true);
    }

    private ResearchOrganization getRoFromDbByCtepId(Ii ctepOrgId, String logStr) {
        return searchForRoInDbByCtepId(ctepOrgId, logStr, false);
    }

    private ResearchOrganization searchForRoInDbByCtepId(Ii ctepOrgId, String logStr, boolean required) {
        ResearchOrganization example = new ResearchOrganization();
        example.getOtherIdentifiers().add(ctepOrgId);

        SearchCriteria<ResearchOrganization> sc = new AnnotatedBeanSearchCriteria<ResearchOrganization>(example);
        List<ResearchOrganization> listOfRos = roService.search(sc);
        checkRoleResults(listOfRos, "ResearchOrganization", logStr, ctepOrgId.getExtension(), required);
        if (CollectionUtils.isEmpty(listOfRos)) {
            return null;
        }
        return listOfRos.get(0);

    }

    private void checkRoleResults(List<? extends Correlation> roles, String roleType, String logStr, String ext,
                                  boolean required) {
        if (required && CollectionUtils.isEmpty(roles)) {
            throw new IllegalArgumentException("The " + roleType + " " + logStr + " " + ext
                    + " provided could not be found in our system.");
        } else if (roles.size() > 1) {
            throw new IllegalArgumentException("The " + roleType + " " + logStr + " " + ext
                    + " provided brought back more than 1 result.");
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
