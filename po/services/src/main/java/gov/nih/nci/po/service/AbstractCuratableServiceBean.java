/**
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
package gov.nih.nci.po.service;

import gov.nih.nci.po.data.bo.AbstractEnhancedOrganizationRole;
import gov.nih.nci.po.data.bo.AbstractPersonRole;
import gov.nih.nci.po.data.bo.Alias;
import gov.nih.nci.po.data.bo.Aliasable;
import gov.nih.nci.po.data.bo.ChangeRequest;
import gov.nih.nci.po.data.bo.Contactable;
import gov.nih.nci.po.data.bo.Correlation;
import gov.nih.nci.po.data.bo.Curatable;
import gov.nih.nci.po.data.bo.Email;
import gov.nih.nci.po.data.bo.Organization;
import gov.nih.nci.po.data.bo.Overridable;
import gov.nih.nci.po.data.bo.Person;
import gov.nih.nci.po.data.bo.PersonRole;
import gov.nih.nci.po.data.bo.PhoneNumber;
import gov.nih.nci.po.data.bo.RoleStatus;
import gov.nih.nci.po.util.PersistentObjectHelper;
import gov.nih.nci.po.util.PoHibernateUtil;
import gov.nih.nci.security.authorization.domainobjects.User;

import org.apache.commons.lang.Validate;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;

import javax.ejb.EJB;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.jms.JMSException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author Scott Miller
 * @author Rohit Gupta
 * @param <T>
 */
public class AbstractCuratableServiceBean<T extends Curatable> extends AbstractBaseServiceBean<T> {
    
    
    /**
     * message publisher used on update notification.
     */
    @EJB
    private MessageProducerLocal publisher;

    /**
     * @return the publisher
     */
    public MessageProducerLocal getPublisher() {
        return publisher;
    }

    /**
     * @param publisher the publisher to set
     */
    public void setPublisher(MessageProducerLocal publisher) {
        this.publisher = publisher;
    }
    
    /**
     * Save the object.
     * @param obj the object
     * @return the id
     * @throws EntityValidationException any validation errors.
     * @throws JMSException any problems publishing announcements via JMS
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public long create(T obj) throws EntityValidationException, JMSException {
        long id = super.createAndValidate(obj);
        getPublisher().sendCreate(getTypeArgument(), obj);
        return id;
    }
    
    /**
     * {@inheritDoc}
     * 
     * @throws JMSException
     */
    @SuppressWarnings(UNCHECKED)
    protected long createActiveWithFallback(AbstractPersonRole obj)
            throws EntityValidationException, JMSException {
        Session s = PoHibernateUtil.getCurrentSession();
        long id = create((T) obj);
        s.flush();
        T createdRole = getById(id);

        ((Correlation) createdRole).setStatus(RoleStatus.ACTIVE);
        copyContactInfoFromPersonToRole(((PersonRole) createdRole).getPlayer(),
                (Contactable) createdRole);
        Map<String, String[]> errors = validate(createdRole);
        if (errors != null && !errors.isEmpty()) {            
            s.evict(createdRole);
        } else {
            curate(createdRole);
        }
        return id;
    }
    

    private void copyContactInfoFromPersonToRole(Person player,
            Contactable createdRole) {
        for (Email email : player.getEmail()) {
            Email roleEmail = new Email();
            roleEmail.setValue(email.getValue());
            createdRole.getEmail().add(roleEmail);
        }
        for (PhoneNumber phone : player.getPhone()) {
            PhoneNumber rolePhone = new PhoneNumber();
            rolePhone.setValue(phone.getValue());
            createdRole.getPhone().add(rolePhone);
        }

    }

    /**
     * Curates the object.
     * @param curatable the object to curate.
     * @throws JMSException any problems publishing announcements via JMS
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void curate(T curatable) throws JMSException {
        final Session s = PoHibernateUtil.getCurrentSession();
        T object = curatable;
        if (object.getId() != null) {
            object = loadAndMerge(object, s);
            handleExistingObjectCuration(s, object);
            getPublisher().sendUpdate(getTypeArgument(), object);
        } else {
            s.save(PersistentObjectHelper.initialize(object));
            getPublisher().sendCreate(getTypeArgument(), object);
        }
    }
    
    /**
     * Curates the object without processing its CR.
     * @param curatable the object to curate.
     * @throws JMSException any problems publishing announcements via JMS
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void curateWithoutCRProcessing(T curatable) throws JMSException {
        final Session s = PoHibernateUtil.getCurrentSession();
        T object = curatable;
        if (object.getId() != null) {
            object = loadAndMerge(object, s);
            getPublisher().sendUpdate(getTypeArgument(), object);
        } else {
            s.save(PersistentObjectHelper.initialize(object));
            getPublisher().sendCreate(getTypeArgument(), object);
        }
    }
    
    
    /**
     * Curates the object by setting 'overriddenBy' attribute.
     * @param overridable the object to curate.
     * @param overriddenBy User who overrode the entity.
     */
    @SuppressWarnings({ "rawtypes", "PMD.AvoidReassigningParameters" })
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void override(Overridable overridable, User overriddenBy) {        
        final Session s = PoHibernateUtil.getCurrentSession(); 
        overridable = (Overridable) s.load(getTypeArgument(), overridable.getId());
        overridable.setOverriddenBy(overriddenBy);     
        s.update(overridable);
    }
    
