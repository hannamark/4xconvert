/**
 * 
 */
package gov.nih.nci.pa.util;

import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;

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
