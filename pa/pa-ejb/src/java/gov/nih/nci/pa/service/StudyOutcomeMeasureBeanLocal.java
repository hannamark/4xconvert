/**
 *
 */
package gov.nih.nci.pa.service;

import gov.nih.nci.pa.domain.StudyOutcomeMeasure;
import gov.nih.nci.pa.iso.convert.StudyOutcomeMeasureConverter;
import gov.nih.nci.pa.iso.dto.StudyOutcomeMeasureDTO;
import gov.nih.nci.pa.util.PaHibernateSessionInterceptor;

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

}
