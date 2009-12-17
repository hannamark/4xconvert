package gov.nih.nci.coppa.services.outcomes.grid.remote;

import gov.nih.nci.accrual.dto.ActivityRelationshipDto;
import gov.nih.nci.accrual.dto.PerformedActivityDto;
import gov.nih.nci.accrual.dto.PerformedObservationDto;
import gov.nih.nci.accrual.dto.StudySubjectDto;
import gov.nih.nci.accrual.dto.SubmissionDto;
import gov.nih.nci.accrual.dto.util.PatientDto;
import gov.nih.nci.accrual.service.ActivityRelationshipService;
import gov.nih.nci.accrual.service.BaseAccrualService;
import gov.nih.nci.accrual.service.BaseAccrualStudyService;
import gov.nih.nci.accrual.service.PerformedActivityService;
import gov.nih.nci.accrual.service.PerformedObservationResultService;
import gov.nih.nci.accrual.service.StudySubjectService;
import gov.nih.nci.accrual.service.SubmissionService;
import gov.nih.nci.accrual.service.UserService;
import gov.nih.nci.accrual.service.util.PatientService;
import gov.nih.nci.pa.iso.dto.BaseDTO;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.naming.CommunicationException;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * Service locator that uses JNDI to look up services.
 */
public final class JNDIServiceLocator implements ServiceLocator {
    private static final Logger LOG = LogManager.getLogger(JNDIServiceLocator.class);
    private static final int MAX_RETRIES = 2;
    private static JNDIServiceLocator instance = new JNDIServiceLocator();
    private InitialContext context;
    private static Map<Class<?>, Method> values = new HashMap<Class<?>, Method>();

    static {
        try {
            /*
             * Cache the Method instead of the actual Remote instance as it would be very difficult to handle
             * NamingException, etc..
             */
            values.put(ActivityRelationshipDto.class, getInstance().getClass().getMethod(
                    "getActivityRelationshipService"));
            values.put(PerformedActivityDto.class, getInstance().getClass().getMethod("getPerformedActivityService"));
            values.put(PerformedObservationDto.class, getInstance().getClass().getMethod(
                    "getPerformedObservationResultService"));
            values.put(StudySubjectDto.class, getInstance().getClass().getMethod("getStudySubjectService"));
            values.put(SubmissionDto.class, getInstance().getClass().getMethod("getSubmissionService"));
            values.put(PatientDto.class, getInstance().getClass().getMethod("getPatientService"));
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    private JNDIServiceLocator() {
        try {
            Properties props = new Properties();
            props.load(JNDIServiceLocator.class.getClassLoader().getResourceAsStream("jndi.properties"));
            context = new InitialContext(props);
        } catch (Exception e) {
            LOG.error("Unable to load jndi properties.", e);
            throw new RuntimeException("Unable to load jndi properties.", e);
        }
    }

    /**
     * Get the singleton instance of the service locator.
     *
     * @return the singleton locator
     */
    public static JNDIServiceLocator getInstance() {
        return instance;
    }

    private Object lookup(String name) throws NamingException {
        Object object = null;
        int i = 0;
        while (object == null && i < MAX_RETRIES) {
            try {
                object = context.lookup(name);
            } catch (CommunicationException com) {
                instance = new JNDIServiceLocator();
            }
            i++;
        }

        return object;
    }

    /**
     * {@inheritDoc}
     */
    public UserService getUserService() throws NamingException {
        return (UserService) lookup("accrual/UserBean/remote");
    }


    /**
     * {@inheritDoc}
     */
    public ActivityRelationshipService getActivityRelationshipService() throws NamingException {
        throw new IllegalStateException("This service must be called securely");
    }

    /**
     * {@inheritDoc}
     */
    public PerformedActivityService getPerformedActivityService() throws NamingException {
        throw new IllegalStateException("This service must be called securely");
    }

    /**
     * {@inheritDoc}
     */
    public PerformedObservationResultService getPerformedObservationResultService() throws NamingException {
        throw new IllegalStateException("This service must be called securely");
    }

    /**
     * {@inheritDoc}
     */
    public StudySubjectService getStudySubjectService() throws NamingException {
        throw new IllegalStateException("This service must be called securely");
    }

    /**
     * {@inheritDoc}
     */
    public SubmissionService getSubmissionService() throws NamingException {
        throw new IllegalStateException("This service must be called securely");
    }

    /**
     * {@inheritDoc}
     */
    public PatientService getPatientService() throws NamingException {
        throw new IllegalStateException("This service must be called securely");
    }

    /**
     * {@inheritDoc}
     */
    public <B extends BaseDTO> BaseAccrualService<B> getBaseAccrualService(Class<B> type) throws NamingException {
        throw new IllegalStateException("This service must be called securely");
    }

    /**
     * {@inheritDoc}
     */
    public <B extends BaseDTO> BaseAccrualStudyService<B> getBaseAccrualStudyService(Class<B> type)
            throws NamingException {
        throw new IllegalStateException("This service must be called securely");
    }
}
