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
import gov.nih.nci.iso21090.Ad;
import gov.nih.nci.iso21090.Adxp;
import gov.nih.nci.iso21090.DSet;
import gov.nih.nci.iso21090.Enxp;
import gov.nih.nci.iso21090.IdentifierReliability;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.iso21090.Tel;
import gov.nih.nci.po.data.bo.ClinicalResearchStaff;
import gov.nih.nci.po.data.bo.Email;
import gov.nih.nci.po.data.bo.EntityStatus;
import gov.nih.nci.po.data.bo.HealthCareProvider;
import gov.nih.nci.po.data.bo.IdentifiedOrganization;
import gov.nih.nci.po.data.bo.IdentifiedPerson;
import gov.nih.nci.po.data.bo.Organization;
import gov.nih.nci.po.data.bo.Person;
import gov.nih.nci.po.data.bo.RoleStatus;
import gov.nih.nci.po.data.convert.IdConverter;
import gov.nih.nci.po.service.AnnotatedBeanSearchCriteria;
import gov.nih.nci.po.service.ClinicalResearchStaffServiceLocal;
import gov.nih.nci.po.service.EntityValidationException;
import gov.nih.nci.po.service.HealthCareProviderServiceLocal;
import gov.nih.nci.po.service.IdentifiedPersonServiceLocal;
import gov.nih.nci.po.service.PersonServiceLocal;
import gov.nih.nci.po.util.PoRegistry;
import gov.nih.nci.po.util.PoXsnapshotHelper;
import gov.nih.nci.services.correlation.ClinicalResearchStaffDTO;
import gov.nih.nci.services.correlation.HealthCareProviderDTO;
import gov.nih.nci.services.person.PersonDTO;
import org.apache.log4j.Logger;

import javax.jms.JMSException;
import javax.naming.Context;
import java.util.Iterator;
import java.util.List;

/**
 * @author Scott Miller
 *
 */
@SuppressWarnings({"PMD.TooManyMethods", "PMD.ExcessiveClassLength" })
public class CtepPersonImporter extends CtepEntityImporter {
    /**
     * The root value of the ctep ii's that reference the friendly user known identifier of the person in ctep's system.
     */
    public static final String CTEP_PERSON_ROOT = "2.16.840.1.113883.3.26.6.1";

    private static final Logger LOG = Logger.getLogger(CtepPersonImporter.class);
    private static final String LOG_SEP = " : ";
    private final CtepOrganizationImporter orgImporter;

    private final PersonServiceLocal personService = PoRegistry.getPersonService();
    private final IdentifiedPersonServiceLocal identifiedPersonService = PoRegistry.getInstance().getServiceLocator()
            .getIdentifiedPersonService();
    private final HealthCareProviderServiceLocal hcpService = PoRegistry.getInstance().getServiceLocator()
            .getHealthCareProviderService();
    private final ClinicalResearchStaffServiceLocal crsService = PoRegistry.getInstance().getServiceLocator()
            .getClinicalResearchStaffService();

    /**
     * Constructor.
     *
     * @param ctepContext the initial context providing access to ctep services.
     * @param orgImporter the org importer.
     */
    public CtepPersonImporter(Context ctepContext, CtepOrganizationImporter orgImporter) {
        super(ctepContext);
        this.orgImporter = orgImporter;
    }

    private Organization getCtepOrganization() throws JMSException, EntityValidationException, CtepImportException {
        return this.orgImporter.getCtepOrganization();
    }

