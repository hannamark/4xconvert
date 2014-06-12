/**
 * The software subject to this notice and license includes both human readable
 * source code form and machine readable, binary, object code form. The CoppaPO
 * Software was developed in conjunction with the National Cancer Institute 
 * (NCI) by NCI employees and 5AM Solutions, Inc. (5AM). To the extent 
 * government employees are authors, any rights in such works shall be subject 
 * to Title 17 of the United States Code, section 105. 
 *
 * This CoppaPO Software License (the License) is between NCI and You. You (or 
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
 * its rights in the CoppaPO Software to (i) use, install, access, operate, 
 * execute, copy, modify, translate, market, publicly display, publicly perform,
 * and prepare derivative works of the CoppaPO Software; (ii) distribute and 
 * have distributed to and by third parties the CoppaPO Software and any 
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
package gov.nih.nci.po.webservices.convert.bridg;

import gov.nih.nci.coppa.po.ClinicalResearchStaff;
import gov.nih.nci.coppa.po.Correlation;
import gov.nih.nci.coppa.po.CorrelationType;
import gov.nih.nci.coppa.po.Entity;
import gov.nih.nci.coppa.po.EntityType;
import gov.nih.nci.coppa.po.HealthCareFacility;
import gov.nih.nci.coppa.po.HealthCareProvider;
import gov.nih.nci.coppa.po.IdentifiedOrganization;
import gov.nih.nci.coppa.po.IdentifiedPerson;
import gov.nih.nci.coppa.po.Organization;
import gov.nih.nci.coppa.po.OrganizationalContact;
import gov.nih.nci.coppa.po.OversightCommittee;
import gov.nih.nci.coppa.po.Person;
import gov.nih.nci.coppa.po.ResearchOrganization;
import gov.nih.nci.iso21090.grid.dto.transform.DtoTransformException;
import gov.nih.nci.services.CorrelationDto;
import gov.nih.nci.services.EntityDto;
import gov.nih.nci.services.correlation.ClinicalResearchStaffDTO;
import gov.nih.nci.services.correlation.HealthCareFacilityDTO;
import gov.nih.nci.services.correlation.HealthCareProviderDTO;
import gov.nih.nci.services.correlation.IdentifiedOrganizationDTO;
import gov.nih.nci.services.correlation.IdentifiedPersonDTO;
import gov.nih.nci.services.correlation.OrganizationalContactDTO;
import gov.nih.nci.services.correlation.OversightCommitteeDTO;
import gov.nih.nci.services.correlation.ResearchOrganizationDTO;
import gov.nih.nci.services.organization.OrganizationDTO;
import gov.nih.nci.services.person.PersonDTO;

/**
 * Helps Entity and Correlation Node transformers to same ops.
 * @author mshestopalov
 *
 */
@SuppressWarnings("PMD")
public class NodeTransformHelper {
    
    /**
     * Help transform a list of correlations into an array or correlation dtos.
     * @param corrList correlations.
     * @return array or correlation dtos
     * @throws DtoTransformException if error during transform.
     */
    public static CorrelationDto[] toDtoCorrelations(CorrelationType corrList) throws DtoTransformException {
        if (corrList == null || corrList.getContent().isEmpty()) {
            return null;
        }
        CorrelationDto[] returnVal = new CorrelationDto[corrList.getContent().size()];
        int i = 0;
        for (Object corr : corrList.getContent()) {
            CorrelationDto temp = toDtoCorrelation((Correlation) corr);
            if (temp != null) {
                returnVal[i++] = temp;
            }
        }
        return returnVal;
    }
    
    /**
     * Help transform a correlation into a correlation Dto.
     * @param corr correlation.
     * @return correlation dto.
     * @throws DtoTransformException if error during transform.
     */
    public static CorrelationDto toDtoCorrelation(Correlation corr) throws DtoTransformException {
        if (corr == null) {
            return null;
        } else if (corr instanceof HealthCareFacility) {
            return HealthCareFacilityTransformer.INSTANCE.toDto((HealthCareFacility) corr);
        } else if (corr instanceof HealthCareProvider) {
            return HealthCareProviderTransformer.INSTANCE.toDto((HealthCareProvider) corr);
        } else if (corr instanceof IdentifiedOrganization) {
            return IdentifiedOrganizationTransformer.INSTANCE.toDto((IdentifiedOrganization) corr);
        } else if (corr instanceof IdentifiedPerson) {
            return IdentifiedPersonTransformer.INSTANCE.toDto((IdentifiedPerson) corr);
        } else if (corr instanceof OrganizationalContact) {
            return OrganizationalContactTransformer.INSTANCE.toDto((OrganizationalContact) corr);
        } else if (corr instanceof ClinicalResearchStaff) {
            return ClinicalResearchStaffTransformer.INSTANCE.toDto((ClinicalResearchStaff) corr);
        } else if (corr instanceof OversightCommittee) {
            return OversightCommitteeTransformer.INSTANCE.toDto((OversightCommittee) corr);
        } else if (corr instanceof ResearchOrganization) {
            return ResearchOrganizationTransformer.INSTANCE.toDto((ResearchOrganization) corr);
        } 
        
        return null;
    }
    
