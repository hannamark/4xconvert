/**
 * The software subject to this notice and license includes both human readable
 * source code form and machine readable, binary, object code form. The PAServices
 * Software was developed in conjunction with the National Cancer Institute
 * (NCI) by NCI employees and 5AM Solutions, Inc. (5AM). To the extent
 * government employees are authors, any rights in such works shall be subject
 * to Title 17 of the United States Code, section 105.
 *
 * This PAServices Software License (the License) is between NCI and You. You (or
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
 * its rights in the PAServices Software to (i) use, install, access, operate,
 * execute, copy, modify, translate, market, publicly display, publicly perform,
 * and prepare derivative works of the PAServices Software; (ii) distribute and
 * have distributed to and by third parties the PAServices Software and any
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
package gov.nih.nci.coppa.services.pa.grid.remote;

import gov.nih.nci.coppa.services.grid.remote.InvokeCoppaServiceException;
import gov.nih.nci.iso21090.Bl;
import gov.nih.nci.iso21090.Cd;
import gov.nih.nci.iso21090.DSet;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.iso21090.St;
import gov.nih.nci.iso21090.Tel;
import gov.nih.nci.pa.enums.SummaryFourFundingCategoryCode;
import gov.nih.nci.pa.iso.dto.DocumentDTO;
import gov.nih.nci.pa.iso.dto.StudyContactDTO;
import gov.nih.nci.pa.iso.dto.StudyIndldeDTO;
import gov.nih.nci.pa.iso.dto.StudyOverallStatusDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.StudyRegulatoryAuthorityDTO;
import gov.nih.nci.pa.iso.dto.StudyResourcingDTO;
import gov.nih.nci.pa.iso.dto.StudySiteAccrualStatusDTO;
import gov.nih.nci.pa.iso.dto.StudySiteContactDTO;
import gov.nih.nci.pa.iso.dto.StudySiteDTO;
import gov.nih.nci.pa.iso.util.AddressConverterUtil;
import gov.nih.nci.pa.iso.util.DSetConverter;
import gov.nih.nci.pa.iso.util.EnOnConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.TrialRegistrationServiceRemote;
import gov.nih.nci.pa.util.ISOUtil;
import gov.nih.nci.services.organization.OrganizationDTO;
import gov.nih.nci.services.person.PersonDTO;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
/**
 * Wrapper class for invoking the TrialRegistrationServiceRemote remote EJB.
 *
 * @author Steve Lustbader
 */

public class InvokeTrialRegistrationEjb implements TrialRegistrationServiceRemote {

    /**
     * {@inheritDoc}
     */
    // CHECKSTYLE:OFF
    public Ii amend(StudyProtocolDTO studyProtocolDTO, StudyOverallStatusDTO overallStatusDTO,
            List<StudyIndldeDTO> studyIndldeDTOs, List<StudyResourcingDTO> studyResourcingDTOs,
            List<DocumentDTO> documentDTOs, OrganizationDTO leadOrganizationDTO, PersonDTO principalInvestigatorDTO,
            OrganizationDTO sponsorOrganizationDTO, StudySiteDTO leadOrganizationSiteIdentifierDTO,
            List<StudySiteDTO> studyIdentifierDTOs, StudyContactDTO studyContactDTO,
            StudySiteContactDTO studySiteContactDTO, List<OrganizationDTO> summary4organizationDTO,
            StudyResourcingDTO summary4studyResourcingDTO, Ii responsiblePartyContactIi,
            StudyRegulatoryAuthorityDTO studyRegAuthDTO, Bl isBatch) throws PAException {
        // CHECKSTYLE:ON

        try {
            return GridSecurityJNDIServiceLocator.newInstance().getTrialRegistrationService().amend(studyProtocolDTO,
                    overallStatusDTO, studyIndldeDTOs, studyResourcingDTOs, documentDTOs, leadOrganizationDTO,
                    principalInvestigatorDTO, sponsorOrganizationDTO, leadOrganizationSiteIdentifierDTO,
                    studyIdentifierDTOs, studyContactDTO, studySiteContactDTO, summary4organizationDTO,
                    summary4studyResourcingDTO, responsiblePartyContactIi, studyRegAuthDTO, isBatch);
        } catch (PAException pae) {
            throw pae;
        } catch (Exception e) {
            throw new InvokeCoppaServiceException(e.toString(), e);
        }
    }