    /**
     * Method to import a person based on its ctep id.
     *
     * @param ctepPersonId the ctep id.
     * @throws JMSException on error
     * @throws EntityValidationException if a validation error occurs
     * @return the organization record.
     * @throws CtepImportException ctep import exception
     */
    @SuppressWarnings("PMD.AvoidThrowingRawExceptionTypes")
    public Person importPerson(Ii ctepPersonId) throws JMSException, EntityValidationException, CtepImportException {
        ctepPersonId.setReliability(IdentifierReliability.VRF);
        ctepPersonId.setIdentifierName(IdConverter.IDENTIFIED_PERSON_IDENTIFIER_NAME);
        try {
            // get org from ctep and convert to local data model
            PersonDTO ctepPersonDto = getCtepPersonService().getPersonById(ctepPersonId);      
            CtepUtils.converPhoneNumberFormats(ctepPersonDto.getTelecomAddress());
            comparePersonIi(ctepPersonId, ctepPersonDto.getIdentifier());
            printPersonDataToLog(ctepPersonDto);
            Person ctepPerson = convertToLocalPerson(ctepPersonDto);
            CtepUtils.validateAddress(ctepPerson.getPostalAddress());
            // search for org based on the ctep provided ii
            IdentifiedPerson identifiedPerson = searchForPreviousRecord(ctepPersonId);
            if (identifiedPerson == null) {
                return createCtepPerson(ctepPerson, ctepPersonId);
            }
            comparePersonIi(ctepPersonId, identifiedPerson.getAssignedIdentifier());
            return updateCtepPerson(ctepPerson, identifiedPerson);
        } catch (CTEPEntException e) {
            LOG.error(e);
            // importing an object that is does not exist remotely, so inactivate it if we have it locally.
            IdentifiedPerson identifiedPerson = searchForPreviousRecord(ctepPersonId);
            if (identifiedPerson != null) {
                Person p = identifiedPerson.getPlayer();
                p.setStatusCode(EntityStatus.INACTIVE);
                this.personService.curate(p);
            }
            return null;
        }
    }

    private void comparePersonIi(Ii originalIi, Ii newIi) throws CtepImportException {
        if (originalIi == null || newIi == null) {
            throw new CtepImportException("null CTEP id found",
                    "Person import aborted, null CTEP Id found: " + originalIi + " or " + newIi);
        } else if (!originalIi.getExtension().equals(newIi.getExtension())
                || !originalIi.getRoot().equals(newIi.getRoot())) {
            throw new CtepImportException("mismatch CTEP ids",
                    "Person import aborted, mismatch in CTEP Ids: (" + originalIi.getRoot() + ", "
                    + originalIi.getExtension() + ") and (" + newIi.getRoot() + ", " + newIi.getExtension() + ")");
        }
    }

    /**
     * @return IdentifiedOrganizationServiceLocal bean
     */
    protected IdentifiedPersonServiceLocal getIdentifiedPerService() {
        return identifiedPersonService;
    }

    private void printPersonDataToLog(PersonDTO perDto) {
        LOG.info("Person ii.extension: " + perDto.getIdentifier().getExtension());
        LOG.info("Person ii.root: " + perDto.getIdentifier().getRoot());
        LOG.info("Person status: " + perDto.getStatusCode().getCode());
        LOG.info("Person name:");
        for (Enxp xp : perDto.getName().getPart()) {
            LOG.info("\t" + xp.getType() + LOG_SEP + xp.getValue());
        }

        LOG.info("Person address:");
        for (Adxp axp : perDto.getPostalAddress().getPart()) {
            LOG.info("\t" + axp.getType() + LOG_SEP + axp.getValue() + LOG_SEP + axp.getCode());
        }

        LOG.info("Telecom Addresses:");
        for (Tel obj : perDto.getTelecomAddress().getItem()) {
            LOG.info("\t" + obj.getValue());
        }
    }

    private Person convertToLocalPerson(PersonDTO dto) {
        dto.setIdentifier(null);
        Person p = (Person) PoXsnapshotHelper.createModel(dto);
        if (p.getEmail().isEmpty()) {
            p.getEmail().add(new Email("unknown@example.com"));
        }
        return p;
    }

