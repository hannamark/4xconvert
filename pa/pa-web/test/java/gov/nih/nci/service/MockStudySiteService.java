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
package gov.nih.nci.service;

import gov.nih.nci.coppa.services.LimitOffset;
import gov.nih.nci.coppa.services.TooManyResultsException;
import gov.nih.nci.iso21090.Cd;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.domain.HealthCareFacility;
import gov.nih.nci.pa.domain.Organization;
import gov.nih.nci.pa.domain.ResearchOrganization;
import gov.nih.nci.pa.domain.StudySite;
import gov.nih.nci.pa.enums.StudySiteFunctionalCode;
import gov.nih.nci.pa.iso.convert.StudySiteConverter;
import gov.nih.nci.pa.iso.dto.StudySiteDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.StudySiteServiceLocal;
import gov.nih.nci.po.data.CurationException;
import gov.nih.nci.po.service.EntityValidationException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author hreinhart
 *
 */
public class MockStudySiteService  extends MockAbstractBaseIsoService <StudySiteDTO> 
    implements StudySiteServiceLocal {

    public static List<StudySite> list;
    static StudySiteConverter converter = new StudySiteConverter();
    private static Long seq = 1L;

    static {
        list = new ArrayList<StudySite>();
        StudySite ss = new StudySite();
        ss.setId(seq++);
        ss.setStudyProtocol(MockStudyProtocolService.list.get(0));
        ss.setFunctionalCode(StudySiteFunctionalCode.LEAD_ORGANIZATION);
        ss.setLocalStudyProtocolIdentifier("LSPID 001");
        list.add(ss);
        ss = new StudySite();
        ss.setId(seq++);
        ss.setStudyProtocol(MockStudyProtocolService.list.get(0));
        ss.setFunctionalCode(StudySiteFunctionalCode.FUNDING_SOURCE);
        ss.setLocalStudyProtocolIdentifier("LSPID 002");
        ResearchOrganization researchOrg = new ResearchOrganization();
        researchOrg.setId(1L);
        ss.setResearchOrganization(researchOrg);
        list.add(ss);
        ss = new StudySite();
        ss.setId(seq++);
        ss.setStudyProtocol(MockStudyProtocolService.list.get(0));
        ss.setFunctionalCode(StudySiteFunctionalCode.TREATING_SITE);
        ss.setLocalStudyProtocolIdentifier("LSPID 003");
        HealthCareFacility hcf = new HealthCareFacility();
        hcf.setId(1L);
        Organization org = new Organization();
        org.setName("test org. name");
        org.setId(1L);
        hcf.setOrganization(org);
        ss.setHealthCareFacility(hcf);
        list.add(ss);
    }

    /**
     * @param studyProtocolIi
     * @param spDTOList
     * @return
     * @throws PAException
     */
    public List<StudySiteDTO> getByStudyProtocol(Ii studyProtocolIi,
            List<StudySiteDTO> spDTOList) throws PAException {
        List<StudySiteDTO> resultList = new ArrayList<StudySiteDTO>();
        for (StudySite sp : list) {
            if (sp.getStudyProtocol().getId().equals(IiConverter.convertToLong(studyProtocolIi))) {
                for (StudySiteDTO criteria : spDTOList) {
                    if (criteria.getFunctionalCode().getCode().equals(sp.getFunctionalCode().getCode())) {
                        resultList.add(converter.convertFromDomainToDto(sp));
                        break;
                    }
                }
            }
        }
        return resultList;
    }

    /**
     * @param studyProtocolIi
     * @param spDTO
     * @return
     * @throws PAException
     */
    public List<StudySiteDTO> getByStudyProtocol(Ii studyProtocolIi,
            StudySiteDTO spDTO) throws PAException {
        List<StudySiteDTO> criteria = new ArrayList<StudySiteDTO>();
        criteria.add(spDTO);
        return getByStudyProtocol(studyProtocolIi, criteria);
    }

    /**
     * @param ii
     * @return
     * @throws PAException
     */
    public List<StudySiteDTO> getByStudyProtocol(Ii ii)
            throws PAException {
        List<StudySiteDTO> resultList = new ArrayList<StudySiteDTO>();
        for (StudySite sp : list) {
            if (sp.getId() == IiConverter.convertToLong(ii)) {
                resultList.add(converter.convertFromDomainToDto(sp));
            }
        }
        return resultList;
    }

    public StudySiteDTO getCurrentByStudyProtocol(Ii studyProtocolIi) throws PAException {
        List<StudySiteDTO> dtoList = this.getByStudyProtocol(studyProtocolIi);
        StudySiteDTO result = null;
        if (!dtoList.isEmpty()) {
            result = dtoList.get(dtoList.size() - 1);
        }
        return result;
    }
    /**
     * @param dto
     * @return
     * @throws PAException
     */
    @Override
    public StudySiteDTO create(StudySiteDTO dto) throws PAException {
        StudySite bo = converter.convertFromDtoToDomain(dto);
        bo.setId(seq++);
        list.add(bo);
        return converter.convertFromDomainToDto(bo);
    }

    /**
     * @param ii
     * @throws PAException
     */
    @Override
    public void delete(Ii ii) throws PAException {
        // TODO Auto-generated method stub

    }

    /**
     * @param ii
     * @return
     * @throws PAException
     */
    @Override
    public StudySiteDTO get(Ii ii) throws PAException {
        StudySiteDTO dto = new StudySiteDTO();
        dto.setResearchOrganizationIi(IiConverter.convertToIi("1"));
        dto.setFunctionalCode(CdConverter.convertStringToCd("functionalCode"));
        dto.setStatusCode(CdConverter.convertStringToCd("statusCode"));
        return dto;
    }

    /**
     * @param dto
     * @return
     * @throws PAException
     */
    @Override
    public StudySiteDTO update(StudySiteDTO dto)
            throws PAException {
        // TODO Auto-generated method stub
        return null;
    }

    public Map<Ii,Ii> copy(Ii fromStudyProtocolii, Ii toStudyProtocolIi)
            throws PAException {
        // TODO Auto-generated method stub
        return null;
    }

    public void cascadeRoleStatus(Ii ii, Cd roleStatusCode) throws PAException {
        // TODO Auto-generated method stub

    }

    public List<StudySiteDTO> search(StudySiteDTO dto, LimitOffset pagingParams)
            throws PAException, TooManyResultsException {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see gov.nih.nci.pa.service.StudySiteService#getStudySiteIiByTrialAndPoHcfIi(gov.nih.nci.iso21090.Ii, gov.nih.nci.iso21090.Ii)
     */
    public Ii getStudySiteIiByTrialAndPoHcfIi(Ii studyProtocolIi, Ii poHcfIi) throws EntityValidationException,
            CurationException, PAException, TooManyResultsException {
        return IiConverter.convertToStudySiteIi(1L);
    }

}
