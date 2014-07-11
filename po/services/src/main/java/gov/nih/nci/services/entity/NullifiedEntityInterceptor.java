/**
 * The software subject to this notice and license includes both human readable
 * source code form and machine readable, binary, object code form. The COPPA PO
 * Software was developed in conjunction with the National Cancer Institute
 * (NCI) by NCI employees and 5AM Solutions, Inc. (5AM). To the extent
 * government employees are authors, any rights in such works shall be subject
 * to Title 17 of the United States Code, section 105.
 *
 * This COPPA PO Software License (the License) is between NCI and You. You (or
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
 * its rights in the COPPA PO Software to (i) use, install, access, operate,
 * execute, copy, modify, translate, market, publicly display, publicly perform,
 * and prepare derivative works of the COPPA PO Software; (ii) distribute and
 * have distributed to and by third parties the COPPA PO Software and any
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
package gov.nih.nci.services.entity;

import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.po.service.OrganizationServiceLocal;
import gov.nih.nci.po.service.PersonServiceLocal;
import gov.nih.nci.services.AbstractBaseNullifiedInterceptor;
import gov.nih.nci.services.organization.OrganizationDTO;
import gov.nih.nci.services.organization.OrganizationEntityServiceBean;
import gov.nih.nci.services.person.PersonDTO;
import gov.nih.nci.services.person.PersonEntityServiceBean;

import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Interceptor to catch any NULLIFIED entities and throw a NullifiedEntityException.
 */
public class NullifiedEntityInterceptor extends AbstractBaseNullifiedInterceptor {
    
    /**
     * Ensures that no object(s) returned have a NULLIFIED entity status.
     *
     * @param invContext the method context
     * @return the method result
     * @throws Exception if invoking the method throws an exception.
     */
    @AroundInvoke
    @SuppressWarnings("PMD.SignatureDeclareThrowsException")
    public Object checkForNullified(InvocationContext invContext) throws Exception {
        // make the call to the underlying method. This method (checkForNullified) wraps the intended method.
        Object returnValue = invContext.proceed();
        if (returnValue instanceof Collection) {
            handleCollection(invContext, (Collection<?>) returnValue);
        } else if (returnValue instanceof PersonDTO) {
            handlePersonDTO(invContext, (PersonDTO) returnValue);
        } else if (returnValue instanceof OrganizationDTO) {
            handleOrgDTO(invContext, (OrganizationDTO) returnValue);
        }

        return returnValue;
    }

    /**
     * Check items in a collection for nullified.
     * @param invContext context.
     * @param collection to check.
     * @throws NullifiedEntityException if item is nullified.
     */
    protected void handleCollection(InvocationContext invContext,
                                  Collection<?> collection) throws NullifiedEntityException {
        if (collection == null) {
            return;
        }
        Map<Ii, Ii> nullifiedEntities = new HashMap<Ii, Ii>();
        for (Object object : collection) {
            Entry<Ii, Ii> entry = handleCollectionElement(invContext, object);
            if (entry != null) {
                nullifiedEntities.put(entry.getKey(), entry.getValue());
            }
        }
        if (!nullifiedEntities.isEmpty()) {
            throw new NullifiedEntityException(nullifiedEntities);
        }
    }
    
    /**
     * Handle an element in the collection.
     * @param invContext context.
     * @param element to handle.
     * @return Map entry.
     */
    protected Entry<Ii, Ii> handleCollectionElement(InvocationContext invContext, Object element) {
        Entry<Ii, Ii> entry = null;
        if (element instanceof PersonDTO) {
            entry = handle(invContext, (PersonDTO) element);
        } else if (element instanceof OrganizationDTO) {
            entry = handle(invContext, (OrganizationDTO) element);
        }
        return entry;
    }

    private void handleOrgDTO(InvocationContext invContext, OrganizationDTO dto) throws NullifiedEntityException {
        Entry<Ii, Ii> entry = handle(invContext, dto);
        if (entry != null) {
            throw new NullifiedEntityException(entry.getKey(), entry.getValue());
        }
    }



    private void handlePersonDTO(InvocationContext invContext, PersonDTO dto) throws NullifiedEntityException {
        Entry<Ii, Ii> entry = handle(invContext, dto);
        if (entry != null) {
            throw new NullifiedEntityException(entry.getKey(), entry.getValue());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected OrganizationServiceLocal getOrganizationServiceBean(InvocationContext invContext) {
        OrganizationEntityServiceBean bean = (OrganizationEntityServiceBean) invContext.getTarget();
        return bean.getOrganizationServiceBean();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected PersonServiceLocal getPersonServiceBean(InvocationContext invContext) {
        PersonEntityServiceBean bean = (PersonEntityServiceBean) invContext.getTarget();
        return bean.getPersonServiceBean();
    }

}