    private IdentifiedPerson searchForPreviousRecord(Ii ctepPersonId) {
        IdentifiedPerson identifiedPerson = new IdentifiedPerson();
        identifiedPerson.setAssignedIdentifier(new Ii());
        identifiedPerson.getAssignedIdentifier().setExtension(ctepPersonId.getExtension());
        identifiedPerson.getAssignedIdentifier().setRoot(ctepPersonId.getRoot());
        SearchCriteria<IdentifiedPerson> sc = new AnnotatedBeanSearchCriteria<IdentifiedPerson>(identifiedPerson);
        List<IdentifiedPerson> identifiedPeople = this.identifiedPersonService.search(sc);
        if (identifiedPeople.isEmpty()) {
            return null;
        }
        return identifiedPeople.get(0);
    }

    private Person createCtepPerson(Person ctepPerson, Ii ctepPersonId) throws JMSException, EntityValidationException,
            CtepImportException {
        // create the local entity record
        this.personService.curate(ctepPerson);
        createIdentifiedPerson(ctepPerson, ctepPersonId);

        // create records for all health care provider records
        HealthCareProviderDTO hcp = getHcpFromCtep(ctepPersonId);
        if (hcp != null) {
            comparePersonIi(ctepPersonId, hcp.getPlayerIdentifier());
            printHcpDataToLog(hcp);
            createHcp(hcp, ctepPerson);
        }

        // create records for all clinical research staff records.
        ClinicalResearchStaffDTO crs = getCrsFromCtep(ctepPersonId);
        if (crs != null) {
            comparePersonIi(ctepPersonId, crs.getPlayerIdentifier());
            printCrsDataToLog(crs);
            createCrs(crs, ctepPerson);
        }
        return ctepPerson;
    }

    private void createIdentifiedPerson(Person ctepPerson, Ii assignedId) throws JMSException,
            EntityValidationException, CtepImportException {
        IdentifiedPerson identifiedPerson = new IdentifiedPerson();
        identifiedPerson.setPlayer(ctepPerson);
        identifiedPerson.setScoper(getCtepOrganization());
        identifiedPerson.setAssignedIdentifier(new Ii());
        identifiedPerson.getAssignedIdentifier().setDisplayable(assignedId.getDisplayable());
        identifiedPerson.getAssignedIdentifier().setExtension(assignedId.getExtension());
        identifiedPerson.getAssignedIdentifier().setIdentifierName(assignedId.getIdentifierName());
        identifiedPerson.getAssignedIdentifier().setNullFlavor(assignedId.getNullFlavor());
        identifiedPerson.getAssignedIdentifier().setReliability(assignedId.getReliability());
        identifiedPerson.getAssignedIdentifier().setRoot(assignedId.getRoot());
        identifiedPerson.getAssignedIdentifier().setScope(assignedId.getScope());
        identifiedPerson.setStatus(RoleStatus.ACTIVE);

        this.identifiedPersonService.curate(identifiedPerson);
    }

    private Person updateCtepPerson(Person ctepPerson, IdentifiedPerson identifiedPerson)
            throws JMSException, EntityValidationException, CtepImportException {
        Person p = identifiedPerson.getPlayer();

        // copy updated data in to the local person
        copyCtepPersonToExistingPerson(ctepPerson, p);
        this.personService.curate(p);

        // update the hcp role
        HealthCareProviderDTO hcpDto = getHcpFromCtep(identifiedPerson.getAssignedIdentifier());
        if (hcpDto != null) {
            comparePersonIi(identifiedPerson.getAssignedIdentifier(), hcpDto.getPlayerIdentifier());
            updateHcpRoles(p, hcpDto);
        }

        // update the crs role
        ClinicalResearchStaffDTO crsDto = getCrsFromCtep(identifiedPerson.getAssignedIdentifier());
        if (crsDto != null) {
            comparePersonIi(identifiedPerson.getAssignedIdentifier(), crsDto.getPlayerIdentifier());
            updateCrsRoles(p, crsDto);
        }


        return p;
    }

