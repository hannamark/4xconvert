package gov.nih.nci.commons.ctrp.web.struts2.interceptor;

import java.util.HashMap;
import java.util.Map;

import com.fiveamsolutions.nci.commons.web.struts2.interceptor.AbstractInterceptorBase;
import com.opensymphony.xwork2.util.ValueStack;

public class AbstractInterceptorBaseTestImpl extends AbstractInterceptorBase {

    private static final long serialVersionUID = 1L;
    private final Map<String, Object> processedParameters = new HashMap<String, Object>();

    @Override
    public void doIntercept(ValueStack stack, Object key, Object value) {
        processedParameters.put((String) key, value);
    }

    protected Map<String, Object> getProcessedParameters() {
        return processedParameters;
    }
}
