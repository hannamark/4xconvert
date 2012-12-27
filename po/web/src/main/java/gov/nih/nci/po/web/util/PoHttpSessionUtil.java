/*
 * The software subject to this notice and license includes both human readable
 * source code form and machine readable, binary, object code form. The po
 * Software was developed in conjunction with the National Cancer Institute
 * (NCI) by NCI employees and 5AM Solutions, Inc. (5AM). To the extent
 * government employees are authors, any rights in such works shall be subject
 * to Title 17 of the United States Code, section 105.
 *
 * This po Software License (the License) is between NCI and You. You (or
 * Your) shall mean a person or an entity, and all other entities that control,
 * are controlled by, or are under common control with the entity. Control for
 * purposes of this definition means (i) the direct or indirect power to cause
 * the direction or management of such entity, whether by contract or otherwise,
 * or (ii) ownership of fifty percent (50%) or more of the outstanding shares,
 * or (iii) beneficial ownership of such entity.
 *
 * This License is granted provided that You agree to the conditions described
 * below. NCI grants You a non-exclusive, worldwide, perpetual, fully-paid-up,
 * no-charge, irrevocable, transferable and royalty-free right and license in
 * its rights in the po Software to (i) use, install, access, operate,
 * execute, copy, modify, translate, market, publicly display, publicly perform,
 * and prepare derivative works of the po Software; (ii) distribute and
 * have distributed to and by third parties the po Software and any
 * modifications and derivative works thereof; and (iii) sublicense the
 * foregoing rights set out in (i) and (ii) to third parties, including the
 * right to license such rights to further third parties. For sake of clarity,
 * and not by way of limitation, NCI shall have no right of accounting or right
 * of payment from You or Your sub-licensees for the rights granted under this
 * License. This License is granted at no charge to You.
 *
 * Your redistributions of the source code for the Software must retain the
 * above copyright notice, this list of conditions and the disclaimer and
 * limitation of liability of Article 6, below. Your redistributions in object
 * code form must reproduce the above copyright notice, this list of conditions
 * and the disclaimer of Article 6 in the documentation and/or other materials
 * provided with the distribution, if any.
 *
 * Your end-user documentation included with the redistribution, if any, must
 * include the following acknowledgment: This product includes software
 * developed by 5AM and the National Cancer Institute. If You do not include
 * such end-user documentation, You shall include this acknowledgment in the
 * Software itself, wherever such third-party acknowledgments normally appear.
 *
 * You may not use the names "The National Cancer Institute", "NCI", or "5AM"
 * to endorse or promote products derived from this Software. This License does
 * not authorize You to use any trademarks, service marks, trade names, logos or
 * product names of either NCI or 5AM, except as required to comply with the
 * terms of this License.
 *
 * For sake of clarity, and not by way of limitation, You may incorporate this
 * Software into Your proprietary programs and into any third party proprietary
 * programs. However, if You incorporate the Software into third party
 * proprietary programs, You agree that You are solely responsible for obtaining
 * any permission from such third parties required to incorporate the Software
 * into such third party proprietary programs and for informing Your
 * sub-licensees, including without limitation Your end-users, of their
 * obligation to secure any required permissions from such third parties before
 * incorporating the Software into such third party proprietary software
 * programs. In the event that You fail to obtain such permissions, You agree
 * to indemnify NCI for any claims against NCI by such third parties, except to
 * the extent prohibited by law, resulting from Your failure to obtain such
 * permissions.
 *
 * For sake of clarity, and not by way of limitation, You may add Your own
 * copyright statement to Your modifications and to the derivative works, and
 * You may provide additional or different license terms and conditions in Your
 * sublicenses of modifications of the Software, or any derivative works of the
 * Software as a whole, provided Your use, reproduction, and distribution of the
 * Work otherwise complies with the conditions stated in this License.
 *
 * THIS SOFTWARE IS PROVIDED "AS IS," AND ANY EXPRESSED OR IMPLIED WARRANTIES,
 * (INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY,
 * NON-INFRINGEMENT AND FITNESS FOR A PARTICULAR PURPOSE) ARE DISCLAIMED. IN NO
 * EVENT SHALL THE NATIONAL CANCER INSTITUTE, 5AM SOLUTIONS, INC. OR THEIR
 * AFFILIATES BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS;
 * OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR
 * OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package gov.nih.nci.po.web.util;

import gov.nih.nci.po.data.bo.ClinicalResearchStaff;
import gov.nih.nci.po.data.bo.Family;
import gov.nih.nci.po.data.bo.FamilyOrganizationRelationship;
import gov.nih.nci.po.data.bo.HealthCareFacility;
import gov.nih.nci.po.data.bo.HealthCareProvider;
import gov.nih.nci.po.data.bo.Organization;
import gov.nih.nci.po.data.bo.OrganizationRelationship;
import gov.nih.nci.po.data.bo.OrganizationalContact;
import gov.nih.nci.po.data.bo.OversightCommittee;
import gov.nih.nci.po.data.bo.Person;
import gov.nih.nci.po.data.bo.ResearchOrganization;
import gov.nih.nci.po.service.OrganizationSearchCriteria;
import gov.nih.nci.po.service.PersonSearchCriteria;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;

/**
 * Manage things in session.
 * TODO revisit this implementations if you plan to use session replication for failover.
 * @author gax
 */
@SuppressWarnings("PMD.TooManyMethods")
public class PoHttpSessionUtil {

