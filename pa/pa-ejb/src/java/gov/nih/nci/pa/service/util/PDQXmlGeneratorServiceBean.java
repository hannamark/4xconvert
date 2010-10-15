/**
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
package gov.nih.nci.pa.service.util;

import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.domain.ClinicalResearchStaff;
import gov.nih.nci.pa.domain.Organization;
import gov.nih.nci.pa.domain.OrganizationalContact;
import gov.nih.nci.pa.domain.ResearchOrganization;
import gov.nih.nci.pa.enums.StudyContactRoleCode;
import gov.nih.nci.pa.enums.StudySiteContactRoleCode;
import gov.nih.nci.pa.enums.StudySiteFunctionalCode;
import gov.nih.nci.pa.iso.dto.StudyContactDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.StudyResourcingDTO;
import gov.nih.nci.pa.iso.dto.StudySiteContactDTO;
import gov.nih.nci.pa.iso.dto.StudySiteDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.DSetConverter;
import gov.nih.nci.pa.iso.util.EnOnConverter;
import gov.nih.nci.pa.iso.util.EnPnConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.HibernateSessionInterceptor;
import gov.nih.nci.pa.util.PAAttributeMaxLen;
import gov.nih.nci.pa.util.PoRegistry;
import gov.nih.nci.services.correlation.ClinicalResearchStaffDTO;
import gov.nih.nci.services.correlation.NullifiedRoleException;
import gov.nih.nci.services.correlation.OrganizationalContactDTO;
import gov.nih.nci.services.correlation.ResearchOrganizationDTO;
import gov.nih.nci.services.entity.NullifiedEntityException;
import gov.nih.nci.services.organization.OrganizationDTO;
import gov.nih.nci.services.person.PersonDTO;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Generates the xml representation of pdq data.
 * @author mshestopalov
 *
 */
