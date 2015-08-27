/**
 * 
 */
package gov.nih.nci.pa.service;

import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.util.CsmUserUtil;
import gov.nih.nci.pa.util.ISOUtil;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

/**
 * Removed grid identity string from {@link StudyProtocolDTO}.
 * 
 * @author dkrylov
 * 
 */
@Interceptor
public class DeGridificationInterceptor {

    /**
     * @param invContext
     *            InvocationContext
     * @return Object
     * @throws Exception
     *             Exception
     */
    @AroundInvoke
    @SuppressWarnings("PMD.SignatureDeclareThrowsException")
    public Object deGridify(InvocationContext invContext) throws Exception {
        for (Object obj : invContext.getParameters()) {
            if (obj instanceof StudyProtocolDTO) {
                StudyProtocolDTO sp = (StudyProtocolDTO) obj;
                if (!ISOUtil.isStNull(sp.getUserLastCreated())) {
                    sp.setUserLastCreated(StConverter.convertToSt(CsmUserUtil
                            .getGridIdentityUsername(
                                    StConverter.convertToString(sp
                                            .getUserLastCreated()))
                            .toLowerCase()));
                }
            }
        }
        return invContext.proceed();

    }

}
