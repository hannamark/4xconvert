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
package gov.nih.nci.services.person;

import gov.nih.nci.coppa.iso.Ad;
import gov.nih.nci.coppa.iso.Cd;
import gov.nih.nci.coppa.iso.DSet;
import gov.nih.nci.coppa.iso.EnPn;
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.coppa.iso.Tel;
import gov.nih.nci.coppa.services.LimitOffset;
import gov.nih.nci.coppa.services.TooManyResultsException;
import gov.nih.nci.po.data.CurationException;
import gov.nih.nci.po.data.bo.AbstractPerson;
import gov.nih.nci.po.data.bo.Patient;
import gov.nih.nci.po.data.bo.Person;
import gov.nih.nci.po.data.bo.PersonCR;
import gov.nih.nci.po.data.bo.PersonEthnicGroup;
import gov.nih.nci.po.data.bo.PersonRace;
import gov.nih.nci.po.data.convert.CdConverter;
import gov.nih.nci.po.data.convert.DateConverter;
import gov.nih.nci.po.data.convert.EthnicGroupCodeConverter;
import gov.nih.nci.po.data.convert.IdConverter;
import gov.nih.nci.po.data.convert.IiConverter;
import gov.nih.nci.po.data.convert.RaceCodeConverter;
import gov.nih.nci.po.data.convert.RoleStatusConverter;
import gov.nih.nci.po.data.convert.SexCodeConverter;
import gov.nih.nci.po.data.convert.StatusCodeConverter;
import gov.nih.nci.po.data.convert.TsConverter;
import gov.nih.nci.po.data.convert.IdConverter.PersonIdConverter;
import gov.nih.nci.po.service.AnnotatedBeanSearchCriteria;
import gov.nih.nci.po.service.EntityValidationException;
import gov.nih.nci.po.service.PatientServiceLocal;
import gov.nih.nci.po.service.PersonCRServiceLocal;
import gov.nih.nci.po.service.PersonServiceLocal;
import gov.nih.nci.po.service.PersonSortCriterion;
import gov.nih.nci.po.util.PoHibernateSessionInterceptor;
import gov.nih.nci.po.util.PoXsnapshotHelper;
import gov.nih.nci.services.Utils;
import gov.nih.nci.services.entity.NullifiedEntityException;
import gov.nih.nci.services.entity.NullifiedEntityInterceptor;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;
import javax.jms.JMSException;

import org.apache.log4j.Logger;
import org.jboss.annotation.security.SecurityDomain;

import com.fiveamsolutions.nci.commons.data.search.PageSortParams;
import com.fiveamsolutions.nci.commons.ejb.AuthorizationInterceptor;

/**
*
* @author lpower
*/
@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@Interceptors({ AuthorizationInterceptor.class, PoHibernateSessionInterceptor.class, 
    NullifiedEntityInterceptor.class  })
@SecurityDomain("po")
@SuppressWarnings({"PMD.TooManyMethods" })
public class PersonEntityServiceBean implements PersonEntityServiceRemote {

    private static final Logger LOG = Logger.getLogger(PersonEntityServiceBean.class);
    private PersonServiceLocal perService;
    private PatientServiceLocal patientService;
    private PersonCRServiceLocal perCRService;
    private static final String DEFAULT_METHOD_ACCESS_ROLE = "client";
    private static int maxResults = Utils.MAX_SEARCH_RESULTS;
    
    /**
     * @param max set the maximum 
     * @deprecated only for testing
     */
    @Deprecated
    public static void setMaxResultsReturnedLimit(int max) {
        maxResults = max;
    }
    /**
     * @param svc service, injected
     */
    @EJB
    public void setPersonServiceBean(PersonServiceLocal svc) {
        this.perService = svc;
    }
    
    /**
     * @param svc service, injected
     */
    @EJB
    public void setPatientServiceBean(PatientServiceLocal svc) {
        this.patientService = svc;
    }
    
    /**
     * @param svc service, injected
     */
    @EJB
    public void setPersonCRServiceBean(PersonCRServiceLocal svc) {
        this.perCRService = svc;
    }

    /**
     * @return perService that was injected by container.
     */
    public PersonServiceLocal getPersonServiceBean() {
        return this.perService;
    }
    