    /**
     * Get the object of type T with the given IDs.
     * @param pids the ids of players
     * @return the object
     */
    @SuppressWarnings("unchecked")
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<T> getByPlayerIds(Long[] pids) {
        if (pids == null || pids.length == 0) {
            return Collections.EMPTY_LIST;
        }

        if (pids.length > MAX_IN_CLAUSE_SIZE) {
            throw new IllegalArgumentException("getByPlayerIds can only search for " 
                    + MAX_IN_CLAUSE_SIZE + " at once.");
        }

        Query q = PoHibernateUtil.getCurrentSession().createQuery("from " + getTypeArgument().getName()
                + " obj where obj.status != :roleStatus AND obj.player.id in (:ids_list)");
        q.setParameter("roleStatus", RoleStatus.NULLIFIED);
        q.setParameterList("ids_list", pids);
        return q.list();
    }

    @SuppressWarnings(UNCHECKED)
    private void handleExistingObjectCuration(final Session s, Curatable object) {
        Curatable target = null;
        ArrayList<ChangeRequest<Curatable>> crs = new ArrayList<ChangeRequest<Curatable>>(
                object.getChangeRequests());

        for (ChangeRequest<Curatable> cr : crs) {
            Curatable currentTarget = cr.getTarget();

            if (currentTarget == null) {
                throw new IllegalArgumentException("target cannot be null");
            }
            if (target == null) {
                target = currentTarget;
            } else if (!target.equals(currentTarget)) {
                throw new IllegalArgumentException("all crs must have the same target");
            }

            cr.setProcessed(true);
            s.update(cr);
        }

        if (target != null) {
            target.getChangeRequests().removeAll(crs);
            s.update(target);
        }
    }

    /**
     * @param object the stale object.
     * @param s the session to load from
     * @return the merged object.
     */
    @SuppressWarnings({ "PMD.CompareObjectsWithEquals", "unchecked" })
    protected T loadAndMerge(T object, Session s) {
        T o = (T) s.load(getTypeArgument(), object.getId());
        if (object != o) {
            o = (T) s.merge(object);
        } else {
            o = object;
        }
        return o;
    }

    /**
     * Get the number of roles that need attention from the curator.
     * @param entityId the player entity's id for the roles.
     * @param roleClass the type of role.
     * @return the count of roles that need attention.
     */
    protected int getHotRoleCount(long entityId, Class<? extends Correlation> roleClass) {
        return getHotRoleCount(entityId, roleClass, true);
    }

    /**
     * Get the number of roles that need attention from the curator, based on the given scoper.
     * @param entityId the scoper entity's id for the roles.
     * @param roleClass the type of role.
     * @return the count of roles that need attention.
     */
    protected int getScoperHotRoleCount(long entityId, Class<? extends Correlation> roleClass) {
        return getHotRoleCount(entityId, roleClass, false);
    }

    private int getHotRoleCount(long entityId, Class<? extends Correlation> roleClass, boolean player) {
        final StringBuffer hql =
                new StringBuffer("select count(distinct r) from " + roleClass.getName()
                        + " r LEFT OUTER JOIN r.changeRequests as rcr where ");
        if (player) {
            hql.append(" r.player.id = ");
        } else {
            hql.append(" r.scoper.id = ");
        }

        hql.append(entityId).append(" and (r.status = 'PENDING' or rcr.processed = 'false')");
        Number n = (Number) PoHibernateUtil.getCurrentSession().createQuery(hql.toString()).uniqueResult();
        return n.intValue();
    }

    /**
     * Merges the aliases for the given object to its duplicate denoted by deuplicateOf.
     *
     * The name of the item and its aliases are merged into the aliases of its duplicate.
     * @param e The entity to merge.
     */
    protected void mergeAliasesToDuplicate(T e) {
        Validate.notNull(e);

        //if duplicate set, then cascade aliases
        if (e instanceof Aliasable && e.getDuplicateOf() instanceof Aliasable) {
            mergeAliases((Aliasable) e, (Aliasable) e.getDuplicateOf());
        }
    }


    private void mergeAliases(Aliasable e, Aliasable duplicateOf) {
        List<String> targetValues = new ArrayList<String>();
        for (Alias alias : duplicateOf.getAlias()) {
            targetValues.add(alias.getValue());
        }

        for (Alias alias : e.getAlias()) {
            if (!targetValues.contains(alias.getValue())) {
                duplicateOf.getAlias().add(new Alias(alias.getValue()));
            }
        }

        Alias nameAlias = getNameAsAlias(e);

        if (nameAlias != null
                && !targetValues.contains(nameAlias.getValue())) {
            duplicateOf.getAlias().add(new Alias(nameAlias.getValue()));
        }
    }

    private Alias getNameAsAlias(Aliasable e) {
        Alias nameAlias = null;

        if (e instanceof Organization
                && StringUtils.isNotBlank(((Organization) e).getName())) {
            nameAlias = new Alias(((Organization) e).getName());
        } else if (e instanceof AbstractEnhancedOrganizationRole
                && StringUtils
                        .isNotBlank(((AbstractEnhancedOrganizationRole) e)
                                .getName())) {
            nameAlias = new Alias(
                    ((AbstractEnhancedOrganizationRole) e).getName());
        }
        return nameAlias;
    }
}