    private void updateCrsRoles(Person p, ClinicalResearchStaffDTO crsDto) throws JMSException,
            EntityValidationException, CtepImportException {
        IdentifiedOrganization  identifiedOrg = this.orgImporter.searchForPreviousRecord(crsDto.getScoperIdentifier());
        Long orgId = identifiedOrg == null ? -1L : identifiedOrg.getPlayer().getId();
        Iterator<ClinicalResearchStaff> i = p.getClinicalResearchStaff().iterator();
        boolean ctepDataSaved = false;
        while (i.hasNext()) {
            ClinicalResearchStaff persistedCrs = i.next();
            if (ctepDataSaved || !orgId.equals(persistedCrs.getScoper().getId())) {
                persistedCrs.setStatus(RoleStatus.NULLIFIED);
                LOG.warn("Nullifying clinical research staff role during import, curator must have added new data.");
            } else {
                // update the CRS by taking the dto from ctep, converting it to our data model, then copying
                // in the values by hand

                // store ii's we will need
                Ii scoperIi = crsDto.getScoperIdentifier();

                // null out the ii's our converters cant handle because they are ctep id's and not our db ids.
                crsDto.setIdentifier(null);
                crsDto.setPlayerIdentifier(null);
                crsDto.setScoperIdentifier(null);

                // convert to local db model
                ClinicalResearchStaff crs = (ClinicalResearchStaff) PoXsnapshotHelper.createModel(crsDto);

                // set fields that we need to, clear the fields not provided by ctep
                persistedCrs.setStatus(getPersonRoleStatus(persistedCrs.getScoper()));
                persistedCrs.getEmail().clear();
                persistedCrs.getEmail().addAll(crs.getEmail());
                persistedCrs.getFax().clear();
                persistedCrs.getFax().addAll(crs.getFax());
                persistedCrs.getPhone().clear();
                persistedCrs.getPhone().addAll(crs.getPhone());
                persistedCrs.setScoper(this.orgImporter.importOrgNoUpdate(scoperIi));
                persistedCrs.getPostalAddresses().clear();
                persistedCrs.getPostalAddresses().addAll(crs.getPostalAddresses());
                persistedCrs.getTty().clear();
                persistedCrs.getUrl().clear();
                ctepDataSaved = true;
            }
            this.crsService.curate(persistedCrs);
        }
        if (!ctepDataSaved) {
            createCrs(crsDto, p);
        }
    }

    private void updateHcpRoles(Person p, HealthCareProviderDTO hcpDto) throws JMSException, EntityValidationException, 
            CtepImportException {
        IdentifiedOrganization  identifiedOrg = this.orgImporter.searchForPreviousRecord(hcpDto.getScoperIdentifier());
        Long orgId = identifiedOrg == null ? -1L : identifiedOrg.getPlayer().getId();
        Iterator<HealthCareProvider> i = p.getHealthCareProviders().iterator();
        boolean ctepDataSaved = false;
        while (i.hasNext()) {
            HealthCareProvider persistedHcp = i.next();
            if (ctepDataSaved || !orgId.equals(persistedHcp.getScoper().getId())) {
                persistedHcp.setStatus(RoleStatus.NULLIFIED);
                LOG.warn("Nullifying health care provider role during import, curator must have added new data.");
            } else {
                // update the HCP by taking the dto from ctep, converting it to our data model, then copying
                // in the values by hand

                // store ii's we will need
                Ii scoperIi = hcpDto.getScoperIdentifier();

                // null out the ii's our converters can't handle because they are ctep id's and not our db ids.
                hcpDto.setIdentifier(null);
                hcpDto.setPlayerIdentifier(null);
                hcpDto.setScoperIdentifier(null);

                // convert to local db model
                HealthCareProvider hcp = (HealthCareProvider) PoXsnapshotHelper.createModel(hcpDto);
                // set fields that we need to, clear the fields not provided by ctep
                persistedHcp.setStatus(getPersonRoleStatus(persistedHcp.getScoper()));
                persistedHcp.setCertificateLicenseText(hcp.getCertificateLicenseText());
                persistedHcp.getEmail().clear();
                persistedHcp.getEmail().addAll(hcp.getEmail());
                persistedHcp.getFax().clear();
                persistedHcp.getFax().addAll(hcp.getFax());
                persistedHcp.getPhone().clear();
                persistedHcp.getPhone().addAll(hcp.getPhone());
                persistedHcp.setScoper(this.orgImporter.importOrgNoUpdate(scoperIi));
                persistedHcp.getPostalAddresses().clear();
                persistedHcp.getPostalAddresses().addAll(hcp.getPostalAddresses());
                persistedHcp.getTty().clear();
                persistedHcp.getUrl().clear();
                ctepDataSaved = true;
            }
            this.hcpService.curate(persistedHcp);
        }
        if (!ctepDataSaved) {
            createHcp(hcpDto, p);
        }
    }

