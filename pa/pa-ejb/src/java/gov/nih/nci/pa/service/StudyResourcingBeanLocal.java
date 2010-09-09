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
import gov.nih.nci.pa.service.exception.PADuplicateException;
import gov.nih.nci.pa.service.search.AnnotatedBeanSearchCriteria;
import gov.nih.nci.pa.service.util.CSMUserService;
import gov.nih.nci.pa.util.HibernateSessionInterceptor;
import gov.nih.nci.pa.util.HibernateUtil;
import gov.nih.nci.pa.util.PADomainUtils;
import gov.nih.nci.pa.util.PAUtil;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

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

    /**
     * Set the invocation context.
     * @param ctx EJB context
     */
    @Override
    @Resource
    public void setSessionContext(SessionContext ctx) {
        this.ejbContext = ctx;
    }

    /**
     * @param studyProtocolIi Ii
     * @return StudyProtocolDTO
     * @throws PAException PAException
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public StudyResourcingDTO getSummary4ReportedResourcing(Ii studyProtocolIi) throws PAException {
        if (PAUtil.isIiNull(studyProtocolIi)) {
            throw new PAException("studyProtocol Identifer should not be null");
        }

        StudyResourcing criteria = new StudyResourcing();
        StudyProtocol sp = new StudyProtocol();
        sp.setId(IiConverter.convertToLong(studyProtocolIi));
        criteria.setStudyProtocol(sp);
        criteria.setSummary4ReportedResourceIndicator(Boolean.TRUE);

        List<StudyResourcing> results = search(new AnnotatedBeanSearchCriteria<StudyResourcing>(criteria));

        if (results.size() > 1) {
            throw new PAException(" Summary 4 Reported Sourcing should not be more than 1 record ");
        }
        return results.isEmpty() ? null : convertFromDomainToDto(results.get(0));
    }

    /**
     *
     * @param studyResourcingDTO StudyResourcingDTO
     * @return StudyProtocolDTO
     * @throws PAException PAException
     */
    public StudyResourcingDTO updateStudyResourcing(StudyResourcingDTO studyResourcingDTO) throws PAException {
        if (studyResourcingDTO == null) {
            throw new PAException("studyResourcingDTO should not be null.");
        }

        enforceValidation(studyResourcingDTO);

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
     *
     * @param studyResourcingDTO StudyResourcingDTO
     * @return StudyProtocolDTO
     * @throws PAException PAException
     */
    public StudyResourcingDTO createStudyResourcing(StudyResourcingDTO studyResourcingDTO) throws PAException {

        if (studyResourcingDTO == null) {
            throw new PAException("studyResourcingDTO should not be null");
        }
        enforceValidation(studyResourcingDTO);

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
     * @param studyProtocolIi Ii
     * @return StudyResourcingDTO
     * @throws PAException PAException
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<StudyResourcingDTO> getStudyResourcingByStudyProtocol(Ii studyProtocolIi)
    throws PAException {
        if (PAUtil.isIiNull(studyProtocolIi)) {
            throw new PAException("studyProtocol Identifer should not be null.");
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
     * @param studyResourceIi Ii
     * @return StudyResourcingDTO
     * @throws PAException PAException
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public StudyResourcingDTO getStudyResourcingById(Ii studyResourceIi) throws PAException {
        return get(studyResourceIi);
    }

    /**
     *
     * @param studyResourcingDTO StudyResourcingDTO
     * @return StudyResourcingDTO
     * @throws PAException PAException
     */
    public Boolean deleteStudyResourcingById(StudyResourcingDTO studyResourcingDTO) throws PAException {
        if (studyResourcingDTO == null) {
            throw new PAException("studyResourcingDTO should not be null.");
        }
        StudyResourcingDTO toDelete = super.get(studyResourcingDTO.getIdentifier());
        toDelete.setActiveIndicator(BlConverter.convertToBl(Boolean.FALSE));
        toDelete.setInactiveCommentText(studyResourcingDTO.getInactiveCommentText());
        super.update(toDelete);
        return Boolean.FALSE;
    }

    private boolean enforceNoDuplicate(StudyResourcingDTO dto) throws PAException {
        String newSerialNumber = dto.getSerialNumber().getValue();
        String newFundingMech = dto.getFundingMechanismCode().getCode();
        String newNciDivCode = dto.getNciDivisionProgramCode().getCode();
        String newNihInstCode = dto.getNihInstitutionCode().getCode();
        List<StudyResourcingDTO> spList = getStudyResourcingByStudyProtocol(dto.getStudyProtocolIdentifier());
        boolean duplicateExists = false;
        for (StudyResourcingDTO sp : spList) {
            boolean sameSerialNumber = newSerialNumber.equals(sp.getSerialNumber().getValue());
            boolean sameFundingMech = newFundingMech.equals(sp.getFundingMechanismCode().getCode());
            boolean sameNciDivCode = newNciDivCode.equals(sp.getNciDivisionProgramCode().getCode());
            boolean sameNihInstCode = newNihInstCode.equals(sp.getNihInstitutionCode().getCode());

            if (sameSerialNumber && sameFundingMech && sameNciDivCode && sameNihInstCode) {
                if (dto.getIdentifier() == null
                        || (!dto.getIdentifier().getExtension().equals(sp.getIdentifier().getExtension()))) {
                    duplicateExists = true;
                    break;
                }
            }
        }
        return duplicateExists;
    }

    private boolean isNumeric(String number) {
        boolean isValid = false;
        // Initialize reg ex for numeric data.
        String expression = "^[0-9]*[0-9]+$";
        CharSequence inputStr = number;
        Pattern pattern = Pattern.compile(expression);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }

    /**
     * @param studyResourcingDTO dto
     * @return
     * @throws PAException e
     */
    @Override
    public void validate(StudyResourcingDTO studyResourcingDTO) throws PAException {
        enforceValidation(studyResourcingDTO);
    }

    /**
     * @param dto StudyResourcingDTO to create
     * @return the created StudyResourcingDTO
     * @throws PAException exception.
     */
    @Override
    public StudyResourcingDTO create(StudyResourcingDTO dto) throws PAException {
        if (PAUtil.isBlNull(dto.getSummary4ReportedResourceIndicator())) {
            throw new PAException("The summary4ReportedResourceIndicator is not set");
        }
        if (!PAUtil.isBlNull(dto.getSummary4ReportedResourceIndicator())
                && BlConverter.convertToBoolean(dto.getSummary4ReportedResourceIndicator()).equals(Boolean.FALSE)) {
            return createStudyResourcing(dto);
        }
        return super.create(dto);
    }

    /**
     * @param dto StudyResourcingDTO to update
     * @return the updated StudyResourcingDTO
     * @throws PAException exception.
     */
    @Override
    public StudyResourcingDTO update(StudyResourcingDTO dto) throws PAException {
        if (PAUtil.isBlNull(dto.getSummary4ReportedResourceIndicator())) {
            throw new PAException("The summary4ReportedResourceIndicator is not set");
        }
        if (!PAUtil.isBlNull(dto.getSummary4ReportedResourceIndicator())
                && BlConverter.convertToBoolean(dto.getSummary4ReportedResourceIndicator()).equals(Boolean.FALSE)) {
            return updateStudyResourcing(dto);
        }
        return super.update(dto);

    }

    private void enforceValidation(StudyResourcingDTO studyResourcingDTO) throws PAException {
        StringBuffer errorBuffer =  new StringBuffer();
        final int serialNumMin = 5;
        final int serialNumMax = 6;
        if (!PAUtil.isBlNull(studyResourcingDTO.getSummary4ReportedResourceIndicator())
                && BlConverter.convertToBoolean(studyResourcingDTO.getSummary4ReportedResourceIndicator())
                .equals(Boolean.FALSE)) {
            //check if nih institute code exists
            if (!PAUtil.isCdNull(studyResourcingDTO.getNihInstitutionCode())) {
                boolean nihExists =
                    PADomainUtils.checkIfValueExists(studyResourcingDTO.getNihInstitutionCode().getCode(),
                            "NIH_INSTITUTE", "nih_institute_code");
                if (!nihExists) {
                    errorBuffer.append("Error while checking for value ")
                    .append(studyResourcingDTO.getNihInstitutionCode().getCode())
                    .append(" from table NIH_INSTITUTE\n");
                }
            }
            if (!PAUtil.isCdNull(studyResourcingDTO.getFundingMechanismCode())) {
                //check if Funding mechanism code exists
                boolean fmExists =
                    PADomainUtils.checkIfValueExists(studyResourcingDTO.getFundingMechanismCode().getCode(),
                            "FUNDING_MECHANISM", "funding_mechanism_code");

                if (!fmExists) {
                    errorBuffer.append("Error while checking for value ")
                    .append(studyResourcingDTO.getFundingMechanismCode().getCode())
                    .append(" from table FUNDING_MECHANISM\n");
                }
            }
            if (studyResourcingDTO.getSerialNumber() != null
                    && studyResourcingDTO.getSerialNumber().getValue() != null) {
                String snValue = studyResourcingDTO.getSerialNumber().getValue().toString();
                if (snValue.length() < serialNumMin || snValue.length() > serialNumMax) {
                    errorBuffer.append("Serial number can be numeric with 5 or 6 digits\n");
                }
                if (!isNumeric(snValue)) {
                    errorBuffer.append("Serial number should have numbers from [0-9]\n");
                }
            }
            if (PAUtil.isIiNotNull(studyResourcingDTO.getStudyProtocolIdentifier())) {
                //this means it is original submission thats why not having Ii
                boolean dupExists = enforceNoDuplicate(studyResourcingDTO);
                if (dupExists) {
                    errorBuffer.append("Duplicate grants are not allowed\n");
                }
            }
        }
        if (errorBuffer.length() > 0) {
            throw new PADuplicateException("Validation Exception " + errorBuffer.toString());
        }
    }
}
