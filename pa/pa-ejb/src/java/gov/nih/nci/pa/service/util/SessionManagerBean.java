/*
* caBIG Open Source Software License
* 
* Copyright Notice.  Copyright 2008, ScenPro, Inc,  (caBIG Participant).   The Protocol  Abstraction (PA) Application
* was created with NCI funding and is part of  the caBIG initiative. The  software subject to  this notice  and license
* includes both  human readable source code form and machine readable, binary, object code form (the caBIG Software).
* 
* This caBIG Software License (the License) is between caBIG  Participant  and  You.  You (or Your) shall  mean a
* person or an entity, and all other entities that control, are  controlled by,  or  are under common  control  with the
* entity.  Control for purposes of this definition means
* 
* (i) the direct or indirect power to cause the direction or management of such entity,whether by contract 
* or otherwise,or
*  
* (ii) ownership of fifty percent (50%) or more of the outstanding shares, or 
* 
* (iii) beneficial ownership of such entity.
* License.  Provided that You agree to the conditions described below, caBIG Participant  grants  You a  non-exclusive,
* worldwide, perpetual, fully-paid-up, no-charge, irrevocable,  transferable  and royalty-free  right and license in its
* rights in the caBIG Software, including any copyright or patent rights therein, to 
* 
* (i) use,install, disclose, access, operate,  execute, reproduce,  copy, modify, translate,  market,  publicly display,
* publicly perform, and prepare derivative works of the caBIG Software in any manner and for any  purpose,  and to have
* or permit others to do so; 
* 
* (ii) make, have made, use, practice, sell, and offer  for sale,  import, and/or  otherwise  dispose of caBIG Software
* (or portions thereof); 
* 
* (iii) distribute and have distributed  to  and by third   parties the   caBIG  Software  and any   modifications  and
* derivative works thereof; and (iv) sublicense the  foregoing rights  set  out in (i), (ii) and (iii) to third parties,
* including the right to license such rights to further third parties. For sake of clarity,and not by way of limitation,
* caBIG Participant shall have no right of accounting or right of payment from You or Your sub licensees for the rights
* granted under this License.   This  License  is  granted  at no  charge  to You. Your downloading, copying, modifying,
* displaying, distributing or use of caBIG Software constitutes acceptance  of  all of the terms and conditions of this
* Agreement.  If You do not agree to such terms and conditions,  You have no right to download,  copy,  modify, display,
* distribute or use the caBIG Software.
* 
* 1.  Your redistributions of the source code for the caBIG Software must retain the above copyright notice, this  list
* of conditions and the disclaimer and limitation of liability of Article 6 below.   Your redistributions in object code
* form must reproduce the above copyright notice,  this list of  conditions  and the  disclaimer  of  Article  6  in the
* documentation and/or other materials provided with the distribution, if any.
* 
* 2.  Your end-user documentation included with the redistribution, if any,  must include the  following acknowledgment:
* This product includes software developed by ScenPro, Inc.   If  You  do not include such end-user documentation, You
* shall include this acknowledgment in the caBIG Software itself, wherever such third-party acknowledgments normally 
* appear.
* 
* 3.  You may not use the names ScenPro, Inc., The National Cancer Institute, NCI, Cancer Bioinformatics Grid or
* caBIG to endorse or promote products derived from this caBIG Software.  This License does not authorize You to use
* any trademarks, service marks, trade names, logos or product names of either caBIG Participant, NCI or caBIG, except
* as required to comply with the terms of this License.
* 
* 4.  For sake of clarity, and not by way of limitation, You  may incorporate this caBIG Software into Your proprietary
* programs and into any third party proprietary programs.  However, if You incorporate the  caBIG Software  into  third
* party proprietary programs,  You agree  that You are  solely responsible  for obtaining any permission from such third
* parties required to incorporate the caBIG Software  into such third party proprietary programs and for informing Your
* sub licensees, including without limitation Your end-users, of their obligation  to  secure  any  required permissions
* from such third parties before incorporating the caBIG Software into such third party proprietary  software programs.
* In the event that You fail to obtain such permissions,  You  agree  to  indemnify  caBIG  Participant  for any claims
* against caBIG Participant by such third parties, except to the extent prohibited by law,  resulting from Your failure
* to obtain such permissions.
* 
* 5.  For sake of clarity, and not by way of limitation, You may add Your own copyright statement  to Your modifications
* and to the derivative works, and You may provide  additional  or  different  license  terms  and  conditions  in  Your
* sublicenses of modifications of the caBIG  Software,  or  any  derivative  works  of  the caBIG Software as a whole,
* provided Your use, reproduction,  and  distribution  of the Work otherwise complies with the conditions stated in this
* License.
* 
* 6.  THIS caBIG SOFTWARE IS PROVIDED "AS IS" AND ANY EXPRESSED OR IMPLIED WARRANTIES  ( INCLUDING, BUT NOT LIMITED TO,
* THE IMPLIED WARRANTIES OF MERCHANTABILITY, NON-INFRINGEMENT AND FITNESS FOR A PARTICULAR PURPOSE) ARE DISCLAIMED.  IN 
* NO EVENT SHALL THE ScenPro, Inc. OR ITS AFFILIATES BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY,
* OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT  LIMITED  TO,  PROCUREMENT OF SUBSTITUTE GOODS  OR SERVICES; LOSS OF USE,
* DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
* LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS caBIG SOFTWARE, EVEN
* IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
* 
* 
*/
package gov.nih.nci.pa.service.util;

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
public class SessionManagerBean implements SessionManagerRemote {

    private final long sessionGCInterval = DateUtils.MILLIS_PER_MINUTE;
    private final long sessionLifespan = DateUtils.MILLIS_PER_HOUR;

    private SessionContext ejbContext;

    private SessionManagerRemote selfProxy;

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
    public void setSelfProxy(SessionManagerRemote proxy) {
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