    private static int sidCounter = Integer.MIN_VALUE;

    /**
     * Regex that matches PO SIDs.
     */
    public static final String SID_REGEX = "^[A-Za-z]+\\-[0-9A-Fa-f]+$";

    private static synchronized String getNextSid(String prefix) {
        return prefix + "-" + Integer.toHexString(sidCounter++);
    }

    /**
     * short for ServletActionContext.getRequest().getSession().
     * @return current session
     */
    public static HttpSession getSession() {
        return ServletActionContext.getRequest().getSession();
    }

    /**
     * Adds an object to the http session with a unique key.
     * @param prefix prefix of sessionkey to use.
     * @param o the object to add to the session
     * @return the key used to add the objects
     */
    private static String addUniqueAttribute(String prefix, Serializable o) {
        HttpSession session = getSession();
        // make sure this session id is unique
        String sessionKey = getNextSid(prefix);
        synchronized (session) {
            while (session.getAttribute(sessionKey) != null) {
                sessionKey = getNextSid(prefix);
            }
            getSession().setAttribute(sessionKey, o);
        }
        return sessionKey;
    }

    /**
     * Validates if there is an attribute in the session with the given key and throws an IllegalArgumentException if
     * there is no matching session attribute.
     * @param key session key to validate
     */
    public static final void validateSessionKey(String key) {
        if (StringUtils.isNotBlank(key) && getSession().getAttribute(key) == null) {
            throw new IllegalArgumentException("invalid session key: " + key);
        }
    }

    /**
     * Adds an org to the http session with a unique key.
     * @param o the org to add to the session
     * @return the key used to add the objects
     */
    public static String addAttribute(Organization o) {
        return addUniqueAttribute("o", o);
    }

    /**
     * Adds a family to the http session with a unique key.
     * @param f the family to add to the session
     * @return the key used to add the objects
     */
    public static String addAttribute(Family f) {
        return addUniqueAttribute("f", f);
    }

    /**
     * Adds a family organization relationship to the http session with a unique key.
     * @param famOrgRel the family organization relationship to add to the session
     * @return the key used to add the objects
     */
    public static String addAttribute(FamilyOrganizationRelationship famOrgRel) {
        return addUniqueAttribute("for", famOrgRel);
    }

    /**
     * Adds an organization relationship to the http session with a unique key.
     * @param orgRelationship the organization relationship to add to the session
     * @return the key used to add the objects
     */
    public static String addAttribute(OrganizationRelationship orgRelationship) {
        return addUniqueAttribute("or", orgRelationship);
    }

    /**
     * Adds a person to the http session with a unique key.
     * @param p the org to add to the session
     * @return the key used to add the objects
     */
    public static String addAttribute(Person p) {
        return addUniqueAttribute("p", p);
    }

    /**
     * Remove an object and all it's children.
     * @param key name of object to remove
     */
    public static void removeAttribute(String key) {
        ArrayList<String> remove = new ArrayList<String>();
        HttpSession session = getSession();
        @SuppressWarnings("unchecked")
        Enumeration<String> en = session.getAttributeNames();
        while (en.hasMoreElements()) {
            String n = en.nextElement();
            if (n.startsWith(key)) {
                remove.add(n);
            }
        }
        for (String s : remove) {
            session.removeAttribute(s);
        }
    }

    /**
     * Adds a struts org search criteria to the http session with a unique key.
     * @param criteria the organization search criteria to add to the session
     * @return the key used to add the objects
     */
    public static String addAttribute(OrganizationSearchCriteria criteria) {
        return addUniqueAttribute("sosc", criteria);
    }

    /**
     * Adds a struts person search criteria to the http session with a unique key.
     * @param criteria the person search criteria to add to the session
     * @return the key used to add the objects
     */
    public static String addAttribute(PersonSearchCriteria criteria) {
        return addUniqueAttribute("spsc", criteria);
    }

    /**
     * Adds a clinical research staff role to the http session with a unique key.
     * @param role the person role to add to the session
     * @return the key used to add the objects
     */
    public static String addAttribute(ClinicalResearchStaff role) {
        return addUniqueAttribute("crs", role);
    }

    /**
     * Adds a health care provider role to the http session with a unique key.
     * @param role the person role to add to the session
     * @return the key used to add the objects
     */
    public static String addAttribute(HealthCareProvider role) {
        return addUniqueAttribute("hcp", role);
    }

    /**
     * Adds a organization contact role to the http session with a unique key.
     * @param role the person role to add to the session
     * @return the key used to add the objects
     */
    public static String addAttribute(OrganizationalContact role) {
        return addUniqueAttribute("oc", role);
    }
    /**
     * Adds a health care facility role to the http session with a unique key.
     * @param role the person role to add to the session
     * @return the key used to add the objects
     */
    public static String addAttribute(HealthCareFacility role) {
        return addUniqueAttribute("hcf", role);
    }

    /**
     * Adds a research organization role to the http session with a unique key.
     * @param role the person role to add to the session
     * @return the key used to add the objects
     */
    public static String addAttribute(ResearchOrganization role) {
        return addUniqueAttribute("ro", role);
    }
    
    /**
     * Adds a OversightCommittee role to the http session with a unique key.
     * @param role the person role to add to the session
     * @return the key used to add the objects
     */
    public static String addAttribute(OversightCommittee role) {
        return addUniqueAttribute("committee", role);
    }
}
