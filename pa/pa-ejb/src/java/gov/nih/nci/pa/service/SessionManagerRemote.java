package gov.nih.nci.pa.service;

import javax.ejb.Remote;

/**
 * Modified for PA.
 * @author Hugh Reinhart
 * @since 05/22/2007
 * copyright NCI 2007.  All rights reserved.
 * This code may not be used without the express written permission of the copyright holder, NCI.
 */
 
@Remote
public interface SessionManagerRemote {
    
    /**
     * Parameter name used to identify the session created by startSession() when redirecting to PA.
     * <code>
     * ...
     * long paSessionCode = sessionManager.startSession(...);
     * String baseURL = "http://server.org/po/pub/person/create.action?";
     * response.sendRedirect(baseURL + SessionManagerRemote.PARAM_CODE + "=" + poSessionCode);
     * </code>
     */
    String PARAM_CODE = "pa.code";
    
    /**
     * the return URL will be appended with this parameter in case of error.
     */
    String PARAM_ERROR = "pa.error";
    
    /**
     * The return URL will be appended with this parameter when a new record is created.
     * the value is the id
     */
    String PARAM_ID = "pa.id";

    /**
     * request a new session for a user on a client app.
     * @param username the id of the user on the client app
     * @param returnURL the URL where the user will be redirected at the end of 
     * the session.  Parameters in the url must not contain and of the reserved 
     * parameter names.
     * @return the code the client's user should identify itself with.
     */
    long startSession(String username, String returnURL);
}
