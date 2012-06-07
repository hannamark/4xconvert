/**
 *
 */
package gov.nih.nci.pa.service;

import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.domain.StudyOutcomeMeasure;
import gov.nih.nci.pa.iso.convert.StudyOutcomeMeasureConverter;
import gov.nih.nci.pa.iso.dto.StudyOutcomeMeasureDTO;
import gov.nih.nci.pa.iso.util.IntConverter;
import gov.nih.nci.pa.util.PaHibernateSessionInterceptor;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

/**
 * @author asharma
 *
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@Interceptors(PaHibernateSessionInterceptor.class)
public class StudyOutcomeMeasureBeanLocal extends
        AbstractStudyIsoService<StudyOutcomeMeasureDTO, StudyOutcomeMeasure, StudyOutcomeMeasureConverter> implements
        StudyOutcomeMeasureServiceLocal {
    
    
    @Override
    protected String getQueryOrderClause() {        
        return " order by alias.displayOrder, alias.id";
    }

    @Override
    public void reorderOutcomes(Ii studyProtocolIi, List<String> ids)
            throws PAException {
        List<StudyOutcomeMeasureDTO> outcomes = getByStudyProtocol(studyProtocolIi);
        for (StudyOutcomeMeasureDTO outcome : outcomes) {
            String id = outcome.getIdentifier().getExtension();
            outcome.setDisplayOrder(ids.contains(id) ? IntConverter
                    .convertToInt(ids.indexOf(id)) : IntConverter
                    .convertToInt((Integer) null));
            update(outcome);
        }
    }

}
