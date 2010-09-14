/**
 *
 */
package gov.nih.nci.pa.interceptor;

import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.domain.StudyProtocol;
import gov.nih.nci.pa.iso.dto.StudyDTO;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.HibernateUtil;
import gov.nih.nci.pa.util.PAUtil;

import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

import org.apache.commons.lang.BooleanUtils;
import org.hibernate.Session;

/**
 * @author Vrushali
 *
 */
public class ProprietaryTrialInterceptor {
    /**
     *
     * @param ctx ctx
     * @return ob
     * @throws Exception e
     */
    @AroundInvoke
    public Object checkIsProprietaryTrial(InvocationContext ctx) throws Exception {
        Object[] objs = ctx.getParameters();
        Ii ii = null;
        if (objs != null) {
            Object obj = objs[0];
            if (obj instanceof Ii) {
                ii = (Ii) obj;
            }
            if (obj instanceof StudyDTO) {
                ii = ((StudyDTO) obj).getStudyProtocolIdentifier();
            }
        }
        if (PAUtil.isIiNotNull(ii)) {
            Session session = HibernateUtil.getCurrentSession();
            StudyProtocol studyProtocol = (StudyProtocol) session.get(StudyProtocol.class,
                                                                      Long.valueOf(ii.getExtension()));
            if (studyProtocol != null && BooleanUtils.isTrue(studyProtocol.getProprietaryTrialIndicator())) {
                throw new PAException(ctx.getMethod().getName() + " for Proprietary trial is not allowed");
            }
        }
        return ctx.proceed();
    }
}
