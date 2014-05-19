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

import static gov.nih.nci.pa.service.AbstractBaseIsoService.SECURITY_DOMAIN;
import static gov.nih.nci.pa.service.AbstractBaseIsoService.SUBMITTER_ROLE;
import gov.nih.nci.coppa.services.LimitOffset;
import gov.nih.nci.coppa.services.TooManyResultsException;
import gov.nih.nci.coppa.services.interceptor.RemoteAuthorizationInterceptor;
import gov.nih.nci.iso21090.Bl;
import gov.nih.nci.iso21090.Cd;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.domain.StudySite;
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
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.util.ISOUtil;
import gov.nih.nci.pa.util.PAConstants;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.pa.util.PaHibernateSessionInterceptor;
import gov.nih.nci.pa.util.PaHibernateUtil;
import gov.nih.nci.services.correlation.AbstractPersonRoleDTO;
import gov.nih.nci.services.correlation.ClinicalResearchStaffDTO;
import gov.nih.nci.services.correlation.HealthCareFacilityDTO;
import gov.nih.nci.services.correlation.HealthCareProviderDTO;
import gov.nih.nci.services.correlation.OrganizationalContactDTO;
import gov.nih.nci.services.organization.OrganizationDTO;
import gov.nih.nci.services.person.PersonDTO;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;

import org.apache.commons.collections.CollectionUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.jboss.ejb3.annotation.SecurityDomain;

/**
 * @author mshestopalov
 */
@Stateless
@Interceptors({RemoteAuthorizationInterceptor.class, PaHibernateSessionInterceptor.class })
@SecurityDomain(SECURITY_DOMAIN)
@RolesAllowed(SUBMITTER_ROLE)
public class ParticipatingSiteServiceBean extends ParticipatingSiteBeanLocal implements ParticipatingSiteServiceRemote {

    /**
     * {@inheritDoc}
     */
    @Override
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

