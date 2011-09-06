/**
 *
 */
package gov.nih.nci.pa.service;

import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.domain.StudyRecruitmentStatus;
import gov.nih.nci.pa.iso.convert.StudyRecruitmentStatusConverter;
import gov.nih.nci.pa.iso.dto.StudyRecruitmentStatusDTO;
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
@Interceptors(PaHibernateSessionInterceptor.class)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class StudyRecruitmentStatusBeanLocal extends
    AbstractCurrentStudyIsoService<StudyRecruitmentStatusDTO, StudyRecruitmentStatus, StudyRecruitmentStatusConverter>
    implements StudyRecruitmentStatusServiceLocal {

    private static final String ERR_MSG_METHOD_NOT_IMPLEMENTED = "Method not yet implemented.";

    /**
     * @param dto dto
     * @return null
     * @throws PAException exception
     */
    @Override
    public StudyRecruitmentStatusDTO update(StudyRecruitmentStatusDTO dto) throws PAException {
        throw new PAException(ERR_MSG_METHOD_NOT_IMPLEMENTED);
    }

    /**
     * @param ii index of object
     * @throws PAException exception
     */
    @Override
    public void delete(Ii ii) throws PAException {
        throw new PAException(ERR_MSG_METHOD_NOT_IMPLEMENTED);
    }

}
