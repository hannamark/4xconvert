/**
 * 
 */
package gov.nih.nci.pa.service.internal;

import gov.nih.nci.pa.domain.StudyOutcomeMeasure;
import gov.nih.nci.pa.iso.convert.StudyOutcomeMeasureConverter;
import gov.nih.nci.pa.iso.dto.StudyOutcomeMeasureDTO;
import gov.nih.nci.pa.service.AbstractStudyIsoService;
import gov.nih.nci.pa.service.StudyOutcomeMeasureServiceLocal;
import gov.nih.nci.pa.util.HibernateSessionInterceptor;

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
@Interceptors(HibernateSessionInterceptor.class)
public class StudyOutcomeMeasureBeanLocal  extends
AbstractStudyIsoService<StudyOutcomeMeasureDTO, StudyOutcomeMeasure, StudyOutcomeMeasureConverter>
implements StudyOutcomeMeasureServiceLocal {

}
