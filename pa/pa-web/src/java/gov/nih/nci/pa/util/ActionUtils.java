/**
 * 
 */
package gov.nih.nci.pa.util;

import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.util.CSMUserService;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;

/**
 * Various utility methods shared by action classes.
 * @author Denis G. Krylov
 *
 */
public final class ActionUtils {
    
    
    /**
     * Checks whether one of user's roles is in session.
     * @param session HttpSession
     * @return boolean
     */
    public static boolean isUserRoleInSession(HttpSession session) {
        boolean isSuAbstractor = BooleanUtils.toBoolean((Boolean) session
                .getAttribute(Constants.IS_SU_ABSTRACTOR));
        boolean isAbstractor = BooleanUtils.toBoolean((Boolean) session
                .getAttribute(Constants.IS_ABSTRACTOR));
        boolean isAdminAbstractor = BooleanUtils.toBoolean((Boolean) session
                .getAttribute(Constants.IS_ADMIN_ABSTRACTOR));
        boolean isScientificAbstractor = BooleanUtils
                .toBoolean((Boolean) session
                        .getAttribute(Constants.IS_SCIENTIFIC_ABSTRACTOR));
        boolean isReportViewer = BooleanUtils.toBoolean((Boolean) session
                .getAttribute(Constants.IS_REPORT_VIEWER));
        return isAbstractor || isSuAbstractor || isScientificAbstractor
                || isAdminAbstractor || isReportViewer;
    }
    
    /**
     * This method returns true if the given user belongs to one of the below provided groups.
     * Abstractor,SuAbstractor,AdminAbstractor,ScientificAbstarctor. This also means that the given user
     * has read write privilege to PA application 
     * @param loginName user
     * @return true or false
     * @throws PAException exception
     */
    public static boolean checkUserHasReadWritePrivilege(String loginName) throws PAException {
        
        List<String> userGroups = CSMUserService.getInstance().getUserGroups(loginName);
        return (userGroups.contains(Constants.ABSTRACTOR)
                || userGroups.contains(Constants.SUABSTRACTOR)
                || userGroups.contains(Constants.ADMIN_ABSTRACTOR) 
                || userGroups.contains(Constants.SCIENTIFIC_ABSTRACTOR)); 
    }   
    
    /**
     * Self-descriptive. The method will sort the passed-in {@link List} (without creating a new one) and will return
     * the same for convenience of invocation chaining. 
     * 
     * @param records
     *            records
     * @return List<StudyProtocolQueryDTO>
     */
    public static List<StudyProtocolQueryDTO> sortTrialsByNciId(List<StudyProtocolQueryDTO> records) {
        if (CollectionUtils.isNotEmpty(records)) {
            Collections.sort(records, new Comparator<StudyProtocolQueryDTO>() {
                @Override
                public int compare(StudyProtocolQueryDTO o1,
                        StudyProtocolQueryDTO o2) {
                    return StringUtils.defaultString(o2.getNciIdentifier())
                            .compareTo(
                                    StringUtils.defaultString(o1
                                            .getNciIdentifier()));
                }
            });
        }
        return records;
    }
    

}