    /**
     * {@inheritDoc}
     */
    // CHECKSTYLE:OFF
    public Ii createAbbreviatedInterventionalStudyProtocol(StudyProtocolDTO studyProtocolDTO,
            StudySiteAccrualStatusDTO studySiteAccrualStatusDTO, List<DocumentDTO> documentDTOs,
            OrganizationDTO leadOrganizationDTO, PersonDTO studySiteInvestigatorDTO,
            StudySiteDTO leadOrganizationStudySiteDTO, OrganizationDTO studySiteOrganizationDTO,
            StudySiteDTO studySiteDTO, StudySiteDTO nctIdentifierDTO, List<OrganizationDTO> summary4OrganizationDTO,
            StudyResourcingDTO summary4StudyResourcingDTO, Bl isBatch) throws PAException {
        // CHECKSTYLE:ON

        try {
            //If no study researching is provided we're defaulting to Industrial
            StudyResourcingDTO resourcing =
                getSummaryStudyResourcing(summary4StudyResourcingDTO, SummaryFourFundingCategoryCode.INDUSTRIAL);
            if (summary4OrganizationDTO == null) {
                OrganizationDTO org = getSummaryOrganization(null);
                summary4OrganizationDTO = new ArrayList<OrganizationDTO>();
                summary4OrganizationDTO.add(org);
            }
            return GridSecurityJNDIServiceLocator.newInstance().getTrialRegistrationService()
                    .createAbbreviatedInterventionalStudyProtocol(studyProtocolDTO, studySiteAccrualStatusDTO,
                            documentDTOs, leadOrganizationDTO, studySiteInvestigatorDTO, leadOrganizationStudySiteDTO,
                            studySiteOrganizationDTO, studySiteDTO, nctIdentifierDTO, summary4OrganizationDTO,
                            resourcing, isBatch);
        } catch (PAException pae) {
            throw pae;
        } catch (Exception e) {
            throw new InvokeCoppaServiceException(e.toString(), e);
        }
    }

    /**
     * {@inheritDoc}
     */
    // CHECKSTYLE:OFF
    public Ii createCompleteInterventionalStudyProtocol(StudyProtocolDTO studyProtocolDTO,
            StudyOverallStatusDTO overallStatusDTO, List<StudyIndldeDTO> studyIndldeDTOs,
            List<StudyResourcingDTO> studyResourcingDTOs, List<DocumentDTO> documentDTOs,
            OrganizationDTO leadOrganizationDTO, PersonDTO principalInvestigatorDTO,
            OrganizationDTO sponsorOrganizationDTO, StudySiteDTO leadOrganizationSiteIdentifierDTO,
            List<StudySiteDTO> studyIdentifierDTOs, StudyContactDTO studyContactDTO,
            StudySiteContactDTO studySiteContactDTO, List<OrganizationDTO> summary4organizationDTO,
            StudyResourcingDTO summary4studyResourcingDTO, Ii responsiblePartyContactIi,
            StudyRegulatoryAuthorityDTO studyRegAuthDTO, Bl isBatch) throws PAException {
        // CHECKSTYLE:ON

        try {
            //If no study researching is provided we're defaulting to National
            StudyResourcingDTO resourcing =
                getSummaryStudyResourcing(summary4studyResourcingDTO, SummaryFourFundingCategoryCode.NATIONAL);
            if (summary4organizationDTO == null) {
                OrganizationDTO org = getSummaryOrganization(null);
                summary4organizationDTO = new ArrayList<OrganizationDTO>();
                summary4organizationDTO.add(org);
            }
            return GridSecurityJNDIServiceLocator.newInstance().getTrialRegistrationService()
                    .createCompleteInterventionalStudyProtocol(studyProtocolDTO, overallStatusDTO, studyIndldeDTOs,
                            studyResourcingDTOs, documentDTOs, leadOrganizationDTO, principalInvestigatorDTO,
                            sponsorOrganizationDTO, leadOrganizationSiteIdentifierDTO, studyIdentifierDTOs,
                            studyContactDTO, studySiteContactDTO, summary4organizationDTO, resourcing,
                            responsiblePartyContactIi, studyRegAuthDTO, isBatch);
        } catch (PAException pae) {
            throw pae;
        } catch (Exception e) {
            throw new InvokeCoppaServiceException(e.toString(), e);
        }
    }