    private void copyCtepPersonToExistingPerson(Person ctepPerson, Person p) {
        // doing this here instead of a 'copy' method in the person itself because all
        // of the relationships are not copied (change requests, roles, etc) The person
        // we are copying from is just the base fields. Also, the person from ctep
        // does not set tty or url and the status is always active, so
        // those fields are skipped.
        p.setFirstName(ctepPerson.getFirstName());
        p.setLastName(ctepPerson.getLastName());
        p.setMiddleName(ctepPerson.getMiddleName());
        p.getPostalAddress().copy(ctepPerson.getPostalAddress());
        p.setPrefix(ctepPerson.getPrefix());
        p.setSuffix(ctepPerson.getSuffix());
        p.getEmail().clear();
        p.getEmail().addAll(ctepPerson.getEmail());
        p.getFax().clear();
        p.getFax().addAll(ctepPerson.getFax());
        p.getPhone().clear();
        p.getPhone().addAll(ctepPerson.getPhone());
        p.setStatusCode(EntityStatus.ACTIVE);
    }

    private HealthCareProviderDTO getHcpFromCtep(Ii personIi) {
        try {
            final HealthCareProviderDTO hcp = getCtepPersonService()
                    .getHealthCareProviderByPlayerId(personIi);
            if (hcp != null) {
                CtepUtils.converPhoneNumberFormats(hcp.getTelecomAddress());
            }
            return hcp;
        } catch (CTEPEntException e) {
            LOG.error(e);
            return null;
        }
    }

    private void createHcp(HealthCareProviderDTO hcpDto, Person ctepPerson) throws JMSException,
            EntityValidationException, CtepImportException {
        // store ii's we will need
        Ii scoperIi = hcpDto.getScoperIdentifier();

        // null out the ii's our converters can't handle because they are ctep id's and not our db ids.
        hcpDto.setIdentifier(null);
        hcpDto.setPlayerIdentifier(null);
        hcpDto.setScoperIdentifier(null);

        // convert to local db model
        HealthCareProvider hcp = (HealthCareProvider) PoXsnapshotHelper.createModel(hcpDto);

        // set fields that we need to
        hcp.setScoper(this.orgImporter.importOrgNoUpdate(scoperIi));
        hcp.setPlayer(ctepPerson);
        hcp.setStatus(getPersonRoleStatus(hcp.getScoper()));

        // store role
        this.hcpService.curate(hcp);
    }

    private ClinicalResearchStaffDTO getCrsFromCtep(Ii personIi) {
        try {
            final ClinicalResearchStaffDTO crs = getCtepPersonService()
                    .getClinicalResearchStaffByPlayerId(personIi);
            if (crs != null) {
                CtepUtils.converPhoneNumberFormats(crs.getTelecomAddress());
            }
            return crs;
        } catch (CTEPEntException e) {
            LOG.error(e);
            return null;
        }
    }

