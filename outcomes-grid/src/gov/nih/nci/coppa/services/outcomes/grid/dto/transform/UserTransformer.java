/**
 * The software subject to this notice and license includes both human readable
 * source code form and machine readable, binary, object code form. The OutcomesServices
 * Software was developed in conjunction with the National Cancer Institute 
 * (NCI) by NCI employees and 5AM Solutions, Inc. (5AM). To the extent 
 * government employees are authors, any rights in such works shall be subject 
 * to Title 17 of the United States Code, section 105. 
 *
 * This OutcomesServices Software License (the License) is between NCI and You. You (or 
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
 * its rights in the OutcomesServices Software to (i) use, install, access, operate, 
 * execute, copy, modify, translate, market, publicly display, publicly perform,
 * and prepare derivative works of the OutcomesServices Software; (ii) distribute and 
 * have distributed to and by third parties the OutcomesServices Software and any 
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
package gov.nih.nci.coppa.services.outcomes.grid.dto.transform;

import gov.nih.nci.coppa.services.outcomes.User;
import gov.nih.nci.iso21090.grid.dto.transform.AbstractTransformer;
import gov.nih.nci.iso21090.grid.dto.transform.DtoTransformException;
import gov.nih.nci.iso21090.grid.dto.transform.Transformer;
import gov.nih.nci.iso21090.grid.dto.transform.iso.IITransformer;
import gov.nih.nci.iso21090.grid.dto.transform.iso.STTransformer;
import gov.nih.nci.outcomes.svc.dto.UserSvcDto;

/**
 * @author mshestopalov
 *
 */
public final class UserTransformer 
    extends AbstractTransformer<User, UserSvcDto>
    implements Transformer<User, UserSvcDto> {

    /**
     * Public singleton.
     */
    public static final UserTransformer INSTANCE = new UserTransformer();

    /**
     * {@inheritDoc}
     */
    public User[] createXmlArray(int arg0) throws DtoTransformException {
        return new User[arg0];
    }

    /**
     * {@inheritDoc}
     */
    public UserSvcDto toDto(User arg0) throws DtoTransformException {
        if (arg0 == null) {
            return null;
        }
        UserSvcDto returnVal = new UserSvcDto();
        returnVal.setIdentifier(IITransformer.INSTANCE.toDto(arg0.getIdentifier()));
        returnVal.setAddress(STTransformer.INSTANCE.toDto(arg0.getAddress()));
        returnVal.setAffiliateOrg(STTransformer.INSTANCE.toDto(arg0.getAffiliateOrg()));
        returnVal.setCity(STTransformer.INSTANCE.toDto(arg0.getCity()));
        returnVal.setCountry(STTransformer.INSTANCE.toDto(arg0.getCountry()));
        returnVal.setFirstName(STTransformer.INSTANCE.toDto(arg0.getFirstName()));
        returnVal.setLastName(STTransformer.INSTANCE.toDto(arg0.getLastName()));
        returnVal.setMiddleName(STTransformer.INSTANCE.toDto(arg0.getMiddleName()));
        returnVal.setPassword(STTransformer.INSTANCE.toDto(arg0.getPassword()));
        returnVal.setPhone(STTransformer.INSTANCE.toDto(arg0.getPhone()));
        returnVal.setPostalCode(STTransformer.INSTANCE.toDto(arg0.getPostalCode()));
        returnVal.setPrsOrg(STTransformer.INSTANCE.toDto(arg0.getPrsOrg()));
        returnVal.setState(STTransformer.INSTANCE.toDto(arg0.getState()));
        returnVal.setIdentity(STTransformer.INSTANCE.toDto(arg0.getIdentity()));
        returnVal.setTreatmentSiteIdentifier(IITransformer.INSTANCE.toDto(arg0.getTreatmentSiteIdentifier()));
        returnVal.setPhysicianIdentifier(IITransformer.INSTANCE.toDto(arg0.getPhysicianIdentifier()));
        return returnVal;
    }

    /**
     * {@inheritDoc}
     */
    public User toXml(UserSvcDto arg0) throws DtoTransformException {
        if (arg0 == null) {
            return null;
        }
        User returnVal = new User();
        returnVal.setIdentifier(IITransformer.INSTANCE.toXml(arg0.getIdentifier()));
        returnVal.setAddress(STTransformer.INSTANCE.toXml(arg0.getAddress()));
        returnVal.setAffiliateOrg(STTransformer.INSTANCE.toXml(arg0.getAffiliateOrg()));
        returnVal.setCity(STTransformer.INSTANCE.toXml(arg0.getCity()));
        returnVal.setCountry(STTransformer.INSTANCE.toXml(arg0.getCountry()));
        returnVal.setFirstName(STTransformer.INSTANCE.toXml(arg0.getFirstName()));
        returnVal.setLastName(STTransformer.INSTANCE.toXml(arg0.getLastName()));
        returnVal.setMiddleName(STTransformer.INSTANCE.toXml(arg0.getMiddleName()));
        returnVal.setPassword(STTransformer.INSTANCE.toXml(arg0.getPassword()));
        returnVal.setPhone(STTransformer.INSTANCE.toXml(arg0.getPhone()));
        returnVal.setPostalCode(STTransformer.INSTANCE.toXml(arg0.getPostalCode()));
        returnVal.setPrsOrg(STTransformer.INSTANCE.toXml(arg0.getPrsOrg()));
        returnVal.setState(STTransformer.INSTANCE.toXml(arg0.getState()));
        returnVal.setIdentity(STTransformer.INSTANCE.toXml(arg0.getIdentity()));
        returnVal.setTreatmentSiteIdentifier(IITransformer.INSTANCE.toXml(arg0.getTreatmentSiteIdentifier()));
        returnVal.setPhysicianIdentifier(IITransformer.INSTANCE.toXml(arg0.getPhysicianIdentifier()));
        return returnVal;
    }

}
