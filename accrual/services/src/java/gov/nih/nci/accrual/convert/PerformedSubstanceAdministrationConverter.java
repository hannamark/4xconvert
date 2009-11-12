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
package gov.nih.nci.accrual.convert;

import gov.nih.nci.accrual.dto.PerformedSubstanceAdministrationDto;
import gov.nih.nci.coppa.iso.Pq;
import gov.nih.nci.pa.domain.PerformedSubstanceAdministration;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.util.PAUtil;

import java.util.zip.DataFormatException;

/**
 * @author Kalpana Guthikonda
 * @since 11/10/2009
 */
@SuppressWarnings({"PMD.CyclomaticComplexity", "PMD.NPathComplexity" })
public class PerformedSubstanceAdministrationConverter extends PerformedActivityConverter {

    /**
     * Convert from domain to dto.
     * @param bo the bo
     * @return the performed substance administration dto
     * @throws DataFormatException the data format exception
     */
    public static PerformedSubstanceAdministrationDto convertFromDomainToDto(PerformedSubstanceAdministration bo)
            throws DataFormatException {
        PerformedSubstanceAdministrationDto dto = (PerformedSubstanceAdministrationDto)
        PerformedActivityConverter.convertFromDomainToDTO(bo, new PerformedSubstanceAdministrationDto());
        
        Pq dose  = new Pq();
        dose.setValue(bo.getDoseValue());
        if (bo.getDoseUnit() != null) {
            dose.setUnit(bo.getDoseUnit());
        }
        dto.setDose(dose);
        dto.setDoseDescription(StConverter.convertToSt(bo.getDoseDescription()));
        dto.setDoseFormCode(CdConverter.convertStringToCd(bo.getDoseFormCode()));
        dto.setDoseFrequencyCode(CdConverter.convertStringToCd(bo.getDoseFrequencyCode()));
        dto.setDoseRegimen(StConverter.convertToSt(bo.getDoseRegimen()));
        
        Pq doseTotal  = new Pq();
        doseTotal.setValue(bo.getDoseTotalValue());
        if (bo.getDoseTotalUnit() != null) {
            doseTotal.setUnit(bo.getDoseTotalUnit());
        }
        dto.setDoseTotal(doseTotal);
        dto.setRouteOfAdministrationCode(CdConverter.convertStringToCd(bo.getRouteOfAdministrationCode()));
        
        Pq doseDuration = new Pq();
        doseDuration.setValue(bo.getDoseDurationValue());
        if (bo.getDoseDurationUnit() != null) {
            doseDuration.setUnit(bo.getDoseDurationUnit());
        }
        dto.setDoseDuration(doseDuration);
        dto.setDoseModificationType(CdConverter.convertStringToCd(bo.getDoseModificationType()));
        return dto;
    }

    /**
     * Convert from dto to domain.
     * @param dto the dto
     * @return the performed substance administration
     * @throws DataFormatException the data format exception
     */
    public static PerformedSubstanceAdministration convertFromDtoToDomain(PerformedSubstanceAdministrationDto dto)
    throws DataFormatException {
        PerformedSubstanceAdministration bo = (PerformedSubstanceAdministration)
        PerformedActivityConverter.convertFromDTOToDomain(dto , new PerformedSubstanceAdministration());      

        if (!PAUtil.isStNull(dto.getDoseDescription())) {
            bo.setDoseDescription(StConverter.convertToString(dto.getDoseDescription()));
        }
        if (!PAUtil.isStNull(dto.getDoseRegimen())) {
            bo.setDoseRegimen(StConverter.convertToString(dto.getDoseRegimen()));
        } 
        if (!PAUtil.isCdNull(dto.getDoseFormCode())) {
            bo.setDoseFormCode(CdConverter.convertCdToString(dto.getDoseFormCode()));
        }
        if (!PAUtil.isCdNull(dto.getDoseFrequencyCode())) {
            bo.setDoseFrequencyCode(CdConverter.convertCdToString(dto.getDoseFrequencyCode())); 
        }
        if (!PAUtil.isCdNull(dto.getRouteOfAdministrationCode())) {
            bo.setRouteOfAdministrationCode(CdConverter.convertCdToString(dto.getRouteOfAdministrationCode()));
        } 
        if (!PAUtil.isCdNull(dto.getDoseModificationType())) {
            bo.setDoseModificationType(CdConverter.convertCdToString(dto.getDoseModificationType()));
        }
        if (dto.getDose() != null) {   
            if (!PAUtil.isPqValueNull(dto.getDose())) {
                bo.setDoseValue(dto.getDose().getValue());
            }
            if (!PAUtil.isPqValueNull(dto.getDose())) {
                bo.setDoseUnit(dto.getDose().getUnit());
            }
        }
        if (dto.getDoseTotal() != null) {
            if (!PAUtil.isPqValueNull(dto.getDoseTotal())) {
                bo.setDoseTotalValue(dto.getDoseTotal().getValue());
            }
            if (!PAUtil.isPqUnitNull(dto.getDoseTotal())) {
                bo.setDoseTotalUnit(dto.getDoseTotal().getUnit());    
            }
        }
        if (dto.getDoseDuration() != null) {
            if (!PAUtil.isPqValueNull(dto.getDoseDuration())) {
                bo.setDoseDurationValue(dto.getDoseDuration().getValue());
            }
            if (!PAUtil.isPqUnitNull(dto.getDoseDuration())) {
                bo.setDoseDurationUnit(dto.getDoseDuration().getUnit());
            }
        }
        return bo;
    }
}
