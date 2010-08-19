/**
 *
 */
package gov.nih.nci.pa.service;

import gov.nih.nci.coppa.services.LimitOffset;
import gov.nih.nci.coppa.services.TooManyResultsException;
import gov.nih.nci.pa.domain.StudyRelationship;
import gov.nih.nci.pa.iso.convert.Converters;
import gov.nih.nci.pa.iso.convert.StudyRelationshipConverter;
import gov.nih.nci.pa.iso.dto.StudyRelationshipDTO;
import gov.nih.nci.pa.service.search.AnnotatedBeanSearchCriteria;
import gov.nih.nci.pa.service.search.StudyRelationshipSortCriterion;
import gov.nih.nci.pa.util.HibernateSessionInterceptor;
import gov.nih.nci.pa.util.PAConstants;

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
@Interceptors({ HibernateSessionInterceptor.class })
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class StudyRelationshipBeanLocal extends
        AbstractBaseIsoService<StudyRelationshipDTO, StudyRelationship, StudyRelationshipConverter> implements
        StudyRelationshipServiceLocal {

    /**
     * {@inheritDoc}
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<StudyRelationshipDTO> search(StudyRelationshipDTO dto, LimitOffset pagingParams) throws PAException,
    TooManyResultsException {
        if (dto == null) {
            throw new PAException("StudyRelationshipDTO should not be null.");
        }

        StudyRelationship criteria = Converters.get(StudyRelationshipConverter.class).convertFromDtoToDomain(dto);

        int maxLimit = Math.min(pagingParams.getLimit(), PAConstants.MAX_SEARCH_RESULTS + 1);
        PageSortParams<StudyRelationship> params = new PageSortParams<StudyRelationship>(maxLimit,
                pagingParams.getOffset(), StudyRelationshipSortCriterion.STUDY_RELATIONSHIP_ID, false);

        List<StudyRelationship> studyRelationshipList =
            search(new AnnotatedBeanSearchCriteria<StudyRelationship>(criteria), params);
        if (studyRelationshipList.size() > PAConstants.MAX_SEARCH_RESULTS) {
            throw new TooManyResultsException(PAConstants.MAX_SEARCH_RESULTS);
        }
        return convertFromDomainToDTO(studyRelationshipList);
    }

    private List<StudyRelationshipDTO> convertFromDomainToDTO(List<StudyRelationship> studyProtocolList) {
        List<StudyRelationshipDTO> studyRelationshipDTOList = new ArrayList<StudyRelationshipDTO>();
        StudyRelationshipConverter studyRelationshipConverter = new StudyRelationshipConverter();
        if (studyProtocolList != null) {
            studyRelationshipDTOList = new ArrayList<StudyRelationshipDTO>();
            for (StudyRelationship sp : studyProtocolList) {
                studyRelationshipDTOList.add(studyRelationshipConverter.convertFromDomainToDto(sp));
            }
        }
        return studyRelationshipDTOList;
    }

}
