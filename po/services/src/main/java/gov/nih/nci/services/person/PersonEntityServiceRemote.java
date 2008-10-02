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

import gov.nih.nci.coppa.iso.Cd;
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.po.service.EntityValidationException;
import gov.nih.nci.services.entity.NullifiedEntityException;

import java.util.List;
import java.util.Map;

import javax.ejb.Remote;

/**
 * 
 * @author lpower
 */
@Remote
public interface PersonEntityServiceRemote {

    /**
     * @param id person id
     * @return Person
     * @throws NullifiedEntityException if the requested id has a NULLIFIED entity status
     */
    PersonDTO getPerson(Ii id) throws NullifiedEntityException;

    /**
     * Remote API to create a person.
     * 
     * @param person Person
     * @return db id
     * @throws EntityValidationException if validation fails.
     */
    Ii createPerson(PersonDTO person) throws EntityValidationException;

    /**
     * Validate a person entity.
     * 
     * @param person the person to validate.
     * @return return validation error messages per invalid field path.
     */
    Map<String, String[]> validate(PersonDTO person);

    /**
     * Provides the ability to find people using conjunctional insensitive substring matching.   
     * 
     * <pre>
     * You may search by specifying combinations of the following: 
     * <li>Family name (last name)</li>
     * <li>Given name (first name). The first given name provided corresponds to a person's 
     * first name. The second given name will correspond to the person's middle name. Subsequent 
     * given names will allow for If more than one given name is provided this will allow you to 
     * search by middle name</li>
     * <li>Given name (middle name). To only search by middle name you must provide two given 
     * names with the first given name being set to "%"</li>
     * <li>Prefix (prefix)</li>
     * <li>Suffix (suffix)</li>
     * 
     * The following are unsupported:
     * <li>PersonDTO.identifier</li>
     * <li>PersonDTO.telecomAddress</li>
     * <li>PersonDTO.postalAddress</li>
     * </pre>
     * @param person criteria used to find matching people
     * @return list of matching people
     */
    List<PersonDTO> search(PersonDTO person);
    
     /**
     * Propose a new entity value to the curator.
     * @param proposedState the CR containg the proposed stated.
     * @throws EntityValidationException if the CR proposes an invalid state for the target.
     */
    void updatePerson(PersonDTO proposedState) throws EntityValidationException;
    
    /**
     * Propose a new status code to the curator.
     * @param targetPer the ID of the person to update.
     * @param statusCode the new status code.
     * @throws EntityValidationException if the CR proposes an invalid state for the target.
     */
    void updatePersonStatus(Ii targetPer, Cd statusCode) throws EntityValidationException;
}
