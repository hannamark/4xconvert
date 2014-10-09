/**
 * The software subject to this notice and license includes both human readable
 * source code form and machine readable, binary, object code form. The po-app
 * Software was developed in conjunction with the National Cancer Institute
 * (NCI) by NCI employees and 5AM Solutions, Inc. (5AM). To the extent
 * government employees are authors, any rights in such works shall be subject
 * to Title 17 of the United States Code, section 105.
 *
 * This po-app Software License (the License) is between NCI and You. You (or
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
 * its rights in the po-app Software to (i) use, install, access, operate,
 * execute, copy, modify, translate, market, publicly display, publicly perform,
 * and prepare derivative works of the po-app Software; (ii) distribute and
 * have distributed to and by third parties the po-app Software and any
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
package gov.nih.nci.services.organization;

import gov.nih.nci.coppa.services.LimitOffset;
import gov.nih.nci.coppa.services.TooManyResultsException;
import gov.nih.nci.iso21090.Cd;
import gov.nih.nci.iso21090.EnOn;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.po.data.CurationException;
import gov.nih.nci.po.service.EntityValidationException;
import gov.nih.nci.services.entity.NullifiedEntityException;

import javax.ejb.Remote;
import java.util.List;
import java.util.Map;
/**
 *
 * @author gax
 */
@Remote
public interface OrganizationEntityServiceRemote {

    /**
     * @param id org id (the Ii.extension must be populated with desired IdentifierReliability.ISS value)
     * @return organization
     * @throws NullifiedEntityException if the requested id has a NULLIFIED entity status
     */
    OrganizationDTO getOrganization(Ii id) throws NullifiedEntityException;

    /**
     * Remote API to create an Organization.
     * @param org organization
     * @return db id
     * @throws EntityValidationException if validation fails
     * @throws CurationException if any unrecoverable error occurred
     */
    Ii createOrganization(OrganizationDTO org) throws EntityValidationException, CurationException;

    /**
     * Validate that an entity has acceptable values.
     * @param org the entity to validate
     * @return return validation error messages per invalid field path.
     */
    Map<String, String[]> validate(OrganizationDTO org);

    /**
     * Provides the ability to find organizations using conjunctional insensitive substring matching.
     *
     * <pre>
     * You may search by specifying combinations of the following:
     * <li>EnOn name (name)</li>
     * <li>St description (description).</li>
     * <li>OrganizationDTO.identifier</li>
     * <li>OrganizationDTO.telecomAddress</li>
     * <li>OrganizationDTO.postalAddress</li>
     * </pre>
     * @param organization criteria used to find matching organizations
     * @return list of matching organizations
     * @deprecated 
     */
    @Deprecated
    List<OrganizationDTO> search(OrganizationDTO organization);
    
    /**
     * This method is an extension of the existing search method. The key difference being the support for paginated
     * results (similar to SQL LIMIT OFFSET queries).
     * 
     * @see #search(OrganizationDTO) for general search behavior
     * @see LimitOffset#LimitOffset(int, int) for special notes related to behavior
     * @param organization criteria used to find matching organizations
     * @param pagination the settings for control pagination of results
     * @return list of matching organizations
     * @throws TooManyResultsException when the system's limit is exceeded
     */
    List<OrganizationDTO> search(OrganizationDTO organization, LimitOffset pagination) 
        throws TooManyResultsException;
    
    /**
     * @param criteriaDTO OrganizationSearchCriteriaDTO
     * @param pagination LimitOffset
     * @return List<OrganizationDTO>
     * @throws TooManyResultsException TooManyResultsException
     */
    List<OrganizationDTO> search(OrganizationSearchCriteriaDTO criteriaDTO, LimitOffset pagination) 
            throws TooManyResultsException;    
    
    /**
     * This method is an extension of the existing paginated search method. The key difference being the support 
     * search by family name.
     * 
     * @see #search(OrganizationDTO) for general search behavior
     * @see LimitOffset#LimitOffset(int, int) for special notes related to behavior
     * @param organization criteria used to find matching organizations
     * @param familyName used to find matching organizations
     * @param pagination the settings for control pagination of results
     * @return list of matching organizations
     * @throws TooManyResultsException when the system's limit is exceeded
     */
    List<OrganizationDTO> search(OrganizationDTO organization, EnOn familyName, LimitOffset pagination)
    throws TooManyResultsException;

    /**
     * Propose a new entity value to the curator.
     * @param proposedState the CR containg the proposed stated.
     * @throws EntityValidationException if the CR proposes an invalid state for the target.
     */
    void updateOrganization(OrganizationDTO proposedState) throws EntityValidationException;

    /**
     * Propose a new status code to the curator.
     * @param targetOrg the ID of the Org to update.
     * @param statusCode the new Status Code.
     * @throws EntityValidationException if the CR proposes an invalid state for the target.
     */
    void updateOrganizationStatus(Ii targetOrg, Cd statusCode) throws EntityValidationException;
    
    /**
     * Finds a nullified organization that used to have the given CTEP ID. If found, returns the organization's
     * Duplicate Of attribute.
     * @param ctepID given CTEP ID
     * @return Ii Duplicate Of attribute
     */
    Ii getDuplicateOfNullifiedOrg(String ctepID);
}
