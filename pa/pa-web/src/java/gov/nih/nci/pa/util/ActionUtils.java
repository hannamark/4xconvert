/**
 * 
 */
package gov.nih.nci.pa.util;

import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;

import com.fiveamsolutions.nci.commons.util.UsernameHolder;

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
        boolean isReportViewer = BooleanUtils.toBoolean((Boolean) session
                .getAttribute(Constants.IS_REPORT_VIEWER));
        return isAbstractor(session) || isReportViewer;
    }   
    
    /**
     * Checks whether one of user's roles is in session.
     * 
     * @param session
     *            HttpSession
     * @return boolean
     */
    public static boolean isAbstractor(HttpSession session) {
        boolean isSuAbstractor = BooleanUtils.toBoolean((Boolean) session
                .getAttribute(Constants.IS_SU_ABSTRACTOR));
        boolean isAbstractor = BooleanUtils.toBoolean((Boolean) session
                .getAttribute(Constants.IS_ABSTRACTOR));
        boolean isAdminAbstractor = BooleanUtils.toBoolean((Boolean) session
                .getAttribute(Constants.IS_ADMIN_ABSTRACTOR));
        boolean isScientificAbstractor = BooleanUtils
                .toBoolean((Boolean) session
                        .getAttribute(Constants.IS_SCIENTIFIC_ABSTRACTOR));
        return isAbstractor || isSuAbstractor || isScientificAbstractor
                || isAdminAbstractor;
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

    /**
     * @param request HttpServletRequest
     */
    public static void setUserRolesInSession(final HttpServletRequest request) {
        boolean isAbstractor = request.isUserInRole(Constants.ABSTRACTOR);
        boolean isSuAbstractor = request.isUserInRole(Constants.SUABSTRACTOR);
        boolean isScientificAbstractor =
            request.isUserInRole(Constants.SCIENTIFIC_ABSTRACTOR);
        boolean isAdminAbstractor = request.isUserInRole(Constants.ADMIN_ABSTRACTOR);
        boolean isReportViewer = request.isUserInRole(Constants.REPORT_VIEWER);
        request.getSession().setAttribute(Constants.IS_ABSTRACTOR, isAbstractor);
        request.getSession().setAttribute(Constants.IS_SU_ABSTRACTOR, isSuAbstractor);
        request.getSession().setAttribute(Constants.IS_ADMIN_ABSTRACTOR, isAdminAbstractor);
        request.getSession().setAttribute(Constants.IS_SCIENTIFIC_ABSTRACTOR,
                isScientificAbstractor);
        request.getSession().setAttribute(Constants.IS_REPORT_VIEWER, isReportViewer);
    }
    
    
    /**
     * @param spqDTO spqDTO
     * @param checkoutCommands checkoutCommands
     */
    public static void setCheckoutCommands(StudyProtocolQueryDTO spqDTO,
            List<String> checkoutCommands) {
        HttpSession session = ServletActionContext.getRequest().getSession();
        boolean suAbs = BooleanUtils.toBoolean((Boolean) session
                .getAttribute(Constants.IS_SU_ABSTRACTOR));
        setAdminCheckoutCommands(spqDTO, suAbs, checkoutCommands);
        setScientificCheckoutCommands(spqDTO, suAbs, checkoutCommands);
        setSuperAbstractorCommands(spqDTO, suAbs, checkoutCommands);
    }

    /**
     * Super abstractors have a button that does both check-outs at the same
     * time.
     * 
     * @param spqDTO
     * @param suAbs
     * @param checkoutCommands
     * @see https://tracker.nci.nih.gov/browse/PO-3966
     */
    private static void setSuperAbstractorCommands(
            StudyProtocolQueryDTO spqDTO, boolean suAbs,
            List<String> checkoutCommands) {
        // for both uses by this super abstractor.
        if (spqDTO.getAdminCheckout().getCheckoutBy() != null && suAbs) {
            checkoutCommands.add("adminAndScientificCheckIn");
        } else if (suAbs) {
            checkoutCommands.add("adminAndScientificCheckOut");
        }
    }

    private static void setAdminCheckoutCommands(StudyProtocolQueryDTO spqDTO,
            boolean suAbs, List<String> checkoutCommands) {
        if (spqDTO.getAdminCheckout().getCheckoutBy() != null) {
            if (spqDTO.getAdminCheckout().getCheckoutBy()
                    .equalsIgnoreCase(UsernameHolder.getUser())
                    || suAbs) {
                checkoutCommands.add("adminCheckIn");
            }
        } else {
            HttpSession session = ServletActionContext.getRequest()
                    .getSession();
            boolean adminAbs = BooleanUtils.toBoolean((Boolean) session
                    .getAttribute(Constants.IS_ADMIN_ABSTRACTOR));
            if (adminAbs || suAbs) {
                checkoutCommands.add("adminCheckOut");
            }
        }
    }

    private static void setScientificCheckoutCommands(
            StudyProtocolQueryDTO spqDTO, boolean suAbs,
            List<String> checkoutCommands) {
        if (spqDTO.getScientificCheckout().getCheckoutBy() != null) {
            if (spqDTO.getScientificCheckout().getCheckoutBy()
                    .equalsIgnoreCase(UsernameHolder.getUser())
                    || suAbs) {
                checkoutCommands.add("scientificCheckIn");
            }
        } else {
            HttpSession session = ServletActionContext.getRequest()
                    .getSession();
            boolean scAbs = BooleanUtils.toBoolean((Boolean) session
                    .getAttribute(Constants.IS_SCIENTIFIC_ABSTRACTOR));
            if (scAbs || suAbs) {
                checkoutCommands.add("scientificCheckOut");
            }
        }
    }
    

}
