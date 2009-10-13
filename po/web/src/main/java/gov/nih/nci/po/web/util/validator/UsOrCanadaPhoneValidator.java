package gov.nih.nci.po.web.util.validator;

import gov.nih.nci.po.data.bo.PhoneNumber;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.util.ValueStack;
import com.opensymphony.xwork2.validator.ValidationException;
import com.opensymphony.xwork2.validator.validators.FieldValidatorSupport;

/**
 * Used to validate a Organization being nullified.
 * @author mshestop
 */
public class UsOrCanadaPhoneValidator extends FieldValidatorSupport {

    /**
     * {@inheritDoc}
     */
    public void validate(Object object) throws ValidationException {
        ValueStack stack = ActionContext.getContext().getValueStack();
        String fieldName = getFieldName();
        List<PhoneNumber> contacts = (List<PhoneNumber>) getFieldValue(fieldName, object);
        
        stack.push(object);
        boolean isUsOrCanadaFormat = ((Addressable) object).isUsOrCanadaFormat();
        if (isUsOrCanadaFormat) { 
            
            for (PhoneNumber phone : contacts) {
                if (!isUsOrCanadaPhonenumber(phone)) {
                    addActionError(object);
                }
            }
           
        }
        stack.pop();
    }
    
    
    private boolean isUsOrCanadaPhonenumber(PhoneNumber tel) {
        Pattern p = Pattern.compile("^\\d{3}-\\d{3}-\\d{4}(x\\d+)?$");
        Matcher m = p.matcher(tel.getValue());
        return m.matches();
    }
}
