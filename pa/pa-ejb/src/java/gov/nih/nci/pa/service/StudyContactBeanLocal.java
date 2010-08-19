/**
 *
 */
package gov.nih.nci.pa.service;

import gov.nih.nci.coppa.services.LimitOffset;
import gov.nih.nci.coppa.services.TooManyResultsException;
import gov.nih.nci.pa.domain.StructuralRole;
import gov.nih.nci.pa.domain.StudyContact;
import gov.nih.nci.pa.enums.ActStatusCode;
import gov.nih.nci.pa.enums.FunctionalRoleStatusCode;
import gov.nih.nci.pa.iso.convert.Converters;
import gov.nih.nci.pa.iso.convert.StudyContactConverter;
import gov.nih.nci.pa.iso.dto.StudyContactDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.DSetConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.search.AnnotatedBeanSearchCriteria;
import gov.nih.nci.pa.service.search.StudyContactSortCriterion;
import gov.nih.nci.pa.service.util.PAServiceUtils;
import gov.nih.nci.pa.util.HibernateSessionInterceptor;
import gov.nih.nci.pa.util.PAConstants;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.services.correlation.ClinicalResearchStaffDTO;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

import com.fiveamsolutions.nci.commons.data.search.PageSortParams;

/**
 * @author asharma
 *
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@Interceptors(HibernateSessionInterceptor.class)
public class StudyContactBeanLocal extends AbstractRoleIsoService<StudyContactDTO, StudyContact, StudyContactConverter>
        implements StudyContactServiceLocal {

    /**
     * {@inheritDoc}
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<StudyContactDTO> search(StudyContactDTO dto, LimitOffset pagingParams) throws PAException,
    TooManyResultsException {
        if (dto == null) {
            throw new PAException("StudyContactDTO should not be null ");
        }

        StudyContact criteria = Converters.get(StudyContactConverter.class).convertFromDtoToDomain(dto);
        criteria.setStatusCode(null);

        if (dto.getStatusCode() != null) {
          criteria.setStatusCode(FunctionalRoleStatusCode.getByCode(dto.getStatusCode().getCode()));
        }

        int maxLimit = Math.min(pagingParams.getLimit(), PAConstants.MAX_SEARCH_RESULTS + 1);
        PageSortParams<StudyContact> params = new PageSortParams<StudyContact>(maxLimit, pagingParams.getOffset(),
                    StudyContactSortCriterion.STUDY_CONTACT_ID, false);
        List<StudyContact> studyContactList = search(new AnnotatedBeanSearchCriteria<StudyContact>(criteria), params);

        if (studyContactList.size() > PAConstants.MAX_SEARCH_RESULTS) {
            throw new TooManyResultsException(PAConstants.MAX_SEARCH_RESULTS);
        }
        return convertFromDomainToDTO(studyContactList);
    }

    private List<StudyContactDTO> convertFromDomainToDTO(List<StudyContact> studyContactList) throws PAException {
        List<StudyContactDTO> studyContactDTOList = null;
        StudyContactConverter scConverter = new StudyContactConverter();
        if (studyContactList != null) {
            studyContactDTOList = new ArrayList<StudyContactDTO>();
            for (StudyContact ss : studyContactList) {
                StudyContactDTO studyContactDTO = scConverter.convertFromDomainToDto(ss);
                studyContactDTOList.add(studyContactDTO);
            }
        }
        return studyContactDTOList;
    }
    /**
     * @param dto dto
     * @throws PAException e
     * @return updated dto
     */
    @Override
    public StudyContactDTO update(StudyContactDTO dto) throws PAException {
        validate(dto);
        getStatusCode(dto);
        return super.update(dto);
    }
    /**
     * validates the dto.
     * @param dto dto to validate.
     * @throws PAException e
     */
    @Override
    public void validate(StudyContactDTO dto) throws PAException {
        PAServiceUtils paServiceUtil = new PAServiceUtils();
        if (!PAUtil.isIiNull(dto.getClinicalResearchStaffIi()) && !PAUtil.isDSetTelNull(dto.getTelecomAddresses())) {
            StructuralRole sr = paServiceUtil.getStructuralRole(IiConverter.convertToPoClinicalResearchStaffIi(
                    dto.getClinicalResearchStaffIi().getExtension()));
            if (sr != null) {
                ClinicalResearchStaffDTO  poSrDto = (ClinicalResearchStaffDTO) paServiceUtil.getCorrelationByIi(
                        IiConverter.convertToPoClinicalResearchStaffIi(sr.getIdentifier()));
                if (paServiceUtil.isEntityCountryUSAOrCanada(poSrDto.getPlayerIdentifier())
                        && !PAUtil.isPhoneValidForUSA(DSetConverter.convertDSetToList(
                                dto.getTelecomAddresses(), PAConstants.PHONE).get(0))) {
                    throw new PAException("Please enter phone in xxx-xxx-xxxx format for USA or CANADA");
                }
            }
        }


    }
    /**
     * @param dto dto to validate
     * @throws PAException e
     * @return dto
     */
    @Override
    public StudyContactDTO create(StudyContactDTO dto) throws PAException {
        validate(dto);
        return super.create(dto);
    }
    private void getStatusCode(StudyContactDTO dto) throws PAException {
        PAServiceUtils paServiceUtil = new PAServiceUtils();
        StructuralRole sr =  null;
        if (!PAUtil.isIiNull(dto.getClinicalResearchStaffIi())) {
            sr = paServiceUtil.getStructuralRole(IiConverter.convertToPoClinicalResearchStaffIi(
                    dto.getClinicalResearchStaffIi().getExtension()));
        }
        if (!PAUtil.isIiNull(dto.getHealthCareProviderIi())) {
            sr = paServiceUtil.getStructuralRole(IiConverter.convertToPoHealtcareProviderIi(
                    dto.getHealthCareProviderIi().getExtension()));
        }
        if (!PAUtil.isIiNull(dto.getOrganizationalContactIi())) {
            sr = paServiceUtil.getStructuralRole(IiConverter.convertToPoOrganizationalContactIi(
                    dto.getOrganizationalContactIi().getExtension()));
        }
        if (sr != null) {
               dto.setStatusCode(getFunctionalRoleStatusCode(CdConverter.convertStringToCd(
                       sr.getStatusCode().getCode()), ActStatusCode.ACTIVE));
         }

    }
}
