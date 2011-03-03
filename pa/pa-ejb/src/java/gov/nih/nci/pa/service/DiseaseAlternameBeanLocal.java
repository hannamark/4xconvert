/**
 *
 */
package gov.nih.nci.pa.service;

import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.domain.PDQDisease;
import gov.nih.nci.pa.domain.DiseaseAltername;
import gov.nih.nci.pa.iso.convert.DiseaseAlternameConverter;
import gov.nih.nci.pa.iso.dto.DiseaseAlternameDTO;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.search.AnnotatedBeanSearchCriteria;
import gov.nih.nci.pa.service.search.DiseaseAlternameSortCriterion;
import gov.nih.nci.pa.util.HibernateSessionInterceptor;
import gov.nih.nci.pa.util.PAConstants;
import gov.nih.nci.pa.util.PAUtil;

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
@Interceptors(HibernateSessionInterceptor.class)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class DiseaseAlternameBeanLocal
    extends AbstractBaseIsoService<DiseaseAlternameDTO, DiseaseAltername, DiseaseAlternameConverter>
    implements DiseaseAlternameServiceLocal , DiseaseAlternameServiceRemote {

    /**
     * {@inheritDoc}
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<DiseaseAlternameDTO> getByDisease(Ii ii) throws PAException {
        if (PAUtil.isIiNull(ii)) {
            throw new PAException("Check the Ii value; null found.  ");
        }

        DiseaseAltername criteria = new DiseaseAltername();
        PDQDisease disease = new PDQDisease();
        disease.setId(IiConverter.convertToLong(ii));
        criteria.setDisease(disease);

        PageSortParams<DiseaseAltername> params = new PageSortParams<DiseaseAltername>(PAConstants.MAX_SEARCH_RESULTS,
                0, DiseaseAlternameSortCriterion.DISEASE_ALTERNAME_ID, false);
        // step 3: query the result
        List<DiseaseAltername> results = search(new AnnotatedBeanSearchCriteria<DiseaseAltername>(criteria), params);
        return convertFromDomainToDTOs(results);
    }
}
