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

import org.apache.log4j.Logger;
import org.hibernate.Session;

/**
 * @author Vrushali
 *
 */
public class ProprietaryTrialInterceptor {
    private static final Logger LOG  = Logger.getLogger(ProprietaryTrialInterceptor.class);
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
            Session session = null;
            StudyProtocol studyProtocol = null;
            session = HibernateUtil.getCurrentSession();
            studyProtocol = (StudyProtocol)
            session.get(StudyProtocol.class, Long.valueOf(ii.getExtension()));
            if (studyProtocol != null && studyProtocol.getProprietaryTrialIndicator() != null
                    && studyProtocol.getProprietaryTrialIndicator()) {
                LOG.info(ctx.getMethod().getName() + "for Proprietary trial is not allowed");
                throw new PAException(ctx.getMethod().getName() + " for Proprietary trial is not allowed");
            }
        }
        return ctx.proceed();
    }
}