    /**
     * {@inheritDoc}
     */
    public void  reject(Ii studyProtocolIi, St rejectionReason, Cd rejectionReasonCode) throws PAException {
        throw new UnsupportedOperationException("reject not allowed");
    }

    /**
     * {@inheritDoc}
     */
    // CHECKSTYLE:OFF
    public void update(StudyProtocolDTO studyProtocolDTO, StudyOverallStatusDTO overallStatusDTO,
            List<StudySiteDTO> studyIdentifierDTOs, List<StudyIndldeDTO> studyIndldeDTOs,
            List<StudyResourcingDTO> studyResourcingDTOs, List<DocumentDTO> documentDTOs,
            StudyContactDTO studyContactDTO, StudySiteContactDTO studyParticipationContactDTO,
            OrganizationDTO summary4organizationDTO, StudyResourcingDTO summary4studyResourcingDTO,
            Ii responsiblePartyContactIi, StudyRegulatoryAuthorityDTO studyRegAuthDTO,
            List<StudySiteDTO> collaboratorDTOs, List<StudySiteAccrualStatusDTO> studySiteAccrualStatusDTOs,
            List<StudySiteDTO> studySiteDTOs, Bl isBatch) throws PAException {
        // CHECKSTYLE:ON
        try {
            if (studyIndldeDTOs != null) {
                for (StudyIndldeDTO indIdeDto : studyIndldeDTOs) {
                     if (!ISOUtil.isIiNull(indIdeDto.getIdentifier())) {
                         StudyIndldeDTO dto = GridSecurityJNDIServiceLocator.newInstance().getStudyIndldeService()
                             .get(indIdeDto.getIdentifier());
                         indIdeDto.setExemptIndicator(dto.getExemptIndicator());
                     }
                }
            }
            GridSecurityJNDIServiceLocator.newInstance().getTrialRegistrationService().update(studyProtocolDTO,
                    overallStatusDTO, studyIdentifierDTOs, studyIndldeDTOs, studyResourcingDTOs, documentDTOs,
                    studyContactDTO, studyParticipationContactDTO, summary4organizationDTO, summary4studyResourcingDTO,
                    responsiblePartyContactIi, studyRegAuthDTO, collaboratorDTOs, studySiteAccrualStatusDTOs,
                    studySiteDTOs, isBatch);
        } catch (PAException pae) {
            throw pae;
        } catch (Exception e) {
            throw new InvokeCoppaServiceException(e.toString(), e);
        }
    }

    private OrganizationDTO getSummaryOrganization(OrganizationDTO summary4OrganizationDTO) {
        OrganizationDTO org = summary4OrganizationDTO;
        if (org == null) {
            //We're going to default to the Unknown Organization if one is not provided.
            org = new OrganizationDTO();
            List<String> email = Arrays.asList("unknown@unknown.com");
            org.setName(EnOnConverter.convertToEnOn("Unknown"));
            org.setTelecomAddress(DSetConverter.convertListToDSet(email, DSetConverter.TYPE_EMAIL, null));
            org.setPostalAddress(AddressConverterUtil.create("UNKNOWN", "UNKNOWN", "UNKNOWN", "MD", "00000", "USA"));
        }
        return org;
    }