@Stateless
@Interceptors(HibernateSessionInterceptor.class)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class PDQXmlGeneratorServiceBean extends CTGovXmlGeneratorServiceBean 
    implements PDQXmlGeneratorServiceRemote {

    private static final String NAME = "name";
    
    /**
     * {@inheritDoc}
     */
    @Override
    protected void addTrialSecondaryIdInfo(StudyProtocolDTO spDTO, Document doc, Element idInfoNode) {
        for (Ii item : spDTO.getSecondaryIdentifiers().getItem()) {
            if (IiConverter.STUDY_PROTOCOL_ROOT.equals(item.getRoot())) {
                continue;
            }
            Element secId = doc.createElement("secondary_id");
            XmlGenHelper.appendElement(secId, XmlGenHelper.createElement("id", item.getExtension(), doc));
            XmlGenHelper.appendElement(idInfoNode, secId);
        }
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    protected void addStudyOwnersInfo(StudyProtocolDTO spDTO, Document doc, Element root) 
        throws PAException {
        
        List<String> loginNames = getRegistryUserService().getTrialOwnerNames(
                Long.valueOf(spDTO.getIdentifier().getExtension()));
        
        Element studyOwners = doc.createElement("trial_owners");
        for (String item : loginNames) {
            XmlGenHelper.appendElement(studyOwners, XmlGenHelper.createElement(NAME, item, doc));
        }
        XmlGenHelper.appendElement(root, studyOwners);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    protected void addNciSpecificInfo(StudyProtocolDTO spDTO, Document doc, Element root) 
        throws PAException {
        StudyResourcingDTO srDTO = getStudyResourcingService()
        .getSummary4ReportedResourcing(spDTO.getIdentifier());
        
        Element nciSpecRoot = doc.createElement("nci_specific_information");
        XmlGenHelper.appendElement(nciSpecRoot, XmlGenHelper.createElement("reporting_data_set_method", 
                spDTO.getAccrualReportingMethodCode().getCode(), doc));
        XmlGenHelper.appendElement(nciSpecRoot, XmlGenHelper.createElement("summary_4_funding_category", 
                srDTO.getTypeCode().getCode(), doc));
        
        Element nciSpecFundSpons = doc.createElement("summary_4_funding_sponsor_source");
        
        OrganizationDTO poOrgDTO = getPoOrgDTOByPaOrgIi(srDTO.getOrganizationIdentifier()); 
        XmlGenHelper.appendElement(nciSpecFundSpons, 
                XmlGenHelper.createElement(NAME, StringUtils.substring(EnOnConverter
                .convertEnOnToString(poOrgDTO.getName()), 0,
                PAAttributeMaxLen.LEN_160), doc));
        XmlGenHelper.loadPoOrganization(poOrgDTO, nciSpecFundSpons, doc, null);
        XmlGenHelper.appendElement(nciSpecRoot, nciSpecFundSpons);
        
        XmlGenHelper.appendElement(nciSpecRoot, XmlGenHelper.createElement("program_code", 
                StConverter.convertToString(spDTO.getProgramCodeText()), doc));
        XmlGenHelper.appendElement(root, nciSpecRoot);
    }
    
    private ResearchOrganizationDTO getPoRODTOByPaRoIi(Ii paRoIi) 
        throws PAException {
        ResearchOrganization paRo = this.getCorUtils().getStructuralRoleByIi(paRoIi);
        Ii poRoIi = IiConverter.convertToPoResearchOrganizationIi(paRo.getIdentifier());
        ResearchOrganizationDTO roDTO;
        try {
            roDTO = PoRegistry.getResearchOrganizationCorrelationService().getCorrelation(poRoIi);
        } catch (NullifiedRoleException e) {
            throw new PAException(e);
        }
        return roDTO;
    }
    
    private OrganizationDTO getPoOrgDTOByPaOrgIi(Ii paOrgIi) 
    throws PAException {
        Organization paOrg = this.getCorUtils().getPAOrganizationByIi(paOrgIi);
        Ii poOrgIi = IiConverter.convertToPoOrganizationIi(paOrg.getIdentifier());
        OrganizationDTO orgDTO;
        try {
            orgDTO = PoRegistry.getOrganizationEntityService().getOrganization(poOrgIi);
        } catch (NullifiedEntityException e) {
            throw new PAException(e);
        }
        return orgDTO;
    }
    
    private OrganizationalContactDTO getPoOCDTOByPaOcIi(Ii paOcIi) 
    throws PAException {
        OrganizationalContact paOc = this.getCorUtils().getStructuralRoleByIi(paOcIi);
        Ii poOcIi = IiConverter.convertToPoOrganizationalContactIi(paOc.getIdentifier());
        OrganizationalContactDTO ocDTO;
        try {
            ocDTO = PoRegistry.getOrganizationalContactCorrelationService().getCorrelation(poOcIi);
        } catch (NullifiedRoleException e) {
            throw new PAException(e);
        }
        return ocDTO;
    }
    
    private ClinicalResearchStaffDTO getPoCrsDTOByPaCrsIi(Ii paCrsIi) 
        throws PAException {
        ClinicalResearchStaff paCrs = this.getCorUtils().getStructuralRoleByIi(paCrsIi);
        ClinicalResearchStaffDTO crsDTO;
        try {
            crsDTO = PoRegistry.getClinicalResearchStaffCorrelationService().getCorrelation(
                    IiConverter.convertToPoClinicalResearchStaffIi(paCrs.getIdentifier()));
        } catch (NullifiedRoleException e) {
            throw new PAException(e);
        }
        return crsDTO;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    protected Element createLeadSponsor(Ii studyProtocolIi, Document doc) 
        throws PAException {
       
        Ii paRoIi = getOrgCorrelationService().getROByFunctionRole(studyProtocolIi, CdConverter
                        .convertToCd(StudySiteFunctionalCode.SPONSOR));  
        Element lead = doc.createElement("lead_sponsor");
        addPoOrganizationByPaRoIi(lead, null, paRoIi, doc);
       
        return lead;
    }
    
    private void addPoOrganizationByPaRoIi(Element root, String childName, Ii paRoIi, Document doc) 
        throws PAException {
        ResearchOrganizationDTO roDTO = getPoRODTOByPaRoIi(paRoIi);
        if (roDTO == null) {
            return;
        }
        OrganizationDTO orgDTO;
        try {
            orgDTO = PoRegistry.getOrganizationEntityService()
                .getOrganization(roDTO.getPlayerIdentifier());
        } catch (NullifiedEntityException e) {
            throw new PAException(e);
        }
        Ii roCtepId = DSetConverter
            .getFirstInDSetByRoot(roDTO.getIdentifier(), IiConverter.CTEP_ORG_IDENTIFIER_ROOT);
        Element child = null;
        if (StringUtils.isEmpty(childName)) {
            child = root;
        } else {
            child = doc.createElement(childName);
        }
        XmlGenHelper.appendElement(child, XmlGenHelper.createElement(NAME, StringUtils.substring(EnOnConverter
                .convertEnOnToString(orgDTO.getName()), 0,
                PAAttributeMaxLen.LEN_160), doc));
        XmlGenHelper.loadPoOrganization(orgDTO, child, doc, roCtepId);
        if (!StringUtils.isEmpty(childName)) {
           XmlGenHelper.appendElement(root, child);
        }
    }
    
    private void addPoPersonByPaCrsIi(Element root, String childName, Ii paCrsIi, Document doc) 
        throws PAException {
        ClinicalResearchStaffDTO crsDTO = getPoCrsDTOByPaCrsIi(paCrsIi);
        if (crsDTO == null) {
            return;
        }
        PersonDTO perDTO;
        try {
            perDTO = PoRegistry.getPersonEntityService().getPerson(crsDTO.getPlayerIdentifier());
        } catch (NullifiedEntityException e) {
            throw new PAException(e);
        }
        Ii crsCtepId = DSetConverter
        .getFirstInDSetByRoot(crsDTO.getIdentifier(), IiConverter.CTEP_PERSON_IDENTIFIER_ROOT);
        Element child = null;
        if (StringUtils.isEmpty(childName)) {
            child = root;
        } else {
            child = doc.createElement(childName);
        }
        XmlGenHelper.appendElement(child, XmlGenHelper.createElement(NAME, StringUtils.substring(EnPnConverter
                .convertToLastCommaFirstName(perDTO.getName()), 0,
                PAAttributeMaxLen.LEN_160), doc));
        XmlGenHelper.loadPoPerson(perDTO, child, doc, crsCtepId);
        if (!StringUtils.isEmpty(childName)) {
           XmlGenHelper.appendElement(root, child);
        }
    }
    
    private void addPoOrganizationalContactByPaOcIi(Element root, String childName, Ii paOcIi, Document doc) 
    throws PAException {
        OrganizationalContactDTO ocDTO = getPoOCDTOByPaOcIi(paOcIi);
        if (ocDTO == null) {
            return;
        }
        Ii ocCtepId = DSetConverter
        .getFirstInDSetByRoot(ocDTO.getIdentifier(), IiConverter.CTEP_PERSON_IDENTIFIER_ROOT);
        Element child = null;
        if (StringUtils.isEmpty(childName)) {
            child = root;
        } else {
            child = doc.createElement(childName);
        }
        XmlGenHelper.appendElement(child, XmlGenHelper.createElement("name", StringUtils.substring(
                StConverter.convertToString(ocDTO.getTitle()), 0,
                PAAttributeMaxLen.LEN_160), doc));
        XmlGenHelper.loadPoOrgContact(ocDTO, child, doc, ocCtepId);
        if (!StringUtils.isEmpty(childName)) {
           XmlGenHelper.appendElement(root, child);
        }
}
    
    /**
     * {@inheritDoc}
     */
    @Override
    protected Element createResponsibleParty(Ii studyProtocolIi, Document doc)
    throws PAException, NullifiedRoleException {
        Element responsibleParty = doc.createElement("resp_party");
        StudyContactDTO scDto = new StudyContactDTO();
        scDto.setRoleCode(CdConverter.convertToCd(StudyContactRoleCode.RESPONSIBLE_PARTY_STUDY_PRINCIPAL_INVESTIGATOR));
        List<StudyContactDTO> scDtos = getStudyContactService().getByStudyProtocol(studyProtocolIi, scDto);
       
        if (CollectionUtils.isNotEmpty(scDtos)) {
            
            addPoPersonByPaCrsIi(responsibleParty, 
                    "resp_party_person", scDtos.get(0).getClinicalResearchStaffIi(), doc); 
            
            StudySiteDTO spartDTO = new StudySiteDTO();
            spartDTO.setFunctionalCode(CdConverter.convertToCd(StudySiteFunctionalCode.LEAD_ORGANIZATION));
            List<StudySiteDTO> sParts = getStudySiteService().getByStudyProtocol(studyProtocolIi, spartDTO);
            
            addPoOrganizationByPaRoIi(responsibleParty, 
                    "resp_party_organization", sParts.get(0).getResearchOrganizationIi(), doc); 
         
        } else {
           
            StudySiteContactDTO spart = new StudySiteContactDTO();
            spart.setRoleCode(CdConverter.convertToCd(StudySiteContactRoleCode.RESPONSIBLE_PARTY_SPONSOR_CONTACT));
            List<StudySiteContactDTO> spcDtos = getStudySiteContactService()
                .getByStudyProtocol(studyProtocolIi, spart);
            if (CollectionUtils.isNotEmpty(spcDtos)) {
                spart = spcDtos.get(0);
                addPoOrganizationalContactByPaOcIi(responsibleParty, "resp_party_generic_contact", 
                        spart.getOrganizationalContactIi(), doc);
            }
            StudySiteDTO spDto = new StudySiteDTO();
            spDto.setFunctionalCode(CdConverter.convertToCd(StudySiteFunctionalCode.SPONSOR));
            List<StudySiteDTO> ssDtos = getStudySiteService().getByStudyProtocol(studyProtocolIi, spDto);
            if (CollectionUtils.isNotEmpty(ssDtos)) {
                spDto = ssDtos.get(0);
                addPoOrganizationByPaRoIi(responsibleParty, 
                        "resp_party_organization", spDto.getResearchOrganizationIi(), doc); 
            }

        }
     
        return responsibleParty;
    }
    
   

    /**
     * {@inheritDoc}
     */
    public String generatePdqXml(Ii studyProtocolIi) throws PAException {
        return super.generateCTGovXml(studyProtocolIi);
    }
    
  
}
