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
import gov.nih.nci.iso21090.Bl;
import gov.nih.nci.iso21090.Cd;
import gov.nih.nci.iso21090.DSet;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.iso21090.Tel;
import gov.nih.nci.pa.domain.Organization;
import gov.nih.nci.pa.domain.RegistryUser;
import gov.nih.nci.pa.domain.StudySite;
import gov.nih.nci.pa.enums.FunctionalRoleStatusCode;
import gov.nih.nci.pa.enums.StudySiteFunctionalCode;
import gov.nih.nci.pa.iso.convert.ParticipatingSiteConverter;
import gov.nih.nci.pa.iso.dto.ParticipatingSiteContactDTO;
import gov.nih.nci.pa.iso.dto.ParticipatingSiteDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.StudySiteAccrualStatusDTO;
import gov.nih.nci.pa.iso.dto.StudySiteContactDTO;
import gov.nih.nci.pa.iso.dto.StudySiteDTO;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.DSetConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.IvlConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.service.exception.DuplicateParticipatingSiteException;
import gov.nih.nci.pa.service.util.CSMUserService;
import gov.nih.nci.pa.util.PAConstants;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.pa.util.PaHibernateSessionInterceptor;
import gov.nih.nci.pa.util.PaHibernateUtil;
import gov.nih.nci.pa.util.PaRegistry;
import gov.nih.nci.po.data.CurationException;
import gov.nih.nci.po.service.EntityValidationException;
import gov.nih.nci.security.authorization.domainobjects.User;
import gov.nih.nci.services.correlation.AbstractPersonRoleDTO;
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

import javax.annotation.Resource;
import javax.annotation.security.RolesAllowed;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Transformer;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.jboss.annotation.security.SecurityDomain;

