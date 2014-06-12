package gov.nih.nci.po.webservices.convert.bridg;

import gov.nih.nci.coppa.po.faults.BaseFaultType;
import gov.nih.nci.coppa.po.faults.EntityValidationFault;
import gov.nih.nci.coppa.po.faults.StringMapType;
import gov.nih.nci.po.service.EntityValidationException;
import org.apache.commons.lang.Validate;
import org.apache.commons.lang.exception.ExceptionUtils;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

/**
 * Ported from COPPA-COMMONS.
 */
@SuppressWarnings("PMD")
public final class FaultConverter {



    private final BaseFaultType fault;


    /**
     * This was ported.
     *
     * @param fault The fault to build.
     */
    public FaultConverter(BaseFaultType fault) {
        this(fault, true);
    }

    /**
     * This was ported.
     *
     * @param fault The fault to build.
     * @param convertStackTrace Whether to convert the stacktrace
     */
    public FaultConverter(BaseFaultType fault, boolean convertStackTrace) {
        Validate.notNull(fault);

        this.fault = fault;
        // add timestamp automatically if not set
        if (this.fault.getTimestamp() == null) {
            GregorianCalendar calendar = (GregorianCalendar) GregorianCalendar.getInstance();
            try {
                this.fault.setTimestamp(DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar));
            } catch (DatatypeConfigurationException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Sets the description of the fault.
     *
     * @param description the new description of the fault.
     */
    public void setDescription(String description) {
        setDescription((description == null) ? null : new String[] {description});
    }

    /**
     * Sets the description of the fault.
     *
     * @param description the new descriptions of the fault.
     */
    public void setDescription(String[] description) {
        List<BaseFaultType.Description> desc = null;
        if (description != null) {
            desc = new ArrayList<BaseFaultType.Description>();

            for (int i = 0; i < description.length; i++) {
                this.fault.getDescription()
                        .add(
                                new BaseFaultType.Description()
                                        .withLang(null)
                                        .withValue(description[i])
                        );
            }
        }

    }

    private void addFaultCause(BaseFaultType faultCause) {
        this.fault.getFaultCause().add(faultCause);
    }


    private void addStackTraceFault(Throwable exception) {
        String message = exception.getClass().getName();
        String stackTrace = ExceptionUtils.getStackTrace(exception);

        // add stack trace fault
        addFaultCause(createStackFault(message, stackTrace));
    }

    private static BaseFaultType createStackFault(String message, String stackTrace) {
        BaseFaultType stackFault = new BaseFaultType();
        BaseFaultType.ErrorCode errorCode = new  BaseFaultType.ErrorCode();
        errorCode.setDialect("stacktrace");
        stackFault.setErrorCode(errorCode);

        if (message != null && message.length() > 0) {
            stackFault.getDescription().add(
                    new BaseFaultType.Description()
                        .withLang(null)
                        .withValue(message)
            );
        }

        GregorianCalendar calendar = (GregorianCalendar) GregorianCalendar.getInstance();
        try {
            stackFault.setTimestamp(DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar));
        } catch (DatatypeConfigurationException e) {
            throw new RuntimeException(e);
        }

        return stackFault;
    }

    /**
     * @param <E> type of fault
     * @param fault the actual fault, non-null value
     * @param exception the actual exception to convert to a type <E>
     * @return a populated <E> fault setting all BaseFaultType values
     */
    public static <E extends BaseFaultType> E toFault(E fault, Throwable exception) {
        FaultConverter helper = new FaultConverter(fault, false);
        helper.setDescription(exception.getMessage());
        helper.addStackTraceFault(exception);

        if (exception instanceof EntityValidationException) {
            Map<String, String[]> errors = ((EntityValidationException) exception).getErrors();
            ((EntityValidationFault) fault).setErrors(new StringMapType());
            for (Map.Entry error : errors.entrySet()) {
                ((EntityValidationFault) fault).getErrors().withEntry(
                        new StringMapType.Entry()
                                .withKey((String) error.getKey())
                                .withValue((String[]) error.getValue())
                );
            }

        }
        return fault;
    }

}