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
package gov.nih.nci.pa.iso.convert;

import gov.nih.nci.coppa.iso.DSet;
import gov.nih.nci.coppa.iso.Tel;
import gov.nih.nci.pa.domain.ClinicalResearchStaff;
import gov.nih.nci.pa.domain.HealthCareProvider;
import gov.nih.nci.pa.domain.OrganizationalContact;
import gov.nih.nci.pa.domain.StudyParticipation;
import gov.nih.nci.pa.domain.StudyParticipationContact;
import gov.nih.nci.pa.domain.StudyProtocol;
import gov.nih.nci.pa.enums.FunctionalRoleStatusCode;
import gov.nih.nci.pa.iso.dto.StudyParticipationContactDTO;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.DSetConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.PAUtil;

import java.util.ArrayList;

/**
 * Convert StudyParticipationContact domain to DTO.
 *
 * @author Hugh Reinhart, Harsha
 * @since 09/23/2008
 * copyright NCI 2008.  All rights reserved.
 * This code may not be used without the express written permission of the
 * copyright holder, NCI.
 */
@SuppressWarnings("PMD.CyclomaticComplexity")
public class StudyParticipationContactConverter extends 
gov.nih.nci.pa.iso.convert.AbstractConverter< StudyParticipationContactDTO,  StudyParticipationContact> {
    /**
     *
     * @param bo StudyProtocol domain object
     * @return dto
     * @throws PAException PAException
     */
    @Override
    public StudyParticipationContactDTO convertFromDomainToDto(
            StudyParticipationContact bo) throws PAException {
        if (bo == null) {
            throw new PAException("Tried to pass null as argument to converter "
                    + "StudyParticipationContactDTO.convertFromDomainToDTO().  ");
        }
        StudyParticipationContactDTO dto = new StudyParticipationContactDTO();
        dto.setIdentifier(IiConverter.convertToIi(bo.getId()));
//        dto.setPostalAddress(AddressConverterUtil.create(bo.getAddressLine(), bo.getDeliveryAddressLine(),
//                bo.getCity(), bo.getState(), bo.getPostalCode(),
//                (bo.getCountry() == null) ? "" : bo.getCountry().getAlpha3()));
        dto.setPrimaryIndicator(BlConverter.convertToBl(bo.getPrimaryIndicator()));
        dto.setRoleCode(CdConverter.convertToCd(bo.getRoleCode()));
        dto.setStatusCode(CdConverter.convertToCd(bo.getStatusCode()));
        dto.setStatusDateRangeLow(TsConverter.convertToTs(bo.getStatusDateRangeLow()));
        if (bo.getStudyParticipation() != null) {
            dto.setStudyParticipationIi(IiConverter.convertToIi(bo.getStudyParticipation().getId()));
        }
        if (bo.getStudyProtocol() != null) {
            dto.setStudyProtocolIdentifier(IiConverter.converToStudyProtocolIi(bo.getStudyProtocol().getId()));
        }
        if (bo.getHealthCareProvider() != null) {
            dto.setHealthCareProviderIi(IiConverter.convertToIi(bo.getHealthCareProvider().getId()));
        }
        if (bo.getClinicalResearchStaff() != null) {
            dto.setClinicalResearchStaffIi(IiConverter.convertToIi(bo.getClinicalResearchStaff().getId()));
        }
        if (bo.getOrganizationalContact() != null) {
            dto.setOrganizationalContactIi(IiConverter.convertToIi(bo.getOrganizationalContact().getId()));
        }

        DSet<Tel> telAddresses = new DSet<Tel>();
        ArrayList<String> emailList = new ArrayList<String>();
        emailList.add(bo.getEmail());
        DSetConverter.convertListToDSet(emailList, "EMAIL", telAddresses);
        ArrayList<String> telList = new ArrayList<String>();
        telList.add(bo.getPhone());
        DSetConverter.convertListToDSet(telList, "PHONE", telAddresses);
        dto.setTelecomAddresses(telAddresses);
        return dto;
    }

    /**
     * Create a new domain object from a given dto.
     * @param dto StudyParticipationContactDTO
     * @return StudyProtocol StudyProtocol
     * @throws PAException PAException
     */
    @Override
    public StudyParticipationContact convertFromDtoToDomain(
            StudyParticipationContactDTO dto) throws PAException {
        return convertFromDtoToDomain(dto , new StudyParticipationContact());
    }


    /**
     * Create a new domain object from a given dto.
     * @param dto StudyParticipationContactDTO
     * @param bo StudyParticipationContact
     * @return StudyProtocol StudyProtocol
     * @throws PAException PAException
     */
    public StudyParticipationContact convertFromDtoToDomain(
            StudyParticipationContactDTO dto , StudyParticipationContact bo) throws PAException {
        
        bo.setId(IiConverter.convertToLong(dto.getIdentifier()));
        bo.setPrimaryIndicator(BlConverter.covertToBoolean(dto.getPrimaryIndicator()));
       // bo.setRoleCode(StudyParticipationContactRoleCode.getByCode(dto.getRoleCode().getCode()));
        bo.setRoleCode(gov.nih.nci.pa.enums.StudyParticipationContactRoleCode.getByCode(
                dto.getRoleCode().getCode()));        
        bo.setStatusCode(FunctionalRoleStatusCode.getByCode(dto.getStatusCode().getCode()));
        bo.setStatusDateRangeLow(TsConverter.convertToTimestamp(dto.getStatusDateRangeLow()));
        
        if (dto.getTelecomAddresses() != null) {
            bo.setEmail(DSetConverter.getFirstElement(dto.getTelecomAddresses(), "EMAIL"));
            bo.setPhone(DSetConverter.getFirstElement(dto.getTelecomAddresses(), "PHONE"));
        }

        if (!PAUtil.isIiNull(dto.getClinicalResearchStaffIi())) {
            ClinicalResearchStaff crs = new ClinicalResearchStaff();
            crs.setId(IiConverter.convertToLong(dto.getClinicalResearchStaffIi()));
            bo.setClinicalResearchStaff(crs);
        }

        if (!PAUtil.isIiNull(dto.getHealthCareProviderIi())) {
            HealthCareProvider healthCareProvider = new HealthCareProvider();
            healthCareProvider.setId(IiConverter.convertToLong(dto.getHealthCareProviderIi()));
            bo.setHealthCareProvider(healthCareProvider);
        }
        
        if (!PAUtil.isIiNull(dto.getOrganizationalContactIi())) {
            OrganizationalContact oc = new OrganizationalContact();
            oc.setId(IiConverter.convertToLong(dto.getOrganizationalContactIi()));
            bo.setOrganizationalContact(oc);
        }
        
        StudyProtocol protocolBo = new StudyProtocol();
        protocolBo.setId(IiConverter.convertToLong(dto.getStudyProtocolIdentifier()));
        bo.setStudyProtocol(protocolBo);
        
        StudyParticipation participationBo = new StudyParticipation();
        participationBo.setId(IiConverter.convertToLong(dto.getStudyParticipationIi()));
        bo.setStudyParticipation(participationBo);
        
        return bo;
    }

}