import com.fiveamsolutions.nci.commons.util.UsernameHolder;

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
    @Resource
    private SessionContext ejbContext;

    private void checkValidUser(Ii studyProtocolIi) throws PAException {
        if (this.ejbContext.isCallerInRole(ADMIN_ABSTRACTOR_ROLE) || this.ejbContext.isCallerInRole(CLIENT_ROLE)) {
            return;
        }
        CSMUserUtil userService = CSMUserService.getInstance();
        User user = userService.getCSMUser(UsernameHolder.getUser());
        StudyProtocolDTO spDTO = getStudyProtocolService().getStudyProtocol(studyProtocolIi);
        if (spDTO == null || PAUtil.isIiNull(spDTO.getIdentifier())) {
            throw new PAException("Trial id " + studyProtocolIi.getExtension() + " does not exist.");
        }
        RegistryUser userId = PaRegistry.getRegistryUserService().getUser(user.getLoginName());
        if (!PaRegistry.getRegistryUserService().isTrialOwner(userId.getId(),
                Long.valueOf(spDTO.getIdentifier().getExtension()))) {
            throw new PAException("User " + user.getLoginName() + "is not a trial owner for trial id "
                    + Long.valueOf(spDTO.getIdentifier().getExtension()));
        }
    }

    /**
     * {@inheritDoc}
     */
    public List<ParticipatingSiteDTO> getParticipatingSitesByStudyProtocol(Ii studyProtocolIi) throws PAException {
        StudyProtocolDTO studyProtocolDTO = getStudyProtocolService().getStudyProtocol(studyProtocolIi);

        StudySiteDTO criteria = new StudySiteDTO();
        criteria.setStudyProtocolIdentifier(studyProtocolDTO.getIdentifier());
        criteria.setFunctionalCode(CdConverter.convertToCd(StudySiteFunctionalCode.TREATING_SITE));

        LimitOffset limit = new LimitOffset(PAConstants.MAX_SEARCH_RESULTS, 0);
        try {
            List<StudySiteDTO> results = getStudySiteService().search(criteria, limit);
            return convertStudySiteDTOsToParticipatingSiteDTOs(results);
        } catch (TooManyResultsException e) {
            throw new PAException(e.getMessage(), e);
        }
    }

    @SuppressWarnings("unchecked")
    private List<ParticipatingSiteDTO> convertStudySiteDTOsToParticipatingSiteDTOs(List<StudySiteDTO> dtos)
            throws PAException {
        ParticipatingSiteConverter converter = new ParticipatingSiteConverter();
        List<Long> ids = (List<Long>) CollectionUtils.collect(dtos, new Transformer() {
            public Object transform(Object arg) {
                StudySiteDTO dto = (StudySiteDTO) arg;
                return IiConverter.convertToLong(dto.getIdentifier());
            }
        });

        Criteria criteria = PaHibernateUtil.getCurrentSession().createCriteria(StudySite.class);
        criteria.add(Restrictions.in("id", ids));
        return converter.convertFromDomainToDtos(criteria.list());
    }

    /**
     * {@inheritDoc}
     */
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
    public void addStudySiteGenericContact(Ii studySite, OrganizationalContactDTO contactDTO, boolean isPrimaryContact,
            DSet<Tel> telecom) throws PAException {
        try {
            validateGenericContact(contactDTO, telecom);
            StudySiteDTO studySiteDTO = PaRegistry.getStudySiteService().get(studySite);
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
    public void addStudySiteInvestigator(Ii studySiteIi, ClinicalResearchStaffDTO poCrsDTO,
            HealthCareProviderDTO poHcpDTO, PersonDTO investigatorDTO, String roleCode) throws PAException {
        try {
            StudySiteDTO studySiteDTO = PaRegistry.getStudySiteService().get(studySiteIi);
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
    public void addStudySitePrimaryContact(Ii studySiteIi, ClinicalResearchStaffDTO poCrsDTO,
            HealthCareProviderDTO poHcpDTO, PersonDTO personDTO, DSet<Tel> telecom) throws PAException {
        try {
            checkStudySiteContactTelecom(telecom);
            StudySiteDTO studySiteDTO = PaRegistry.getStudySiteService().get(studySiteIi);
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

            StudyProtocolDTO spDTO = PaRegistry.getStudyProtocolService().getStudyProtocol(
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
        if (PAUtil.isIiNotNull(studySiteDTO.getStudyProtocolIdentifier())) {
            StudyProtocolDTO spDto = getStudyProtocolService()
                .getStudyProtocol(studySiteDTO.getStudyProtocolIdentifier());
            if (spDto == null || !currentSite.getStudyProtocolIdentifier().getExtension()
                    .equals(spDto.getIdentifier().getExtension())) {
                throw new PAException("Trial identifier provided with this update, does not match"
                        + " participating site trial identifier.");
            }
        }
    }

    private StudySite saveOrUpdateStudySiteHelper(boolean isCreate, StudySiteDTO siteDTO, Ii poHcfIi,
            StudySiteAccrualStatusDTO currentStatus) throws PAException, EntityValidationException, CurationException {
        if (isCreate && poHcfIi != null) {
            Long paHealthCareFacilityId = getOcsr().createHcfWithExistingPoHcf(poHcfIi);
            // check that we are not creating another part site w/ same trial and hcf ids.
            Ii paHcfIi = IiConverter.convertToIi(paHealthCareFacilityId);
            if (isDuplicate(siteDTO.getStudyProtocolIdentifier(), paHcfIi)) {
                throw new DuplicateParticipatingSiteException(
                        siteDTO.getStudyProtocolIdentifier(), poHcfIi);
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
        return getStudySite(studySiteDTO.getIdentifier());
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

    private StudySite getStudySite(Ii studySiteIi) {
        return (StudySite) PaHibernateUtil.getCurrentSession().get(StudySite.class,
                IiConverter.convertToLong(studySiteIi));
    }

    /**
     * {@inheritDoc}
     */
    public ParticipatingSiteDTO createStudySiteParticipant(StudySiteDTO studySiteDTO,
            StudySiteAccrualStatusDTO currentStatusDTO, OrganizationDTO orgDTO, HealthCareFacilityDTO hcfDTO,
            List<ParticipatingSiteContactDTO> participatingSiteContactDTOList) throws PAException {
        checkValidUser(studySiteDTO.getStudyProtocolIdentifier());
        ParticipatingSiteDTO participatingSiteDTO =
            this.createStudySiteParticipant(studySiteDTO, currentStatusDTO,
                orgDTO, hcfDTO);
        updateStudySiteContacts(participatingSiteContactDTOList, participatingSiteDTO);
        return getParticipatingSite(participatingSiteDTO.getIdentifier());
    }

    /**
     * {@inheritDoc}
     */
    public ParticipatingSiteDTO createStudySiteParticipant(StudySiteDTO studySiteDTO,
            StudySiteAccrualStatusDTO currentStatusDTO, Ii poHcfIi,
            List<ParticipatingSiteContactDTO> participatingSiteContactDTOList) throws PAException {
        checkValidUser(studySiteDTO.getStudyProtocolIdentifier());
        ParticipatingSiteDTO participatingSiteDTO =
            this.createStudySiteParticipant(studySiteDTO, currentStatusDTO,
                poHcfIi);
        updateStudySiteContacts(participatingSiteContactDTOList, participatingSiteDTO);
        return getParticipatingSite(participatingSiteDTO.getIdentifier());
    }

    /**
     * {@inheritDoc}
     */
    public ParticipatingSiteDTO updateStudySiteParticipant(StudySiteDTO studySiteDTO,
            StudySiteAccrualStatusDTO currentStatusDTO,
            List<ParticipatingSiteContactDTO> participatingSiteContactDTOList) throws PAException {
        StudySiteDTO currentSite = getStudySiteDTO(studySiteDTO.getIdentifier());
        checkValidUser(currentSite.getStudyProtocolIdentifier());
        ParticipatingSiteDTO participatingSiteDTO =
            this.updateStudySiteParticipant(studySiteDTO, currentStatusDTO);
        updateStudySiteContacts(participatingSiteContactDTOList, participatingSiteDTO);
        return getParticipatingSite(participatingSiteDTO.getIdentifier());
    }

    private StudySiteDTO getStudySiteDTO(Ii studySiteIi) throws PAException {
        if (PAUtil.isIiNull(studySiteIi)) {
            throw new PAException("Study site identifier must be provided for update.");
        }
        StudySiteDTO currentSite = PaRegistry.getStudySiteService().get(studySiteIi);
        if (currentSite == null) {
            throw new PAException("No participating site found by identifier"
                    + studySiteIi.getExtension());
        }
        return currentSite;
    }

    private void updateStudySiteContacts(List<ParticipatingSiteContactDTO> participatingSiteContactDTOList,
            ParticipatingSiteDTO participatingSiteDTO) throws PAException {
        deleteExistingStudySiteContants(participatingSiteDTO);
        for (ParticipatingSiteContactDTO participatingSiteContactDTO : participatingSiteContactDTOList) {
            addStudySiteContact(participatingSiteDTO, participatingSiteContactDTO);
        }
    }
    private void deleteExistingStudySiteContants(ParticipatingSiteDTO participatingSiteDTO) throws PAException {
        List<StudySiteContactDTO> dtos = getStudySiteContactService().getByStudySite(
                participatingSiteDTO.getIdentifier());
        for (StudySiteContactDTO dto : dtos) {
            getStudySiteContactService().delete(dto.getIdentifier());
        }
    }

    private Boolean getPrimaryIndicator(Bl primInd) throws PAException {
        Boolean isPrimary = BlConverter.convertToBoolean(primInd);
        if (isPrimary == null) {
            throw new PAException("Primary indicator must be set on all study site contacts.");
        }
        return isPrimary;
    }

    private String getRoleCode(Cd cdCode) throws PAException {
        String code = CdConverter.convertCdToString(cdCode);
        if (code == null) {
            throw new PAException("Role Code must be set on all study site contacts.");
        }
        return code;
    }

    private void addStudySiteContact(ParticipatingSiteDTO participatingSiteDTO,
            ParticipatingSiteContactDTO participatingSiteContactDTO) throws PAException {
        StudySiteContactDTO studySiteContactDTO = participatingSiteContactDTO.getStudySiteContactDTO();
        PersonDTO personDTO = participatingSiteContactDTO.getPersonDTO();
        AbstractPersonRoleDTO personRoleDTO = participatingSiteContactDTO.getAbstractPersonRoleDTO();
        String roleCode = getRoleCode(studySiteContactDTO.getRoleCode());
        Boolean isPrimary = getPrimaryIndicator(studySiteContactDTO.getPrimaryIndicator());

        if (personRoleDTO instanceof ClinicalResearchStaffDTO) {
            this.addStudySiteInvestigator(participatingSiteDTO.getIdentifier(),
                    (ClinicalResearchStaffDTO) personRoleDTO, null, personDTO, roleCode);
            if (isPrimary) {
                this.addStudySitePrimaryContact(participatingSiteDTO.getIdentifier(),
                        (ClinicalResearchStaffDTO) personRoleDTO, null, personDTO, studySiteContactDTO
                                .getTelecomAddresses());
            }
        } else if (personRoleDTO instanceof HealthCareProviderDTO) {
            this.addStudySiteInvestigator(participatingSiteDTO.getIdentifier(), null,
                    (HealthCareProviderDTO) personRoleDTO, personDTO, roleCode);
            if (isPrimary) {
                this.addStudySitePrimaryContact(participatingSiteDTO.getIdentifier(), null,
                        (HealthCareProviderDTO) personRoleDTO, personDTO, studySiteContactDTO.getTelecomAddresses());
            }
        } else if (personRoleDTO instanceof OrganizationalContactDTO) {
            this.addStudySiteGenericContact(participatingSiteDTO.getIdentifier(),
                    (OrganizationalContactDTO) personRoleDTO, isPrimary, studySiteContactDTO.getTelecomAddresses());
        }
    }

    private void checkStudySiteContactTelecom(DSet<Tel> telecom) throws PAException {
        if (telecom == null || CollectionUtils.isEmpty(telecom.getItem())) {
            throw new PAException("Study Site Contacts must have telecom address info "
                    + "for primary contact.");
        }
    }

    private ParticipatingSiteDTO getParticipatingSite(Ii studySiteIi) throws PAException {
        ParticipatingSiteDTO participatingSiteDTO = new ParticipatingSiteConverter()
                .convertFromDomainToDto(getStudySite(studySiteIi));
        // we should be able just to do a participatingSiteDTO.getStudySiteContacts() to fetch the StudySiteContactlist,
        // but it doesn't appear to be working properly. PO-2911 created to address that.
        participatingSiteDTO.setStudySiteContacts(getStudySiteContactService().getByStudySite(studySiteIi));
        return participatingSiteDTO;
    }

}