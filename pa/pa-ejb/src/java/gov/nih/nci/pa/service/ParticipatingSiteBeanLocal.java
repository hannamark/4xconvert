/*
 * The software subject to this notice and license includes both human readable
 * source code form and machine readable, binary, object code form. The pa
 * Software was developed in conjunction with the National Cancer Institute
 * (NCI) by NCI employees and 5AM Solutions, Inc. (5AM). To the extent
 * government employees are authors, any rights in such works shall be subject
 * to Title 17 of the United States Code, section 105.
 *
 * This pa Software License (the License) is between NCI and You. You (or
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
 * its rights in the pa Software to (i) use, install, access, operate,
 * execute, copy, modify, translate, market, publicly display, publicly perform,
 * and prepare derivative works of the pa Software; (ii) distribute and
 * have distributed to and by third parties the pa Software and any
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
package gov.nih.nci.pa.service;


import static gov.nih.nci.pa.service.AbstractBaseIsoService.ADMIN_ABSTRACTOR_ROLE;
import static gov.nih.nci.pa.service.AbstractBaseIsoService.CLIENT_ROLE;
import static gov.nih.nci.pa.service.AbstractBaseIsoService.SUBMITTER_ROLE;
import gov.nih.nci.coppa.services.LimitOffset;
import gov.nih.nci.coppa.services.TooManyResultsException;
import gov.nih.nci.iso21090.DSet;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.iso21090.Tel;
import gov.nih.nci.pa.domain.Organization;
import gov.nih.nci.pa.domain.StudySite;
import gov.nih.nci.pa.enums.FunctionalRoleStatusCode;
import gov.nih.nci.pa.enums.StudySiteFunctionalCode;
import gov.nih.nci.pa.iso.convert.ParticipatingSiteConverter;
import gov.nih.nci.pa.iso.dto.ParticipatingSiteDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.StudySiteAccrualStatusDTO;
import gov.nih.nci.pa.iso.dto.StudySiteDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.DSetConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.IvlConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.service.exception.DuplicateParticipatingSiteException;
import gov.nih.nci.pa.util.ISOUtil;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.pa.util.PaHibernateSessionInterceptor;
import gov.nih.nci.pa.util.PaHibernateUtil;
import gov.nih.nci.po.data.CurationException;
import gov.nih.nci.po.service.EntityValidationException;
import gov.nih.nci.services.correlation.ClinicalResearchStaffDTO;
import gov.nih.nci.services.correlation.HealthCareFacilityDTO;
import gov.nih.nci.services.correlation.HealthCareProviderDTO;
import gov.nih.nci.services.correlation.OrganizationalContactDTO;
import gov.nih.nci.services.organization.OrganizationDTO;
import gov.nih.nci.services.person.PersonDTO;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.jboss.annotation.security.SecurityDomain;

/**
 * @author mshestopalov
 *
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@Interceptors(PaHibernateSessionInterceptor.class)
@SecurityDomain("pa")
@RolesAllowed({CLIENT_ROLE, ADMIN_ABSTRACTOR_ROLE, SUBMITTER_ROLE })
@SuppressWarnings("PMD.AvoidRethrowingException") //Suppressed to catch and throw PAException to avoid re-wrapping.
public class ParticipatingSiteBeanLocal extends AbstractParticipatingSitesBean
implements ParticipatingSiteServiceLocal {

    /**
     * {@inheritDoc}
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public Ii getParticipatingSiteIi(Ii studyProtocolIi, Ii someHcfIi) throws PAException {
        try {
            StudyProtocolDTO studyProtocolDTO = getStudyProtocolService().getStudyProtocol(studyProtocolIi);
            HealthCareFacilityDTO hcfDTO = new HealthCareFacilityDTO();
            hcfDTO.setIdentifier(DSetConverter.convertIiToDset(someHcfIi));
            Ii poHcfIi = generateHcfIiFromCtepIdOrNewOrg(null, hcfDTO);
            return getStudySiteService().getStudySiteIiByTrialAndPoHcfIi(studyProtocolDTO.getIdentifier(), poHcfIi);
        } catch (PAException e) {
            throw e;
        } catch (Exception e) {
            throw new PAException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addStudySiteGenericContact(Ii studySite, OrganizationalContactDTO contactDTO, boolean isPrimaryContact,
            DSet<Tel> telecom) throws PAException {
        try {
            validateGenericContact(contactDTO, telecom);
            StudySiteDTO studySiteDTO = getStudySiteService().get(studySite);
            Organization paOrg = getCorrUtils().getPAOrganizationByIi(studySiteDTO.getHealthcareFacilityIi());
            contactDTO.setScoperIdentifier(IiConverter.convertToPoOrganizationIi(paOrg.getIdentifier()));
            Ii genericContactIi = getCorrUtils().getGenericContactIiFromCtepId(contactDTO);
            Ii orgIi = getCorrUtils().getPoOrgIiFromPaHcfIi(studySiteDTO.getHealthcareFacilityIi());
            Map<String, Ii> myMap = new HashMap<String, Ii>();
            myMap.put(IiConverter.ORGANIZATIONAL_CONTACT_ROOT, genericContactIi);
            myMap.put(IiConverter.ORG_ROOT, orgIi);
            createStudyParticationContactRecord(studySiteDTO, myMap, isPrimaryContact, null, telecom);
        } catch (PAException e) {
            throw e;
        } catch (Exception e) {
            throw new PAException(e);
        }
    }

    private void validateGenericContact(OrganizationalContactDTO contactDTO, DSet<Tel> telecom) throws PAException {
        String email = DSetConverter.getFirstElement(telecom, "EMAIL");
        String phone = DSetConverter.getFirstElement(telecom, "PHONE");
        if (StringUtils.isEmpty(email) && StringUtils.isEmpty(phone)) {
            throw new PAException("Generic contact '" + StConverter.convertToString(contactDTO.getTitle())
                    + "' must have either a phone number or email address specified.");
        }
        String typeCode = CdConverter.convertCdToString(contactDTO.getTypeCode());
        if (!StringUtils.equalsIgnoreCase(typeCode, "Site")) {
            throw new PAException("Generic contact '" + StConverter.convertToString(contactDTO.getTitle())
                    + "' can only have a type of 'Site' not " + typeCode);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addStudySiteInvestigator(Ii studySiteIi, ClinicalResearchStaffDTO poCrsDTO,
            HealthCareProviderDTO poHcpDTO, PersonDTO investigatorDTO, String roleCode) throws PAException {
        try {
            StudySiteDTO studySiteDTO = getStudySiteService().get(studySiteIi);
            Ii orgIi = getCorrUtils().getPoOrgIiFromPaHcfIi(studySiteDTO.getHealthcareFacilityIi());
            Map<String, Ii> myMap = generateCrsAndHcpFromCtepIdOrNewPerson(investigatorDTO, poCrsDTO, poHcpDTO, orgIi);
            createStudyParticationContactRecord(studySiteDTO, myMap, false, roleCode, null);
        } catch (PAException e) {
            throw e;
        } catch (Exception e) {
            throw new PAException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addStudySitePrimaryContact(Ii studySiteIi, ClinicalResearchStaffDTO poCrsDTO,
            HealthCareProviderDTO poHcpDTO, PersonDTO personDTO, DSet<Tel> telecom) throws PAException {
        try {
            if (ISOUtil.isDSetEmpty(telecom)) {
                throw new PAException("Study Site Contacts must have telecom address info for primary contact.");
            }
            StudySiteDTO studySiteDTO = getStudySiteService().get(studySiteIi);
            Ii orgIi = getCorrUtils().getPoOrgIiFromPaHcfIi(studySiteDTO.getHealthcareFacilityIi());
            Map<String, Ii> myMap = generateCrsAndHcpFromCtepIdOrNewPerson(personDTO, poCrsDTO, poHcpDTO, orgIi);
            createStudyParticationContactRecord(studySiteDTO, myMap, true, null, telecom);
        } catch (PAException e) {
            throw e;
        } catch (Exception e) {
            throw new PAException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ParticipatingSiteDTO createStudySiteParticipant(StudySiteDTO studySiteDTO,
            StudySiteAccrualStatusDTO currentStatusDTO, OrganizationDTO orgDTO, HealthCareFacilityDTO hcfDTO)
            throws PAException {
        // assume that siteDTO has a real Ii for studyProtocol
        try {
            Ii poHcfIi = generateHcfIiFromCtepIdOrNewOrg(orgDTO, hcfDTO);
            return createStudySiteParticipant(studySiteDTO, currentStatusDTO, poHcfIi);
        } catch (PAException e) {
            throw e;
        } catch (Exception e) {
            throw new PAException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ParticipatingSiteDTO createStudySiteParticipant(StudySiteDTO studySiteDTO,
            StudySiteAccrualStatusDTO currentStatusDTO, Ii poHcfIi) throws PAException {
        // assume that there is a poHcf out there already
        // assume that siteDTO has a real Ii for studyProtocol
        try {
            // check business rules based on trial type.
            StudyProtocolDTO spDTO = getStudyProtocolService().getStudyProtocol(
                    studySiteDTO.getStudyProtocolIdentifier());
            studySiteDTO.setStudyProtocolIdentifier(spDTO.getIdentifier());
            if (spDTO.getProprietaryTrialIndicator().getValue().booleanValue()) {
                enforceBusinessRulesForProprietary(spDTO, studySiteDTO, currentStatusDTO);
            } else {
                enforceBusinessRules(currentStatusDTO, PAUtil.getCurrentTime());
            }
            StudySite ss = saveOrUpdateStudySiteHelper(true, studySiteDTO, poHcfIi, currentStatusDTO);            
            return new ParticipatingSiteConverter().convertFromDomainToDto(ss);
        } catch (PAException e) {
            throw e;
        } catch (Exception e) {
            throw new PAException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ParticipatingSiteDTO updateStudySiteParticipant(StudySiteDTO studySiteDTO,
            StudySiteAccrualStatusDTO currentStatusDTO) throws PAException {
        // assume that siteDTO has a real Ii for studyProtocol
        try {
            // check business rules based on trial type.
            StudySiteDTO currentSite = getStudySiteDTO(studySiteDTO.getIdentifier());
            validateStudySite(studySiteDTO, currentSite);
            studySiteDTO.setStudyProtocolIdentifier(currentSite.getStudyProtocolIdentifier());
            studySiteDTO.setHealthcareFacilityIi(currentSite.getHealthcareFacilityIi());
            studySiteDTO.setStatusCode(CdConverter.convertToCd(FunctionalRoleStatusCode.PENDING));

            StudyProtocolDTO spDTO = getStudyProtocolService().getStudyProtocol(
                    currentSite.getStudyProtocolIdentifier());
            if (spDTO.getProprietaryTrialIndicator().getValue().booleanValue()) {
                enforceBusinessRulesForProprietary(spDTO, studySiteDTO, currentStatusDTO);
            } else {
                enforceBusinessRules(currentStatusDTO, PAUtil.getCurrentTime());
            }
            StudySite ss = saveOrUpdateStudySiteHelper(false, studySiteDTO, null, currentStatusDTO);
            return new ParticipatingSiteConverter().convertFromDomainToDto(ss);
        } catch (PAException e) {
            throw e;
        } catch (Exception e) {
            throw new PAException(e);
        }
    }

    private void validateStudySite(StudySiteDTO studySiteDTO, StudySiteDTO currentSite) throws PAException {
        if (!ISOUtil.isIiNull(studySiteDTO.getStudyProtocolIdentifier())) {
            StudyProtocolDTO spDto = getStudyProtocolService().getStudyProtocol(studySiteDTO
                                                                                    .getStudyProtocolIdentifier());
            if (spDto == null
                    || !currentSite.getStudyProtocolIdentifier().getExtension()
                        .equals(spDto.getIdentifier().getExtension())) {
                throw new PAException("Trial identifier provided with this update, does not match"
                        + " participating site trial identifier.");
            }
        }
    }

    private StudySite saveOrUpdateStudySiteHelper(boolean isCreate, StudySiteDTO siteDTO, Ii poHcfIi,
            StudySiteAccrualStatusDTO currentStatus) throws PAException, EntityValidationException, CurationException {
        if (isCreate && poHcfIi != null) {
            Long paHealthCareFacilityId = getOrganizationCorrelationService().createHcfWithExistingPoHcf(poHcfIi);
            // check that we are not creating another part site w/ same trial and hcf ids.
            Ii paHcfIi = IiConverter.convertToIi(paHealthCareFacilityId);
            if (isDuplicate(siteDTO.getStudyProtocolIdentifier(), paHcfIi)) {
                throw new DuplicateParticipatingSiteException(siteDTO.getStudyProtocolIdentifier(), poHcfIi);
            }
            siteDTO.setHealthcareFacilityIi(paHcfIi);
        }
        siteDTO.setFunctionalCode(CdConverter.convertToCd(StudySiteFunctionalCode.TREATING_SITE));

        if (isCreate) {
            siteDTO.setIdentifier(null);
        }

        siteDTO.setStatusDateRange(IvlConverter.convertTs().convertToIvl(new Timestamp(new Date().getTime()), null));

        StudySiteDTO studySiteDTO = null;
        if (isCreate) {
            studySiteDTO = getStudySiteService().create(siteDTO);
        } else {
            studySiteDTO = getStudySiteService().update(siteDTO);
        }
        createStudySiteAccrualStatus(studySiteDTO.getIdentifier(), currentStatus);
        final StudySite studySite = getStudySite(studySiteDTO.getIdentifier());
        getAccrualAccessServiceLocal().synchronizeSiteAccrualAccess(studySite.getId());
        return studySite;
    }

    private boolean isDuplicate(Ii trialIi, Ii paHcfIi) throws PAException {
        StudySiteDTO ssDto = new StudySiteDTO();
        ssDto.setStudyProtocolIdentifier(trialIi);
        ssDto.setHealthcareFacilityIi(paHcfIi);
        ssDto.setFunctionalCode(CdConverter.convertStringToCd(StudySiteFunctionalCode.TREATING_SITE.getCode()));

        List<StudySiteDTO> ssDtoList;
        try {
            ssDtoList = this.getStudySiteService().search(ssDto, new LimitOffset(1, 0));
        } catch (TooManyResultsException e) {
            return true;
        }
        return CollectionUtils.isNotEmpty(ssDtoList);
    }

    /**
     * Gets the study site dto with the given studySiteIi.
     * @param studySiteIi The study site Ii
     * @return The study site dto with the given studySiteIi
     * @throws PAException if an error occurs
     */
    protected StudySiteDTO getStudySiteDTO(Ii studySiteIi) throws PAException {
        if (ISOUtil.isIiNull(studySiteIi)) {
            throw new PAException("Study site identifier must be provided for update.");
        }
        StudySiteDTO currentSite = getStudySiteService().get(studySiteIi);
        if (currentSite == null) {
            throw new PAException("No participating site found by identifier" + studySiteIi.getExtension());
        }
        return currentSite;
    }

    /**
     * Gets the study site with the given studySiteIi.
     * @param studySiteIi The study site Ii
     * @return The study site with the given studySiteIi.
     */
    protected StudySite getStudySite(Ii studySiteIi) {
        return (StudySite) PaHibernateUtil.getCurrentSession().get(StudySite.class,
                                                                   IiConverter.convertToLong(studySiteIi));
    }
}