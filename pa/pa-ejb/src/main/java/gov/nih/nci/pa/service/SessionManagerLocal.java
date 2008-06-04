package gov.nih.nci.pa.service;

import javax.ejb.Local;

/**
 * Local session manager interface.  Provides get, touch, and remove methods.
 * Also provides testing mechanisms so that clients can verify functionality
 * without running the entire protocol.  Initially written for caArray.
 * Modified for PA.
 * @author Hugh Reinhart
 * @since 05/22/2007
 * copyright NCI 2007.  All rights reserved.
 * This code may not be used without the express written permission of the copyright holder, NCI.
 */

@Local
public interface SessionManagerLocal {
    /**
     * Get a previously created session by it's secret code.
     * @param code the code that the client is identifying the session with.
     * @return the session with the matching code.
     */
    SessionEntry getSession(long code);

    /**
     * remove (invalidate) a session.
     * @param entry the session to remove
     */
    void removeSession(SessionEntry entry);

    /**
     * update last access timestamp.
     * @param entry the session to who's accessTime will be updated
     */
    void touch(SessionEntry entry);

    /**
     * @return how long inactive sessions should live.
     */
    long getSessionLifespan();

    /**
     * request a new session for a user on a client app.
     * @param username the id of the user on the client app
     * @param returnURL the URL where the user will be redirected at the end of
     * the session.  Parameters in the url must not contain and of the reserved
     * parameter names.
     * @return the code the client's user should identify itself with.
     */
    long startLocalSession(String username, String returnURL);
}