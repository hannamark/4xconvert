/*
 * The software subject to this notice and license includes both human readable
 * source code form and machine readable, binary, object code form. The pa
 * Software was developed in conjunction with the National Cancer Institute
 * (NCI) by NCI employees and 5AM Solutions, Inc. (5AM). To the extent
 * government employees are authors, any rights in such works shall be subject
 * to Title 17 of the United States Code, section 105.
 *
 * This pa Software License (the License) is between NCI and You. You (or
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
 * its rights in the pa Software to (i) use, install, access, operate,
 * execute, copy, modify, translate, market, publicly display, publicly perform,
 * and prepare derivative works of the pa Software; (ii) distribute and
 * have distributed to and by third parties the pa Software and any
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
package gov.nih.nci.pa.service;

import gov.nih.nci.iso21090.Bl;
import gov.nih.nci.iso21090.DSet;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.iso21090.Tel;
import gov.nih.nci.pa.enums.StudySiteFunctionalCode;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.StudySiteAccrualStatusDTO;
import gov.nih.nci.pa.iso.dto.StudySiteDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.DSetConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.IvlConverter;
import gov.nih.nci.pa.util.HibernateSessionInterceptor;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.pa.util.PaRegistry;
import gov.nih.nci.po.data.CurationException;
import gov.nih.nci.po.service.EntityValidationException;
import gov.nih.nci.services.correlation.ClinicalResearchStaffDTO;
import gov.nih.nci.services.correlation.HealthCareFacilityDTO;
import gov.nih.nci.services.correlation.HealthCareProviderDTO;
import gov.nih.nci.services.correlation.OrganizationalContactDTO;
import gov.nih.nci.services.organization.OrganizationDTO;
import gov.nih.nci.services.person.PersonDTO;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

/**
 * @author mshestopalov
 *
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@Interceptors(HibernateSessionInterceptor.class)
public class ParticipatingSiteBeanLocal extends AbstractParticipatingSitesBean
    implements ParticipatingSiteServiceLocal {
    private SessionContext ejbContext;

    @Resource
    void setSessionContext(SessionContext ctx) {
        this.ejbContext = ctx;
    }

    /**
     * {@inheritDoc}
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public Bl isParticipatingSite(Ii studyProtocolIi, Ii someHcfIi) throws PAException {
        try {
            
            Ii siteIi = getParticipatingSiteIi(studyProtocolIi, someHcfIi);
            Bl bl = new Bl();
            bl.setValue(Boolean.valueOf(PAUtil.isIiNotNull(siteIi)));
            return bl;
        } catch (Exception e) {
            throw new PAException(e);
        }
    }
    
    /**
     * {@inheritDoc}
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public Ii getParticipatingSiteIi(Ii studyProtocolIi, Ii someHcfIi) throws PAException {
        try {
            StudyProtocolDTO studyProtocolDTO = getStudyProtocolService().getStudyProtocol(studyProtocolIi);
            HealthCareFacilityDTO hcfDTO = new HealthCareFacilityDTO();
            hcfDTO.setIdentifier(DSetConverter.convertIiToDset(someHcfIi));
            Ii poHcfIi = generateHcfIiFromCtepIdOrNewOrg(null, hcfDTO);
            return getStudySiteService()
                .getStudySiteIiByTrialAndPoHcfIi(studyProtocolDTO.getIdentifier(), poHcfIi);
        } catch (Exception e) {
            throw new PAException(e);
        }
    }
    
   

    
    /**
     * {@inheritDoc}
     */
    public void addStudySiteGenericContact(Ii studySite, OrganizationalContactDTO contactDTO, 
            boolean isPrimaryContact, DSet<Tel> telecom) 
        throws PAException {
        try {

            StudySiteDTO studySiteDTO = PaRegistry.getStudySiteService().get(studySite);
            
            Ii genericContactIi = getCorrUtils().getGenericContactIiFromCtepId(contactDTO);
            Ii orgIi = getCorrUtils().getPoOrgIiFromPaHcfIi(studySiteDTO.getHealthcareFacilityIi());
            Map<String, Ii> myMap = new HashMap<String, Ii>();
            myMap.put(IiConverter.ORGANIZATIONAL_CONTACT_ROOT, genericContactIi);
            myMap.put(IiConverter.ORG_ROOT, orgIi);
            createStudyParticationContactRecord(studySiteDTO, myMap, 
                    isPrimaryContact, null, telecom);
        } catch (Exception e) {
            ejbContext.setRollbackOnly();
            throw new PAException(e);
        }
        
    }
    
    /**
     * {@inheritDoc}
     */
    public void addStudySiteInvestigator(Ii studySiteIi, ClinicalResearchStaffDTO poCrsDTO,
            HealthCareProviderDTO poHcpDTO, PersonDTO investigatorDTO, 
            String roleCode)
    throws PAException {
        try {
            StudySiteDTO studySiteDTO = PaRegistry.getStudySiteService().get(studySiteIi);
            Ii orgIi = getCorrUtils().getPoOrgIiFromPaHcfIi(studySiteDTO.getHealthcareFacilityIi());
            Map<String, Ii> myMap = generateCrsAndHcpFromCtepIdOrNewPerson(investigatorDTO,
                    poCrsDTO, poHcpDTO, orgIi); 
            createStudyParticationContactRecord(studySiteDTO, myMap, 
                    false, roleCode, null);
        } catch (Exception e) {
            ejbContext.setRollbackOnly();
            throw new PAException(e);
        }
    }
    
    /**
     * {@inheritDoc}
     */
    public void addStudySitePrimaryContact(Ii studySiteIi, ClinicalResearchStaffDTO poCrsDTO,
            HealthCareProviderDTO poHcpDTO, PersonDTO personDTO, DSet<Tel> telecom) throws PAException {
        try {
            StudySiteDTO studySiteDTO = PaRegistry.getStudySiteService().get(studySiteIi);
            Ii orgIi = getCorrUtils().getPoOrgIiFromPaHcfIi(studySiteDTO.getHealthcareFacilityIi());
            Map<String, Ii> myMap = generateCrsAndHcpFromCtepIdOrNewPerson(personDTO,
                    poCrsDTO, poHcpDTO, orgIi); 
            createStudyParticationContactRecord(studySiteDTO, myMap, 
                    true, null, telecom);
        } catch (Exception e) {
            ejbContext.setRollbackOnly();
            throw new PAException(e);
        }
    }
        
    /**
     * {@inheritDoc}
     */
    public Ii createStudySiteParticipant(StudySiteDTO studySiteDTO, 
            StudySiteAccrualStatusDTO currentStatusDTO,
            OrganizationDTO orgDTO, HealthCareFacilityDTO hcfDTO) throws PAException {
        Ii studySiteIi = null;
        // assume that siteDTO has a real Ii for studyProtocol
        try {
            Ii poHcfIi = generateHcfIiFromCtepIdOrNewOrg(orgDTO, hcfDTO);
            studySiteIi = createStudySiteParticipant(studySiteDTO,
                    currentStatusDTO, poHcfIi); 
        } catch (Exception e) {
            ejbContext.setRollbackOnly();
            throw new PAException(e);
        }
        return studySiteIi;
    }

    /**
     * {@inheritDoc}
     */
    public Ii createStudySiteParticipant(StudySiteDTO studySiteDTO,
            StudySiteAccrualStatusDTO currentStatusDTO, Ii poHcfIi) 
        throws PAException {
        // assume that there is a poHcf out there already
        // assume that siteDTO has a real Ii for studyProtocol
        Ii returnVal = null;
        try {
            // check business rules based on trial type.
            StudyProtocolDTO spDTO =
                getStudyProtocolService().getStudyProtocol(studySiteDTO.getStudyProtocolIdentifier()); 
            studySiteDTO.setStudyProtocolIdentifier(spDTO.getIdentifier());
            if (spDTO.getProprietaryTrialIndicator().getValue().booleanValue()) {
                enforceBusinessRulesForProprietary(spDTO, studySiteDTO, currentStatusDTO);
            } else {
                enforceBusinessRules(currentStatusDTO, PAUtil.getCurrentTime());
            }
            StudySiteDTO ssDTO = saveOrUpdateStudySiteHelper(true, studySiteDTO, poHcfIi,
                    currentStatusDTO);
            returnVal =  ssDTO.getIdentifier();
        } catch (Exception e) {
            ejbContext.setRollbackOnly();
            throw new PAException(e);
        }
        return returnVal;
    }
    
    /**
     * {@inheritDoc}
     */
    public void updateStudySiteParticipant(StudySiteDTO studySiteDTO,
            StudySiteAccrualStatusDTO currentStatusDTO) 
        throws PAException {
        // assume that siteDTO has a real Ii for studyProtocol
        
        try {
            // check business rules based on trial type.
            StudySiteDTO currentSite = PaRegistry.getStudySiteService().get(studySiteDTO.getIdentifier());
            studySiteDTO.setStudyProtocolIdentifier(currentSite.getStudyProtocolIdentifier());
            studySiteDTO.setHealthcareFacilityIi(currentSite.getHealthcareFacilityIi());
            StudyProtocolDTO spDTO = PaRegistry.getStudyProtocolService()
                .getStudyProtocol(currentSite.getStudyProtocolIdentifier());
            if (spDTO.getProprietaryTrialIndicator().getValue().booleanValue()) {
                enforceBusinessRulesForProprietary(spDTO, studySiteDTO, currentStatusDTO);
            } else {
                enforceBusinessRules(currentStatusDTO, PAUtil.getCurrentTime());
            }
            saveOrUpdateStudySiteHelper(false, studySiteDTO, null,
                    currentStatusDTO);
            
        } catch (Exception e) {
            ejbContext.setRollbackOnly();
            throw new PAException(e);
        }
    }
    
    private StudySiteDTO saveOrUpdateStudySiteHelper(boolean isCreate, StudySiteDTO siteDTO, Ii poHcfIi,
            StudySiteAccrualStatusDTO currentStatus)
        throws PAException, EntityValidationException, CurationException {

        if (isCreate && poHcfIi != null) {
            Long paHealthCareFacilityId = getOcsr()
                .createHcfWithExistingPoHcf(poHcfIi);
            siteDTO.setHealthcareFacilityIi(IiConverter.convertToIi(paHealthCareFacilityId));
        } 
        siteDTO.setFunctionalCode(CdConverter.convertToCd(StudySiteFunctionalCode.TREATING_SITE));

        if (isCreate) {
            siteDTO.setIdentifier(null);
        }

        siteDTO.setStatusDateRange(IvlConverter.convertTs().convertToIvl(new Timestamp(new Date().getTime()), null));

        StudySiteDTO studySiteDTO = null;
        if (isCreate) {
            studySiteDTO = getStudySiteService().create(siteDTO);
        } else {
            studySiteDTO = getStudySiteService().update(siteDTO);
        }

        createStudySiteAccrualStatus(studySiteDTO.getIdentifier(), currentStatus);

        return studySiteDTO;
    }

}