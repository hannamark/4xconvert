package gov.nih.nci.pa.service.util;


import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.Index;

/**
 * Maintain session data.  Originally created by Gax.  Modified for PA.
 * @author Hugh Reinhart
 * @since 05/22/2007
 * copyright NCI 2007.  All rights reserved.
 * This code may not be used without the express written permission of the copyright holder, NCI.
 */

@Entity
public class SessionEntry  implements Serializable {
    private static final long serialVersionUID = 0L;
    private Long id;
    private String returnURL;
    private String remoteUser;
    private String refererApp;
    private long createdTime;
    private long accessTime;
    private long secret;

    private static final int COL_LEN_DEFAULT = 256;
    private static final int COL_LEN_LONG = 2048;

    SessionEntry() {
        //  Empty constructor.
    }
    
    SessionEntry(String returnURL, String user, String refererApp, long secret) {
        this.returnURL = returnURL;
        this.remoteUser = user;
        this.refererApp = refererApp;
        this.secret = secret;
        this.createdTime = System.currentTimeMillis();
        this.accessTime = 0L;
    }

    /**
     * @return database id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    /**
     * @param id database id
     */
    @SuppressWarnings("unused")
    protected void setId(Long id) {
        this.id = id;
    }

    /**
     * Get the last access time.
     * @see SessionManagerLocal.touch()
     * @return the time when this session was last accessed.
     */
    @Column(nullable = false)
    public long getAccessTime() {
        return accessTime;
    }

    /**
     * Set the time at which this session was last accessed.
     * @param accessTime the time when this session was last accessed.
     */
    void setAccessTime(long accessTime) {
        this.accessTime = accessTime;
    }

    /**
     * Get the time at wich this session was greated.
     * @see SessionManagerRemote.startSession()
     * @return creation time
     */
    @Column(nullable = false)
    public long getCreatedTime() {
        return createdTime;
    }

    /**
     * @param when this session was created.
     */
    void setCreatedTime(long createdTime) {
        this.createdTime = createdTime;
    }

    /**
     * Get the login name of the app that initiated the session.
     * @return the name.
     */
    @Column(length = COL_LEN_DEFAULT, nullable = false)
    public String getRefererApp() {
        return refererApp;
    }

    /**
     * Set the name of the referer client app.
     * @param refererApp the name of the app
     */
    void setRefererApp(String refererApp) {
        this.refererApp = refererApp;
    }

    /**
     * Get the url to where the user should be redirected to when the session ends.
     * @return the URL to redirect to without PO specific parameters.
     */
    @Column(length = COL_LEN_LONG, nullable = false)
    public String getReturnURL() {
        return returnURL;
    }

    /**
     * Set the URL to which the user is redirected to.
     * @param returnURL the URL.
     */
    public void setReturnURL(String returnURL) {
        this.returnURL = returnURL;
    }

    /**
     * The random nonce identifier of the session.
     * @return the random secret code / nonce
     */
    @Column(unique = true, nullable = false)
    @Index(name = "SecretIdx")
    public long getSecret() {
        return secret;
    }

    /**
     * Set the nonce.
     * @param secret the nonce.
     */
    public void setSecret(long secret) {
        this.secret = secret;
    }

    /**
     * Get the session's username as identified by the client app.
     * @return user
     */
    @Column(length = COL_LEN_DEFAULT, nullable = false)
    public String getRemoteUser() {
        return remoteUser;
    }

    /**
     * @param user the user's principal mane on the client app
     */
    public void setRemoteUser(String user) {
        this.remoteUser = user;
    }


}
