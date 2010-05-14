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
package gov.nih.nci.accrual.outweb.action;

import gov.nih.nci.accrual.outweb.OutcomesWebException;
import gov.nih.nci.accrual.outweb.dto.util.UserAccountWebDto;
import gov.nih.nci.accrual.outweb.mail.MailManager;
import gov.nih.nci.accrual.outweb.util.EncoderDecoder;
import gov.nih.nci.outcomes.svc.util.OutcomesPropertyReader;
import gov.nih.nci.outcomes.svc.util.SvcConstants;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.security.cgmm.exceptions.CGMMException;

import java.util.Map;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.Preparable;

/**
 * @author smatyas
 * 
 */
public class RegisterAction extends AccountSupportAction implements Preparable {

    private static final long serialVersionUID = 1L;

    private UserAccountWebDto userAccount = new UserAccountWebDto();
    private AccountActions userAction = null;

    /**
     * {@inheritDoc}
     */
    @Override
    public void prepare() {
        super.prepare();
        getUserAccount().setIdentity(StConverter.convertToSt(getUserDN()));
    }

    /**
     * @return the authenicated user's Grid Identity DN
     */
    protected String getUserDN() {
        return ServletActionContext.getRequest().getUserPrincipal().getName();
    }

    private String getUserCN() {
        return getUserDN().split("CN=")[1];
    }

    /**
     * {@inheritDoc}
     */
    public String start() {
        Map<String, String> userInformation;
        try {
            userInformation = new gov.nih.nci.security.cgmm.helper.impl.GridAuthHelper().getAttributesMap(getUserCN(),
                    getPassword(), OutcomesPropertyReader.getAuthServiceUrl());
        } catch (CGMMException e) {
            throw new OutcomesWebException(e);
        }

        getUserAccount().setEmail(StConverter.convertToSt(userInformation.get("CGMM_EMAIL_ID").toString()));
        getUserAccount().setFirstName(StConverter.convertToSt(userInformation.get("CGMM_FIRST_NAME").toString()));
        getUserAccount().setLastName(StConverter.convertToSt(userInformation.get("CGMM_LAST_NAME").toString()));
        return SUCCESS;
    }

    /**
     * {@inheritDoc}
     */
    public String request() {
        getUserAccount().validate(this);
        if (hasFieldErrors()) {
            return "start";
        }

        try {
            final MailManager mailManager = new MailManager();
            mailManager.sendConfirmationMail(StConverter.convertToString(getUserAccount().getEmail()), StConverter
                    .convertToString(getUserAccount().getIdentity()));
            LOG.info(" sending email to " + StConverter.convertToString(getUserAccount().getEmail()));
        } catch (Exception e) {
            LOG.error("error while sending e-mail");
            return ERROR;
        }
        return SUCCESS;
    }

    /**
     * {@inheritDoc}
     */
    public String activate() {
        setUserAction(AccountActions.ACTIVATE);
        String loginName = decodeString(ServletActionContext.getRequest().getParameter("loginName"));
        String email = decodeString(ServletActionContext.getRequest().getParameter("email"));

        LOG.info("decoded login name: " + loginName);
        LOG.info("decoded email: " + email);

        if (!getUserDN().equals(loginName)) {
            setUserAction(AccountActions.REQUEST_LOGIN_MISMATCH);
            return "redirectToLogoutThenLogin";
        }

        getUserAccount().setEmail(StConverter.convertToSt(email));
        setUserAction(AccountActions.ACTIVATE);
        return SUCCESS;
    }

    private String decodeString(String src) {
        if (src != null) {
            return EncoderDecoder.decodeString(src);
        }
        return null;
    }

    /**
     * Creates the user account.
     * 
     * @return the result
     */
    public String create() {
        getUserAccount().setAction(SvcConstants.CREATE);
        getUserAccount().validate(this);
        if (hasFieldErrors() || hasActionErrors()) {
            return "activate";
        }
        try {
            // create the user
            userSvc.createUser(getUserAccount().getSvcDto());
            setUserAction(AccountActions.CREATE);
            return "redirectToLogoutThenLogin";
        } catch (Exception e) {
            LOG.error("error while updating user info", e);
            return ERROR;
        }
    }

    /**
     * @param userAccount the userAccount to set
     */
    public void setUserAccount(UserAccountWebDto userAccount) {
        this.userAccount = userAccount;
    }

    /**
     * @return the userAccount
     */
    public UserAccountWebDto getUserAccount() {
        return userAccount;
    }

    /**
     * @param userAction the userAction to set
     */
    public void setUserAction(AccountActions userAction) {
        this.userAction = userAction;
    }

    /**
     * @return the userAction
     */
    public AccountActions getUserAction() {
        return userAction;
    }

}
