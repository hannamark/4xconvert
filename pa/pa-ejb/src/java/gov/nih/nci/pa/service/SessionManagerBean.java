package gov.nih.nci.pa.service;

import gov.nih.nci.pa.util.HibernateSessionInterceptor;
import gov.nih.nci.pa.util.HibernateUtil;

import java.net.MalformedURLException;
import java.net.URL;
import java.security.Principal;
import java.security.SecureRandom;
import java.util.Random;
import java.util.StringTokenizer;

import javax.annotation.Resource;
//import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 * Session manager bean.  Initially written for caArray.  Modified for PA.
 * @author Hugh Reinhart
 * @since 05/22/2007
 * copyright NCI 2007.  All rights reserved.
 * This code may not be used without the express written permission of the copyright holder, NCI.
 */

@SuppressWarnings("PMD.CyclomaticComplexity")
@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@Interceptors({ HibernateSessionInterceptor.class })
public class SessionManagerBean implements SessionManagerRemote, SessionManagerLocal {

    private final long sessionGCInterval = DateUtils.MILLIS_PER_MINUTE;
    private final long sessionLifespan = DateUtils.MILLIS_PER_HOUR;

    private SessionContext ejbContext;

    private SessionManagerLocal selfProxy;

    private static Random random = new SecureRandom();

    private static final Logger LOG = Logger.getLogger(SessionManagerBean.class);

    private static long lastGCTime = 0L;

    private static final int RETRIES = 10;

    /**
     * Set the invocation context.
     * @param ctx EJB context
     */
    @Resource
    public void setSessionContext(SessionContext ctx) {
        this.ejbContext = ctx;
    }

    /**
     * Set a container wrapped proxy of this SessionManagerBean.
     * @param proxy managed proxy of self.
     */
    @EJB
    public void setSelfProxy(SessionManagerLocal proxy) {
        this.selfProxy = proxy;
    }

    /**
     * @param r the new random number generator to use
     */
    public static void setRandom(Random r) {
        random = r;
    }

    /**
     * {@inheritDoc}
     */
//    @RolesAllowed({ "client" })
    public long startSession(String username, String returnURL) {
        try {
            Principal callerPrincipal = ejbContext.getCallerPrincipal();
            String caller = null;
            if (callerPrincipal != null) {
                caller = callerPrincipal.getName();
            }
            URL clientReturnURL = new URL(returnURL);
            checkParams(clientReturnURL);
            return createEntry(clientReturnURL.toString(), username, caller);
        } catch (MalformedURLException ex) {
            throw new IllegalArgumentException(ex);
        }
    }

    /**
     * {@inheritDoc}
     */
    public long startLocalSession(String username, String returnURL) {
        return createEntry(returnURL, username, "localuser");
    }

    /**
     * Try to generate a unique random code for this session.
     */
    private long createEntry(String clientReturnURL, String username, String caller) {
        int tries = RETRIES;
        while ((--tries) > 0) {
            long code = random.nextLong();
            try {
                // see if the code is currently in use
                getSession(code);
            } catch (IllegalArgumentException e) {
                // this is the expected behavior - code not in use
                Session s = HibernateUtil.getCurrentSession();
                SessionEntry entry = new SessionEntry(clientReturnURL, username, caller, code);
                s.save(entry);
                return code;
            }
        }

        throw new IllegalStateException("failed to create a session after many tries");
    }

    /**
     * {@inheritDoc}
     */
    public SessionEntry getSession(long code) {
        Session s = HibernateUtil.getCurrentSession();
        Query q = s.createQuery("from " + SessionEntry.class.getName() + " se where se.secret = ?");
        q.setParameter(0, code);
        SessionEntry se = (SessionEntry) q.uniqueResult();
        if (se == null) {
            throw new IllegalArgumentException("session " + code + " not found");
        } else {
            long old = System.currentTimeMillis() - this.sessionLifespan;
            if ((se.getAccessTime() == 0L && se.getCreatedTime() < old)
                    || (se.getAccessTime() > 0 && se.getAccessTime() < old)) {
                // Delete expired sessions from cache immediately
                selfProxy.removeSession(se);
                throw new IllegalArgumentException("session " + code + " no longer available");
            }
        }
        selfProxy.touch(se);
        return se;
    }

    /**
     * {@inheritDoc}
     */
    public void removeSession(SessionEntry entry) {
        Session s = HibernateUtil.getCurrentSession();
        s.delete(entry);
        purgeExpiredSessions();
    }

    /**
     * {@inheritDoc}
     */
    public void touch(SessionEntry entry) {
        Session s = HibernateUtil.getCurrentSession();
        entry.setAccessTime(System.currentTimeMillis());
        s.update(entry);
        purgeExpiredSessions();
    }

   /**
     * purge expired sessions.  only call within a transactions.
     */
    private void purgeExpiredSessions() {
        long now = System.currentTimeMillis();
        synchronized (SessionManagerBean.class) {
            if ((now - lastGCTime) < this.sessionGCInterval) {
                return;
            }
        }
        long old = now - this.sessionLifespan;
        Session s = HibernateUtil.getCurrentSession();
        Query q = s.createQuery("delete from " + SessionEntry.class.getName()
                  // those that haven't been accessed in a while
                + " se where (se.accessTime > 0 and se.accessTime < ?)"
                 // those that were never accessed by the user at all.
                + " or (se.accessTime = 0 and se.createdTime < ?)");
        q.setParameter(0, old);
        q.setParameter(1, old);
        int count = q.executeUpdate();
        LOG.info("scrapped " + count + " old sessions");
        synchronized (SessionManagerBean.class) {
            lastGCTime = now;
        }
    }

    @SuppressWarnings("PMD.CyclomaticComplexity")
    private static void checkParams(URL url) {
        String q = url.getQuery();
        if (q == null) { return; }
        StringTokenizer st = new StringTokenizer(q, "?&=", true);
        boolean checkName = false;
        while (st.hasMoreTokens()) {
            String current = st.nextToken();
            if ("?".equals(current) || "&".equals(current)) {
                checkName = true;
            } else if ("=".equals(current)) {
                checkName = false;
            } else if (checkName) {
                if (PARAM_ID.equals(current) || PARAM_ERROR.equals(current)) {
                    throw new IllegalArgumentException("parameter " + current + "is reserved");
                } else {
                    checkName = false;
                }
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    public long getSessionLifespan() {
        return sessionLifespan;
    }
}
