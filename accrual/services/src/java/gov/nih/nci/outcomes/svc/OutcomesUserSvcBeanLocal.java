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

package gov.nih.nci.outcomes.svc;

import gov.nih.nci.accrual.service.util.AccrualCsmUtil;
import gov.nih.nci.accrual.util.PoRegistry;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.iso21090.St;
import gov.nih.nci.outcomes.svc.dto.UserSvcDto;
import gov.nih.nci.outcomes.svc.exception.OutcomesException;
import gov.nih.nci.pa.domain.Country;
import gov.nih.nci.pa.domain.RegistryUser;
import gov.nih.nci.pa.enums.USStateCode;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.HibernateSessionInterceptor;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.pa.util.PaRegistry;
import gov.nih.nci.security.authorization.domainobjects.User;
import gov.nih.nci.services.entity.NullifiedEntityException;
import gov.nih.nci.services.organization.OrganizationDTO;
import gov.nih.nci.services.person.PersonDTO;

import java.rmi.RemoteException;
import java.util.List;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

import org.apache.log4j.Logger;

/**
 * @author Lisa Kelley
 * @since Dec 9, 2009
 */
@Stateless
@Interceptors(HibernateSessionInterceptor.class)
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@SuppressWarnings({"PMD.CyclomaticComplexity", "PMD.NPathComplexity", "PMD.ExcessiveMethodLength" })
public class OutcomesUserSvcBeanLocal extends BaseOutcomesSvc implements OutcomesUserSvc, OutcomesUserSvcLocal {

    private SessionContext ejbContext;
    /**
     * Sets the session context.
     *
     * @param ctx the new session context
     */
    @Resource
    protected void setSessionContext(SessionContext ctx) {
        ejbContext = ctx;
    }

    /**
     * {@inheritDoc}
     */
    public UserSvcDto getUser() throws OutcomesException {
        String callerName = null;
        try {
            if (ejbContext.getCallerPrincipal() != null) {
                callerName = getCallerPrincipal(ejbContext);
            }
        } catch (Exception e) {
            Logger.getLogger(getClass()).error(e.getMessage());
        }
        St isoLoginName = StConverter.convertToSt(callerName);
        return getUser(isoLoginName);
    }

    private void validateLoginName(St isoLoginName) throws OutcomesException {
        if (PAUtil.isStNull(isoLoginName)) {
            throw new OutcomesException("LoginName must be set.");
        }
        String lName = StConverter.convertToString(isoLoginName);
        String contextName = null;
        try {
            contextName = getCallerPrincipal(ejbContext);
        } catch (Exception e) {
            Logger.getLogger(getClass()).error("Context not found." + e.getMessage());
        }
       if (PAUtil.isNotEmpty(contextName) && !lName.equals(contextName)) {
            Logger.getLogger(getClass()).error("LoginName " + lName + " does not match context" + contextName + ".");
       }

    }
    /**
     * {@inheritDoc}
     */
    public UserSvcDto createUser(UserSvcDto dto) throws OutcomesException {
        /* allow validation to occur on LoginName / Identity */
        validateLoginName(dto.getIdentity());
        return createUserHelper(dto);
    }

