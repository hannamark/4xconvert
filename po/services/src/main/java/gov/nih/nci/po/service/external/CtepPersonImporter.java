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
import gov.nih.nci.coppa.iso.Ad;
import gov.nih.nci.coppa.iso.Adxp;
import gov.nih.nci.coppa.iso.Enxp;
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.coppa.iso.Tel;
import gov.nih.nci.po.data.bo.Email;
import gov.nih.nci.po.data.bo.IdentifiedPerson;
import gov.nih.nci.po.data.bo.Organization;
import gov.nih.nci.po.data.bo.Person;
import gov.nih.nci.po.data.bo.RoleStatus;
import gov.nih.nci.po.service.AnnotatedBeanSearchCriteria;
import gov.nih.nci.po.service.IdentifiedPersonServiceLocal;
import gov.nih.nci.po.service.PersonServiceLocal;
import gov.nih.nci.po.service.SearchCriteria;
import gov.nih.nci.po.util.PoRegistry;
import gov.nih.nci.po.util.PoXsnapshotHelper;
import gov.nih.nci.services.correlation.ClinicalResearchStaffDTO;
import gov.nih.nci.services.correlation.HealthCareProviderDTO;
import gov.nih.nci.services.person.PersonDTO;

import java.util.ArrayList;
import java.util.List;

import javax.jms.JMSException;
import javax.naming.InitialContext;

import org.apache.log4j.Logger;

/**
 * @author Scott Miller
 *
 */
public class CtepPersonImporter extends CtepEntityImporter {
    private static final Logger LOG = Logger.getLogger(CtepPersonImporter.class);
    private static final String LOG_SEP = " : ";
    private final CtepOrganizationImporter orgImporter;

    private final PersonServiceLocal personService = PoRegistry.getPersonService();
    private final IdentifiedPersonServiceLocal identifiedPersonService = PoRegistry.getInstance().
        getServiceLocator().getIdentifiedPersonService();
    //private final HealthCareFacilityServiceLocal hcfService = PoRegistry.getInstance().
      //  getServiceLocator().getHealthCareFacilityService();
   // private final ResearchOrganizationServiceLocal roService = PoRegistry.getInstance().
     //   getServiceLocator().getResearchOrganizationService();

    /**
     * Constructor.
     * @param ctepContext the initial context providing access to ctep services.
     * @param orgImporter the org importer.
     */
    public CtepPersonImporter(InitialContext ctepContext, CtepOrganizationImporter orgImporter) {
        super(ctepContext);
        this.orgImporter = orgImporter;
    }

    private Organization getCtepOrganization() throws JMSException {
        return this.orgImporter.getCtepOrganization();
    }

    /**
     * Method to import a person based on its ctep id.
     * @param ctepPersonId the ctep id.
     * @throws JMSException on error
     * @return the organization record.
     */
    @SuppressWarnings("PMD.AvoidThrowingRawExceptionTypes")
    public Person importPerson(Ii ctepPersonId) throws JMSException {
        try {
            // get org from ctep and convert to local data model
            PersonDTO ctepPersonDto = getCtepPersonService().getPersonById(ctepPersonId);
            Ii assignedId = ctepPersonDto.getIdentifier();
            if (LOG.isDebugEnabled()) {
                printPersonDataToDebugLog(ctepPersonDto);
            }
            Person ctepPerson = convertToLocalPerson(ctepPersonDto);

            // search for org based on the ctep provided ii
            IdentifiedPerson identifiedPerson = searchForPreviousRecord(assignedId);
            if (identifiedPerson == null) {
                return createCtepPerson(ctepPerson, assignedId);
            } else {
                return updateCtepPerson(ctepPerson, identifiedPerson);
            }
        } catch (CTEPEntException e) {
            // unexpected exception, we should only be calling this method for objects
            // whose ID we know exists.
            throw new RuntimeException(e);
        }
    }

    private void printPersonDataToDebugLog(PersonDTO perDto) {
        LOG.debug("Person ii.extension: " + perDto.getIdentifier().getExtension());
        LOG.debug("Person ii.root: " + perDto.getIdentifier().getRoot());
        LOG.debug("Person status: " + perDto.getStatusCode().getCode());
        LOG.debug("Person name:");
        for (Enxp xp : perDto.getName().getPart()) {
            LOG.debug("\t" + xp.getType() + LOG_SEP + xp.getValue());
        }

        LOG.debug("Person address:");
        for (Adxp axp : perDto.getPostalAddress().getPart()) {
            LOG.debug("\t" + axp.getType() + LOG_SEP + axp.getValue() + LOG_SEP + axp.getCode());
        }

        LOG.debug("Telecom Addresses:");
        for (Tel obj : perDto.getTelecomAddress().getItem()) {
            LOG.debug("\t" + obj.getValue());
        }
    }

    private Person convertToLocalPerson(PersonDTO dto) {
        dto.setIdentifier(null);
        Person p = (Person) PoXsnapshotHelper.createModel(dto);
        if (p.getEmail().isEmpty()) {
            p.getEmail().add(new Email("test@test.com"));
        }
        return p;
    }

    private IdentifiedPerson searchForPreviousRecord(Ii ctepPersonId) {
        IdentifiedPerson identifiedPerson = new IdentifiedPerson();
        identifiedPerson.setAssignedIdentifier(ctepPersonId);
        SearchCriteria<IdentifiedPerson> sc =
            new AnnotatedBeanSearchCriteria<IdentifiedPerson>(identifiedPerson);
        List<IdentifiedPerson> identifiedPeople = this.identifiedPersonService.search(sc);
        if (identifiedPeople.isEmpty()) {
            return null;
        }
        return identifiedPeople.get(0);
    }

