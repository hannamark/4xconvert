/**
 *
 */
package gov.nih.nci.pa.service;

import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.domain.StudyOverallStatus;
import gov.nih.nci.pa.domain.StudyRecruitmentStatus;
import gov.nih.nci.pa.enums.StudyRecruitmentStatusCode;
import gov.nih.nci.pa.iso.convert.StudyRecruitmentStatusConverter;
import gov.nih.nci.pa.iso.dto.StudyRecruitmentStatusDTO;
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
public class StudyRecruitmentStatusBeanLocal extends
    AbstractCurrentStudyIsoService<StudyRecruitmentStatusDTO, StudyRecruitmentStatus, StudyRecruitmentStatusConverter>
    implements StudyRecruitmentStatusServiceLocal {

    private static final String ERR_MSG_METHOD_NOT_IMPLEMENTED = "Method not yet implemented.";

    /**
     * Protected static create method for auto-generating a recruitment status domain object to be used in other
     * services.
     * @param bo the StudyOverallStatus domain object.
     * @return the recruitment status domain object.
     */
    public static StudyRecruitmentStatus create(StudyOverallStatus bo) {
        // automatically update StudyRecruitmentStatus for applicable overall status code's
        if ((bo != null) && (StudyRecruitmentStatusCode.getByStudyStatusCode(bo.getStatusCode()) != null)) {
            StudyRecruitmentStatus srsBo = new StudyRecruitmentStatus();
            srsBo.setStatusCode(StudyRecruitmentStatusCode.getByStudyStatusCode(bo.getStatusCode()));
            srsBo.setStatusDate(bo.getStatusDate());
            srsBo.setStudyProtocol(bo.getStudyProtocol());
            return srsBo;
        }
        return null;
    }

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