    /**
     * Help transform a CorrelationType object with 1 item to a CorrelationDto.
     * @param corrType type object.
     * @return dto.
     * @throws DtoTransformException exception thrown if transform error.
     */
    public static CorrelationDto typeToDtoCorrelation(CorrelationType corrType) throws DtoTransformException {
        if (corrType != null && !corrType.getContent().isEmpty()) {
            return NodeTransformHelper.toDtoCorrelation(
                (Correlation) corrType.getContent().get(0));
        }
        
        return null;
    }
    
    /**
     * Help transform an array of correlation dtos into a list of correlations.
     * @param corrList array of correlation dtos
     * @return list of correlations
     * @throws DtoTransformException if error during transform
     */
    public static CorrelationType toXmlCorrelations(CorrelationDto[] corrList) throws DtoTransformException {
        if (corrList == null || corrList.length == 0) {
            return null;
        }
        CorrelationType returnVal = new CorrelationType();
        for (CorrelationDto corr : corrList) {
            Correlation temp = toXmlCorrelation(corr);
            if (temp != null) {
                returnVal.getContent().add(temp);
            }
        }
        return returnVal;
    }
    
    /**
     * Help transform correlation dto into correlation.
     * @param corr correlation dto.
     * @return correlation.
     * @throws DtoTransformException if there is an error during transform.
     */
    public static Correlation toXmlCorrelation(CorrelationDto corr) throws DtoTransformException {
        if (corr == null) {
            return null;
        } else if (corr instanceof HealthCareFacilityDTO) {
            return HealthCareFacilityTransformer.INSTANCE.toXml((HealthCareFacilityDTO) corr);
        } else if (corr instanceof HealthCareProviderDTO) {
            return HealthCareProviderTransformer.INSTANCE.toXml((HealthCareProviderDTO) corr);
        } else if (corr instanceof IdentifiedOrganizationDTO) {
            return IdentifiedOrganizationTransformer.INSTANCE.toXml((IdentifiedOrganizationDTO) corr);
        } else if (corr instanceof IdentifiedPersonDTO) {
            return IdentifiedPersonTransformer.INSTANCE.toXml((IdentifiedPersonDTO) corr);
        } else if (corr instanceof OrganizationalContactDTO) {
            return OrganizationalContactTransformer.INSTANCE.toXml((OrganizationalContactDTO) corr);
        } else if (corr instanceof ClinicalResearchStaffDTO) {
            return ClinicalResearchStaffTransformer.INSTANCE.toXml((ClinicalResearchStaffDTO) corr);
        } else if (corr instanceof OversightCommitteeDTO) {
            return OversightCommitteeTransformer.INSTANCE.toXml((OversightCommitteeDTO) corr);
        } else if (corr instanceof ResearchOrganizationDTO) {
            return ResearchOrganizationTransformer.INSTANCE.toXml((ResearchOrganizationDTO) corr);
        }
        
        return null;
    }
    
    /**
     * Help transform correlation dto to correlation.
     * @param corrDto dto.
     * @return correlation type.
     * @throws DtoTransformException exception thrown if transform exception.
     */
    public static CorrelationType toXmlCorrelationType(CorrelationDto corrDto) throws DtoTransformException {
        Correlation corr = toXmlCorrelation(corrDto);
        if (corr != null) {
            CorrelationType corrType = new CorrelationType();
            corrType.getContent().add(corr);
            return corrType;
        }
        return null;
    }
    
    /**
     * Help transform entity into an entity dto.
     * @param content the entity.
     * @return entity dto.
     * @throws DtoTransformException if there is an error during transform.
     */
    public static EntityDto toDtoEntity(EntityType content) throws DtoTransformException {
        if (content == null || content.getContent() == null || content.getContent().isEmpty()) {
            return null;
        }
        
        Entity entity = (Entity) content.getContent().get(0);
        if (entity instanceof Person) {
            return PersonTransformer.INSTANCE.toDto((Person) entity);
        } else {
            return OrganizationTransformer.INSTANCE.toDto((Organization) entity);
        }   
    }
    
    /**
     * Help transform entity dto into entity.
     * @param entityDto entity dto.
     * @return entity.
     * @throws DtoTransformException if there is an error during transform.
     */
    public static EntityType toXmlEntity(EntityDto entityDto) throws DtoTransformException {
        Entity entity = null;
        if (entityDto instanceof PersonDTO) {
            entity =  PersonTransformer.INSTANCE.toXml((PersonDTO) entityDto);
        } else {
            entity = OrganizationTransformer.INSTANCE.toXml((OrganizationDTO) entityDto);
        }   
        
        EntityType returnVal = new EntityType();
        returnVal.getContent().add(entity);
        return returnVal;
    }

}