    /**
     * @param dto
     * @return the created user object
     * @throws OutcomesException if any error occurs or validation fails
     */
    protected UserSvcDto createUserHelper(UserSvcDto dto) throws OutcomesException {
        validatePhysicianAndTreatingSite(dto);
        String loginName = StConverter.convertToString(dto.getIdentity());

        RegistryUser registryUser;
        try {
            registryUser = PaRegistry.getRegisterUserService().getUser(loginName);
        } catch (PAException e) {
            throw new OutcomesException("Exception while retrieving user.", e);
        }
        if (registryUser != null) {
            throw new OutcomesException("User already exists.");
        }
        validateCountry(StConverter.convertToString(dto.getCountry()));
        UserSvcDto resultDto;
        try {
            registryUser = converterToRegistryUser(dto);
            registryUser.setCountry(getCountryName(registryUser.getCountry()));
            registryUser.setState(USStateCode.valueOf(registryUser.getState()).getCode());
            // create the CSM user
            User csmUser = AccrualCsmUtil.getInstance().createCSMUser(registryUser,
                loginName, StConverter.convertToString(dto.getPassword()));
            registryUser.setCsmUserId(csmUser.getUserId());
            registryUser = PaRegistry.getRegisterUserService().createUser(registryUser);
            registryUser.setCountry(getCountryCode(registryUser.getCountry()));
            registryUser.setState(USStateCode.getByCode(registryUser.getState()).getName());
            resultDto = converterToSvcDto(registryUser);
            resultDto.setIdentity(StConverter.convertToSt(csmUser.getLoginName()));
            resultDto.setPassword(StConverter.convertToSt(csmUser.getPassword()));
        } catch (RemoteException e) {
            throw new OutcomesException("Iso conversion exception in createUser().", e);
        } catch (PAException e) {
            throw new OutcomesException("Exception while creating user.", e);
        }
        return resultDto;
    }
    /**
     * {@inheritDoc}
     */
    public UserSvcDto updateUser(UserSvcDto dto) throws OutcomesException {
        validateLoginName(dto.getIdentity());
        validatePhysicianAndTreatingSite(dto);
        String loginName = StConverter.convertToString(dto.getIdentity());
        RegistryUser registryUser;
        try {
            registryUser = PaRegistry.getRegisterUserService().getUser(loginName);
        } catch (PAException e) {
            throw new OutcomesException("Exception while retrieving user.", e);
        }
        if (registryUser == null) {
            throw new OutcomesException("User does not exist.");
        }
        validateCountry(StConverter.convertToString(dto.getCountry()));
        Long csmUserId = registryUser.getCsmUserId();
        UserSvcDto resultUserDto;
        try {
            registryUser = converterToRegistryUser(dto);
            registryUser.setCsmUserId(csmUserId);
            registryUser.setCountry(getCountryName(registryUser.getCountry()));
            registryUser.setState(USStateCode.valueOf(registryUser.getState()).getCode());
            // update the CSM user
            User csmUser = AccrualCsmUtil.getInstance().updateCSMUser(registryUser,
                loginName, StConverter.convertToString(dto.getPassword()));
            registryUser = PaRegistry.getRegisterUserService().updateUser(registryUser);
            registryUser.setCountry(getCountryCode(registryUser.getCountry()));
            registryUser.setState(USStateCode.getByCode(registryUser.getState()).getName());
            resultUserDto = converterToSvcDto(registryUser);
            resultUserDto.setIdentity(StConverter.convertToSt(csmUser.getLoginName()));
            resultUserDto.setPassword(StConverter.convertToSt(csmUser.getPassword()));
            resultUserDto.setTreatmentSiteIdentifier(checkPoOrganizationId(
                    resultUserDto.getTreatmentSiteIdentifier()));
            resultUserDto.setPhysicianIdentifier(checkPoPersonId(
                    resultUserDto.getPhysicianIdentifier()));
        } catch (RemoteException e) {
            throw new OutcomesException("Iso conversion exception in updateUser().", e);
        } catch (PAException e) {
            throw new OutcomesException("Exception while updating user.", e);
        }
        return resultUserDto;
    }
    private String getCountryCode(String countryName) throws RemoteException {
        List<Country> countries;
        try {
            countries = PaRegistry.getLookUpTableService().getCountries();
        } catch (PAException e) {
            throw new RemoteException("Exception while retrieving countries.", e);
        }

        for (int i = 0; i < countries.size(); i++) {
            Country country = countries.get(i);
            if (country.getName().equals(countryName)) {
                return country.getAlpha3();
            }
        }

        throw new RemoteException("Error while retrieving country code");
    }

    private String getCountryName(String countryCode) throws RemoteException {
        List<Country> countries;
        try {
            countries = PaRegistry.getLookUpTableService().getCountries();
        } catch (PAException e) {
            throw new RemoteException("Exception while retrieving countries.", e);
        }

        for (int i = 0; i < countries.size(); i++) {
            Country country = countries.get(i);
            if (country.getAlpha3().equals(countryCode)) {
                return country.getName();
            }
        }

        throw new RemoteException("Error while retrieving country name");
    }

    private void validatePhysicianAndTreatingSite(UserSvcDto dto) throws OutcomesException {
        Ii orgIi = IiConverter.convertToPoOrganizationIi(
                IiConverter.convertToString(dto.getTreatmentSiteIdentifier()));
        try {
            orgIi = checkPoOrganizationId(orgIi);
        } catch (RemoteException e) {
            orgIi = null;
        }
        if (PAUtil.isIiNull(orgIi)) {
            throw new OutcomesException("Treatment site identifier error.");
        }
        Ii perIi = IiConverter.convertToPoPersonIi(IiConverter.convertToString(dto.getPhysicianIdentifier()));
        try {
            perIi = checkPoPersonId(perIi);
        } catch (RemoteException e) {
            perIi = null;
        }
        if (PAUtil.isIiNull(perIi)) {
            throw new OutcomesException("Physician identifier error.");
        }
    }

    private Ii checkPoOrganizationId(Ii poOrganizationId) throws RemoteException {
        if (poOrganizationId == null) {
            return null;
        }

        OrganizationDTO organization;
        try {
            organization = PoRegistry.getOrganizationEntityService().getOrganization(poOrganizationId);
        } catch (NullifiedEntityException e) {
            return null;
        }

        return organization.getIdentifier();
    }