    /**
     * @return perService that was injected by container.
     */
    public PatientServiceLocal getPatientServiceBean() {
        return this.patientService;
    }
    /**
     * {@inheritDoc}
     * @throws NullifiedEntityException
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    @RolesAllowed(DEFAULT_METHOD_ACCESS_ROLE)
    public PersonDTO getPerson(Ii id) throws NullifiedEntityException {
        if (isPatient(id)) {
             long targetId = IiConverter.convertPatientToLong(id);
             Patient patBO = patientService.getById(targetId);
             return genPersonFromPatient(patBO, id);
        } else {
            Person perBO = perService.getById(IiConverter.convertToLong(id));
            return (PersonDTO) PoXsnapshotHelper.createSnapshot(perBO);
        }
    }
    
    private PersonDTO genPersonFromPatient(Patient patBO, Ii id) {
        PersonDTO pDto = new PersonDTO();
        pDto.setBirthDate(DateConverter.convertToTs(patBO.getBirthDate()));
        EthnicGroupCodeConverter.EnumConverter egc = new EthnicGroupCodeConverter.EnumConverter();
        pDto.setEthnicGroupCode(egc.convert(DSet.class, patBO.getEthnicGroupCode()));
        RaceCodeConverter.EnumConverter rc = new RaceCodeConverter.EnumConverter();
        pDto.setRaceCode(rc.convert(DSet.class, patBO.getRaceCode()));
        pDto.setSexCode(SexCodeConverter.convertToCd(patBO.getSexCode()));
        
        pDto.setIdentifier(id);
        EnPn enPn = new EnPn();
        enPn.setNullFlavor(gov.nih.nci.coppa.iso.NullFlavor.MSK);
        pDto.setName(enPn);
        Ad ad = new Ad();
        ad.setNullFlavor(gov.nih.nci.coppa.iso.NullFlavor.MSK);
        pDto.setPostalAddress(ad);
        pDto.setStatusCode(RoleStatusConverter.convertToCd(patBO.getStatus()));
        Tel tel = new Tel();
        tel.setNullFlavor(gov.nih.nci.coppa.iso.NullFlavor.MSK);
        DSet<Tel> tels = new DSet<Tel>();
        tels.setItem(new HashSet<Tel>());
        tels.getItem().add(tel);
        pDto.setTelecomAddress(tels);
        
        return pDto;
    }
    
    private boolean isPatient(Ii id) {
        if (id != null && id.getNullFlavor() == null 
                && id.getExtension() != null && id.getExtension().startsWith(IdConverter.PATIENT_PREFIX)) {
            return true;
        }
        
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @RolesAllowed(DEFAULT_METHOD_ACCESS_ROLE)
    @SuppressWarnings("PMD.PreserveStackTrace")
    public Ii createPerson(PersonDTO person) throws EntityValidationException, CurationException {
        Person perBO = (Person) PoXsnapshotHelper.createModel(person);
        try {
            return new PersonIdConverter().convertToIi(perService.create(perBO));
        } catch (JMSException e) {
            LOG.error("Problem is JMS, unable to complete requst to create data.", e);
            throw new CurationException("Unable to publish the creation message.");
        }
    }
  
    /**
     * {@inheritDoc}
     */
    @RolesAllowed(DEFAULT_METHOD_ACCESS_ROLE)
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public Map<String, String[]> validate(PersonDTO person) {
        Person perBO = (Person) PoXsnapshotHelper.createModel(person);
        return perService.validate(perBO);
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    @RolesAllowed(DEFAULT_METHOD_ACCESS_ROLE)
    @Deprecated
    public List<PersonDTO> search(PersonDTO person) {
        Person personBO = (Person) PoXsnapshotHelper.createModel(person);
        List<Person> listBOs = getPersonServiceBean().search(new AnnotatedBeanSearchCriteria<Person>(personBO), null);
        return PoXsnapshotHelper.createSnapshotList(listBOs);
    }
    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    @RolesAllowed(DEFAULT_METHOD_ACCESS_ROLE)
    public List<PersonDTO> search(PersonDTO person, LimitOffset pagination) throws TooManyResultsException {
        Person personBO = (Person) PoXsnapshotHelper.createModel(person);
        int maxLimit = Math.min(pagination.getLimit(), maxResults + 1);
        PageSortParams<Person> params = new PageSortParams<Person>(maxLimit, pagination.getOffset(), 
                PersonSortCriterion.PERSON_ID, false);
        List<Person> listBOs = getPersonServiceBean().search(new AnnotatedBeanSearchCriteria<Person>(personBO), params);
        if (listBOs.size() > maxResults) {
            throw new TooManyResultsException(maxResults);
        }
        return PoXsnapshotHelper.createSnapshotList(listBOs);
    }

    /**
     * {@inheritDoc}
     */
    @RolesAllowed(DEFAULT_METHOD_ACCESS_ROLE)
    public void updatePerson(PersonDTO proposedState) throws EntityValidationException {
        if (isPatient(proposedState.getIdentifier())) {
            updatePatient(proposedState);
        } else {
            Long pId = IiConverter.convertToLong(proposedState.getIdentifier());
            if (pId != null) {
                Person target = perService.getById(pId);
                if (target != null) {
                    PersonCR cr = new PersonCR(target);
                    proposedState.setIdentifier(null);
                    PoXsnapshotHelper.copyIntoAbstractModel(proposedState, cr, AbstractPerson.class);
                    cr.setId(null);
                    if (cr.getStatusCode() != target.getStatusCode()) {
                        throw new IllegalArgumentException(
                                "use updateOrganizationStatus() to update the statusCode property");
                    }
                    perCRService.create(cr);
                } else {
                    throw new IllegalArgumentException("Person could not be found with provided identifier.");
                }
            } else {
                throw new IllegalArgumentException("Person to be updated did not contain an identifier.");
            }
        }
    }
    
    private void updatePatient(PersonDTO proposedState) {
        long pid = IiConverter.convertPatientToLong(proposedState.getIdentifier());
        Patient patBO = patientService.getById(pid);
        if (patBO != null) {
            // copy over fields from person dto to patient dto.
            patBO.setBirthDate(TsConverter.convertToDate(proposedState.getBirthDate()));
            patBO.setSexCode(SexCodeConverter.convertToStatusEnum(proposedState.getSexCode()));
            EthnicGroupCodeConverter.DSetConverter egConv = new EthnicGroupCodeConverter.DSetConverter();
            patBO.setEthnicGroupCode((Set<PersonEthnicGroup>) egConv
                    .convert(Set.class, proposedState.getEthnicGroupCode()));
            RaceCodeConverter.DSetConverter rcConv = new RaceCodeConverter.DSetConverter();
            patBO.setRaceCode((Set<PersonRace>) rcConv.convert(Set.class, proposedState.getRaceCode()));
            
            try {
                patientService.curate(patBO);
            } catch (JMSException jms) {
                LOG.error("Problem is JMS, unable to complete requst to update patient data.", jms);
                throw new IllegalArgumentException("JMS Exception during curation of patient", jms);
            }
        } else {
            throw new IllegalArgumentException("Patient could not be found with provided identifier.");
        }
    }
    
    private void updatePatientStatus(Ii targetIi, Cd statusCode) {
        long pid = IiConverter.convertPatientToLong(targetIi);
        Patient target = patientService.getById(pid);
        if (target != null) {
            target.setStatus(CdConverter.convertToRoleStatus(statusCode));
            try {
                patientService.curate(target);
            } catch (JMSException e) {
                LOG.error("Problem is JMS, unable to complete requst to update patient status.", e);
                throw new IllegalArgumentException("JMS Exception during curation of patient status", e);
            }
        } else {
            throw new IllegalArgumentException("Patient could not be found with provided identifier.");
        }
    }
    
    /**
     * {@inheritDoc}
     */
    @RolesAllowed(DEFAULT_METHOD_ACCESS_ROLE)
    public void updatePersonStatus(Ii targetOrg, Cd statusCode) throws EntityValidationException {
        if (isPatient(targetOrg)) {
            updatePatientStatus(targetOrg, statusCode);
        } else {
            Long pId = IiConverter.convertToLong(targetOrg);
            if (pId != null) {
                Person target = perService.getById(pId);
                if (target != null) { 
                    // lazy way to clone with stripped hibernate IDs.
                    PersonDTO tmp = (PersonDTO) PoXsnapshotHelper.createSnapshot(target);
                    PersonCR cr = new PersonCR(target);
                    PoXsnapshotHelper.copyIntoAbstractModel(tmp, cr, AbstractPerson.class);
                    cr.setId(null);
                    cr.setStatusCode(StatusCodeConverter.convertToStatusEnum(statusCode));
                    perCRService.create(cr);
                } else {
                    throw new IllegalArgumentException("Person could not be found with provided identifier.");
                }
            } else {
                throw new IllegalArgumentException("Person to be updated did not contain an identifier.");
            }
        }
    }
}