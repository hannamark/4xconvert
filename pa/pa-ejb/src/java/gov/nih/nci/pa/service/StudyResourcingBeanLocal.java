/**
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

import gov.nih.nci.iso21090.Cd;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.domain.StudyProtocol;
import gov.nih.nci.pa.domain.StudyResourcing;
import gov.nih.nci.pa.enums.NciDivisionProgramCode;
import gov.nih.nci.pa.enums.SummaryFourFundingCategoryCode;
import gov.nih.nci.pa.iso.convert.StudyResourcingConverter;
import gov.nih.nci.pa.iso.dto.StudyResourcingDTO;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.service.exception.PAValidationException;
import gov.nih.nci.pa.service.search.AnnotatedBeanSearchCriteria;
import gov.nih.nci.pa.service.util.CSMUserService;
import gov.nih.nci.pa.service.util.PAServiceUtils;
import gov.nih.nci.pa.util.HibernateSessionInterceptor;
import gov.nih.nci.pa.util.HibernateUtil;
import gov.nih.nci.pa.util.PADomainUtils;
import gov.nih.nci.pa.util.PAUtil;

import java.util.List;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.hibernate.Session;

/**
 * @author asharma
 *
 */
@Stateless
@Interceptors({ HibernateSessionInterceptor.class })
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class StudyResourcingBeanLocal extends
        AbstractStudyIsoService<StudyResourcingDTO, StudyResourcing, StudyResourcingConverter> implements
        StudyResourcingServiceLocal {

    private static StudyResourcingConverter src = new StudyResourcingConverter();
    private SessionContext ejbContext;
    private PAServiceUtils paServiceUtils = new PAServiceUtils();

    /**
     * @param paServiceUtils the paServiceUtils to set
     */
    public void setPaServiceUtils(PAServiceUtils paServiceUtils) {
        this.paServiceUtils = paServiceUtils;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Resource
    public void setSessionContext(SessionContext ctx) {
        this.ejbContext = ctx;
    }

    /**
     * {@inheritDoc}
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public StudyResourcingDTO getSummary4ReportedResourcing(Ii studyProtocolIi) throws PAException {
        if (PAUtil.isIiNull(studyProtocolIi)) {
            throw new PAException("studyProtocol Identifier should not be null");
        }

        StudyResourcing criteria = new StudyResourcing();
        StudyProtocol sp = new StudyProtocol();
        sp.setId(IiConverter.convertToLong(studyProtocolIi));
        criteria.setStudyProtocol(sp);
        criteria.setSummary4ReportedResourceIndicator(Boolean.TRUE);

        List<StudyResourcing> results = search(new AnnotatedBeanSearchCriteria<StudyResourcing>(criteria));

        if (results.size() > 1) {
            throw new PAException("Summary 4 Reported Sourcing should not be more than 1 record");
        }
        return results.isEmpty() ? null : convertFromDomainToDto(results.get(0));
    }

    /**
     * {@inheritDoc}
     */
    public StudyResourcingDTO updateStudyResourcing(StudyResourcingDTO studyResourcingDTO) throws PAException {
        validate(studyResourcingDTO);

        StudyResourcing studyResourcing = convertFromDtoToDomain(super.get(studyResourcingDTO.getIdentifier()));
        // set the values from paramter
        if (studyResourcingDTO.getTypeCode() != null) {
            studyResourcing.setTypeCode(SummaryFourFundingCategoryCode.getByCode(
                    studyResourcingDTO.getTypeCode().getCode()));
        }
        studyResourcing.setOrganizationIdentifier(IiConverter.convertToString(
                studyResourcingDTO.getOrganizationIdentifier()));
        studyResourcing.setDateLastUpdated(new java.sql.Timestamp((new java.util.Date()).getTime()));
        studyResourcing.setUserLastUpdated(CSMUserService.getInstance().lookupUser(ejbContext));
        studyResourcing.setFundingMechanismCode(CdConverter.convertCdToString(
                studyResourcingDTO.getFundingMechanismCode()));
        studyResourcing.setNciDivisionProgramCode(NciDivisionProgramCode.getByCode(
                studyResourcingDTO.getNciDivisionProgramCode().getCode()));
        studyResourcing.setNihInstituteCode(studyResourcingDTO.getNihInstitutionCode().getCode());
        studyResourcing.setSerialNumber(StConverter.convertToString(studyResourcingDTO.getSerialNumber()));
        Session session = HibernateUtil.getCurrentSession();
        session.merge(studyResourcing);
        session.flush();
        return src.convertFromDomainToDto(studyResourcing);
    }

    /**
     * {@inheritDoc}
     */
    public StudyResourcingDTO createStudyResourcing(StudyResourcingDTO studyResourcingDTO) throws PAException {
        validate(studyResourcingDTO);
        Session session = null;
        StudyResourcing studyResourcing = src.convertFromDtoToDomain(studyResourcingDTO);
        java.sql.Timestamp now = new java.sql.Timestamp((new java.util.Date()).getTime());
        studyResourcing.setDateLastCreated(now);
        studyResourcing.setUserLastCreated(CSMUserService.getInstance().lookupUser(ejbContext));
        // create Protocol Obj
        StudyProtocol studyProtocol = new StudyProtocol();
        studyProtocol.setId(IiConverter.convertToLong(studyResourcingDTO.getStudyProtocolIdentifier()));
        studyResourcing.setStudyProtocol(studyProtocol);
        studyResourcing.setActiveIndicator(true);
        session = HibernateUtil.getCurrentSession();
        session.save(studyResourcing);
        session.flush();
        return src.convertFromDomainToDto(studyResourcing);
    }

    /**
     * {@inheritDoc}
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<StudyResourcingDTO> getStudyResourcingByStudyProtocol(Ii studyProtocolIi) throws PAException {
        if (PAUtil.isIiNull(studyProtocolIi)) {
            throw new PAException("studyProtocol Identifier should not be null.");
        }

        StudyResourcing criteria = new StudyResourcing();
        StudyProtocol sp = new StudyProtocol();
        sp.setId(IiConverter.convertToLong(studyProtocolIi));
        criteria.setStudyProtocol(sp);
        criteria.setSummary4ReportedResourceIndicator(Boolean.FALSE);
        criteria.setActiveIndicator(Boolean.TRUE);

        List<StudyResourcing> results = search(new AnnotatedBeanSearchCriteria<StudyResourcing>(criteria));
        return convertFromDomainToDTOs(results);
    }

    /**
     * {@inheritDoc}
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public StudyResourcingDTO getStudyResourcingById(Ii studyResourceIi) throws PAException {
        return get(studyResourceIi);
    }

    /**
     * {@inheritDoc}
     */
    public Boolean deleteStudyResourcingById(StudyResourcingDTO studyResourcingDTO) throws PAException {
        if (studyResourcingDTO == null) {
            throw new PAException("studyResourcingDTO should not be null.");
        }
        StudyResourcingDTO toDelete = super.get(studyResourcingDTO.getIdentifier());
        toDelete.setActiveIndicator(BlConverter.convertToBl(Boolean.FALSE));
        toDelete.setInactiveCommentText(studyResourcingDTO.getInactiveCommentText());
        super.setSessionContext(ejbContext);
        super.update(toDelete);
        return Boolean.FALSE;
    }

    private boolean enforceNoDuplicate(StudyResourcingDTO dto) throws PAException {
        boolean duplicateExists = false;
        if (PAUtil.isIiNotNull(dto.getStudyProtocolIdentifier())) {
            //this means it is original submission thats why not having Ii
            List<StudyResourcingDTO> spList = getStudyResourcingByStudyProtocol(dto.getStudyProtocolIdentifier());
            for (StudyResourcingDTO sp : spList) {
                if (paServiceUtils.isGrantDuplicate(dto, sp) && (dto.getIdentifier() == null
                        || (!dto.getIdentifier().getExtension().equals(sp.getIdentifier().getExtension())))) {
                    duplicateExists = true;
                    break;
                }
            }
        }
        return duplicateExists;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void validate(StudyResourcingDTO studyResourcingDTO) throws PAException {
        if (studyResourcingDTO == null) {
            throw new PAValidationException("studyResourcingDTO should not be null");
        }
        StringBuffer errorBuffer =  new StringBuffer();
        if (!BooleanUtils.toBooleanDefaultIfNull(
                BlConverter.convertToBoolean(studyResourcingDTO.getSummary4ReportedResourceIndicator()), true)) {
            validateGrantInformation(studyResourcingDTO);
            //check if NIH institute code exists
            isCodeExists(studyResourcingDTO.getNihInstitutionCode(), errorBuffer, "NIH_INSTITUTE",
                    "nih_institute_code");
            isCodeExists(studyResourcingDTO.getFundingMechanismCode(), errorBuffer, "FUNDING_MECHANISM",
                    "funding_mechanism_code");
            validateSerialNo(studyResourcingDTO, errorBuffer);
            if (enforceNoDuplicate(studyResourcingDTO)) {
                errorBuffer.append("Duplicate grants are not allowed.\n");
            }
        }
        if (errorBuffer.length() > 0) {
            throw new PAValidationException("Validation Exception " + errorBuffer.toString());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StudyResourcingDTO create(StudyResourcingDTO dto) throws PAException {
        if (PAUtil.isBlNull(dto.getSummary4ReportedResourceIndicator())) {
            throw new PAException("The summary4ReportedResourceIndicator is not set.");
        }
        if (!BooleanUtils.toBooleanDefaultIfNull(
                BlConverter.convertToBoolean(dto.getSummary4ReportedResourceIndicator()), true)) {
            return createStudyResourcing(dto);
        }
        return super.create(dto);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StudyResourcingDTO update(StudyResourcingDTO dto) throws PAException {
        if (PAUtil.isBlNull(dto.getSummary4ReportedResourceIndicator())) {
            throw new PAException("The summary4ReportedResourceIndicator is not set.");
        }
        if (!BooleanUtils.toBooleanDefaultIfNull(
                BlConverter.convertToBoolean(dto.getSummary4ReportedResourceIndicator()), true)) {
            return updateStudyResourcing(dto);
        }
        return super.update(dto);

    }

    private void validateGrantInformation(StudyResourcingDTO dto) throws PAException {
        if (PAUtil.isCdNull(dto.getFundingMechanismCode())) {
            throw new PAValidationException("The funding mechanism code is not set.");
        }
        if (PAUtil.isCdNull(dto.getNihInstitutionCode())) {
            throw new PAValidationException("The NIH Institution code is not set.");
        }
        if (PAUtil.isStNull(dto.getSerialNumber())) {
            throw new PAValidationException("The serial number is not set.");
        }
        if (PAUtil.isCdNull(dto.getNciDivisionProgramCode())) {
            throw new PAValidationException("The NCI Division/Program code is not set.");
        }
    }


    private void validateSerialNo(StudyResourcingDTO studyResourcingDTO, StringBuffer errorBuffer) {
        final int serialNumMin = 5;
        final int serialNumMax = 6;
        if (!PAUtil.isStNull(studyResourcingDTO.getSerialNumber())) {
            String snValue = studyResourcingDTO.getSerialNumber().getValue();
            if (snValue.length() < serialNumMin || snValue.length() > serialNumMax) {
                errorBuffer.append("Serial number can be numeric with 5 or 6 digits\n");
            }
            if (!NumberUtils.isDigits(snValue)) {
                errorBuffer.append("Serial number should have numbers from [0-9]\n");
            }
        }
    }

    private void isCodeExists(Cd code, StringBuffer errorBuffer, String tableName, String codeName) throws PAException {
        if (!PAUtil.isCdNull(code)) {
            boolean isExists = PADomainUtils.checkIfValueExists(code.getCode(), tableName, codeName);
            if (!isExists) {
                errorBuffer.append("Error while checking for value ")
                .append(code.getCode()).append(" from table ").append(tableName).append("\n.");
            }
        }
    }
}