    private void createCrs(ClinicalResearchStaffDTO crsDto, Person ctepPerson) throws JMSException,
            EntityValidationException, CtepImportException {
        // store ii's we will need
        Ii scoperIi = crsDto.getScoperIdentifier();

        // null out the ii's our converters can't handle because they are ctep id's and not our db ids.
        crsDto.setIdentifier(null);
        crsDto.setScoperIdentifier(null);
        crsDto.setPlayerIdentifier(null);

        // convert to local db model
        ClinicalResearchStaff crs = (ClinicalResearchStaff) PoXsnapshotHelper.createModel(crsDto);

        // set fields that we need to
        crs.setScoper(this.orgImporter.importOrgNoUpdate(scoperIi));
        crs.setStatus(getPersonRoleStatus(crs.getScoper()));
        crs.setPlayer(ctepPerson);

        // store role
        this.crsService.curate(crs);
    }

    @SuppressWarnings("unchecked")
    private void printHcpDataToLog(HealthCareProviderDTO hcp) {
        LOG.info("  ** HCP Data ** \n");
        LOG.info("hcp identifiers: ");
        for (Ii ii : hcp.getIdentifier().getItem()) {
            LOG.info("\t hcp.id.identifierName: " + ii.getIdentifierName());
            LOG.info("\t hcp.id.extension: " + ii.getExtension());
            LOG.info("\t hcp.id.root: " + ii.getRoot());
        }
        LOG.info("\t hcp.player.extension: " + hcp.getPlayerIdentifier().getExtension());
        LOG.info("\t hcp.player.root: " + hcp.getPlayerIdentifier().getRoot());
        LOG.info("\t hcp.scoper.extension: " + hcp.getScoperIdentifier().getExtension());
        LOG.info("\t hcp.scoper.root: " + hcp.getScoperIdentifier().getRoot());
        LOG.info("\t hcp.certLicText: " + hcp.getCertificateLicenseText());
        LOG.info("\t hcp.status: " + hcp.getStatus().getCode());
        LOG.info("HCP addresses:");
        printAddresses(hcp.getPostalAddress());
        LOG.info("HCP Telecom Addresses:");
        printTels(hcp.getTelecomAddress());
    }

    @SuppressWarnings("unchecked")
    private void printCrsDataToLog(ClinicalResearchStaffDTO crs) {
        LOG.info("  ** CRS Data ** \n");
        LOG.info("crs identifiers: ");
        for (Ii ii : crs.getIdentifier().getItem()) {
            LOG.info("\t crs.id.identifierName: " + ii.getIdentifierName());
            LOG.info("\t crs.id.extension: " + ii.getExtension());
            LOG.info("\t crs.id.root: " + ii.getRoot());
        }
        LOG.info("\t crs.player.extension: " + crs.getPlayerIdentifier().getExtension());
        LOG.info("\t crs.player.root: " + crs.getPlayerIdentifier().getRoot());
        LOG.info("\t crs.scoper.extension: " + crs.getScoperIdentifier().getExtension());
        LOG.info("\t crs.scoper.root: " + crs.getScoperIdentifier().getRoot());
        LOG.info("\t crs.status: " + crs.getStatus().getCode());
        LOG.info("crs addresses:");
        printAddresses(crs.getPostalAddress());
        LOG.info("crs Telecom Addresses:");
        printTels(crs.getTelecomAddress());
    }

    private void printAddresses(DSet<Ad> ads) {
        for (Ad ad : ads.getItem()) {
            LOG.info("\t Address\n");
            for (Adxp axp : ad.getPart()) {
                LOG.info("\t" + axp.getType() + LOG_SEP + axp.getValue() + LOG_SEP + axp.getCode());
            }
        }
    }

    private void printTels(DSet<Tel> tels) {
        for (Tel obj : tels.getItem()) {
            LOG.info("\t" + obj.getValue());
        }
    }

    private RoleStatus getPersonRoleStatus(Organization scoper) {
        return scoper.getStatusCode() == EntityStatus.PENDING ? RoleStatus.PENDING : RoleStatus.ACTIVE;
    }
}
