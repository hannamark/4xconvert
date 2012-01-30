package gov.nih.nci.commons.ctrp.web.struts2.interceptor;

import gov.nih.nci.commons.ctrp.util.NCICommonsUtils;

import com.fiveamsolutions.nci.commons.web.struts2.interceptor.AbstractInterceptorBase;
import com.opensymphony.xwork2.interceptor.Interceptor;
import com.opensymphony.xwork2.util.ValueStack;

/**
 * Interceptor that strips Strings of malicious characters and replaces them with their html code. The replacement will
 * disable any script entered by users into input fields. Branched from com.fiveamsolutions.nci.commons so that it 
 * can be edited for CTRP.
 * 
 * Note that the filterSymbols and filterScriptTag should not both be set true.
 * 
 * @author Brian Pickeral
 */
public class XSSFilterInterceptor extends AbstractInterceptorBase implements Interceptor {
    private static final long serialVersionUID = 1L;
    // Controls substitution of symbols (< and >). If true, symbols are substituted with &lt; and &gt;.
    private boolean filterSymbols = true;
    // Controls replacement of controls characters.If true, control characters are stripped out.
    private boolean filterControlChars = true;
    // Prevents script injection using url encoded <.
    private boolean filterScriptTag = false;

    /**
     * {@inheritDoc}
     */
    @Override
    public void doIntercept(ValueStack stack, Object key, Object value) {
        if (value instanceof String) {
            String s = (String) value;
            stack.setValue((String) key,
                    NCICommonsUtils.performXSSFilter(s, filterSymbols, filterControlChars, filterScriptTag));
        } else if (value instanceof String[]) {
            String[] values = (String[]) value;
            for (int i = 0; i < values.length; i++) {
                String s = values[i];
                values[i] = NCICommonsUtils.performXSSFilter(s, filterSymbols, filterControlChars, filterScriptTag);
            }
        }
    }

    /**
         * Determines if '<' and '>' symbols should be replaced by '&lt;' and '&gt;'.
         * If true, symbols are replaced.
         * @return the filterSymbols
         */
    public boolean isFilterSymbols() {
        return filterSymbols;
    }

    /**
         * Determines if '<' and '>' symbols should be replaced by '&lt;' and '&gt;'.
         * If set to true, symbols will be replaced.
         * @param filterSymbols the filterSymbols to set
         */
    public void setFilterSymbols(boolean filterSymbols) {
        this.filterSymbols = filterSymbols;
    }

    /**
     * Determines if control characters are stripped out or not. If true, control characters are stripped out.
     * 
     * @return the filterControlChars
     */
    public boolean isFilterControlChars() {
        return filterControlChars;
    }

    /**
     * Determines if control characters are stripped out or not. If set to true, control characters will be stripped
     * out.
     * 
     * @param filterControlChars the filterControlChars to set
     */
    public void setFilterControlChars(boolean filterControlChars) {
        this.filterControlChars = filterControlChars;
    }

    /**
     * Determines if %3c should convert to %20.
     * 
     * @return the filterScriptTag
     */
    public boolean isFilterScriptTag() {
        return filterScriptTag;
    }

    /**
     * Determines if %3c should convert to %20.
     * 
     * @param filterScriptTag the filterScriptTag to set
     */
    public void setFilterScriptTag(boolean filterScriptTag) {
        this.filterScriptTag = filterScriptTag;
    }
}