    /**
     * Convert the given list of StudySiteDTO into a list of ParticipatingSiteDTO.
     * @param dtos The list of StudySiteDTO to convert
     * @return The list of ParticipatingSiteDTO
     * @throws PAException if an error occurs
     */
    @SuppressWarnings("unchecked")
    List<ParticipatingSiteDTO> convertStudySiteDTOsToParticipatingSiteDTOs(List<StudySiteDTO> dtos)
            throws PAException {
        List<Long> ids = new ArrayList<Long>();
        List<StudySite> results = new ArrayList<StudySite>();
        if (CollectionUtils.isNotEmpty(dtos)) {
            for (StudySiteDTO dto : dtos) {
                ids.add(IiConverter.convertToLong(dto.getIdentifier()));
            }
            Criteria criteria = PaHibernateUtil.getCurrentSession().createCriteria(StudySite.class);
            criteria.add(Restrictions.in("id", ids));
            results = criteria.list();
        }
        ParticipatingSiteConverter converter = new ParticipatingSiteConverter();
        return converter.convertFromDomainToDtos(results);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ParticipatingSiteDTO createStudySiteParticipant(StudySiteDTO studySiteDTO,
            StudySiteAccrualStatusDTO currentStatusDTO, OrganizationDTO orgDTO, HealthCareFacilityDTO hcfDTO,
            List<ParticipatingSiteContactDTO> participatingSiteContactDTOList) throws PAException {
        checkStudyProtocol(studySiteDTO.getStudyProtocolIdentifier());
        ParticipatingSiteDTO participatingSiteDTO = createStudySiteParticipant(studySiteDTO, currentStatusDTO, orgDTO,
                                                                               hcfDTO);
        updateStudySiteContacts(participatingSiteContactDTOList, participatingSiteDTO);
        return getParticipatingSite(participatingSiteDTO.getIdentifier());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ParticipatingSiteDTO createStudySiteParticipant(StudySiteDTO studySiteDTO,
            StudySiteAccrualStatusDTO currentStatusDTO, Ii poHcfIi,
            List<ParticipatingSiteContactDTO> participatingSiteContactDTOList) throws PAException {
        checkStudyProtocol(studySiteDTO.getStudyProtocolIdentifier());
        ParticipatingSiteDTO participatingSiteDTO = createStudySiteParticipant(studySiteDTO, currentStatusDTO, poHcfIi);
        updateStudySiteContacts(participatingSiteContactDTOList, participatingSiteDTO);
        return getParticipatingSite(participatingSiteDTO.getIdentifier());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ParticipatingSiteDTO updateStudySiteParticipant(StudySiteDTO studySiteDTO,
            StudySiteAccrualStatusDTO currentStatusDTO,
            List<ParticipatingSiteContactDTO> participatingSiteContactDTOList) throws PAException {
        StudySiteDTO currentSite = getStudySiteDTO(studySiteDTO.getIdentifier());
        checkStudyProtocol(currentSite.getStudyProtocolIdentifier());
        ParticipatingSiteDTO participatingSiteDTO = updateStudySiteParticipant(studySiteDTO, currentStatusDTO);
        updateStudySiteContacts(participatingSiteContactDTOList, participatingSiteDTO);
        return getParticipatingSite(participatingSiteDTO.getIdentifier());
    }

    /**
     * Check that the study protocol with the given Ii exist and that the current user can access it.
     * @param studyProtocolIi The study protocil Ii
     * @throws PAException if the study protocol does not exist or the user can not access it
     */
    void checkStudyProtocol(Ii studyProtocolIi) throws PAException {
        StudyProtocolDTO studyProtocolDTO = getStudyProtocolService().getStudyProtocol(studyProtocolIi);
        if (studyProtocolDTO == null || ISOUtil.isIiNull(studyProtocolDTO.getIdentifier())) {
            throw new PAException("Trial id " + studyProtocolIi.getExtension() + " does not exist.");
        }
        PAUtil.checkUserIsTrialOwnerOrAbstractor(studyProtocolDTO);
    }

    /**
     * Update the contacts of a given study site.
     * @param participatingSiteContactDTOList The new contacts
     * @param participatingSiteDTO The participating site to update
     * @throws PAException if an error occurs
     */
    void updateStudySiteContacts(List<ParticipatingSiteContactDTO> participatingSiteContactDTOList,
            ParticipatingSiteDTO participatingSiteDTO) throws PAException {
        StudySiteContactService studySiteContactService = getStudySiteContactService();
        for (StudySiteContactDTO dto : studySiteContactService.getByStudySite(participatingSiteDTO.getIdentifier())) {
            studySiteContactService.delete(dto.getIdentifier());
        }
        for (ParticipatingSiteContactDTO participatingSiteContactDTO : participatingSiteContactDTOList) {
            addStudySiteContact(participatingSiteDTO, participatingSiteContactDTO);
        }
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
                                                (ClinicalResearchStaffDTO) personRoleDTO, null, personDTO,
                                                studySiteContactDTO.getTelecomAddresses());
            }
        } else if (personRoleDTO instanceof HealthCareProviderDTO) {
            this.addStudySiteInvestigator(participatingSiteDTO.getIdentifier(), null,
                                          (HealthCareProviderDTO) personRoleDTO, personDTO, roleCode);
            if (isPrimary) {
                this.addStudySitePrimaryContact(participatingSiteDTO.getIdentifier(), null,
                                                (HealthCareProviderDTO) personRoleDTO, personDTO,
                                                studySiteContactDTO.getTelecomAddresses());
            }
        } else if (personRoleDTO instanceof OrganizationalContactDTO) {
            this.addStudySiteGenericContact(participatingSiteDTO.getIdentifier(),
                                            (OrganizationalContactDTO) personRoleDTO, isPrimary,
                                            studySiteContactDTO.getTelecomAddresses());
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

    /**
     * Gets the Participating site with the given Ii.
     * @param studySiteIi The study site Ii
     * @return The Participating site with the given Ii.
     * @throws PAException if an error occurs
     */
    ParticipatingSiteDTO getParticipatingSite(Ii studySiteIi) throws PAException {
        ParticipatingSiteDTO participatingSiteDTO = new ParticipatingSiteConverter()
            .convertFromDomainToDto(getStudySite(studySiteIi));
        // we should be able just to do a participatingSiteDTO.getStudySiteContacts() to fetch the StudySiteContactlist,
        // but it doesn't appear to be working properly. PO-2911 created to address that.
        participatingSiteDTO.setStudySiteContacts(getStudySiteContactService().getByStudySite(studySiteIi));
        return participatingSiteDTO;
    }
}