    private Ii checkPoPersonId(Ii poPersonId) throws RemoteException {
        if (poPersonId == null) {
            return null;
        }

        PersonDTO person;
        try {
            person = PoRegistry.getPersonEntityService().getPerson(poPersonId);
        } catch (NullifiedEntityException e) {
            return null;
        }

        return person.getIdentifier();
    }
    private UserSvcDto converterToSvcDto(RegistryUser bo) {
        UserSvcDto dto = new UserSvcDto();
        dto.setIdentifier(IiConverter.convertToIi(bo.getId()));
        dto.setFirstName(StConverter.convertToSt(bo.getFirstName()));
        if (bo.getMiddleName() != null) {
            dto.setMiddleName(StConverter.convertToSt(bo.getMiddleName()));
        }
        dto.setLastName(StConverter.convertToSt(bo.getLastName()));
        if (bo.getAddressLine() != null) {
            dto.setAddress(StConverter.convertToSt(bo.getAddressLine()));
        }
        if (bo.getCity() != null) {
            dto.setCity(StConverter.convertToSt(bo.getCity()));
        }
        dto.setState(StConverter.convertToSt(bo.getState()));
        if (bo.getPostalCode() != null) {
            dto.setPostalCode(StConverter.convertToSt(bo.getPostalCode()));
        }
        dto.setCountry(StConverter.convertToSt(bo.getCountry()));
        dto.setPhone(StConverter.convertToSt(bo.getPhone()));
        dto.setAffiliateOrg(StConverter.convertToSt(bo.getAffiliateOrg()));
        if (bo.getPrsOrgName() != null) {
            dto.setPrsOrg(StConverter.convertToSt(bo.getPrsOrgName()));
        }
        if (bo.getPoOrganizationId() != null) {
            dto.setTreatmentSiteIdentifier(IiConverter.convertToPoOrganizationIi(bo.getPoOrganizationId().toString()));
        }
        if (bo.getPoPersonId() != null) {
            dto.setPhysicianIdentifier(IiConverter.convertToPoPersonIi(bo.getPoPersonId().toString()));
        }
        return dto;
    }
    private RegistryUser converterToRegistryUser(UserSvcDto dto) {
        RegistryUser bo = new RegistryUser();
        if (!PAUtil.isIiNull(dto.getIdentifier())) {
            bo.setId(IiConverter.convertToLong(dto.getIdentifier()));
        }
        bo.setFirstName(StConverter.convertToString(dto.getFirstName()));
        if (!PAUtil.isStNull(dto.getMiddleName())) {
            bo.setMiddleName(StConverter.convertToString(dto.getMiddleName()));
        }
        bo.setLastName(StConverter.convertToString(dto.getLastName()));
        if (!PAUtil.isStNull(dto.getAddress())) {
            bo.setAddressLine(StConverter.convertToString(dto.getAddress()));
        }
        if (!PAUtil.isStNull(dto.getCity())) {
            bo.setCity(StConverter.convertToString(dto.getCity()));
        }
        bo.setState(StConverter.convertToString(dto.getState()));
        if (!PAUtil.isStNull(dto.getPostalCode())) {
            bo.setPostalCode(StConverter.convertToString(dto.getPostalCode()));
        }
        bo.setCountry(StConverter.convertToString(dto.getCountry()));
        bo.setPhone(StConverter.convertToString(dto.getPhone()));
        bo.setAffiliateOrg(StConverter.convertToString(dto.getAffiliateOrg()));
        if (!PAUtil.isStNull(dto.getPrsOrg())) {
            bo.setPrsOrgName(StConverter.convertToString(dto.getPrsOrg()));
        }
        bo.setPoOrganizationId(IiConverter.convertToLong(dto.getTreatmentSiteIdentifier()));
        bo.setPoPersonId(IiConverter.convertToLong(dto.getPhysicianIdentifier()));
        return bo;
    }
    /**
     *
     * @param country
     */
    private void validateCountry(String country) throws OutcomesException {
        List<Country> countries;
        try {
            countries = PaRegistry.getLookUpTableService().getCountries();
        } catch (PAException e) {
            throw new OutcomesException("Exception while retrieving countries.", e);
        }

        boolean countryFound = false;
        for (Country countryIterator : countries) {
           if (countryIterator.getAlpha3().equals(country)) {
               countryFound = true;
               break; // since match has been found
           }
        }
        if (!countryFound) {
            throw new OutcomesException("Invalid country specified");
        }
    }
    /**
     * @param userName name
     * @return dto
     * @throws OutcomesException e
     */
    public UserSvcDto getUser(St userName) throws OutcomesException {
        validateLoginName(userName);
        String loginName = StConverter.convertToString(userName);
        RegistryUser registryUser;
        UserSvcDto resultDto = null;
        try {
            registryUser = PaRegistry.getRegisterUserService().getUser(loginName);
            if (registryUser != null) { // user exists
                registryUser.setCountry(getCountryCode(registryUser.getCountry()));
                registryUser.setState(USStateCode.getByCode(registryUser.getState()).getName());
                resultDto = converterToSvcDto(registryUser);
                resultDto.setTreatmentSiteIdentifier(checkPoOrganizationId(resultDto.getTreatmentSiteIdentifier()));
                resultDto.setPhysicianIdentifier(checkPoPersonId(resultDto.getPhysicianIdentifier()));
                // get the CSM user
                User csmUser = AccrualCsmUtil.getInstance().getCSMUser(loginName);
                resultDto.setIdentity(StConverter.convertToSt(csmUser.getLoginName()));
                resultDto.setPassword(StConverter.convertToSt(csmUser.getPassword()));
            }
        } catch (RemoteException e) {
                throw new OutcomesException("Exception OutcomesUserSvc in getUser().", e);
        } catch (PAException e) {
            throw new OutcomesException("Exception while retrieving user.", e);
        }

        return resultDto;
    }
}
