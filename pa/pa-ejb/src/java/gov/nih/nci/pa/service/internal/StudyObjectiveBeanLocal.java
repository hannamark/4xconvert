/**
 * 
 */
package gov.nih.nci.pa.service.internal;

import gov.nih.nci.pa.domain.StudyObjective;
import gov.nih.nci.pa.iso.convert.StudyObjectiveConverter;
import gov.nih.nci.pa.iso.dto.StudyObjectiveDTO;
import gov.nih.nci.pa.service.AbstractStudyIsoService;
import gov.nih.nci.pa.service.StudyObjectiveServiceLocal;
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
@Interceptors(HibernateSessionInterceptor.class)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class StudyObjectiveBeanLocal 
 extends AbstractStudyIsoService<StudyObjectiveDTO, StudyObjective, StudyObjectiveConverter>
 implements StudyObjectiveServiceLocal {

}