    private Person createCtepPerson(Person ctepPerson, Ii assignedId) throws JMSException {
        // create the local entity record
        this.personService.curate(ctepPerson);

        // create the identified entity record
        IdentifiedPerson identifiedPerson = new IdentifiedPerson();
        identifiedPerson.setPlayer(ctepPerson);
        identifiedPerson.setScoper(getCtepOrganization());
        identifiedPerson.setAssignedIdentifier(assignedId);
        identifiedPerson.setStatus(RoleStatus.ACTIVE);
        this.identifiedPersonService.curate(identifiedPerson);

        // create identified person record for any other person identifiers provied in ctep services


        // create records for all health care provider records
        List<HealthCareProviderDTO> hcps = getHcpFromCtep(assignedId);
        if (LOG.isDebugEnabled()) {
            printHcpDataToDebugLog(hcps);
        }

        // create records for all clinical research staff records.
        List<ClinicalResearchStaffDTO> crss = getCrsFromCtep(assignedId);
        if (LOG.isDebugEnabled()) {
            printCrsDataToDebugLog(crss);
        }

        return ctepPerson;
    }

    private Person updateCtepPerson(Person ctepPerson, IdentifiedPerson identifiedPerson) {
        // TODO support update
        LOG.debug("Attempting to update person with name : ctep ids - "
                + ctepPerson.getLastName() + " : "
                + identifiedPerson.getAssignedIdentifier().getExtension());
        throw new UnsupportedOperationException();
    }

    private List<HealthCareProviderDTO> getHcpFromCtep(Ii personIi) {
        try {
            return getCtepPersonService().getHealthCareProviderByPlayerId(personIi);
        } catch (CTEPEntException e) {
            return new ArrayList<HealthCareProviderDTO>();
        }
    }

    private List<ClinicalResearchStaffDTO> getCrsFromCtep(Ii personIi) {
        try {
            return getCtepPersonService().getClinicalResearchStaffByPlayerId(personIi);
        } catch (CTEPEntException e) {
            return new ArrayList<ClinicalResearchStaffDTO>();
        }
    }

    private void printHcpDataToDebugLog(List<HealthCareProviderDTO> hcps) {
        if (hcps.isEmpty()) {
            LOG.debug("This person is not a HCP.");
        } else {
            for (HealthCareProviderDTO hcp : hcps) {
                LOG.debug("  ** HCP Data ** \n");
                LOG.debug("\t hcp.id.extension: " + hcp.getIdentifier().getExtension());
                LOG.debug("\t hcp.id.root: " + hcp.getIdentifier().getRoot());
                LOG.debug("\t hcp.player.extension: " + hcp.getPlayerIdentifier().getExtension());
                LOG.debug("\t hcp.player.root: " + hcp.getPlayerIdentifier().getRoot());
                LOG.debug("\t hcp.scoper.extension: " + hcp.getScoperIdentifier().getExtension());
                LOG.debug("\t hcp.scoper.root: " + hcp.getScoperIdentifier().getRoot());
                LOG.debug("\t hcp.certLicText: " + hcp.getCertificateLicenseText());
                LOG.debug("\t hcp.status: " + hcp.getStatus().getCode());
                LOG.debug("HCP addresses:");
                for (Object adObj : hcp.getPostalAddress().getItem()) {
                    Ad ad = (Ad) adObj;
                    LOG.debug("\t Address\n");
                    for (Adxp axp : ad.getPart()) {
                        LOG.debug("\t" + axp.getType() + LOG_SEP + axp.getValue() + LOG_SEP + axp.getCode());
                    }
                }
                LOG.debug("HCP Telecom Addresses:");
                for (Tel obj : hcp.getTelecomAddress().getItem()) {
                    LOG.debug("\t" + obj.getValue());
                }
            }
        }

    }

    private void printCrsDataToDebugLog(List<ClinicalResearchStaffDTO> crss) {
        if (crss.isEmpty()) {
            LOG.debug("This person is not a CRS.");
        } else {
            for (ClinicalResearchStaffDTO crs : crss) {
                LOG.debug("  ** HCP Data ** \n");
                LOG.debug("\t crs.id.extension: " + crs.getIdentifier().getExtension());
                LOG.debug("\t crs.id.root: " + crs.getIdentifier().getRoot());
                LOG.debug("\t crs.player.extension: " + crs.getPlayerIdentifier().getExtension());
                LOG.debug("\t crs.player.root: " + crs.getPlayerIdentifier().getRoot());
                LOG.debug("\t crs.scoper.extension: " + crs.getScoperIdentifier().getExtension());
                LOG.debug("\t crs.scoper.root: " + crs.getScoperIdentifier().getRoot());
                LOG.debug("\t crs.status: " + crs.getStatus().getCode());
                LOG.debug("crs addresses:");
                for (Object adObj : crs.getPostalAddress().getItem()) {
                    Ad ad = (Ad) adObj;
                    LOG.debug("\t Address\n");
                    for (Adxp axp : ad.getPart()) {
                        LOG.debug("\t" + axp.getType() + LOG_SEP + axp.getValue() + LOG_SEP + axp.getCode());
                    }
                }
                LOG.debug("crs Telecom Addresses:");
                for (Tel obj : crs.getTelecomAddress().getItem()) {
                    LOG.debug("\t" + obj.getValue());
                }
            }
        }
    }

}