    private StudyResourcingDTO getSummaryStudyResourcing(StudyResourcingDTO summary4StudyResourcingDTO,
            SummaryFourFundingCategoryCode categoryCode) {
        StudyResourcingDTO resourcing = summary4StudyResourcingDTO;
        if (resourcing == null) {
            resourcing = new StudyResourcingDTO();
        }
        if (resourcing.getTypeCode() == null) {
            Cd typeCode = new Cd();
            typeCode.setCode(categoryCode.getCode());
            resourcing.setTypeCode(typeCode);
        }
        return resourcing;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void update(StudyProtocolDTO studyProtocolDTO, StudyOverallStatusDTO overallStatusDTO,
            List<StudyResourcingDTO> studyResourcingDTOs, List<DocumentDTO> documentDTOs,
            List<StudySiteAccrualStatusDTO> studySiteAccrualStatusDTOs, List<StudySiteDTO> studySiteDTOs,
            Bl isBatchMode)
            throws PAException {
        throw new UnsupportedOperationException();
    }

    // CHECKSTYLE:OFF
    @SuppressWarnings({ "PMD.ExcessiveParameterList",
            "PMD.CyclomaticComplexity" })
    @Override
    public Ii createCompleteInterventionalStudyProtocol(
            StudyProtocolDTO studyProtocolDTO,
            StudyOverallStatusDTO overallStatusDTO,
            List<StudyIndldeDTO> studyIndldeDTOs,
            List<StudyResourcingDTO> studyResourcingDTOs,
            List<DocumentDTO> documentDTOs,
            OrganizationDTO leadOrganizationDTO,
            PersonDTO principalInvestigatorDTO,
            OrganizationDTO sponsorOrganizationDTO,
            StudySiteDTO leadOrganizationSiteIdentifierDTO,
            List<StudySiteDTO> studyIdentifierDTOs,
            StudyContactDTO studyContactDTO,
            StudySiteContactDTO studySiteContactDTO,
            List<OrganizationDTO> summary4organizationDTO,
            StudyResourcingDTO summary4studyResourcingDTO,
            Ii responsiblePartyContactIi,
            StudyRegulatoryAuthorityDTO studyRegAuthDTO, Bl isBatchMode,
            DSet<Tel> owners) throws PAException {
        throw new PAException(
                "Unsupported operation; available in v3.4.1 and onwards only");
    }

    @SuppressWarnings({ "PMD.ExcessiveParameterList",
            "PMD.CyclomaticComplexity" })
    @Override
    public Ii createAbbreviatedInterventionalStudyProtocol(
            StudyProtocolDTO studyProtocolDTO,
            StudySiteAccrualStatusDTO studySiteAccrualStatusDTO,
            List<DocumentDTO> documentDTOs,
            OrganizationDTO leadOrganizationDTO,
            PersonDTO studySiteInvestigatorDTO,
            StudySiteDTO leadOrganizationStudySiteDTO,
            OrganizationDTO studySiteOrganizationDTO,
            StudySiteDTO studySiteDTO, StudySiteDTO nctIdentifierDTO,
            List<OrganizationDTO> summary4OrganizationDTO,
            StudyResourcingDTO summary4StudyResourcingDTO, Bl isBatchMode,
            DSet<Tel> owners) throws PAException {
        throw new PAException(
                "Unsupported operation; available in v3.4.1 and onwards only");
    }

   
    public Ii amend(StudyProtocolDTO studyProtocolDTO,
            StudyOverallStatusDTO overallStatusDTO,
            List<StudyIndldeDTO> studyIndldeDTOs,
            List<StudyResourcingDTO> studyResourcingDTOs,
            List<DocumentDTO> documentDTOs,
            OrganizationDTO leadOrganizationDTO,
            PersonDTO principalInvestigatorDTO,
            OrganizationDTO sponsorOrganizationDTO,
            StudySiteDTO leadOrganizationSiteIdentifierDTO,
            List<StudySiteDTO> studyIdentifierDTOs,
            StudyContactDTO studyContactDTO,
            StudySiteContactDTO studySiteContactDTO,
            List<OrganizationDTO> summary4organizationDTO,
            StudyResourcingDTO summary4studyResourcingDTO,
            Ii responsiblePartyContactIi,
            StudyRegulatoryAuthorityDTO studyRegAuthDTO, Bl isBatchMode,
            Bl handleDuplicateGrantAndINDsGracefully) throws PAException {
        throw new PAException(
                "Unsupported operation; available in v3.4 and onwards only");
    }
}